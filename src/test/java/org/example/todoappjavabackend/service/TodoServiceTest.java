package org.example.todoappjavabackend.service;

import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.model.TodoStatus;
import org.example.todoappjavabackend.repository.TodoRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TodoServiceTest {

    TodoRepo todoRepo = mock(TodoRepo.class);
    TodoService todoService = new TodoService(todoRepo);

    @Test
    public void retrieveAllTodosTest_whenNoTodosInDB_thenReturnEmptyList() {
        // GIVEN

        // WHEN
        List<Todo> actual = todoService.retrieveAllTodos();

        // THEN
        List<Todo> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveAllTodosTest() {
        // GIVEN
        Todo todo1 = new Todo("123", "Cooking", TodoStatus.OPEN);
        Todo todo2 = new Todo("456", "Jogging", TodoStatus.DONE);
        Todo todo3 = new Todo("789", "Reading", TodoStatus.OPEN);
        List<Todo> todos = List.of(todo1, todo2, todo3);

        when(todoService.retrieveAllTodos()).thenReturn(todos);

        // WHEN
        List<Todo> actual = todoService.retrieveAllTodos();

        // THEN
        List<Todo> expected = List.of(todo1, todo2, todo3);
        assertEquals(expected, actual);
    }



}