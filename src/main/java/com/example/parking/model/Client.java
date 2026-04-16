package com.example.parking.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class Client {
    private String id;

    @NotBlank(message = "ПІБ не може бути порожнім")
    private String fullName;

    @NotBlank(message = "Номер телефону обов'язковий")
    private String phone;

    @NotBlank(message = "Номерний знак обов'язковий")
    private String licensePlate;
}