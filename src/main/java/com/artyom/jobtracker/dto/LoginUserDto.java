package com.artyom.jobtracker.dto;


import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
    @NotBlank String email,
    @NotBlank String password
) {}