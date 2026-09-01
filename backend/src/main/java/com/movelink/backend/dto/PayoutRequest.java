package com.movelink.backend.dto;

import lombok.Data;

@Data
public class PayoutRequest {

    private Long driverId;

    private Double amount;
}