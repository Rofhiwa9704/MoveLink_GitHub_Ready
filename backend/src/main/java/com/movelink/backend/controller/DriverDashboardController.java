package com.movelink.backend.controller;

import com.movelink.backend.dto.DriverDashboardResponse;
import com.movelink.backend.service.DriverDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DriverDashboardController {

    private final DriverDashboardService dashboardService;

    public DriverDashboardController(DriverDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<DriverDashboardResponse> getDashboard(
            @PathVariable Long driverId) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(driverId)
        );
    }
}