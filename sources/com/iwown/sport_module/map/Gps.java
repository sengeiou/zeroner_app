package com.iwown.sport_module.map;

public class Gps {
    private double wgLat;
    private double wgLon;

    public Gps(double wgLat2, double wgLon2) {
        setWgLat(wgLat2);
        setWgLon(wgLon2);
    }

    public double getWgLat() {
        return this.wgLat;
    }

    public void setWgLat(double wgLat2) {
        this.wgLat = wgLat2;
    }

    public double getWgLon() {
        return this.wgLon;
    }

    public void setWgLon(double wgLon2) {
        this.wgLon = wgLon2;
    }

    public String toString() {
        return this.wgLat + "," + this.wgLon;
    }
}
