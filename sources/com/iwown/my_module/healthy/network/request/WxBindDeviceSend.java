package com.iwown.my_module.healthy.network.request;

public class WxBindDeviceSend {
    private int device_model;
    private String macaddr;

    public String getMacaddr() {
        return this.macaddr;
    }

    public void setMacaddr(String macaddr2) {
        this.macaddr = macaddr2;
    }

    public int getDevice_model() {
        return this.device_model;
    }

    public void setDevice_model(int device_model2) {
        this.device_model = device_model2;
    }
}
