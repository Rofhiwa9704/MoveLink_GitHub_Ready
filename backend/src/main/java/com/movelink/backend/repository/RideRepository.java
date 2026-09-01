package com.movelink.backend.repository;

import com.movelink.backend.entity.Ride;
import com.movelink.backend.enums.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByCustomerId(Long customerId);

    List<Ride> findByDriverId(Long driverId);

    List<Ride> findByStatus(RideStatus status);

    // NEW
    List<Ride> findByStatusAndDriverIsNull(RideStatus status);

    long countByStatus(RideStatus status);

    long countByDriverIdAndStatus(Long driverId, RideStatus status);

    long countByDriverId(Long driverId);
}
