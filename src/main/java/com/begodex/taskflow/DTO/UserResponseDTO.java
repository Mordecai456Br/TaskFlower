package com.begodex.taskflow.DTO;

import com.begodex.taskflow.models.user.User;

import java.math.BigDecimal;

public record UserResponseDTO(
        Long id,
        String login

) {
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getLogin()

        );
    }

}
