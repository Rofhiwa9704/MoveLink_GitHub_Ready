package com.movelink.backend.controller;

import com.movelink.backend.dto.ReviewRequest;
import com.movelink.backend.entity.DriverReview;
import com.movelink.backend.service.DriverReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class DriverReviewController {

    private final DriverReviewService driverReviewService;

    public DriverReviewController(DriverReviewService driverReviewService) {
        this.driverReviewService = driverReviewService;
    }

    @PostMapping
    public DriverReview addReview(@RequestBody ReviewRequest request) {
        return driverReviewService.addReview(request);
    }

    @GetMapping("/driver/{driverId}")
    public List<DriverReview> getDriverReviews(@PathVariable Long driverId) {
        return driverReviewService.getDriverReviews(driverId);
    }

    @GetMapping("/driver/{driverId}/average")
    public Double getAverageRating(@PathVariable Long driverId) {
        return driverReviewService.getAverageRating(driverId);
    }
}