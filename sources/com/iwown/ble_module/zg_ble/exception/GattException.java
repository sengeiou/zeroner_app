package com.iwown.ble_module.zg_ble.exception;

public class GattException extends BleException {
    private int gattStatus;

    public GattException(int gattStatus2) {
        super(101, "Gatt Exception Occurred! ");
        this.gattStatus = gattStatus2;
    }

    public int getGattStatus() {
        return this.gattStatus;
    }

    public GattException setGattStatus(int gattStatus2) {
        this.gattStatus = gattStatus2;
        return this;
    }

    public String toString() {
        return "GattException{gattStatus=" + this.gattStatus + "} " + super.toString();
    }
}
