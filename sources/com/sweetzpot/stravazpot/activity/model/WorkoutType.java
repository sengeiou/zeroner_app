package com.sweetzpot.stravazpot.activity.model;

public enum WorkoutType {
    RUN_DEFAULT(0),
    RUN_RACE(1),
    RUN_LONGRUN(2),
    RUN_WORKOUT(3),
    RIDE_DEFAULT(10),
    RIDE_RACE(11),
    RIDE_WORKOUT(12);
    
    private int rawValue;

    private WorkoutType(int rawValue2) {
        this.rawValue = rawValue2;
    }

    public int getRawValue() {
        return this.rawValue;
    }
}
