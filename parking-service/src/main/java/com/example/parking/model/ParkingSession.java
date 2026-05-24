package com.example.parking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "parking_sessions")
public class ParkingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "ID паркомісця обов'язкове")
    private String spotId;

    @NotBlank(message = "ID клієнта обов'язкове")
    private String clientId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
}