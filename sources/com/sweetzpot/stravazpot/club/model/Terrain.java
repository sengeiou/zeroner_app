package com.sweetzpot.stravazpot.club.model;

public enum Terrain {
    MOSTLY_FLAT(0),
    ROLLING_HILLS(1),
    KILLER_CLIMBS(2);
    
    private int rawValue;

    private Terrain(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }
}
