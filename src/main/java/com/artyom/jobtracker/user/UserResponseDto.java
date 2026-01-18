package com.artyom.jobtracker.user;


public record UserResponseDto(
    Long id,
    String name,
    String email
) {}