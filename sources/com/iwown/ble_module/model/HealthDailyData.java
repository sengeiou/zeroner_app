package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class HealthDailyData {
    private int avg_bpm = -1;
    private int bpm = -1;
    private int bpm_hr = -1;
    private float calorie = -1.0f;
    private int data_type = -1;
    private int day = -1;
    private int dbp = -1;
    private float distance = -1.0f;
    private int hf = -1;
    private int level = -1;
    private int lf = -1;
    private int lf_hf = -1;
    private int max_bpm = -1;
    private int min_bpm = -1;
    private int month = -1;
    private int sbp = -1;
    private int sdnn = -1;
    private int steps = -1;
    private int year = -1;

    public HealthDailyData parseData(byte[] datas) {
        try {
            int year2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)) + 2000;
            int month2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)) + 1;
            int day2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)) + 1;
            int data_type2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            int steps2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 12));
            float distance2 = ((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 16))) * 0.1f;
            float calorie2 = ((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 20))) * 0.1f;
            setYear(year2);
            setMonth(month2);
            setDay(day2);
            setData_type(data_type2);
            setSteps(steps2);
            setDistance(distance2);
            setCalorie(calorie2);
            String highBits = ByteUtil.byteToBit((byte) data_type2).substring(4, 8);
            if (highBits.equalsIgnoreCase("0001")) {
                int avg_bpm2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22));
                int max_bpm2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 22, 24));
                int min_bpm2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 24, 26));
                int level2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 26, 27));
                setAvg_bpm(avg_bpm2);
                setMax_bpm(max_bpm2);
                setMin_bpm(min_bpm2);
                setLevel(level2);
            } else if (highBits.equalsIgnoreCase("0010")) {
                int sdnn2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22));
                int lf2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 22, 24));
                int hf2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 24, 26));
                int lf_hf2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 26, 28));
                int bpm_hr2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 26, 28));
                setSdnn(sdnn2);
                setLf(lf2);
                setHf(hf2);
                setLf_hf(lf_hf2);
                setBpm_hr(bpm_hr2);
            } else if (highBits.equalsIgnoreCase("0011")) {
                int sbp2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22));
                int dbp2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 22, 24));
                int bmp = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 24, 26));
                setSbp(sbp2);
                setDbp(dbp2);
                setBpm(bmp);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
        return this;
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
}
