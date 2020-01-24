package com.iwown.device_module.common.Bluetooth.receiver.iv.bean;

import com.iwown.ble_module.iwown.bean.DataDetailHeart;

public class HeartRateDetial {
    private static final long serialVersionUID = 1;
    private int r0 = 0;
    private int r1 = 0;
    private int r2 = 0;
    private int r3 = 0;
    private int r4 = 0;
    private int r5 = 0;

    public static HeartRateDetial parse(DataDetailHeart dataDetailHeart) {
        HeartRateDetial detial = new HeartRateDetial();
        detial.setR1(dataDetailHeart.getR1Time());
        detial.setR2(dataDetailHeart.getR2Time());
        detial.setR3(dataDetailHeart.getR3Time());
        detial.setR4(dataDetailHeart.getR4Time());
        detial.setR5(dataDetailHeart.getR5Time());
        return detial;
    }

    public int getR0() {
        return this.r0;
    }

    public void setR0(int r02) {
        this.r0 = r02;
    }

    public int getR1() {
        return this.r1;
    }

    public void setR1(int r12) {
        this.r1 = r12;
    }

    public int getR2() {
        return this.r2;
    }

    public void setR2(int r22) {
        this.r2 = r22;
    }

    public int getR3() {
        return this.r3;
    }

    public void setR3(int r32) {
        this.r3 = r32;
    }

    public int getR4() {
        return this.r4;
    }

    public void setR4(int r42) {
        this.r4 = r42;
    }

    public int getR5() {
        return this.r5;
    }

    public void setR5(int r52) {
        this.r5 = r52;
    }
}
