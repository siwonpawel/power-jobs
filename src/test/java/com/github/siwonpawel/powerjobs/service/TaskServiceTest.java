package com.github.siwonpawel.powerjobs.service;

import com.github.siwonpawel.powerjobs.async.listener.AsyncTaskExecutorListener;
import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import com.github.siwonpawel.powerjobs.domain.vo.fromfrontend.NewTaskBody;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.NewTaskResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskServiceTest {

    // stop executing tasks
    @MockBean
    AsyncTaskExecutorListener asyncTaskExecutorListener;

    @Autowired
    TaskService taskService;

    @AfterEach
    void cleanup() {
        taskService.cleanup();
    }

    @Test
    void when_addedTask_findAll_returnsIt() {
        NewTaskBody newTaskBody = new NewTaskBody(BigInteger.valueOf(2), BigInteger.valueOf(10));
        taskService.add(newTaskBody);

        List<PendingTask> allTasks = taskService.getAllTasks();

        assertThat(allTasks).isNotNull().hasSize(1);
        PendingTask pendingTask = allTasks.get(0);
        assertNotNull(pendingTask);
        assertNotNull(pendingTask.getBase());
        assertEquals(BigInteger.valueOf(2), pendingTask.getBase());
        assertNotNull(pendingTask.getExponent());
        assertEquals(BigInteger.valueOf(10), pendingTask.getExponent());
        assertEquals(TaskStatus.PREPARED, pendingTask.getStatus());
    }

    @Test
    void getAllTasks() {
        NewTaskBody newTask1 = new NewTaskBody(BigInteger.valueOf(2), BigInteger.valueOf(10));
        NewTaskBody newTask2 = new NewTaskBody(BigInteger.valueOf(2), BigInteger.valueOf(10));
        NewTaskBody newTask3 = new NewTaskBody(BigInteger.valueOf(2), BigInteger.valueOf(10));
        taskService.add(newTask1);
        taskService.add(newTask2);
        taskService.add(newTask3);


    }

    @Test
    void when_addedTask_findById_returnsIt() {
        NewTaskBody newTaskBody = new NewTaskBody(BigInteger.valueOf(2), BigInteger.valueOf(10));
        NewTaskResponse addResponse = taskService.add(newTaskBody);

        PendingTask addedTask = taskService.getById(addResponse.getId());

        assertNotNull(addedTask);
        assertNotNull(addedTask.getBase());
        assertEquals(BigInteger.valueOf(2), addedTask.getBase());
        assertNotNull(addedTask.getExponent());
        assertEquals(BigInteger.valueOf(10), addedTask.getExponent());
        assertEquals(TaskStatus.PREPARED, addedTask.getStatus());
    }
}