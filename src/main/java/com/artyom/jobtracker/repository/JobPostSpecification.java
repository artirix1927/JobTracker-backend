package com.artyom.jobtracker.repository;

import org.springframework.data.jpa.domain.Specification;

import com.artyom.jobtracker.entity.JobPost;

public class JobPostSpecification {

    public static Specification<JobPost> hasTitle(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<JobPost> hasAddress(String address) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("location")), "%" + address.toLowerCase() + "%");
    }
}