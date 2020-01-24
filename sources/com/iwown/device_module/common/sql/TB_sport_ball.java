package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_sport_ball extends DataSupport {
    private float calorie;
    private String data_from;
    private int duration;
    private long end_time;
    private String heart_url;
    private int sport_type;
    private long start_time;
    private long uid;
    private int upload_type;

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

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
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

    public String getHeart_url() {
        return this.heart_url;
    }

    public void setHeart_url(String heart_url2) {
        this.heart_url = heart_url2;
    }

    public int getUpload_type() {
        return this.upload_type;
    }

    public void setUpload_type(int upload_type2) {
        this.upload_type = upload_type2;
    }

    public long getId() {
        return getBaseObjId();
    }
}
