package com.artyom.jobtracker.controller;

public class InvalidTwoFaCodeException extends RuntimeException {
    public InvalidTwoFaCodeException() {
        super("Invalid 2FA code");
    }
}
