package com.iwown.device_module.device_operation.bean;

public class DeviceModel {
    public static final int scale = 3;
    public static final int smart_band = 1;
    public static final int watch = 2;
    private int battery;
    private String device_address;
    private String device_name;
    private long time;
    private int type;

    public static int getSmart_band() {
        return 1;
    }

    public static int getWatch() {
        return 2;
    }

    public static int getScale() {
        return 3;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String getDevice_name() {
        return this.device_name;
    }

    public void setDevice_name(String device_name2) {
        this.device_name = device_name2;
    }

    public String getDevice_address() {
        return this.device_address;
    }

    public void setDevice_address(String device_address2) {
        this.device_address = device_address2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public int getBattery() {
        return this.battery;
    }

    public void setBattery(int battery2) {
        this.battery = battery2;
    }
}
