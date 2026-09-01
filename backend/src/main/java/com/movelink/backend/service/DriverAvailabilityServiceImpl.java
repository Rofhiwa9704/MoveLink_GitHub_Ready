package com.movelink.backend.service;

import com.movelink.backend.dto.DriverAvailabilityRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.DriverAvailability;
import com.movelink.backend.repository.DriverAvailabilityRepository;
import com.movelink.backend.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverAvailabilityServiceImpl implements DriverAvailabilityService {

    private final DriverAvailabilityRepository availabilityRepository;
    private final DriverRepository driverRepository;

    public DriverAvailabilityServiceImpl(
            DriverAvailabilityRepository availabilityRepository,
            DriverRepository driverRepository) {

        this.availabilityRepository = availabilityRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public DriverAvailability addAvailability(DriverAvailabilityRequest request) {

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        boolean conflict = availabilityRepository
                .existsByDriverIdAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        request.getDriverId(),
                        request.getEndTime(),
                        request.getStartTime()
                );

        if (conflict) {
            throw new RuntimeException("This availability overlaps an existing schedule.");
        }

        DriverAvailability availability = DriverAvailability.builder()
                .driver(driver)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .booked(false)
                .build();

        return availabilityRepository.save(availability);
    }

    @Override
    public List<DriverAvailability> getDriverAvailability(Long driverId) {
        return availabilityRepository.findByDriverId(driverId);
    }
}