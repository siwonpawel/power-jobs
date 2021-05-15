package com.github.siwonpawel.powerjobs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import lombok.*;
import lombok.experimental.Delegate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PendingTask extends Task implements Runnable {

    public static final int TIMEOUT_MILLIS = 100;

    @JsonIgnore
    @Delegate(types = Task.class)
    private Task task;

    @Setter
    private Integer progress;

    public PendingTask(Task task) {
        this.task = task;
        this.progress = 0;
    }

    @Override
    public void run() {
        int exponent = task.getExponent().intValue();

        /*
            for loop for counting progress, normally I would use Math.pow() or BigInteger.pow()
            artificalWait() is made to make task seem to be 'heavly processing'
         */
        for(int i = 1; i <= exponent; i++) {
            artificalWait();
            this.progress = i * 100 / exponent;
            if(i == 1) {
                task.setResult(task.getBase());
                continue;
            }
            task.setResult(task.getResult().multiply(task.getBase()));
        }
    }

    private void artificalWait() {
        for(int i = 0; i < 100_000_000; i++) {

        }
    }

    public Task getTask() {
        return task;
    }

    public Integer getProgress() {
        if(TaskStatus.FINISHED.equals(task.getStatus())) {
            return 100;
        }

        return progress;
    }

    public PendingTask updateStatus() {
        switch (task.getStatus()) {
            case PREPARED -> setStatus(TaskStatus.RUNNING);
            case RUNNING -> setStatus(TaskStatus.FINISHED);
        }

        return this;
    }
}
