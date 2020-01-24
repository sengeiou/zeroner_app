package com.sweetzpot.stravazpot.athlete.model;

public enum MeasurementPreference {
    FEET("feet"),
    METERS("meters");
    
    private String rawValue;

    private MeasurementPreference(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
