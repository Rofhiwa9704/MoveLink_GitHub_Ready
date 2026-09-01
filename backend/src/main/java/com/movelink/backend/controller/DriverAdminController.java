package com.movelink.backend.controller;

import com.movelink.backend.entity.Driver;
import com.movelink.backend.repository.DriverRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver-admin")
public class DriverAdminController {

    private final DriverRepository driverRepository;

    public DriverAdminController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @PutMapping("/{driverId}/availability/{available}")
    public ResponseEntity<String> setAvailability(
            @PathVariable Long driverId,
            @PathVariable boolean available) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setAvailable(available);
        driverRepository.save(driver);

        return ResponseEntity.ok(
                "Driver " + driverId + " availability set to " + available
        );
    }
}