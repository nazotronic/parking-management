package com.example.parking.controller;

import com.example.parking.model.ParkingSession;
import com.example.parking.service.ParkingSessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions") // Всі запити сюди починаються з /api/sessions
public class ParkingSessionController {

    private final ParkingSessionService service;

    public ParkingSessionController(ParkingSessionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ParkingSession> getAll() {
        return service.getAllSessions();
    }

    @GetMapping("/{id}")
    public ParkingSession getById(@PathVariable String id) {
        return service.getSessionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSession create(@Valid @RequestBody ParkingSession session) {
        return service.createSession(session);
    }
}