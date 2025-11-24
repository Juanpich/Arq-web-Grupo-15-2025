package org.example.iaagentservice.infrastructure.controllers;

import org.example.iaagentservice.application.services.AIAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
public class ChatController {

    /**
     * Definir en intelij una variable de entorno:
     * OLLAMA_API_KEY con la API Key de Ollama.
     * la api que pase en el grupo
     */

    @Autowired
    private AIAgentService aiAgentService;

    @PostMapping(value = "/prompt", produces = "application/json")
    public ResponseEntity<?> procesarPrompt(@RequestBody String prompt) {
        try {
            return aiAgentService.processPrompt(prompt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el prompt: " + e.getMessage());
        }
    }
}

