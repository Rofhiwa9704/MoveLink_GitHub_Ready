package com.movelink.backend.entity;

import com.movelink.backend.enums.MessageType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rideId;

    private Long senderId;

    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime sentAt;

    @PrePersist
    public void onCreate() {
        sentAt = LocalDateTime.now();
    }
}