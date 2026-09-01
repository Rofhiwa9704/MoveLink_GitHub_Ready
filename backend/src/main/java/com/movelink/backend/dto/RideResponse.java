package com.movelink.backend.dto;

import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.Ride;
import com.movelink.backend.entity.User;

public class RideResponse {

    private Long id;

    private String pickupLocation;
    private String destination;

    private Double pickupLatitude;
    private Double pickupLongitude;

    private Double distance;
    private Double fare;

    private String scheduledDateTime;
    private Boolean scheduledRide;

    private String loadDescription;
    private Double estimatedWeight;
    private Integer helpersRequired;
    private String requiredVehicleType;

    private String status;
    private String requestTime;

    private CustomerResponse customer;
    private DriverResponse driver;

    public RideResponse(Ride ride) {

        this.id = ride.getId();
        this.pickupLocation = ride.getPickupLocation();
        this.destination = ride.getDestination();

        this.pickupLatitude = ride.getPickupLatitude();
        this.pickupLongitude = ride.getPickupLongitude();

        this.distance = ride.getDistance();
        this.fare = ride.getFare();

        if (ride.getScheduledDateTime() != null) {
            this.scheduledDateTime =
                    ride.getScheduledDateTime().toString();
        }

        this.scheduledRide = ride.getScheduledRide();

        this.loadDescription = ride.getLoadDescription();
        this.estimatedWeight = ride.getEstimatedWeight();
        this.helpersRequired = ride.getHelpersRequired();

        if (ride.getRequiredVehicleType() != null) {
            this.requiredVehicleType =
                    ride.getRequiredVehicleType().toString();
        }

        if (ride.getStatus() != null) {
            this.status = ride.getStatus().toString();
        }

        if (ride.getRequestTime() != null) {
            this.requestTime = ride.getRequestTime().toString();
        }

        if (ride.getCustomer() != null) {
            this.customer = new CustomerResponse(ride.getCustomer());
        }

        if (ride.getDriver() != null) {
            this.driver = new DriverResponse(ride.getDriver());
        }
    }

    public Long getId() {
        return id;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public Double getPickupLatitude() {
        return pickupLatitude;
    }

    public Double getPickupLongitude() {
        return pickupLongitude;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getFare() {
        return fare;
    }

    public String getScheduledDateTime() {
        return scheduledDateTime;
    }

    public Boolean getScheduledRide() {
        return scheduledRide;
    }

    public String getLoadDescription() {
        return loadDescription;
    }

    public Double getEstimatedWeight() {
        return estimatedWeight;
    }

    public Integer getHelpersRequired() {
        return helpersRequired;
    }

    public String getRequiredVehicleType() {
        return requiredVehicleType;
    }

    public String getStatus() {
        return status;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public DriverResponse getDriver() {
        return driver;
    }

    public static class CustomerResponse {

        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String role;

        public CustomerResponse(User user) {
            this.id = user.getId();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();

            if (user.getRole() != null) {
                this.role = user.getRole().toString();
            }
        }

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getRole() {
            return role;
        }
    }

    public static class DriverResponse {

        private Long id;

        private String licenseNumber;
        private String vehicleType;
        private String vehicleModel;
        private String vehicleColor;
        private String vehiclePlateNumber;

        private Boolean available;
        private Double earnings;
        private Boolean verified;

        private Double latitude;
        private Double longitude;

        private String firstName;
        private String lastName;

        public DriverResponse(Driver driver) {

            this.id = driver.getId();

            this.licenseNumber = driver.getLicenseNumber();

            if (driver.getVehicleType() != null) {
                this.vehicleType =
                        driver.getVehicleType().toString();
            }

            this.vehicleModel = driver.getVehicleModel();
            this.vehicleColor = driver.getVehicleColor();
            this.vehiclePlateNumber =
                    driver.getVehiclePlateNumber();

            this.available = driver.isAvailable();
            this.earnings = driver.getEarnings();
            this.verified = driver.getVerified();

            this.latitude = driver.getLatitude();
            this.longitude = driver.getLongitude();

            if (driver.getUser() != null) {
                this.firstName =
                        driver.getUser().getFirstName();

                this.lastName =
                        driver.getUser().getLastName();
            }
        }

        public Long getId() {
            return id;
        }

        public String getLicenseNumber() {
            return licenseNumber;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public String getVehicleModel() {
            return vehicleModel;
        }

        public String getVehicleColor() {
            return vehicleColor;
        }

        public String getVehiclePlateNumber() {
            return vehiclePlateNumber;
        }

        public Boolean getAvailable() {
            return available;
        }

        public Double getEarnings() {
            return earnings;
        }

        public Boolean getVerified() {
            return verified;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}