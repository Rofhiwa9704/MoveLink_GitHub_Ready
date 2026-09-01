package com.movelink.backend.service;

import com.movelink.backend.dto.DriverLocationRequest;
import com.movelink.backend.dto.DriverTrackingResponse;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.util.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
public class TrackingServiceImpl implements TrackingService {

    private final DriverRepository driverRepository;

    public TrackingServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public DriverTrackingResponse updateDriverLocation(
            Long driverId,
            DriverLocationRequest request) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setLatitude(request.getLatitude());
        driver.setLongitude(request.getLongitude());

        driverRepository.save(driver);

        return DriverTrackingResponse.builder()
                .driverId(driver.getId())
                .latitude(driver.getLatitude())
                .longitude(driver.getLongitude())
                .available(driver.isAvailable())
                .distanceFromPickup(null)
                .build();
    }

    @Override
    public DriverTrackingResponse getDriverLocation(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return DriverTrackingResponse.builder()
                .driverId(driver.getId())
                .latitude(driver.getLatitude())
                .longitude(driver.getLongitude())
                .available(driver.isAvailable())
                .distanceFromPickup(null)
                .build();
    }

    @Override
    public DriverTrackingResponse findNearestDriver(
            Double pickupLatitude,
            Double pickupLongitude) {

        Driver nearest = driverRepository.findByAvailableTrue()
                .stream()
                .filter(driver ->
                        driver.getLatitude() != null &&
                        driver.getLongitude() != null)
                .min((d1, d2) -> Double.compare(
                        DistanceCalculator.calculateDistance(
                                pickupLatitude,
                                pickupLongitude,
                                d1.getLatitude(),
                                d1.getLongitude()),
                        DistanceCalculator.calculateDistance(
                                pickupLatitude,
                                pickupLongitude,
                                d2.getLatitude(),
                                d2.getLongitude())
                ))
                .orElseThrow(() -> new RuntimeException("No available driver found"));

        double distance = DistanceCalculator.calculateDistance(
                pickupLatitude,
                pickupLongitude,
                nearest.getLatitude(),
                nearest.getLongitude());

        return DriverTrackingResponse.builder()
                .driverId(nearest.getId())
                .latitude(nearest.getLatitude())
                .longitude(nearest.getLongitude())
                .available(nearest.isAvailable())
                .distanceFromPickup(distance)
                .build();
    }
}