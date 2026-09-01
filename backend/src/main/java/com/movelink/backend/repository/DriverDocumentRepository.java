package com.movelink.backend.repository;

import com.movelink.backend.entity.DriverDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverDocumentRepository extends JpaRepository<DriverDocument, Long> {

    Optional<DriverDocument> findByDriverId(Long driverId);

}