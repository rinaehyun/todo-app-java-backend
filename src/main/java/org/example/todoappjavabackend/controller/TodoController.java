package org.example.todoappjavabackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.todoappjavabackend.dto.NewTodoDto;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /* TODO: check endpoints again
       `/api/todo` -> only todos? or other status as well?
    */
}
