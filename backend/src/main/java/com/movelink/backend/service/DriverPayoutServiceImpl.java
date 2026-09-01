package com.movelink.backend.service;

import com.movelink.backend.dto.PayoutRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.DriverPayout;
import com.movelink.backend.enums.PayoutStatus;
import com.movelink.backend.repository.DriverPayoutRepository;
import com.movelink.backend.repository.DriverRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverPayoutServiceImpl implements DriverPayoutService {

    private final DriverPayoutRepository payoutRepository;
    private final DriverRepository driverRepository;

    public DriverPayoutServiceImpl(
            DriverPayoutRepository payoutRepository,
            DriverRepository driverRepository) {

        this.payoutRepository = payoutRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public DriverPayout requestPayout(PayoutRequest request) {

        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (driver.getEarnings() < request.getAmount()) {
            throw new RuntimeException("Insufficient earnings.");
        }

        driver.setEarnings(driver.getEarnings() - request.getAmount());
        driverRepository.save(driver);

        DriverPayout payout = DriverPayout.builder()
                .driver(driver)
                .amount(request.getAmount())
                .status(PayoutStatus.PENDING)
                .build();

        return payoutRepository.save(payout);
    }

    @Override
    public DriverPayout approvePayout(Long payoutId) {

        DriverPayout payout = payoutRepository.findById(payoutId)
                .orElseThrow(() -> new RuntimeException("Payout not found"));

        payout.setStatus(PayoutStatus.PAID);
        payout.setPaidAt(LocalDateTime.now());

        return payoutRepository.save(payout);
    }

    @Override
    public List<DriverPayout> getDriverPayouts(Long driverId) {
        return payoutRepository.findByDriverId(driverId);
    }
}