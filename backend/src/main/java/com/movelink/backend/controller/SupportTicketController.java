package com.movelink.backend.controller;

import com.movelink.backend.dto.SupportTicketRequest;
import com.movelink.backend.dto.SupportTicketResponse;
import com.movelink.backend.service.SupportTicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportTicketController {

    private final SupportTicketService supportTicketService;

    public SupportTicketController(SupportTicketService supportTicketService) {
        this.supportTicketService = supportTicketService;
    }

    @PostMapping
    public ResponseEntity<SupportTicketResponse> createTicket(
            @RequestBody SupportTicketRequest request) {

        return ResponseEntity.ok(
                supportTicketService.createTicket(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<SupportTicketResponse>> getAllTickets() {

        return ResponseEntity.ok(
                supportTicketService.getAllTickets()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SupportTicketResponse>> getUserTickets(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                supportTicketService.getUserTickets(userId)
        );
    }

    @PutMapping("/{ticketId}/reply")
    public ResponseEntity<SupportTicketResponse> replyToTicket(
            @PathVariable Long ticketId,
            @RequestParam String reply) {

        return ResponseEntity.ok(
                supportTicketService.replyToTicket(ticketId, reply)
        );
    }

    @PutMapping("/{ticketId}/close")
    public ResponseEntity<SupportTicketResponse> closeTicket(
            @PathVariable Long ticketId) {

        return ResponseEntity.ok(
                supportTicketService.closeTicket(ticketId)
        );
    }
}