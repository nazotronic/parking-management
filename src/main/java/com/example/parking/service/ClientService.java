package com.example.parking.service;

import com.example.parking.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    // In-memory сховище для клієнтів
    private final List<Client> clients = new ArrayList<>();

    // Отримати всіх клієнтів
    public List<Client> getAllClients() {
        return clients;
    }

    // Знайти клієнта за ID
    public Client getClientById(String id) {
        return clients.stream()
                .filter(client -> client.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Клієнта з ID " + id + " не знайдено"));
    }

    // Створити нового клієнта
    public Client createClient(Client client) {
        client.setId(UUID.randomUUID().toString()); // Генеруємо унікальний ID
        clients.add(client);
        return client;
    }
}