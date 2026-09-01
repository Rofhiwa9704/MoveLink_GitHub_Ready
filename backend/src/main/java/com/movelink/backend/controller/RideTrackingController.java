package com.movelink.backend.controller;

import com.movelink.backend.entity.RideTracking;
import com.movelink.backend.enums.RideTrackingStatus;
import com.movelink.backend.service.RideTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride-tracking")
public class RideTrackingController {

    private final RideTrackingService rideTrackingService;

    public RideTrackingController(RideTrackingService rideTrackingService) {
        this.rideTrackingService = rideTrackingService;
    }

    @PostMapping("/{rideId}")
    public ResponseEntity<RideTracking> createTracking(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideTrackingService.createTracking(rideId)
        );
    }

    @PutMapping("/{rideId}/status")
    public ResponseEntity<RideTracking> updateStatus(
            @PathVariable Long rideId,
            @RequestParam RideTrackingStatus status) {

        return ResponseEntity.ok(
                rideTrackingService.updateStatus(rideId, status)
        );
    }

    @PutMapping("/{rideId}/location")
    public ResponseEntity<RideTracking> updateLocation(
            @PathVariable Long rideId,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {

        return ResponseEntity.ok(
                rideTrackingService.updateLocation(
                        rideId,
                        latitude,
                        longitude
                )
        );
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideTracking> getTracking(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideTrackingService.getTracking(rideId)
        );
    }
}