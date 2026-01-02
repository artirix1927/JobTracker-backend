package com.artyom.jobtracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @SuppressWarnings("FieldMayBeFinal")
    private UserStatus role = UserStatus.USER;

    @Column()
    private String refreshToken;

    public String getRefreshToken() { 
        return refreshToken; 
    }
    public void setRefreshToken(String refreshToken) { 
        this.refreshToken = refreshToken; 
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
       this.email = email;
    }

    public void setPassword(String encoded_password) {
        this.password = encoded_password;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getRole(){
        return role;
    }
}
