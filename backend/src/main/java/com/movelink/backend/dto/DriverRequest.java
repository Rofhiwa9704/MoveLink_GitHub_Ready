package com.movelink.backend.dto;

import com.movelink.backend.enums.VehicleType;
import lombok.Data;

@Data
public class DriverRequest {

    private String licenseNumber;

    private VehicleType vehicleType;

    private String vehicleModel;

    private String vehicleColor;

    private String vehiclePlateNumber;

    private String idNumber;

    private String driversLicenseNumber;

    private String vehicleRegistrationNumber;
}