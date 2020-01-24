package com.iwown.data_link.device;

public class DeviceInfo {
    public static final int Sdk_Type_Iv = 1;
    public static final int Sdk_Type_Mtk = 2;
    public static final int Sdk_Type_Zg = 3;
    public String dev_modle = "";
    public String dev_name = "";
    public String mac = "";
    public int sdk_type;

    public String toString() {
        return "DeviceInfo{dev_name='" + this.dev_name + '\'' + ", mac='" + this.mac + '\'' + ", sdk_type=" + this.sdk_type + '}';
    }
}
