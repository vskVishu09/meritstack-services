package com.example.meritstack.controller;

import com.example.meritstack.exception.ResourceNotFoundException;
import com.example.meritstack.model.User;
import com.example.meritstack.repository.UserRepository;
import com.example.meritstack.security.CurrentUser;
import com.example.meritstack.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        List<String> list = new ArrayList<>();
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @GetMapping("/user/welcome")
    @PreAuthorize("hasRole('USER')")
    public String getWelcome() {
        return "This is welcome test";
    }
}
