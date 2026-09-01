package com.movelink.backend.service;

import com.movelink.backend.dto.RatingRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Rating;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.User;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.RatingRepository;
import com.movelink.backend.repository.RideRepository;
import com.movelink.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(
            RatingRepository ratingRepository,
            RideRepository rideRepository,
            DriverRepository driverRepository,
            UserRepository userRepository) {

        this.ratingRepository = ratingRepository;
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Rating submitRating(RatingRequest request) {

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Rating rating = Rating.builder()
                .stars(request.getStars())
                .comment(request.getComment())
                .ride(ride)
                .driver(driver)
                .customer(customer)
                .build();

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getDriverRatings(Long driverId) {
        return ratingRepository.findByDriverId(driverId);
    }

    @Override
    public Double getAverageRating(Long driverId) {

        Double average = ratingRepository.getAverageRating(driverId);

        return average != null ? average : 0.0;
    }
}