package com.sweetzpot.stravazpot.common.model;

public enum ResourceState {
    META(1),
    SUMMARY(2),
    DETAILED(3);
    
    private int rawValue;

    private ResourceState(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }

    public String toString() {
        return String.valueOf(this.rawValue);
    }
}
