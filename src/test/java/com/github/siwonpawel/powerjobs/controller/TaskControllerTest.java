package com.github.siwonpawel.powerjobs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.Task;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import com.github.siwonpawel.powerjobs.domain.vo.fromfrontend.NewTaskBody;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.NewTaskResponse;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.TaskResponse;
import com.github.siwonpawel.powerjobs.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private final ObjectMapper ob = new ObjectMapper();

    @Test
    void when_getAllTask_shouldConvertToTaskResponse() throws Exception {
        List<PendingTask> pendingTasks = List.of(
                new PendingTask(new Task(1L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), TaskStatus.FINISHED), 0),
                new PendingTask(new Task(2L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), TaskStatus.PREPARED), 0),
                new PendingTask(new Task(3L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), TaskStatus.RUNNING), 10)
        );
        when(taskService.getAllTasks()).thenReturn(pendingTasks);
        List<TaskResponse> response = List.of(
                new TaskResponse(1L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), "Finished", "100%"),
                new TaskResponse(2L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), "Prepared", null),
                new TaskResponse(3L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), "Running", "10%")
        );

        mockMvc.perform(
                get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON).content(ob.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    void when_addTask_shouldReturnNewTaskResponse() throws Exception {
        NewTaskBody newTaskBody = new NewTaskBody(BigInteger.valueOf(2), BigInteger.valueOf(3));
        NewTaskResponse newTaskResponse = new NewTaskResponse(1L);
        when(taskService.add(eq(newTaskBody)))
                .thenReturn(newTaskResponse);

        mockMvc.perform(post("/tasks", newTaskBody)
                .content(ob.writeValueAsString(newTaskResponse))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    void getTask() throws Exception {
        PendingTask pendingTask = new PendingTask(new Task(1L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), TaskStatus.FINISHED), 0);
        when(taskService.getById(eq(1L)))
                .thenReturn(pendingTask);
        TaskResponse finished = new TaskResponse(1L, BigInteger.valueOf(2), BigInteger.valueOf(10), BigInteger.valueOf(1024), "Finished", "100%");

        mockMvc.perform(
                    get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ob.writeValueAsString(finished)))
                .andExpect(status().isOk());
    }
}