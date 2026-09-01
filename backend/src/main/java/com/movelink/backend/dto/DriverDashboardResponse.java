package com.movelink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDashboardResponse {

    private Long driverId;

    private Double totalEarnings;

    private Long completedRides;

    private Long cancelledRides;

    private Double averageRating;

    private Long totalReviews;
}