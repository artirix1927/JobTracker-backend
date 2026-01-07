package com.artyom.jobtracker.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artyom.jobtracker.dto.CreateJobPostRequest;
import com.artyom.jobtracker.entity.JobPost;
import com.artyom.jobtracker.entity.User;
import com.artyom.jobtracker.service.JobPostService;


@RestController
@RequestMapping("/api/job-posts")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    @PostMapping("/create")
    public JobPost createJobPost(@RequestBody CreateJobPostRequest req) {

        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        JobPost post = new JobPost();
        post.setTitle(req.title());
        post.setCompany(req.company());
        post.setDescription(req.description());
        post.setSalary(req.salary());
        post.setLocation(req.location());
        post.setJobType(req.jobType());
        post.setPostedBy(currentUser); // ✅ THIS WAS MISSING

        return jobPostService.createJobPost(post);
    }

   
    @GetMapping("/by-user")
    public List<JobPost> searchByUser(@RequestParam Long userId) {
        return jobPostService.searchByCreator(userId);
    }

    @GetMapping("/all")
    public Page<JobPost> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return jobPostService.getAllJobPosts(PageRequest.of(page, size));
    }

    @GetMapping("/search")
    public Page<JobPost> searchJobs(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> address,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return jobPostService.search(title, address, PageRequest.of(page, size));
    }

}
