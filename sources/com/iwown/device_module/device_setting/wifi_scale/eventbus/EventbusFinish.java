package com.iwown.device_module.device_setting.wifi_scale.eventbus;

public class EventbusFinish {
    public static final int Data_BelongTo_Fail = 4;
    public static final int Finish_Activity = 1;
    public static final int LoadData_Activity = 2;
    public static final int Reload_WeightData = 3;
    public static final int WifiScale_Active = 12;
    public static final int WifiScale_Clean_Fail = 7;
    public static final int WifiScale_Clean_Wifi = 6;
    public static final int WifiScale_Data_Size = 9;
    public static final int WifiScale_Finish = 5;
    public static final int WifiScale_Not_BelongTo = 11;
    public static final int WifiScale_Not_BelongTo_WeightPage = 13;
    public static final int WifiScale_Server_NoData = 10;
    public static final int WifiScale_Setting_Activity_Finish = 14;
    public static final int WifiScale_Show_Data = 8;
    public static final int WifiScale_Weight_Updata = 15;
    private int action;
    private Object data;

    public Object getData() {
        return this.data;
    }

    public void setData(Object data2) {
        this.data = data2;
    }

    public EventbusFinish() {
    }

    public EventbusFinish(int action2) {
        this.action = action2;
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int action2) {
        this.action = action2;
    }
}
