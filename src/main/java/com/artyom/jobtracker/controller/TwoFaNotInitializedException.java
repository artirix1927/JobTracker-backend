package com.artyom.jobtracker.controller;

public class TwoFaNotInitializedException extends RuntimeException {
    public TwoFaNotInitializedException() {
        super("2FA setup not initialized");
    }
}