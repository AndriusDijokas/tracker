package com.example.tracker.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public enum ErrorStatus {

    INCORRECT_REQUEST("INCORRECT_REQUEST"),

    DEVELOPER_NOT_FOUND("Developer not found"),
    TASK_NOT_FOUND("Task not found"),
    TASK_TYPE_NOT_FOUND("Task type not found");

    private final String message;

    public final GenericException getException() {
        return new GenericException(this);
    }

    public void throwException() {
        throw new GenericException(this);
    }
}
