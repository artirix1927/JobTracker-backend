package com.artyom.jobtracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@SuppressWarnings("unused")
@Entity
public class JobPost {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String company;
    private String description;

    @ManyToOne
    private User postedBy;
}
