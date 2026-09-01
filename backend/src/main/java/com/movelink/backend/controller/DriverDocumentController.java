package com.movelink.backend.controller;

import com.movelink.backend.entity.DriverDocument;
import com.movelink.backend.service.DriverDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/driver-documents")
public class DriverDocumentController {

    private final DriverDocumentService driverDocumentService;

    public DriverDocumentController(DriverDocumentService driverDocumentService) {
        this.driverDocumentService = driverDocumentService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<DriverDocument> uploadDocuments(

            @RequestParam Long driverId,

            @RequestParam MultipartFile idDocument,

            @RequestParam MultipartFile driversLicense,

            @RequestParam MultipartFile vehicleRegistration,

            @RequestParam MultipartFile vehiclePhoto,

            @RequestParam(required = false) MultipartFile roadworthyCertificate,

            @RequestParam(required = false) MultipartFile insuranceDocument) {

        return ResponseEntity.ok(
                driverDocumentService.uploadDocuments(
                        driverId,
                        idDocument,
                        driversLicense,
                        vehicleRegistration,
                        vehiclePhoto,
                        roadworthyCertificate,
                        insuranceDocument
                )
        );
    }
}