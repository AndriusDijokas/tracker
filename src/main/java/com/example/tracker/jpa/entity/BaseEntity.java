package com.example.tracker.jpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    @PrePersist
    private void onCreate() {
        dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        dateUpdated = LocalDateTime.now();
    }

}
