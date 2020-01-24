package com.iwown.ble_module.zg_ble.data.model;

import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.util.Arrays;

public class ZGHeartData {
    public int averageHeart;
    public int day;
    public int highestHeart;
    public int index;
    public int lowHeart;
    public int month;
    public int[] staticHeart = new int[Opcodes.ADD_INT];
    public int year;

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

    public int getHighestHeart() {
        return this.highestHeart;
    }

    public void setHighestHeart(int highestHeart2) {
        this.highestHeart = highestHeart2;
    }

    public int getLowHeart() {
        return this.lowHeart;
    }

    public void setLowHeart(int lowHeart2) {
        this.lowHeart = lowHeart2;
    }

    public int getAverageHeart() {
        return this.averageHeart;
    }

    public void setAverageHeart(int averageHeart2) {
        this.averageHeart = averageHeart2;
    }

    public int[] getStaticHeart() {
        return this.staticHeart;
    }

    public void setStaticHeart(int[] staticHeart2) {
        this.staticHeart = staticHeart2;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public void addStaticHeart(int data) {
        if (this.index >= 144) {
            KLog.e("addStaticHeart index > 144  error " + this.index);
            return;
        }
        this.staticHeart[this.index] = data;
        this.index++;
    }

    public String toString() {
        return "ZGHeartData{year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", highestHeart=" + this.highestHeart + ", lowHeart=" + this.lowHeart + ", averageHeart=" + this.averageHeart + ", staticHeart=" + Arrays.toString(this.staticHeart) + ", index=" + this.index + '}';
    }
}
