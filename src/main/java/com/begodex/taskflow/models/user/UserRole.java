package com.begodex.taskflow.models.user;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    COLABORATOR("colaborator"),
    MANAGER("manager");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}


