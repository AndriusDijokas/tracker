package com.example.tracker.jpa.repository;

import com.example.tracker.jpa.entity.TaskEntity;
import com.example.tracker.jpa.entity.enums.TaskType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedList;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Page<TaskEntity> getByType(TaskType type, Pageable pageable);

    LinkedList<TaskEntity> getByType(TaskType type);
}