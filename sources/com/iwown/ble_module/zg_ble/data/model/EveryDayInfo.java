package com.iwown.ble_module.zg_ble.data.model;

public class EveryDayInfo {
    public int day;
    public int month;
    public long year;

    public String toString() {
        return String.format(" date: %d-%d-%d ", new Object[]{Long.valueOf(this.year), Integer.valueOf(this.month), Integer.valueOf(this.day)});
    }
}
