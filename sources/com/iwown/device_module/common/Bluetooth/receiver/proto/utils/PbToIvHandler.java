package com.iwown.device_module.common.Bluetooth.receiver.proto.utils;

import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.ble_module.utils.Util;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.HeartRateDetial;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.utils.DataUtil;
import com.iwown.device_module.common.sql.ProtoBuf_80_data;
import com.iwown.device_module.common.sql.TB_v3_sport_data;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PbToIvHandler {
    public static void pbDataToHeart(long uid, int year, int month, int day, String dataFrom, List<ProtoBuf_80_data> datas) {
        int mSize = 0;
        if (datas != null) {
            mSize = datas.size();
        }
        KLog.i("==========================" + year + "" + month + "" + day + "-----" + JsonUtils.toJson(datas));
        if (mSize > 0) {
            List<Integer> heart53 = new ArrayList<>();
            int count53 = 0;
            int heartPre = 0;
            for (int i = 0; i < mSize; i++) {
                if (i < mSize - 1) {
                    if (((ProtoBuf_80_data) datas.get(i)).getHour() != ((ProtoBuf_80_data) datas.get(i + 1)).getHour()) {
                        for (int j = count53; j < 60; j++) {
                            int heart = ((ProtoBuf_80_data) datas.get(i + 1)).getAvg_bpm();
                            if (BluetoothOperation.isProtoBuf()) {
                                if (j % 10 == 1) {
                                    heartPre = 0;
                                }
                            } else if (j % 10 == 0) {
                                heartPre = 0;
                            }
                            if (heart != 0) {
                                heart53.add(Integer.valueOf(heart));
                                heartPre = heart;
                            } else {
                                heart53.add(Integer.valueOf(heartPre));
                            }
                        }
                        DataUtil.saveTb53Heart(uid, year, month, day, ((ProtoBuf_80_data) datas.get(i)).getHour(), heart53, dataFrom);
                        heart53 = new ArrayList<>();
                        count53 = 0;
                    } else {
                        int j2 = count53;
                        while (j2 <= ((ProtoBuf_80_data) datas.get(i)).getMinute() && j2 < 60) {
                            int heart2 = ((ProtoBuf_80_data) datas.get(i)).getAvg_bpm();
                            if (BluetoothOperation.isProtoBuf()) {
                                if (j2 % 10 == 1) {
                                    heartPre = 0;
                                }
                            } else if (j2 % 10 == 0) {
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
                        count53 = ((ProtoBuf_80_data) datas.get(i)).getMinute() + 1;
                    }
                } else {
                    int j3 = count53;
                    while (j3 <= ((ProtoBuf_80_data) datas.get(i)).getMinute() && j3 < 60) {
                        int heart3 = ((ProtoBuf_80_data) datas.get(i)).getAvg_bpm();
                        if (heart3 != 0) {
                            heart53.add(Integer.valueOf(heart3));
                            heartPre = heart3;
                        } else {
                            heart53.add(Integer.valueOf(heartPre));
                        }
                        j3++;
                    }
                    DataUtil.saveTb53Heart(uid, year, month, day, ((ProtoBuf_80_data) datas.get(i)).getHour(), heart53, dataFrom);
                }
            }
        }
    }

    public static void pbFatigueDataToIv(long uid, int year, int month, int day, String dataFrom, List<ProtoBuf_80_data> datas) {
        List<FatigueData> fatigueData = new ArrayList<>();
        int mSize = 0;
        if (datas != null && datas.size() > 0) {
            mSize = datas.size();
        }
        for (int i = 0; i < mSize; i++) {
            if (((ProtoBuf_80_data) datas.get(i)).getFatigue() > 0.0f) {
                fatigueData.add(new FatigueData(String.format("%02d", new Object[]{Integer.valueOf(((ProtoBuf_80_data) datas.get(i)).getHour())}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(((ProtoBuf_80_data) datas.get(i)).getMinute())}), (int) ((ProtoBuf_80_data) datas.get(i)).getFatigue()));
            }
        }
        if (fatigueData.size() > 0) {
            DataUtil.saveFatiueData(uid, Util.date2TimeStamp(year, month, day, 0, 0, 0) * 1000, dataFrom, year2DateStr(year, month, day), JsonUtils.toJson(fatigueData));
        }
    }

    public static void sportAnd51HeartDataToIv(long uid, int year, int month, int day, String dataFrom, List<ProtoBuf_80_data> datas) {
        Detail_data detail_data1;
        int activity;
        int activity2;
        int activity3;
        int mSize = 0;
        long startUTime = 0;
        long endUTime = 0;
        int stTime = 0;
        if (datas != null && datas.size() > 0) {
            mSize = datas.size();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        new ArrayList();
        int lastWalkI = -1;
        for (int i = 0; i < mSize; i++) {
            if (((ProtoBuf_80_data) datas.get(i)).getType() == 1) {
                arrayList.add(datas.get(i));
                lastWalkI = i;
            }
            if (((ProtoBuf_80_data) datas.get(i)).getType() != 1) {
                arrayList2.add(datas.get(i));
                int mStatue = ByteUtil.int2byte(((ProtoBuf_80_data) datas.get(i)).getState());
                int automatic = mStatue >> 4;
                if ((mStatue & 15) == 1 && automatic > 0) {
                    int j = 1;
                    while (true) {
                        if (j <= automatic + 1) {
                            if (i - j == lastWalkI && lastWalkI >= 0) {
                                ((ProtoBuf_80_data) datas.get(i - 1)).endStep = ((ProtoBuf_80_data) datas.get(i)).getStep();
                                ((ProtoBuf_80_data) datas.get(i - 1)).endDis = ((ProtoBuf_80_data) datas.get(i)).getDistance();
                                ((ProtoBuf_80_data) datas.get(i - 1)).endClo = ((ProtoBuf_80_data) datas.get(i)).getCalorie();
                                int i2 = automatic;
                                ((ProtoBuf_80_data) datas.get(i - 1)).endMin = i2;
                                arrayList.remove(arrayList.size() - 1);
                                arrayList.add(datas.get(i - 1));
                                break;
                            }
                            j++;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        ArrayList arrayList3 = new ArrayList();
        if (arrayList.size() > 0) {
            float distance = ((ProtoBuf_80_data) arrayList.get(0)).getDistance();
            startUTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList.get(0)).getHour(), ((ProtoBuf_80_data) arrayList.get(0)).getMinute(), ((ProtoBuf_80_data) arrayList.get(0)).getSecond());
            endUTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList.get(0)).getHour(), ((ProtoBuf_80_data) arrayList.get(0)).getMinute(), ((ProtoBuf_80_data) arrayList.get(0)).getSecond());
            stTime = (((ProtoBuf_80_data) arrayList.get(0)).getHour() * 60) + ((ProtoBuf_80_data) arrayList.get(0)).getMinute();
            int edTime = (((ProtoBuf_80_data) arrayList.get(0)).getHour() * 60) + ((ProtoBuf_80_data) arrayList.get(0)).getMinute();
            float calorie = ((ProtoBuf_80_data) arrayList.get(0)).getCalorie();
            int activity4 = 1;
            int step = ((ProtoBuf_80_data) arrayList.get(0)).getStep();
            boolean hasFa = false;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                KLog.d("no2855 testwalk : " + ((ProtoBuf_80_data) arrayList.get(i3)).endStep);
                if (i3 > 0) {
                    if (((((ProtoBuf_80_data) arrayList.get(i3)).getHour() * 60) + ((ProtoBuf_80_data) arrayList.get(i3)).getMinute()) - edTime <= 5 || hasFa) {
                        if (hasFa) {
                            distance = 0.0f;
                            startUTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList.get(i3)).getHour(), ((ProtoBuf_80_data) arrayList.get(i3)).getMinute(), ((ProtoBuf_80_data) arrayList.get(i3)).getSecond());
                            stTime = (((ProtoBuf_80_data) arrayList.get(i3)).getHour() * 60) + ((ProtoBuf_80_data) arrayList.get(i3)).getMinute();
                            calorie = 0.0f;
                            activity4 = 0;
                            step = 0;
                            hasFa = false;
                        }
                        endUTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList.get(i3)).getHour(), ((ProtoBuf_80_data) arrayList.get(i3)).getMinute(), ((ProtoBuf_80_data) arrayList.get(i3)).getSecond());
                        edTime = (((ProtoBuf_80_data) arrayList.get(i3)).getHour() * 60) + ((ProtoBuf_80_data) arrayList.get(i3)).getMinute();
                        distance += ((ProtoBuf_80_data) arrayList.get(i3)).getDistance();
                        calorie += ((ProtoBuf_80_data) arrayList.get(i3)).getCalorie();
                        activity4++;
                        step += ((ProtoBuf_80_data) arrayList.get(i3)).getStep();
                        if (((ProtoBuf_80_data) arrayList.get(i3)).endStep > 0) {
                            hasFa = true;
                            edTime -= ((ProtoBuf_80_data) arrayList.get(i3)).endMin;
                            endUTime -= (long) (((ProtoBuf_80_data) arrayList.get(i3)).endMin * 60);
                            step -= ((ProtoBuf_80_data) arrayList.get(i3)).endStep;
                            distance -= ((ProtoBuf_80_data) arrayList.get(i3)).endDis;
                            calorie -= ((ProtoBuf_80_data) arrayList.get(i3)).endClo;
                            if (endUTime < startUTime) {
                                endUTime = startUTime + 59;
                                edTime = stTime;
                            }
                            if (step > 0 || distance > 0.0f) {
                                ArrayList arrayList4 = arrayList3;
                                arrayList4.add(DataUtil.getTbSport(uid, 1, year, month, day, stTime, startUTime, edTime, endUTime, calorie, JsonUtils.toJson(getDetail(activity4, step, distance)), dataFrom, 0));
                            }
                        }
                    } else {
                        if (step > 0 || distance > 0.0f) {
                            ArrayList arrayList5 = arrayList3;
                            arrayList5.add(DataUtil.getTbSport(uid, 1, year, month, day, stTime, startUTime, edTime, endUTime, calorie, JsonUtils.toJson(getDetail(activity4, step, distance)), dataFrom, 0));
                        }
                        distance = ((ProtoBuf_80_data) arrayList.get(i3)).getDistance();
                        startUTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList.get(i3)).getHour(), ((ProtoBuf_80_data) arrayList.get(i3)).getMinute(), ((ProtoBuf_80_data) arrayList.get(i3)).getSecond());
                        endUTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList.get(i3)).getHour(), ((ProtoBuf_80_data) arrayList.get(i3)).getMinute(), ((ProtoBuf_80_data) arrayList.get(i3)).getSecond());
                        stTime = (((ProtoBuf_80_data) arrayList.get(i3)).getHour() * 60) + ((ProtoBuf_80_data) arrayList.get(i3)).getMinute();
                        edTime = (((ProtoBuf_80_data) arrayList.get(i3)).getHour() * 60) + ((ProtoBuf_80_data) arrayList.get(i3)).getMinute();
                        calorie = ((ProtoBuf_80_data) arrayList.get(i3)).getCalorie();
                        activity4 = 1;
                        step = ((ProtoBuf_80_data) arrayList.get(i3)).getStep();
                    }
                }
                if (i3 == arrayList.size() - 1 && (step > 0 || distance > 0.0f)) {
                    ArrayList arrayList6 = arrayList3;
                    arrayList6.add(DataUtil.getTbSport(uid, 1, year, month, day, stTime, startUTime, edTime, endUTime, calorie, JsonUtils.toJson(getDetail(activity4, step, distance)), dataFrom, 0));
                }
            }
        }
        int sportType = 0;
        boolean pause = false;
        int pauseTime = 0;
        long pauseUt = 0;
        float calorie2 = 0.0f;
        float distance2 = 0.0f;
        int step2 = 0;
        long lastTime = 0;
        int lastEt = 0;
        boolean isOver = true;
        int[] date51 = new int[6];
        LinkedList<Integer> heart53 = new LinkedList<>();
        String lastHeartTime = "";
        int lastHeart = 0;
        int age = ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app).age;
        int maxHeart = getMaxHeart(age);
        int automatic2 = 0;
        if (arrayList2.size() > 0) {
            for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                if (((ProtoBuf_80_data) arrayList2.get(i4)).getType() != 0 || sportType != 0) {
                    int mStatue2 = ByteUtil.int2byte(((ProtoBuf_80_data) arrayList2.get(i4)).getState());
                    int state = mStatue2 & 15;
                    int mautomatic = mStatue2 >> 4;
                    KLog.e("no2855--> p1Min " + state + " == " + mautomatic);
                    if (!isOver && state == 1) {
                        int df = ((int) (lastTime - startUTime)) - pauseTime;
                        if (df % 60 == 0) {
                            activity3 = df / 60;
                        } else {
                            activity3 = (df / 60) + 1;
                        }
                        KLog.d("no2855-->结束异常标志: " + lastTime + " -startUTime: " + startUTime + " pauseTime: " + pauseTime + " activity: " + activity3);
                        if (activity3 > 0) {
                            arrayList3.add(DataUtil.getTbSport(uid, sportType, year, month, day, stTime, startUTime, lastEt, lastTime, calorie2, JsonUtils.toJson(getDetail(activity3 + 0, step2, distance2)), dataFrom, automatic2));
                            saveTb51Heart(uid, age, year, month, day, startUTime, endUTime, getDetail51(date51), dataFrom, automatic2, JsonUtils.toJson(heart53), sportType);
                            DataUtil.saveBlueToGpsSport(uid, sportType, startUTime, lastTime, calorie2, step2, distance2, dataFrom, automatic2, pauseTime, 0, "1");
                        }
                        sportType = 0;
                        pauseTime = 0;
                    }
                    if (sportType != 0) {
                        calorie2 += ((ProtoBuf_80_data) arrayList2.get(i4)).getCalorie();
                        distance2 += ((ProtoBuf_80_data) arrayList2.get(i4)).getDistance();
                        step2 += ((ProtoBuf_80_data) arrayList2.get(i4)).getStep();
                        int heart = ((ProtoBuf_80_data) arrayList2.get(i4)).getAvg_bpm();
                        if (heart < 30 || (heart > maxHeart && maxHeart != 0)) {
                            heart = 0;
                        }
                        String nowHeartTime = ((ProtoBuf_80_data) arrayList2.get(i4)).getHour() + "/" + ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute();
                        boolean needAdd = false;
                        if (!nowHeartTime.equals(lastHeartTime)) {
                            heart53.add(Integer.valueOf(heart));
                            lastHeartTime = nowHeartTime;
                            lastHeart = heart;
                            needAdd = true;
                        } else if (lastHeart == 0 && heart > 0) {
                            if (heart53.size() > 0) {
                                heart53.removeLast();
                            }
                            heart53.add(Integer.valueOf(heart));
                            lastHeart = heart;
                            needAdd = true;
                        }
                        if (heart != 0 && needAdd) {
                            int level = getHeartLev(maxHeart, heart);
                            if (level != -1) {
                                date51[level] = date51[level] + 1;
                            }
                        }
                        lastTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList2.get(i4)).getHour(), ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute(), ((ProtoBuf_80_data) arrayList2.get(i4)).getSecond());
                        lastEt = (((ProtoBuf_80_data) arrayList2.get(i4)).getHour() * 60) + ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute();
                        if (state == 3 && !pause) {
                            pauseUt = lastTime;
                            KLog.d("tset61s", "no2855pauseUt: " + pauseUt + " - " + sportType);
                            pause = true;
                        }
                        if (!(!pause || state == 3 || state == 0)) {
                            pauseTime += (int) (lastTime - pauseUt);
                            pause = false;
                            KLog.d("no2855-->pauseTime: " + pauseTime + " - " + sportType);
                        }
                        if (state == 2) {
                            isOver = true;
                            if (sportType == 0) {
                                sportType = ((ProtoBuf_80_data) arrayList2.get(i4)).getType();
                            }
                            endUTime = lastTime;
                            int edTime2 = (((ProtoBuf_80_data) arrayList2.get(i4)).getHour() * 60) + ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute();
                            int df2 = ((int) (lastTime - startUTime)) - pauseTime;
                            if (df2 % 60 == 0) {
                                activity = df2 / 60;
                            } else {
                                activity = (df2 / 60) + 1;
                            }
                            KLog.d("no2855--> 结束标志: " + sportType + " - " + endUTime + " -startUTime: " + startUTime + " pauseTime: " + pauseTime + " activity: " + activity);
                            ArrayList arrayList7 = arrayList3;
                            arrayList7.add(DataUtil.getTbSport(uid, sportType, year, month, day, stTime, startUTime, edTime2, endUTime, calorie2, JsonUtils.toJson(getDetail(activity, step2, distance2)), dataFrom, automatic2));
                            DataUtil.saveBlueToGpsSport(uid, sportType, startUTime, lastTime, calorie2, step2, distance2, dataFrom, automatic2, pauseTime, 0, "1");
                            saveTb51Heart(uid, age, year, month, day, startUTime, endUTime, getDetail51(date51), dataFrom, automatic2, JsonUtils.toJson(heart53), sportType);
                            sportType = 0;
                            calorie2 = 0.0f;
                            distance2 = 0.0f;
                            pauseTime = 0;
                            automatic2 = 0;
                        }
                    } else if (state == 1) {
                        KLog.d("no2855-->开始一段运动: " + ((ProtoBuf_80_data) arrayList2.get(i4)).getAvg_bpm() + " = " + ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute() + " seq: " + ((ProtoBuf_80_data) arrayList2.get(i4)).getSeq());
                        pauseTime = 0;
                        automatic2 = mautomatic;
                        heart53.clear();
                        isOver = false;
                        for (int z = 0; z < 6; z++) {
                            date51[z] = 0;
                        }
                        int heart2 = ((ProtoBuf_80_data) arrayList2.get(i4)).getAvg_bpm();
                        if (heart2 < 30 || (heart2 > maxHeart && maxHeart != 0)) {
                            heart2 = 0;
                        }
                        lastHeartTime = "";
                        String nowHeartTime2 = ((ProtoBuf_80_data) arrayList2.get(i4)).getHour() + "/" + ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute();
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
                        calorie2 = 0.0f + ((ProtoBuf_80_data) arrayList2.get(i4)).getCalorie();
                        distance2 = 0.0f + ((ProtoBuf_80_data) arrayList2.get(i4)).getDistance();
                        step2 = 0 + ((ProtoBuf_80_data) arrayList2.get(i4)).getStep();
                        sportType = ((ProtoBuf_80_data) arrayList2.get(i4)).getType();
                        stTime = (((ProtoBuf_80_data) arrayList2.get(i4)).getHour() * 60) + ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute();
                        startUTime = Util.date2TimeStamp(year, month, day, ((ProtoBuf_80_data) arrayList2.get(i4)).getHour(), ((ProtoBuf_80_data) arrayList2.get(i4)).getMinute(), ((ProtoBuf_80_data) arrayList2.get(i4)).getSecond());
                        KLog.d("tset61s", "no2855时间开始: " + startUTime + " - " + sportType);
                    }
                    if (i4 == arrayList2.size() - 1 && !isOver) {
                        int df3 = ((int) (lastTime - startUTime)) - pauseTime;
                        if (df3 % 60 == 0) {
                            activity2 = df3 / 60;
                        } else {
                            activity2 = (df3 / 60) + 1;
                        }
                        KLog.d("no2855-->结束异常标志: " + lastTime + " -startUTime: " + startUTime + " pauseTime: " + pauseTime + " activity: " + activity2);
                        if (activity2 > 0) {
                            arrayList3.add(DataUtil.getTbSport(uid, sportType, year, month, day, stTime, startUTime, lastEt, lastTime, calorie2, JsonUtils.toJson(getDetail(activity2 + 0, step2, distance2)), dataFrom, automatic2));
                            DataUtil.saveBlueToGpsSport(uid, sportType, startUTime, lastTime, calorie2, step2, distance2, dataFrom, automatic2, pauseTime, 0, "1");
                            saveTb51Heart(uid, age, year, month, day, startUTime, endUTime, getDetail51(date51), dataFrom, automatic2, JsonUtils.toJson(heart53), sportType);
                            sportType = 0;
                            pauseTime = 0;
                        }
                    }
                }
            }
        }
        if (arrayList3.size() > 0) {
            long[] times = new long[arrayList3.size()];
            int[] tr1 = new int[arrayList3.size()];
            for (int i5 = 0; i5 < arrayList3.size(); i5++) {
                tr1[i5] = i5;
                times[i5] = ((TB_v3_sport_data) arrayList3.get(i5)).getStart_uxtime();
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
            ArrayList arrayList8 = new ArrayList();
            for (int i7 = arrayList3.size() - 1; i7 >= 0; i7--) {
                arrayList8.add(arrayList3.get(tr1[i7]));
            }
            try {
                ArrayList arrayList9 = new ArrayList();
                int count = arrayList8.size();
                int mStep = 0;
                int mSTime = 0;
                float mDis = 0.0f;
                int mAct = 0;
                double mCals = Utils.DOUBLE_EPSILON;
                int mNum = 0;
                long stUTime = 0;
                int index = 0;
                for (int i8 = 0; i8 < count; i8++) {
                    Detail_data detail_data = (Detail_data) JsonUtils.fromJson(((TB_v3_sport_data) arrayList8.get(i8)).getDetail_data(), Detail_data.class);
                    if (i8 < count - 1) {
                        detail_data1 = (Detail_data) JsonUtils.fromJson(((TB_v3_sport_data) arrayList8.get(i8)).getDetail_data(), Detail_data.class);
                    } else {
                        detail_data1 = new Detail_data();
                    }
                    if (i8 < count - 1 && ((TB_v3_sport_data) arrayList8.get(i8)).getSport_type() == 1) {
                        if (((TB_v3_sport_data) arrayList8.get(i8)).getSport_type() == ((TB_v3_sport_data) arrayList8.get(i8 + 1)).getSport_type()) {
                            if (((TB_v3_sport_data) arrayList8.get(i8)).getStart_time() / 60 == ((TB_v3_sport_data) arrayList8.get(i8 + 1)).getStart_time() / 60 && (detail_data.getActivity() <= 5 || detail_data1.getActivity() <= 5)) {
                                if (((TB_v3_sport_data) arrayList8.get(i8 + 1)).getStart_uxtime() - ((TB_v3_sport_data) arrayList8.get(i8)).getEnd_uxtime() <= 1800) {
                                    mStep += detail_data.getStep();
                                    mDis += detail_data.getDistance();
                                    mAct += detail_data.getActivity();
                                    mCals += ((TB_v3_sport_data) arrayList8.get(i8)).getCalorie();
                                    index += detail_data.getCount();
                                    if (mNum == 0) {
                                        mSTime = ((TB_v3_sport_data) arrayList8.get(i8)).getStart_time();
                                        stUTime = ((TB_v3_sport_data) arrayList8.get(i8)).getStart_uxtime();
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
                    int mETime = ((TB_v3_sport_data) arrayList8.get(i8)).getEnd_time();
                    long edUTime = ((TB_v3_sport_data) arrayList8.get(i8)).getEnd_uxtime();
                    if (mNum == 0) {
                        mSTime = ((TB_v3_sport_data) arrayList8.get(i8)).getStart_time();
                        stUTime = ((TB_v3_sport_data) arrayList8.get(i8)).getStart_uxtime();
                    }
                    double mCals2 = mCals + ((TB_v3_sport_data) arrayList8.get(i8)).getCalorie();
                    detail_data.setStep(mStep2);
                    detail_data.setDistance(mDis2);
                    detail_data.setActivity(mAct2);
                    detail_data.setCount(index);
                    ((TB_v3_sport_data) arrayList8.get(i8)).setDetail_data(JsonUtils.toJson(detail_data));
                    ((TB_v3_sport_data) arrayList8.get(i8)).setStart_time(mSTime);
                    ((TB_v3_sport_data) arrayList8.get(i8)).setStart_uxtime(stUTime);
                    ((TB_v3_sport_data) arrayList8.get(i8)).setEnd_time(mETime);
                    ((TB_v3_sport_data) arrayList8.get(i8)).setCalorie(mCals2);
                    ((TB_v3_sport_data) arrayList8.get(i8)).setEnd_uxtime(edUTime);
                    arrayList9.add(arrayList8.get(i8));
                    mStep = 0;
                    mSTime = 0;
                    mDis = 0.0f;
                    mAct = 0;
                    mCals = Utils.DOUBLE_EPSILON;
                    mNum = 0;
                    stUTime = 0;
                }
                for (int i9 = 0; i9 < arrayList9.size(); i9++) {
                    ((TB_v3_sport_data) arrayList9.get(i9)).saveOrUpdate("uid=? and start_uxtime=? and data_from=? and sport_type=?", uid + "", ((TB_v3_sport_data) arrayList9.get(i9)).getStart_uxtime() + "", dataFrom + "", ((TB_v3_sport_data) arrayList9.get(i9)).getSport_type() + "");
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                KLog.e("解析pb运动异常");
            }
        }
    }

    private static Detail_data getDetail(int activity, int step, float distance) {
        Detail_data d = new Detail_data();
        d.setActivity(activity);
        d.setCount(0);
        d.setStep(step);
        d.setDistance(distance);
        return d;
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

    private static void saveTb51Heart(long uid, int age, int year, int month, int day, long sUTime, long eUTime, String detail, String dataFrom, int automatic, String heart53, int sportType) {
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
        DataUtil.saveHr2File(heart, null, false, false);
    }

    public static String year2DateStr(int year, int month, int day) {
        return year + String.format("%02d", new Object[]{Integer.valueOf(month)}) + String.format("%02d", new Object[]{Integer.valueOf(day)});
    }

    public static int getMaxHeart(int age) {
        return 220 - age;
    }

    public static void saveGpsToBlue(long uid, String dataFrom, int year, int month, int day) {
        DataUtil.writeMtkGps2TB(uid, dataFrom, year, month, day, true);
    }
}
