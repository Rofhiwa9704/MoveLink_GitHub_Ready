package com.movelink.backend.controller;

import com.movelink.backend.dto.AuthResponse;
import com.movelink.backend.dto.LoginRequest;
import com.movelink.backend.dto.RegisterRequest;
import com.movelink.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/users")
public ResponseEntity<?> getUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
}

}