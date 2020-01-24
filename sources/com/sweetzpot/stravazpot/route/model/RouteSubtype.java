package com.sweetzpot.stravazpot.route.model;

public enum RouteSubtype {
    ROAD(1),
    MTB(2),
    CX(3),
    TRAIL(4),
    MIXED(5);
    
    private int rawValue;

    private RouteSubtype(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }

    public String toString() {
        return String.valueOf(this.rawValue);
    }
}
