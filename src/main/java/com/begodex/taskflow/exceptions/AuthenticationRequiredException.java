package com.begodex.taskflow.exceptions;

public class AuthenticationRequiredException extends RuntimeException {
    public AuthenticationRequiredException() {
        super("Authentication required");
    }
}
