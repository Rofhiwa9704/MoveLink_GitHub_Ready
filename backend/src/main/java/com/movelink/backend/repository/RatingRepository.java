package com.movelink.backend.repository;

import com.movelink.backend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByDriverId(Long driverId);

    long countByDriverId(Long driverId);

    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.driver.id = :driverId")
    Double getAverageRating(@Param("driverId") Long driverId);
}