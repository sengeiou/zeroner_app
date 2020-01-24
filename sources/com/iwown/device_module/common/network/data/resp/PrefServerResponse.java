package com.iwown.device_module.common.network.data.resp;

public class PrefServerResponse extends RetCode {
    DeviceSettingRemote data;

    public DeviceSettingRemote getData() {
        return this.data;
    }

    public void setData(DeviceSettingRemote data2) {
        this.data = data2;
    }
}
