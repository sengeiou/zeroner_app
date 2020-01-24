package com.iwown.ble_module.iwown.bean;

public class CmdBean {
    private byte[] data;
    private boolean over;

    public boolean isOver() {
        return this.over;
    }

    public void setOver(boolean over2) {
        this.over = over2;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data2) {
        this.data = data2;
    }
}
