package com.movelink.backend.entity;

import com.movelink.backend.enums.PayoutStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "driver_payouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverPayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PayoutStatus status;

    private LocalDateTime requestedAt;

    private LocalDateTime paidAt;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @PrePersist
    public void prePersist() {
        requestedAt = LocalDateTime.now();

        if (status == null) {
            status = PayoutStatus.PENDING;
        }
    }
}