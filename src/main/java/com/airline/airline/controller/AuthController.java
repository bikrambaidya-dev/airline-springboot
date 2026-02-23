package com.airline.airline.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.airline.airline.dto.request.LoginRequest;
import com.airline.airline.dto.request.RegisterRequest;
import com.airline.airline.dto.response.ApiResponse;
import com.airline.airline.service.AuthService;

/**
 * Authentication Controller with standardized response handling
 * All authentication endpoints return ApiResponse<T>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST - Register a new user
     * Returns 201 Created with user data and JWT token
     * Validation errors return 400 Bad Request (handled by GlobalExceptionHandler)
     * Duplicate email returns 409 Conflict (handled by GlobalExceptionHandler)
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    /**
     * POST - Login user
     * Returns 200 OK with JWT token and user data
     * Invalid credentials return 401 Unauthorized (handled by AuthService)
     * Invalid input returns 400 Bad Request (handled by GlobalExceptionHandler)
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
