package com.artyom.jobtracker.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "user_2fa")
@Getter
@Setter
public class User2Fa {

    @Id
    private Long userId; // same as User.id

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String secret;

    @Column(nullable = false)
    private boolean enabled = false;

    @Column(nullable = true)
    private String deviceToken; // optional: for “remember me”
}
