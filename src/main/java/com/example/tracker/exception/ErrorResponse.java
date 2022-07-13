package com.example.tracker.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final String message;
    private final String code;

    public ErrorResponse(ErrorStatus errorStatus) {
        this(errorStatus.getMessage(), errorStatus.name());
    }

    public ErrorResponse(GenericException e) {
        this(e.getMessage(), e.getCode());
    }
}
