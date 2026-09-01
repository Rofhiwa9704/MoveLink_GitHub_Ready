package com.movelink.backend.service;

import com.movelink.backend.dto.RideRequest;
import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.User;
import com.movelink.backend.enums.RideStatus;
import com.movelink.backend.repository.DriverRepository;
import com.movelink.backend.repository.RideRepository;
import com.movelink.backend.repository.UserRepository;
import com.movelink.backend.util.DistanceCalculator;
import com.movelink.backend.util.FareCalculator;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    public RideServiceImpl(
            RideRepository rideRepository,
            DriverRepository driverRepository,
            UserRepository userRepository) {

        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // REQUEST RIDE
    // =========================================================

    @Override
    public Ride requestRide(RideRequest request) {

        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        Ride ride = Ride.builder()
                .pickupLocation(request.getPickupLocation())
                .pickupLatitude(request.getPickupLatitude())
                .pickupLongitude(request.getPickupLongitude())
                .destination(request.getDestination())
                .loadDescription(request.getLoadDescription())
                .estimatedWeight(request.getEstimatedWeight())
                .helpersRequired(request.getHelpersRequired())
                .requiredVehicleType(request.getRequiredVehicleType())
                .scheduledDateTime(request.getScheduledDateTime())
                .scheduledRide(
                        request.getScheduledRide() != null
                                ? request.getScheduledRide()
                                : false
                )
                .customer(customer)
                .status(RideStatus.PENDING)
                .build();

        /*
         * Automatically look for the nearest suitable driver.
         *
         * If no suitable driver is found, the ride stays PENDING.
         */
        if (request.getPickupLatitude() != null
                && request.getPickupLongitude() != null) {

            driverRepository.findByAvailableTrue()
                    .stream()
                    .filter(driver ->
                            driver.getVehicleType()
                                    == request.getRequiredVehicleType()
                                    && driver.getVerified()
                                    && driver.isAvailable()
                                    && driver.getLatitude() != null
                                    && driver.getLongitude() != null
                    )
                    .min(Comparator.comparingDouble(driver ->
                            DistanceCalculator.calculateDistance(
                                    request.getPickupLatitude(),
                                    request.getPickupLongitude(),
                                    driver.getLatitude(),
                                    driver.getLongitude()
                            )
                    ))
                    .ifPresent(driver -> {

                        ride.setDriver(driver);
                        ride.setStatus(RideStatus.ACCEPTED);

                        driver.setAvailable(false);
                        driverRepository.save(driver);
                    });
        }

        return rideRepository.save(ride);
    }

    // =========================================================
    // ASSIGN DRIVER
    // =========================================================

    @Override
    public Ride assignDriver(Long rideId, Long driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        if (!driver.isAvailable()) {
            throw new RuntimeException(
                    "Driver is not available. Driver ID: "
                            + driverId
            );
        }

        if (!driver.getVerified()) {
            throw new RuntimeException(
                    "Driver is not verified."
            );
        }

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);

        driver.setAvailable(false);
        driverRepository.save(driver);

        return rideRepository.save(ride);
    }

    // =========================================================
    // ACCEPT RIDE
    // =========================================================

    @Override
    public Ride acceptRide(Long rideId, Long driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        /*
         * Prevent a ride from being accepted twice.
         */
        if (ride.getStatus() != RideStatus.PENDING) {

            throw new RuntimeException(
                    "Ride is no longer available. Current status: "
                            + ride.getStatus()
            );
        }

        /*
         * Make sure another driver has not already taken it.
         */
        if (ride.getDriver() != null) {

            throw new RuntimeException(
                    "Ride has already been assigned to a driver."
            );
        }

        /*
         * Driver must be available.
         */
        if (!driver.isAvailable()) {

            throw new RuntimeException(
                    "Driver is not available. Driver ID: "
                            + driverId
            );
        }

        /*
         * Driver must be verified.
         */
        if (!driver.getVerified()) {

            throw new RuntimeException(
                    "Driver is not verified."
            );
        }

        /*
         * Check vehicle type.
         */
        if (ride.getRequiredVehicleType() != null
                && driver.getVehicleType()
                != ride.getRequiredVehicleType()) {

            throw new RuntimeException(
                    "Driver vehicle type does not match the ride."
            );
        }

        /*
         * Assign driver to ride.
         */
        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);

        /*
         * Driver is now busy.
         */
        driver.setAvailable(false);

        driverRepository.save(driver);

        return rideRepository.save(ride);
    }

    // =========================================================
    // DECLINE RIDE
    // =========================================================

    @Override
    public Ride declineRide(Long rideId, Long driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        /*
         * Completed and cancelled rides cannot be declined.
         */
        if (ride.getStatus() == RideStatus.COMPLETED
                || ride.getStatus() == RideStatus.CANCELLED) {

            throw new RuntimeException(
                    "Ride cannot be declined. Current status: "
                            + ride.getStatus()
            );
        }

        /*
         * If this driver is currently assigned to the ride,
         * remove the driver.
         */
        if (ride.getDriver() != null
                && ride.getDriver().getId().equals(driverId)) {

            ride.setDriver(null);
        }

        /*
         * Make the driver available again.
         */
        driver.setAvailable(true);
        driverRepository.save(driver);

        /*
         * Find another suitable driver.
         */
        List<Driver> availableDrivers =
                driverRepository.findByAvailableTrue();

        /*
         * If pickup coordinates are unavailable,
         * we cannot calculate distance.
         */
        if (ride.getPickupLatitude() == null
                || ride.getPickupLongitude() == null) {

            ride.setDriver(null);
            ride.setStatus(RideStatus.WAITING_FOR_DRIVER);

            return rideRepository.save(ride);
        }

        availableDrivers.stream()
                .filter(otherDriver ->
                        !otherDriver.getId().equals(driverId)
                        && otherDriver.getVerified()
                        && otherDriver.isAvailable()
                        && otherDriver.getLatitude() != null
                        && otherDriver.getLongitude() != null
                        && (
                            ride.getRequiredVehicleType() == null
                            || otherDriver.getVehicleType()
                            == ride.getRequiredVehicleType()
                        )
                )
                .min(Comparator.comparingDouble(otherDriver ->
                        DistanceCalculator.calculateDistance(
                                ride.getPickupLatitude(),
                                ride.getPickupLongitude(),
                                otherDriver.getLatitude(),
                                otherDriver.getLongitude()
                        )
                ))
                .ifPresentOrElse(

                        /*
                         * Another driver was found.
                         */
                        otherDriver -> {

                            ride.setDriver(otherDriver);
                            ride.setStatus(RideStatus.ACCEPTED);

                            otherDriver.setAvailable(false);
                            driverRepository.save(otherDriver);
                        },

                        /*
                         * No other driver available.
                         */
                        () -> {

                            ride.setDriver(null);
                            ride.setStatus(
                                    RideStatus.WAITING_FOR_DRIVER
                            );
                        }
                );

        return rideRepository.save(ride);
    }

    // =========================================================
    // START RIDE
    // =========================================================

    @Override
    public Ride startRide(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.ACCEPTED) {

            throw new RuntimeException(
                    "Ride cannot be started. Current status: "
                            + ride.getStatus()
            );
        }

        if (ride.getDriver() == null) {

            throw new RuntimeException(
                    "Cannot start ride without a driver."
            );
        }

        ride.setStatus(RideStatus.IN_PROGRESS);

        return rideRepository.save(ride);
    }

    // =========================================================
    // COMPLETE RIDE
    // =========================================================

    @Override
    public Ride completeRide(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.IN_PROGRESS) {

            throw new RuntimeException(
                    "Ride cannot be completed. Current status: "
                            + ride.getStatus()
            );
        }

        ride.setStatus(RideStatus.COMPLETED);

        Driver driver = ride.getDriver();

        if (driver != null) {

            /*
             * Calculate distance when coordinates are available.
             */
            if (ride.getPickupLatitude() != null
                    && ride.getPickupLongitude() != null
                    && driver.getLatitude() != null
                    && driver.getLongitude() != null) {

                double distance =
                        DistanceCalculator.calculateDistance(
                                ride.getPickupLatitude(),
                                ride.getPickupLongitude(),
                                driver.getLatitude(),
                                driver.getLongitude()
                        );

                ride.setDistance(distance);

                double fare =
                        FareCalculator.calculateFare(
                                distance,
                                driver.getVehicleType()
                        );

                ride.setFare(fare);

                /*
                 * Add the fare to driver earnings.
                 */
                driver.setEarnings(
                        driver.getEarnings() + fare
                );
            }

            /*
             * Driver becomes available again.
             */
            driver.setAvailable(true);

            driverRepository.save(driver);
        }

        return rideRepository.save(ride);
    }

    // =========================================================
    // CANCEL RIDE
    // =========================================================

    @Override
    public Ride cancelRide(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        if (ride.getStatus() == RideStatus.IN_PROGRESS
                || ride.getStatus() == RideStatus.COMPLETED) {

            throw new RuntimeException(
                    "Ride cannot be cancelled. Current status: "
                            + ride.getStatus()
            );
        }

        ride.setStatus(RideStatus.CANCELLED);

        Driver driver = ride.getDriver();

        if (driver != null) {

            driver.setAvailable(true);
            driverRepository.save(driver);
        }

        return rideRepository.save(ride);
    }

    // =========================================================
    // GET ALL RIDES
    // =========================================================

    @Override
    public List<Ride> getAllRides() {

        return rideRepository.findAll();
    }

    // =========================================================
    // GET DRIVER RIDES
    // =========================================================

    @Override
    public List<Ride> getDriverRides(Long driverId) {

        return rideRepository.findByDriverId(driverId);
    }

    // =========================================================
    // GET CUSTOMER RIDES
    // =========================================================

    @Override
    public List<Ride> getCustomerRides(Long customerId) {

        return rideRepository.findByCustomerId(customerId);
    }

    // =========================================================
    // GET PENDING RIDES
    // =========================================================

    @Override
    public List<Ride> getPendingRides() {

        return rideRepository.findByStatusAndDriverIsNull(
                RideStatus.PENDING
        );
    }
}