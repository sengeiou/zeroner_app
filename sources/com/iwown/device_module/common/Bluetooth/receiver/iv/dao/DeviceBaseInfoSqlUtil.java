package com.iwown.device_module.common.Bluetooth.receiver.iv.dao;

import android.content.Context;
import android.text.TextUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.DeviceBaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import org.litepal.crud.DataSupport;

public class DeviceBaseInfoSqlUtil {
    public static DeviceBaseInfo getBaseInfoByKey(String key, long newUID, String data_from) {
        return (DeviceBaseInfo) DataSupport.where("uid=? and key=? and data_form=?", newUID + "", key + "", data_from + "").findFirst(DeviceBaseInfo.class);
    }

    public static void updateDeviceBaseInfo(Context context, String key, String content) {
        if (!TextUtils.isEmpty(PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device))) {
            DeviceBaseInfo baseInfoByKey = getBaseInfoByKey(key, PrefUtil.getLong(context, UserAction.User_Uid), PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device));
            if (baseInfoByKey == null) {
                baseInfoByKey = new DeviceBaseInfo();
                baseInfoByKey.setUid(PrefUtil.getLong(context, UserAction.User_Uid) + "");
                baseInfoByKey.setData_form(PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device));
                baseInfoByKey.setKey(key);
            }
            baseInfoByKey.setContent(content);
            baseInfoByKey.save();
        }
    }

    public static DeviceBaseInfo getDeviceBaseInfoByKey(String key) {
        return getBaseInfoByKey(key, PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
    }
}
