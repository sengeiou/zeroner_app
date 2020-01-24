package com.iwown.device_module.device_setting.configure.eventbus;

public class UpdateConfigUI {
    public static final String Config_Battery_Update = "UpdateConfigUI.Config_Battery_Update";
    public static final String Config_Device_Firmware_Version = "UpdateConfigUI.Config_Device_Firmware_Version";
    public static final String Config_Device_Fragment_Visable = "UpdateConfigUI.Config_Device_Fragment_Visable";
    public static final String Config_Model_Down = "UpdateConfigUI.Config_Model_Down";
    private String action;

    public UpdateConfigUI(String action2) {
        this.action = action2;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action2) {
        this.action = action2;
    }
}
