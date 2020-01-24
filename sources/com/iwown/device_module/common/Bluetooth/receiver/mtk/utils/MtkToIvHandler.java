package com.iwown.device_module.common.Bluetooth.receiver.mtk.utils;

import android.os.Environment;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.app.nativeinvoke.NativeInvoker;
import com.iwown.app.nativeinvoke.SA_SleepBufInfo;
import com.iwown.app.nativeinvoke.SA_SleepDataInfo;
import com.iwown.ble_module.utils.Util;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.eventbus.ViewRefresh;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.data_link.sleep_data.SleepScoreHandler;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FilePath;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.HeartRateDetial;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.SleepSegment;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.network.data.resp.F1SleepData;
import com.iwown.device_module.common.network.data.resp.F1SleepData.DataBean.SleepdataBean;
import com.iwown.device_module.common.network.data.resp.F1SleepTimeBean;
import com.iwown.device_module.common.sql.TB_61_data;
import com.iwown.device_module.common.sql.TB_BP_data;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import com.iwown.device_module.common.sql.heart.TB_swimrate_data;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_hours;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.file.FileUtils;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;
import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.SaveCallback;

public class MtkToIvHandler {
    private static ThreadPoolExecutor fixedThreadPool = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new LinkedBlockingDeque());

    public static void delete61Data() {
        DateUtil d = new DateUtil();
        d.addDay(-15);
        DataSupport.deleteAll(TB_61_data.class, "time <= ? ", d.getTimestamp() + "");
    }

    public static List<TB_61_data> sort61DataBySeq(long uid, int year, int month, int day, String dataFrom) {
        if (dataFrom == null) {
            dataFrom = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name) + "";
        }
        KLog.d("test61数据：", "某一天的数据--year:  " + year + HelpFormatter.DEFAULT_OPT_PREFIX + month + HelpFormatter.DEFAULT_OPT_PREFIX + day + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + dataFrom);
        List<TB_61_data> allDatas = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=?", String.valueOf(uid), year + "", month + "", day + "", dataFrom).order("seq asc").find(TB_61_data.class);
        List<TB_61_data> datas = new ArrayList<>();
        if (allDatas.size() <= 1) {
            datas.addAll(allDatas);
        } else if (((TB_61_data) allDatas.get(allDatas.size() - 1)).getSeq() - ((TB_61_data) allDatas.get(0)).getSeq() >= 4000) {
            int cut = 0;
            int i = 1;
            while (true) {
                if (i >= allDatas.size()) {
                    break;
                } else if (((TB_61_data) allDatas.get(i)).getSeq() - ((TB_61_data) allDatas.get(i - 1)).getSeq() >= 2000) {
                    cut = i;
                    break;
                } else {
                    i++;
                }
            }
            List<TB_61_data> firstDatas = new ArrayList<>();
            List<TB_61_data> twoDatas = new ArrayList<>();
            for (int i2 = 0; i2 < allDatas.size(); i2++) {
                if (i2 < cut) {
                    firstDatas.add(allDatas.get(i2));
                } else {
                    twoDatas.add(allDatas.get(i2));
                }
            }
            twoDatas.addAll(firstDatas);
            datas.addAll(twoDatas);
        } else {
            datas.addAll(allDatas);
        }
        return datas;
    }

    public static void mtk61DataToHeart(long uid, int year, int month, int day, String dataFrom, List<TB_61_data> datas) {
        int mSize = 0;
        if (datas != null) {
            mSize = datas.size();
        }
        if (mSize > 0) {
            List<Integer> heart53 = new ArrayList<>();
            int count53 = 0;
            int heartPre = 0;
            for (int i = 0; i < mSize; i++) {
                if (i < mSize - 1) {
                    if (((TB_61_data) datas.get(i)).getHour() != ((TB_61_data) datas.get(i + 1)).getHour()) {
                        for (int j = count53; j < 60; j++) {
                            int heart = ((TB_61_data) datas.get(i + 1)).getAvg_bpm();
                            if (j % 10 == 0) {
                                heartPre = 0;
                            }
                            if (heart != 0) {
                                heart53.add(Integer.valueOf(heart));
                                heartPre = heart;
                            } else {
                                heart53.add(Integer.valueOf(heartPre));
                            }
                        }
                        DataUtil.saveTb53Heart(uid, year, month, day, ((TB_61_data) datas.get(i)).getHour(), heart53, dataFrom);
                        heart53 = new ArrayList<>();
                        count53 = 0;
                    } else {
                        int j2 = count53;
                        while (j2 <= ((TB_61_data) datas.get(i)).getMin() && j2 < 60) {
                            int heart2 = ((TB_61_data) datas.get(i)).getAvg_bpm();
                            if (j2 % 10 == 0) {
                                heartPre = 0;
                            }
                            if (heart2 != 0) {
                                heart53.add(Integer.valueOf(heart2));
                                heartPre = heart2;
                            } else {
                                heart53.add(Integer.valueOf(heartPre));
                            }
                            j2++;
                        }
                        count53 = ((TB_61_data) datas.get(i)).getMin() + 1;
                    }
                } else {
                    int j3 = count53;
                    while (j3 <= ((TB_61_data) datas.get(i)).getMin() && j3 < 60) {
                        int heart3 = ((TB_61_data) datas.get(i)).getAvg_bpm();
                        if (heart3 != 0) {
                            heart53.add(Integer.valueOf(heart3));
                            heartPre = heart3;
                        } else {
                            heart53.add(Integer.valueOf(heartPre));
                        }
                        j3++;
                    }
                    DataUtil.saveTb53Heart(uid, year, month, day, ((TB_61_data) datas.get(i)).getHour(), heart53, dataFrom);
                }
            }
        }
    }

    public static void mtk61DataToHeartBle(long uid, int year, int month, int day, String dataFrom, List<TB_61_data> datasOld) {
        int mSize = 0;
        List<TB_61_data> datas = new ArrayList<>();
        datas.addAll(datasOld);
        if (datas != null) {
            mSize = datas.size();
        }
        if (mSize > 0) {
            List<Integer> heart53 = new ArrayList<>();
            try {
                HashMap hashMap = new HashMap();
                for (int i = 0; i < datas.size(); i++) {
                    int hour = ((TB_61_data) datas.get(i)).getHour();
                    int min = ((TB_61_data) datas.get(i)).getMin();
                    String str = "" + min;
                    if (min < 10) {
                        str = "0" + min;
                    }
                    String key = hour + "" + str;
                    TB_61_data oneData = (TB_61_data) hashMap.get(key);
                    if (oneData == null) {
                        hashMap.put(key, datas.get(i));
                    } else if (oneData.getAvg_bpm() == 0) {
                        hashMap.put(key, datas.get(i));
                    }
                }
                if (hashMap.size() > 0) {
                    datas.clear();
                    for (Entry entry : hashMap.entrySet()) {
                        datas.add((TB_61_data) entry.getValue());
                    }
                }
                mSize = datas.size();
                Collections.sort(datas);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            int count53 = 0;
            int heartPre = 0;
            for (int i2 = 0; i2 < mSize; i2++) {
                if (i2 >= mSize - 1) {
                    int j = count53;
                    while (j <= ((TB_61_data) datas.get(i2)).getMin() && j < 60) {
                        int heart = ((TB_61_data) datas.get(i2)).getAvg_bpm();
                        if (heart != 0) {
                            heart53.add(Integer.valueOf(heart));
                            heartPre = heart;
                        } else {
                            heart53.add(Integer.valueOf(heartPre));
                        }
                        j++;
                    }
                    DataUtil.saveTb53Heart(uid, year, month, day, ((TB_61_data) datas.get(i2)).getHour(), heart53, dataFrom);
                } else if (((TB_61_data) datas.get(i2)).getHour() != ((TB_61_data) datas.get(i2 + 1)).getHour()) {
                    for (int j2 = count53; j2 < 60; j2++) {
                        int heart2 = ((TB_61_data) datas.get(i2 + 1)).getAvg_bpm();
                        if (j2 % 10 == 0) {
                            heartPre = 0;
                        }
                        if (heart2 != 0) {
                            heart53.add(Integer.valueOf(heart2));
                            heartPre = heart2;
                        } else {
                            heart53.add(Integer.valueOf(heartPre));
                        }
                    }
                    DataUtil.saveTb53Heart(uid, year, month, day, ((TB_61_data) datas.get(i2)).getHour(), heart53, dataFrom);
                    heart53 = new ArrayList<>();
                    count53 = 0;
                } else {
                    int j3 = count53;
                    while (j3 <= ((TB_61_data) datas.get(i2)).getMin() && j3 < 60) {
                        int heart3 = ((TB_61_data) datas.get(i2)).getAvg_bpm();
                        if (j3 % 10 == 0) {
                            heartPre = 0;
                        }
                        if (heart3 != 0) {
                            heart53.add(Integer.valueOf(heart3));
                            heartPre = heart3;
                        } else {
                            heart53.add(Integer.valueOf(heartPre));
                        }
                        j3++;
                    }
                    count53 = ((TB_61_data) datas.get(i2)).getMin() + 1;
                }
            }
        }
    }

    public static void mtkrefech53(long uid, long startTime, long endTime, String dataFrom, List<Integer> heart53) {
        int nums = 0;
        for (TB_v3_heartRate_data_hours data_hours : DataSupport.where("uid=? and record_date>=? and record_date<=? and data_from=?", uid + "", (startTime - 60) + "", endTime + "", dataFrom + "").order("record_date").find(TB_v3_heartRate_data_hours.class)) {
            List<Integer> hourList = JsonUtils.getListJson(data_hours.getDetail_data(), Integer.class);
            List<Integer> newHour = new ArrayList<>(60);
            int num53 = 0;
            long mSt = data_hours.getRecord_date();
            int size = hourList.size();
            int i = 0;
            while (i < size && i < 60) {
                long mt = mSt + ((long) (i * 60));
                boolean isFirst = nums == 0 && Math.abs(startTime - mt) < 60;
                boolean isIn = mt > startTime && mt < endTime;
                if ((isFirst || isIn) && num53 < heart53.size()) {
                    newHour.set(i, heart53.get(num53));
                    num53++;
                } else {
                    newHour.set(i, hourList.get(i));
                }
                i++;
            }
            nums++;
            data_hours.setDetail_data(JsonUtils.toJson(newHour));
            data_hours.updateAll("uid=? and data_from=? and record_date=?", uid + "", dataFrom, data_hours.getRecord_date() + "");
        }
    }

    public static void mtk61DataTo51Heart(long uid, int year, int month, int day, String dataFrom, List<TB_61_data> list) {
    }

    public static void fatigueDataToIv(long uid, int year, int month, int day, String dataFrom, List<TB_61_data> datas) {
        List<FatigueData> fatigueData = new ArrayList<>();
        int mSize = 0;
        if (datas != null && datas.size() > 0) {
            mSize = datas.size();
        }
        for (int i = 0; i < mSize; i++) {
            if (((TB_61_data) datas.get(i)).getBpm_hr() > 0) {
                fatigueData.add(new FatigueData(String.format("%02d", new Object[]{Integer.valueOf(((TB_61_data) datas.get(i)).getHour())}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(((TB_61_data) datas.get(i)).getMin())}), ((TB_61_data) datas.get(i)).getBpm_hr()));
            }
        }
        if (fatigueData.size() > 0) {
            DataUtil.saveFatiueData(uid, Util.date2TimeStamp(year, month, day, 0, 0, 0) * 1000, dataFrom, year2DateStr(year, month, day), JsonUtils.toJson(fatigueData));
        }
    }

    public static void sportAnd51HeartDataToIv(long uid, int year, int month, int day, String dataFrom, List<TB_61_data> datas) {
        Detail_data detail_data1;
        int pauseTime;
        int step;
        int activity;
        int mSize = 0;
        long startUTime = 0;
        long endUTime = 0;
        int stTime = 0;
        if (datas != null && datas.size() > 0) {
            mSize = datas.size();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i = 0; i < mSize; i++) {
            if (((TB_61_data) datas.get(i)).getSport_type() == 1 && ((TB_61_data) datas.get(i)).getData_type() >= 32) {
                arrayList.add(datas.get(i));
            } else if (((TB_61_data) datas.get(i)).getSport_type() != 1) {
                arrayList2.add(datas.get(i));
                if (((TB_61_data) datas.get(i)).getState_type() == 1 && ((TB_61_data) datas.get(i)).getAutomatic() > 0) {
                    for (int j = 0; j <= ((TB_61_data) datas.get(i)).getAutomatic(); j++) {
                        DateUtil dateUtil = new DateUtil((((TB_61_data) datas.get(i)).getTime() / 1000) - ((long) (j * 60)), true);
                        List<TB_61_data> noAdds = DataSupport.where("uid=? and year=? and month=? and day=? and hour=? and min=? and sport_type=? and data_from=? and data_type>=?", String.valueOf(uid), year + "", month + "", day + "", dateUtil.getHour() + "", dateUtil.getMinute() + "", "1", dataFrom, "32").find(TB_61_data.class);
                        if (noAdds != null && noAdds.size() > 0) {
                            arrayList3.addAll(noAdds);
                        }
                    }
                }
            }
        }
        ArrayList arrayList4 = new ArrayList();
        if (arrayList.size() > 0) {
            float distance = ((TB_61_data) arrayList.get(0)).getDistance();
            startUTime = Util.date2TimeStamp(year, month, day, ((TB_61_data) arrayList.get(0)).getHour(), ((TB_61_data) arrayList.get(0)).getMin());
            endUTime = Util.date2TimeStamp(year, month, day, ((TB_61_data) arrayList.get(0)).getHour(), ((TB_61_data) arrayList.get(0)).getMin());
            stTime = (((TB_61_data) arrayList.get(0)).getHour() * 60) + ((TB_61_data) arrayList.get(0)).getMin();
            int edTime = (((TB_61_data) arrayList.get(0)).getHour() * 60) + ((TB_61_data) arrayList.get(0)).getMin();
            float calorie = ((TB_61_data) arrayList.get(0)).getCalorie();
            int activity2 = 1;
            int step2 = ((TB_61_data) arrayList.get(0)).getStep();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (i2 > 0) {
                    if (!arrayList3.contains(arrayList.get(i2))) {
                        if (((((TB_61_data) arrayList.get(i2)).getHour() * 60) + ((TB_61_data) arrayList.get(i2)).getMin()) - edTime > 5) {
                            if (step2 > 0 || distance > 0.0f) {
                                arrayList4.add(DataUtil.getTbSport(uid, 1, year, month, day, stTime, startUTime, edTime, endUTime, calorie, JsonUtils.toJson(getDetail(activity2, step2, distance)), dataFrom, 0));
                            }
                            distance = ((TB_61_data) arrayList.get(i2)).getDistance();
                            startUTime = Util.date2TimeStamp(year, month, day, ((TB_61_data) arrayList.get(i2)).getHour(), ((TB_61_data) arrayList.get(i2)).getMin());
                            endUTime = Util.date2TimeStamp(year, month, day, ((TB_61_data) arrayList.get(i2)).getHour(), ((TB_61_data) arrayList.get(i2)).getMin());
                            stTime = (((TB_61_data) arrayList.get(i2)).getHour() * 60) + ((TB_61_data) arrayList.get(i2)).getMin();
                            edTime = (((TB_61_data) arrayList.get(i2)).getHour() * 60) + ((TB_61_data) arrayList.get(i2)).getMin();
                            calorie = ((TB_61_data) arrayList.get(i2)).getCalorie();
                            activity2 = 1;
                            step2 = ((TB_61_data) arrayList.get(i2)).getStep();
                        } else {
                            endUTime = Util.date2TimeStamp(year, month, day, ((TB_61_data) arrayList.get(i2)).getHour(), ((TB_61_data) arrayList.get(i2)).getMin());
                            edTime = (((TB_61_data) arrayList.get(i2)).getHour() * 60) + ((TB_61_data) arrayList.get(i2)).getMin();
                            distance += ((TB_61_data) arrayList.get(i2)).getDistance();
                            calorie += ((TB_61_data) arrayList.get(i2)).getCalorie();
                            activity2++;
                            step2 += ((TB_61_data) arrayList.get(i2)).getStep();
                        }
                    }
                }
                if (i2 == arrayList.size() - 1 && (step2 > 0 || distance > 0.0f)) {
                    arrayList4.add(DataUtil.getTbSport(uid, 1, year, month, day, stTime, startUTime, edTime, endUTime, calorie, JsonUtils.toJson(getDetail(activity2, step2, distance)), dataFrom, 0));
                }
            }
        }
        int sportType = 0;
        boolean pause = false;
        int pauseTime2 = 0;
        long pauseUt = 0;
        float calorie2 = 0.0f;
        float distance2 = 0.0f;
        int step3 = 0;
        long lastTime = 0;
        int lastEt = 0;
        int automatic = 0;
        boolean isOver = true;
        int[] date51 = new int[6];
        LinkedList<Integer> heart53 = new LinkedList<>();
        String lastHeartTime = "";
        int lastHeart = 0;
        int age = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app).age;
        int maxHeart = getMaxHeart(age);
        int minStep = 0;
        LinkedList<Integer> stepList = new LinkedList<>();
        if (arrayList2.size() > 0) {
            int i3 = 0;
            while (true) {
                pauseTime = pauseTime2;
                step = step3;
                if (i3 >= arrayList2.size()) {
                    break;
                }
                if (((TB_61_data) arrayList2.get(i3)).getSport_type() == 0 && sportType == 0) {
                    pauseTime2 = pauseTime;
                    step3 = step;
                } else {
                    if (isOver || (!(((TB_61_data) arrayList2.get(i3)).getState_type() == 1 && ((TB_61_data) arrayList2.get(i3)).getCalorie() == 0.0f && ((TB_61_data) arrayList2.get(i3)).getAvg_bpm() == 0) && (((TB_61_data) arrayList2.get(i3)).getState_type() != 1 || ((TB_61_data) arrayList2.get(i3)).getAutomatic() <= 0))) {
                        pauseTime2 = pauseTime;
                    } else {
                        int df = ((int) (lastTime - startUTime)) - pauseTime;
                        int activity3 = df % 60 == 0 ? df / 60 : (df / 60) + 1;
                        KLog.d("no2855-->结束异常标志: " + lastTime + " -startUTime: " + startUTime + " pauseTime: " + pauseTime + " activity: " + activity3);
                        if (activity3 > 0) {
                            arrayList4.add(DataUtil.getTbSport(uid, sportType, year, month, day, stTime, startUTime, lastEt, lastTime, calorie2, JsonUtils.toJson(getDetail(activity3 + automatic, step, distance2)), dataFrom, automatic));
                            String detail51 = getDetail51(date51);
                            if (sportType == 131) {
                                saveTb51Swim(uid, age, year, month, day, startUTime, endUTime, detail51, dataFrom, automatic, sportType, stepList);
                            } else {
                                saveTb51Heart(uid, age, year, month, day, startUTime, endUTime, detail51, dataFrom, automatic, JsonUtils.toJson(heart53), sportType, stepList);
                            }
                            DataUtil.saveBlueToGpsSport(uid, sportType, startUTime, lastTime, calorie2, step, distance2, dataFrom, automatic, pauseTime, 0, "1");
                        }
                        sportType = 0;
                        pauseTime2 = 0;
                        minStep = 0;
                    }
                    if (sportType != 0) {
                        if (((TB_61_data) arrayList2.get(i3)).getState_type() != 3 || !isP1Version2()) {
                            calorie2 += ((TB_61_data) arrayList2.get(i3)).getCalorie();
                            distance2 += ((TB_61_data) arrayList2.get(i3)).getDistance();
                            step3 = step + ((TB_61_data) arrayList2.get(i3)).getStep();
                        } else {
                            step3 = step;
                        }
                        int heart = ((TB_61_data) arrayList2.get(i3)).getAvg_bpm();
                        if (heart < 30 || (heart > maxHeart && maxHeart != 0)) {
                            heart = 0;
                        }
                        String nowHeartTime = ((TB_61_data) arrayList2.get(i3)).getHour() + "/" + ((TB_61_data) arrayList2.get(i3)).getMin();
                        boolean needAdd = false;
                        if (!nowHeartTime.equals(lastHeartTime)) {
                            heart53.add(Integer.valueOf(heart));
                            lastHeartTime = nowHeartTime;
                            lastHeart = heart;
                            needAdd = true;
                            stepList.add(Integer.valueOf(minStep));
                            minStep = ((TB_61_data) arrayList2.get(i3)).getStep();
                        } else {
                            if (lastHeart == 0 && heart > 0) {
                                if (heart53.size() > 0) {
                                    heart53.removeLast();
                                }
                                heart53.add(Integer.valueOf(heart));
                                lastHeart = heart;
                                needAdd = true;
                            }
                            minStep += ((TB_61_data) arrayList2.get(i3)).getStep();
                        }
                        if (heart != 0 && needAdd) {
                            int level = getHeartLev(maxHeart, heart);
                            if (level != -1) {
                                date51[level] = date51[level] + 1;
                            }
                        }
                        lastTime = Util.date2TimeStamp(year, month, day, ((TB_61_data) arrayList2.get(i3)).getHour(), ((TB_61_data) arrayList2.get(i3)).getMin(), ((TB_61_data) arrayList2.get(i3)).getReserve());
                        lastEt = (((TB_61_data) arrayList2.get(i3)).getHour() * 60) + ((TB_61_data) arrayList2.get(i3)).getMin();
                        if (((TB_61_data) arrayList2.get(i3)).getState_type() == 3 && !pause) {
                            pauseUt = lastTime;
                            KLog.d("tset61s", "no2855pauseUt: " + pauseUt + " - " + sportType);
                            pause = true;
                        }
                        if (!(!pause || ((TB_61_data) arrayList2.get(i3)).getState_type() == 3 || ((TB_61_data) arrayList2.get(i3)).getState_type() == 0)) {
                            pauseTime2 += (int) (lastTime - pauseUt);
                            pause = false;
                            KLog.d("no2855-->pauseTime: " + pauseTime2 + " - " + sportType);
                        }
                        if (((TB_61_data) arrayList2.get(i3)).getState_type() == 2) {
                            isOver = true;
                            if (sportType == 0) {
                                sportType = ((TB_61_data) arrayList2.get(i3)).getSport_type();
                            }
                            endUTime = lastTime;
                            int edTime2 = (((TB_61_data) arrayList2.get(i3)).getHour() * 60) + ((TB_61_data) arrayList2.get(i3)).getMin();
                            int df2 = ((int) (lastTime - startUTime)) - pauseTime2;
                            if (df2 % 60 == 0) {
                                activity = df2 / 60;
                            } else {
                                activity = (df2 / 60) + 1 + automatic;
                            }
                            KLog.d("no2855--> 结束标志: " + sportType + " - " + endUTime + " -startUTime: " + startUTime + " pauseTime: " + pauseTime2 + " activity: " + activity);
                            stepList.add(Integer.valueOf(minStep));
                            if (sportType == 131) {
                                int laps = ((TB_61_data) arrayList2.get(i3)).getStep();
                                int poolLength = ((int) ((TB_61_data) arrayList2.get(i3)).getDistance()) * 10;
                                step3 -= ((TB_61_data) arrayList2.get(i3)).getStep();
                                distance2 = (float) (laps * poolLength);
                                DataUtil.saveBlueToSwimSport(uid, sportType, startUTime, lastTime, calorie2, step3, poolLength, laps, dataFrom, automatic, pauseTime2, "1");
                            } else {
                                DataUtil.saveBlueToGpsSport(uid, sportType, startUTime, lastTime, calorie2, step3, distance2, dataFrom, automatic, pauseTime2, step3, "1");
                            }
                            arrayList4.add(DataUtil.getTbSport(uid, sportType, year, month, day, stTime, startUTime, edTime2, endUTime, calorie2, JsonUtils.toJson(getDetail(activity, step3, distance2)), dataFrom, automatic));
                            String detail512 = getDetail51(date51);
                            if (sportType == 131) {
                                saveTb51Swim(uid, age, year, month, day, startUTime, endUTime, detail512, dataFrom, automatic, sportType, stepList);
                            } else {
                                saveTb51Heart(uid, age, year, month, day, startUTime, endUTime, detail512, dataFrom, automatic, JsonUtils.toJson(heart53), sportType, stepList);
                            }
                            sportType = 0;
                            calorie2 = 0.0f;
                            distance2 = 0.0f;
                            pauseTime2 = 0;
                            automatic = 0;
                        }
                    } else if (((TB_61_data) arrayList2.get(i3)).getState_type() == 1) {
                        pauseTime2 = 0;
                        heart53.clear();
                        stepList.clear();
                        isOver = false;
                        automatic = ((TB_61_data) arrayList2.get(i3)).getAutomatic();
                        for (int z = 0; z < 6; z++) {
                            date51[z] = 0;
                        }
                        int heart2 = ((TB_61_data) arrayList2.get(i3)).getAvg_bpm();
                        if (heart2 < 30 || (heart2 > maxHeart && maxHeart != 0)) {
                            heart2 = 0;
                        }
                        minStep = ((TB_61_data) arrayList2.get(i3)).getStep();
                        lastHeartTime = "";
                        String nowHeartTime2 = ((TB_61_data) arrayList2.get(i3)).getHour() + "/" + ((TB_61_data) arrayList2.get(i3)).getMin();
                        boolean needAdd2 = false;
                        if (!nowHeartTime2.equals(lastHeartTime)) {
                            heart53.add(Integer.valueOf(heart2));
                            lastHeartTime = nowHeartTime2;
                            lastHeart = heart2;
                            needAdd2 = true;
                        } else if (lastHeart == 0 && heart2 > 0) {
                            if (heart53.size() > 0) {
                                heart53.removeLast();
                            }
                            heart53.add(Integer.valueOf(heart2));
                            lastHeart = heart2;
                            needAdd2 = true;
                        }
                        if (heart2 != 0 && needAdd2) {
                            int level2 = getHeartLev(maxHeart, heart2);
                            if (level2 != -1) {
                                date51[level2] = 1;
                            }
                        }
                        calorie2 = 0.0f + ((TB_61_data) arrayList2.get(i3)).getCalorie();
                        distance2 = 0.0f + ((TB_61_data) arrayList2.get(i3)).getDistance();
                        step3 = 0 + ((TB_61_data) arrayList2.get(i3)).getStep();
                        sportType = ((TB_61_data) arrayList2.get(i3)).getSport_type();
                        stTime = (((TB_61_data) arrayList2.get(i3)).getHour() * 60) + ((TB_61_data) arrayList2.get(i3)).getMin();
                        startUTime = Util.date2TimeStamp(year, month, day, ((TB_61_data) arrayList2.get(i3)).getHour(), ((TB_61_data) arrayList2.get(i3)).getMin(), ((TB_61_data) arrayList2.get(i3)).getReserve());
                        KLog.d("tset61s", "no2855时间开始: " + startUTime + " - " + sportType);
                    } else {
                        step3 = step;
                    }
                    if (i3 == arrayList2.size() - 1 && !isOver) {
                        int df3 = ((int) (lastTime - startUTime)) - pauseTime2;
                        int activity4 = df3 % 60 == 0 ? df3 / 60 : (df3 / 60) + 1;
                        KLog.d("no2855-->结束异常标志: " + lastTime + " -startUTime: " + startUTime + " pauseTime: " + pauseTime2 + " activity: " + activity4);
                        if (activity4 > 0) {
                            arrayList4.add(DataUtil.getTbSport(uid, sportType, year, month, day, stTime, startUTime, lastEt, lastTime, calorie2, JsonUtils.toJson(getDetail(activity4 + automatic, step3, distance2)), dataFrom, automatic));
                            DataUtil.saveBlueToGpsSport(uid, sportType, startUTime, lastTime, calorie2, step3, distance2, dataFrom, automatic, pauseTime2, 0, "1");
                            String detail513 = getDetail51(date51);
                            if (sportType == 131) {
                                saveTb51Swim(uid, age, year, month, day, startUTime, endUTime, detail513, dataFrom, automatic, sportType, stepList);
                            } else {
                                saveTb51Heart(uid, age, year, month, day, startUTime, endUTime, detail513, dataFrom, automatic, JsonUtils.toJson(heart53), sportType, stepList);
                            }
                            sportType = 0;
                            pauseTime2 = 0;
                        }
                    }
                }
                i3++;
            }
            int i4 = step;
        }
        if (arrayList4.size() > 0) {
            long[] times = new long[arrayList4.size()];
            int[] tr1 = new int[arrayList4.size()];
            for (int i5 = 0; i5 < arrayList4.size(); i5++) {
                tr1[i5] = i5;
                times[i5] = ((TB_v3_sport_data) arrayList4.get(i5)).getStart_uxtime();
            }
            for (int i6 = 0; i6 < times.length - 1; i6++) {
                for (int j2 = 0; j2 < (times.length - i6) - 1; j2++) {
                    if (times[j2] < times[j2 + 1]) {
                        long temp = times[j2];
                        times[j2] = times[j2 + 1];
                        times[j2 + 1] = temp;
                        int tr2 = tr1[j2];
                        tr1[j2] = tr1[j2 + 1];
                        tr1[j2 + 1] = tr2;
                    }
                }
            }
            ArrayList arrayList5 = new ArrayList();
            for (int i7 = arrayList4.size() - 1; i7 >= 0; i7--) {
                arrayList5.add(arrayList4.get(tr1[i7]));
            }
            try {
                ArrayList arrayList6 = new ArrayList();
                int count = arrayList5.size();
                int mStep = 0;
                int mSTime = 0;
                float mDis = 0.0f;
                int mAct = 0;
                double mCals = Utils.DOUBLE_EPSILON;
                int mNum = 0;
                long stUTime = 0;
                int index = 0;
                for (int i8 = 0; i8 < count; i8++) {
                    Detail_data detail_data = (Detail_data) JsonUtils.fromJson(((TB_v3_sport_data) arrayList5.get(i8)).getDetail_data(), Detail_data.class);
                    if (i8 < count - 1) {
                        detail_data1 = (Detail_data) JsonUtils.fromJson(((TB_v3_sport_data) arrayList5.get(i8)).getDetail_data(), Detail_data.class);
                    } else {
                        detail_data1 = new Detail_data();
                    }
                    if (i8 < count - 1 && ((TB_v3_sport_data) arrayList5.get(i8)).getSport_type() == 1) {
                        if (((TB_v3_sport_data) arrayList5.get(i8)).getSport_type() == ((TB_v3_sport_data) arrayList5.get(i8 + 1)).getSport_type()) {
                            if (((TB_v3_sport_data) arrayList5.get(i8)).getStart_time() / 60 == ((TB_v3_sport_data) arrayList5.get(i8 + 1)).getStart_time() / 60 && (detail_data.getActivity() <= 5 || detail_data1.getActivity() <= 5)) {
                                if (((TB_v3_sport_data) arrayList5.get(i8 + 1)).getStart_uxtime() - ((TB_v3_sport_data) arrayList5.get(i8)).getEnd_uxtime() <= 1800) {
                                    mStep += detail_data.getStep();
                                    mDis += detail_data.getDistance();
                                    mAct += detail_data.getActivity();
                                    mCals += ((TB_v3_sport_data) arrayList5.get(i8)).getCalorie();
                                    index += detail_data.getCount();
                                    if (mNum == 0) {
                                        mSTime = ((TB_v3_sport_data) arrayList5.get(i8)).getStart_time();
                                        stUTime = ((TB_v3_sport_data) arrayList5.get(i8)).getStart_uxtime();
                                    }
                                    mNum++;
                                }
                            }
                        }
                    }
                    int mStep2 = mStep + detail_data.getStep();
                    float mDis2 = mDis + detail_data.getDistance();
                    int mAct2 = mAct + detail_data.getActivity();
                    index += detail_data.getCount();
                    int mETime = ((TB_v3_sport_data) arrayList5.get(i8)).getEnd_time();
                    long edUTime = ((TB_v3_sport_data) arrayList5.get(i8)).getEnd_uxtime();
                    if (mNum == 0) {
                        mSTime = ((TB_v3_sport_data) arrayList5.get(i8)).getStart_time();
                        stUTime = ((TB_v3_sport_data) arrayList5.get(i8)).getStart_uxtime();
                    }
                    double mCals2 = mCals + ((TB_v3_sport_data) arrayList5.get(i8)).getCalorie();
                    detail_data.setStep(mStep2);
                    detail_data.setDistance(mDis2);
                    detail_data.setActivity(mAct2);
                    detail_data.setCount(index);
                    ((TB_v3_sport_data) arrayList5.get(i8)).setDetail_data(JsonUtils.toJson(detail_data));
                    ((TB_v3_sport_data) arrayList5.get(i8)).setStart_time(mSTime);
                    ((TB_v3_sport_data) arrayList5.get(i8)).setStart_uxtime(stUTime);
                    ((TB_v3_sport_data) arrayList5.get(i8)).setEnd_time(mETime);
                    ((TB_v3_sport_data) arrayList5.get(i8)).setCalorie(mCals2);
                    ((TB_v3_sport_data) arrayList5.get(i8)).setEnd_uxtime(edUTime);
                    if (!arrayList6.contains(arrayList5.get(i8))) {
                        arrayList6.add(arrayList5.get(i8));
                    }
                    mStep = 0;
                    mSTime = 0;
                    mDis = 0.0f;
                    mAct = 0;
                    mCals = Utils.DOUBLE_EPSILON;
                    mNum = 0;
                    stUTime = 0;
                }
                for (int i9 = 0; i9 < arrayList6.size(); i9++) {
                    ((TB_v3_sport_data) arrayList6.get(i9)).saveOrUpdate("uid=? and start_uxtime=? and data_from=? and sport_type=?", uid + "", ((TB_v3_sport_data) arrayList6.get(i9)).getStart_uxtime() + "", dataFrom + "", ((TB_v3_sport_data) arrayList6.get(i9)).getSport_type() + "");
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                KLog.e("解析61运动异常");
            }
        }
    }

    public static int getHeartLev(int maxHeart, int heart) {
        if (((double) heart) <= ((double) maxHeart) * 0.5d && heart >= 35) {
            return 0;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.6d) {
            return 1;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.7d) {
            return 2;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.8d) {
            return 3;
        }
        if (((double) heart) <= ((double) maxHeart) * 0.9d) {
            return 4;
        }
        if (((double) heart) <= ((double) maxHeart) * 1.0d) {
            return 5;
        }
        return -1;
    }

    public static void mtkServerSleepDataToSleepFinal(F1SleepData f1SleepData, String date) {
        int activity;
        if (f1SleepData.getData().getD() == null) {
            return;
        }
        if ((f1SleepData.getData().getS() == 0 || f1SleepData.getData().getS() == 1) && f1SleepData.getData().getC() == 1) {
            ArrayList arrayList = new ArrayList();
            List<SleepdataBean> data = f1SleepData.getData().getD();
            if (data.size() > 0) {
                int totalDeep = 0;
                int totalLight = 0;
                int totalWakeUp = 0;
                SleepSegment tampSeg = new SleepSegment();
                for (int i = 0; i < data.size(); i++) {
                    SleepdataBean bean = (SleepdataBean) data.get(i);
                    SleepSegment segment = new SleepSegment();
                    int start = (bean.getA().getH() * 60) + bean.getA().getI();
                    int end = (bean.getO().getH() * 60) + bean.getO().getI();
                    if (start <= end) {
                        activity = end - start;
                    } else {
                        activity = (end + 1440) - start;
                    }
                    if (i == 0) {
                        segment.setSt(0);
                        segment.setEt(activity);
                        segment.setType(bean.getM());
                        tampSeg = segment;
                        arrayList.add(0, segment);
                    } else if (i > 0) {
                        segment.setSt(tampSeg.getEt());
                        segment.setEt(tampSeg.getEt() + activity);
                        segment.setType(bean.getM());
                        arrayList.add(segment);
                        tampSeg = segment;
                    }
                    int sleepType = bean.getM();
                    if (sleepType == 3) {
                        totalDeep += activity;
                    } else if (sleepType == 4) {
                        totalLight += activity;
                    } else if (sleepType == 6) {
                        totalWakeUp += activity;
                    }
                }
                F1SleepTimeBean startTime = f1SleepData.getData().getI();
                F1SleepTimeBean endTime = f1SleepData.getData().getO();
                DateUtil startDateUtil = new DateUtil(startTime.getY() + 2000, startTime.getM(), startTime.getD(), startTime.getH(), startTime.getI());
                DateUtil endDateUtil = new DateUtil(endTime.getY() + 2000, endTime.getM(), endTime.getD(), endTime.getH(), endTime.getI());
                TB_SLEEP_Final_DATA sleepDataByDate1 = Mtk_DeviceBaseInfoSqlUtil.getSleepDataByDate1(ContextUtil.app, date);
                if (sleepDataByDate1 == null) {
                    sleepDataByDate1 = new TB_SLEEP_Final_DATA();
                    sleepDataByDate1.setToDefault("_uploaded");
                    sleepDataByDate1.setUid(ContextUtil.getLUID());
                    sleepDataByDate1.setData_from(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
                    sleepDataByDate1.setDate(date);
                }
                int score = SleepScoreHandler.calSleepScore(totalDeep + totalLight + totalWakeUp, totalDeep, startDateUtil.getUnixTimestamp());
                sleepDataByDate1.setYear(startDateUtil.getYear());
                sleepDataByDate1.setMonth(startDateUtil.getMonth());
                sleepDataByDate1.setStart_time(startDateUtil.getUnixTimestamp());
                sleepDataByDate1.setEnd_time(endDateUtil.getUnixTimestamp());
                sleepDataByDate1.setDeepSleepTime((float) totalDeep);
                sleepDataByDate1.setLightSleepTime((float) totalLight);
                sleepDataByDate1.setScore(score);
                sleepDataByDate1.setSleep_segment(JsonUtils.toJson(arrayList));
                sleepDataByDate1.save();
            }
        }
    }

    private static String getDetail51(int[] time) {
        HeartRateDetial detial = new HeartRateDetial();
        detial.setR0(time[0]);
        detial.setR1(time[1]);
        detial.setR2(time[2]);
        detial.setR3(time[3]);
        detial.setR4(time[4]);
        detial.setR5(time[5]);
        KLog.e("no2855-->=============================" + JsonUtils.toJson(detial));
        return JsonUtils.toJson(detial);
    }

    private static void saveTb51Heart(long uid, int age, int year, int month, int day, long sUTime, long eUTime, String detail, String dataFrom, int automatic, String heart53, int sportType, LinkedList<Integer> steps) {
        TB_heartrate_data heart = new TB_heartrate_data();
        heart.setUid(uid);
        heart.setYear(year);
        heart.setMonth(month);
        heart.setDay(day);
        heart.setStart_time(sUTime - ((long) (automatic * 60)));
        heart.setEnd_time(eUTime);
        heart.setDetail_data(detail);
        heart.setData_from(dataFrom);
        heart.setReserved(heart53);
        heart.setAge(age);
        heart.setSport_type(sportType);
        heart.saveOrUpdate("uid=? and start_time=? and data_from=?", uid + "", sUTime + "", dataFrom + "");
        if (steps == null || steps.size() <= 0) {
            DataUtil.saveHr2File(heart, null, false, false);
            return;
        }
        int[] walks = new int[steps.size()];
        int i = 0;
        Iterator it = steps.iterator();
        while (it.hasNext()) {
            walks[i] = ((Integer) it.next()).intValue();
            i++;
        }
        DataUtil.saveHr2File(heart, walks, false, false);
    }

    private static void saveTb51Swim(long uid, int age, int year, int month, int day, long sUTime, long eUTime, String detail, String dataFrom, int automatic, int sportType, LinkedList<Integer> steps) {
        TB_swimrate_data heart = new TB_swimrate_data();
        heart.setUid(uid);
        heart.setYear(year);
        heart.setMonth(month);
        heart.setDay(day);
        heart.setStart_time(sUTime - ((long) (automatic * 60)));
        heart.setEnd_time(eUTime);
        heart.setDetail_data(detail);
        heart.setData_from(dataFrom);
        heart.setAge(age);
        heart.setSport_type(sportType);
        heart.setReserved(JsonUtils.toJson(steps));
        heart.saveOrUpdate("uid=? and start_time=? and data_from=?", uid + "", sUTime + "", dataFrom + "");
        DataUtil.saveSwim2File(heart, steps, false);
    }

    public static void mtkLocalSleepDataToSleepFinal(SA_SleepBufInfo f1SleepData, String date) {
        int activity;
        KLog.i("==========mtkLocalSleepDataToSleepFinal==========" + JsonUtils.toJson(f1SleepData));
        if (f1SleepData.sleepdata == null) {
            return;
        }
        if (f1SleepData.datastatus == 0 || f1SleepData.datastatus == 1) {
            List<SleepSegment> segList = new ArrayList<>();
            SA_SleepDataInfo[] sleepData = f1SleepData.sleepdata;
            if (sleepData.length > 0) {
                int totalDeep = 0;
                int totalLight = 0;
                int totalWakeUp = 0;
                SleepSegment tampSeg = new SleepSegment();
                for (int i = 0; i < sleepData.length; i++) {
                    SA_SleepDataInfo bean = sleepData[i];
                    SleepSegment segment = new SleepSegment();
                    int start = (bean.startTime.hour * 60) + bean.startTime.minute;
                    int end = (bean.stopTime.hour * 60) + bean.stopTime.minute;
                    if (start <= end) {
                        activity = end - start;
                    } else {
                        activity = (end + 1440) - start;
                    }
                    KLog.e(start + "===no2855-->=start" + end + "end" + activity + "activity=====");
                    if (i == 0) {
                        segment.setSt(0);
                        segment.setEt(activity);
                        segment.setType(bean.sleepMode);
                        tampSeg = segment;
                        segList.add(0, segment);
                    } else if (i > 0) {
                        segment.setSt(tampSeg.getEt());
                        segment.setEt(tampSeg.getEt() + activity);
                        segment.setType(bean.sleepMode);
                        segList.add(segment);
                        tampSeg = segment;
                    }
                    int sleepType = bean.sleepMode;
                    if (sleepType == 3) {
                        totalDeep += activity;
                    } else if (sleepType == 4) {
                        totalLight += activity;
                    } else if (sleepType == 6) {
                        totalWakeUp += activity;
                    }
                }
                DateUtil startDateUtil = new DateUtil(f1SleepData.inSleepTime.year + 2000, f1SleepData.inSleepTime.month, f1SleepData.inSleepTime.day, f1SleepData.inSleepTime.hour, f1SleepData.inSleepTime.minute);
                DateUtil endDateUtil = new DateUtil(f1SleepData.outSleepTime.year + 2000, f1SleepData.outSleepTime.month, f1SleepData.outSleepTime.day, f1SleepData.outSleepTime.hour, f1SleepData.outSleepTime.minute);
                TB_SLEEP_Final_DATA sleepDataByDate1 = Mtk_DeviceBaseInfoSqlUtil.getSleepDataByDate1(ContextUtil.app, date);
                if (sleepDataByDate1 == null) {
                    sleepDataByDate1 = new TB_SLEEP_Final_DATA();
                    sleepDataByDate1.setToDefault("_uploaded");
                    sleepDataByDate1.setUid(ContextUtil.getLUID());
                    sleepDataByDate1.setData_from(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
                    sleepDataByDate1.setDate(date);
                }
                try {
                    int score = SleepScoreHandler.calSleepScore(totalDeep + totalLight + totalWakeUp, totalDeep, startDateUtil.getUnixTimestamp());
                    sleepDataByDate1.setYear(startDateUtil.getYear());
                    sleepDataByDate1.setMonth(startDateUtil.getMonth());
                    sleepDataByDate1.setStart_time(startDateUtil.getUnixTimestamp());
                    sleepDataByDate1.setEnd_time(endDateUtil.getUnixTimestamp());
                    sleepDataByDate1.setDeepSleepTime((float) totalDeep);
                    sleepDataByDate1.setLightSleepTime((float) totalLight);
                    sleepDataByDate1.setScore(score);
                    sleepDataByDate1.setSleep_segment(JsonUtils.toJson(segList));
                    sleepDataByDate1.saveAsync().listen(new SaveCallback() {
                        public void onFinish(boolean success) {
                            EventBus.getDefault().post(new ViewRefresh(false, 97));
                            KLog.d("testSleep", "no2855-->testSleep睡眠保存并指出来: ");
                            HealthDataEventBus.updateHealthSleepEvent();
                        }
                    });
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        } else {
            KLog.e("no2855--> 睡眠 return111 ");
        }
    }

    public static void mtk61DataToBloodData(long uid, int year, int month, int day, String dataFrom, List<TB_61_data> datas) {
        for (TB_61_data t61Data : datas) {
            int sbp = t61Data.getSbp();
            int dbp = t61Data.getDbp();
            if (!(sbp == 0 || dbp == 0)) {
                DateUtil d = new DateUtil(year, month, day, t61Data.getHour(), t61Data.getMin(), t61Data.getReserve());
                TB_BP_data tbp = (TB_BP_data) DataSupport.where("uid =? and dataFrom=? and bpTime=?", ContextUtil.getUID(), ContextUtil.getDeviceNameNoClear(), d.getUnixTimestamp() + "").findFirst(TB_BP_data.class);
                if (tbp == null) {
                    tbp = new TB_BP_data();
                }
                tbp.setUid(ContextUtil.getLUID());
                tbp.setDataFrom(ContextUtil.getDeviceNameNoClear());
                tbp.setDbp(dbp);
                tbp.setSbp(sbp);
                tbp.setBpTime(d.getUnixTimestamp());
                tbp.saveOrUpdate("uid =? and dataFrom=? and bpTime=?", ContextUtil.getUID(), ContextUtil.getDeviceNameNoClear(), d.getUnixTimestamp() + "");
            }
        }
    }

    public static void save61FileSleep(String date) {
        try {
            SA_SleepBufInfo retData = new SA_SleepBufInfo();
            long uid = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app).uid;
            String path = FilePath.Mtk_Ble_61_Sleep_Dir + date + "/uid-" + uid + "-date-" + date + "-source-" + ContextUtil.getDeviceNameNoClear();
            KLog.d("testSleep", "no2855-->testSleep睡眠：" + path + "   存在？" + FileUtils.checkFileExists(path));
            L.file("testSleep睡眠：" + path + "   存在？" + FileUtils.checkFileExists(path), 3);
            if (FileUtils.checkFileExists(path)) {
                String str = date;
                retData.datastatus = new NativeInvoker().calculateSleep(Environment.getExternalStorageDirectory().getAbsolutePath() + FilePath.Mtk_Ble_61_Sleep_Dir, uid, str, ContextUtil.getDeviceNameNoClear(), 1, retData);
                String msf = JsonUtils.toJson(retData);
                KLog.d("testSleep", "no2855-->testSleep: " + msf);
                L.file("testSleep: " + msf, 3);
                mtkLocalSleepDataToSleepFinal(retData, date);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static String year2DateStr(int year, int month, int day) {
        return year + String.format("%02d", new Object[]{Integer.valueOf(month)}) + String.format("%02d", new Object[]{Integer.valueOf(day)});
    }

    private static Detail_data getDetail(int activity, int step, float distance) {
        Detail_data d = new Detail_data();
        d.setActivity(activity);
        d.setCount(0);
        d.setStep(step);
        d.setDistance(distance);
        return d;
    }

    public static boolean isP1Version2() {
        if (WristbandModel.MODEL_P1_V1.equals(DeviceUtils.getDeviceInfo().getModel())) {
            return false;
        }
        return true;
    }

    public static int getMaxHeart(int age) {
        return 220 - age;
    }

    public static void saveGpsToBlue(long uid, String dataFrom, int year, int month, int day) {
        DataUtil.writeMtkGps2TB(uid, dataFrom, year, month, day, false);
    }
}
