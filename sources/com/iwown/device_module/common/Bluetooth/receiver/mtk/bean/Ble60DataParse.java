package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class Ble60DataParse {
    private int avg_bpm;
    private int bpm;
    private int bpm_hr;
    private float calorie;
    private int data_type;
    private int day;
    private int dbp;
    private float distance;
    private int hf;
    private int level;
    private int lf;
    private int lf_hf;
    private int max_bpm;
    private int min_bpm;
    private int month;
    private int sbp;
    private int sdnn;
    private int steps;
    private int year;

    public static Ble60DataParse parse(byte[] datas) {
        Ble60DataParse ble60DataParse = new Ble60DataParse();
        int year2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)) + 2000;
        int month2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)) + 1;
        int day2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)) + 1;
        int data_type2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
        int steps2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 12));
        float distance2 = (float) (Math.round(100.0f * (((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 16))) * 0.1f)) / 100);
        float calorie2 = (float) (Math.round(10.0f * (((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 20))) * 0.1f)) / 10);
        ble60DataParse.setYear(year2);
        ble60DataParse.setMonth(month2);
        ble60DataParse.setDay(day2);
        ble60DataParse.setData_type(data_type2);
        ble60DataParse.setSteps(steps2);
        ble60DataParse.setDistance(distance2);
        ble60DataParse.setCalorie(calorie2);
        String highBits = ByteUtil.byteToBit((byte) data_type2).substring(4, 8);
        if (highBits.equalsIgnoreCase("0001")) {
            int avg_bpm2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22));
            int max_bpm2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 22, 24));
            int min_bpm2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 24, 26));
            int level2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 26, 27));
            ble60DataParse.setAvg_bpm(avg_bpm2);
            ble60DataParse.setMax_bpm(max_bpm2);
            ble60DataParse.setMin_bpm(min_bpm2);
            ble60DataParse.setLevel(level2);
        } else if (highBits.equalsIgnoreCase("0010")) {
            int sdnn2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22));
            int lf2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 22, 24));
            int hf2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 24, 26));
            int lf_hf2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 26, 28));
            int bpm_hr2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 26, 28));
            ble60DataParse.setSdnn(sdnn2);
            ble60DataParse.setLf(lf2);
            ble60DataParse.setHf(hf2);
            ble60DataParse.setLf_hf(lf_hf2);
            ble60DataParse.setBpm_hr(bpm_hr2);
        } else if (highBits.equalsIgnoreCase("0011")) {
            int sbp2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22));
            int dbp2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 22, 24));
            int bmp = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 24, 26));
            ble60DataParse.setSbp(sbp2);
            ble60DataParse.setDbp(dbp2);
            ble60DataParse.setBpm(bmp);
        }
        return ble60DataParse;
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

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps2) {
        this.steps = steps2;
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

    public int getAvg_bpm() {
        return this.avg_bpm;
    }

    public void setAvg_bpm(int avg_bpm2) {
        this.avg_bpm = avg_bpm2;
    }

    public int getMax_bpm() {
        return this.max_bpm;
    }

    public void setMax_bpm(int max_bpm2) {
        this.max_bpm = max_bpm2;
    }

    public int getMin_bpm() {
        return this.min_bpm;
    }

    public void setMin_bpm(int min_bpm2) {
        this.min_bpm = min_bpm2;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level2) {
        this.level = level2;
    }

    public int getSdnn() {
        return this.sdnn;
    }

    public void setSdnn(int sdnn2) {
        this.sdnn = sdnn2;
    }

    public int getLf() {
        return this.lf;
    }

    public void setLf(int lf2) {
        this.lf = lf2;
    }

    public int getHf() {
        return this.hf;
    }

    public void setHf(int hf2) {
        this.hf = hf2;
    }

    public int getLf_hf() {
        return this.lf_hf;
    }

    public void setLf_hf(int lf_hf2) {
        this.lf_hf = lf_hf2;
    }

    public int getBpm_hr() {
        return this.bpm_hr;
    }

    public void setBpm_hr(int bpm_hr2) {
        this.bpm_hr = bpm_hr2;
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

    public int getBpm() {
        return this.bpm;
    }

    public void setBpm(int bpm2) {
        this.bpm = bpm2;
    }

    public String toString() {
        return "Ble60DataParse{year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", data_type=" + this.data_type + ", steps=" + this.steps + ", distance=" + this.distance + ", calorie=" + this.calorie + ", avg_bpm=" + this.avg_bpm + ", max_bpm=" + this.max_bpm + ", min_bpm=" + this.min_bpm + ", level=" + this.level + ", sdnn=" + this.sdnn + ", lf=" + this.lf + ", hf=" + this.hf + ", lf_hf=" + this.lf_hf + ", bpm_hr=" + this.bpm_hr + ", sbp=" + this.sbp + ", dbp=" + this.dbp + ", bpm=" + this.bpm + '}';
    }
}
