package com.example.parking.service;

import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.model.ParkingSession;
import com.example.parking.repository.ParkingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class ParkingSessionService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final RestTemplate restTemplate;

    // URL сервісу клієнтів (Service A)
    private static final String CLIENT_SERVICE_URL = "http://localhost:8081/api/clients/";

    public ParkingSessionService(ParkingSessionRepository parkingSessionRepository, RestTemplate restTemplate) {
        this.parkingSessionRepository = parkingSessionRepository;
        this.restTemplate = restTemplate;
    }

    public Page<ParkingSession> getAllSessions(Pageable pageable) {
        return parkingSessionRepository.findAll(pageable);
    }

    public ParkingSession getSessionById(String id) {
        return parkingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сесію з ID " + id + " не знайдено"));
    }

    @Transactional
    public ParkingSession createSession(ParkingSession session) {
        // МІЖСЕРВІСНА ВЗАЄМОДІЯ: Перевіряємо чи існує клієнт у Service A
        try {
            restTemplate.getForObject(CLIENT_SERVICE_URL + session.getClientId(), Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Клієнта з ID " + session.getClientId() + " не знайдено в client-service!");
        } catch (Exception e) {
            throw new RuntimeException("Сервіс клієнтів (client-service) тимчасово недоступний. Неможливо створити сесію.");
        }

        session.setStartTime(LocalDateTime.now());
        session.setActive(true);
        session.setEndTime(null);
        return parkingSessionRepository.save(session);
    }

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

    @Transactional
    public ParkingSession closeSession(String id) {
        ParkingSession session = getSessionById(id);
        session.setActive(false);
        session.setEndTime(LocalDateTime.now());
        return parkingSessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(String id) {
        ParkingSession session = getSessionById(id);
        parkingSessionRepository.delete(session);
    }
}