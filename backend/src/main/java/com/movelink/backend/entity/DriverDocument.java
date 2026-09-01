package com.movelink.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "driver_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    private String idDocument;

    private String driversLicense;

    private String vehicleRegistration;

    private String vehiclePhoto;

    private String roadworthyCertificate;

    private String insuranceDocument;

    @Builder.Default
    private Boolean approved = false;
}