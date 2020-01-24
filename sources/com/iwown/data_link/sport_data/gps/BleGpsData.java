package com.iwown.data_link.sport_data.gps;

public class BleGpsData {
    private String data_from;
    private double lat;
    private double lon;
    private long time;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat2) {
        this.lat = lat2;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLon(double lon2) {
        this.lon = lon2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }
}
