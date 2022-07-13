package com.example.tracker.controller;

import com.example.tracker.model.planing.Week;
import com.example.tracker.service.PlaningService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Planing")
@RestController
@RequiredArgsConstructor
@RequestMapping("/planing")
public class PlaningController {
    private final PlaningService planingService;

    @GetMapping("/plan")
    public List<Week> getPlan(@RequestParam(value = "loadData", defaultValue = "false") Boolean loadData){
        return planingService.plan(loadData);
    }
}
