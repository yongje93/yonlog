package com.yonlog.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class YonlogException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public YonlogException() {
    }

    public YonlogException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

}
