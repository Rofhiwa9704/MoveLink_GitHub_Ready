package com.movelink.backend.repository;

import com.movelink.backend.entity.DriverReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverReviewRepository extends JpaRepository<DriverReview, Long> {

    List<DriverReview> findByDriverId(Long driverId);
}