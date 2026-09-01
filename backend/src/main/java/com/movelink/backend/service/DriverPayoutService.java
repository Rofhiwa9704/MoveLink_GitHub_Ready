package com.movelink.backend.service;

import com.movelink.backend.dto.PayoutRequest;
import com.movelink.backend.entity.DriverPayout;

import java.util.List;

public interface DriverPayoutService {

    DriverPayout requestPayout(PayoutRequest request);

    DriverPayout approvePayout(Long payoutId);

    List<DriverPayout> getDriverPayouts(Long driverId);
}