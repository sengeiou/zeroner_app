package com.iwown.data_link.sport_data.gps;

public class Gps {
    private double wgLat;
    private double wgLon;

    public Gps() {
    }

    public Gps(double wgLat2, double wgLon2) {
        this.wgLat = wgLat2;
        this.wgLon = wgLon2;
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
}
