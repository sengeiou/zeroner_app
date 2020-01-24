package com.iwown.ble_module.proto.model;

public class ProtoBufHardwareInfo {
    public static String[] fotas = {"NON", "CC2540", "NRF51", "DA1468X", "MT2523", "NRF52_BLE", "NRF52_SERIAL"};
    private String deviceTime;
    private String factory;
    private String fota;
    private int fotaType;
    private String mac;
    private String model;
    private String version;

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac2) {
        this.mac = mac2;
    }

    public String getDeviceTime() {
        return this.deviceTime;
    }

    public void setDeviceTime(String deviceTime2) {
        this.deviceTime = deviceTime2;
    }

    public String getFactory() {
        return this.factory;
    }

    public void setFactory(String factory2) {
        this.factory = factory2;
    }

    public String getFota() {
        return this.fota;
    }

    public void setFota(String fota2) {
        this.fota = fota2;
    }

    public int getFotaType() {
        return this.fotaType;
    }

    public void setFotaType(int fotaType2) {
        this.fotaType = fotaType2;
    }
}
