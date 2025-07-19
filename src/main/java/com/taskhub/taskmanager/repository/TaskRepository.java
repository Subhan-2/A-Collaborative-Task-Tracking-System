package com.taskhub.taskmanager.repository;

import com.taskhub.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserid(Long userid);
}
