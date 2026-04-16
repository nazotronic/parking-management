package com.example.parking.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ParkingSession {
    private String id;

    @NotBlank(message = "ID паркомісця обов'язкове")
    private String spotId;

    @NotBlank(message = "ID клієнта обов'язкове")
    private String clientId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
}