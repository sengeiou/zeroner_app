package com.sweetzpot.stravazpot.stream.model;

public enum SeriesType {
    TIME("time"),
    DISTANCE("distance");
    
    private String rawValue;

    private SeriesType(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
