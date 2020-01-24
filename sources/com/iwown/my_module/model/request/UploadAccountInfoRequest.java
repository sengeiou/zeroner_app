package com.iwown.my_module.model.request;

public class UploadAccountInfoRequest {
    private int app;
    private int app_version;
    private String brand;
    private String country;
    private String model;
    private String sdk_version;
    private long uid;

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
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

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country2) {
        this.country = country2;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand2) {
        this.brand = brand2;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public String getSdk_version() {
        return this.sdk_version;
    }

    public void setSdk_version(String sdk_version2) {
        this.sdk_version = sdk_version2;
    }
}
