package com.movelink.backend.entity;

import com.movelink.backend.enums.QuoteStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String message;

    @Enumerated(EnumType.STRING)
private QuoteStatus status;


    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @PrePersist
public void prePersist() {

    createdAt = LocalDateTime.now();

    if (status == null) {
        status = QuoteStatus.PENDING;
    }
}

}