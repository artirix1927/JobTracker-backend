package com.artyom.jobtracker.dto;

import org.springframework.web.multipart.MultipartFile;

public record JobApplicationCreateRequest(
    Long jobPostId,
    String fullName,
    String email,
    String phone,
    String address,
    MultipartFile resume // optional
) {}