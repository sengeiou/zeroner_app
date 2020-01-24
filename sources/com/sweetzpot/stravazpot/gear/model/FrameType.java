package com.sweetzpot.stravazpot.gear.model;

public enum FrameType {
    MTB(1),
    CROSS(2),
    ROAD(3),
    TIME_TRIAL(4);
    
    private int rawValue;

    private FrameType(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }

    public String toString() {
        return String.valueOf(this.rawValue);
    }
}
