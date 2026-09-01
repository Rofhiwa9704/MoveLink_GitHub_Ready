package com.movelink.backend.service;

import com.movelink.backend.dto.DriverAvailabilityRequest;
import com.movelink.backend.entity.DriverAvailability;

import java.util.List;

public interface DriverAvailabilityService {

    DriverAvailability addAvailability(DriverAvailabilityRequest request);

    List<DriverAvailability> getDriverAvailability(Long driverId);
    
}