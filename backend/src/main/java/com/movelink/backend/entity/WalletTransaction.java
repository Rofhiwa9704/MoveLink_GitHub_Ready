package com.movelink.backend.entity;

import com.movelink.backend.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @PrePersist
    public void prePersist() {
        transactionDate = LocalDateTime.now();
    }
}