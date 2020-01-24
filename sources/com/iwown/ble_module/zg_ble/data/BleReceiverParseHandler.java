package com.iwown.ble_module.zg_ble.data;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.DataDate;
import com.iwown.ble_module.zg_ble.data.model.bh_totalinfo;

public class BleReceiverParseHandler {
    public static String parse88(byte[] data) {
        DataDate dateObj = DataDate.getInstance();
        dateObj.setData(data, data[2]);
        if (data[2] == -125) {
            return JsonTool.toJson(dateObj.getDataDateObject());
        }
        return null;
    }

    public static String parse89_81(byte[] data) {
        byte[] totaldataBinary = new byte[16];
        for (int i = 4; i < 20; i++) {
            totaldataBinary[i - 4] = data[i];
        }
        return JsonTool.toJson(bh_totalinfo.GetInstanceFromBytes(totaldataBinary));
    }
}
