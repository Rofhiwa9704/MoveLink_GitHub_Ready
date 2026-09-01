package com.movelink.backend.repository;

import com.movelink.backend.entity.Quote;
import com.movelink.backend.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findByRideId(Long rideId);

    List<Quote> findByRide(Ride ride);
}