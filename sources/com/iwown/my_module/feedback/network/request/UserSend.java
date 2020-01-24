package com.iwown.my_module.feedback.network.request;

public class UserSend {
    private String app;
    private String appVersion;
    private String brand;
    private String city;
    private String country;
    private String device;
    private String deviceVersion;
    private String phone;
    private String phoneSystem;
    private String phoneVersion;
    private String uid;

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid2) {
        this.uid = uid2;
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app2) {
        this.app = app2;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion2) {
        this.appVersion = appVersion2;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone2) {
        this.phone = phone2;
    }

    public String getPhoneVersion() {
        return this.phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion2) {
        this.phoneVersion = phoneVersion2;
    }

    public String getPhoneSystem() {
        return this.phoneSystem;
    }

    public void setPhoneSystem(String phoneSystem2) {
        this.phoneSystem = phoneSystem2;
    }

    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device2) {
        this.device = device2;
    }

    public String getDeviceVersion() {
        return this.deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion2) {
        this.deviceVersion = deviceVersion2;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country2) {
        this.country = country2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city2) {
        this.city = city2;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand2) {
        this.brand = brand2;
    }
}
