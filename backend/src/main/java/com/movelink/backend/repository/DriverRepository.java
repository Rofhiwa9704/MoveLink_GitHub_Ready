package com.movelink.backend.repository;

import com.movelink.backend.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    // Existing methods
    List<Driver> findByAvailableTrue();

    List<Driver> findByVerifiedTrue();

    Optional<Driver> findByUserId(Long userId);

    // New methods for Admin Dashboard
    long countByVerifiedTrue();

    long countByAvailableTrue();
}