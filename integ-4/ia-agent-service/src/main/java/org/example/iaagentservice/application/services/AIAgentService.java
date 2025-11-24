package org.example.iaagentservice.application.services;

import org.example.iaagentservice.infrastructure.feign.JourneyFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.example.iaagentservice.clients.GroqClient;
import org.example.iaagentservice.domain.dto.ChatResponse;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * - Servicio que:
 * - Construye el prompt con el esquema SQL
 * - Llama a Groq para generar SQL
 * - Valida y extrae una unica sentencia SQL (SELECT/INSERT/UPDATE/DELETE)
 * - Ejecuta de forma segura (bloquea DDL peligrosos)
 */
@Service
public class AIAgentService {

    private JourneyFeignClient joruneyFeignClient;

    @Autowired
    private GroqClient groqChatClient;

    private final String SQL_CONTEXT;

    private static final Logger log = LoggerFactory.getLogger(AIAgentService.class);

    // Reglas de extracción/seguridad para la sentencia SQL
    // se acepta solo una sentencia que empiece por SELECT|INSERT|UPDATE|DELETE
    // y que termine en ';'
    private static final Pattern SQL_ALLOWED =
            Pattern.compile("(?is)\\b(SELECT|INSERT|UPDATE|DELETE)\\b[\\s\\S]*?;");

    // se bloquean operaciones peligrosas por si el modelo "derrapa"
    private static final Pattern SQL_FORBIDDEN =
            Pattern.compile("(?i)\\b(DROP|TRUNCATE|ALTER|CREATE|GRANT|REVOKE)\\b");

    public AIAgentService(JourneyFeignClient jfc) {
        this.SQL_CONTEXT = loadSQLschema("context_squema.sql");
        this.joruneyFeignClient = jfc;
    }

    private String loadSQLschema(String fileName) {
        try (InputStream inputStream = new ClassPathResource(fileName).getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo SQL desde resources: " + e.getMessage(), e);
        }
    }

    /** Genera el prompt, obtiene la SQL de Groq, la valida y ejecuta. */
    // @Transactional para soportar INSERT/UPDATE/DELETE
    @Transactional
    public ResponseEntity<?> processPrompt(String userPrompt) {
        try {
            String finalPrompt = """
                    Este es el esquema de mi base de datos MySQL:
                    %s

                    Basándote exclusivamente en este esquema, devolveme ÚNICAMENTE una sentencia SQL
                    MySQL completa y VÁLIDA (sin texto adicional, sin markdown, sin comentarios) que
                    termine con punto y coma. La sentencia puede ser SELECT/INSERT/UPDATE/DELETE.
                    %s
                    """.formatted(SQL_CONTEXT, userPrompt);

            log.info("<-- PROMPT ENVIADO A LA IA -->\n{}", finalPrompt);

            String respuestaIa = groqChatClient.ask(finalPrompt);
            log.info("<-- RESPUESTA IA -->\n{}", respuestaIa);

            // Usamos la nueva extracción segura (usando el metodo de extracccion de la clase)
            String sql = extractSQLRequest(respuestaIa);

            if (sql == null || sql.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ChatResponse<>(false,
                                "No se encontró una sentencia SQL válida en la respuesta de la IA.", null));
            }

            log.info("<-- SQL EXTRAIDA -->\n{}", sql);

            // saca el ; final si lo tiene, jpa/jdbc no lo necesitan
            String sqlToExecute = sql.endsWith(";") ? sql.substring(0, sql.length() - 1) : sql;

            try {
                Object data;
                // Se llama al microservicio Journey para ejecutar la SQL
                    List<Object[]> results = joruneyFeignClient.executeSqlRequest(sqlToExecute);
                    data = results;
                    return ResponseEntity.ok(new ChatResponse<>(true, "Consulta ejecutada con éxito", data));
            } catch (Exception e) {
                log.warn("Error al ejecutar SQL: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ChatResponse<>(false, "Error al ejecutar la sentencia: " + e.getMessage(), null));
            }

        } catch (Exception e) {
            log.error("Fallo al procesar prompt", e);
            return new ResponseEntity<>(
                    new ChatResponse<>(false, "Error al procesar el prompt: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    //      metodo de extraccion de sentencia sql
    //   - Acepta SOLO una sentencia que empiece con SELECT/INSERT/UPDATE/DELETE
    //   - Exige punto y coma final
    //   - Recorta t0do lo que venga despues de ";"
    //   - Bloquea DDL peligrosos (DROP/TRUNCATE/ALTER/CREATE/GRANT/REVOKE)
    private String extractSQLRequest(String respuesta) {
        if (respuesta == null) return null;

        Matcher m = SQL_ALLOWED.matcher(respuesta);
        if (!m.find()) return null;

        String sql = m.group().trim();

        // Asegurar UNA sola sentencia (hasta el primer ';')
        int first = sql.indexOf(';');
        if (first > -1) {
            sql = sql.substring(0, first + 1);
        }

        // Bloquear DDL
        if (SQL_FORBIDDEN.matcher(sql).find()) {
            log.warn("Sentencia bloqueada por contener DDL prohibido: {}", sql);
            return null;
        }

        return sql;
    }
}
