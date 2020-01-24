package com.iwown.device_module.common.Bluetooth.receiver.zg.dao;

import android.content.Context;
import com.iwown.ble_module.zg_ble.data.model.bh_totalinfo;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import org.litepal.crud.DataSupport;

public class ZG_Sql_Utils {
    public static ZG_BaseInfo getBaseInfoByKey(String key_hardinfo, long newUID, String derviceName) {
        return (ZG_BaseInfo) DataSupport.where("uid=? and key=? and data_form=?", newUID + "", key_hardinfo + "", derviceName + "").findFirst(ZG_BaseInfo.class);
    }

    public static TB_SLEEP_Final_DATA getSleepDataByDate1(Context context, DateUtil dateUtil) {
        return (TB_SLEEP_Final_DATA) DataSupport.where("uid=? and date =? and data_from=?", ContextUtil.getUID(), dateUtil.getSyyyyMMddDate(), PrefUtil.getString(context, BleAction.Bluetooth_Device_Name)).findFirst(TB_SLEEP_Final_DATA.class);
    }

    public static void updateTotalinfoSleep(long uid, String deviceName) {
        updateTotalinfo(uid, deviceName, 1);
    }

    public static void updateTotalinfoSport(long uid, String deviceName) {
        updateTotalinfo(uid, deviceName, 2);
    }

    public static void updateTotalinfoHeart(long uid, String deviceName) {
        updateTotalinfo(uid, deviceName, 3);
    }

    private static void updateTotalinfo(long uid, String deviceName, int type) {
        ZG_BaseInfo baseInfo = (ZG_BaseInfo) DataSupport.where("uid=? and key=? and data_form=?", uid + "", ZG_BaseInfo.key_last_totaldata, deviceName + "").findFirst(ZG_BaseInfo.class);
        if (baseInfo != null) {
            bh_totalinfo totalinfo = (bh_totalinfo) JsonUtils.fromJson(baseInfo.getContent(), bh_totalinfo.class);
            if (totalinfo != null) {
                if (type == 1) {
                    totalinfo.setSleepType(2);
                } else if (type == 2) {
                    totalinfo.setSportType(2);
                } else if (type == 3) {
                    totalinfo.setHeartType(2);
                }
                baseInfo.setContent(JsonTool.toJson(totalinfo));
                baseInfo.updateAll("uid=? and key=? and data_form=?", uid + "", ZG_BaseInfo.key_last_totaldata, deviceName + "");
            }
        }
    }
}
