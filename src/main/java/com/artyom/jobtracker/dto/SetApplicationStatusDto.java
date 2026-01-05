package com.artyom.jobtracker.dto;

import jakarta.validation.constraints.NotBlank;

public record SetApplicationStatusDto (
    @NotBlank Long jobApplicationId,
    @NotBlank String newStatus
) {}
