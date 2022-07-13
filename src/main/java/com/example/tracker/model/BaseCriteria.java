package com.example.tracker.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Validated
@Data
@NoArgsConstructor
public class BaseCriteria {

    @Parameter(required = true)
    @Schema(defaultValue = "0")
    @Min(0)
    int page;
    @Schema(defaultValue = "10")
    @Parameter(required = true)
    @Min(0)
    int size;
}
