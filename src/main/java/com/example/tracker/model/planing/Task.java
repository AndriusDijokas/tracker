package com.example.tracker.model.planing;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
public class Task {
    Long id;
    Long storyPoints;
    @Builder.Default
    Boolean isSplinted = false;
}
