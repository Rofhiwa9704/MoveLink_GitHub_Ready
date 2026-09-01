package com.movelink.backend.service;

import com.movelink.backend.dto.ReviewRequest;
import com.movelink.backend.entity.Review;

import java.util.List;

public interface ReviewService {

    Review addReview(ReviewRequest request);

    List<Review> getDriverReviews(Long driverId);
}