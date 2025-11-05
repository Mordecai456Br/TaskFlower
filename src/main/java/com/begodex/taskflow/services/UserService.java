package com.begodex.taskflow.services;

import com.begodex.taskflow.DTO.UserResponseDTO;
import com.begodex.taskflow.exceptions.EntityNotFoundException;
import com.begodex.taskflow.exceptions.UserAlreadyExistsException;
import com.begodex.taskflow.exceptions.UserIsAlreadyAdminException;
import com.begodex.taskflow.models.user.User;
import com.begodex.taskflow.models.user.UserRole;
import com.begodex.taskflow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User", id));

        return new UserResponseDTO(user);

    }

    @Transactional
    public User register(String login, String rawPassword, UserRole requestedRole) {

        // verifica se o user ja existe
        if (userRepository.findByLogin(login) != null) {
            throw new UserAlreadyExistsException(login);
        }

        // decide role: se count == 0 -> ADMIN, senão USER (ignora requestedRole para segurança)
        UserRole assignedRole = (userRepository.count() == 0) ? UserRole.ADMIN : UserRole.USER;

        // cria entidade com senha criptografada e balance default (construtor já coloca 200)
        String encrypted = passwordEncoder.encode(rawPassword);
        User user = new User(login, encrypted, assignedRole);

        return userRepository.save(user);

    }

    @Transactional
    public User promoteToAdmin(Long userId) {

        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) throw new EntityNotFoundException("User", userId);

        User user = userOptional.get();

        if (user.getRole() == UserRole.ADMIN) {

            throw new UserIsAlreadyAdminException();
        }

        user.setRole(UserRole.ADMIN);
        return userRepository.save(user);
    }
}
