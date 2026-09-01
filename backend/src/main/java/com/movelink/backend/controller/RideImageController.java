package com.movelink.backend.controller;

import com.movelink.backend.entity.RideImage;
import com.movelink.backend.service.RideImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ride-images")
public class RideImageController {

    private final RideImageService rideImageService;

    public RideImageController(RideImageService rideImageService) {
        this.rideImageService = rideImageService;
    }

    @PostMapping("/{rideId}")
    public ResponseEntity<RideImage> uploadImage(
            @PathVariable Long rideId,
            @RequestParam String imageUrl) {

        return ResponseEntity.ok(
                rideImageService.saveImage(rideId, imageUrl));
    }

    @GetMapping("/{rideId}")
    public ResponseEntity<List<RideImage>> getRideImages(
            @PathVariable Long rideId) {

        return ResponseEntity.ok(
                rideImageService.getRideImages(rideId));
    }
}