package com.example.tracker.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorResponse extends ErrorResponse {
    private final List<FieldError> fieldErrors;

    public ValidationErrorResponse(ErrorStatus errorStatus, List<FieldError> fieldErrors) {
        super(errorStatus);
        this.fieldErrors = fieldErrors;
    }
}
