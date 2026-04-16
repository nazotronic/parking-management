package com.example.parking.service;

import com.example.parking.model.ParkingSession;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ParkingSessionService {
    // In-memory сховище для сесій паркування
    private final List<ParkingSession> sessions = new ArrayList<>();

    // Отримати всю історію сесій
    public List<ParkingSession> getAllSessions() {
        return sessions;
    }

    // Знайти конкретну сесію за ID
    public ParkingSession getSessionById(String id) {
        return sessions.stream()
                .filter(session -> session.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Сесію з ID " + id + " не знайдено"));
    }

    // Створити нову сесію (машина заїхала)
    public ParkingSession createSession(ParkingSession session) {
        session.setId(UUID.randomUUID().toString()); // Генеруємо ID сесії
        session.setStartTime(LocalDateTime.now());   // Автоматично ставимо поточний час заїзду
        session.setActive(true);                     // Сесія стає активною
        session.setEndTime(null);                    // Часу виїзду ще немає

        sessions.add(session);
        return session;
    }
}