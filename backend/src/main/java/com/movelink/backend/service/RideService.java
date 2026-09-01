package com.movelink.backend.service;

import com.movelink.backend.dto.RideRequest;
import com.movelink.backend.entity.Ride;

import java.util.List;

public interface RideService {

    Ride requestRide(RideRequest request);

    Ride assignDriver(Long rideId, Long driverId);

    Ride acceptRide(Long rideId, Long driverId);

    Ride declineRide(Long rideId, Long driverId);

    Ride startRide(Long rideId);

    Ride completeRide(Long rideId);

    Ride cancelRide(Long rideId);

    List<Ride> getAllRides();

    List<Ride> getDriverRides(Long driverId);

    List<Ride> getCustomerRides(Long customerId);

    // NEW
    List<Ride> getPendingRides();
}