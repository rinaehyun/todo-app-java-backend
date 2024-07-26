package org.example.todoappjavabackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("todos")
public record Todo(
        @Id
        String id,
        String todo,
        String description
) {
}
