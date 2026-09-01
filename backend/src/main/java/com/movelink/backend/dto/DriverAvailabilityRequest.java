package com.movelink.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DriverAvailabilityRequest {

    private Long driverId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}