package com.iwown.ble_module.model;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.utils.Util;
import java.util.Arrays;

public class R1HealthMinuteData {
    private String cmd;
    private int ctrl;
    private int data_type;
    private int day;
    private int hour;
    private R1HeartRateMinuteData hr;
    private int minute;
    private int month;
    private int second;
    private int seq;
    private R1WalkRunMinuteData walk;
    private int year;

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String cmd2) {
        this.cmd = cmd2;
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

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute2) {
        this.minute = minute2;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public R1HeartRateMinuteData getHr() {
        return this.hr;
    }

    public void setHr(R1HeartRateMinuteData hr2) {
        this.hr = hr2;
    }

    public R1WalkRunMinuteData getWalk() {
        return this.walk;
    }

    public void setWalk(R1WalkRunMinuteData walk2) {
        this.walk = walk2;
    }

    public R1HealthMinuteData parseData(byte[] datas) {
        int index;
        setCtrl(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
        setSeq(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 7)));
        setYear(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 2000);
        setMonth(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1);
        setDay(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10)) + 1);
        setHour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11)));
        setMinute(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12)));
        setSecond(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13)));
        int data_type2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
        setData_type(data_type2);
        byte[] data = ByteUtil.byteToBitArray(data_type2);
        if (data[2] == 1 && datas.length >= 51) {
            byte[] walkDataBytes = Arrays.copyOfRange(datas, 14, 51);
            R1WalkRunMinuteData walkData = new R1WalkRunMinuteData();
            walkData.setSport_type(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 0, 2)));
            walkData.setState_type(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 2, 3)));
            walkData.setStep(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 3, 5)));
            walkData.setDistance(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 5, 7))) / 10.0f);
            walkData.setCalorie(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 7, 9))) / 10.0f);
            walkData.setRateOfStride_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 9, 11)));
            walkData.setRateOfStride_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 11, 13)));
            walkData.setRateOfStride_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 13, 15)));
            walkData.setFlight_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 15, 17)));
            walkData.setFlight_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 17, 19)));
            walkData.setFlight_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 19, 21)));
            walkData.setTouchDown_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 21, 23)));
            walkData.setTouchDown_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 23, 25)));
            walkData.setTouchDown_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 25, 27)));
            walkData.setTouchDownPower_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 27, 29)));
            walkData.setTouchDownPower_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 29, 31)));
            walkData.setTouchDownPower_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 31, 33)));
            walkData.setTouchDownPower_balance(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 33, 35)));
            walkData.setTouchDownPower_stop(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 35, 37)));
            setWalk(walkData);
        }
        if (data[7] == 1) {
            if (data[2] != 1 || datas.length < 51) {
                index = 14;
            } else {
                index = 51;
            }
            byte[] hrDataBytes = Arrays.copyOfRange(datas, index, index + 6);
            int min_hr = ByteUtil.bytesToInt(Arrays.copyOfRange(hrDataBytes, 0, 2));
            int max_hr = ByteUtil.bytesToInt(Arrays.copyOfRange(hrDataBytes, 2, 4));
            int avg_hr = ByteUtil.bytesToInt(Arrays.copyOfRange(hrDataBytes, 4, 6));
            R1HeartRateMinuteData hrData = new R1HeartRateMinuteData();
            hrData.setMax_hr(max_hr);
            hrData.setAvg_hr(avg_hr);
            hrData.setMin_hr(min_hr);
            setHr(hrData);
        }
        setCmd(Util.bytesToString(datas, false));
        return this;
    }
}
