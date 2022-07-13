package com.example.tracker.model;

import org.springframework.http.ResponseEntity;

public enum GenericMessages {

    SUCCESSFULLY_CREATED("Successfully created"),
    SUCCESSFULLY_CHANGED("Successfully changed"),
    SUCCESSFULLY_ADDED("Successfully added"),
    SUCCESSFULLY_STARTED("Successfully started"),
    SUCCESSFULLY_REMOVED("Successfully removed"),
    SUCCESSFULLY_UPGRADE("Successfully removed"),
    SUCCESSFULLY_COLLECTED("Successfully collected");

    private final String message;

    GenericMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseEntity<GenericResponse> ok(GenericMessages messages){
        return ResponseEntity.ok(new GenericResponse(messages));
    }
}
