package com.artyom.jobtracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artyom.jobtracker.entity.JobPost;
import com.artyom.jobtracker.entity.User;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    
    List<JobPost> findByTitleContainingIgnoreCase(String title);

    List<JobPost> findByPostedBy(User user);

}