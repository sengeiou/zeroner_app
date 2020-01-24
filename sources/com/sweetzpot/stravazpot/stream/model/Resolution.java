package com.sweetzpot.stravazpot.stream.model;

public enum Resolution {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");
    
    private String rawValue;

    private Resolution(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
