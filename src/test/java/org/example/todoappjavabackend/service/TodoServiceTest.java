package org.example.todoappjavabackend.service;

import org.example.todoappjavabackend.dto.NewTodoDto;
import org.example.todoappjavabackend.dto.UpdateTodoDto;
import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.model.TodoStatus;
import org.example.todoappjavabackend.repository.TodoRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    TodoRepo todoRepo = mock(TodoRepo.class);
    IdService idService = mock(IdService.class);
    TodoService todoService = new TodoService(todoRepo, idService);

    @Test
    public void retrieveAllTodosTest_whenNoTodosInDB_thenReturnEmptyList() {
        // GIVEN
        List<Todo> todos = new ArrayList<>();
        when(todoRepo.findAll()).thenReturn(todos);

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

        when(todoRepo.findAll()).thenReturn(todos);

        // WHEN
        List<Todo> actual = todoService.retrieveAllTodos();

        // THEN
        List<Todo> expected = List.of(todo1, todo2, todo3);
        verify(todoRepo, times(1)).findAll();
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveTodoByIdTest_whenTodoInDB_thenReturnTodo() {
        // GIVEN
        Todo todo1 = new Todo("123", "Cooking", TodoStatus.OPEN);
        Todo todo2 = new Todo("456", "Jogging", TodoStatus.DONE);
        List<Todo> todos = List.of(todo1, todo2);

        String id = "123";
        Todo retrievedTodo = todos.stream().filter(todo -> todo.id().equals(id)).toList().get(0);
        when(todoRepo.findById(id)).thenReturn(Optional.ofNullable(retrievedTodo));

        // WHEN
        Optional<Todo> actual = todoService.retrieveTodoById(id);

        // THEN
        assert retrievedTodo != null;
        Optional<Todo> expected = Optional.of(retrievedTodo);

        verify(todoRepo, times(1)).findById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void retrieveTodoByIdTest_whenNoTodoInDB_thenReturnNothing() {
        // GIVEN
        Todo todo1 = new Todo("123", "Cooking", TodoStatus.OPEN);
        Todo todo2 = new Todo("456", "Jogging", TodoStatus.DONE);
        List<Todo> todos = List.of(todo1, todo2);

        String id = "789";
        when(todoRepo.findById(id)).thenReturn(Optional.empty());

        // WHEN
        Optional<Todo> actual = todoService.retrieveTodoById(id);

        // THEN
        Optional<Todo> expected = Optional.empty();
        verify(todoRepo, times(1)).findById(id);
        assertEquals(expected, actual);
    }

    @Test
    public void saveNewTodoTest_whenNewTodoAsInput_thenReturnNewTodo() {
        // GIVEN
        NewTodoDto newTodoDto = new NewTodoDto("Cooking");
        Todo todoToSave = new Todo(idService.randomId(), newTodoDto.description(), TodoStatus.OPEN);
        when(todoRepo.save(todoToSave)).thenReturn(todoToSave);

        // WHEN
        Todo actual = todoService.saveNewTodo(newTodoDto);

        // THEN
        Todo expected = todoToSave;
        verify(todoRepo, times(1)).save(todoToSave);
        assertEquals(expected, actual);
    }

    @Test
    public void updateTodoByIdTest_whenTodoInDB_thenDeleteTodo() {
        // GIVEN
        Todo todo1 = new Todo("123", "Cooking", TodoStatus.OPEN);
        Todo todo2 = new Todo("456", "Jogging", TodoStatus.DONE);
        List<Todo> todos = List.of(todo1, todo2);

        String id = "456";

        UpdateTodoDto updateTodoDto = new UpdateTodoDto("Running", TodoStatus.IN_PROGRESS);
        Todo updatedTodo = new Todo(id, updateTodoDto.description(), updateTodoDto.status());

        when(todoRepo.findById(id)).thenReturn(Optional.of(todo2));
        when(todoRepo.save(updatedTodo)).thenReturn(updatedTodo);

        // WHEN
        Optional<Todo> actual = todoService.updateTodoById(id, updateTodoDto);

        // THEN
        Optional<Todo> expected = Optional.of(updatedTodo);
        verify(todoRepo, times(1)).findById(id);
        verify(todoRepo, times(1)).save(updatedTodo);
        assertEquals(expected, actual);
    }

    @Test
    public void deleteTodoByIdTest_whenTodoInDB_thenReturnVoid_DeleteTodo() {
        String id = "456";
        doNothing().when(todoRepo).deleteById(id);
        todoService.deleteTodoById(id);
        verify(todoRepo, times(1)).deleteById(id);
    }
}