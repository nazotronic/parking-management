package com.example.parking.service;

import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.model.ParkingSession;
import com.example.parking.repository.ParkingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ParkingSessionService {

    private final ParkingSessionRepository parkingSessionRepository;

    public ParkingSessionService(ParkingSessionRepository parkingSessionRepository) {
        this.parkingSessionRepository = parkingSessionRepository;
    }

    // Отримати всі сесії з пагінацією
    public Page<ParkingSession> getAllSessions(Pageable pageable) {
        return parkingSessionRepository.findAll(pageable);
    }

    // Знайти сесію за ID
    public ParkingSession getSessionById(String id) {
        return parkingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сесію з ID " + id + " не знайдено"));
    }

    // Створити нову сесію
    @Transactional
    public ParkingSession createSession(ParkingSession session) {
        session.setStartTime(LocalDateTime.now());
        session.setActive(true);
        session.setEndTime(null);
        return parkingSessionRepository.save(session);
    }

    // Оновити сесію (наприклад, закрити її)
    @Transactional
    public ParkingSession updateSession(String id, ParkingSession updatedSession) {
        ParkingSession existingSession = getSessionById(id);
        existingSession.setSpotId(updatedSession.getSpotId());
        existingSession.setClientId(updatedSession.getClientId());
        existingSession.setStartTime(updatedSession.getStartTime());
        existingSession.setEndTime(updatedSession.getEndTime());
        existingSession.setActive(updatedSession.isActive());
        return parkingSessionRepository.save(existingSession);
    }

    // Закрити сесію (додатковий зручний метод)
    @Transactional
    public ParkingSession closeSession(String id) {
        ParkingSession session = getSessionById(id);
        session.setActive(false);
        session.setEndTime(LocalDateTime.now());
        return parkingSessionRepository.save(session);
    }

    // Видалити сесію
    @Transactional
    public void deleteSession(String id) {
        ParkingSession session = getSessionById(id);
        parkingSessionRepository.delete(session);
    }
}