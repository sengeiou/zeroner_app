package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_68_data extends DataSupport {
    private int avg_hr;
    private float calorie;
    private String cmd;
    private int ctrl;
    private String data_from;
    private int data_type;
    private int day;
    private float distance;
    private int flight_avg;
    private int flight_max;
    private int flight_min;
    private int hour;
    private int max_hr;
    private int min;
    private int min_hr;
    private int month;
    private int rateOfStride_avg;
    private int rateOfStride_max;
    private int rateOfStride_min;
    private int seconds;
    private int seq;
    private int sport_type;
    private int state_type;
    private int step;
    private long time;
    private int touchDownPower_avg;
    private int touchDownPower_balance;
    private int touchDownPower_max;
    private int touchDownPower_min;
    private int touchDownPower_stop;
    private int touchDown_avg;
    private int touchDown_max;
    private int touchDown_min;
    private long uid;
    private int year;

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String cmd2) {
        this.cmd = cmd2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public int getCtrl() {
        return this.ctrl;
    }

    public void setCtrl(int ctrl2) {
        this.ctrl = ctrl2;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq2) {
        this.seq = seq2;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min2) {
        this.min = min2;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds2) {
        this.seconds = seconds2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public int getState_type() {
        return this.state_type;
    }

    public void setState_type(int state_type2) {
        this.state_type = state_type2;
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

    public int getRateOfStride_min() {
        return this.rateOfStride_min;
    }

    public void setRateOfStride_min(int rateOfStride_min2) {
        this.rateOfStride_min = rateOfStride_min2;
    }

    public int getRateOfStride_max() {
        return this.rateOfStride_max;
    }

    public void setRateOfStride_max(int rateOfStride_max2) {
        this.rateOfStride_max = rateOfStride_max2;
    }

    public int getRateOfStride_avg() {
        return this.rateOfStride_avg;
    }

    public void setRateOfStride_avg(int rateOfStride_avg2) {
        this.rateOfStride_avg = rateOfStride_avg2;
    }

    public int getFlight_min() {
        return this.flight_min;
    }

    public void setFlight_min(int flight_min2) {
        this.flight_min = flight_min2;
    }

    public int getFlight_max() {
        return this.flight_max;
    }

    public void setFlight_max(int flight_max2) {
        this.flight_max = flight_max2;
    }

    public int getFlight_avg() {
        return this.flight_avg;
    }

    public void setFlight_avg(int flight_avg2) {
        this.flight_avg = flight_avg2;
    }

    public int getTouchDown_min() {
        return this.touchDown_min;
    }

    public void setTouchDown_min(int touchDown_min2) {
        this.touchDown_min = touchDown_min2;
    }

    public int getTouchDown_max() {
        return this.touchDown_max;
    }

    public void setTouchDown_max(int touchDown_max2) {
        this.touchDown_max = touchDown_max2;
    }

    public int getTouchDown_avg() {
        return this.touchDown_avg;
    }

    public void setTouchDown_avg(int touchDown_avg2) {
        this.touchDown_avg = touchDown_avg2;
    }

    public int getTouchDownPower_min() {
        return this.touchDownPower_min;
    }

    public void setTouchDownPower_min(int touchDownPower_min2) {
        this.touchDownPower_min = touchDownPower_min2;
    }

    public int getTouchDownPower_max() {
        return this.touchDownPower_max;
    }

    public void setTouchDownPower_max(int touchDownPower_max2) {
        this.touchDownPower_max = touchDownPower_max2;
    }

    public int getTouchDownPower_avg() {
        return this.touchDownPower_avg;
    }

    public void setTouchDownPower_avg(int touchDownPower_avg2) {
        this.touchDownPower_avg = touchDownPower_avg2;
    }

    public int getTouchDownPower_balance() {
        return this.touchDownPower_balance;
    }

    public void setTouchDownPower_balance(int touchDownPower_balance2) {
        this.touchDownPower_balance = touchDownPower_balance2;
    }

    public int getTouchDownPower_stop() {
        return this.touchDownPower_stop;
    }

    public void setTouchDownPower_stop(int touchDownPower_stop2) {
        this.touchDownPower_stop = touchDownPower_stop2;
    }

    public int getMin_hr() {
        return this.min_hr;
    }

    public void setMin_hr(int min_hr2) {
        this.min_hr = min_hr2;
    }

    public int getMax_hr() {
        return this.max_hr;
    }

    public void setMax_hr(int max_hr2) {
        this.max_hr = max_hr2;
    }

    public int getAvg_hr() {
        return this.avg_hr;
    }

    public void setAvg_hr(int avg_hr2) {
        this.avg_hr = avg_hr2;
    }
}
