package com.artyom.jobtracker.job_post;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateJobPostRequest(
    @NotBlank String title,
    @NotBlank String company,
    @NotBlank String description,
    @NotNull @Positive BigDecimal salary,
    @NotBlank String location,
    @NotNull JobType jobType
) {}
