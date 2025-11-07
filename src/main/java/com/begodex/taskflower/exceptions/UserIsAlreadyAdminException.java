package com.begodex.taskflower.exceptions;

public class UserIsAlreadyAdminException extends RuntimeException {
    public UserIsAlreadyAdminException() {
        super("User is already ADMIN");
    }


}
