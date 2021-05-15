package com.github.siwonpawel.powerjobs.converter;

import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.Task;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.TaskResponse;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;


class PendingTaskToTaskResponseConverterTest {

    PendingTaskToTaskResponseConverter converter = new PendingTaskToTaskResponseConverter();

    @Test
    public void when_taskPrepared_shouldNotFillResultAndProgress() {
        PendingTask pendingTask = genericTask(TaskStatus.PREPARED);

        TaskResponse conversionResult = converter.convert(pendingTask);

        assertNotNull(conversionResult);
        assertNotNull(conversionResult.getId());
        assertNotNull(conversionResult.getBase());
        assertNotNull(conversionResult.getExponent());
        assertNotNull(conversionResult.getStatus());
        assertNull(conversionResult.getProgress());
        assertNull(conversionResult.getResult());
    }

    @Test
    public void when_taskRunning_shouldFillProgress() {
        PendingTask pendingTask = genericTask(TaskStatus.RUNNING);

        TaskResponse conversionResult = converter.convert(pendingTask);

        assertNotNull(conversionResult);
        assertNotNull(conversionResult.getId());
        assertNotNull(conversionResult.getBase());
        assertNotNull(conversionResult.getExponent());
        assertNotNull(conversionResult.getStatus());
        assertNotNull(conversionResult.getProgress());
        assertEquals(conversionResult.getProgress(), "1%");
        assertNull(conversionResult.getResult());
    }

    @Test
    public void when_tashFinished_shouldFillAllFields() {
        PendingTask pendingTask = genericTask(TaskStatus.FINISHED);

        TaskResponse conversionResult = converter.convert(pendingTask);

        assertNotNull(conversionResult);
        assertNotNull(conversionResult.getId());
        assertNotNull(conversionResult.getBase());
        assertNotNull(conversionResult.getExponent());
        assertNotNull(conversionResult.getStatus());
        assertNotNull(conversionResult.getProgress());
        assertEquals(conversionResult.getProgress(), "100%");
        assertNotNull(conversionResult.getResult());
        assertEquals(BigInteger.valueOf(128), conversionResult.getResult());
    }

    static PendingTask genericTask(TaskStatus status) {
        return generateTask(2, 10, 128, 1, status);
    }

    static PendingTask generateTask(int base, int exponent, int result, int progress, TaskStatus status) {

        Task task = new Task(BigInteger.valueOf(base), BigInteger.valueOf(exponent));
        task.setResult(BigInteger.valueOf(result));
        task.setStatus(status);
        task.setId(1L);

        PendingTask pendingTask = new PendingTask(task);
        pendingTask.setProgress(progress);
        return pendingTask;
    }

}