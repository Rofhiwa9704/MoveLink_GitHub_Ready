package com.movelink.backend.repository;

import com.movelink.backend.entity.SupportTicket;
import com.movelink.backend.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    List<SupportTicket> findByUserId(Long userId);

    List<SupportTicket> findByStatus(TicketStatus status);

    long countByStatus(TicketStatus status);
}