package com.iwown.sport_module.gps.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.io.Serializable;

public class GpsDetailItem implements Serializable, MultiItemEntity {
    private int activity_time;
    private String avg_hr;
    private String calorie;
    private String data_from;
    private int data_type;
    private float distance;
    private String down_gps_url = "";
    private long end_time = 0;
    private String flight_avg;
    private int itemType;
    private String rateOfStride_avg;
    private String speedList;
    private long start_time = 0;
    private String timeStr;
    private int time_type;
    private int touchDownPower_balance;
    private String touchDown_avg;

    public String getDown_gps_url() {
        return this.down_gps_url;
    }

    public void setDown_gps_url(String down_gps_url2) {
        this.down_gps_url = down_gps_url2;
    }

    public String getTimeStr() {
        return this.timeStr;
    }

    public void setTimeStr(String timeStr2) {
        this.timeStr = timeStr2;
    }

    public GpsDetailItem(int itemType2) {
        this.itemType = itemType2;
    }

    public void setItemType(int itemType2) {
        this.itemType = itemType2;
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

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public int getTime_type() {
        return this.time_type;
    }

    public void setTime_type(int time_type2) {
        this.time_type = time_type2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public int getActivity_time() {
        return this.activity_time;
    }

    public void setActivity_time(int activity_time2) {
        this.activity_time = activity_time2;
    }

    public int getItemType() {
        return 1;
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

    public void setTouchDownPower_balance(int touchDownPower_balance2) {
        this.touchDownPower_balance = touchDownPower_balance2;
    }

    public String getSpeedList() {
        return this.speedList;
    }

    public void setSpeedList(String speedList2) {
        this.speedList = speedList2;
    }

    public String getAvg_hr() {
        return this.avg_hr;
    }

    public void setAvg_hr(String avg_hr2) {
        this.avg_hr = avg_hr2;
    }

    public String getCalorie() {
        return this.calorie;
    }

    public void setCalorie(String calorie2) {
        this.calorie = calorie2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }
}
