package com.sweetzpot.stravazpot.common.model;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    NOT_DEFINED("null");
    
    private String representation;

    private Gender(String representation2) {
        this.representation = representation2;
    }

    public String toString() {
        return this.representation;
    }
}
