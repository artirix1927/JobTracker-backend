package com.artyom.jobtracker.entity;

public enum ApplicationStatus {
    APPLIED,
    INTERVIEW,
    OFFER,
    REJECTED;

    public boolean canTransitionTo(ApplicationStatus next) {
        return switch (this) {
            case APPLIED -> next == INTERVIEW || next == REJECTED;
            case INTERVIEW -> next == OFFER || next == REJECTED;
            case OFFER, REJECTED -> false;
        };
    }
}