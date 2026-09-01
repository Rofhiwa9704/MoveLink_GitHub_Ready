package com.movelink.backend.service;

import com.movelink.backend.entity.DriverDocument;
import org.springframework.web.multipart.MultipartFile;

public interface DriverDocumentService {

    DriverDocument uploadDocuments(
            Long driverId,
            MultipartFile idDocument,
            MultipartFile driversLicense,
            MultipartFile vehicleRegistration,
            MultipartFile vehiclePhoto,
            MultipartFile roadworthyCertificate,
            MultipartFile insuranceDocument
    );
    DriverDocument approveDocument(Long documentId);
}