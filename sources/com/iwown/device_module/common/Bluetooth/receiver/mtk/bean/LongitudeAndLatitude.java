package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

public class LongitudeAndLatitude {
    private int altitude;
    private int gps_speed;
    private double latitude;
    private double longitude;

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude2) {
        this.longitude = longitude2;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude2) {
        this.latitude = latitude2;
    }

    public int getGps_speed() {
        return this.gps_speed;
    }

    public void setGps_speed(int gps_speed2) {
        this.gps_speed = gps_speed2;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public void setAltitude(int altitude2) {
        this.altitude = altitude2;
    }

    public String toString() {
        return "LongitudeAndLatitude{longitude=" + this.longitude + ", latitude=" + this.latitude + ", gps_speed=" + this.gps_speed + ", altitude=" + this.altitude + '}';
    }
}
