package com.example.tracker.service.interfaces;

import com.example.tracker.jpa.entity.DeveloperEntity;
import com.example.tracker.model.Developer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDeveloperService {
    void create(Developer developer);

    void update(Long id, Developer developer);

    DeveloperEntity getById(Long id);

    Page<Developer> get(Pageable pageable);

    List<DeveloperEntity> getAll();
}
