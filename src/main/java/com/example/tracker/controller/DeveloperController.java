package com.example.tracker.controller;


import com.example.tracker.model.BaseCriteria;
import com.example.tracker.model.Developer;
import com.example.tracker.model.GenericMessages;
import com.example.tracker.model.GenericResponse;
import com.example.tracker.service.interfaces.IDeveloperService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//Todo all request mappings can be moved to enum.
//Todo can be returned CREATED, UPDATED responses for simplicity leaving OK.
//Todo skipping delete method.

@Tag(name = "Developer")
@RestController
@RequiredArgsConstructor
@RequestMapping("/developers")
public class DeveloperController {

    private final IDeveloperService developerService;

    @PostMapping()
    public ResponseEntity<GenericResponse> create(@RequestBody @Valid Developer developer) {
        developerService.create(developer);
        return GenericMessages.ok(GenericMessages.SUCCESSFULLY_CREATED);
    }

    @GetMapping()
    public ResponseEntity<Page<Developer>> get(@ParameterObject BaseCriteria baseCriteria) {
        return ResponseEntity.ok(developerService.get(PageRequest.of(baseCriteria.getPage(), baseCriteria.getSize())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") Long id, @RequestBody @Valid Developer developer) {
        developerService.update(id, developer);
        return GenericMessages.ok(GenericMessages.SUCCESSFULLY_CHANGED);
    }


}
