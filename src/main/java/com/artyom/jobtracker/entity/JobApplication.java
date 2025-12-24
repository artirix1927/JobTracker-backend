package com.artyom.jobtracker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@SuppressWarnings("unused")
@Entity
public class JobApplication {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private JobPost jobPost;

    @Enumerated(EnumType.STRING)
    @SuppressWarnings("FieldMayBeFinal")
    private Status status = Status.APPLIED; // APPLIED, INTERVIEW, OFFER, REJECTED

    private final LocalDateTime appliedAt = LocalDateTime.now();
}