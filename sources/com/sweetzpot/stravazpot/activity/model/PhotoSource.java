package com.sweetzpot.stravazpot.activity.model;

public enum PhotoSource {
    STRAVA(1),
    INSTAGRAM(2);
    
    private int rawValue;

    private PhotoSource(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }
}
