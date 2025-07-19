package com.taskhub.taskmanager.dto;

import lombok.Data;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private String status; // Added status field
}
