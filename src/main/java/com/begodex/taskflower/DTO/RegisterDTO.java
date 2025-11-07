package com.begodex.taskflower.DTO;

import com.begodex.taskflower.models.user.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
