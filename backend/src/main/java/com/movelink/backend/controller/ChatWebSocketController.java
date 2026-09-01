package com.movelink.backend.controller;

import com.movelink.backend.dto.ChatMessageRequest;
import com.movelink.backend.entity.ChatMessage;
import com.movelink.backend.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final ChatService chatService;

    public ChatWebSocketController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.send")
    public ChatMessage sendMessage(
            @Payload ChatMessageRequest request) {

        return chatService.sendRealtimeMessage(request);
    }
}