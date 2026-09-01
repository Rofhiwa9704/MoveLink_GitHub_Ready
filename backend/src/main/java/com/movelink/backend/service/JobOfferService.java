package com.movelink.backend.service;

import com.movelink.backend.entity.JobOffer;

import java.util.List;

public interface JobOfferService {

    void createJobOffers(Long rideId);

    List<JobOffer> getDriverOffers(Long driverId);

    JobOffer acceptOffer(Long offerId);

    JobOffer declineOffer(Long offerId);
}