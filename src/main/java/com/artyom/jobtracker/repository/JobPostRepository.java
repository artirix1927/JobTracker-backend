package com.artyom.jobtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artyom.jobtracker.entity.JobPost;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {}