package com.example.parking.controller;

import com.example.parking.model.Client;
import com.example.parking.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Client> getAll(Pageable pageable) {
        return service.getAllClients(pageable);
    }

    @GetMapping("/{id}")
    public Client getById(@PathVariable String id) {
        return service.getClientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@Valid @RequestBody Client client) {
        return service.createClient(client);
    }

    @PutMapping("/{id}")
    public Client update(@PathVariable String id, @Valid @RequestBody Client client) {
        return service.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.deleteClient(id);
    }
}