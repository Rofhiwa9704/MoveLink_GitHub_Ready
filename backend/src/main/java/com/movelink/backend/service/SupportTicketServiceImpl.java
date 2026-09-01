package com.movelink.backend.service;

import com.movelink.backend.dto.SupportTicketRequest;
import com.movelink.backend.dto.SupportTicketResponse;
import com.movelink.backend.entity.SupportTicket;
import com.movelink.backend.entity.User;
import com.movelink.backend.enums.TicketStatus;
import com.movelink.backend.repository.SupportTicketRepository;
import com.movelink.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final UserRepository userRepository;

    public SupportTicketServiceImpl(
            SupportTicketRepository supportTicketRepository,
            UserRepository userRepository) {

        this.supportTicketRepository = supportTicketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SupportTicketResponse createTicket(SupportTicketRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SupportTicket ticket = SupportTicket.builder()
                .subject(request.getSubject())
                .description(request.getDescription())
                .priority(request.getPriority())
                .user(user)
                .build();

        return mapToResponse(supportTicketRepository.save(ticket));
    }

    @Override
    public List<SupportTicketResponse> getAllTickets() {
        return supportTicketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupportTicketResponse> getUserTickets(Long userId) {
        return supportTicketRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SupportTicketResponse replyToTicket(Long ticketId, String reply) {

        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setAdminReply(reply);
        ticket.setStatus(TicketStatus.IN_PROGRESS);

        return mapToResponse(supportTicketRepository.save(ticket));
    }

    @Override
    public SupportTicketResponse closeTicket(Long ticketId) {

        SupportTicket ticket = supportTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.CLOSED);

        return mapToResponse(supportTicketRepository.save(ticket));
    }

    private SupportTicketResponse mapToResponse(SupportTicket ticket) {

        return SupportTicketResponse.builder()
                .id(ticket.getId())
                .subject(ticket.getSubject())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .priority(ticket.getPriority())
                .adminReply(ticket.getAdminReply())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .userId(ticket.getUser().getId())
                .build();
    }
}