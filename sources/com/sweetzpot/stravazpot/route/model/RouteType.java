package com.sweetzpot.stravazpot.route.model;

public enum RouteType {
    RIDE(1),
    RUN(2);
    
    private int rawValue;

    private RouteType(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }

    public String toString() {
        return String.valueOf(this.rawValue);
    }
}
