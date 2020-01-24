package com.sweetzpot.stravazpot.segment.model;

public enum AgeGroup {
    AGE_0_24("0_24"),
    AGE_25_34("25_34"),
    AGE_35_44("35_44"),
    AGE_45_54("45_54"),
    AGE_55_64("55_64"),
    AGE_65_PLUS("65_plus");
    
    private String rawValue;

    private AgeGroup(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
