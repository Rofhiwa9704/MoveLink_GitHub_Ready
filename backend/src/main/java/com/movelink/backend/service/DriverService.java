package com.movelink.backend.service;

import com.movelink.backend.dto.DriverLocationRequest;
import com.movelink.backend.dto.DriverLocationResponse;
import com.movelink.backend.dto.DriverRequest;
import com.movelink.backend.entity.Driver;

import java.util.List;

public interface DriverService {

    String registerDriver(DriverRequest request);

    Driver approveDriver(Long driverId);

    Double getDriverEarnings(Long driverId);

    Driver updateLocation(Long driverId, DriverLocationRequest request);

    DriverLocationResponse getDriverLocation(Long driverId);

    // ==========================
    // NEW METHODS
    // ==========================

    Driver goOnline(Long driverId);

    Driver goOffline(Long driverId);

    List<Driver> getAvailableDrivers();

    List<Driver> getAllDrivers();
}