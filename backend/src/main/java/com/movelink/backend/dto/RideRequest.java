package com.movelink.backend.dto;

import com.movelink.backend.enums.VehicleType;
import lombok.Data;

@Data
public class RideRequest {

    private Long customerId;

    private String pickupLocation;

    private String destination;

    private Double pickupLatitude;

    private Double pickupLongitude;

    private String loadDescription;

    private Double estimatedWeight;

    private int helpersRequired;

    // Vehicle required by customer
    private VehicleType requiredVehicleType;

    private java.time.LocalDateTime scheduledDateTime;

private Boolean scheduledRide;

}