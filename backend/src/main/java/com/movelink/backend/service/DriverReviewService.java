package com.movelink.backend.service;

import com.movelink.backend.dto.ReviewRequest;
import com.movelink.backend.entity.DriverReview;

import java.util.List;

public interface DriverReviewService {

    DriverReview addReview(ReviewRequest request);

    List<DriverReview> getDriverReviews(Long driverId);

    Double getAverageRating(Long driverId);
}