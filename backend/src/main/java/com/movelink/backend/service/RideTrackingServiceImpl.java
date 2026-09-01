package com.movelink.backend.service;

import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.RideTracking;
import com.movelink.backend.enums.RideTrackingStatus;
import com.movelink.backend.repository.RideRepository;
import com.movelink.backend.repository.RideTrackingRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class RideTrackingServiceImpl implements RideTrackingService {

    private final RideRepository rideRepository;
    private final RideTrackingRepository rideTrackingRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public RideTrackingServiceImpl(
        RideRepository rideRepository,
        RideTrackingRepository rideTrackingRepository,
        SimpMessagingTemplate messagingTemplate) {

    this.rideRepository = rideRepository;
    this.rideTrackingRepository = rideTrackingRepository;
    this.messagingTemplate = messagingTemplate;
}
    

    @Override
    public RideTracking createTracking(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        RideTracking tracking = RideTracking.builder()
                .ride(ride)
                .status(RideTrackingStatus.RIDE_REQUESTED)
                .build();

        return rideTrackingRepository.save(tracking);
    }

    @Override
    public RideTracking updateStatus(Long rideId,
                                     RideTrackingStatus status) {

        RideTracking tracking = rideTrackingRepository.findByRideId(rideId)
                .orElseThrow(() -> new RuntimeException("Tracking not found"));

        tracking.setStatus(status);

        return rideTrackingRepository.save(tracking);
    }

    @Override
public RideTracking updateLocation(Long rideId,
                                   Double latitude,
                                   Double longitude) {

    RideTracking tracking = rideTrackingRepository.findByRideId(rideId)
            .orElseThrow(() -> new RuntimeException("Tracking not found"));

    tracking.setLatitude(latitude);
    tracking.setLongitude(longitude);

    RideTracking updatedTracking = rideTrackingRepository.save(tracking);

    // Send the updated location to all subscribed clients
    messagingTemplate.convertAndSend(
            "/topic/rides/" + rideId + "/location",
            updatedTracking
    );

    return updatedTracking;
}

    @Override
    public RideTracking getTracking(Long rideId) {

        return rideTrackingRepository.findByRideId(rideId)
                .orElseThrow(() -> new RuntimeException("Tracking not found"));
    }
}