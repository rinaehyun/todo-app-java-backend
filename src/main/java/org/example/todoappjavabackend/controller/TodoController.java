package org.example.todoappjavabackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.repository.TodoRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepo todoRepo;

    @PostMapping("/todo")
    public Todo saveTodo(@RequestBody Todo todo) {
        return todo;
    }
}
