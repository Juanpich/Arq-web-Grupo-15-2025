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

        // Tool 1: Calcular precio
        tools.add(createToolDefinition(
            "calculate_trip_price",
            "Calcula el precio de un viaje en monopatín basado en distancia y duración",
            Map.of(
                "distance", new ParamDef("number", "Distancia en kilómetros"),
                "duration", new ParamDef("number", "Duración en minutos")
            ),
            List.of("distance", "duration")
        ));

        // Tool 2: Calcular distancia
        tools.add(createToolDefinition(
            "get_distance_to_location",
            "Calcula la distancia desde la ubicación actual del usuario hasta un destino",
            Map.of(
                "destination", new ParamDef("string", "Nombre del lugar de destino")
            ),
            List.of("destination")
        ));

        // Tool 3: Estadísticas de uso
        tools.add(createToolDefinition(
            "get_user_scooter_usage",
            "Obtiene estadísticas de uso de monopatines del usuario",
            Map.of(
                "period", new ParamDef("string", "Período: 'month', 'week', 'all'")
            ),
            List.of()
        ));

        // Tool 4: Monopatines cercanos
        tools.add(createToolDefinition(
            "find_nearby_scooters",
            "Encuentra monopatines disponibles cerca de una ubicación",
            Map.of(
                "location", new ParamDef("string", "Ubicación de búsqueda"),
                "radius", new ParamDef("number", "Radio de búsqueda en km")
            ),
            List.of()
        ));

        return tools;
    }

    public String executeTool(String toolName, String userId, JsonNode arguments) {
        try {
            switch (toolName) {
                case "calculate_trip_price":
                    return calculateTripPrice(arguments);

                case "get_distance_to_location":
                    return getDistanceToLocation(userId, arguments);

                case "get_user_scooter_usage":
                    return getUserScooterUsage(userId, arguments);

                case "find_nearby_scooters":
                    return findNearbyScooters(userId, arguments);

                default:
                    return "Error: Herramienta no encontrada - " + toolName;
            }
        } catch (Exception e) {
            return "Error ejecutando " + toolName + ": " + e.getMessage();
        }
    }

    private String calculateTripPrice(JsonNode args) {
        double distance = args.get("distance").asDouble();
        double duration = args.get("duration").asDouble();

        String url = "http://pricing-service/api/pricing/calculate?distance=" + distance + "&duration=" + duration;
        String response = restTemplate.getForObject(url, String.class);

        return "Precio calculado: " + response;
    }

    private String getDistanceToLocation(String userId, JsonNode args) {
        String destination = args.get("destination").asText();

        String url = "http://location-service/api/locations/distance?userId=" + userId + "&destination=" + destination;
        String response = restTemplate.getForObject(url, String.class);

        return "Distancia a " + destination + ": " + response;
    }

    private String getUserScooterUsage(String userId, JsonNode args) {
        String period = args.has("period") ? args.get("period").asText() : "all";

        String url = "http://trip-service/api/trips/usage/" + userId + "?period=" + period;
        String response = restTemplate.getForObject(url, String.class);

        return "Estadísticas de uso: " + response;
    }

    private String findNearbyScooters(String userId, JsonNode args) {
        String location = args.has("location") ? args.get("location").asText() : "current";
        double radius = args.has("radius") ? args.get("radius").asDouble() : 2.0;

        String url = "http://scooter-service/api/scooters/nearby?userId=" + userId +
                     "&location=" + location + "&radius=" + radius;
        String response = restTemplate.getForObject(url, String.class);

        return "Monopatines cercanos: " + response;
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
