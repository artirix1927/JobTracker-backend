package com.artyom.jobtracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.artyom.jobtracker.entity.JobPost;
import com.artyom.jobtracker.entity.User;
import com.artyom.jobtracker.repository.JobPostRepository;

@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    public JobPostService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public JobPost createJobPost(JobPost post) {
        System.out.println(post);
        return jobPostRepository.save(post);
    }

    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }

    public List<JobPost> searchByTitle(String title) {
        return jobPostRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<JobPost> searchByCreator(User user) {
        return jobPostRepository.findByPostedBy(user);
    }
}
