package com.movelink.backend.controller;

import com.movelink.backend.dto.DriverLocationRequest;
import com.movelink.backend.dto.DriverTrackingResponse;
import com.movelink.backend.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PutMapping("/location/{driverId}")
    public ResponseEntity<DriverTrackingResponse> updateLocation(
            @PathVariable Long driverId,
            @RequestBody DriverLocationRequest request) {

        return ResponseEntity.ok(
                trackingService.updateDriverLocation(driverId, request)
        );
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<DriverTrackingResponse> getDriverLocation(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                trackingService.getDriverLocation(driverId)
        );
    }

    @GetMapping("/nearest")
    public ResponseEntity<DriverTrackingResponse> getNearestDriver(
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        return ResponseEntity.ok(
                trackingService.findNearestDriver(latitude, longitude)
        );
    }
}