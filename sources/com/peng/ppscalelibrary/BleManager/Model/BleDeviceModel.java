package com.peng.ppscalelibrary.BleManager.Model;

public class BleDeviceModel {
    String deviceMac;
    String deviceName;

    public String getDeviceMac() {
        return this.deviceMac;
    }

    public void setDeviceMac(String deviceMac2) {
        this.deviceMac = deviceMac2;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName2) {
        this.deviceName = deviceName2;
    }

    public BleDeviceModel(String deviceMac2, String deviceName2) {
        this.deviceMac = deviceMac2;
        this.deviceName = deviceName2;
    }
}
