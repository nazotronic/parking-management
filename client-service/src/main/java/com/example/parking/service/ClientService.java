package com.example.parking.service;

import com.example.parking.exception.ResourceNotFoundException;
import com.example.parking.model.Client;
import com.example.parking.repository.ClientRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    // Кешуємо результат за ключем "clients_page" (оскільки це пагінація)
    @Cacheable(value = "clients", key = "'page_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<Client> getAllClients(Pageable pageable) {
        // Ми додаємо Thread.sleep, щоб імітувати довгий запит і побачити вплив кешу
        try {
            Thread.sleep(50); // Імітація довгого запиту до БД
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return clientRepository.findAll(pageable);
    }

    // Кешуємо конкретного клієнта за його ID
    @Cacheable(value = "clients", key = "#id")
    public Client getClientById(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Клієнта з ID " + id + " не знайдено"));
    }

    // Очищаємо всі записи в кеші "clients", коли додаємо нового клієнта
    @Transactional
    @CacheEvict(value = "clients", allEntries = true)
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    // Очищаємо кеш при оновленні
    @Transactional
    @CacheEvict(value = "clients", allEntries = true)
    public Client updateClient(String id, Client updatedClient) {
        Client existingClient = getClientById(id);
        existingClient.setFullName(updatedClient.getFullName());
        existingClient.setPhone(updatedClient.getPhone());
        existingClient.setLicensePlate(updatedClient.getLicensePlate());
        return clientRepository.save(existingClient);
    }

    // Очищаємо кеш при видаленні
    @Transactional
    @CacheEvict(value = "clients", allEntries = true)
    public void deleteClient(String id) {
        Client existingClient = getClientById(id);
        clientRepository.delete(existingClient);
    }
}