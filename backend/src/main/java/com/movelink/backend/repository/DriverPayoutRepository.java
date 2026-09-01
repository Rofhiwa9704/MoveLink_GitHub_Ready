package com.movelink.backend.repository;

import com.movelink.backend.entity.DriverPayout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverPayoutRepository extends JpaRepository<DriverPayout, Long> {

    List<DriverPayout> findByDriverId(Long driverId);
}