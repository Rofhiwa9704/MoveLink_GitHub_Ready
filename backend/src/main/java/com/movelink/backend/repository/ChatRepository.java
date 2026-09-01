package com.movelink.backend.repository;

import com.movelink.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByRideIdOrderBySentAtAsc(Long rideId);
}