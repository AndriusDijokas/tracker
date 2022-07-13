package com.example.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class GenericResponse {
    String status;
    String message;

    public GenericResponse(GenericMessages genericMessages) {
        status = genericMessages.name();
        message = genericMessages.getMessage();
    }
}
