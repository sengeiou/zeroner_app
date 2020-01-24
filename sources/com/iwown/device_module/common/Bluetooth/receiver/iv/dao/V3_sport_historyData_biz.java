package com.iwown.device_module.common.Bluetooth.receiver.iv.dao;

import android.database.Cursor;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.TB_v3_sport_total_data;
import com.iwown.device_module.common.sql.TB_v3_walk;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.litepal.crud.DataSupport;

public class V3_sport_historyData_biz {
    public static int query_data_exist(String uid, int year, int month, int day) {
        try {
            return DataSupport.where(" uid=? AND year=? and month=? and day=? ", uid, year + "", month + "", day + "").count(TB_v3_sport_total_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static int query_data_exist(String uid, long timeStamp) {
        try {
            return DataSupport.where("  uid=? AND time_stamp=? ", uid, timeStamp + "").count(TB_v3_sport_total_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static TB_v3_sport_total_data query_data_by_timeStamp(String uid, long timeStamp) {
        try {
            List<TB_v3_sport_total_data> data = DataSupport.where("  uid=? AND time_stamp=? ", uid, timeStamp + "").find(TB_v3_sport_total_data.class);
            if (data.size() > 0) {
                return (TB_v3_sport_total_data) data.get(0);
            }
            return null;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public int deleteDatabyDate(int year, int month, int day, long uid) {
        try {
            return DataSupport.deleteAll(TB_v3_sport_total_data.class, " year=? AND month=? AND day=? AND uid=? ", year + "", month + "", day + "", uid + "");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static int deleteDatabyDate(long time_stamp, long uid) {
        try {
            return DataSupport.deleteAll(TB_v3_sport_total_data.class, " uid=? AND time_stamp=? ", uid + "", time_stamp + "");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public int query_totalCal(String field, String uid, int week) {
        try {
            return ((Integer) DataSupport.where(" uid=? AND week=?", uid, week + "").sum(TB_v3_sport_total_data.class, field, Integer.TYPE)).intValue();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public int query_UPLOAD(String email, String year, String month, String day) {
        int index = 0;
        try {
            String s = year + month + day;
            Cursor c = DataSupport.findBySQL("select COUNT(*) from(select * from tb_v3_sport_total_data where uid= " + email + " and _uploaded= 0 )where  year||month||day != '" + s + "'");
            if (c != null && c.moveToFirst()) {
                index = c.getInt(0);
            }
            c.close();
            KLog.i("index = " + index + "//uid = " + email + "//s=" + s);
            return index;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return -1;
        }
    }

    public boolean queryDatabyDate(int year, int month, int day, long uid) {
        boolean z = false;
        try {
            if (DataSupport.where(" year=? AND month=? AND day=? AND uid=? ", year + "", month + "", day + "", uid + "").find(TB_v3_sport_total_data.class).size() > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return z;
        }
    }

    public List<TB_v3_sport_total_data> query_UPLOAD1(String email, String year, String month, String day) {
        try {
            List<TB_v3_sport_total_data> list = new ArrayList<>();
            Cursor c = DataSupport.findBySQL("select * from(select * from tb_v3_sport_total_data where uid= " + email + " and _uploaded= 0 )where  year||month||day != '" + (year + month + day) + "'");
            if (c != null) {
                KLog.i("c = " + c.getCount());
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    TB_v3_sport_total_data entity = new TB_v3_sport_total_data();
                    entity.set_uploaded(c.getInt(c.getColumnIndex("_uploaded")));
                    entity.setUid(c.getLong(c.getColumnIndex(UserConst.UID)));
                    entity.setTotal_steps(c.getInt(c.getColumnIndex("total_steps")));
                    entity.setTotal_calorie(c.getFloat(c.getColumnIndex("total_calorie")));
                    entity.setYear(c.getInt(c.getColumnIndex("year")));
                    entity.setMonth(c.getInt(c.getColumnIndex("month")));
                    entity.setDay(c.getInt(c.getColumnIndex("day")));
                    entity.setWeek(c.getInt(c.getColumnIndex("week")));
                    entity.setDetail_data(c.getString(c.getColumnIndex("detail_data")));
                    entity.setDay_goal_calo(c.getInt(c.getColumnIndex("day_goal_calo")));
                    entity.setReserved(c.getString(c.getColumnIndex("reserved")));
                    entity.setTime_stamp(c.getLong(c.getColumnIndex("time_stamp")));
                    list.add(entity);
                    c.moveToNext();
                }
            }
            c.close();
            return list;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public void updateExportFlag(String uid) {
        try {
            KLog.i("进入更改upload字段 ");
            DateUtil date = new DateUtil(new Date());
            String s = "'" + date.getYear() + date.getMonth() + date.getDay() + "'";
            KLog.i("s = " + s);
            TB_v3_sport_total_data data = new TB_v3_sport_total_data();
            data.set_uploaded(1);
            data.updateAll("year||month||day !=?  and uid=? ", s, uid);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static void saveTBWalk(int year, int month, int day, String strCalorie, String strDistance, String strSteps) {
        try {
            float calorie = Float.parseFloat(strCalorie);
            float distance = Float.parseFloat(strDistance);
            int steps = Integer.parseInt(strSteps);
            long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
            DateUtil dateUtil = new DateUtil(year, month, day, 8, 0, 0);
            String dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
            List<TB_v3_walk> list = DataSupport.where("uid=? and date=? and data_from=?", uid + "", dateUtil.getSyyyyMMddDate() + "", dataFrom + "").order("step desc").find(TB_v3_walk.class);
            if (list.size() <= 0) {
                TB_v3_walk walk = new TB_v3_walk();
                walk.setUid(uid);
                walk.setRecord_date(dateUtil.getUnixTimestamp());
                walk.setData_from(dataFrom);
                walk.setStep(steps);
                walk.setDate(dateUtil.getSyyyyMMddDate());
                walk.setCalorie(calorie);
                walk.setDistance(distance);
                walk.set_uploaded(0);
                walk.save();
            } else if (list.size() > 0) {
                TB_v3_walk walk2 = new TB_v3_walk();
                walk2.setCalorie(calorie);
                walk2.setDistance(distance);
                walk2.setStep(steps);
                walk2.set_uploaded(0);
                walk2.updateAll("uid=? and date=? and data_from=?", uid + "", dateUtil.getSyyyyMMddDate() + "", dataFrom + "");
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
