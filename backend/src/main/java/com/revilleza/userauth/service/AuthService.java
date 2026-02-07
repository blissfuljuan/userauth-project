package com.revilleza.userauth.service;

import com.revilleza.userauth.dto.AuthResponse;
import com.revilleza.userauth.dto.LoginRequest;
import com.revilleza.userauth.dto.RegisterRequest;
import com.revilleza.userauth.model.User;
import com.revilleza.userauth.repository.UserRepository;
import com.revilleza.userauth.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public User register(RegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        String hashed = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getFirstname(), request.getLastname(), request.getMiddlename(), email, hashed);

        return userRepository.save(user);

    }

    public AuthResponse authenticate(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!user.isActive()) {
            throw new IllegalArgumentException("Account is disabled.");
        }

        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!matches) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token, "Bearer");
    }

    public void logout() {

    }
}
