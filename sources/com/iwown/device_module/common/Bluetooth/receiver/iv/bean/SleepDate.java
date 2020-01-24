package com.iwown.device_module.common.Bluetooth.receiver.iv.bean;

public class SleepDate {
    public int d;
    public int m;
    public int y;

    public SleepDate(int y2, int m2, int d2) {
        this.y = y2;
        this.m = m2;
        this.d = d2;
    }

    public boolean equals(Object o) {
        boolean z = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SleepDate sleepDate = (SleepDate) o;
        if (this.y != sleepDate.y || this.m != sleepDate.m) {
            return false;
        }
        if (this.d != sleepDate.d) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((this.y * 31) + this.m) * 31) + this.d;
    }
}
