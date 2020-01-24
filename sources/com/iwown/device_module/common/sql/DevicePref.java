package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class DevicePref extends DataSupport {
    private String model;
    private String setting;
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

    public String getSetting() {
        return this.setting;
    }

    public void setSetting(String setting2) {
        this.setting = setting2;
    }
}
