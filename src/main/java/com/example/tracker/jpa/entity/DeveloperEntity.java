package com.example.tracker.jpa.entity;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "developer")
public class DeveloperEntity extends BaseEntity {

    String name;

    @OneToMany(mappedBy = "developer")
    Set<TaskEntity> tasks;

}
