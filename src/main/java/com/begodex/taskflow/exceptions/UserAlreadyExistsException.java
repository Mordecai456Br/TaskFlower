package com.begodex.taskflow.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String login) {
        super("User " + login + " Already exists.");
    }
}
