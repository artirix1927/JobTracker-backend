package com.artyom.jobtracker.job_post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface JobPostRepository
    extends JpaRepository<JobPost, Long>, JpaSpecificationExecutor<JobPost> {

    List<JobPost> findByTitleContainingIgnoreCase(String title);

    Page<JobPost> findByPostedById(Long userId, Pageable pageable);
}