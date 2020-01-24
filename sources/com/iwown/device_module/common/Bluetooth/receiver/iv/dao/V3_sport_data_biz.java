package com.iwown.device_module.common.Bluetooth.receiver.iv.dao;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import org.litepal.crud.DataSupport;

public class V3_sport_data_biz {
    public int query_UPLOAD(String uId) {
        try {
            return DataSupport.where(" uid=? AND _uploaded=?", uId, "0").count(TB_v3_sport_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public int query_Calorie(String uid, int year, int month, int day) {
        try {
            return ((Integer) DataSupport.where(" uid=? AND year=? and month=? and day=? ", uid, year + "", month + "", day + "").sum(TB_v3_sport_data.class, "calorie", Integer.TYPE)).intValue();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static boolean querySportTypeAndTimeExist(long uid, int type, int start, int end, int year, int month, int day) {
        try {
            return DataSupport.where(" uid=? AND sport_type=? and start_time=? and end_time=? and year=? and month=? and day=? ", new StringBuilder().append(uid).append("").toString(), new StringBuilder().append(type).append("").toString(), new StringBuilder().append(start).append("").toString(), new StringBuilder().append(end).append("").toString(), new StringBuilder().append(year).append("").toString(), new StringBuilder().append(month).append("").toString(), new StringBuilder().append(day).append("").toString()).count(TB_v3_sport_data.class) > 0;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return false;
        }
    }
}
