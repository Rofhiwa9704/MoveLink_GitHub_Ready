package com.movelink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationMessage {

    private Long driverId;

    private Double latitude;

    private Double longitude;
}