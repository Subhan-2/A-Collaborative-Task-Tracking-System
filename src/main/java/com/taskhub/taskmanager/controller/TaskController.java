package com.taskhub.taskmanager.controller;

import com.taskhub.taskmanager.dto.TaskRequest;
import com.taskhub.taskmanager.dto.TaskResponse;
import com.taskhub.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // Temporary hardcoded userId (replace with authenticated user ID)
    private final Long hardcodedUserId = 1L;

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest request) {
        return taskService.createTask(request, hardcodedUserId);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks(hardcodedUserId);
    }

    @PutMapping("/{id}/complete")
    public void markTaskAsCompleted(@PathVariable Long id) {
        taskService.markTaskAsCompleted(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
