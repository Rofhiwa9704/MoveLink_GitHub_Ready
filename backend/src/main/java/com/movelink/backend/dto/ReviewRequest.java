package com.movelink.backend.dto;

import lombok.Data;

@Data
public class ReviewRequest {

    private Long driverId;

    private Long customerId;

    private Long rideId;

    private Integer rating;

    private String comment;
}