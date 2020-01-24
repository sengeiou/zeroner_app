package com.iwown.device_module.common.network.data.req;

public class DeviceSettingsSend {
    private int app;
    private int app_platform;
    private String model;
    private String version;

    public int getApp() {
        return this.app;
    }

    public void setApp(int app2) {
        this.app = app2;
    }

    public int getApp_platform() {
        return this.app_platform;
    }

    public void setApp_platform(int app_platform2) {
        this.app_platform = app_platform2;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version2) {
        this.version = version2;
    }
}
