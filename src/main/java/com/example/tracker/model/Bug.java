package com.example.tracker.model;

import com.example.tracker.jpa.entity.enums.BugPriority;
import com.example.tracker.jpa.entity.enums.BugStatus;
import com.example.tracker.jpa.entity.enums.TaskType;
import com.example.tracker.validations.Enum;
import com.example.tracker.validations.ValidString;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

public record Bug(
        Long id,
        @ValidString
        String title,
        @ValidString
        String description,
        @Enum(enumClass = BugStatus.class)
        BugStatus status,
        @JsonIgnore
        TaskType type,
        @Enum(enumClass = BugPriority.class)
        BugPriority priority,
        @Min(value = 0L, message = "The value must be positive")
        Long storyPoints,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dateCreated
) {

    @Override
    public TaskType type() {
        return type;
    }
}
