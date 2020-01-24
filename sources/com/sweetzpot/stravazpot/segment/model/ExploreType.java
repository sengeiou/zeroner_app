package com.sweetzpot.stravazpot.segment.model;

import com.google.android.gms.fitness.FitnessActivities;

public enum ExploreType {
    RUNNING(FitnessActivities.RUNNING),
    RIDING("riding");
    
    private String rawType;

    private ExploreType(String rawType2) {
        this.rawType = rawType2;
    }

    public String toString() {
        return this.rawType;
    }
}
