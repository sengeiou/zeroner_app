package com.iwown.device_module.device_alarm_schedule.common;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.iwown.device_module.common.sql.TB_Alarmstatue;
import com.iwown.device_module.common.sql.TB_schedulestatue;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_alarm_schedule.iv.bean.RecentNotification;
import com.iwown.device_module.device_alarm_schedule.iv.biz.V3_scheduleData_biz;
import com.iwown.device_module.device_alarm_schedule.utils.AddScheduleUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.litepal.crud.DataSupport;

public class ScheduleManager {
    public static final int SCHEDULE_ZG_LIMIT = 3;
    public static final int Schedule_Manager_Out_All_Num = 1;
    public static final int Schedule_Manager_Out_Everyday = 2;
    public static final int Schedule_Manager_Success = 0;
    private static String TAG = "Schedule";
    private static String changeDeviceAddr = "changedeviceaddr";
    private static String changeDeviceFileName = "zeroner_changedevice";
    private static String currentVersion = "v3.3.1";
    private static ScheduleManager mScheduleManager;
    private static String updateAllAlarm = "updateallalarm";
    private static String versionAlarm = "alarmversion";
    private static String versionSchedule = "scheduleversion";
    private Context mContext;

    public static void initData(Context context) {
        if (mScheduleManager == null) {
            mScheduleManager = new ScheduleManager(context);
        }
    }

    public static synchronized ScheduleManager getInstance() {
        ScheduleManager scheduleManager;
        synchronized (ScheduleManager.class) {
            scheduleManager = mScheduleManager;
        }
        return scheduleManager;
    }

    private ScheduleManager(Context context) {
        this.mContext = context;
    }

    public void setQueueManagerResult(IQueueManagerResultListener queueManagerResult) {
        ScheduleCommandUtil.getInstance().setQueueManagerResult(queueManagerResult);
    }

