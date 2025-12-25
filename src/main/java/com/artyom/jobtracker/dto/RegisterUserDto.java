package com.artyom.jobtracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(
    @NotBlank String name,
    @Email String email,
    @NotBlank String password
) {}