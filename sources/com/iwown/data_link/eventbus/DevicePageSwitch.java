package com.iwown.data_link.eventbus;

public class DevicePageSwitch {
    public static final String Device_Check_List = "DevicePageSwitch.Device_Check_List";
    public static final String Device_Setting = "DevicePageSwitch.Device_Setting";
    public static final String Device_Top_Switch_To_Scale = "DevicePageSwitch.Device_Top_Switch_To_Scale";
    public static final String Device_Top_Switch_To_Setting = "DevicePageSwitch.Device_Top_Switch_To_Setting";
    public static final String Device_Top_Unbind_Device = "DevicePageSwitch.Device_Top_Unbind_Device";
    private String action;

    public DevicePageSwitch(String action2) {
        this.action = action2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }
}
