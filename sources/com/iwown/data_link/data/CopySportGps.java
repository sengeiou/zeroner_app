package com.iwown.data_link.data;

public class CopySportGps {
    private float calorie;
    private String data_from;
    private float distance;
    private int done_times;
    private int duration;
    private long end_time;
    private String gps_url;
    private String heart_url;
    private float pace;
    private String r1_url;
    private int source_type;
    private int sport_type;
    private long start_time;
    private int step;
    private int stride;
    private long uid;
    private int upload = 0;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getStart_time() {
        return this.start_time;
    }

    public void setStart_time(long start_time2) {
        this.start_time = start_time2;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public int getSource_type() {
        return this.source_type;
    }

    public void setSource_type(int source_type2) {
        this.source_type = source_type2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
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

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration2) {
        this.duration = duration2;
    }

    public float getPace() {
        return this.pace;
    }

    public void setPace(float pace2) {
        this.pace = pace2;
    }

    public int getStride() {
        return this.stride;
    }

    public void setStride(int stride2) {
        this.stride = stride2;
    }

    public String getGps_url() {
        return this.gps_url;
    }

    public void setGps_url(String gps_url2) {
        this.gps_url = gps_url2;
    }

    public String getHeart_url() {
        return this.heart_url;
    }

    public void setHeart_url(String heart_url2) {
        this.heart_url = heart_url2;
    }

    public String getR1_url() {
        return this.r1_url;
    }

    public void setR1_url(String r1_url2) {
        this.r1_url = r1_url2;
    }

    public int getDone_times() {
        return this.done_times;
    }

    public void setDone_times(int done_times2) {
        this.done_times = done_times2;
    }

    public int getUpload() {
        return this.upload;
    }

    public void setUpload(int upload2) {
        this.upload = upload2;
    }
}
