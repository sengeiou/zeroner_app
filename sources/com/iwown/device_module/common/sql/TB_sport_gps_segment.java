package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_sport_gps_segment extends DataSupport {
    private float calorie;
    private String data_from;
    private float distance;
    private int duration;
    private long end_time;
    private String gps_url;
    private String heart_url;
    private String mtime;
    private String r1_url;
    private int source_type;
    private int sport_type;
    private long start_time;
    private int step;
    private long uid;
    private int upload;
    private int url_type;

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

    public long getId() {
        return getBaseObjId();
    }

    public int getUpload() {
        return this.upload;
    }

    public void setUpload(int upload2) {
        this.upload = upload2;
    }

    public int getUrl_type() {
        return this.url_type;
    }

    public void setUrl_type(int url_type2) {
        this.url_type = url_type2;
    }

    public String getMtime() {
        return this.mtime;
    }

    public void setMtime(String mtime2) {
        this.mtime = mtime2;
    }
}
