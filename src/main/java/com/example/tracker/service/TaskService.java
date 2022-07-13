package com.example.tracker.service;

import com.example.tracker.exception.ErrorStatus;
import com.example.tracker.jpa.entity.TaskEntity;
import com.example.tracker.jpa.entity.enums.BugStatus;
import com.example.tracker.jpa.entity.enums.StoryStatus;
import com.example.tracker.jpa.entity.enums.TaskType;
import com.example.tracker.jpa.repository.TaskRepository;
import com.example.tracker.model.Bug;
import com.example.tracker.model.Story;
import com.example.tracker.service.interfaces.IDeveloperService;
import com.example.tracker.service.interfaces.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import static com.example.tracker.jpa.entity.enums.TaskType.BUG;
import static com.example.tracker.jpa.entity.enums.TaskType.STORY;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {


    private final TaskRepository repository;

    private final IDeveloperService developerService;


    private void create(TaskEntity taskEntity) {
        repository.save(taskEntity);
    }

    private void update(TaskEntity taskEntity) {
        repository.save(taskEntity);
    }

    @Override
    public void createBug(Bug bug) {
        create(TaskEntity.builder()
                .type(bug.type())
                .storyPoints(bug.storyPoints())
                .description(bug.description())
                .title(bug.title())
                .status(BugStatus.NEW.name())
                .type(BUG)
                .priority(bug.priority())
                .build());
    }

    @Override
    public void createStory(Story story) {
        create(TaskEntity.builder()
                .type(story.type())
                .storyPoints(story.storyPoints())
                .description(story.description())
                .title(story.title())
                .type(STORY)
                .status(StoryStatus.NEW.name())
                .build());
    }

    @Override
    public void updateStory(Long id, Story story) {
        var taskEntity = getById(id);
        taskEntity.setDescription(story.description());
        taskEntity.setStatus(story.status().name());
        taskEntity.setTitle(story.title());
        taskEntity.setStoryPoints(story.storyPoints());
        update(taskEntity);
    }

    @Override
    public void updateBug(Long id, Bug bug) {
        var taskEntity = getById(id);
        taskEntity.setDescription(bug.description());
        taskEntity.setStatus(bug.status().name());
        taskEntity.setTitle(bug.title());
        taskEntity.setStoryPoints(bug.storyPoints());
        taskEntity.setPriority(bug.priority());
        update(taskEntity);
    }

    @Override
    public void delete(Long id) {
        var taskEntity = getById(id);
        repository.delete(taskEntity);
    }

    @Override
    public void assignDeveloper(Long taskId, Long developerId) {
        var task = getById(taskId);
        var developer = developerService.getById(developerId);
        task.setDeveloper(developer);
        developer.setTasks(Set.of(task));
        repository.save(task);

    }

    @Override
    public void removeDeveloper(Long taskId, Long developerId) {
        var task = getById(taskId);
        var developerEntity = Optional.ofNullable(task.getDeveloper());

        developerEntity.ifPresentOrElse(developer -> {
                    if (developer.getId().compareTo(developerId) == 0) {
                        task.setDeveloper(null);
                        repository.save(task);
                    }
                },
                ErrorStatus.DEVELOPER_NOT_FOUND::throwException);

    }

    private TaskEntity getById(Long id) {
        return repository.findById(id).orElseThrow(ErrorStatus.TASK_NOT_FOUND::getException);
    }

    @Override
    public <T> T get(Pageable pageable, TaskType type) {
        switch (type) {
            case BUG:
                return getBugs(pageable);
            case STORY:
                return getStories(pageable);
            default:
                ErrorStatus.TASK_TYPE_NOT_FOUND.throwException();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T extends Page<Bug>> T getBugs(Pageable pageable) {
        return (T) repository.getByType(BUG, pageable).map(bug ->
                new Bug(bug.getId(),
                        bug.getTitle(),
                        bug.getDescription(),
                        BugStatus.valueOf(bug.getStatus()),
                        bug.getType(),
                        bug.getPriority(),
                        bug.getStoryPoints(),
                        bug.getDateCreated())
        );
    }

    @SuppressWarnings("unchecked")
    private <T extends Page<Story>> T getStories(Pageable pageable) {
        return (T) repository.getByType(STORY, pageable).map(story ->
                new Story(
                        story.getId(),
                        story.getTitle(),
                        story.getDescription(),
                        StoryStatus.valueOf(story.getStatus()),
                        story.getType(),
                        story.getStoryPoints(),
                        story.getDateCreated())
        );
    }

    @Override
    public LinkedList<TaskEntity> getByType(TaskType type){
        return repository.getByType(type);
    }


}
