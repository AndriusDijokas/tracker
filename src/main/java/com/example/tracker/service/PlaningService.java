package com.example.tracker.service;

import com.example.tracker.datacache.CacheStore;
import com.example.tracker.jpa.entity.DeveloperEntity;
import com.example.tracker.jpa.entity.TaskEntity;
import com.example.tracker.jpa.entity.enums.StoryStatus;
import com.example.tracker.jpa.entity.enums.TaskType;
import com.example.tracker.model.Story;
import com.example.tracker.model.planing.Developer;
import com.example.tracker.model.planing.Task;
import com.example.tracker.model.planing.Week;
import com.example.tracker.service.interfaces.IDeveloperService;
import com.example.tracker.service.interfaces.IPlaningService;
import com.example.tracker.service.interfaces.ITaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class PlaningService implements IPlaningService {

    private final IDeveloperService developerService;
    private final ITaskService taskService;
    private final CacheStore<Developer> splintedTaskStorage;

    public static final Long MAX_DEVELOPER_POWER_FOR_WEEK = 10L;

    @Override
    public List<Week> plan(Boolean loadData) {

        if (loadData) loadTestData();

        /*Get data*/
        var tasks = taskService.getByType(TaskType.STORY);
        var developers = developerService.getAll();

        /*Sorting*/
        tasks = tasks.stream()
                .sorted(Comparator.comparing(TaskEntity::getStoryPoints).reversed())
                .collect(Collectors.toCollection(LinkedList::new));

        /*Constants needed for calculation*/
        var totalStoryPoints = tasks.stream().mapToLong(TaskEntity::getStoryPoints).sum();
        var totalAmountOfStoryPoint = developers.stream().count() * MAX_DEVELOPER_POWER_FOR_WEEK;
        var fullWeeks = (int) Math.ceil((double) totalStoryPoints / totalAmountOfStoryPoint);
        var nextWeek = getNextWeekOfTheYear();

        List<Week> pWeeks = new ArrayList<>();
        List<TaskEntity> assignedTasks = new ArrayList<>();

        var run = true;
        while (run) {
            if (fullWeeks <= 0) {
                run = false;
                break;
            }
            fullWeeks--;
            nextWeek++;

            var week = Week.builder().weekOfYear(nextWeek).build();

            List<Developer> planedDevelopers = new ArrayList<>();
            var iterableTasks = filterOutAssigned(tasks, assignedTasks);
            for (DeveloperEntity developer : developers) {
                var dev = Developer.builder().id(developer.getId()).build();
                iterableTasks = taskAssigment(iterableTasks, assignedTasks, dev, nextWeek);
                planedDevelopers.add(dev);
            }
            week.setDevelopers(planedDevelopers);
            pWeeks.add(week);
        }
        return pWeeks;
    }


    public LinkedList<TaskEntity> taskAssigment(LinkedList<TaskEntity> tasks, List<TaskEntity> assignedTasks, Developer developer, int week) {

        List<Task> pTasks = new ArrayList<>();
        LinkedList<TaskEntity> leftOver = new LinkedList<>();
        var collection = tasks.iterator();
        var developerPower = MAX_DEVELOPER_POWER_FOR_WEEK;
        while (collection.hasNext()) {

            var taskEntity = collection.next();
            var task = Task.builder().id(taskEntity.getId()).storyPoints(taskEntity.getStoryPoints()).build();
            var initDeveloperPower = developerPower;
            developerPower = developerPower - taskEntity.getStoryPoints();

            if (developerPower == 0L) {
                collection.remove();
                while (collection.hasNext()) {
                    leftOver.add(collection.next());
                }
                assignedTasks.add(taskEntity);
                pTasks.add(task);
                developer.setTasks(pTasks);
                return leftOver;
            } else if (developerPower < 0L) {

                if (MAX_DEVELOPER_POWER_FOR_WEEK < taskEntity.getStoryPoints()) {
                    //Todo: Split task
                } else {
                    //restore power
                    developerPower = initDeveloperPower;
                    leftOver.add(taskEntity);
                    continue;
                }
            } else {
                collection.remove();
                assignedTasks.add(taskEntity);
                pTasks.add(task);
            }
        }
        developer.setTasks(pTasks);
        return leftOver;
    }

    private int getNextWeekOfTheYear() {
        LocalDate date = LocalDate.now();
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        return date.get(woy);
    }

    /*Not finished*/
    private void splitAndSaveTask(TaskEntity task, Developer developer, int currentWeekOfTheYear, Long leftDeveloperPower) {
        var split = (int) Math.ceil((double) task.getStoryPoints() / MAX_DEVELOPER_POWER_FOR_WEEK);
        List<Task> tasks = new ArrayList<>();
        while (true) {
            if (split <= 0) {
                break;
            }
            split--;
            currentWeekOfTheYear++;
            task.setStoryPoints(task.getStoryPoints() - leftDeveloperPower);
            tasks.add(Task.builder()
                    .id(task.getId())
                    .storyPoints(task.getStoryPoints()).isSplinted(true)
                    .build());
        }
        //Todo if task is in more that 10 story points. Idea is to save to chase the splinted task and on next week assign accordingly.

        // splintedTaskStorage.add(currentWeekOfTheYear+developer.getId().toString(), Developer.builder().id(developer.getId()).tasks(tasks).build());
    }

    private LinkedList<TaskEntity> filterOutAssigned(LinkedList<TaskEntity> tasks, List<TaskEntity> assignedTasks) {
        return tasks.stream()
                .filter(t5 -> !assignedTasks.stream()
                        .anyMatch(task -> task.getId().equals(t5.getId()))).collect(Collectors.toCollection(LinkedList::new));
    }


    private void loadTestData() {

        developerService.create(new com.example.tracker.model.Developer(null, "Dijokas"));
        developerService.create(new com.example.tracker.model.Developer(null, "Pijokas"));

        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 3L, null));
        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 7L, null));
        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 10L, null));
        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 3L, null));
        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 3L, null));
        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 7L, null));
        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 10L, null));
        taskService.createStory(new Story(null, "title", "dasda", StoryStatus.NEW, TaskType.STORY, 3L, null));
    }

}
