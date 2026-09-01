package com.movelink.backend.service;

import com.movelink.backend.dto.DriverLocationRequest;
import com.movelink.backend.dto.DriverTrackingResponse;

public interface TrackingService {

    DriverTrackingResponse updateDriverLocation(
            Long driverId,
            DriverLocationRequest request);

    DriverTrackingResponse getDriverLocation(Long driverId);

    DriverTrackingResponse findNearestDriver(
            Double pickupLatitude,
            Double pickupLongitude);
}