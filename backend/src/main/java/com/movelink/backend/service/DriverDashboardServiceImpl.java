package com.movelink.backend.service;

import com.movelink.backend.dto.DriverDashboardResponse;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.enums.RideStatus;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.RatingRepository;
import com.movelink.backend.repository.ReviewRepository;
import com.movelink.backend.repository.RideRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverDashboardServiceImpl implements DriverDashboardService {

    private final DriverRepository driverRepository;
    private final RideRepository rideRepository;
    private final RatingRepository ratingRepository;
    private final ReviewRepository reviewRepository;

    public DriverDashboardServiceImpl(
            DriverRepository driverRepository,
            RideRepository rideRepository,
            RatingRepository ratingRepository,
            ReviewRepository reviewRepository) {

        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
        this.ratingRepository = ratingRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public DriverDashboardResponse getDashboard(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        long completedRides =
                rideRepository.countByDriverIdAndStatus(driverId, RideStatus.COMPLETED);

        long cancelledRides =
                rideRepository.countByDriverIdAndStatus(driverId, RideStatus.CANCELLED);

        Double averageRating =
                ratingRepository.getAverageRating(driverId);

        if (averageRating == null) {
            averageRating = 0.0;
        }

        long totalReviews =
                reviewRepository.countByDriverId(driverId);

        return DriverDashboardResponse.builder()
                .driverId(driverId)
                .totalEarnings(driver.getEarnings())
                .completedRides(completedRides)
                .cancelledRides(cancelledRides)
                .averageRating(averageRating)
                .totalReviews(totalReviews)
                .build();
    }
}