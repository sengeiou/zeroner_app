package com.iwown.device_module.common.Bluetooth.receiver.zg.bean;

public class ZGFirmwareUpgrade {
    private int success;

    public ZGFirmwareUpgrade() {
    }

    public ZGFirmwareUpgrade(int success2) {
        this.success = success2;
    }

    public int getSuccess() {
        return this.success;
    }

    public void setSuccess(int success2) {
        this.success = success2;
    }
}
