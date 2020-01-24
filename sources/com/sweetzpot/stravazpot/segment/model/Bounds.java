package com.sweetzpot.stravazpot.segment.model;

import com.sweetzpot.stravazpot.common.model.Coordinates;

public class Bounds {
    private final Coordinates northEast;
    private final Coordinates southWest;

    public static Bounds with(Coordinates southWest2, Coordinates northEast2) {
        return new Bounds(southWest2, northEast2);
    }

    public Bounds(Coordinates southWest2, Coordinates northEast2) {
        this.southWest = southWest2;
        this.northEast = northEast2;
    }

    public String toString() {
        return this.southWest.getLatitude() + "," + this.southWest.getLongitude() + "," + this.northEast.getLatitude() + "," + this.northEast.getLongitude();
    }
}
