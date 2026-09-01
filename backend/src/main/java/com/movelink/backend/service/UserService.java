package com.movelink.backend.service;

import com.movelink.backend.dto.AuthResponse;
import com.movelink.backend.dto.LoginRequest;
import com.movelink.backend.dto.RegisterRequest;
import com.movelink.backend.entity.User;

import java.util.List;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    List<User> getAllUsers();
}