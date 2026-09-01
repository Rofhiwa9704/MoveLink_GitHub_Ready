package com.movelink.backend.service;

import com.movelink.backend.dto.DriverLocationMessage;
import com.movelink.backend.dto.DriverLocationRequest;
import com.movelink.backend.dto.DriverLocationResponse;
import com.movelink.backend.dto.DriverRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.repository.DriverRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public DriverServiceImpl(
            DriverRepository driverRepository,
            SimpMessagingTemplate messagingTemplate) {

        this.driverRepository = driverRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public String registerDriver(DriverRequest request) {

        Driver driver = Driver.builder()
                .licenseNumber(request.getLicenseNumber())
                .vehicleType(request.getVehicleType())
                .vehicleModel(request.getVehicleModel())
                .vehicleColor(request.getVehicleColor())
                .vehiclePlateNumber(request.getVehiclePlateNumber())

                .idNumber(request.getIdNumber())
                .driversLicenseNumber(request.getDriversLicenseNumber())
                .vehicleRegistrationNumber(request.getVehicleRegistrationNumber())

                .verified(false)
                .available(true)
                .earnings(0.0)
                .build();

        driverRepository.save(driver);

        return "Driver registered successfully";
    }

    @Override
    public Driver approveDriver(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setVerified(true);

        return driverRepository.save(driver);
    }

    @Override
    public Double getDriverEarnings(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return driver.getEarnings();
    }

    @Override
    public DriverLocationResponse getDriverLocation(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return new DriverLocationResponse(
                driver.getId(),
                driver.getLatitude(),
                driver.getLongitude(),
                driver.isAvailable()
        );
    }

    @Override
    public Driver updateLocation(Long driverId, DriverLocationRequest request) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setLatitude(request.getLatitude());
        driver.setLongitude(request.getLongitude());

        driverRepository.save(driver);

        messagingTemplate.convertAndSend(
                "/topic/driver/" + driver.getId(),
                new DriverLocationMessage(
                        driver.getId(),
                        driver.getLatitude(),
                        driver.getLongitude()
                )
        );

        return driver;
    }

    // ==========================
    // NEW METHODS
    // ==========================

    @Override
    public Driver goOnline(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setAvailable(true);

        return driverRepository.save(driver);
    }

    @Override
    public Driver goOffline(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setAvailable(false);

        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByAvailableTrue();
    }

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}