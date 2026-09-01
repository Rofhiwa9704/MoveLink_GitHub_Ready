package com.movelink.backend.dto;

import lombok.Data;

@Data
public class PaymentRequest {

    private Long rideId;

    private Long customerId;

    private Double amount;
}