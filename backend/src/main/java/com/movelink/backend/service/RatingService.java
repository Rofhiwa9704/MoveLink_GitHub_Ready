package com.movelink.backend.service;

import com.movelink.backend.dto.RatingRequest;
import com.movelink.backend.entity.Rating;

import java.util.List;

public interface RatingService {

    Rating submitRating(RatingRequest request);

    List<Rating> getDriverRatings(Long driverId);

    Double getAverageRating(Long driverId);
}