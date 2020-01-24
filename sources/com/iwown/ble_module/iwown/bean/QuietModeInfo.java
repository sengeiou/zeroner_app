package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import java.util.Arrays;

public class QuietModeInfo {
    private int endHour;
    private int endMin;
    private int isOpened;
    private int startHour;
    private int startMin;

    public int isOpened() {
        return this.isOpened;
    }

    public void setOpened(int opened) {
        this.isOpened = opened;
    }

    public int getStartHour() {
        return this.startHour;
    }

    public void setStartHour(int startHour2) {
        this.startHour = startHour2;
    }

    public int getEndHour() {
        return this.endHour;
    }

    public void setEndHour(int endHour2) {
        this.endHour = endHour2;
    }

    public int getStartMin() {
        return this.startMin;
    }

    public void setStartMin(int startMin2) {
        this.startMin = startMin2;
    }

    public int getEndMin() {
        return this.endMin;
    }

    public void setEndMin(int endMin2) {
        this.endMin = endMin2;
    }

    public void parseData(byte[] datas) {
        setOpened(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
        setStartHour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)));
        setStartMin(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)));
        setEndHour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)));
        setEndMin(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)));
    }
}
