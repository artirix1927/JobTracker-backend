package com.artyom.jobtracker.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.artyom.jobtracker.entity.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUserId(Long userId);

    Page<JobApplication> findByJobPostId(Long jobPostId, Pageable pageable);

    boolean existsByUserIdAndJobPostId(Long userId, Long jobPostId);

    
}