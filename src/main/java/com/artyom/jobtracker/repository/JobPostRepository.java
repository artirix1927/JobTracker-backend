package com.artyom.jobtracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artyom.jobtracker.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    
    List<JobPost> findByTitleContainingIgnoreCase(String title);

    List<JobPost> findByPostedById(Long userId);

}