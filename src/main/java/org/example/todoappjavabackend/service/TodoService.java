package org.example.todoappjavabackend.service;

import org.example.todoappjavabackend.dto.NewTodoDto;
import org.example.todoappjavabackend.dto.UpdateTodoDto;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.model.TodoStatus;
import org.example.todoappjavabackend.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepo todoRepo;
    private final IdService idService;

    public TodoService(TodoRepo todoRepo, IdService idService) {
        this.todoRepo = todoRepo;
        this.idService = idService;
    }

    public Todo saveNewTodo(NewTodoDto todoDto) {
        Todo todoToSave = new Todo(
                idService.randomId(),
                todoDto.description(),
                TodoStatus.OPEN
        );
        return todoRepo.save(todoToSave);
    }

    public List<Todo> retrieveAllTodos() {
        return todoRepo.findAll();
    }

    public Todo retrieveTodoById(String id) {
        return todoRepo.findById(id).orElseThrow();
    }

    public Todo updateTodoById(String id, UpdateTodoDto updateTodoDto) {
        return todoRepo.findById(id)
                    .map(todo -> {
                        Todo updatedTodo = todo;

                        updatedTodo = updatedTodo.withDescription(updateTodoDto.description());
                        updatedTodo = updatedTodo.withStatus(updateTodoDto.status());
                        return todoRepo.save(updatedTodo);
                    }).orElseThrow();
    }

    public void deleteTodoById(String id) {
        todoRepo.deleteById(id);
    }
}
