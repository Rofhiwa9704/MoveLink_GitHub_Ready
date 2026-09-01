package com.movelink.backend.dto;

import com.movelink.backend.enums.TicketPriority;
import lombok.Data;

@Data
public class SupportTicketRequest {

    private Long userId;

    private String subject;

    private String description;

    private TicketPriority priority;
}