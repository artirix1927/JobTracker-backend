package com.artyom.jobtracker.user;

public record AuthResponseDto(String accessToken, String refreshToken) {}