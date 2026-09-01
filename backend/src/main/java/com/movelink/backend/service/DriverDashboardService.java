package com.movelink.backend.service;

import com.movelink.backend.dto.DriverDashboardResponse;

public interface DriverDashboardService {

    DriverDashboardResponse getDashboard(Long driverId);
}