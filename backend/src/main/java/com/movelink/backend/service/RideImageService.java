package com.movelink.backend.service;

import com.movelink.backend.entity.RideImage;

import java.util.List;

public interface RideImageService {

    RideImage saveImage(Long rideId, String imageUrl);

    List<RideImage> getRideImages(Long rideId);
}