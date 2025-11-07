package com.begodex.taskflower.controllers;

import com.begodex.taskflower.DTO.UserResponseDTO;
import com.begodex.taskflower.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

// Controller responsável por endpoints relacionados a usuários
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.begodex.taskflower.services.UserService userService;

    @PostMapping("/{id}/promote-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> promoteToAdmin(@PathVariable Long id) {
        userService.promoteToAdmin(id);
        return ResponseEntity.ok().build(); // 200 se promovido
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }



}
