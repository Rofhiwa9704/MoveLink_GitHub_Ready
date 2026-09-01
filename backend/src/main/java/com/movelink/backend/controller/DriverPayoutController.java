package com.movelink.backend.controller;

import com.movelink.backend.dto.PayoutRequest;
import com.movelink.backend.entity.DriverPayout;
import com.movelink.backend.service.DriverPayoutService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payouts")
public class DriverPayoutController {

    private final DriverPayoutService payoutService;

    public DriverPayoutController(DriverPayoutService payoutService) {
        this.payoutService = payoutService;
    }

    @PostMapping
    public DriverPayout requestPayout(@RequestBody PayoutRequest request) {
        return payoutService.requestPayout(request);
    }

    @PutMapping("/{payoutId}/approve")
    public DriverPayout approvePayout(@PathVariable Long payoutId) {
        return payoutService.approvePayout(payoutId);
    }

    @GetMapping("/driver/{driverId}")
    public List<DriverPayout> getDriverPayouts(@PathVariable Long driverId) {
        return payoutService.getDriverPayouts(driverId);
    }
}