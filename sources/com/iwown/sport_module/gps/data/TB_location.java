package com.iwown.sport_module.gps.data;

import org.litepal.crud.DataSupport;

public class TB_location extends DataSupport {
    private double lat;
    private double lon;
    private int pause_type;
    private int sport_type;
    private int step;
    private long time;
    private long time_id;
    private long uid;

    public TB_location() {
    }

    public TB_location(double lat2, double lon2) {
        this.lat = lat2;
        this.lon = lon2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public long getTime_id() {
        return this.time_id;
    }

    public void setTime_id(long time_id2) {
        this.time_id = time_id2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
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

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public int getPause_type() {
        return this.pause_type;
    }

    public void setPause_type(int pause_type2) {
        this.pause_type = pause_type2;
    }
}
