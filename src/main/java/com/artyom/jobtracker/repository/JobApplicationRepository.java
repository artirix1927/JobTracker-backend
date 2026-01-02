package com.artyom.jobtracker.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artyom.jobtracker.entity.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);

    List<JobApplication> findByJobPostId(Long jobPostId);

    
}