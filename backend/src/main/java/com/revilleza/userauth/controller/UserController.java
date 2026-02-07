package com.revilleza.userauth.controller;

import com.revilleza.userauth.dto.*;
import com.revilleza.userauth.model.User;
import com.revilleza.userauth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMiddlename(),
                user.getEmail()));
    }
}
