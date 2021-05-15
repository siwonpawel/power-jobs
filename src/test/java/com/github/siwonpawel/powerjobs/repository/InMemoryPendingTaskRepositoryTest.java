package com.github.siwonpawel.powerjobs.repository;

import com.github.siwonpawel.powerjobs.async.listener.AsyncTaskExecutorListener;
import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.Task;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import com.github.siwonpawel.powerjobs.service.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class InMemoryPendingTaskRepositoryTest {

    @MockBean
    AsyncTaskExecutorListener asyncTaskExecutorListener;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    InMemoryPendingTaskRepository inMemoryPendingTaskRepository;

    @Autowired
    TaskService taskService;

    @AfterEach
    void cleanupMemoryRepo() {
        inMemoryPendingTaskRepository.cleanup();
    }

    @Test
    void whenFindAll_shouldReturnFromMemoryAndFromDatabase() {
        Task task1 = new Task(BigInteger.valueOf(2), BigInteger.valueOf(10));
        task1.setStatus(TaskStatus.FINISHED);
        inMemoryPendingTaskRepository.save(task1);
        Task task2 = new Task(BigInteger.valueOf(2), BigInteger.valueOf(10));
        inMemoryPendingTaskRepository.cleanup();
        Task task3 = new Task(BigInteger.valueOf(2), BigInteger.valueOf(10));
        inMemoryPendingTaskRepository.save(task3);

        List<PendingTask> all = taskService.getAllTasks();

        assertThat(all)
                .hasSize(2);
    }

    @Test
    void whenCreateNew_shouldSaveToDBAndMemoryRepository() {
        Task task = new Task(BigInteger.valueOf(2), BigInteger.valueOf(10));

        PendingTask saved = inMemoryPendingTaskRepository.save(task);
        PendingTask fromMemory = inMemoryPendingTaskRepository.getById(saved.getId());
        Optional<Task> byId = taskRepository.findById(saved.getId());

        assertThat(saved)
                .isNotNull()
                .isEqualTo(fromMemory);
        assertThat(byId)
                .isPresent();
    }

    @Test
    void whenGetById_shouldReturnFromInMemoryRepository() {
        Task task = new Task(BigInteger.valueOf(2), BigInteger.valueOf(10));

        PendingTask saved = inMemoryPendingTaskRepository.save(task);
        PendingTask fromMemory = inMemoryPendingTaskRepository.getById(saved.getId());

        assertThat(saved)
                .isNotNull()
                .isEqualTo(fromMemory);
    }

    @Test
    void whenGetById_shouldReturnDatabaseRepository() {
        Task task = new Task(BigInteger.valueOf(2), BigInteger.valueOf(10));

        PendingTask saved = inMemoryPendingTaskRepository.save(task);
        inMemoryPendingTaskRepository.cleanup();
        PendingTask fromMemory = inMemoryPendingTaskRepository.getById(saved.getId());

        assertThat(saved)
                .isNotNull()
                .isEqualTo(fromMemory);
    }
}