package com.iwown.device_module.common.network.data.resp;

import com.iwown.device_module.device_setting.configure.SettingDefault;
import java.util.List;

public class DeviceSettingRemote {
    private String model;
    private List<SettingDefault> setting;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public List<SettingDefault> getSetting() {
        return this.setting;
    }

    public void setSetting(List<SettingDefault> setting2) {
        this.setting = setting2;
    }
}
