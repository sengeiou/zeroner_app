package com.sweetzpot.stravazpot.athlete.model;

public enum FriendStatus {
    PENDING("pending"),
    ACCEPTED("accepted"),
    BLOCKED("blocked"),
    NOT_FRIENDS("null");
    
    private String rawValue;

    private FriendStatus(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
