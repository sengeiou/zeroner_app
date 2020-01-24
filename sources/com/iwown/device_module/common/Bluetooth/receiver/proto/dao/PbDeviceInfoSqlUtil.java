package com.iwown.device_module.common.Bluetooth.receiver.proto.dao;

import android.content.Context;
import android.text.TextUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.PbBaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import org.litepal.crud.DataSupport;

public class PbDeviceInfoSqlUtil {
    public static PbBaseInfo getBaseInfoByKey(String key, long newUID, String data_from) {
        return (PbBaseInfo) DataSupport.where("uid=? and key=? and data_form=?", newUID + "", key + "", data_from + "").findFirst(PbBaseInfo.class);
    }

    public static void updateDeviceBaseInfo(Context context, String key, String content) {
        if (!TextUtils.isEmpty(PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device))) {
            String uid = PrefUtil.getLong(context, UserAction.User_Uid) + "";
            String dataFrom = PrefUtil.getString(context, BleAction.Bluetooth_Device_Name_Current_Device);
            PbBaseInfo baseInfo = new PbBaseInfo();
            baseInfo.setUid(uid);
            baseInfo.setData_form(dataFrom);
            baseInfo.setKey(key);
            baseInfo.setContent(content);
            baseInfo.saveOrUpdate("uid=? and key=? and data_form=?", uid, key, dataFrom);
        }
    }

    public static PbBaseInfo getDeviceBaseInfoByKey(String key) {
        return getBaseInfoByKey(key, PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
    }
}
