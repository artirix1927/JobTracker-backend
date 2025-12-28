package com.artyom.jobtracker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artyom.jobtracker.dto.AuthResponseDto;
import com.artyom.jobtracker.dto.LoginUserDto;
import com.artyom.jobtracker.dto.RefreshTokenDto;
import com.artyom.jobtracker.dto.RegisterUserDto;
import com.artyom.jobtracker.dto.UserResponseDto;
import com.artyom.jobtracker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody RegisterUserDto dto) {
        return userService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody LoginUserDto dto) {
        return userService.login(dto); 
    }

    @PostMapping("/refresh")
    public String refreshAccessToken(@RequestBody RefreshTokenDto request) {
        return userService.refreshAccessToken(request.refreshToken()); 
    }



}

