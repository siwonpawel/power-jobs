package com.github.siwonpawel.powerjobs.repository;

import com.github.siwonpawel.powerjobs.converter.TaskToPendingTaskConverter;
import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.Task;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.siwonpawel.powerjobs.utils.ListMerger.merge;

@Log4j2
@Component
@RequiredArgsConstructor
public class InMemoryPendingTaskRepository {

    private final TaskRepository taskRepo;
    private final TaskToPendingTaskConverter taskConverter;

    List<PendingTask> pendingTasks = Collections.synchronizedList(new ArrayList<>());
    LinkedBlockingQueue<PendingTask> preparedTasks = new LinkedBlockingQueue<>();

    @PostConstruct
    public void init() {
        preparedTasks = taskRepo.findAllByStatusIsNot(TaskStatus.FINISHED)
                .stream()
                .map(taskConverter::convert)
                .collect(Collectors.toCollection(LinkedBlockingQueue::new));
    }

    public List<PendingTask> findAll() {
        return merge(pendingTasks, preparedTasks);
    }

    public Optional<PendingTask> getTask() {
        try {
            PendingTask take = preparedTasks.take();
            pendingTasks.add(take);
            return Optional.of(take);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Synchronized
    public void update(final PendingTask pendingTask) {
        if(TaskStatus.FINISHED.equals(pendingTask.getStatus())) {
            pendingTasks.remove(pendingTask);
        }

        taskRepo.save(pendingTask.getTask());
    }

    @Synchronized
    public PendingTask save(final Task newTask) {
        Task savedTask = taskRepo.save(newTask);
        PendingTask preparedTask = new PendingTask(savedTask);
        preparedTasks.add(preparedTask);
        return preparedTask;
    }

    public PendingTask getById(Long id) {
        return Stream.concat(pendingTasks.stream(), preparedTasks.stream())
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseGet(() ->
                        taskRepo.findById(id)
                                .map(PendingTask::new)
                                .orElseThrow(() -> {
                                    log.error("Task with id = {} not found", id);
                                    return new EntityNotFoundException();
                                })
                );
    }

    public void cleanup() {
        preparedTasks = new LinkedBlockingQueue<>();
        pendingTasks = Collections.synchronizedList(new ArrayList<>());
    }
}
