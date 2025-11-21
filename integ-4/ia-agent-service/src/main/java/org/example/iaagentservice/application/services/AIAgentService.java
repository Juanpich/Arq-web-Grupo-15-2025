package org.example.iaagentservice.application.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.iaagentservice.domain.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AIAgentService {

    private final RestTemplate restTemplate;
    private final ToolRegistry toolRegistry;
    private final ObjectMapper objectMapper;
    private final String groqApiKey;
    private final String groqApiUrl;

    public AIAgentService(RestTemplate restTemplate, ToolRegistry toolRegistry, ObjectMapper objectMapper,
                          @Value("${groq.api.key}") String groqApiKey,
                          @Value("${groq.api.url}") String groqApiUrl) {
        this.restTemplate = restTemplate;
        this.toolRegistry = toolRegistry;
        this.objectMapper = objectMapper;
        this.groqApiKey = groqApiKey;
        this.groqApiUrl = groqApiUrl;
    }

    public ChatResponse processQuery(String userId, String userQuery) {
        try {
            // Construir request inicial con tools
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama3-70b-8192");
            requestBody.put("messages", buildMessages(userQuery));
            requestBody.put("tools", toolRegistry.getToolsDefinitions());
            requestBody.put("tool_choice", "auto");

            // Primera llamada a Groq
            ResponseEntity<String> response = restTemplate.exchange(
                groqApiUrl,
                HttpMethod.POST,
                buildHttpEntity(requestBody),
                String.class
            );

            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            JsonNode message = jsonResponse.get("choices").get(0).get("message");

            // Verificar si Groq quiere ejecutar tools
            if (message.has("tool_calls")) {
                return handleToolCalls(userId, message.get("tool_calls"), userQuery);
            } else {
                // Respuesta directa sin tools
                String content = message.get("content").asText();
                return new ChatResponse(content);
            }

        } catch (Exception e) {
            return new ChatResponse("Lo siento, hubo un error procesando tu consulta: " + e.getMessage());
        }
    }

    private ChatResponse handleToolCalls(String userId, JsonNode toolCalls, String originalQuery) {
        List<String> results = new ArrayList<>();

        try {
            // Ejecutar cada tool solicitada
            for (JsonNode toolCall : toolCalls) {
                String toolName = toolCall.get("function").get("name").asText();
                String argumentsStr = toolCall.get("function").get("arguments").asText();
                JsonNode arguments = objectMapper.readTree(argumentsStr);

                String result = toolRegistry.executeTool(toolName, userId, arguments);
                results.add(result);
            }

            // Segunda llamada a Groq con los resultados
            return generateFinalResponse(originalQuery, results);

        } catch (Exception e) {
            return new ChatResponse("Error ejecutando herramientas: " + e.getMessage());
        }
    }

    private ChatResponse generateFinalResponse(String query, List<String> toolResults) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama3-70b-8192");

            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "user", "content", query));
            messages.add(Map.of("role", "assistant", "content", String.join("\n", toolResults)));

            requestBody.put("messages", messages);

            ResponseEntity<String> response = restTemplate.exchange(
                groqApiUrl,
                HttpMethod.POST,
                buildHttpEntity(requestBody),
                String.class
            );

            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            String content = jsonResponse.get("choices").get(0).get("message").get("content").asText();

            return new ChatResponse(content);

        } catch (Exception e) {
            return new ChatResponse("Resultados: " + String.join(", ", toolResults));
        }
    }

    private List<Map<String, String>> buildMessages(String userQuery) {
        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "Eres un asistente virtual para un sistema de alquiler de monopatines eléctricos. " +
                                     "Ayudas a usuarios premium con consultas sobre precios, distancias y uso de monopatines.");

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userQuery);

        messages.add(systemMessage);
        messages.add(userMessage);

        return messages;
    }

    private HttpEntity<Map<String, Object>> buildHttpEntity(Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + groqApiKey);
        headers.set("Content-Type", "application/json");
        return new HttpEntity<>(body, headers);
    }
}
