package com.movelink.backend.controller;

import com.movelink.backend.dto.RatingRequest;
import com.movelink.backend.entity.Rating;
import jakarta.validation.Valid;
import com.movelink.backend.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> submitRating(@Valid @RequestBody RatingRequest request) {

        return ResponseEntity.ok(
                ratingService.submitRating(request)
        );
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Rating>> getDriverRatings(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                ratingService.getDriverRatings(driverId)
        );
    }

    @GetMapping("/driver/{driverId}/average")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                ratingService.getAverageRating(driverId)
        );
    }
}