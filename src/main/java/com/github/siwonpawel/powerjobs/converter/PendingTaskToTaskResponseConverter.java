package com.github.siwonpawel.powerjobs.converter;

import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.TaskResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class PendingTaskToTaskResponseConverter implements Converter<PendingTask, TaskResponse> {

    public static final String PROGRESS_100 = "100%";
    public static final String PROGRESS_FORMAT = "%d%%";

    @Override
    public TaskResponse convert(PendingTask source) {

        String progress = null;
        BigInteger result = null;

        if(TaskStatus.FINISHED.equals(source.getStatus())) {
            progress = PROGRESS_100;
            result = source.getResult();
        }

        if(TaskStatus.RUNNING.equals(source.getStatus())) {
            progress = String.format(PROGRESS_FORMAT, source.getProgress());
        }

        return TaskResponse.builder()
                .id(source.getId())
                .base(source.getBase())
                .exponent(source.getExponent())
                .result(result)
                .status(source.getStatus().getDisplayValue())
                .progress(progress)
                .build();
    }
}
