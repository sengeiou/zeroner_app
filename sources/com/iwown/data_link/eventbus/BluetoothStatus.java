package com.iwown.data_link.eventbus;

public class BluetoothStatus {
    private int requestCode;

    public BluetoothStatus(int requestCode2) {
        this.requestCode = requestCode2;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public void setRequestCode(int requestCode2) {
        this.requestCode = requestCode2;
    }
}
