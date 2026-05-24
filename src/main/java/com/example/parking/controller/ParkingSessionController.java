package com.example.parking.controller;

import com.example.parking.model.ParkingSession;
import com.example.parking.service.ParkingSessionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class ParkingSessionController {

    private final ParkingSessionService service;

    public ParkingSessionController(ParkingSessionService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ParkingSession> getAll(Pageable pageable) {
        return service.getAllSessions(pageable);
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

    @PutMapping("/{id}")
    public ParkingSession update(@PathVariable String id, @Valid @RequestBody ParkingSession session) {
        return service.updateSession(id, session);
    }
    
    @PutMapping("/{id}/close")
    public ParkingSession closeSession(@PathVariable String id) {
        return service.closeSession(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteSession(id);
    }
}