package org.example.todoappjavabackend.dto;

import org.example.todoappjavabackend.model.TodoStatus;

public record NewTodoDto(
        int todoNumber,
        String description,
        TodoStatus status) {
}
