package com.github.siwonpawel.powerjobs.async.listener;

import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.repository.InMemoryPendingTaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@Log4j2
@Component
@RequiredArgsConstructor
public class AsyncTaskExecutorListener implements Runnable {

    @NonNull
    private final InMemoryPendingTaskRepository pendingTaskRepo;

    @NonNull
    private final Executor taskExecutor;

    @PostConstruct
    public void initListener() {
        log.debug("Starting listener");
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                log.trace("Waiting for new task");
                pendingTaskRepo.getTask()
                        .ifPresent(this::executeTask);
            } catch(Exception e) {
                log.error("Executing task throws an error: {}", e.getMessage());
            }
        }
    }

    private void executeTask(PendingTask task) {
        taskExecutor.execute(() -> {
            log.trace("Start of execution {}", task);
            pendingTaskRepo.update(task.updateStatus());
            task.run();
            pendingTaskRepo.update(task.updateStatus());
        });
    }
}
