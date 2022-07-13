package com.example.tracker.model;

import com.example.tracker.validations.ValidString;


public record Developer(Long id,
                        @ValidString
                        String name) {

}
