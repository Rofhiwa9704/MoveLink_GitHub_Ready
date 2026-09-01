package com.movelink.backend.util;

import com.movelink.backend.enums.VehicleType;

public class FareCalculator {

    public static double calculateFare(double distance, VehicleType vehicleType) {

        double baseFare;
        double pricePerKm;

        switch (vehicleType) {

            case BAKKIE:
                baseFare = 150;
                pricePerKm = 8;
                break;

            case MINI_TRUCK:
                baseFare = 250;
                pricePerKm = 10;
                break;

            case ONE_TON_TRUCK:
                baseFare = 350;
                pricePerKm = 12;
                break;

            case THREE_TON_TRUCK:
                baseFare = 500;
                pricePerKm = 16;
                break;

            case FIVE_TON_TRUCK:
                baseFare = 700;
                pricePerKm = 20;
                break;

            case EIGHT_TON_TRUCK:
                baseFare = 1000;
                pricePerKm = 28;
                break;

            case PANEL_VAN:
                baseFare = 220;
                pricePerKm = 9;
                break;

            case HORSE_AND_TRAILER:
                baseFare = 1500;
                pricePerKm = 35;
                break;

            default:
                baseFare = 150;
                pricePerKm = 8;
        }

        return baseFare + (distance * pricePerKm);
    }
}