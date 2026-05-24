package com.example.parking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "ПІБ не може бути порожнім")
    private String fullName;

    @NotBlank(message = "Номер телефону обов'язковий")
    private String phone;

    @NotBlank(message = "Номерний знак обов'язковий")
    private String licensePlate;
}