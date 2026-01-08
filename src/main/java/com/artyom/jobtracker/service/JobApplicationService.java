package com.artyom.jobtracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.artyom.jobtracker.dto.JobApplicationCreateRequest;
import com.artyom.jobtracker.entity.ApplicationStatus;
import com.artyom.jobtracker.entity.JobApplication;
import com.artyom.jobtracker.entity.JobPost;
import com.artyom.jobtracker.entity.User;
import com.artyom.jobtracker.repository.JobApplicationRepository;
import com.artyom.jobtracker.repository.JobPostRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobPostRepository jobPostRepository;

    public JobApplication createApplication(
            JobApplicationCreateRequest request,
            User user
    ) {
        Long jobPostId = request.jobPostId();
        
        boolean alreadyApplied = jobApplicationRepository.existsByUserIdAndJobPostId(user.getId(), jobPostId);
        if (alreadyApplied) {
            throw new IllegalArgumentException("You have already applied to this job.");
        }


        JobPost jobPost = jobPostRepository.findById(request.jobPostId())
                .orElseThrow(() -> new IllegalArgumentException("Job post not found"));

        JobApplication application = new JobApplication();
        application.setUser(user);
        application.setJobPost(jobPost);
        application.setFullName(request.fullName());
        application.setEmail(request.email());
        application.setPhone(request.phone());
        application.setAddress(request.address());

        if (request.resume() != null && !request.resume().isEmpty()) {
            try {
                Path uploadDir = Paths.get("uploads/resumes");
                Files.createDirectories(uploadDir);

                String filename = UUID.randomUUID() + "_" +
                        request.resume().getOriginalFilename();

                Path filePath = uploadDir.resolve(filename);
                Files.copy(request.resume().getInputStream(), filePath);

                application.setResumePath("resumes/" + filename);
                application.setResumeFilename(request.resume().getOriginalFilename());

            } catch (IOException e) {
                throw new RuntimeException("Failed to save resume file", e);
            }
        }

        return jobApplicationRepository.save(application);
    }


    public Page<JobApplication> getApplicationsForJob(Long jobPostId, int page, int size) {
        return jobApplicationRepository.findByJobPostId(
            jobPostId,
            PageRequest.of(page, size, Sort.by("id").descending())
        );
    }


    public JobApplication setJobApplicationStatus(Long jobApplicationId, String newStatus) {

        JobApplication application = jobApplicationRepository.findById(jobApplicationId)
        .orElseThrow(() -> new RuntimeException("Job Application Not Found"));


        application.setStatus(ApplicationStatus.valueOf(newStatus));


        return jobApplicationRepository.save(application);

    }

}
