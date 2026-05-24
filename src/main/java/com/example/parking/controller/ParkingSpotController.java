package com.example.parking.controller;

import com.example.parking.model.ParkingSpot;
import com.example.parking.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spots")
public class ParkingSpotController {

    private final ParkingSpotService service;

    public ParkingSpotController(ParkingSpotService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ParkingSpot> getAll(Pageable pageable) {
        return service.getAllSpots(pageable);
    }

    @GetMapping("/{id}")
    public ParkingSpot getById(@PathVariable String id) {
        return service.getSpotById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSpot create(@Valid @RequestBody ParkingSpot spot) {
        return service.createSpot(spot);
    }

    @PutMapping("/{id}")
    public ParkingSpot update(@PathVariable String id, @Valid @RequestBody ParkingSpot spot) {
        return service.updateSpot(id, spot);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteSpot(id);
    }
}