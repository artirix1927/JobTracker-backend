package com.artyom.jobtracker.user;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService, TwoFactorAuthService twoFactorAuthService, User2FaRepository user2FaRepository) {
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

    @PostMapping("/verify-2fa")
    public AuthResponseDto verify2Fa(@Valid @RequestBody TwoFaRequestDto dto) {
        return userService.verify2Fa(
            dto.getUser(),
            dto.getCode()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(
            @Valid @RequestBody RefreshTokenDto request
    ) {
        return ResponseEntity.ok(
            userService.refreshAccessToken(request.refreshToken())
        );
    }

    @PostMapping("/enable-2fa")
    public Map<String, String> enable2FA(@AuthenticationPrincipal User user) {
        return this.userService.enable2Fa(user);
    }


    @PostMapping("/verify-2fa-setup")
    public ResponseEntity<Boolean> verify2FASetup(@AuthenticationPrincipal User user,
                                                  @Valid @RequestBody EnableTwoFactorDto dto) {
        boolean isCodeValid = this.userService.verify2FaSetup(user, dto.getCode());

        return ResponseEntity.ok(isCodeValid);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal User user) {
        userService.logout(user);
        return ResponseEntity.ok().build();
    }



}

