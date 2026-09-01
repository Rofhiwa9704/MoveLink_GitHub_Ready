package com.movelink.backend.service;

import com.movelink.backend.dto.QuoteRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Quote;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.enums.QuoteStatus;
import com.movelink.backend.enums.RideStatus;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.QuoteRepository;
import com.movelink.backend.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;

    public QuoteServiceImpl(
            QuoteRepository quoteRepository,
            RideRepository rideRepository,
            DriverRepository driverRepository) {

        this.quoteRepository = quoteRepository;
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Quote submitQuote(QuoteRequest request) {

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        Quote quote = Quote.builder()
                .amount(request.getAmount())
                .message(request.getMessage())
                .ride(ride)
                .driver(driver)
                .status(QuoteStatus.PENDING)
                .build();

        return quoteRepository.save(quote);
    }

    @Override
    public List<Quote> getQuotes(Long rideId) {

        return quoteRepository.findByRideId(rideId);
    }

    @Override
    public Quote acceptQuote(Long quoteId) {

        Quote acceptedQuote = quoteRepository.findById(quoteId)
                .orElseThrow(() -> new RuntimeException("Quote not found"));

        Ride ride = acceptedQuote.getRide();

        List<Quote> quotes = quoteRepository.findByRide(ride);

        for (Quote quote : quotes) {

            if (quote.getId().equals(acceptedQuote.getId())) {
                quote.setStatus(QuoteStatus.ACCEPTED);
            } else {
                quote.setStatus(QuoteStatus.REJECTED);
            }

            quoteRepository.save(quote);
        }

        Driver driver = acceptedQuote.getDriver();

        driver.setAvailable(false);
        driverRepository.save(driver);

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);

        rideRepository.save(ride);

        return acceptedQuote;
    }
}