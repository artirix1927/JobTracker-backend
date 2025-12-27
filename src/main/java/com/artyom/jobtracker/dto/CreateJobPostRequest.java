package com.artyom.jobtracker.dto;

import java.math.BigDecimal;

import com.artyom.jobtracker.entity.JobType;

public record CreateJobPostRequest(
    String title,
    String company,
    String description,
    BigDecimal salary,
    String location,
    JobType jobType
) {}
