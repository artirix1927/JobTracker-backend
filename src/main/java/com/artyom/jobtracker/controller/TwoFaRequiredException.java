package com.artyom.jobtracker.controller;

public class TwoFaRequiredException extends RuntimeException {
    private final String email;

    public TwoFaRequiredException(String email) {
        super("2FA required");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}