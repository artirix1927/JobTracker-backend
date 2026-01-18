package com.artyom.jobtracker.user;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDto(
    @NotBlank String refreshToken
) {}