package com.artyom.jobtracker.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.artyom.jobtracker.entity.JobPost;
import com.artyom.jobtracker.repository.JobPostRepository;
import com.artyom.jobtracker.repository.JobPostSpecification;

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

    public Page<JobPost> getAllJobPosts(Pageable pageable) {
        return jobPostRepository.findAll(pageable);
    }

    public Page<JobPost> search(Optional<String> title, Optional<String> address, Pageable pageable) {
        Specification<JobPost> spec = (root, query, cb) -> cb.conjunction();
        
        if (title.isPresent() && !title.get().isBlank()) {
            spec = spec.and(JobPostSpecification.hasTitle(title.get()));
        }
        if (address.isPresent() && !address.get().isBlank()) {
            spec = spec.and(JobPostSpecification.hasAddress(address.get()));
        }

        return jobPostRepository.findAll(spec, pageable);
    }

    public Page<JobPost> getJobsByUser(Long userId, Pageable pageable) {
        return jobPostRepository.findByPostedById(userId, pageable);
    }
}
