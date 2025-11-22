package org.example.iaagentservice.application.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ToolRegistry {

    private final RestTemplate restTemplate;

    public ToolRegistry(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getToolsDefinitions() {
        List<Map<String, Object>> tools = new ArrayList<>();

        // Tool 1: List all user journeys
        tools.add(createToolDefinition(
            "get_all_user_journeys",
            "Obtiene el listado de todos los viajes realizados por un usuario.",
            Map.of(), // no parameters from LLM; userId provided by executeTool
            List.of()
        ));

        // Tool 2: User journeys by date range
        tools.add(createToolDefinition(
            "get_user_journeys_by_date_range",
            "Obtiene los viajes de un usuario en un rango de fechas. Las fechas deben estar en formato 'YYYY-MM-DD'.",
            Map.of(
                "start_date", new ParamDef("string", "Fecha de inicio en formato YYYY-MM-DD"),
                "end_date", new ParamDef("string", "Fecha de fin en formato YYYY-MM-DD")
            ),
            List.of("start_date", "end_date")
        ));

        // Tool 3: Get journey price
        tools.add(createToolDefinition(
            "get_journey_price",
            "Consulta el precio de un viaje específico usando su ID.",
            Map.of(
                "journey_id", new ParamDef("number", "ID del viaje")
            ),
            List.of("journey_id")
        ));

        return tools;
    }

    public String executeTool(String toolName, String userId, JsonNode arguments) {
        try {
            switch (toolName) {
                case "get_all_user_journeys":
                    return getAllUserJourneys(userId);

                case "get_user_journeys_by_date_range":
                    return getUserJourneysByDateRange(userId, arguments);

                case "get_journey_price":
                    return getJourneyPrice(arguments);

                default:
                    return "Error: Herramienta no encontrada - " + toolName;
            }
        } catch (Exception e) {
            return "Error ejecutando " + toolName + ": " + e.getMessage();
        }
    }

    // New Tool implementations

    private String getAllUserJourneys(String userId) {
        if (userId == null || userId.isBlank()) {
            return "Error: userId no proporcionado";
        }
        String url = "http://localhost:8002/journey/user/" + userId;
        String response = restTemplate.getForObject(url, String.class);
        return response != null ? response : "[]";
    }

    private String getUserJourneysByDateRange(String userId, JsonNode arguments) {
        if (userId == null || userId.isBlank()) {
            return "Error: userId no proporcionado";
        }
        if (arguments == null || !arguments.has("start_date") || !arguments.has("end_date")) {
            return "Error: se requieren los parámetros start_date y end_date";
        }
        String start = arguments.get("start_date").asText();
        String end = arguments.get("end_date").asText();

        String url = "http://localhost:8002/journey/byUser/" + userId +
                     "?start-date=" + start + "&end-date=" + end;
        String response = restTemplate.getForObject(url, String.class);
        return response != null ? response : "[]";
    }

    private String getJourneyPrice(JsonNode arguments) {
        if (arguments == null || !arguments.has("journey_id")) {
            return "Error: se requiere el parámetro journey_id";
        }
        String id = arguments.get("journey_id").asText();
        String url = "http://localhost:8002/journey/" + id + "/price";
        String response = restTemplate.getForObject(url, String.class);
        return response != null ? response : "No se encontró precio para el viaje";
    }

    private Map<String, Object> createToolDefinition(String name, String description,
                                                     Map<String, ParamDef> parameters,
                                                     List<String> required) {
        Map<String, Object> tool = new HashMap<>();
        tool.put("type", "function");

        Map<String, Object> function = new HashMap<>();
        function.put("name", name);
        function.put("description", description);

        Map<String, Object> params = new HashMap<>();
        params.put("type", "object");

        Map<String, Object> properties = new HashMap<>();
        parameters.forEach((key, paramDef) -> {
            Map<String, String> property = new HashMap<>();
            property.put("type", paramDef.type);
            property.put("description", paramDef.description);
            properties.put(key, property);
        });

        params.put("properties", properties);
        params.put("required", required);

        function.put("parameters", params);
        tool.put("function", function);

        return tool;
    }

    private static class ParamDef {
        String type;
        String description;

        ParamDef(String type, String description) {
            this.type = type;
            this.description = description;
        }
    }
}
