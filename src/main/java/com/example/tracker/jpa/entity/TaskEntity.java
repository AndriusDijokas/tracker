package com.example.tracker.jpa.entity;


import com.example.tracker.jpa.entity.enums.BugPriority;
import com.example.tracker.jpa.entity.enums.TaskType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Objects;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "task")
public class TaskEntity extends BaseEntity {
    String title;
    String description;
    @Enumerated(EnumType.STRING)
    TaskType type;
    String status;
    @Enumerated
    BugPriority priority;
    Long storyPoints;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    DeveloperEntity developer;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskEntity)) return false;
        TaskEntity entity = (TaskEntity) o;
        return Objects.equals(getTitle(), entity.getTitle()) && Objects.equals(getDescription(), entity.getDescription()) && getType() == entity.getType() && Objects.equals(getStatus(), entity.getStatus()) && getPriority() == entity.getPriority() && Objects.equals(getStoryPoints(), entity.getStoryPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getType(), getStatus(), getPriority(), getStoryPoints());
    }
}
