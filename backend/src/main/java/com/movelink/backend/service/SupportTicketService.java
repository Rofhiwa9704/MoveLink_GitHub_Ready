package com.movelink.backend.service;

import com.movelink.backend.dto.SupportTicketRequest;
import com.movelink.backend.dto.SupportTicketResponse;

import java.util.List;

public interface SupportTicketService {

    SupportTicketResponse createTicket(SupportTicketRequest request);

    List<SupportTicketResponse> getAllTickets();

    List<SupportTicketResponse> getUserTickets(Long userId);

    SupportTicketResponse replyToTicket(Long ticketId, String reply);

    SupportTicketResponse closeTicket(Long ticketId);
}