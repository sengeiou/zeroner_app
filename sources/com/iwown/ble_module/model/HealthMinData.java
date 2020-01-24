package com.iwown.ble_module.model;

import android.support.v4.util.Pools.SynchronizedPool;
import com.google.common.base.Ascii;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.ble_module.utils.Util;
import java.util.Arrays;

public class HealthMinData {
    private static final SynchronizedPool<HealthMinData> sPool = new SynchronizedPool<>(20);
    private int automaticMin;
    private int avg_bpm;
    private int bpm;
    private int bpm_hr;
    private float calorie;
    private String cmd;
    private int ctrl;
    private int data_type;
    private int day;
    private int dbp;
    private float distance;
    private double fatigue;
    private int hf;
    private int hour;
    private int level;
    private int lf;
    private int lf_hf;
    private int max_bpm;
    private int min;
    private int min_bpm;
    private int month;
    private int reserve;
    private int sbp;
    private int sdnn;
    private int second;
    private int seq;
    private int sport_type;
    private int state_type;
    private int step;
    private int year;

    public static HealthMinData obtain() {
        HealthMinData minData = (HealthMinData) sPool.acquire();
        if (minData == null) {
            return new HealthMinData();
        }
        return minData;
    }

    public void recycle() {
        sPool.release(this);
    }

    public int getAutomaticMin() {
        return this.automaticMin;
    }

    public void setAutomaticMin(int automaticMin2) {
        this.automaticMin = automaticMin2;
    }

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public double getFatigue() {
        return this.fatigue;
    }

    public void setFatigue(double fatigue2) {
        this.fatigue = fatigue2;
    }

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

    public int getMin() {
        return this.min;
    }

    public void setMin(int min2) {
        this.min = min2;
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

    public int getState_type() {
        return this.state_type;
    }

    public void setState_type(int state_type2) {
        this.state_type = state_type2;
    }

    public int getReserve() {
        return this.reserve;
    }

    public void setReserve(int reserve2) {
        this.reserve = reserve2;
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

    public HealthMinData parseData(byte[] datas) {
        try {
            setCtrl(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
            setSeq(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 7)));
            setYear(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 2000);
            setMonth(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1);
            setDay(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10)) + 1);
            setHour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11)));
            setMin(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12)));
            int data_type2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            setData_type(data_type2);
            int index = 13;
            byte[] data = ByteUtil.byteToBitArray(data_type2);
            if (data[2] == 1) {
                byte[] pedoData = Arrays.copyOfRange(datas, 13, 23);
                setCalorie(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 0, 2))) * 0.1f);
                setStep(ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 2, 4)));
                setDistance(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 4, 6))) * 0.1f);
                setSport_type(ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 6, 8)));
                int automaticMin2 = ByteUtil.byteToInt((byte) (pedoData[8] >> 4));
                setState_type(ByteUtil.byteToInt((byte) (pedoData[8] & Ascii.SI)));
                setAutomaticMin(automaticMin2);
                setSecond(ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 9, 10)));
                index = 13 + 10;
            }
            if (data[7] == 1) {
                byte[] hrData = Arrays.copyOfRange(datas, index, index + 7);
                setMin_bpm(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 0, 2)));
                setMax_bpm(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 2, 4)));
                setAvg_bpm(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 4, 6)));
                setLevel(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 6, 7)));
                index += 7;
            }
            if (data[6] == 1) {
                byte[] hrvData = Arrays.copyOfRange(datas, index, index + 14);
                setSdnn(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 0, 2)));
                int lf2 = ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 2, 6));
                setLf(lf2);
                setHf(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 6, 10)));
                setLf_hf(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 10, 12)));
                int bpm_hr2 = ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 12, 14));
                setBpm_hr(bpm_hr2);
                if (bpm_hr2 > 0) {
                    setFatigue((double) bpm_hr2);
                } else {
                    setFatigue(Math.log((double) lf2) * 20.0d);
                }
                index += 14;
            }
            if (data[5] == 1) {
                byte[] bpData = Arrays.copyOfRange(datas, index, index + 6);
                setSbp(ByteUtil.bytesToInt(Arrays.copyOfRange(bpData, 0, 2)));
                setDbp(ByteUtil.bytesToInt(Arrays.copyOfRange(bpData, 2, 4)));
                setBpm(ByteUtil.bytesToInt(Arrays.copyOfRange(bpData, 4, 6)));
                int index2 = index + 6;
            }
            setCmd(Util.bytesToString(datas, false));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
        return this;
    }
}
