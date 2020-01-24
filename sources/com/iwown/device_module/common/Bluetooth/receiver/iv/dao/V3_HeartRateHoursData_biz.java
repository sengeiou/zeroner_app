package com.iwown.device_module.common.Bluetooth.receiver.iv.dao;

import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_hours;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class V3_HeartRateHoursData_biz {
    public int query_data_exist(String uid, int year, int month, int day, int hours, String dataFrom) {
        try {
            Log.d("testHeart", uid + " : " + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day + ":" + hours + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dataFrom);
            int index = DataSupport.where(" uid=? and year=? and month=? and day=? and hours=? and data_from=?", uid, year + "", month + "", day + "", hours + "", dataFrom + "").count(TB_v3_heartRate_data_hours.class);
            Log.d("testHeart", "index is " + index);
            return index;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static int queryDataExist(String uid, long recordDate, String dataFrom) {
        try {
            int index = DataSupport.where(" uid=? and record_date=? and data_from=?", uid, recordDate + "", dataFrom + "").count(TB_v3_heartRate_data_hours.class);
            Log.d("testHeart", "index is " + index);
            return index;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static List<TB_v3_heartRate_data_hours> query_data(String uid, int year, int month, int day, int hours) {
        try {
            new ArrayList();
            return DataSupport.where(" uid=? and year=? and month=? and day=? and hours=? ", uid, year + "", month + "", day + "", hours + "").order("hours desc").find(TB_v3_heartRate_data_hours.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public List<TB_v3_heartRate_data_hours> queryUpload(long uid) {
        return DataSupport.where("uid=? and _uploaded=?", String.valueOf(uid), "0").find(TB_v3_heartRate_data_hours.class);
    }
}
