package com.github.siwonpawel.powerjobs.converter;

import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.Task;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TaskToPendingTaskConverter implements Converter<Task, PendingTask> {
    @Override
    public PendingTask convert(@NonNull Task source) {
        return PendingTask.builder()
                .task(source)
                .progress(TaskStatus.FINISHED.equals(source.getStatus()) ? 100 : null)
                .build();
    }
}
