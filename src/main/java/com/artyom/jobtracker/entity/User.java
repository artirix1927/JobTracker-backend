package com.artyom.jobtracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @SuppressWarnings("FieldMayBeFinal")
    private String role = "USER";

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
