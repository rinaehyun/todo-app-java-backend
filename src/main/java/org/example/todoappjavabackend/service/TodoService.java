package org.example.todoappjavabackend.service;

import lombok.RequiredArgsConstructor;
import org.example.todoappjavabackend.dto.NewTodoDto;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.model.TodoStatus;
import org.example.todoappjavabackend.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepo todoRepo;

    public Todo saveNewTodo(NewTodoDto todoDto) {
        Todo todoToSave = new Todo(
                UUID.randomUUID().toString(),
                todoDto.description(),
                TodoStatus.OPEN
        );
        return todoRepo.save(todoToSave);
    }

    public List<Todo> retrieveAllTodos() {
        List<Todo> todos = todoRepo.findAll();
        return todos;
    }

}
