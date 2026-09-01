package com.movelink.backend.service;

import com.movelink.backend.dto.ChatMessageRequest;
import com.movelink.backend.entity.ChatMessage;
import com.movelink.backend.repository.ChatRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatServiceImpl(
            ChatRepository chatRepository,
            SimpMessagingTemplate messagingTemplate) {

        this.chatRepository = chatRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public ChatMessage sendMessage(ChatMessageRequest request) {

        ChatMessage message = ChatMessage.builder()
                .rideId(request.getRideId())
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .messageType(request.getMessageType())
                .message(request.getMessage())
                .build();

        return chatRepository.save(message);
    }

    @Override
    public ChatMessage sendRealtimeMessage(ChatMessageRequest request) {

        ChatMessage saved = sendMessage(request);

        messagingTemplate.convertAndSend(
                "/topic/chat/" + request.getRideId(),
                saved
        );

        return saved;
    }

    @Override
    public List<ChatMessage> getRideMessages(Long rideId) {

        return chatRepository.findByRideIdOrderBySentAtAsc(rideId);
    }
}