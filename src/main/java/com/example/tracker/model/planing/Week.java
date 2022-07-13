package com.example.tracker.model.planing;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
public class Week {
    int weekOfYear;
    List<Developer> developers;
}
