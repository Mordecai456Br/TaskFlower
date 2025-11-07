package com.begodex.taskflower.exceptions;

public class AuthenticationRequiredException extends RuntimeException {
    public AuthenticationRequiredException() {
        super("Authentication required");
    }
}
