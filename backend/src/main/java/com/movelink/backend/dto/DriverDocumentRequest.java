package com.movelink.backend.dto;

import lombok.Data;

@Data
public class DriverDocumentRequest {

    private Long driverId;

    private String idDocument;

    private String driversLicense;

    private String vehicleRegistration;

    private String vehiclePhoto;

    private String roadworthyCertificate;

    private String insuranceDocument;
}