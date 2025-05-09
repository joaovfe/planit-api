package com.planit.api.configuration.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super("ValidationException error: " + message);
    }
}