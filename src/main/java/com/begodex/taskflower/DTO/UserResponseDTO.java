package com.begodex.taskflower.DTO;

import com.begodex.taskflower.models.user.User;

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
