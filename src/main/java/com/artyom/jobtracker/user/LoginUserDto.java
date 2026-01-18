package com.artyom.jobtracker.user;


import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
    @NotBlank String email,
    @NotBlank String password
) {}