package com.iwown.device_module.device_operation.bean;

public class DeviceType {
    private int classid;
    private String device_name;
    private int device_type;
    private int imageId;

    public DeviceType() {
    }

    public DeviceType(int device_type2, String device_name2) {
        this.device_type = device_type2;
        this.device_name = device_name2;
    }

    public DeviceType(String device_name2, int classid2) {
        this.device_name = device_name2;
        this.classid = classid2;
    }

    public DeviceType(String device_name2, int imageId2, int classid2) {
        this.device_name = device_name2;
        this.imageId = imageId2;
        this.classid = classid2;
    }

    public int getClassid() {
        return this.classid;
    }

    public int getImageId() {
        return this.imageId;
    }

    public int getDevice_type() {
        return this.device_type;
    }

    public void setDevice_type(int device_type2) {
        this.device_type = device_type2;
    }

    public String getDevice_name() {
        return this.device_name;
    }

    public void setDevice_name(String device_name2) {
        this.device_name = device_name2;
    }

    public String toString() {
        return "DeviceType{device_type=" + this.device_type + ", device_name='" + this.device_name + '\'' + ", imageId=" + this.imageId + ", classid=" + this.classid + '}';
    }
}
