package com.sweetzpot.stravazpot.club.model;

import com.google.android.gms.fitness.FitnessActivities;

public enum SportType {
    CYCLING("cycling"),
    RUNNING(FitnessActivities.RUNNING),
    TRIATHLON("triathlon"),
    OTHER(FitnessActivities.OTHER);
    
    private String rawValue;

    private SportType(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
