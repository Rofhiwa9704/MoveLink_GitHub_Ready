package com.movelink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponse {

    private long totalUsers;

    private long totalDrivers;

    private long verifiedDrivers;

    private long availableDrivers;

    private long totalRides;

    private long pendingRides;

    private long acceptedRides;

    private long inProgressRides;

    private long completedRides;

    private long cancelledRides;

    private long totalPayments;

    private double totalRevenue;

    private long totalRatings;

    private long totalReviews;

    private long openSupportTickets;
}