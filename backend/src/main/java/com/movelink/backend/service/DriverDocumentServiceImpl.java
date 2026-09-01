package com.movelink.backend.service;


import com.movelink.backend.entity.Driver;
import com.movelink.backend.entity.DriverDocument;
import com.movelink.backend.repository.DriverDocumentRepository;
import org.springframework.web.multipart.MultipartFile;
import com.movelink.backend.repository.DriverRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverDocumentServiceImpl implements DriverDocumentService {

    private final DriverRepository driverRepository;
    private final DriverDocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    public DriverDocumentServiceImpl(
        DriverRepository driverRepository,
        DriverDocumentRepository documentRepository,
        FileStorageService fileStorageService) {

    this.driverRepository = driverRepository;
    this.documentRepository = documentRepository;
    this.fileStorageService = fileStorageService;
}

@Override
public DriverDocument uploadDocuments(
        Long driverId,
        MultipartFile idDocument,
        MultipartFile driversLicense,
        MultipartFile vehicleRegistration,
        MultipartFile vehiclePhoto,
        MultipartFile roadworthyCertificate,
        MultipartFile insuranceDocument) {

    Driver driver = driverRepository.findById(driverId)
            .orElseThrow(() -> new RuntimeException("Driver not found"));

    DriverDocument document = DriverDocument.builder()
            .driver(driver)
            .idDocument(fileStorageService.storeFile(idDocument))
            .driversLicense(fileStorageService.storeFile(driversLicense))
            .vehicleRegistration(fileStorageService.storeFile(vehicleRegistration))
            .vehiclePhoto(fileStorageService.storeFile(vehiclePhoto))
            .roadworthyCertificate(
                    roadworthyCertificate != null
                            ? fileStorageService.storeFile(roadworthyCertificate)
                            : null)
            .insuranceDocument(
                    insuranceDocument != null
                            ? fileStorageService.storeFile(insuranceDocument)
                            : null)
            .approved(false)
            .build();

    return documentRepository.save(document);
}

@Override
public DriverDocument approveDocument(Long documentId) {

    DriverDocument document = documentRepository.findById(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found"));

    document.setApproved(true);

    Driver driver = document.getDriver();
    driver.setVerified(true);

    driverRepository.save(driver);

    return documentRepository.save(document);
}
    }

     
