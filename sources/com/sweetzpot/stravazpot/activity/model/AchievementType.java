package com.sweetzpot.stravazpot.activity.model;

public enum AchievementType {
    OVERALL(2),
    PR(3),
    YEAR_OVERALL(5);
    
    private int rawValue;

    private AchievementType(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }
}
