package com.github.siwonpawel.powerjobs.repository;

import com.github.siwonpawel.powerjobs.domain.Task;
import com.github.siwonpawel.powerjobs.domain.dictionary.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByStatusIsNot(TaskStatus status);

    List<Task> findAllByStatus(TaskStatus status);

}
