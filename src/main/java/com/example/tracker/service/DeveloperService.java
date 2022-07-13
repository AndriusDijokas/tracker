package com.example.tracker.service;


import com.example.tracker.exception.ErrorStatus;
import com.example.tracker.jpa.entity.DeveloperEntity;
import com.example.tracker.jpa.entity.DeveloperRepository;
import com.example.tracker.model.Developer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DeveloperService implements com.example.tracker.service.interfaces.IDeveloperService {


    private final DeveloperRepository repository;


    @Override
    public void create(Developer developer){
        var developerEntity = DeveloperEntity.builder()
                .name(developer.name())
                .build();
        repository.save(developerEntity);
    }

    @Override
    public void update(Long id, Developer developer){
       var developerEntity = getById(id);
       developerEntity.setName(developer.name());
       repository.save(developerEntity);

    }

    @Override
    public DeveloperEntity getById(Long id){
        return repository.findById(id).orElseThrow(ErrorStatus.DEVELOPER_NOT_FOUND::getException);
    }

    @Override
    public Page<Developer> get(Pageable pageable){
        return repository.getAll(pageable).map(developerEntity -> new Developer(developerEntity.getId(), developerEntity.getName()));
    }

    @Override
    public List<DeveloperEntity> getAll(){
        return repository.findAll();
    }





}
