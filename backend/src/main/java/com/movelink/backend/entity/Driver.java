package com.movelink.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licenseNumber;

    @Enumerated(EnumType.STRING)
private com.movelink.backend.enums.VehicleType vehicleType;

    private String vehicleModel;

    private String vehicleColor;

    private String vehiclePlateNumber;

    private boolean available;

@Column(nullable = false)
@Builder.Default
private Double earnings = 0.0;

@Column(nullable = false)
@Builder.Default
private Boolean verified = false;

private String idNumber;

private String driversLicenseNumber;

private String vehicleRegistrationNumber;

@OneToOne
@JoinColumn(name = "user_id")
private User user;

@Column
private Double latitude;

@Column
private Double longitude;

@OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
private DriverDocument documents;

}