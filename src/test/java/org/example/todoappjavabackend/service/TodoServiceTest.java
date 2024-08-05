package org.example.todoappjavabackend.service;

import org.example.todoappjavabackend.dto.NewTodoDto;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.model.TodoStatus;
import org.example.todoappjavabackend.repository.TodoRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    TodoRepo todoRepo = mock(TodoRepo.class);
    IdService idService = mock(IdService.class);
    TodoService todoService = new TodoService(todoRepo, idService);

    @Test
    public void retrieveAllTodosTest_whenNoTodosInDB_thenReturnEmptyList() {
        // GIVEN

        // WHEN
        List<Todo> actual = todoService.retrieveAllTodos();

        // THEN
        List<Todo> expected = new ArrayList<>();
        verify(todoRepo, times(1)).findAll();
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
        verify(todoRepo, times(1)).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveTodoById_whenTodoInDB_thenReturnTodo() {
        // GIVEN
        Todo todo1 = new Todo("123", "Cooking", TodoStatus.OPEN);
        Todo todo2 = new Todo("456", "Jogging", TodoStatus.DONE);
        List<Todo> todos = List.of(todo1, todo2);

        String id = "123";
        Todo retrievedTodo = todos.stream().filter(todo -> todo.id().equals(id)).toList().get(0);
        when(todoService.retrieveTodoById(id)).thenReturn(Optional.ofNullable(retrievedTodo));

        // WHEN
        Optional<Todo> actual = todoService.retrieveTodoById(id);

        // THEN
        assert retrievedTodo != null;
        Optional<Todo> expected = Optional.of(retrievedTodo);

        verify(todoRepo, times(1)).findById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveTodoById_whenNoTodoInDB_thenReturnNothing() {
        // GIVEN
        Todo todo1 = new Todo("123", "Cooking", TodoStatus.OPEN);
        Todo todo2 = new Todo("456", "Jogging", TodoStatus.DONE);
        List<Todo> todos = List.of(todo1, todo2);

        String id = "789";
        when(todoService.retrieveTodoById(id)).thenReturn(Optional.empty());

        // WHEN
        Optional<Todo> actual = todoService.retrieveTodoById(id);

        // THEN
        Optional<Todo> expected = Optional.empty();
        verify(todoRepo, times(1)).findById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void saveNewTodo_whenNewTodoAsInput_thenReturnNewTodo() {
        // GIVEN
        NewTodoDto newTodoDto = new NewTodoDto("Cooking");
        Todo todoToSave = new Todo(idService.randomId(), newTodoDto.description(), TodoStatus.OPEN);
        when(todoService.saveNewTodo(newTodoDto)).thenReturn(todoToSave);

        // WHEN
        Todo actual = todoService.saveNewTodo(newTodoDto);

        // THEN
        Todo expected = todoToSave;
        verify(todoRepo, times(1)).save(todoToSave);
        assertEquals(expected, actual);
    }
}