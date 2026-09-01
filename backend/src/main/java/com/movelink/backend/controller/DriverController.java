package com.movelink.backend.controller;

import com.movelink.backend.dto.DriverLocationRequest;
import com.movelink.backend.dto.DriverLocationResponse;
import com.movelink.backend.dto.DriverRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.service.DriverServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverServiceImpl driverService;
    private final DriverRepository driverRepository;

    public DriverController(
            DriverServiceImpl driverService,
            DriverRepository driverRepository) {

        this.driverService = driverService;
        this.driverRepository = driverRepository;
    }

    // ==========================
    // DRIVER REGISTRATION
    // ==========================

    @PostMapping("/register")
    public ResponseEntity<String> registerDriver(
            @RequestBody DriverRequest request) {

        return ResponseEntity.ok(
                driverService.registerDriver(request)
        );
    }

    // ==========================
    // APPROVE DRIVER
    // ==========================

    @PutMapping("/{driverId}/approve")
    public ResponseEntity<Driver> approveDriver(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                driverService.approveDriver(driverId)
        );
    }

    // ==========================
    // DRIVER LOCATION
    // ==========================

    @PutMapping("/{driverId}/location")
    public ResponseEntity<Driver> updateLocation(
            @PathVariable Long driverId,
            @RequestBody DriverLocationRequest request) {

        return ResponseEntity.ok(
                driverService.updateLocation(driverId, request)
        );
    }

    @GetMapping("/{driverId}/location")
    public ResponseEntity<DriverLocationResponse> getDriverLocation(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                driverService.getDriverLocation(driverId)
        );
    }

    // ==========================
    // DRIVER EARNINGS
    // ==========================

    @GetMapping("/{driverId}/earnings")
    public ResponseEntity<Double> getDriverEarnings(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                driverService.getDriverEarnings(driverId)
        );
    }

    // ==========================
    // DRIVER GO ONLINE
    // ==========================

    @PutMapping("/{driverId}/online")
    public ResponseEntity<Driver> goOnline(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                driverService.goOnline(driverId)
        );
    }

    // ==========================
    // DRIVER GO OFFLINE
    // ==========================

    @PutMapping("/{driverId}/offline")
    public ResponseEntity<Driver> goOffline(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                driverService.goOffline(driverId)
        );
    }

    // ==========================
    // VERIFY DRIVER
    // ==========================

    @PutMapping("/{driverId}/verify")
    public ResponseEntity<?> verifyDriver(
            @PathVariable Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found")
                );

        driver.setVerified(true);

        driverRepository.save(driver);

        return ResponseEntity.ok(driver);
    }

    // ==========================
    // GET AVAILABLE DRIVERS
    // ==========================

    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {

        return ResponseEntity.ok(
                driverService.getAvailableDrivers()
        );
    }

    // ==========================
    // GET ALL DRIVERS
    // ==========================

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {

        return ResponseEntity.ok(
                driverService.getAllDrivers()
        );
    }
}
