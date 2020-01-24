package com.iwown.device_module.common.network.data.req;

public class UserDeviceReq {
    private int device_model;
    private String fw_version;
    private long uid;

    public int getDevice_model() {
        return this.device_model;
    }

    public void setDevice_model(int device_model2) {
        this.device_model = device_model2;
    }

    public String getFw_version() {
        return this.fw_version;
    }

    public void setFw_version(String fw_version2) {
        this.fw_version = fw_version2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }
}
