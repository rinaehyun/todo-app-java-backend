package org.example.todoappjavabackend.exception;


import java.util.Date;

public record ErrorMessage(
        Date timestamp,
        int status,
        String message,
        String description
) {
}
