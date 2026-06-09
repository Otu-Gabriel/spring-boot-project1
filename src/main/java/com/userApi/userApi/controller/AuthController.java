package com.userApi.userApi.controller;

import com.userApi.userApi.dto.LoginRequestDTO;
import com.userApi.userApi.dto.LoginResponseDTO;
import com.userApi.userApi.dto.UserRequestDTO;
import com.userApi.userApi.dto.UserResponseDTO;
import com.userApi.userApi.response.ApiResponse;
import com.userApi.userApi.service.AuthService;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(
            @Valid @RequestBody UserRequestDTO dto
    ) {

        log.info("It hit the authController" );
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User registered successfully",
                        authService.register(dto)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>>
    login(
            @RequestBody LoginRequestDTO dto
    ) {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        authService.login(dto)
                )
        );
    }
}