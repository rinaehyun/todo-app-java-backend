package org.example.todoappjavabackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.todoappjavabackend.dto.NewTodoDto;
import org.example.todoappjavabackend.dto.UpdateTodoDto;
import org.example.todoappjavabackend.exception.ErrorMessage;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public Todo saveTodo(@RequestBody NewTodoDto todoDto) {
        return todoService.saveNewTodo(todoDto);
    }

    @GetMapping("/todo")
    public List<Todo> getAllTodos() {
        return todoService.retrieveAllTodos();
    }

    @GetMapping("/todo/{id}")
    public Todo getTodoById(@PathVariable String id) {
        return todoService.retrieveTodoById(id);
    }

    @PutMapping("/todo/{id}")
    public Todo updateTodo(
            @PathVariable String id,
            @RequestBody UpdateTodoDto updateTodoDto) {
        return todoService.updateTodoById(id, updateTodoDto);
    }

    @DeleteMapping("/todo/{id}")
    public void deleteTodo(@PathVariable String id) {
        todoService.deleteTodoById(id);
    }
}
