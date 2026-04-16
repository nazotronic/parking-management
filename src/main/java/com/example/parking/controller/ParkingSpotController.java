package com.example.parking.controller;

import com.example.parking.model.ParkingSpot;
import com.example.parking.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Кажемо, що цей клас обробляє REST-запити
@RequestMapping("/api/spots") // Базовий URL для всіх методів тут
public class ParkingSpotController {

    private final ParkingSpotService service;

    public ParkingSpotController(ParkingSpotService service) {
        this.service = service;
    }

    @GetMapping // Обробляє GET /api/spots
    public List<ParkingSpot> getAll() {
        return service.getAllSpots();
    }

    @GetMapping("/{id}") // Обробляє GET /api/spots/{id}
    public ParkingSpot getById(@PathVariable String id) {
        return service.getSpotById(id);
    }

    @PostMapping // Обробляє POST /api/spots
    @ResponseStatus(HttpStatus.CREATED) // Повертає код 201 Created при успіху
    public ParkingSpot create(@Valid @RequestBody ParkingSpot spot) {
        return service.createSpot(spot);
    }
}