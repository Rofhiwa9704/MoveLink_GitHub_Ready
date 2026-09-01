package com.movelink.backend.service;

import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.JobOffer;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.enums.JobOfferStatus;
import com.movelink.backend.enums.RideStatus;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.JobOfferRepository;
import com.movelink.backend.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public JobOfferServiceImpl(
            JobOfferRepository jobOfferRepository,
            RideRepository rideRepository,
            DriverRepository driverRepository) {

        this.jobOfferRepository = jobOfferRepository;
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public void createJobOffers(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        List<Driver> drivers = driverRepository.findByAvailableTrue();

        for (Driver driver : drivers) {

            if (!Boolean.TRUE.equals(driver.getVerified())) {
                continue;
            }

            JobOffer offer = JobOffer.builder()
                    .ride(ride)
                    .driver(driver)
                    .status(JobOfferStatus.PENDING)
                    .build();

            jobOfferRepository.save(offer);
        }
    }

    @Override
    public List<JobOffer> getDriverOffers(Long driverId) {
        return jobOfferRepository.findByDriverId(driverId);
    }

    @Override
    public JobOffer acceptOffer(Long offerId) {

        JobOffer offer = jobOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        Ride ride = offer.getRide();

        ride.setDriver(offer.getDriver());
        ride.setStatus(RideStatus.ACCEPTED);

        rideRepository.save(ride);

        List<JobOffer> offers = jobOfferRepository.findByRideId(ride.getId());

        for (JobOffer o : offers) {

            if (o.getId().equals(offer.getId())) {
                o.setStatus(JobOfferStatus.ACCEPTED);
            } else {
                o.setStatus(JobOfferStatus.EXPIRED);
            }

            jobOfferRepository.save(o);
        }

        return offer;
    }

    @Override
    public JobOffer declineOffer(Long offerId) {

        JobOffer offer = jobOfferRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        offer.setStatus(JobOfferStatus.DECLINED);

        return jobOfferRepository.save(offer);
    }
}