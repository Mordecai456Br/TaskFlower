package com.begodex.taskflow.services;

import com.begodex.taskflow.exceptions.AuthenticationRequiredException;
import com.begodex.taskflow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public static Long getAuthenticatedUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal == null || "anonymousUser".equals(principal.toString())) {
            throw new AuthenticationRequiredException();
        }

        try {
            return (Long) principal.getClass().getMethod("getId").invoke(principal);
        } catch (Exception e) {
            throw new RuntimeException("Unable to resolve authenticated user id", e);
        }
    }

}
