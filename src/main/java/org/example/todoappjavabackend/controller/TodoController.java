package org.example.todoappjavabackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.todoappjavabackend.dto.NewTodoDto;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.service.TodoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public Todo saveTodo(@RequestBody NewTodoDto todoDto) {
        return todoService.saveNewTodo(todoDto);
    }
}
