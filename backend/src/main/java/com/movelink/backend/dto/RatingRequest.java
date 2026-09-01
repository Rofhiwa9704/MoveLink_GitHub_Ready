package com.movelink.backend.dto;

import lombok.Data;

@Data
public class RatingRequest {

    private Long rideId;

    private Long driverId;

    private Long customerId;

    private Integer stars;

    private String comment;
}