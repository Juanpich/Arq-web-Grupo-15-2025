package org.example.iaagentservice.infrastructure.controllers;

import org.example.iaagentservice.application.services.AIAgentService;
import org.example.iaagentservice.domain.dto.ChatRequest;
import org.example.iaagentservice.domain.dto.ChatResponse;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final AIAgentService aiAgentService;

    public ChatController(AIAgentService aiAgentService) {
        this.aiAgentService = aiAgentService;
    }

//    @PostMapping
//    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request, Authentication authentication) {
//        boolean isPremium = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .anyMatch(role -> role.equals("ROLE_PREMIUM"));
//
//        if (!isPremium) {
//            return ResponseEntity.status(403)
//                    .body(new ChatResponse("Esta funcionalidad es exclusiva para usuarios premium"));
//        }
//
//        String userId = authentication.getName();
//        ChatResponse response = aiAgentService.processQuery(userId, request.getMessage());
//
//        return ResponseEntity.ok(response);
//    }
}
