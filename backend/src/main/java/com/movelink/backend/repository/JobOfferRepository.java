package com.movelink.backend.repository;

import com.movelink.backend.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    List<JobOffer> findByDriverId(Long driverId);

    List<JobOffer> findByRideId(Long rideId);
}