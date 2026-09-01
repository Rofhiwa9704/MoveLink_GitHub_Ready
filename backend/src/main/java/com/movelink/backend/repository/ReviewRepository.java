package com.movelink.backend.repository;

import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByDriver(Driver driver);

    List<Review> findByDriverId(Long driverId);

    long countByDriverId(Long driverId);
}