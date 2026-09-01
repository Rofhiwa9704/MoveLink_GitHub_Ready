package com.movelink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatNotification {

    private Long rideId;

    private Long senderId;

    private Long receiverId;

    private String message;
}