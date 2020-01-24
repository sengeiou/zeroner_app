package com.iwown.device_module.common.Bluetooth.receiver.iv.dao;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.Util;
import com.iwown.data_link.sleep_data.SleepScoreHandler;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.SleepSegment;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.SleepStatusFlag;
import com.iwown.device_module.common.sql.sleep.TB_SLEEP_Final_DATA;
import com.iwown.device_module.common.sql.sleep.TB_v3_sleep_data;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.litepal.crud.DataSupport;

public class V3_sleepData_biz {
    public int queryDataExixt(TB_v3_sleep_data tb_v3_sleep_data) {
        int index = 0;
        try {
            return DataSupport.where("uid=? and year=? and month=? and day=? and start_time=? and end_time=? and data_from=? and sleep_type=?", PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid) + "", tb_v3_sleep_data.getYear() + "", tb_v3_sleep_data.getMonth() + "", tb_v3_sleep_data.getDay() + "", tb_v3_sleep_data.getStart_time() + "", tb_v3_sleep_data.getEnd_time() + "", PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name) + "", tb_v3_sleep_data.getSleep_type() + "").count(TB_v3_sleep_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return index;
        }
    }

    public int query_UPLOAD(String uId) {
        try {
            return DataSupport.where(" email=? AND _uploaded=?", uId, "0").count(TB_v3_sleep_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return 0;
        }
    }

    public static int queryDataExixt(long uid, int year, int month, int day, int startTime, int endTime, int activityTime, int type) {
        int index = 0;
        try {
            return DataSupport.where(" uid=? and day=? and start_time=? and end_time=? and activity=? and sleep_type=? and data_from=?", uid + "", day + "", startTime + "", endTime + "", activityTime + "", type + "", PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name)).count(TB_v3_sleep_data.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return index;
        }
    }

    public static List<TB_v3_sleep_data> querySleepData(String uid, String dataFrom, int year, int month, int day) {
        new ArrayList();
        DateUtil date = new DateUtil(year, month, day);
        DateUtil preDate = new DateUtil(date.getYear(), date.getMonth(), date.getDay());
        preDate.addDay(-1);
        return DataSupport.where("uid =? and data_from=? and ((month=? and day=? and start_time>=1080) or (month=? and day=? and start_time<1080))", uid, dataFrom, String.valueOf(preDate.getMonth()), String.valueOf(preDate.getDay()), String.valueOf(date.getMonth()), String.valueOf(date.getDay())).find(TB_v3_sleep_data.class);
    }

    public static List<TB_v3_sleep_data> deleteSoberSleepData(List<TB_v3_sleep_data> data) {
        List<TB_v3_sleep_data> sleeps = new ArrayList<>();
        List<Integer> placedIndexList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (((TB_v3_sleep_data) data.get(i)).getSleep_type() == 5) {
                placedIndexList.add(Integer.valueOf(i));
                for (int j = i - 1; j >= 0; j--) {
                    int preSleepType = ((TB_v3_sleep_data) data.get(j)).getSleep_type();
                    placedIndexList.add(Integer.valueOf(j));
                    if (preSleepType == 1) {
                        break;
                    }
                }
            }
        }
        for (int i2 = 0; i2 < data.size(); i2++) {
            if (!placedIndexList.contains(Integer.valueOf(i2))) {
                sleeps.add(data.get(i2));
            }
        }
        return sleeps;
    }

    public static void sleepDetail(long uid, String dataFrom, int y, int m, int d) {
        int activity;
        boolean isType2;
        List<TB_v3_sleep_data> sleepList = deleteSoberSleepData(querySleepData(String.valueOf(uid), dataFrom, y, m, d));
        if (sleepList.size() > 0) {
            int sleepTime = 0;
            int totalTime = 0;
            ArrayList<SleepStatusFlag> sleepStatus = new ArrayList<>();
            int totalSleepTime = 0;
            int deepSleepTotal = 0;
            int lightSleepTotal = 0;
            int noSleepTotal = 0;
            long startJudge = 0;
            long endJudge = 0;
            int endSize = -2;
            int soberTime = 0;
            int startMin = 0;
            int endMin = 0;
            boolean isStartTimeFound = false;
            long mSTime = 0;
            long mETime = 0;
            for (int i = 0; i < sleepList.size(); i++) {
                int newTime = ((TB_v3_sleep_data) sleepList.get(i)).getAll_end() - ((TB_v3_sleep_data) sleepList.get(i)).getAll_start();
                if (!(newTime == 0 || newTime == sleepTime)) {
                    sleepTime = newTime;
                    totalTime += newTime >= 0 ? newTime : (1440 - ((TB_v3_sleep_data) sleepList.get(i)).getAll_start()) + ((TB_v3_sleep_data) sleepList.get(i)).getAll_end();
                }
                int startTime = ((TB_v3_sleep_data) sleepList.get(i)).getStart_time();
                int endTime = ((TB_v3_sleep_data) sleepList.get(i)).getEnd_time();
                int sleepType = ((TB_v3_sleep_data) sleepList.get(i)).getSleep_type();
                int sleepNum = endTime - startTime;
                if (sleepNum >= 0) {
                    activity = sleepNum;
                } else {
                    activity = (1440 - startTime) + endTime;
                }
                int year = ((TB_v3_sleep_data) sleepList.get(i)).getYear();
                int month = ((TB_v3_sleep_data) sleepList.get(i)).getMonth();
                int day = ((TB_v3_sleep_data) sleepList.get(i)).getDay();
                long stUx = Util.date2TimeStamp(year, month, day, startTime / 60, startTime % 60);
                DateUtil dateUtil = new DateUtil(year, month, day);
                if (startTime > endTime) {
                    dateUtil.addDay(1);
                }
                long etUx = Util.date2TimeStamp(dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), endTime / 60, endTime % 60);
                KLog.e("NewSleepActivity----", ((TB_v3_sleep_data) sleepList.get(i)).toJson());
                if (sleepType == 3) {
                    SleepStatusFlag sleepStatusFlag = new SleepStatusFlag(activity, 1, startTime);
                    sleepStatus.add(sleepStatusFlag);
                    deepSleepTotal += activity;
                    totalSleepTime += activity;
                } else if (sleepType == 4) {
                    SleepStatusFlag sleepStatusFlag2 = new SleepStatusFlag(activity, 2, startTime);
                    sleepStatus.add(sleepStatusFlag2);
                    lightSleepTotal += activity;
                    totalSleepTime += activity;
                }
                if (i == 0) {
                    startMin = startTime;
                    startJudge = stUx;
                    if (newTime > 0) {
                        mSTime = Util.date2TimeStamp(((TB_v3_sleep_data) sleepList.get(i)).getYear(), ((TB_v3_sleep_data) sleepList.get(i)).getMonth(), ((TB_v3_sleep_data) sleepList.get(i)).getDay(), ((TB_v3_sleep_data) sleepList.get(i)).getAll_start() / 60, ((TB_v3_sleep_data) sleepList.get(i)).getAll_start() % 60);
                    } else {
                        mSTime = Util.date2TimeStamp(((TB_v3_sleep_data) sleepList.get(i)).getYear(), ((TB_v3_sleep_data) sleepList.get(i)).getMonth(), ((TB_v3_sleep_data) sleepList.get(i)).getDay(), ((TB_v3_sleep_data) sleepList.get(i)).getStart_time() / 60, ((TB_v3_sleep_data) sleepList.get(i)).getStart_time() % 60);
                    }
                }
                if (i == sleepList.size() - 1) {
                    if (endJudge < etUx) {
                        endJudge = etUx;
                        endMin = endTime;
                    }
                    if (newTime > 0) {
                        mETime = Util.date2TimeStamp(((TB_v3_sleep_data) sleepList.get(i)).getYear(), ((TB_v3_sleep_data) sleepList.get(i)).getMonth(), ((TB_v3_sleep_data) sleepList.get(i)).getDay(), ((TB_v3_sleep_data) sleepList.get(i)).getAll_end() / 60, ((TB_v3_sleep_data) sleepList.get(i)).getAll_end() % 60);
                        if (mETime < mSTime) {
                            mETime += 86400;
                        }
                    } else {
                        mETime = Util.date2TimeStamp(((TB_v3_sleep_data) sleepList.get(i)).getYear(), ((TB_v3_sleep_data) sleepList.get(i)).getMonth(), ((TB_v3_sleep_data) sleepList.get(i)).getDay(), ((TB_v3_sleep_data) sleepList.get(i)).getEnd_time() / 60, ((TB_v3_sleep_data) sleepList.get(i)).getEnd_time() % 60);
                        if (mETime < mSTime) {
                            mETime += 86400;
                        }
                    }
                }
                if (sleepType == 2) {
                    isType2 = true;
                    if (totalSleepTime < totalTime) {
                        SleepStatusFlag sleepStatusFlag3 = new SleepStatusFlag(totalTime - totalSleepTime, 2, endTime);
                        sleepStatus.add(sleepStatusFlag3);
                        totalSleepTime = totalTime;
                    }
                    if (!isStartTimeFound) {
                        if (startJudge < stUx) {
                            startJudge = stUx;
                            startMin = startTime;
                        }
                        isStartTimeFound = true;
                    }
                    if (endJudge < etUx) {
                        endJudge = etUx;
                        endMin = endTime;
                    }
                    endSize = i;
                    soberTime = endTime;
                } else {
                    isType2 = false;
                }
                if (i == endSize + 1 && !isType2) {
                    if (startTime < soberTime) {
                        SleepStatusFlag sleepStatusFlag4 = new SleepStatusFlag((startTime - soberTime) + 1440, 0, soberTime);
                        sleepStatus.add(sleepStatusFlag4);
                        noSleepTotal += (startTime - soberTime) + 1440;
                    } else {
                        SleepStatusFlag sleepStatusFlag5 = new SleepStatusFlag(startTime - soberTime, 0, soberTime);
                        sleepStatus.add(sleepStatusFlag5);
                        noSleepTotal += startTime - soberTime;
                    }
                }
                if (i == sleepList.size() - 1 && endTime < startTime) {
                    new DateUtil(year, month, day).addDay(1);
                }
            }
            if (totalTime > 0) {
                if (totalSleepTime < totalTime) {
                    SleepStatusFlag sleepStatusFlag6 = new SleepStatusFlag(totalTime - totalSleepTime, 2, endMin);
                    sleepStatus.add(sleepStatusFlag6);
                    totalSleepTime = totalTime;
                }
                startMin = ((TB_v3_sleep_data) sleepList.get(0)).getAll_start();
                endMin = ((TB_v3_sleep_data) sleepList.get(sleepList.size() - 1)).getAll_end();
                if (lightSleepTotal + deepSleepTotal < totalTime) {
                    lightSleepTotal = totalTime - deepSleepTotal;
                }
            }
            KLog.e("SleepDate----", "///startMin =" + startMin + "endMin=" + endMin);
            ArrayList<Integer> barWidth = new ArrayList<>();
            ArrayList arrayList = new ArrayList();
            int upStart = 0;
            int deepTime = 0;
            int lightTime = 0;
            Iterator it = sleepStatus.iterator();
            while (it.hasNext()) {
                SleepStatusFlag sleep = (SleepStatusFlag) it.next();
                if (totalSleepTime + noSleepTotal != 0) {
                    barWidth.add(Integer.valueOf(sleep.getTime()));
                    if (sleep.isDeepFlag() == 1) {
                        SleepSegment sleepSegment = new SleepSegment(sleep.getTime() + upStart, upStart, 3);
                        arrayList.add(sleepSegment);
                        deepTime += sleep.getTime();
                    } else if (sleep.isDeepFlag() == 2) {
                        SleepSegment sleepSegment2 = new SleepSegment(sleep.getTime() + upStart, upStart, 4);
                        arrayList.add(sleepSegment2);
                        lightTime += sleep.getTime();
                    } else {
                        SleepSegment sleepSegment3 = new SleepSegment(sleep.getTime() + upStart, upStart, 6);
                        arrayList.add(sleepSegment3);
                    }
                    upStart += sleep.getTime();
                    KLog.i("持续时间time = " + sleep.getTime());
                }
            }
            if (arrayList.size() > 0) {
                SleepSegment sleepSegment4 = new SleepSegment(upStart, upStart, 2);
                arrayList.add(sleepSegment4);
            }
            int score = SleepScoreHandler.calSleepScore(deepSleepTotal + lightSleepTotal + noSleepTotal, deepSleepTotal, mSTime);
            TB_SLEEP_Final_DATA entity = new TB_SLEEP_Final_DATA();
            entity.setStart_time(mSTime);
            entity.setEnd_time(mETime);
            DateUtil dateUtil2 = new DateUtil(y, m, d);
            entity.setDate(dateUtil2.getSyyyyMMddDate());
            entity.setYear(y);
            entity.setMonth(m);
            entity.setFeel_type(0);
            entity.setLightSleepTime((float) lightSleepTotal);
            entity.setDeepSleepTime((float) deepTime);
            entity.setUid(uid);
            entity.setScore(score);
            entity.setSleep_segment(JsonUtils.toJson(arrayList));
            entity.setData_from(dataFrom);
            entity.saveOrUpdate("uid=? and start_time=?", uid + "", mSTime + "");
        }
    }
}
