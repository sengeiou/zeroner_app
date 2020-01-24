package com.sweetzpot.stravazpot.club.model;

public enum Membership {
    MEMBER("member"),
    PENDING("pending"),
    NOT_MEMBER("null");
    
    private String rawType;

    private Membership(String rawType2) {
        this.rawType = rawType2;
    }

    public String toString() {
        return this.rawType;
    }
}
