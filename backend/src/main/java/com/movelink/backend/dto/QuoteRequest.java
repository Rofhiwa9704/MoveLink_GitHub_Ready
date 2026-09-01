package com.movelink.backend.dto;

import lombok.Data;

@Data
public class QuoteRequest {

    private Long rideId;

    private Long driverId;

    private Double amount;

    private String message;
}