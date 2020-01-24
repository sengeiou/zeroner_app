package com.sweetzpot.stravazpot.club.model;

public enum SkillLevel {
    CASUAL(1),
    TEMPO(2),
    HAMMERFEST(4);
    
    private int rawValue;

    private SkillLevel(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }
}
