package com.artyom.jobtracker.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> refreshAccessToken(
            @Valid @RequestBody RefreshTokenDto request
    ) {
        return ResponseEntity.ok(
            userService.refreshAccessToken(request.refreshToken())
        );
    }



}

