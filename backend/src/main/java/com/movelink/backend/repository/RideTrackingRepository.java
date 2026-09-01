package com.movelink.backend.repository;

import com.movelink.backend.entity.RideTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RideTrackingRepository extends JpaRepository<RideTracking, Long> {

    Optional<RideTracking> findByRideId(Long rideId);
}