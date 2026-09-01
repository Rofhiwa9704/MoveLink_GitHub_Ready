package com.movelink.backend.dto;

import com.movelink.backend.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideHistoryResponse {

    private Long rideId;

    private String pickupLocation;

    private String destination;

    private RideStatus status;

    private Double quotedPrice;

    private LocalDateTime createdAt;
}