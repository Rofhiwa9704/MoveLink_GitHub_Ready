package com.movelink.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ride_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
}