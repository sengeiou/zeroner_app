package com.iwown.device_module.common.sql;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class ProtoBuf_80_data extends DataSupport {
    private float MEAN;
    private float PNN50;
    private float RMSSD;
    private float SDNN;
    private int avg_bpm;
    private int bpTime;
    private float calorie;
    private boolean charge;
    private String data_from;
    private int day;
    private int dbp;
    private float distance;
    @Column(ignore = true)
    public float endClo = 0.0f;
    @Column(ignore = true)
    public float endDis = 0.0f;
    @Column(ignore = true)
    public int endMin = 0;
    @Column(ignore = true)
    public int endStep = 0;
    private float fatigue;
    private int hour;
    private int max_bpm;
    private int min_bpm;
    private int minute;
    private int month;
    private int sbp;
    private int second;
    private int seq;
    private boolean shutdown;
    private String sleepData;
    private int state;
    private int step;
    private int time;
    private int type;
    private long uid;
    private int year;

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

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute2) {
        this.minute = minute2;
    }

    public boolean isCharge() {
        return this.charge;
    }

    public void setCharge(boolean charge2) {
        this.charge = charge2;
    }

    public boolean isShutdown() {
        return this.shutdown;
    }

    public void setShutdown(boolean shutdown2) {
        this.shutdown = shutdown2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
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

    public int getMin_bpm() {
        return this.min_bpm;
    }

    public void setMin_bpm(int min_bpm2) {
        this.min_bpm = min_bpm2;
    }

    public int getMax_bpm() {
        return this.max_bpm;
    }

    public void setMax_bpm(int max_bpm2) {
        this.max_bpm = max_bpm2;
    }

    public int getAvg_bpm() {
        return this.avg_bpm;
    }

    public void setAvg_bpm(int avg_bpm2) {
        this.avg_bpm = avg_bpm2;
    }

    public float getSDNN() {
        return this.SDNN;
    }

    public void setSDNN(float SDNN2) {
        this.SDNN = SDNN2;
    }

    public float getRMSSD() {
        return this.RMSSD;
    }

    public void setRMSSD(float RMSSD2) {
        this.RMSSD = RMSSD2;
    }

    public float getPNN50() {
        return this.PNN50;
    }

    public void setPNN50(float PNN502) {
        this.PNN50 = PNN502;
    }

    public float getMEAN() {
        return this.MEAN;
    }

    public void setMEAN(float MEAN2) {
        this.MEAN = MEAN2;
    }

    public float getFatigue() {
        return this.fatigue;
    }

    public void setFatigue(float fatigue2) {
        this.fatigue = fatigue2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public String getSleepData() {
        return this.sleepData;
    }

    public void setSleepData(String sleepData2) {
        this.sleepData = sleepData2;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq2) {
        this.seq = seq2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public int getSbp() {
        return this.sbp;
    }

    public void setSbp(int sbp2) {
        this.sbp = sbp2;
    }

    public int getDbp() {
        return this.dbp;
    }

    public void setDbp(int dbp2) {
        this.dbp = dbp2;
    }

    public int getBpTime() {
        return this.bpTime;
    }

    public void setBpTime(int bpTime2) {
        this.bpTime = bpTime2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj == null || !(obj instanceof ProtoBuf_80_data)) {
            return false;
        }
        if (this.uid == ((ProtoBuf_80_data) obj).getUid() && ((ProtoBuf_80_data) obj).getData_from() != null && ((ProtoBuf_80_data) obj).getData_from().equals(this.data_from) && ((ProtoBuf_80_data) obj).getTime() == this.time && ((ProtoBuf_80_data) obj).getSeq() == this.seq) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }
}
