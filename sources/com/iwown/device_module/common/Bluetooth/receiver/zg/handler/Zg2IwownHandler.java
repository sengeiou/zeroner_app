package com.iwown.device_module.common.Bluetooth.receiver.zg.handler;

import android.content.Context;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.ble_module.zg_ble.data.model.ZGHeartData;
import com.iwown.ble_module.zg_ble.data.model.ZgSleepData;
import com.iwown.ble_module.zg_ble.data.model.bh_totalinfo;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailSportData;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailSportData.Sport;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailWalkData;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.ViewRefresh;
import com.iwown.data_link.sleep_data.SleepScoreHandler;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.CurrData_0x29;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.Detail_data;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.HeartRateDetial;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.Bluetooth.receiver.zg.bean.ZGRn_sportCompartor;
import com.iwown.device_module.common.Bluetooth.receiver.zg.dao.ZG_Sql_Utils;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import com.iwown.device_module.common.sql.TB_v3_walk;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_hours;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;

public class Zg2IwownHandler {
    private static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new LinkedBlockingDeque());

    public static void converter2HourHeart(Context context, int dataType, ZGHeartData zgHeartData) {
        KLog.e("zg=====start==========" + new DateUtil().getYyyyMMdd_HHmmssDate());
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        String deviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        int y = zgHeartData.getYear();
        int m = zgHeartData.getMonth();
        int d = zgHeartData.getDay();
        int[] old_rates = zgHeartData.getStaticHeart();
        if (old_rates.length < 144) {
            KLog.e("converter2HourHeart old_rates length < 144");
            return;
        }
        int[] rates = new int[1440];
        for (int rate_index = 0; rate_index < 1440; rate_index++) {
            rates[rate_index] = old_rates[rate_index / 10];
        }
        DateUtil dateUtil1 = new DateUtil(y, m, d);
        DateUtil dateUtil = new DateUtil();
        String mUid = ContextUtil.getUID();
        String mDeviceName = ContextUtil.getDeviceNameCurr();
        TB_v3_heartRate_data_hours lastHeartHour = (TB_v3_heartRate_data_hours) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=?", mUid, mDeviceName, y + "", m + "", d + "").order("hours asc").findLast(TB_v3_heartRate_data_hours.class);
        if (lastHeartHour != null) {
            KLog.d("no2855 find LastHeart--> " + lastHeartHour.getDay() + HelpFormatter.DEFAULT_OPT_PREFIX + lastHeartHour.getHours());
        }
        int i = 0;
        while (true) {
            if (i < 24) {
                if (DateUtil.isSameDay(new Date(), new Date(dateUtil1.getTimestamp())) && i > dateUtil.getHour()) {
                    KLog.d("53 last ffff");
                    EventBus.getDefault().post(new ViewRefresh(false, 83));
                    break;
                }
                TB_v3_heartRate_data_hours hoursData = new TB_v3_heartRate_data_hours();
                hoursData.setUid(uid);
                hoursData.setData_from(deviceName);
                int hour = i;
                hoursData.setYear(y);
                hoursData.setMonth(m);
                hoursData.setDay(d);
                hoursData.setHours(hour);
                hoursData.setRecord_date(new DateUtil(y, m, d, hour, 0).getUnixTimestamp());
                hoursData.setToDefault("_uploaded");
                hoursData.set_uploaded(0);
                int[] time = new int[60];
                System.arraycopy(rates, hour * 60, time, 0, 60);
                hoursData.setDetail_data(new Gson().toJson((Object) time));
                if (DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and hours =?", mUid, mDeviceName, y + "", m + "", d + "", hour + "").count(TB_v3_heartRate_data_hours.class) <= 0) {
                    hoursData.save();
                } else if (lastHeartHour != null) {
                    boolean isSameHour = lastHeartHour.getMonth() == m && lastHeartHour.getDay() == d && lastHeartHour.getHours() == hour;
                    boolean isSameData = lastHeartHour.getDetail_data().equals(hoursData.getDetail_data());
                    if (isSameHour && !isSameData) {
                        hoursData.updateAll("uid=? and data_from=? and year=? and month=? and day=? and hours =?", mUid, mDeviceName, y + "", m + "", d + "", hour + "");
                    }
                }
                i++;
            } else {
                break;
            }
        }
        if (y > 0 && m > 0 && d > 0) {
            ZGBaseUtils.postSyncDataEventZg(ZGBaseUtils.Heart, zgHeartData.getYear(), zgHeartData.getMonth(), zgHeartData.getDay());
            if (dateUtil1.isToday()) {
                EventBus.getDefault().post(new ViewRefresh(false, 83));
                HealthDataEventBus.updateHealthHeartEvent();
            }
        }
        KLog.e(" converter2HourHeart ok" + new DateUtil().getYyyyMMdd_HHmmssDate());
    }

    public static void converter2Sleep(Context context, int dataType, ZgSleepData zgSleepData) {
        if (zgSleepData.getStartTime() <= 0) {
            KLog.e("sync converter2Sleep sleep data startTime is 0" + zgSleepData.getDay());
            L.file("converter2Sleep sleep data startTime is 0" + zgSleepData.getDay(), 3);
            return;
        }
        DateUtil dateUtil = new DateUtil(zgSleepData.getYear(), zgSleepData.getMonth(), zgSleepData.getDay());
        TB_SLEEP_Final_DATA sleepDataByDate1 = ZG_Sql_Utils.getSleepDataByDate1(context, dateUtil);
        if (sleepDataByDate1 == null) {
            sleepDataByDate1 = new TB_SLEEP_Final_DATA();
            sleepDataByDate1.setToDefault("_uploaded");
            sleepDataByDate1.setUid(ContextUtil.getLUID());
            sleepDataByDate1.setData_from(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
            sleepDataByDate1.setDate(dateUtil.getSyyyyMMddDate());
        }
        int score = SleepScoreHandler.calSleepScore(zgSleepData.getDeepSleep() + zgSleepData.getLightSleep() + zgSleepData.getWakeSleep(), zgSleepData.getDeepSleep(), zgSleepData.getStartTime());
        sleepDataByDate1.setYear(zgSleepData.getYear());
        sleepDataByDate1.setMonth(zgSleepData.getMonth());
        sleepDataByDate1.setStart_time(zgSleepData.getStartTime());
        sleepDataByDate1.setEnd_time(zgSleepData.getEndTime());
        sleepDataByDate1.setDeepSleepTime((float) zgSleepData.getDeepSleep());
        sleepDataByDate1.setLightSleepTime((float) zgSleepData.getLightSleep());
        sleepDataByDate1.setSleep_segment(JsonUtils.toJson(zgSleepData.getData()));
        sleepDataByDate1.setScore(score);
        boolean isok = sleepDataByDate1.save();
        KLog.e(dateUtil.getSyyyyMMddDate() + " sync sleep save statue is " + isok);
        L.file(dateUtil.getSyyyyMMddDate() + " sleep save statue is " + isok, 3);
        if (dateUtil.getUnixTimestamp() > 1000) {
            ZGBaseUtils.postSyncDataEventZg(ZGBaseUtils.Sleep, zgSleepData.getYear(), zgSleepData.getMonth(), zgSleepData.getDay());
        }
        if (dateUtil.isToday()) {
            HealthDataEventBus.updateHealthSleepEvent();
            EventBus.getDefault().post(new ViewRefresh(true, 40));
        }
    }

    public static void converter2Walking(Context context, int dataType, bh_totalinfo info) {
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        String dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        DateUtil dateUtil = new DateUtil(info.getYear(), info.getMonth(), info.getDay());
        if (dateUtil.isToday()) {
            DataSupport.deleteAll(TB_v3_walk.class, "uid=? and _uploaded!=2 and date=? and data_from=?", uid + "", "" + String.valueOf(dateUtil.getSyyyyMMddDate()), dataFrom + "");
            TB_v3_walk rn_walking_data = new TB_v3_walk();
            rn_walking_data.setData_from(dataFrom);
            rn_walking_data.setUid(uid);
            rn_walking_data.setRecord_date(dateUtil.getUnixTimestamp());
            rn_walking_data.setDistance((float) info.getDistance());
            rn_walking_data.setCalorie((float) info.getCalorie());
            rn_walking_data.setStep(info.getStep());
            rn_walking_data.setDate(dateUtil.getSyyyyMMddDate());
            rn_walking_data.save();
            EventBus.getDefault().post(new CurrData_0x29(info.getStep() + "", info.getCalorie() + ""));
            EventBus.getDefault().post(new ViewRefresh(true, 41));
        }
        ZGBaseUtils.postSyncDataEventZg(ZGBaseUtils.Walking, info.getYear(), info.getMonth(), info.getDay());
    }

    public static void converterSportTo51Data(ZgDetailSportData zgDetailSportData) {
        int age = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app).age;
        int maxHeart = 220 - age;
        int y = zgDetailSportData.getYear();
        int m = zgDetailSportData.getMonth();
        int d = zgDetailSportData.getDay();
        List<Sport> sports = zgDetailSportData.getSports();
        KLog.i("converterSportTo51Data" + JsonUtils.toJson(sports));
        long uid = ContextUtil.getLUID();
        String dataFrom = ContextUtil.getDeviceNameNoClear() + "";
        if (sports != null && sports.size() > 0) {
            for (int i = 0; i < sports.size(); i++) {
                Sport sport = (Sport) sports.get(i);
                List<Integer> heartPoint = sport.getHeart();
                KLog.i("converterSportTo51Data+heartPoint" + JsonUtils.toJson(heartPoint));
                int start = sport.getStartMin();
                int end = sport.getEndMin();
                int startHour = start / 60;
                int startMin = start % 60;
                int endHour = end / 60;
                int endMin = end % 60;
                int r0 = 0;
                int r1 = 0;
                int r2 = 0;
                int r3 = 0;
                int r4 = 0;
                int r5 = 0;
                for (int j = 0; j < heartPoint.size(); j++) {
                    int lever = getHeartLev(maxHeart, ((Integer) heartPoint.get(j)).intValue());
                    KLog.e(lever + "=====================" + heartPoint.get(j));
                    if (lever == 0) {
                        r0++;
                    } else if (lever == 1) {
                        r1++;
                    } else if (lever == 2) {
                        r2++;
                    } else if (lever == 3) {
                        r3++;
                    } else if (lever == 4) {
                        r4++;
                    } else if (lever == 5) {
                        r5++;
                    }
                }
                HeartRateDetial hrData = new HeartRateDetial();
                hrData.setR0(r0);
                hrData.setR1(r1);
                hrData.setR2(r2);
                hrData.setR3(r3);
                hrData.setR4(r4);
                hrData.setR5(r5);
                TB_heartrate_data heart = new TB_heartrate_data();
                heart.setUid(uid);
                heart.setYear(y);
                heart.setMonth(m);
                heart.setDay(d);
                DateUtil d1 = new DateUtil(y, m, d, startHour, startMin);
                heart.setStart_time(d1.getUnixTimestamp());
                heart.setEnd_time(new DateUtil(y, m, d, endHour, endMin).getUnixTimestamp());
                heart.setDetail_data(JsonUtils.toJson(hrData));
                heart.setReserved(JsonUtils.toJson(heartPoint));
                heart.setData_from(dataFrom);
                heart.setAge(age);
                heart.setSport_type(sport.getSportType());
                heart.saveOrUpdate("uid=? and start_time=? and data_from=?", uid + "", d1.getUnixTimestamp() + "", dataFrom);
                DataUtil.upGpsSportOneUrl("1", 1, uid, d1.getUnixTimestamp(), dataFrom, DataUtil.getSportDataTYpe(((Sport) sports.get(i)).getSportType()));
                KLog.i(heart.toString());
            }
        }
    }

    public static int getHeartLev(int maxHeart, int heart) {
        if (((double) heart) <= ((double) maxHeart) * 0.5d && heart >= 35) {
            return 0;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.6d && ((double) heart) > ((double) maxHeart) * 0.5d) {
            return 1;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.7d && ((double) heart) > ((double) maxHeart) * 0.6d) {
            return 2;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.8d && ((double) heart) > ((double) maxHeart) * 0.7d) {
            return 3;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.9d && ((double) heart) > ((double) maxHeart) * 0.8d) {
            return 4;
        }
        if (((double) heart) <= ((double) maxHeart) * 1.0d && ((double) heart) > ((double) maxHeart) * 0.9d) {
            return 5;
        }
        KLog.i("====getHeartLev====" + heart);
        return -1;
    }

    public static void converterTrainingHeart(ZgDetailSportData zgDetailSportData) {
        List<Sport> sports = zgDetailSportData.getSports();
        if (sports != null && sports.size() > 0) {
            for (int i = 0; i < sports.size(); i++) {
                Sport sport = (Sport) sports.get(i);
                List<Integer> heartPoint = sport.getHeart();
                int start = sport.getStartMin();
                int end = sport.getEndMin();
                int startHour = start / 60;
                int startMin = start % 60;
                int endHour = end / 60;
                List<Integer> datas = new ArrayList<>();
                List<TB_v3_heartRate_data_hours> heatrs = DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and hours>=? and hours<=?", ContextUtil.getUID(), ContextUtil.getDeviceNameNoClear(), zgDetailSportData.getYear() + "", zgDetailSportData.getMonth() + "", zgDetailSportData.getDay() + "", startHour + "", endHour + "").find(TB_v3_heartRate_data_hours.class);
                if (heatrs != null && heatrs.size() > 0) {
                    for (int j = 0; j < heatrs.size(); j++) {
                        ArrayList<Integer> listInt = JsonUtils.getListJson(((TB_v3_heartRate_data_hours) heatrs.get(j)).getDetail_data(), Integer.class);
                        if (listInt != null && listInt.size() > 0) {
                            datas.addAll(listInt);
                        }
                    }
                    if (datas.size() > 0) {
                        int index = end - start;
                        int j2 = 0;
                        while (j2 < index) {
                            try {
                                datas.set(startMin + j2, heartPoint.get(j2));
                                j2++;
                            } catch (Exception e) {
                                ThrowableExtension.printStackTrace(e);
                            }
                        }
                        for (int j3 = 0; j3 < heatrs.size(); j3++) {
                            ArrayList arrayList = new ArrayList();
                            for (int k = j3 * 60; k < (j3 * 60) + 60; k++) {
                                arrayList.add(datas.get(k));
                            }
                            ((TB_v3_heartRate_data_hours) heatrs.get(j3)).setDetail_data(JsonUtils.toJson(arrayList));
                            ((TB_v3_heartRate_data_hours) heatrs.get(j3)).saveOrUpdate("uid=? and record_date=? and data_from=?", ((TB_v3_heartRate_data_hours) heatrs.get(j3)).getUid() + "", ((TB_v3_heartRate_data_hours) heatrs.get(j3)).getRecord_date() + "", ((TB_v3_heartRate_data_hours) heatrs.get(j3)).getData_from() + "");
                        }
                    }
                }
            }
        }
    }

    public static void converter2sport(Context context, int dataType, ZgDetailSportData zgDetailSportData) {
        String deviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        List<Sport> sports = zgDetailSportData.getSports();
        if (sports != null && !sports.isEmpty()) {
            KLog.e("sports pre size " + sports.size());
            DateUtil dateUtil = new DateUtil(zgDetailSportData.getYear(), zgDetailSportData.getMonth(), zgDetailSportData.getDay());
            dateUtil.setHour(0);
            dateUtil.setMinute(0);
            dateUtil.setSecond(0);
            List<TB_v3_sport_data> tb_v3_sport_dataList = DataSupport.where("uid=? and  year=? and month=? and day=? and data_from=?", uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", deviceName + "").find(TB_v3_sport_data.class);
            int zeroTime_s = (int) dateUtil.getZeroTime();
            for (Sport new_sport : sports) {
                int startTime_S = zeroTime_s + (new_sport.getStartMin() * 60);
                int endTime_S = zeroTime_s + (new_sport.getEndMin() * 60);
                DateUtil dateUtil2 = new DateUtil((long) startTime_S, true);
                DateUtil endDate = new DateUtil((long) endTime_S, true);
                long y_m_d_h_m_s = dateUtil2.getUnixTimestamp();
                long y_m_d_h_m_s1 = endDate.getUnixTimestamp();
                boolean isExist = false;
                Iterator it = tb_v3_sport_dataList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    TB_v3_sport_data sport = (TB_v3_sport_data) it.next();
                    if (sport.getStart_uxtime() == ((long) startTime_S) && sport.getEnd_uxtime() == ((long) endTime_S)) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    TB_v3_sport_data rn_sport_data = new TB_v3_sport_data();
                    rn_sport_data.setUid(uid);
                    rn_sport_data.setCalorie((double) new_sport.getCalories());
                    rn_sport_data.setData_from(deviceName);
                    rn_sport_data.setToDefault("_uploaded");
                    rn_sport_data.set_uploaded(0);
                    rn_sport_data.setStart_time(new_sport.getStartMin());
                    rn_sport_data.setEnd_time(new_sport.getEndMin());
                    rn_sport_data.setYear(zgDetailSportData.getYear());
                    rn_sport_data.setMonth(zgDetailSportData.getMonth());
                    rn_sport_data.setDay(zgDetailSportData.getDay());
                    rn_sport_data.setStart_uxtime(y_m_d_h_m_s);
                    rn_sport_data.setEnd_uxtime(y_m_d_h_m_s1);
                    rn_sport_data.setSport_type(new_sport.getSportType());
                    sportAddDetail(new_sport.getTotalMin(), new_sport.getSteps(), new_sport.getDistance(), rn_sport_data, true);
                    DataUtil.saveBlueToGpsSport(uid, new_sport.getSportType(), y_m_d_h_m_s, y_m_d_h_m_s1, new_sport.getCalories(), new_sport.getSteps(), new_sport.getDistance(), deviceName, 0, 0, 0, "");
                    KLog.e("rn_sport_data0 " + rn_sport_data.toString());
                }
            }
            if (dateUtil.getUnixTimestamp() > 1000) {
                ZGBaseUtils.postSyncDataEventZg(ZGBaseUtils.Sport, zgDetailSportData.getYear(), zgDetailSportData.getMonth(), zgDetailSportData.getDay());
            }
            if (dateUtil.isToday()) {
                EventBus.getDefault().post(new ViewRefresh(true, 40));
            }
        }
    }

    public static void getWalkingSport(Context context, ZgDetailWalkData zgDetailWalkData) {
        final String deviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        DateUtil dateUtil = new DateUtil(zgDetailWalkData.getYear(), zgDetailWalkData.getMonth(), zgDetailWalkData.getDay());
        dateUtil.setHour(0);
        dateUtil.setMinute(0);
        dateUtil.setSecond(0);
        DataSupport.deleteAll(TB_v3_sport_data.class, "uid=? and  year=? and month=? and day=? and data_from=? and  sport_type=1", uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", deviceName + "");
        List<TB_v3_sport_data> rn_sport_datas = DataSupport.where("uid=? and  year=? and month=? and day=? and data_from=? and sport_type!= 255", uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", deviceName + "").find(TB_v3_sport_data.class);
        List<Integer> walking_steps = zgDetailWalkData.getData();
        int[] walking_arrys = new int[walking_steps.size()];
        for (int i = 0; i < walking_steps.size(); i++) {
            walking_arrys[i] = ((Integer) walking_steps.get(i)).intValue();
        }
        int allSize = walking_steps.size();
        for (TB_v3_sport_data data : rn_sport_datas) {
            if (data.getSport_type() != 1) {
                try {
                    Date date = new Date(data.getStart_uxtime() * 1000);
                    Date end = new Date(data.getEnd_uxtime() * 1000);
                    DateUtil dateUtil2 = new DateUtil(date);
                    int start_time = dateUtil2.getTodayMin();
                    DateUtil dateUtil3 = new DateUtil(end);
                    int end_time = dateUtil3.getTodayMin();
                    KLog.e("no2855---> " + start_time + "  > " + end_time + " > " + data.getSport_type() + " = " + walking_arrys.length);
                    int[] zero_values = new int[((end_time - start_time) + 1)];
                    System.arraycopy(zero_values, 0, walking_arrys, start_time, zero_values.length);
                    TB_heartrate_data heartrate_data = (TB_heartrate_data) DataSupport.where("uid=? and data_from=? and start_time=?", uid + "", deviceName + "", data.getStart_uxtime() + "").findFirst(TB_heartrate_data.class);
                    if (heartrate_data != null) {
                        int mSize = end_time - start_time;
                        int[] walks = new int[mSize];
                        for (int i2 = 0; i2 < mSize; i2++) {
                            if (start_time + i2 >= allSize) {
                                walks[i2] = 0;
                            } else {
                                walks[i2] = ((Integer) walking_steps.get(start_time + i2)).intValue();
                            }
                        }
                        ThreadPoolExecutor threadPoolExecutor = fixedThreadPool;
                        final TB_heartrate_data tB_heartrate_data = heartrate_data;
                        final int[] iArr = walks;
                        AnonymousClass1 r0 = new Runnable() {
                            public void run() {
                                if ((deviceName + "").contains("Band23")) {
                                    DataUtil.saveHr2File(tB_heartrate_data, iArr, false, true);
                                } else {
                                    DataUtil.saveHr2File(tB_heartrate_data, iArr, true, true);
                                }
                            }
                        };
                        threadPoolExecutor.execute(r0);
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }
        ArrayList<TB_v3_sport_data> arrayList = new ArrayList<>();
        long zeroTime_s = dateUtil.getZeroTime();
        int start_index = 0;
        int total_time_steps = 0;
        float total_time_distance = 0.0f;
        for (int i3 = 0; i3 < walking_arrys.length; i3++) {
            if (start_index == 0 && walking_arrys[i3] != 0) {
                start_index = i3;
            } else if (start_index != 0 && walking_arrys[i3] == 0) {
                long endTime_S = zeroTime_s + ((long) (i3 * 60));
                DateUtil dateUtil4 = new DateUtil(zeroTime_s + ((long) (start_index * 60)), true);
                DateUtil endDate = new DateUtil(endTime_S, true);
                TB_v3_sport_data rn_sport_data = new TB_v3_sport_data();
                rn_sport_data.setUid(uid);
                rn_sport_data.setCalorie((double) ZGBaseUtils.geKcal(Math.round(total_time_distance)));
                rn_sport_data.setData_from(deviceName);
                rn_sport_data.setToDefault("_uploaded");
                rn_sport_data.set_uploaded(0);
                rn_sport_data.setStart_time(dateUtil4.getTodayMin());
                rn_sport_data.setEnd_time(endDate.getTodayMin());
                rn_sport_data.setYear(dateUtil4.getYear());
                rn_sport_data.setMonth(dateUtil4.getMonth());
                rn_sport_data.setDay(dateUtil4.getDay());
                rn_sport_data.setStart_uxtime(dateUtil4.getUnixTimestamp());
                rn_sport_data.setEnd_uxtime(endDate.getUnixTimestamp());
                rn_sport_data.setSport_type(1);
                sportAddDetail((i3 - start_index) + 1, total_time_steps, total_time_distance, rn_sport_data, false);
                arrayList.add(rn_sport_data);
                start_index = 0;
                total_time_steps = 0;
                total_time_distance = 0.0f;
            }
            float pace = 0.55f;
            if (walking_arrys[i3] > 120) {
                pace = 0.85f;
            }
            total_time_distance += ((float) walking_arrys[i3]) * pace;
            total_time_steps += walking_arrys[i3];
        }
        Collections.sort(arrayList, new ZGRn_sportCompartor());
        for (TB_v3_sport_data data2 : arrayList) {
            KLog.e("sort after " + data2.getStart_time() + "  " + data2.getEnd_time() + "  ");
        }
        for (TB_v3_sport_data sport_data : zg1440WalkingFilter(context, arrayList)) {
            KLog.e("merge after " + sport_data.getStart_time() + "  " + sport_data.getEnd_time() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            sport_data.save();
        }
        if (dateUtil.getUnixTimestamp() > 1000) {
            ZGBaseUtils.postSyncDataEventZg(ZGBaseUtils.Walking_2_Sport, zgDetailWalkData.getYear(), zgDetailWalkData.getMonth(), zgDetailWalkData.getDay());
        }
        if (dateUtil.isToday()) {
            EventBus.getDefault().post(new ViewRefresh(true, 40));
        }
    }

    private static void sportAddDetail(int activity, int total_time_steps, float total_time_distance, TB_v3_sport_data rn_sport_data, boolean isSave) {
        Detail_data d = new Detail_data();
        d.setStep(total_time_steps);
        d.setDistance((float) Math.round(total_time_distance));
        d.setActivity(activity);
        rn_sport_data.setDetail_data(JsonUtils.toJson(d));
        if (isSave) {
            if (!DataSupport.isExist(TB_v3_sport_data.class, "uid=? and start_uxtime=? and data_from=? and sport_type=?", rn_sport_data.getUid() + "", rn_sport_data.getStart_uxtime() + "", rn_sport_data.getData_from(), rn_sport_data.getSport_type() + "")) {
                rn_sport_data.save();
            }
        }
    }

    private static List<TB_v3_sport_data> zg1440WalkingFilter(Context context, List<TB_v3_sport_data> new_spors) {
        String deviceName = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        List<TB_v3_sport_data> result_datas = new ArrayList<>();
        for (int i = 0; i < new_spors.size(); i++) {
            TB_v3_sport_data sport_data = (TB_v3_sport_data) new_spors.get(i);
            Detail_data detail_data = (Detail_data) JsonUtils.fromJson(sport_data.getDetail_data(), Detail_data.class);
            if (detail_data.getActivity() <= 5) {
                TB_v3_sport_data data = null;
                if (result_datas.size() != 0) {
                    data = (TB_v3_sport_data) result_datas.get(result_datas.size() - 1);
                }
                long zeroTime = new DateUtil(sport_data.getYear(), sport_data.getMonth(), sport_data.getDay()).getZeroTime();
                long endTime_S = zeroTime + ((long) (sport_data.getEnd_time() * 60));
                DateUtil dateUtil = new DateUtil(zeroTime + ((long) (sport_data.getStart_time() * 60)), true);
                DateUtil endDate = new DateUtil(endTime_S, true);
                if (data == null) {
                    TB_v3_sport_data data2 = new TB_v3_sport_data();
                    data2.setUid(uid);
                    data2.setCalorie((double) ZGBaseUtils.geKcal(Math.round(detail_data.getDistance())));
                    data2.setData_from(deviceName);
                    data2.setStart_time(sport_data.getStart_time());
                    data2.setYear(sport_data.getYear());
                    data2.setMonth(sport_data.getMonth());
                    data2.setDay(sport_data.getDay());
                    data2.setToDefault("_uploaded");
                    data2.set_uploaded(0);
                    data2.setEnd_time(sport_data.getEnd_time());
                    data2.setSport_type(sport_data.getSport_type());
                    data2.setSport_type(1);
                    data2.setStart_uxtime(dateUtil.getUnixTimestamp());
                    data2.setEnd_uxtime(endDate.getUnixTimestamp());
                    sportAddDetail(detail_data.getActivity(), detail_data.getStep(), (float) Math.round(detail_data.getDistance()), data2, false);
                    result_datas.add(data2);
                } else {
                    if (sport_data.getStart_time() - data.getEnd_time() <= 30 && data.getStart_time() / 60 == data.getEnd_time() / 60 && sport_data.getStart_time() > data.getEnd_time()) {
                        data.setToDefault("_uploaded");
                        data.set_uploaded(0);
                        Detail_data detail_data1 = (Detail_data) JsonUtils.fromJson(data.getDetail_data(), Detail_data.class);
                        data.setEnd_time(sport_data.getEnd_time());
                        int activity = detail_data.getActivity() + detail_data1.getActivity();
                        data.setStart_uxtime(dateUtil.getUnixTimestamp());
                        data.setEnd_uxtime(endDate.getUnixTimestamp());
                        data.setCalorie((double) ZGBaseUtils.geKcal(Math.round(detail_data.getDistance() + detail_data1.getDistance())));
                        data.setSport_type(1);
                        sportAddDetail(activity, detail_data.getStep() + detail_data1.getStep(), detail_data.getDistance() + detail_data1.getDistance(), data, false);
                        KLog.e("合并 " + data.getStart_time() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + data.getEnd_time() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + activity + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + detail_data.getStep() + detail_data1.getStep());
                    }
                    result_datas.add(sport_data);
                }
            } else {
                TB_v3_sport_data data3 = null;
                if (result_datas.size() != 0) {
                    data3 = (TB_v3_sport_data) result_datas.get(result_datas.size() - 1);
                }
                long zeroTime2 = new DateUtil(sport_data.getYear(), sport_data.getMonth(), sport_data.getDay()).getZeroTime();
                long endTime_S2 = zeroTime2 + ((long) (sport_data.getEnd_time() * 60));
                DateUtil dateUtil2 = new DateUtil(zeroTime2 + ((long) (sport_data.getStart_time() * 60)), true);
                DateUtil endDate2 = new DateUtil(endTime_S2, true);
                if (data3 != null && ((Detail_data) JsonUtils.fromJson(sport_data.getDetail_data(), Detail_data.class)).getActivity() < 5 && sport_data.getStart_time() - data3.getEnd_time() <= 30 && data3.getStart_time() / 60 == data3.getEnd_time() / 60 && sport_data.getStart_time() > data3.getEnd_time()) {
                    data3.setToDefault("_uploaded");
                    data3.set_uploaded(0);
                    Detail_data detail_data12 = (Detail_data) JsonUtils.fromJson(data3.getDetail_data(), Detail_data.class);
                    data3.setEnd_time(sport_data.getEnd_time());
                    int activity2 = detail_data.getActivity() + detail_data12.getActivity();
                    data3.setStart_uxtime(dateUtil2.getUnixTimestamp());
                    data3.setEnd_uxtime(endDate2.getUnixTimestamp());
                    data3.setCalorie((double) ZGBaseUtils.geKcal(Math.round(detail_data.getDistance() + detail_data12.getDistance())));
                    data3.setSport_type(1);
                    sportAddDetail(activity2, detail_data.getStep() + detail_data12.getStep(), detail_data.getDistance() + detail_data12.getDistance(), data3, false);
                    KLog.e("合并 " + data3.getStart_time() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + data3.getEnd_time() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + activity2 + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + detail_data.getStep() + detail_data12.getStep());
                }
                result_datas.add(sport_data);
            }
        }
        return result_datas;
    }
}
