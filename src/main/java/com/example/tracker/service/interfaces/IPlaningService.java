package com.example.tracker.service.interfaces;

import com.example.tracker.model.planing.Week;

import java.util.List;

public interface IPlaningService {
    List<Week> plan(Boolean loadData);
}
