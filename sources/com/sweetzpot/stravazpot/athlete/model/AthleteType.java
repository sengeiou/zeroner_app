package com.sweetzpot.stravazpot.athlete.model;

public enum AthleteType {
    CYCLIST(0),
    RUNNER(1);
    
    private int rawValue;

    private AthleteType(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }

    public String toString() {
        return String.valueOf(this.rawValue);
    }
}
