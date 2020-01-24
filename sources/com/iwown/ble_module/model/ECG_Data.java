package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class ECG_Data {
    private int ctrl;
    private int day;
    private int[] ecg_raw_data;
    private int hour;
    private int min;
    private int month;
    private int second;
    private int seq;
    private int year;

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

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public int[] getEcg_raw_data() {
        return this.ecg_raw_data;
    }

    public void setEcg_raw_data(int[] ecg_raw_data2) {
        this.ecg_raw_data = ecg_raw_data2;
    }

    public ECG_Data parseData(byte[] data) {
        try {
            this.ctrl = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
            this.seq = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 5, 7));
            this.year = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 7, 8)) + 2000;
            this.month = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 8, 9)) + 1;
            this.day = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 9, 10)) + 1;
            this.hour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 10, 11));
            this.min = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 11, 12));
            this.second = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 12, 13));
            this.ecg_raw_data = new int[60];
            if (data.length > 133) {
                for (int i = 0; i < 60; i++) {
                    if (data.length >= (i * 4) + 15) {
                        this.ecg_raw_data[i] = ByteUtil.bytes2IntIncludeSignBit(Arrays.copyOfRange(data, (i * 4) + 13, (i * 4) + 17));
                    }
                }
            } else {
                for (int i2 = 0; i2 < 60; i2++) {
                    if (data.length >= (i2 * 2) + 15) {
                        this.ecg_raw_data[i2] = ByteUtil.bytes2IntIncludeSignBit(Arrays.copyOfRange(data, (i2 * 2) + 13, (i2 * 2) + 15));
                    }
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
        return this;
    }
}
