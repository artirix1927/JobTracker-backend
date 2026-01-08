package com.artyom.jobtracker.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artyom.jobtracker.dto.JobApplicationCreateRequest;
import com.artyom.jobtracker.dto.SetApplicationStatusDto;
import com.artyom.jobtracker.entity.JobApplication;
import com.artyom.jobtracker.entity.User;
import com.artyom.jobtracker.service.JobApplicationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/job-application")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping(
        value = "/create",
        consumes = "multipart/form-data"
    )
    public JobApplication apply(
            @AuthenticationPrincipal User user,
            @Valid @ModelAttribute JobApplicationCreateRequest request
    ) {
        return jobApplicationService.createApplication(request, user);
    }

    @GetMapping("/by-job-post")
    public Page<JobApplication> byJobPost(
        @RequestParam @NotNull @Positive Long jobPostId,
        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
        @RequestParam(defaultValue = "10") @Positive int size
    ) {
        return jobApplicationService.getApplicationsForJob(jobPostId, page, size);
    }

    @PostMapping(value = "/set-status")
    public JobApplication setStatus(@Valid @RequestBody SetApplicationStatusDto req){
        return jobApplicationService.setJobApplicationStatus(req.jobApplicationId(), req.newStatus());
    }




}
