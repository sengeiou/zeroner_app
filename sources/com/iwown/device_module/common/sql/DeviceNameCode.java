package com.iwown.device_module.common.sql;

import java.util.List;

public class DeviceNameCode {
    private List<DeviceName> data;
    private int retCode;

    public int getRetCode() {
        return this.retCode;
    }

    public void setRetCode(int retCode2) {
        this.retCode = retCode2;
    }

    public List<DeviceName> getData() {
        return this.data;
    }

    public void setData(List<DeviceName> data2) {
        this.data = data2;
    }
}
