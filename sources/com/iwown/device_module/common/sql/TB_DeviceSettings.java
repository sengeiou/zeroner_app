package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_DeviceSettings extends DataSupport {
    private int app;
    private int app_platform;
    private int command;
    private String data_from;
    private String device_fw_version;
    private int device_platform;
    private String model;
    private int model_dfu;
    private int model_wechat;
    private String server_fw_version;
    private String setting;
    private String suffix;

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

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

    public int getModel_dfu() {
        return this.model_dfu;
    }

    public void setModel_dfu(int model_dfu2) {
        this.model_dfu = model_dfu2;
    }

    public String getServer_fw_version() {
        return this.server_fw_version;
    }

    public void setServer_fw_version(String server_fw_version2) {
        this.server_fw_version = server_fw_version2;
    }

    public String getDevice_fw_version() {
        return this.device_fw_version;
    }

    public void setDevice_fw_version(String device_fw_version2) {
        this.device_fw_version = device_fw_version2;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix2) {
        this.suffix = suffix2;
    }

    public int getDevice_platform() {
        return this.device_platform;
    }

    public void setDevice_platform(int device_platform2) {
        this.device_platform = device_platform2;
    }

    public String getSetting() {
        return this.setting;
    }

    public void setSetting(String setting2) {
        this.setting = setting2;
    }

    public int getCommand() {
        return this.command;
    }

    public void setCommand(int command2) {
        this.command = command2;
    }

    public int getModel_wechat() {
        return this.model_wechat;
    }

    public void setModel_wechat(int model_wechat2) {
        this.model_wechat = model_wechat2;
    }
}
