package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;
import java.util.Calendar;

public class DeviceTime {
    int day = -1;
    int hour = -1;
    int minute = -1;
    int month = -1;
    int second = -1;
    int week = -1;
    int year = -1;

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

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week2) {
        this.week = week2;
    }

    public void parseData(byte[] datas) {
        try {
            this.year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)) + 2000;
            this.month = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)) + 1;
            this.day = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)) + 1;
            this.hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.minute = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.second = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
            Calendar calendar = Calendar.getInstance();
            calendar.set(this.year, this.month, this.day, this.hour, this.minute, this.second);
            this.week = calendar.get(3);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
    }
}
