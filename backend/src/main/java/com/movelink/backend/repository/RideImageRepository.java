package com.movelink.backend.repository;

import com.movelink.backend.entity.RideImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideImageRepository extends JpaRepository<RideImage, Long> {

    List<RideImage> findByRideId(Long rideId);
}