    public String getUID() {
        return String.valueOf(PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid));
    }

    public boolean isNoNeedUpdateLastScheduleData() {
        return ScheduleCommandUtil.getInstance().getNoException();
    }

    public TB_schedulestatue getCurEffectLastTime() {
        int afterSize;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        int hour = calendar.get(11);
        int min = calendar.get(12);
        if (DataSupport.where("UID = ? AND dates = ?", String.valueOf(getUID()), AddScheduleUtil.getTBDatesString(year, month, day)).count(TB_schedulestatue.class) == 0) {
            afterSize = DataSupport.where("UID = ? AND dates > ?", String.valueOf(getUID()), AddScheduleUtil.getTBDatesString(year, month, day)).count(TB_schedulestatue.class);
        } else {
            if (DataSupport.where("UID = ? AND dates = ? AND times > ?", String.valueOf(getUID()), AddScheduleUtil.getTBDatesString(year, month, day), AddScheduleUtil.getTBTimesString(hour, min)).count(TB_schedulestatue.class) == 0) {
                afterSize = DataSupport.where("UID = ? AND dates > ?", String.valueOf(getUID()), AddScheduleUtil.getTBDatesString(year, month, day)).count(TB_schedulestatue.class);
            } else {
                List<TB_schedulestatue> data = DataSupport.where("UID = ? AND dates = ? AND times > ?", String.valueOf(getUID()), AddScheduleUtil.getTBDatesString(year, month, day), AddScheduleUtil.getTBTimesString(hour, min)).find(TB_schedulestatue.class);
                int minTime = 999999999;
                int minIndex = 0;
                for (int i = 0; i < data.size(); i++) {
                    if (((TB_schedulestatue) data.get(i)).getTimes() < minTime) {
                        minTime = ((TB_schedulestatue) data.get(i)).getTimes();
                        minIndex = i;
                    }
                }
                return (TB_schedulestatue) data.get(minIndex);
            }
        }
        if (afterSize == 0) {
            return null;
        }
        List<TB_schedulestatue> data2 = DataSupport.where("UID = ? AND dates > ?", String.valueOf(getUID()), AddScheduleUtil.getTBDatesString(year, month, day)).find(TB_schedulestatue.class);
        int minDate = 999999999;
        int sizeData = data2.size();
        for (int i2 = 0; i2 < sizeData; i2++) {
            if (((TB_schedulestatue) data2.get(i2)).getDates() < minDate) {
                minDate = ((TB_schedulestatue) data2.get(i2)).getDates();
            }
        }
        List<TB_schedulestatue> minData = new ArrayList<>();
        int count = 0;
        for (int i3 = 0; i3 < sizeData; i3++) {
            if (((TB_schedulestatue) data2.get(i3)).getDates() == minDate) {
                count++;
                minData.add(data2.get(i3));
            }
        }
        if (1 == count) {
            return (TB_schedulestatue) minData.get(0);
        }
        int sizeData2 = minData.size();
        int minTime2 = 99999999;
        int minIndex2 = 0;
        for (int i4 = 0; i4 < sizeData2; i4++) {
            if (((TB_schedulestatue) data2.get(i4)).getTimes() < minTime2) {
                minTime2 = ((TB_schedulestatue) data2.get(i4)).getTimes();
                minIndex2 = i4;
            }
        }
        return (TB_schedulestatue) minData.get(minIndex2);
    }

    public RecentNotification getRecentNotification() {
        RecentNotification recentNotification = new RecentNotification();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(1);
        int month = calendar.get(2) + 1;
        int day = calendar.get(5);
        List<TB_schedulestatue> selectedDayScheduleDataList = getSelectedDayScheduleData(year, month, day);
        int hour = calendar.get(11);
        int minute = calendar.get(12);
        int weekDay = calendar.get(7) - 1;
        if (selectedDayScheduleDataList.size() > 0) {
            Iterator it = selectedDayScheduleDataList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                TB_schedulestatue tb_schedulestatue = (TB_schedulestatue) it.next();
                if (tb_schedulestatue.getHour() <= hour) {
                    if (tb_schedulestatue.getHour() == hour && tb_schedulestatue.getMinute() >= minute) {
                        recentNotification.setType(1);
                        recentNotification.setTitle(tb_schedulestatue.getText());
                        recentNotification.setRemind(tb_schedulestatue.getRemind());
                        recentNotification.setYear(tb_schedulestatue.getYear());
                        recentNotification.setMonth(tb_schedulestatue.getMonth());
                        recentNotification.setDay(tb_schedulestatue.getDay());
                        recentNotification.setHour(tb_schedulestatue.getHour());
                        recentNotification.setMinute(tb_schedulestatue.getMinute());
                        recentNotification.setTBSchedulestatue(tb_schedulestatue);
                        break;
                    }
                } else {
                    recentNotification.setType(1);
                    recentNotification.setTitle(tb_schedulestatue.getText());
                    recentNotification.setRemind(tb_schedulestatue.getRemind());
                    recentNotification.setYear(tb_schedulestatue.getYear());
                    recentNotification.setMonth(tb_schedulestatue.getMonth());
                    recentNotification.setDay(tb_schedulestatue.getDay());
                    recentNotification.setHour(tb_schedulestatue.getHour());
                    recentNotification.setMinute(tb_schedulestatue.getMinute());
                    recentNotification.setTBSchedulestatue(tb_schedulestatue);
                    break;
                }
            }
        }
        List<TB_Alarmstatue> allAlarmData = getSelectedDayAlarmData(year, month, day, weekDay);
        if (allAlarmData.size() > 0) {
            Iterator it2 = allAlarmData.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                TB_Alarmstatue tb_alarmstatue = (TB_Alarmstatue) it2.next();
                if (tb_alarmstatue.getOpenState() == 1) {
                    if (tb_alarmstatue.getAc_Hour() <= hour) {
                        if (tb_alarmstatue.getAc_Hour() == hour && tb_alarmstatue.getAc_Minute() >= minute) {
                            recentNotification.setType(2);
                            recentNotification.setTitle(tb_alarmstatue.getAc_String());
                            recentNotification.setRemind(tb_alarmstatue.getRemind());
                            recentNotification.setYear(year);
                            recentNotification.setMonth(month);
                            recentNotification.setDay(day);
                            recentNotification.setHour(tb_alarmstatue.getAc_Hour());
                            recentNotification.setMinute(tb_alarmstatue.getAc_Minute());
                            recentNotification.setTBAlarmstatue(tb_alarmstatue);
                            break;
                        }
                    } else {
                        recentNotification.setType(2);
                        recentNotification.setTitle(tb_alarmstatue.getAc_String());
                        recentNotification.setRemind(tb_alarmstatue.getRemind());
                        recentNotification.setYear(year);
                        recentNotification.setMonth(month);
                        recentNotification.setDay(day);
                        recentNotification.setHour(tb_alarmstatue.getAc_Hour());
                        recentNotification.setMinute(tb_alarmstatue.getAc_Minute());
                        recentNotification.setTBAlarmstatue(tb_alarmstatue);
                        break;
                    }
                }
            }
        }
        return recentNotification;
    }

    public List<TB_schedulestatue> getAllScheduleData() {
        List<TB_schedulestatue> listAll = DataSupport.where("UID = ? ", String.valueOf(getUID())).find(TB_schedulestatue.class);
        if (listAll.size() > 0) {
            Collections.sort(listAll, new Comparator<TB_schedulestatue>() {
                public int compare(TB_schedulestatue t1, TB_schedulestatue t2) {
                    return new Integer(t1.getTimes()).compareTo(Integer.valueOf(t2.getTimes()));
                }
            });
        }
        return listAll;
    }

    public List<TB_schedulestatue> getSelectedDayScheduleData(int year, int month, int day) {
        List<TB_schedulestatue> listAll = DataSupport.where("UID = ? AND year = ? AND month = ? AND day = ?", String.valueOf(getUID()), String.valueOf(year), String.valueOf(month), String.valueOf(day)).find(TB_schedulestatue.class);
        if (listAll.size() > 0) {
            Collections.sort(listAll, new Comparator<TB_schedulestatue>() {
                public int compare(TB_schedulestatue t1, TB_schedulestatue t2) {
                    return new Integer(t1.getTimes()).compareTo(Integer.valueOf(t2.getTimes()));
                }
            });
        }
        return listAll;
    }

    public List<TB_Alarmstatue> getAllAlarmData() {
        new ArrayList();
        List<TB_Alarmstatue> listAll = DataSupport.where("UID=?", String.valueOf(getUID())).find(TB_Alarmstatue.class);
        if (listAll.size() > 1) {
            Collections.sort(listAll, new Comparator<TB_Alarmstatue>() {
                public int compare(TB_Alarmstatue t1, TB_Alarmstatue t2) {
                    return new Integer((t1.getAc_Hour() * 60) + t1.getAc_Minute()).compareTo(Integer.valueOf((t2.getAc_Hour() * 60) + t2.getAc_Minute()));
                }
            });
        }
        return listAll;
    }

    public List<TB_Alarmstatue> getSelectedDayAlarmData(int year, int month, int day, int week) {
        new ArrayList();
        List<TB_Alarmstatue> listAll = DataSupport.where("UID=?", String.valueOf(getUID())).find(TB_Alarmstatue.class);
        int size = listAll.size();
        List<TB_Alarmstatue> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TB_Alarmstatue data = (TB_Alarmstatue) listAll.get(i);
            if (AddScheduleUtil.isAtAlarmWeek(week, data.getAc_Conf()) || (AddScheduleUtil.isTodayAlarm(data, year, month, day) && data.getAc_Conf() == 0)) {
                list.add(data);
            }
        }
        if (list.size() > 1) {
            Collections.sort(list, new Comparator<TB_Alarmstatue>() {
                public int compare(TB_Alarmstatue t1, TB_Alarmstatue t2) {
                    return new Integer((t1.getAc_Hour() * 60) + t1.getAc_Minute()).compareTo(Integer.valueOf((t2.getAc_Hour() * 60) + t2.getAc_Minute()));
                }
            });
        }
        return list;
    }

    public void editSchedule(int id, int year, int month, int day, int hour, int minute, String item, String remind, int shakeMode, int shakeNum) {
        TB_schedulestatue frontData = (TB_schedulestatue) DataSupport.find(TB_schedulestatue.class, (long) id);
        ContentValues values = new ContentValues();
        values.put("remind", remind);
        values.put("text", item);
        values.put("dates", Integer.valueOf(AddScheduleUtil.getTBDatesInt(year, month, day)));
        values.put("times", Integer.valueOf(AddScheduleUtil.getTBTimesInt(hour, minute)));
        values.put("year", Integer.valueOf(year));
        values.put("month", Integer.valueOf(month));
        values.put("day", Integer.valueOf(day));
        values.put("hour", Integer.valueOf(hour));
        values.put("minute", Integer.valueOf(minute));
        values.put("zg_mode", Integer.valueOf(shakeMode));
        values.put("zg_number", Integer.valueOf(shakeNum));
        DataSupport.update(TB_schedulestatue.class, values, (long) id);
        TB_schedulestatue afterData = (TB_schedulestatue) DataSupport.find(TB_schedulestatue.class, (long) id);
        afterData.save();
        ScheduleCommandUtil.edit(frontData, afterData);
        postEvent();
    }

    public void addScheduleNoQueue(int year, int month, int day, int hour, int minute, String item, String remind, int shakeMode, int shakeNum) {
        TB_schedulestatue tbData = new TB_schedulestatue();
        int times = AddScheduleUtil.getTBTimesInt(hour, minute);
        int dates = AddScheduleUtil.getTBDatesInt(year, month, day);
        tbData.setUID(String.valueOf(getUID()));
        tbData.setYear(year);
        tbData.setMonth(month);
        tbData.setDay(day);
        tbData.setHour(hour);
        tbData.setMinute(minute);
        tbData.setText(item);
        tbData.setRemind(remind);
        tbData.setTimes(times);
        tbData.setDates(dates);
        tbData.setZg_mode(shakeMode);
        tbData.setZg_number(shakeNum);
        KLog.e("  " + tbData.getZg_mode() + "  " + tbData.getZg_number());
        tbData.save();
        V3_scheduleData_biz.getInstance(this.mContext).writeScheduleNoResponse(year, month, day, hour, minute, item);
        postEvent();
    }

    public void addSchedule(int year, int month, int day, int hour, int minute, String item, String remind, int shakeMode, int shakeNum) {
        TB_schedulestatue tbData = new TB_schedulestatue();
        int times = AddScheduleUtil.getTBTimesInt(hour, minute);
        int dates = AddScheduleUtil.getTBDatesInt(year, month, day);
        tbData.setUID(String.valueOf(getUID()));
        tbData.setYear(year);
        tbData.setMonth(month);
        tbData.setDay(day);
        tbData.setHour(hour);
        tbData.setMinute(minute);
        tbData.setText(item);
        tbData.setRemind(remind);
        tbData.setTimes(times);
        tbData.setDates(dates);
        tbData.setZg_mode(shakeMode);
        tbData.setZg_number(shakeNum);
        tbData.save();
        ScheduleCommandUtil.add(tbData);
        postEvent();
    }

    public boolean getIsBusyWriting() {
        return ScheduleCommandUtil.getInstance().getIsBusyWriting();
    }

    public void deleteSchedule(TB_schedulestatue data) {
        DataSupport.delete(TB_schedulestatue.class, (long) data.getId());
        if (isOutCurrentTBdata(data)) {
            ScheduleCommandUtil.delete(data);
        }
        postEvent();
    }

    public void readScheduleInfo() {
        ScheduleCommandUtil.getInstance().readScheduleInfo();
    }

    private boolean isOutCurrentTBdata(TB_schedulestatue data) {
        return new DateUtil(data.getYear(), data.getMonth(), data.getDay(), data.getHour(), data.getMinute()).getTimestamp() > Calendar.getInstance().getTimeInMillis();
    }

    public int isAddSchedule(int year, int month, int day) {
        if (!BluetoothOperation.isZg()) {
            Calendar setCal = Calendar.getInstance();
            setCal.set(1, year);
            setCal.set(2, month - 1);
            setCal.set(5, day);
            String setDates = AddScheduleUtil.getTBDatesString(setCal.get(1), setCal.get(2) + 1, setCal.get(5));
            KLog.e("================" + setDates);
            int num = DataSupport.where("UID=? AND dates=?", String.valueOf(getUID()), setDates).count(TB_schedulestatue.class);
            KLog.d(TAG, "num = " + num);
            if (num == ScheduleCommandUtil.getInstance().getPerdaySetableNum()) {
                return 2;
            }
            Calendar curCal = Calendar.getInstance();
            String times = AddScheduleUtil.getTBTimesString(curCal.get(11), curCal.get(12));
            String dates = AddScheduleUtil.getTBDatesString(curCal.get(1), curCal.get(2) + 1, curCal.get(5));
            int num2 = DataSupport.where("UID=? AND dates > ?", String.valueOf(getUID()), dates).count(TB_schedulestatue.class) + DataSupport.where("UID=? AND dates=? AND times > ?", String.valueOf(getUID()), dates, times).count(TB_schedulestatue.class);
            KLog.d(TAG, "num = " + num2);
            if (num2 == ScheduleCommandUtil.getInstance().getMaxSetableNum()) {
                return 1;
            }
            return 0;
        } else if (ZGBaseUtils.getSchedulesCount() == 4) {
            return 3;
        } else {
            return 0;
        }
    }

    public int getMaxSetableNum() {
        return ScheduleCommandUtil.getInstance().getMaxSetableNum();
    }

    public int getPerdaySetableNum() {
        return ScheduleCommandUtil.getInstance().getPerdaySetableNum();
    }

    public int SyncDataAfterErr() {
        return ScheduleCommandUtil.getInstance().startSyncDataAfterErr();
    }

    public boolean getIsSyncDataAfterErr() {
        return ScheduleCommandUtil.getInstance().getIsSyncDataAfterErr();
    }

    public int getBluedToothTime() {
        return ScheduleCommandUtil.getInstance().getBluedToothTime();
    }

    public void readDeviceInfoFromTB() {
        ScheduleCommandUtil.getInstance().setInitDataFromTB();
    }

    public void clearBusyWrite() {
        ScheduleCommandUtil.getInstance().clearBusyWrite();
    }

    public int startReadDeviceInfo() {
        return ScheduleCommandUtil.getInstance().startReadDeviceInfo();
    }

    public boolean isChangeDeviceSchedule(boolean isUpdateShareFile) {
        SharedPreferences versionsFile = this.mContext.getSharedPreferences(changeDeviceFileName, 0);
        if (!versionsFile.getString(versionSchedule, "null").equals(currentVersion)) {
            return true;
        }
        if (versionsFile.getString(changeDeviceAddr, "no").equals(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device))) {
            return false;
        }
        if (!isUpdateShareFile) {
            return true;
        }
        clearIsChangeDeviceSchedule();
        return true;
    }

    public void clearIsChangeDeviceSchedule() {
        Editor editor = this.mContext.getSharedPreferences(changeDeviceFileName, 0).edit();
        editor.putString(versionSchedule, currentVersion);
        editor.putString(changeDeviceAddr, "no");
        editor.commit();
    }

    public void updateIsChangeDeviceSchedule() {
        Editor editor = this.mContext.getSharedPreferences(changeDeviceFileName, 0).edit();
        editor.putString(versionSchedule, currentVersion);
        editor.putString(changeDeviceAddr, PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
        editor.commit();
    }

    public boolean isChangeDeviceAlarm() {
        SharedPreferences versionsFile = this.mContext.getSharedPreferences(changeDeviceFileName, 0);
        if (!versionsFile.getString(versionAlarm, "null").equals(currentVersion)) {
            Editor editor = versionsFile.edit();
            editor.putString(versionAlarm, currentVersion);
            editor.putString(updateAllAlarm, PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
            editor.commit();
            return true;
        } else if (versionsFile.getString(updateAllAlarm, "no").equals(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name))) {
            return false;
        } else {
            Editor editor2 = versionsFile.edit();
            editor2.putString(updateAllAlarm, PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
            editor2.commit();
            return true;
        }
    }

    public boolean getIsSupportSchedule() {
        return ScheduleCommandUtil.getInstance().getIsSupportSchedule();
    }

    private void postEvent() {
    }

    public void newScheduleBluetoothDataParseBiz() {
        ScheduleCommandUtil.getInstance().newScheduleBluetoothDataParseBiz();
    }

    public void registerReceiver() {
        ScheduleCommandUtil.getInstance().registerReceiver();
    }

    public void unRegisterReceiver() {
        ScheduleCommandUtil.getInstance().unRegisterReceiver();
    }
}
