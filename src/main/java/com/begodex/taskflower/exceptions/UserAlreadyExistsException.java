package com.begodex.taskflower.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String login) {
        super("User " + login + " Already exists.");
    }
}
