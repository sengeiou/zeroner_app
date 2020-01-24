package com.iwown.device_module.common.Bluetooth.receiver.iv.dao;

import android.database.Cursor;
import com.google.android.gms.fitness.data.Field;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.common.sql.TB_v3_sport_type_data;
import java.util.HashMap;
import java.util.Map;
import org.litepal.crud.DataSupport;

public class V3_sport_type_data_biz {
    public static Map<String, Integer> queryDataFromTB(String uid, int year, int month, int day) {
        Map<String, Integer> map = new HashMap<>();
        try {
            Cursor c = DataSupport.findBySQL(" select year*month+day as au, uid,sum(steps) as steps , sum(calorie) as calo  from tb_v3_sport_type_data  where uid = " + uid + " and year = " + year + " and month = " + month + " and day = " + day + "");
            if (c != null) {
                while (c.moveToNext()) {
                    int steps = c.getInt(c.getColumnIndex("steps"));
                    int calories = c.getInt(c.getColumnIndex("calo"));
                    map.put("time_stamp", Integer.valueOf(c.getInt(c.getColumnIndex("au"))));
                    map.put("steps", Integer.valueOf(steps));
                    map.put(Field.NUTRIENT_CALORIES, Integer.valueOf(calories));
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            map.put("steps", Integer.valueOf(0));
            map.put(Field.NUTRIENT_CALORIES, Integer.valueOf(0));
        }
        return map;
    }

    public int queryDataByDate(String uid, int year, int month, int day) {
        try {
            return DataSupport.where(" uid=? AND year=? AND month=? AND day=? ", uid + "", year + "", month + "", day + "").count(TB_v3_sport_type_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public int deleteDataByDay(long uid, long timeStamp) {
        try {
            return DataSupport.deleteAll(TB_v3_sport_type_data.class, " uid=? and time= ?", uid + "", timeStamp + "");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public int updata_totalSport(String uid, int year, int month, int day) {
        int index = 0;
        try {
            Cursor c = DataSupport.findBySQL("insert or replace into tb_v3_sport_total_data (time_stamp,uid,total_steps,total_calorie)  select year*month+day as au, uid,sum(steps) as steps , sum(calorie) as calo  from tb_v3_sport_type_data  where uid = " + uid + " and year = " + year + " and month = " + month + " and day = " + day + "");
            if (c != null) {
                index = c.getCount();
            }
            c.close();
            return index;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }
}
