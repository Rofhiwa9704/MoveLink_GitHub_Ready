package com.movelink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverLocationResponse {

    private Long driverId;

    private Double latitude;

    private Double longitude;

    private boolean available;
}