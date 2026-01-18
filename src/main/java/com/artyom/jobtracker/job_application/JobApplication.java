package com.artyom.jobtracker.job_application;

import java.time.LocalDateTime;

import com.artyom.jobtracker.job_post.JobPost;
import com.artyom.jobtracker.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("unused")
@Entity
@Table(
    name = "job_application",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_post_id"})
)
@Getter
@Setter
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private JobPost jobPost;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED; // APPLIED, INTERVIEW, OFFER, REJECTED

    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String resumePath;

    @Column(nullable = true)
    private String resumeFilename;


    @PrePersist
    void onCreate() {
        this.appliedAt = LocalDateTime.now();
    }
}
