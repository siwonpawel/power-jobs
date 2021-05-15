package com.github.siwonpawel.powerjobs.controller;


import com.github.siwonpawel.powerjobs.converter.PendingTaskToTaskResponseConverter;
import com.github.siwonpawel.powerjobs.domain.vo.fromfrontend.NewTaskBody;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.NewTaskResponse;
import com.github.siwonpawel.powerjobs.domain.vo.tofrontend.TaskResponse;
import com.github.siwonpawel.powerjobs.service.TaskService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@OpenAPIDefinition
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {

    @NonNull
    private final TaskService taskService;

    @NonNull
    private final PendingTaskToTaskResponseConverter toTaskResponse;

    @Tag(
            name = "Task: get all",
            description = "Returns all tasks that are present in application. " +
                    "It returns waiting for execution tasks, running and executed tasks"
    )
    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks().stream()
                .map(toTaskResponse::convert)
                .collect(Collectors.toList());
    }

    @Tag(name = "Task: add", description = "Create new task and schedule it for execution")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewTaskResponse addTask(@RequestBody NewTaskBody newTask) {
        return taskService.add(newTask);
    }

    @Tag(name = "Task: get task by id", description = "Get task identified by id. " +
            "Returns tasks from waiting for execution, running or executed pools")
    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable("id") Long id) throws EntityNotFoundException {
        return toTaskResponse.convert(taskService.getById(id));
    }

}
