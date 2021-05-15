package com.github.siwonpawel.powerjobs.converter;

import com.github.siwonpawel.powerjobs.domain.PendingTask;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.siwonpawel.powerjobs.converter.PendingTaskToTaskResponseConverterTest.genericTask;

class TaskToPendingTaskConverterTest {

    TaskToPendingTaskConverter converter = new TaskToPendingTaskConverter();

    @Test
    public void when_taskFinished_shouldFillProgressWith100() {
        PendingTask pendingTask = genericTask(TaskStatus.FINISHED);

        PendingTask conversionResult = converter.convert(pendingTask);

        assertNotNull(conversionResult);
        assertEquals(100, conversionResult.getProgress());
    }

    @Test
    public void when_tashPrepared_shouldFillProgressWithNull() {
        PendingTask pendingTask = genericTask(TaskStatus.PREPARED);

        PendingTask conversionResult = converter.convert(pendingTask);

        assertNotNull(conversionResult);
        assertNull(conversionResult.getProgress());
    }
}