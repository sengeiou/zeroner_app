package com.iwown.device_module.device_alarm_schedule.common;

import android.content.Context;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class AlarmManager {
    private static final String TAG = "AlarmManager";

    public static boolean isNoIn(int contrastData, int[] data, int endNum) {
        for (int i = 0; i < endNum; i++) {
            if (data[i] == contrastData) {
                return false;
            }
        }
        return true;
    }

    public static int isAddAlarm(String UID, int maxNum) {
        new ArrayList();
        List<TB_Alarmstatue> alarmList = DataSupport.where("UID=?", UID).find(TB_Alarmstatue.class);
        int num = alarmList.size();
        if (num < maxNum) {
            int[] idx = new int[num];
            for (int i = 0; i < num; i++) {
                idx[i] = ((TB_Alarmstatue) alarmList.get(i)).getAc_Idx();
            }
            for (int i2 = 0; i2 < maxNum; i2++) {
                if (isNoIn(i2, idx, num)) {
                    KLog.d(TAG, "isAddAlarm: " + i2);
                    return i2;
                }
            }
        }
        KLog.d(TAG, "isAddAlarm: -1");
        return -1;
    }

    public static void editAlarm(String UID, int id, int weekReapt, int hour, int minute, String item, String remind, boolean isOpen, int shakeMode, int shakeNum) {
        new ArrayList();
        List<TB_Alarmstatue> list = DataSupport.where("UID=? AND Ac_Idx=?", UID, String.valueOf(id)).find(TB_Alarmstatue.class);
        if (list.size() == 1) {
            TB_Alarmstatue data = (TB_Alarmstatue) list.get(0);
            if (id == 0) {
                data.setToDefault("id");
                data.setToDefault("Ac_Idx");
            } else {
                data.setId(id);
                data.setAc_Idx(id);
            }
            if (weekReapt == 0) {
                data.setToDefault("Ac_Conf");
                data.setAc_Conf(0);
            } else {
                data.setAc_Conf(weekReapt);
            }
            if (hour == 0) {
                data.setToDefault("Ac_Hour");
                data.setAc_Hour(0);
            } else {
                data.setAc_Hour(hour);
            }
            if (minute == 0) {
                data.setToDefault("Ac_Minute");
                data.setAc_Minute(0);
            } else {
                data.setAc_Minute(minute);
            }
            data.setAc_String(item);
            data.setUID(UID);
            data.setRemind(remind);
            data.setZg_mode(shakeMode);
            data.setZg_number(shakeNum);
            data.setOpenState(isOpen ? 1 : 0);
            KLog.e(TAG, shakeMode + "/" + shakeNum);
            if (isOpen) {
                data.setOpenState(1);
                KLog.e(TAG, data.toString());
                KLog.e(TAG, data.saveOrUpdate("UID=? AND Ac_Idx=?", UID, String.valueOf(id)) + "");
                AlarmCommandUtil.writeAlarm(id, weekReapt, hour, minute, item);
            } else {
                data.setToDefault("openState");
                data.saveOrUpdate("UID=? AND Ac_Idx=?", UID, String.valueOf(id));
                AlarmCommandUtil.closeAlarm(id);
            }
            KLog.e(TAG, "editAlarm");
        }
    }

    public static void addAlarm(String UID, int id, int weekReapt, int hour, int minute, String item, String remind, int shakeMode, int shakeNum) {
        TB_Alarmstatue data = new TB_Alarmstatue();
        if (id == 0) {
            data.setToDefault("id");
            data.setToDefault("Ac_Idx");
        } else {
            data.setId(id);
            data.setAc_Idx(id);
        }
        if (weekReapt == 0) {
            data.setToDefault("Ac_Conf");
        } else {
            data.setAc_Conf(weekReapt);
        }
        if (hour == 0) {
            data.setToDefault("Ac_Hour");
        } else {
            data.setAc_Hour(hour);
        }
        if (minute == 0) {
            data.setToDefault("Ac_Minute");
        } else {
            data.setAc_Minute(minute);
        }
        data.setAc_String(item);
        data.setOpenState(1);
        data.setUID(UID);
        data.setRemind(remind);
        data.setZg_mode(shakeMode);
        data.setZg_number(shakeNum);
        data.save();
        KLog.d(TAG, "addAlarm");
        AlarmCommandUtil.writeAlarm(id, weekReapt, hour, minute, item);
    }

    public static void openAlarm(String UID, int id) {
        new ArrayList();
        List<TB_Alarmstatue> dataList = DataSupport.where("UID=? AND Ac_Idx=?", UID, String.valueOf(id)).find(TB_Alarmstatue.class);
        if (1 == dataList.size()) {
            TB_Alarmstatue data = (TB_Alarmstatue) dataList.get(0);
            editAlarm(UID, data.getAc_Idx(), data.getAc_Conf(), data.getAc_Hour(), data.getAc_Minute(), data.getAc_String(), data.getRemind(), true, data.getZg_mode(), data.getZg_number());
            KLog.d(TAG, "openAlarm");
        }
    }

    public static void closeAlarm(String UID, int id) {
        new ArrayList();
        List<TB_Alarmstatue> dataList = DataSupport.where("UID=? AND Ac_Idx=?", UID, String.valueOf(id)).find(TB_Alarmstatue.class);
        if (1 == dataList.size()) {
            TB_Alarmstatue data = (TB_Alarmstatue) dataList.get(0);
            editAlarm(UID, data.getAc_Idx(), data.getAc_Conf(), data.getAc_Hour(), data.getAc_Minute(), data.getAc_String(), data.getRemind(), false, data.getZg_mode(), data.getZg_number());
            KLog.d(TAG, "closeAlarm");
        }
    }

    public static void deleteAlarm(String UID, int id) {
        DataSupport.deleteAll(TB_Alarmstatue.class, "UID=? AND Ac_Idx=?", UID, String.valueOf(id));
        AlarmCommandUtil.closeAlarm(id);
        KLog.d(TAG, "deleteAlarm");
    }

    public static void updateAlarmFirst(Context context) {
        KLog.d("V3_alarmData_biz", "updateAlarmFirst");
        if (ScheduleManager.getInstance().isChangeDeviceAlarm()) {
            updateAllAlarm(String.valueOf(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid)));
        }
    }

    public static List<TB_Alarmstatue> getAllAlarmData() {
        new ArrayList();
        return DataSupport.where("UID=?", String.valueOf(ContextUtil.getUID())).find(TB_Alarmstatue.class);
    }

    public static void updateAllAlarm(String UID) {
        int count = DataSupport.where("UID=? AND openState=?", UID, "1").count(TB_Alarmstatue.class);
        if (count == 0) {
            KLog.d("V3_alarmData_biz", "0 == count");
            for (int i = 0; i < 8; i++) {
                AlarmCommandUtil.closeAlarm(i);
            }
            return;
        }
        KLog.d("V3_alarmData_biz", "count = " + count);
        List<TB_Alarmstatue> data = DataSupport.where("UID=? AND openState=?", UID, "1").find(TB_Alarmstatue.class);
        int count2 = data.size();
        KLog.d("V3_alarmData_biz", "count = " + count2);
        for (int i2 = 0; i2 < 6; i2++) {
            boolean isEditAlarm = false;
            int j = 0;
            while (true) {
                if (j >= count2) {
                    break;
                } else if (i2 == ((TB_Alarmstatue) data.get(j)).getAc_Idx()) {
                    isEditAlarm = true;
                    TB_Alarmstatue tbData = (TB_Alarmstatue) data.get(j);
                    KLog.d("V3_alarmData_biz", "writeAlarm = " + i2);
                    AlarmCommandUtil.writeAlarm(tbData.getAc_Idx(), tbData.getAc_Conf(), tbData.getAc_Hour(), tbData.getAc_Minute(), tbData.getAc_String());
                    break;
                } else {
                    j++;
                }
            }
            if (!isEditAlarm) {
                KLog.d("V3_alarmData_biz", "closeAlarm = " + i2);
                AlarmCommandUtil.closeAlarm(i2);
            }
        }
        int[] alarmID = new int[count2];
        for (int i3 = 0; i3 < count2; i3++) {
            alarmID[i3] = ((TB_Alarmstatue) data.get(i3)).getAc_Idx();
        }
        for (int i4 = 0; i4 < 8; i4++) {
            boolean isEditAlarm2 = true;
            int j2 = 0;
            while (true) {
                if (j2 >= count2) {
                    break;
                } else if (i4 == alarmID[j2]) {
                    isEditAlarm2 = false;
                    break;
                } else {
                    j2++;
                }
            }
            if (isEditAlarm2) {
                AlarmCommandUtil.closeAlarm(i4);
            }
        }
    }
}
