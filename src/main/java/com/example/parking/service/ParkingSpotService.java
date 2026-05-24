package com.example.parking.service;

import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.model.ParkingSpot;
import com.example.parking.repository.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    // Отримати всі місця з пагінацією
    public Page<ParkingSpot> getAllSpots(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    // Знайти місце за ID
    public ParkingSpot getSpotById(String id) {
        return parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Паркомісце з ID " + id + " не знайдено"));
    }

    // Створити нове місце
    @Transactional
    public ParkingSpot createSpot(ParkingSpot spot) {
        spot.setOccupied(false); // За замовчуванням нове місце вільне
        return parkingSpotRepository.save(spot);
    }

    // Оновити паркомісце
    @Transactional
    public ParkingSpot updateSpot(String id, ParkingSpot updatedSpot) {
        ParkingSpot existingSpot = getSpotById(id);
        existingSpot.setNumber(updatedSpot.getNumber());
        existingSpot.setOccupied(updatedSpot.isOccupied());
        return parkingSpotRepository.save(existingSpot);
    }

    // Видалити паркомісце
    @Transactional
    public void deleteSpot(String id) {
        ParkingSpot existingSpot = getSpotById(id);
        parkingSpotRepository.delete(existingSpot);
    }
}