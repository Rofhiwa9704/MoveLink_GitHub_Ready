package com.movelink.backend.entity;

import com.movelink.backend.enums.RideStatus;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickupLocation;

    private String destination;

    @Column
    private Double pickupLatitude;

    @Column
    private Double pickupLongitude;

    @Column
    private Double distance;

    @Column
    private Double fare;

    private LocalDateTime scheduledDateTime;

@Builder.Default
private Boolean scheduledRide = false;

    @Column(length = 1000)
private String loadDescription;

private Double estimatedWeight;

private Integer helpersRequired;

@Enumerated(EnumType.STRING)
private com.movelink.backend.enums.VehicleType requiredVehicleType;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private LocalDateTime requestTime;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
private java.util.List<RideImage> images;

    @PrePersist
    public void prePersist() {
        requestTime = LocalDateTime.now();
    }
}