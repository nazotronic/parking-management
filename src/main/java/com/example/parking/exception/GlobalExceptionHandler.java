package com.example.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 1. Обробка помилки "Об'єкт не знайдено" (коли ми кидали RuntimeException у сервісах)
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Повертає статус 404
    public Map<String, String> handleNotFoundException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        // Результат буде: {"error": "Паркомісце з ID 123 не знайдено"}
        return errorResponse;
    }

    // 2. Обробка помилок валідації (коли дані не пройшли перевірку @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Повертає статус 400
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Проходимось по всіх неправильних полях і збираємо наші повідомлення
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        // Результат буде: {"fullName": "ПІБ не може бути порожнім"}
        return errors;
    }
}