package com.movelink.backend.service;

import com.movelink.backend.entity.RideTracking;
import com.movelink.backend.enums.RideTrackingStatus;

public interface RideTrackingService {

    RideTracking createTracking(Long rideId);

    RideTracking updateStatus(Long rideId, RideTrackingStatus status);

    RideTracking updateLocation(Long rideId,
                                Double latitude,
                                Double longitude);

    RideTracking getTracking(Long rideId);
}