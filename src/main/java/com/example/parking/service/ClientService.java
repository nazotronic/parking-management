package com.example.parking.service;

import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.model.Client;
import com.example.parking.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // Отримати всіх клієнтів з пагінацією
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    // Знайти клієнта за ID
    public Client getClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Клієнта з ID " + id + " не знайдено"));
    }

    // Створити нового клієнта
    @Transactional
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    // Оновити клієнта
    @Transactional
    public Client updateClient(String id, Client updatedClient) {
        Client existingClient = getClientById(id);
        existingClient.setFullName(updatedClient.getFullName());
        existingClient.setPhone(updatedClient.getPhone());
        existingClient.setLicensePlate(updatedClient.getLicensePlate());
        return clientRepository.save(existingClient);
    }

    // Видалити клієнта
    @Transactional
    public void deleteClient(String id) {
        Client existingClient = getClientById(id);
        clientRepository.delete(existingClient);
    }
}