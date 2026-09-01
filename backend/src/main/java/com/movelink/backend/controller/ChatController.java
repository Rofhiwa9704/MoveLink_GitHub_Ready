package com.movelink.backend.controller;

import com.movelink.backend.dto.ChatMessageRequest;
import com.movelink.backend.entity.ChatMessage;
import com.movelink.backend.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public ResponseEntity<ChatMessage> sendMessage(
            @RequestBody ChatMessageRequest request) {

        return ResponseEntity.ok(chatService.sendMessage(request));
    }

    @PostMapping("/send-realtime")
public ResponseEntity<ChatMessage> sendRealtimeMessage(
        @RequestBody ChatMessageRequest request) {

    return ResponseEntity.ok(
            chatService.sendRealtimeMessage(request)
    );
}

    @GetMapping("/{rideId}")
    public ResponseEntity<List<ChatMessage>> getMessages(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(chatService.getRideMessages(rideId));
    }
}