package com.movelink.backend.controller;

import com.movelink.backend.entity.JobOffer;
import com.movelink.backend.service.JobOfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-offers")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    public JobOfferController(JobOfferService jobOfferService) {
        this.jobOfferService = jobOfferService;
    }

    @PostMapping("/create/{rideId}")
    public String createJobOffers(@PathVariable Long rideId) {

        jobOfferService.createJobOffers(rideId);

        return "Job offers created successfully";
    }

    @GetMapping("/driver/{driverId}")
    public List<JobOffer> getDriverOffers(@PathVariable Long driverId) {

        return jobOfferService.getDriverOffers(driverId);
    }

    @PutMapping("/accept/{offerId}")
    public JobOffer acceptOffer(@PathVariable Long offerId) {

        return jobOfferService.acceptOffer(offerId);
    }

    @PutMapping("/decline/{offerId}")
    public JobOffer declineOffer(@PathVariable Long offerId) {

        return jobOfferService.declineOffer(offerId);
    }
}