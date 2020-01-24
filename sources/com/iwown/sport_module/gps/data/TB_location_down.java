package com.iwown.sport_module.gps.data;

import org.litepal.crud.DataSupport;

public class TB_location_down extends DataSupport {
    private float calorie;
    private String data_from;
    private int data_type;
    private float distance;
    private long end_time;
    private int from;
    private String gps_msg;
    private int is_upload;
    private int page;
    private int sport_type;
    private int time;
    private long time_id;
    private long uid;

    public int getFrom() {
        return this.from;
    }

    public void setFrom(int from2) {
        this.from = from2;
    }

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page2) {
        this.page = page2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public long getTime_id() {
        return this.time_id;
    }

    public void setTime_id(long time_id2) {
        this.time_id = time_id2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
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

    public String getGps_msg() {
        return this.gps_msg;
    }

    public void setGps_msg(String gps_msg2) {
        this.gps_msg = gps_msg2;
    }

    public int getIs_upload() {
        return this.is_upload;
    }

    public void setIs_upload(int is_upload2) {
        this.is_upload = is_upload2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }
}
