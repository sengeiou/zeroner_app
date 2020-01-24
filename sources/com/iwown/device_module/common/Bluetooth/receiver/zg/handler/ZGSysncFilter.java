package com.iwown.device_module.common.Bluetooth.receiver.zg.handler;

import android.content.Context;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.data.model.EveryDayInfo;
import com.iwown.ble_module.zg_ble.data.model.SevenDayStore;
import com.iwown.ble_module.zg_ble.data.model.TDay;
import com.iwown.ble_module.zg_ble.data.model.bh_totalinfo;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.test.ExecutorUtils;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_gps.ZgGpsDay;
import com.iwown.device_module.device_gps.ZgGpsDay.GpsDay;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class ZGSysncFilter {
    public static Map<String, Integer> gpsMap;
    public static String gpsTotalDay = null;
    public static String model = "";
    /* access modifiers changed from: private */
    public static int totalDays = 7;
    public static bh_totalinfo totalinfo = new bh_totalinfo();

    public static void setGpsTotalDay(String gpsTotalDay2, long uid, String dataFrom) {
        if (gpsMap == null) {
            gpsMap = new HashMap();
        }
        KLog.e("no2855-> " + gpsTotalDay2);
        gpsMap.clear();
        ZgGpsDay zgGpsDay = (ZgGpsDay) JsonUtils.fromJson(gpsTotalDay2, ZgGpsDay.class);
        if (zgGpsDay == null || zgGpsDay.getDetail() == null) {
            KLog.e("no2855->gson 解析也出错？？");
            return;
        }
        for (GpsDay gpsDay : zgGpsDay.getDetail()) {
            if (((TB_mtk_statue) DataSupport.where("uid=? and data_from=? and year=? and month=? and day=? and type=?", String.valueOf(uid), dataFrom, gpsDay.getYear() + "", gpsDay.getMonth() + "", gpsDay.getDay() + "", "8912").findFirst(TB_mtk_statue.class)) != null) {
                TB_mtk_statue mtk_statue = new TB_mtk_statue();
                mtk_statue.setUid(uid);
                mtk_statue.setHas_tb(2);
                mtk_statue.setHas_file(2);
                mtk_statue.setData_from(dataFrom);
                mtk_statue.setDay(gpsDay.getDay());
                mtk_statue.setMonth(gpsDay.getMonth());
                mtk_statue.setYear(gpsDay.getYear());
                mtk_statue.setDate(new DateUtil(gpsDay.getYear(), gpsDay.getMonth(), gpsDay.getDay()).getUnixTimestamp());
                mtk_statue.setType(8902);
                mtk_statue.save();
            }
            gpsMap.put(getGpsMapKey(gpsDay.getYear(), gpsDay.getMonth(), gpsDay.getDay()), Integer.valueOf(gpsDay.getPosition()));
        }
    }

    public static void update8cType() {
    }

    public static void initSyncCondition(final Context context, final SevenDayStore sevenDayStore, long uid, String dataFrom) {
        Date date = null;
        totalDays = sevenDayStore.totalDays;
        try {
            ZG_BaseInfo zgBaseInfoByKey = ZGDataParsePresenter.getZGBaseInfoByKey(ZG_BaseInfo.key_data_last_day);
            if (zgBaseInfoByKey != null && !TextUtils.isEmpty(zgBaseInfoByKey.getContent())) {
                date = DateUtil.String2Date("yyyy-MM-dd", zgBaseInfoByKey.getContent());
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (date != null) {
            totalDays = DateUtil.differentDaysByMillisecond(date, new Date());
            if (totalDays > 7) {
                totalDays = 7;
            }
        }
        KLog.e("no2855initSyncCondition " + totalDays);
        if (totalDays == 0) {
            KLog.d("no2855today sync  first sync total data");
            ZG_BaseInfo zgBaseInfoByKey2 = ZGDataParsePresenter.getZGBaseInfoByKey(ZG_BaseInfo.key_last_totaldata);
            if (zgBaseInfoByKey2 != null && !TextUtils.isEmpty(zgBaseInfoByKey2.getContent())) {
                totalinfo = (bh_totalinfo) JsonUtils.fromJson(zgBaseInfoByKey2.getContent(), bh_totalinfo.class);
                KLog.d("old total data " + JsonUtils.toJson(totalinfo));
            }
            DateUtil myDa = new DateUtil();
            myDa.addDay(-8);
            List<TB_mtk_statue> mtk_statues = DataSupport.where("uid=? and data_from=? and has_tb!=? and type=? and date>?", uid + "", dataFrom, "1", "8912", myDa.getUnixTimestamp() + "").find(TB_mtk_statue.class);
            if (mtk_statues == null || mtk_statues.size() <= 0) {
                BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getTotalData(TDay.Today)));
                return;
            }
            resh8CSend(context, sevenDayStore, mtk_statues);
            return;
        }
        KLog.d("total Day Sync" + totalDays);
        ExecutorUtils.getExecutorService().execute(new Runnable() {
            public void run() {
                DateUtil dateToday = new DateUtil();
                ZGSysncFilter.syncData(0, dateToday.getYear(), dateToday.getMonth(), dateToday.getDay());
                for (int i = 0; i < ZGSysncFilter.totalDays; i++) {
                    if (sevenDayStore.storeDateObject != null && i < sevenDayStore.storeDateObject.size()) {
                        EveryDayInfo everyDayInfo = (EveryDayInfo) sevenDayStore.storeDateObject.get(i);
                        KLog.e("no2855同步s：" + everyDayInfo.year + HelpFormatter.DEFAULT_OPT_PREFIX + everyDayInfo.month + HelpFormatter.DEFAULT_OPT_PREFIX + everyDayInfo.day);
                        ZGSysncFilter.syncData(i + 1, (int) everyDayInfo.year, everyDayInfo.month, everyDayInfo.day);
                    }
                }
                KLog.e("发送 最后一条接收指令");
                BleDataOrderHandler.getInstance().syncDataOver(context);
            }
        });
    }

    public static void resh8CSend(final Context context, final SevenDayStore sevenDayStore, final List<TB_mtk_statue> mtk_statues) {
        KLog.d("no2855 8c 有部分数据为入表: ");
        ExecutorUtils.getExecutorService().execute(new Runnable() {
            public void run() {
                List<String> days = new ArrayList<>(7);
                for (TB_mtk_statue mtk_statue : mtk_statues) {
                    days.add(mtk_statue.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + mtk_statue.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + mtk_statue.getDay());
                }
                DateUtil dateToday = new DateUtil();
                ZGSysncFilter.syncData(0, dateToday.getYear(), dateToday.getMonth(), dateToday.getDay());
                for (int i = 0; i < ZGSysncFilter.totalDays; i++) {
                    if (sevenDayStore.storeDateObject != null && i < sevenDayStore.storeDateObject.size()) {
                        EveryDayInfo everyDayInfo = (EveryDayInfo) sevenDayStore.storeDateObject.get(i);
                        KLog.e("no2855异常同步s：" + everyDayInfo.year + HelpFormatter.DEFAULT_OPT_PREFIX + everyDayInfo.month + HelpFormatter.DEFAULT_OPT_PREFIX + everyDayInfo.day);
                        if (days.contains(everyDayInfo.year + HelpFormatter.DEFAULT_OPT_PREFIX + everyDayInfo.month + HelpFormatter.DEFAULT_OPT_PREFIX + everyDayInfo.day)) {
                            ZGSysncFilter.syncData(i + 1, (int) everyDayInfo.year, everyDayInfo.month, everyDayInfo.day);
                        }
                    }
                }
                KLog.e("发送 最后一条接收指令");
                BleDataOrderHandler.getInstance().syncDataOver(context);
            }
        });
    }

    public static void syncTodayData(Context applicationContext, bh_totalinfo newTotalInfo) {
        boolean isUpSleep;
        boolean isUpSport;
        boolean isUpWalk = false;
        if (totalDays != 0) {
            KLog.e("no2855no Today " + newTotalInfo.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + newTotalInfo.getDay());
            DateUtil dateUtil = new DateUtil();
            if (newTotalInfo.getMonth() == dateUtil.getMonth() && newTotalInfo.getDay() == dateUtil.getDay()) {
                if (newTotalInfo.getLatestHeart() > 0) {
                    newTotalInfo.setHeartType(1);
                }
                if (newTotalInfo.getCalorie() > 0) {
                    newTotalInfo.setSportType(1);
                }
                if (newTotalInfo.getSleepMinutes() > 0) {
                    newTotalInfo.setSleepType(1);
                }
                ZGDataParsePresenter.updateZGBaseInfo(ZG_BaseInfo.key_last_totaldata, JsonTool.toJson(newTotalInfo));
                return;
            }
            return;
        }
        ZG_BaseInfo zgBaseInfoByKey = ZGDataParsePresenter.getZGBaseInfoByKey(ZG_BaseInfo.key_last_totaldata);
        if (zgBaseInfoByKey != null && !TextUtils.isEmpty(zgBaseInfoByKey.getContent())) {
            totalinfo = (bh_totalinfo) JsonUtils.fromJson(zgBaseInfoByKey.getContent(), bh_totalinfo.class);
        }
        if (totalinfo == null) {
            totalinfo = new bh_totalinfo();
        }
        if (newTotalInfo.getLatestHeart() > 0 && totalinfo.getHeartType() == 0) {
            totalinfo.setHeartType(1);
        }
        if (newTotalInfo.getCalorie() > 0 && totalinfo.getSportType() == 0) {
            totalinfo.setSportType(1);
        }
        if (newTotalInfo.getSleepMinutes() > 0 && totalinfo.getSleepType() == 0) {
            totalinfo.setSleepType(1);
        }
        if (totalinfo.getLatestHeart() != newTotalInfo.getLatestHeart() || totalinfo.getHeartType() == 1) {
            KLog.d("syncTodayData Heart");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().readHeartData(totalDays)));
        }
        if ((newTotalInfo.getSleepMinutes() == 0 || totalinfo.getSleepMinutes() == newTotalInfo.getSleepMinutes()) && totalinfo.getSleepType() != 1) {
            isUpSleep = false;
        } else {
            isUpSleep = true;
        }
        if (isUpSleep) {
            KLog.d("syncTodayData Sleep");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSleep(totalDays)));
        }
        if ((newTotalInfo.getCalorie() == 0 || totalinfo.getCalorie() == newTotalInfo.getCalorie()) && totalinfo.getSportType() != 1) {
            isUpSport = false;
        } else {
            isUpSport = true;
        }
        if (isUpSport) {
            KLog.d("syncTodayData Sport");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSport(totalDays)));
            if (gpsMap != null) {
                StringBuffer buffer = new StringBuffer().append(newTotalInfo.getYear()).append("/").append(newTotalInfo.getMonth()).append("/").append(newTotalInfo.getDay());
                KLog.e("no2855-> 徐亚同步的8c " + buffer.toString());
                if (gpsMap.get(buffer.toString()) != null) {
                    KLog.e("no2855-> 发送的8C数据 " + gpsMap.get(buffer.toString()));
                    BleDataOrderHandler.getInstance().getOneDayGps(((Integer) gpsMap.get(buffer.toString())).intValue());
                }
            }
        }
        if (isUpSport || !(newTotalInfo.getStep() == 0 || totalinfo.getStep() == newTotalInfo.getStep())) {
            isUpWalk = true;
        }
        if (isUpWalk) {
            KLog.d("syncTodayData Step");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailWalk(totalDays)));
        }
        KLog.e("no2855发送 最后一条接收指令");
        BleDataOrderHandler.getInstance().syncDataOver(applicationContext);
        if (totalinfo != null) {
            newTotalInfo.setSleepType(totalinfo.getSleepType());
            newTotalInfo.setSportType(totalinfo.getSportType());
            newTotalInfo.setHeartType(totalinfo.getHeartType());
        }
        ZGDataParsePresenter.updateZGBaseInfo(ZG_BaseInfo.key_last_totaldata, JsonTool.toJson(newTotalInfo));
    }

    public static void syncData(int day, int timeYear, int timeMonthy, int timeDay) {
        KLog.e(" no2855同步ZG syncinitDataInfo " + day);
        TDay day1 = null;
        if (day == 0) {
            day1 = TDay.Today;
        } else if (day == 1) {
            day1 = TDay.T_1;
        } else if (day == 2) {
            day1 = TDay.T_2;
        } else if (day == 3) {
            day1 = TDay.T_3;
        } else if (day == 4) {
            day1 = TDay.T_4;
        } else if (day == 5) {
            day1 = TDay.T_5;
        } else if (day == 6) {
            day1 = TDay.T_6;
        } else if (day == 7) {
            day1 = TDay.T_7;
        }
        if (day1 != null) {
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getTotalData(day1)));
        }
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().readHeartData(day)));
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSleep(day)));
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSport(day)));
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailWalk(day)));
        if (gpsMap != null && timeYear != 0 && timeMonthy != 0 && timeDay != 0) {
            String keyDay = getGpsMapKey(timeYear, timeMonthy, timeDay);
            if (gpsMap.get(keyDay) != null) {
                KLog.e("no2855->gson 准备同步 gps发送的日期-->" + gpsMap.get(keyDay));
                BleDataOrderHandler.getInstance().getOneDayGps(((Integer) gpsMap.get(keyDay)).intValue());
            }
        }
    }

    private static String getGpsMapKey(int timeYear, int timeMonth, int timeDay) {
        return new StringBuffer().append(timeYear).append("/").append(timeMonth).append("/").append(timeDay).toString();
    }
}
