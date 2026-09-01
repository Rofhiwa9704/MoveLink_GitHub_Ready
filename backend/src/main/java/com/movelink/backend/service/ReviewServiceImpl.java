package com.movelink.backend.service;

import com.movelink.backend.dto.ReviewRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Review;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.User;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.ReviewRepository;
import com.movelink.backend.repository.RideRepository;
import com.movelink.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            DriverRepository driverRepository,
            RideRepository rideRepository) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public Review addReview(ReviewRequest request) {

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        Review review = Review.builder()
                .customer(customer)
                .driver(driver)
                .ride(ride)
                .comment(request.getComment())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getDriverReviews(Long driverId) {

        return reviewRepository.findByDriverId(driverId);
    }
}