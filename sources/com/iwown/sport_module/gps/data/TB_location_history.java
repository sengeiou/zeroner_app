package com.iwown.sport_module.gps.data;

import org.litepal.crud.DataSupport;

public class TB_location_history extends DataSupport {
    private String avg_hr;
    private float calorie;
    private String data_from;
    private int data_type;
    private float distance;
    private long end_time;
    private String flight_avg;
    private String gps_msg;
    private int is_upload;
    private String rateOfStride_avg;
    private String speedList;
    private int sport_type;
    private int time;
    private long time_id;
    private int touchDownPower_balance;
    private String touchDown_avg;
    private long uid;
    private String year_month_day;

    public long getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(long end_time2) {
        this.end_time = end_time2;
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

    public String getYear_month_day() {
        return this.year_month_day;
    }

    public void setYear_month_day(String year_month_day2) {
        this.year_month_day = year_month_day2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public String getAvg_hr() {
        return this.avg_hr;
    }

    public void setAvg_hr(String avg_hr2) {
        this.avg_hr = avg_hr2;
    }

    public String getRateOfStride_avg() {
        return this.rateOfStride_avg;
    }

    public void setRateOfStride_avg(String rateOfStride_avg2) {
        this.rateOfStride_avg = rateOfStride_avg2;
    }

    public String getFlight_avg() {
        return this.flight_avg;
    }

    public void setFlight_avg(String flight_avg2) {
        this.flight_avg = flight_avg2;
    }

    public String getTouchDown_avg() {
        return this.touchDown_avg;
    }

    public void setTouchDown_avg(String touchDown_avg2) {
        this.touchDown_avg = touchDown_avg2;
    }

    public int getTouchDownPower_balance() {
        return this.touchDownPower_balance;
    }

    public String getSpeedList() {
        return this.speedList;
    }

    public void setSpeedList(String speedList2) {
        this.speedList = speedList2;
    }

    public void setTouchDownPower_balance(int touchDownPower_balance2) {
        this.touchDownPower_balance = touchDownPower_balance2;
    }
}
