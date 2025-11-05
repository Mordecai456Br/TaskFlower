package com.begodex.taskflow.DTO;

import com.begodex.taskflow.models.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
