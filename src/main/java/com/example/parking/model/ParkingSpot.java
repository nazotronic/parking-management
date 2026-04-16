package com.example.parking.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ParkingSpot {
    private String id;

    @NotBlank(message = "Номер паркомісця не може бути порожнім")
    private String number;

    private boolean isOccupied;
}