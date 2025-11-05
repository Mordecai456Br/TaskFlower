package com.begodex.taskflow.exceptions;

public class UserIsAlreadyAdminException extends RuntimeException {
    public UserIsAlreadyAdminException() {
        super("User is already ADMIN");
    }


}
