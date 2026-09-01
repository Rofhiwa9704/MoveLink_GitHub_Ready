package com.movelink.backend.service;

import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.RideImage;
import com.movelink.backend.repository.RideImageRepository;
import com.movelink.backend.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideImageServiceImpl implements RideImageService {

    private final RideRepository rideRepository;
    private final RideImageRepository rideImageRepository;

    public RideImageServiceImpl(
            RideRepository rideRepository,
            RideImageRepository rideImageRepository) {

        this.rideRepository = rideRepository;
        this.rideImageRepository = rideImageRepository;
    }

    @Override
    public RideImage saveImage(Long rideId, String imageUrl) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        RideImage image = RideImage.builder()
                .ride(ride)
                .imageUrl(imageUrl)
                .build();

        return rideImageRepository.save(image);
    }

    @Override
    public List<RideImage> getRideImages(Long rideId) {

        return rideImageRepository.findByRideId(rideId);
    }
}