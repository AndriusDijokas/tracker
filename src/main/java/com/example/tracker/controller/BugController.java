package com.example.tracker.controller;


import com.example.tracker.jpa.entity.enums.TaskType;
import com.example.tracker.model.*;
import com.example.tracker.service.interfaces.ITaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Bugs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bugs")
public class BugController {

    private final ITaskService taskService;

    @PostMapping()
    public ResponseEntity<GenericResponse> create(@RequestBody @Valid Bug bug) {
        taskService.createBug(bug);
        return GenericMessages.ok(GenericMessages.SUCCESSFULLY_CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") Long id, @RequestBody @Valid Bug bug) {
        taskService.updateBug(id, bug);
        return GenericMessages.ok(GenericMessages.SUCCESSFULLY_CHANGED);
    }

    @GetMapping()
    public ResponseEntity<Page<Bug>> get(@ParameterObject BaseCriteria criteria) {
        return ResponseEntity.ok(taskService.get(PageRequest.of(criteria.getPage(), criteria.getSize()), TaskType.BUG));
    }

}
