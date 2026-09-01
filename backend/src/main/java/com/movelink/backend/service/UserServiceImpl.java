package com.movelink.backend.service;

import com.movelink.backend.dto.AuthResponse;
import com.movelink.backend.dto.LoginRequest;
import com.movelink.backend.dto.RegisterRequest;
import com.movelink.backend.entity.User;
import com.movelink.backend.enums.UserRole;
import com.movelink.backend.repository.UserRepository;
import com.movelink.backend.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(
                    null,
                    "Email already exists",
                    null,
                    null,
                    null
            );
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return new AuthResponse(
                    null,
                    "Phone number already exists",
                    null,
                    null,
                    null
            );
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.CUSTOMER)
                .build();

        userRepository.save(user);

        return new AuthResponse(
                null,
                "Registration successful",
                user.getRole().name(),
                user.getEmail(),
                user.getId()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return new AuthResponse(
                    null,
                    "User not found",
                    null,
                    null,
                    null
            );
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return new AuthResponse(
                    null,
                    "Incorrect password",
                    null,
                    null,
                    null
            );
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Login successful",
                user.getRole().name(),
                user.getEmail(),
                user.getId()
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}