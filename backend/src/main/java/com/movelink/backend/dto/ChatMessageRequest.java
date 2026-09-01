package com.movelink.backend.dto;

import com.movelink.backend.enums.MessageType;
import lombok.Data;

@Data
public class ChatMessageRequest {

    private Long rideId;

    private Long senderId;

    private Long receiverId;

    private MessageType messageType;

    private String message;
}