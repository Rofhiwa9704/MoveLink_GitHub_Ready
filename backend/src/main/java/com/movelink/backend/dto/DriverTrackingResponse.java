package com.movelink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverTrackingResponse {

    private Long driverId;

    private Double latitude;

    private Double longitude;

    private Boolean available;

    private Double distanceFromPickup;
}