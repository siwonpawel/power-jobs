package com.github.siwonpawel.powerjobs.service;

import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.Task;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import com.github.siwonpawel.powerjobs.domain.vo.fromfrontend.NewTaskBody;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.NewTaskResponse;
import com.github.siwonpawel.powerjobs.repository.InMemoryPendingTaskRepository;
import com.github.siwonpawel.powerjobs.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.siwonpawel.powerjobs.utils.ListMerger.merge;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskService {

    @NonNull
    private final TaskRepository taskRepo;

    @NonNull
    private final InMemoryPendingTaskRepository pendingTaskRepository;

    public List<PendingTask> getAllTasks() {
        List<PendingTask> allFinished = taskRepo.findAllByStatus(TaskStatus.FINISHED).stream()
                .map(PendingTask::new)
                .collect(Collectors.toList());

        return merge(allFinished, pendingTaskRepository.findAll());
    }

    public NewTaskResponse add(NewTaskBody newTask) {
        log.trace("Creating new task with data {}", newTask);
        Task saved = pendingTaskRepository.save(new Task(newTask.getBase(), newTask.getExponent()));
        return new NewTaskResponse(saved.getId());
    }

    public PendingTask getById(Long id) {
        return pendingTaskRepository.getById(id);
    }

    public void cleanup() {
        pendingTaskRepository.cleanup();
    }
}
