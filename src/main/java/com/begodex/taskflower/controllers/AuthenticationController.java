package com.begodex.taskflower.controllers;

import com.begodex.taskflower.DTO.AuthenticationDTO;
import com.begodex.taskflower.DTO.LoginResponseDTO;
import com.begodex.taskflower.DTO.RegisterDTO;
import com.begodex.taskflower.infra.security.TokenService;
import com.begodex.taskflower.models.user.*;
import com.begodex.taskflower.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private com.begodex.taskflower.services.UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){

        var result = userService.register(data.login(), data.password(), data.role());

        if (result == null) {

            return ResponseEntity.badRequest().body("Login already exists");
        }

        return ResponseEntity.ok().build();
    }
}
