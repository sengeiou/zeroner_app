package com.iwown.device_module.common.network.data.req;

public class FwUpdate {
    private int app;
    private int app_platform;
    private int app_version;
    private int device_model;
    private int device_type;
    private String fw_version;
    private int module;
    private String platform;
    private int skip;

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String platform2) {
        this.platform = platform2;
    }

    public int getSkip() {
        return this.skip;
    }

    public void setSkip(int skip2) {
        this.skip = skip2;
    }

    public int getModule() {
        return this.module;
    }

    public void setModule(int module2) {
        this.module = module2;
    }

    public int getApp_platform() {
        return this.app_platform;
    }

    public void setApp_platform(int app_platform2) {
        this.app_platform = app_platform2;
    }

    public int getApp() {
        return this.app;
    }

    public void setApp(int app2) {
        this.app = app2;
    }

    public int getApp_version() {
        return this.app_version;
    }

    public void setApp_version(int app_version2) {
        this.app_version = app_version2;
    }

    public int getDevice_type() {
        return this.device_type;
    }

    public void setDevice_type(int device_type2) {
        this.device_type = device_type2;
    }

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
}
