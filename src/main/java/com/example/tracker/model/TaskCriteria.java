package com.example.tracker.model;

import com.example.tracker.jpa.entity.enums.TaskType;
import com.example.tracker.validations.Enum;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
public class TaskCriteria extends BaseCriteria {

    @Enum(enumClass = TaskType.class)
    TaskType type;
}
