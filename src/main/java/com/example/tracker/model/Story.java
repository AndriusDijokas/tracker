package com.example.tracker.model;

import com.example.tracker.jpa.entity.enums.StoryStatus;
import com.example.tracker.jpa.entity.enums.TaskType;
import com.example.tracker.validations.Enum;
import com.example.tracker.validations.ValidString;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

public record Story(Long id,
                    @ValidString String title,
                    @ValidString String description,
                    @Enum(enumClass = StoryStatus.class) StoryStatus status,
                    @JsonIgnore TaskType type,
                    @Min(value = 0L, message = "The value must be positive") Long storyPoints,
                    @JsonIgnore
                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
                    LocalDateTime dateCreated
) {

}
