package com.movelink.backend.repository;

import com.movelink.backend.entity.DriverAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DriverAvailabilityRepository extends JpaRepository<DriverAvailability, Long> {

    List<DriverAvailability> findByDriverId(Long driverId);

    boolean existsByDriverIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Long driverId,
            LocalDateTime endTime,
            LocalDateTime startTime

    );

    List<DriverAvailability> findByBookedFalseAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
        java.time.LocalDateTime bookingTime,
        java.time.LocalDateTime bookingTime2
);
}