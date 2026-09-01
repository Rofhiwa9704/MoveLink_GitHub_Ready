package com.movelink.backend.controller;

import com.movelink.backend.dto.RideRequest;
import com.movelink.backend.dto.RideResponse;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@CrossOrigin(origins = "http://localhost:5173")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    private RideResponse toResponse(Ride ride) {
        return new RideResponse(ride);
    }

    @PostMapping("/request")
    public ResponseEntity<RideResponse> requestRide(
            @Valid @RequestBody RideRequest request) {

        Ride ride = rideService.requestRide(request);

        return ResponseEntity.ok(toResponse(ride));
    }

    @PutMapping("/{rideId}/assign/{driverId}")
    public ResponseEntity<RideResponse> assignDriver(
            @PathVariable Long rideId,
            @PathVariable Long driverId) {

        Ride ride = rideService.assignDriver(rideId, driverId);

        return ResponseEntity.ok(toResponse(ride));
    }

    @PutMapping("/{rideId}/accept/{driverId}")
    public ResponseEntity<RideResponse> acceptRide(
            @PathVariable Long rideId,
            @PathVariable Long driverId) {

        Ride ride = rideService.acceptRide(rideId, driverId);

        return ResponseEntity.ok(toResponse(ride));
    }

    @PutMapping("/{rideId}/decline/{driverId}")
    public ResponseEntity<RideResponse> declineRide(
            @PathVariable Long rideId,
            @PathVariable Long driverId) {

        Ride ride = rideService.declineRide(rideId, driverId);

        return ResponseEntity.ok(toResponse(ride));
    }

    @PutMapping("/{rideId}/start")
    public ResponseEntity<RideResponse> startRide(
            @PathVariable Long rideId) {

        Ride ride = rideService.startRide(rideId);

        return ResponseEntity.ok(toResponse(ride));
    }

    @PutMapping("/{rideId}/complete")
    public ResponseEntity<RideResponse> completeRide(
            @PathVariable Long rideId) {

        Ride ride = rideService.completeRide(rideId);

        return ResponseEntity.ok(toResponse(ride));
    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<RideResponse> cancelRide(
            @PathVariable Long rideId) {

        Ride ride = rideService.cancelRide(rideId);

        return ResponseEntity.ok(toResponse(ride));
    }

    @GetMapping
    public ResponseEntity<List<RideResponse>> getAllRides() {

        List<RideResponse> rides = rideService.getAllRides()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(rides);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RideResponse>> getPendingRides() {

        List<RideResponse> rides = rideService.getPendingRides()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(rides);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<RideResponse>> getDriverRides(
            @PathVariable Long driverId) {

        List<RideResponse> rides = rideService
                .getDriverRides(driverId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(rides);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RideResponse>> getCustomerRides(
            @PathVariable Long customerId) {

        List<RideResponse> rides = rideService
                .getCustomerRides(customerId)
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(rides);
    }
}