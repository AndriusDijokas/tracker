package com.example.tracker.service.interfaces;

import com.example.tracker.jpa.entity.TaskEntity;
import com.example.tracker.jpa.entity.enums.TaskType;
import com.example.tracker.model.Bug;
import com.example.tracker.model.Story;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;

public interface ITaskService {


    void createBug(Bug bug);

    void createStory(Story story);

    void updateStory(Long id, Story story);

    void updateBug(Long id, Bug bug);

    void delete(Long id);

    void assignDeveloper(Long taskId, Long developerId);

    void removeDeveloper(Long taskId, Long developerId);

    <T> T get(Pageable pageable, TaskType type);

    LinkedList<TaskEntity> getByType(TaskType type);
}
