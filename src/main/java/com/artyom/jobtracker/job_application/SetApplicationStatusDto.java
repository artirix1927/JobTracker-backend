package com.artyom.jobtracker.job_application;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SetApplicationStatusDto (
    @NotNull @Positive Long jobApplicationId,
    @NotBlank String newStatus
) {}