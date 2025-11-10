package com.physiotrack.backend.controller;

import com.physiotrack.backend.service.ChatService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/openai")
    public ResponseEntity<String> perguntar(@RequestBody Map<String, String> body) {
        try {
            String pergunta = body.get("pergunta");
            String resposta = chatService.perguntar(pergunta);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    @PostMapping("/gemini")
    public ResponseEntity<String> perguntarGemini(@RequestBody String pergunta) {
        try {
            String resposta = chatService.perguntarGemini(pergunta);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
}