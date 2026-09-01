package com.movelink.backend.service;

import com.movelink.backend.dto.ReviewRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.DriverReview;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.User;
import com.movelink.backend.enums.RideStatus;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.DriverReviewRepository;
import com.movelink.backend.repository.RideRepository;
import com.movelink.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverReviewServiceImpl implements DriverReviewService {

    private final DriverReviewRepository reviewRepository;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    public DriverReviewServiceImpl(
            DriverReviewRepository reviewRepository,
            DriverRepository driverRepository,
            UserRepository userRepository,
            RideRepository rideRepository) {

        this.reviewRepository = reviewRepository;
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
    }

    @Override
    public DriverReview addReview(ReviewRequest request) {

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.COMPLETED) {
            throw new RuntimeException("Only completed rides can be reviewed.");
        }

        DriverReview review = DriverReview.builder()
                .driver(driver)
                .customer(customer)
                .ride(ride)
                .rating(request.getRating())
                .comment(request.getComment())
                .build();

        return reviewRepository.save(review);
    }

    @Override
    public List<DriverReview> getDriverReviews(Long driverId) {
        return reviewRepository.findByDriverId(driverId);
    }

    @Override
    public Double getAverageRating(Long driverId) {

        List<DriverReview> reviews = reviewRepository.findByDriverId(driverId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        double total = 0;

        for (DriverReview review : reviews) {
            total += review.getRating();
        }

        return total / reviews.size();
    }
}