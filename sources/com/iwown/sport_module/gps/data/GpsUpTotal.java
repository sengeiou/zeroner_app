package com.iwown.sport_module.gps.data;

public class GpsUpTotal {
    private float avgPace;
    private String cadence;
    private float calorie;
    private int data_from;
    private float distance;
    private int duration;
    private int sports_type;
    private long timeEnd;
    private long timeStart;
    private String track_url;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getTimeStart() {
        return this.timeStart;
    }

    public void setTimeStart(long timeStart2) {
        this.timeStart = timeStart2;
    }

    public long getTimeEnd() {
        return this.timeEnd;
    }

    public void setTimeEnd(long timeEnd2) {
        this.timeEnd = timeEnd2;
    }

    public int getSports_type() {
        return this.sports_type;
    }

    public void setSports_type(int sports_type2) {
        this.sports_type = sports_type2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration2) {
        this.duration = duration2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public float getAvgPace() {
        return this.avgPace;
    }

    public void setAvgPace(float avgPace2) {
        this.avgPace = avgPace2;
    }

    public String getTrack_url() {
        return this.track_url;
    }

    public void setTrack_url(String track_url2) {
        this.track_url = track_url2;
    }

    public String getCadence() {
        return this.cadence;
    }

    public void setCadence(String cadence2) {
        this.cadence = cadence2;
    }

    public int getData_from() {
        return this.data_from;
    }

    public void setData_from(int data_from2) {
        this.data_from = data_from2;
    }
}
