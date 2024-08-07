package org.example.todoappjavabackend;

import org.example.todoappjavabackend.model.Todo;
import org.example.todoappjavabackend.model.TodoStatus;
import org.example.todoappjavabackend.repository.TodoRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepo todoRepo;

    @Test
    @DirtiesContext
    void getAllTodosTest_whenRepoIsEmpty_thenReturnEmptyList() {
        try {
            mockMvc.perform(get("/api/todo"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DirtiesContext
    void getAllTodosTest_whenRepoHasTodos_thenReturnAllTodos() {
        // GIVEN
        todoRepo.save(new Todo("123", "Cooking", TodoStatus.OPEN));
        todoRepo.save(new Todo("456", "Jogging", TodoStatus.IN_PROGRESS));

        try {
            // WHEN
            mockMvc.perform(get("/api/todo"))
                    // THEN
                    .andExpect(status().isOk())
                    .andExpect(content().json("""
                       [
                         {
                           "id": "123",
                           "description": "Cooking",
                           "status": "OPEN"
                         },
                         {
                           "id": "456",
                           "description": "Jogging",
                           "status": "IN_PROGRESS"
                         }
                       ]
                     """));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DirtiesContext
    void getTodoByIdTest_whenTodoWithIdExists_thenReturnTodo() throws Exception {
        // GIVEN
        todoRepo.save(new Todo("123", "Cooking", TodoStatus.IN_PROGRESS));

        // WHEN
        mockMvc.perform(get("/api/todo/123"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                   {
                     "id": "123",
                     "description": "Cooking",
                     "status": "IN_PROGRESS"
                   }
                 """));
    }

    @Test
    @DirtiesContext
    void getTodoByIdTest_whenTodoWithIdDoesNotExist_thenThrowErrorMessage() throws Exception {
        // GIVEN
        todoRepo.save(new Todo("123", "Cooking", TodoStatus.IN_PROGRESS));

        // WHEN
        mockMvc.perform(get("/api/todo/456"))
                // THEN
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoSuchElementException))
                .andExpect(result -> assertEquals("No value present", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DirtiesContext
    void saveTodoTest_whenNewTodoExists_thenReturnNewTodoWithRandomIdAndOpenStatus() throws Exception {
        // GIVEN

        // WHEN
        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "description": "Cooking"
                    }
                 """))
                // THEN
                .andDo(print())
                .andExpect(status().isOk())
                /*.andExpect(content().json("""
                   {
                     "id": "123",
                     "description": "Cooking",
                     "status": "OPEN"
                   }
                """))*/
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value("Cooking"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

}
