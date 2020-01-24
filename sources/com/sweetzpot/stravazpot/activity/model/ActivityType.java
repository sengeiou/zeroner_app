package com.sweetzpot.stravazpot.activity.model;

public enum ActivityType {
    RIDE("Ride"),
    RUN("Run"),
    SWIM("Swim"),
    HIKE("Hike"),
    WALK("Walk"),
    ALPINE_SKI("AlpineSki"),
    BACKCOUNTRY_SKI("BackcountrySki"),
    CANOEING("Canoeing"),
    CROSSFIT("Crossfit"),
    EBIKE_RIDE("EBikeRide"),
    ELLIPTICAL("Elliptical"),
    ICE_SKATE("IceSkate"),
    INLINE_SKATE("InlineSkate"),
    KAYAKING("Kayaking"),
    KITE_SURF("Kitesurf"),
    NORDIC_SKI("NordicSki"),
    ROCK_CLIMBING("RockClimbing"),
    ROLLER_SKI("RollerSki"),
    ROWING("Rowing"),
    SNOWBOARD("Snowboard"),
    SNOWSHOE("Snowshoe"),
    STAIR_STEPPER("StairStepper"),
    STANDUP_PADDLING("StandUpPaddling"),
    SURFING("Surfing"),
    VIRTUAL_RIDE("VirtualRide"),
    WEIGHT_TRAINING("WeightTraining"),
    WINDSURF("Windsurf"),
    WORKOUT("Workout"),
    YOGA("Yoga");
    
    private String rawValue;

    private ActivityType(String rawValue2) {
        this.rawValue = rawValue2;
    }

    public String toString() {
        return this.rawValue;
    }
}
