package org.example.todoappjavabackend.dto;

import org.example.todoappjavabackend.model.TodoStatus;

public record UpdateTodoDto(
        String description,
        TodoStatus status
) {
}
