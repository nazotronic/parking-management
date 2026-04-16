package com.example.parking.controller;

import com.example.parking.model.Client;
import com.example.parking.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients") // Всі запити сюди починаються з /api/clients
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Client> getAll() {
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable String id) {
        return service.getClientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Віддаємо 201 Created
    public Client create(@Valid @RequestBody Client client) {
        // @Valid перевірить, чи не пусті ПІБ та інші поля, які ми помітили @NotBlank
        return service.createClient(client);
    }
}