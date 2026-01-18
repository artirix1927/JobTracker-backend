package com.artyom.jobtracker.job_application;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record JobApplicationCreateRequest(
    @NotNull @Positive Long jobPostId,
    @NotBlank String fullName,
    @NotBlank @Email String email,
    @NotBlank String phone,
    String address,
    MultipartFile resume // optional
) {}