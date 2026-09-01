package com.movelink.backend.entity;

import com.movelink.backend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentReference;

    private LocalDateTime paidAt;

    @OneToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = PaymentStatus.PENDING;
        }
    }
}