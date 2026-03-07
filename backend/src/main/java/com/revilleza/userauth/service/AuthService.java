package com.revilleza.userauth.service;

import com.revilleza.userauth.dto.AuthResponse;
import com.revilleza.userauth.dto.LoginRequest;
import com.revilleza.userauth.dto.RegisterRequest;
import com.revilleza.userauth.model.User;
import com.revilleza.userauth.model.UserRole;
import com.revilleza.userauth.repository.UserRepository;
import com.revilleza.userauth.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        User user = new User(request.getFirstname(), request.getLastname(), request.getMiddlename(), email, hashed, request.getRole());

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

    public AuthResponse authenticateWithGoogleOAuth2User(OAuth2User oAuth2User) {
        String email = toStringValue(oAuth2User.getAttribute("email")).trim().toLowerCase();
        if (email.isBlank() ) {
            throw new IllegalArgumentException("Google account is unavailable.");
        }

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            String firstname = toStringValue(oAuth2User.getAttribute("given_name"));
            if(firstname.isBlank())
                firstname = "Google";

            String lastname = toStringValue(oAuth2User.getAttribute("family_name"));
            if (lastname.isBlank())
                lastname = "User";

            User newUser = new User(
                    firstname,
                    lastname,
                    "",
                    email,
                    passwordEncoder.encode(UUID.randomUUID().toString()),
                    UserRole.USER
            );

            return userRepository.save(newUser);
        });

        if (!user.isActive()) {
            throw new IllegalArgumentException("Accound is disabled");
        }

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token, "Bearer");
    }

    public void logout() {

    }

    private String toStringValue(Object value) {
        return value == null ? "" : value.toString();
    }
}