package com.movelink.backend.service;

import com.movelink.backend.dto.ChatMessageRequest;
import com.movelink.backend.entity.ChatMessage;

import java.util.List;

public interface ChatService {

    ChatMessage sendMessage(ChatMessageRequest request);

    ChatMessage sendRealtimeMessage(ChatMessageRequest request);

    List<ChatMessage> getRideMessages(Long rideId);
}