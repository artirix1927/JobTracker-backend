package com.artyom.jobtracker.job_post;

import org.springframework.data.jpa.domain.Specification;

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