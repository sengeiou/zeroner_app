package com.sweetzpot.stravazpot.club.model;

import com.google.android.gms.fitness.FitnessActivities;

public enum ClubType {
    CASUAL_CLUB("casual_club"),
    RACING_TEAM("racing_team"),
    SHOP("shop"),
    COMPANY("company"),
    OTHER(FitnessActivities.OTHER);
    
    private String rawValue;

    private ClubType(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
