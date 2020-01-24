package com.iwown.device_module.common.Bluetooth.receiver.mtk.dao;

import android.content.Context;
import android.text.TextUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import org.litepal.crud.DataSupport;

public class Mtk_DeviceBaseInfoSqlUtil {
    public static Mtk_DeviceBaseInfo getBaseInfoByKey(String key, long newUID, String data_from) {
        return (Mtk_DeviceBaseInfo) DataSupport.where("uid=? and key=? and data_form=?", newUID + "", key + "", data_from + "").findFirst(Mtk_DeviceBaseInfo.class);
    }

    public static void updateDeviceBaseInfo(Context context, String key, String content) {
        if (!TextUtils.isEmpty(PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device))) {
            Mtk_DeviceBaseInfo baseInfoByKey = getBaseInfoByKey(key, PrefUtil.getLong(context, UserAction.User_Uid), PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device));
            if (baseInfoByKey == null) {
                baseInfoByKey = new Mtk_DeviceBaseInfo();
                baseInfoByKey.setUid(PrefUtil.getLong(context, UserAction.User_Uid) + "");
                baseInfoByKey.setData_form(PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device));
                baseInfoByKey.setKey(key);
            }
            baseInfoByKey.setContent(content);
            baseInfoByKey.save();
        }
    }

    public static Mtk_DeviceBaseInfo getDeviceBaseInfoByKey(String key) {
        return getBaseInfoByKey(key, PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
    }

    public static TB_SLEEP_Final_DATA getSleepDataByDate1(Context context, String date) {
        return (TB_SLEEP_Final_DATA) DataSupport.where("uid=? and date =? and data_from=?", ContextUtil.getUID(), date, PrefUtil.getString(context, BleAction.Bluetooth_Device_Name)).findFirst(TB_SLEEP_Final_DATA.class);
    }
}
