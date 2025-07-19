package com.taskhub.taskmanager.service;

import com.taskhub.taskmanager.dto.TaskRequest;
import com.taskhub.taskmanager.dto.TaskResponse;
import com.taskhub.taskmanager.entity.Task;
import com.taskhub.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskRequest request, Long userId) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .userid(userId)
                .build();

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public List<TaskResponse> getAllTasks(Long userId) {
        return taskRepository.findByUserid(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void markTaskAsCompleted(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus("completed");
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    private TaskResponse mapToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
