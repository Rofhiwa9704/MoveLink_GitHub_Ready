package com.movelink.backend.dto;

import lombok.Data;

@Data
public class WalletRequest {

    private Long userId;

    private Double amount;
}