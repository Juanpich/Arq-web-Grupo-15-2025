package org.example.iaagentservice.infrastructure.controllers;

import org.example.iaagentservice.application.services.AIAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ia-chat")
public class ChatController {

    /**
     * variable de entorno:
     * GORQ_API_KEY = api key que pase al grupo
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

