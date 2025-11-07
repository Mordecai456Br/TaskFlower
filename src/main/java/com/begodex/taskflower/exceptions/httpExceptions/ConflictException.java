package com.begodex.taskflower.exceptions.httpExceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
