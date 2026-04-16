package com.example.parking.service;

import com.example.parking.model.ParkingSpot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingSpotService {
    // Наше in-memory сховище
    private final List<ParkingSpot> spots = new ArrayList<>();

    // GET all
    public List<ParkingSpot> getAllSpots() {
        return spots;
    }

    // GET by id
    public ParkingSpot getSpotById(String id) {
        return spots.stream()
                .filter(spot -> spot.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Паркомісце з ID " + id + " не знайдено"));
    }

    // POST (Створення)
    public ParkingSpot createSpot(ParkingSpot spot) {
        spot.setId(UUID.randomUUID().toString()); // Генеруємо унікальний ID
        spot.setOccupied(false); // За замовчуванням нове місце вільне
        spots.add(spot);
        return spot;
    }
}