package com.iwown.device_module.common.Bluetooth.receiver.mtk.utils;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.utils.Util;
import com.iwown.data_link.sport_data.Detail_data;
import com.iwown.data_link.sport_data.ModuleRouteSportService;
import com.iwown.data_link.sport_data.R1DataBean;
import com.iwown.data_link.sport_data.R1_68_data;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.HeartRateDetial;
import com.iwown.device_module.common.sql.heart.TB_heartrate_r1_data;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_r1_hours;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class R1ConvertHeartHandler {
    public static void to51(R1DataBean r1DataBean) {
        if (r1DataBean.getTag().equals("R1TableConvert")) {
            sportAnd51HeartDataToIv(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice(), ModuleRouteSportService.getInstance().get68Data(0, 0, 0, UserConfig.getInstance().getDevice(), UserConfig.getInstance().getNewUID()));
        }
    }

    public static void mtk68DataToHeart(long uid, int year, int month, int day, String dataFrom, List<R1_68_data> datas) {
        int mSize = 0;
        if (datas != null) {
            mSize = datas.size();
        }
        if (mSize > 0) {
            List<Integer> heart53 = new ArrayList<>();
            int count53 = 0;
            for (int i = 0; i < mSize; i++) {
                if (i < mSize - 1) {
                    if (((R1_68_data) datas.get(i)).getHour() != ((R1_68_data) datas.get(i + 1)).getHour()) {
                        for (int j = count53; j < 60; j++) {
                            heart53.add(Integer.valueOf(((R1_68_data) datas.get(i + 1)).getAvg_hr()));
                        }
                        saveTb53Heart(uid, year, month, day, ((R1_68_data) datas.get(i)).getHour(), heart53, dataFrom);
                        heart53 = new ArrayList<>();
                        count53 = 0;
                    } else {
                        int j2 = count53;
                        while (j2 <= ((R1_68_data) datas.get(i)).getMin() && j2 < 60) {
                            int heart = ((R1_68_data) datas.get(i)).getAvg_hr();
                            if (j2 == ((R1_68_data) datas.get(i)).getMin()) {
                                heart53.add(Integer.valueOf(heart));
                            } else {
                                heart53.add(Integer.valueOf(0));
                            }
                            j2++;
                        }
                        count53 = ((R1_68_data) datas.get(i)).getMin() + 1;
                    }
                } else {
                    int j3 = count53;
                    while (j3 <= ((R1_68_data) datas.get(i)).getMin() && j3 < 60) {
                        int heart2 = ((R1_68_data) datas.get(i)).getAvg_hr();
                        if (j3 == ((R1_68_data) datas.get(i)).getMin()) {
                            heart53.add(Integer.valueOf(heart2));
                        } else {
                            heart53.add(Integer.valueOf(0));
                        }
                        j3++;
                    }
                    saveTb53Heart(uid, year, month, day, ((R1_68_data) datas.get(i)).getHour(), heart53, dataFrom);
                }
            }
        }
    }

    public static void sportAnd51HeartDataToIv(long uid, String dataFrom, List<R1_68_data> r1_68_data) {
        long startUTime = 0;
        int stTime = 0;
        int edTime = 0;
        if (r1_68_data != null) {
            int sportType = 0;
            boolean pause = false;
            int pauseTime = 0;
            long pauseUt = 0;
            int[] he51 = new int[6];
            float[] ca51 = new float[6];
            int[] date51 = new int[6];
            int maxHeart = getMaxHeart(ModuleRouteUserInfoService.getInstance().getUserInfo(ContextUtil.app).age);
            if (r1_68_data.size() > 0) {
                int i = 0;
                while (true) {
                    int edTime2 = edTime;
                    if (i < r1_68_data.size()) {
                        if (((R1_68_data) r1_68_data.get(i)).getState_type() == 1) {
                            pauseTime = 0;
                            for (int z = 0; z < 6; z++) {
                                he51[z] = 0;
                                ca51[z] = 0.0f;
                                date51[z] = 0;
                            }
                            int heart = ((R1_68_data) r1_68_data.get(i)).getAvg_hr();
                            if (heart != 0) {
                                int level = getHeartLev(maxHeart, heart);
                                if (level != -1) {
                                    he51[level] = ((R1_68_data) r1_68_data.get(i)).getAvg_hr();
                                    ca51[level] = ((R1_68_data) r1_68_data.get(i)).getCalorie();
                                    date51[level] = 1;
                                }
                            }
                            sportType = ((R1_68_data) r1_68_data.get(i)).getSport_type();
                            stTime = (((R1_68_data) r1_68_data.get(i)).getHour() * 60) + ((R1_68_data) r1_68_data.get(i)).getMin();
                            startUTime = Util.date2TimeStamp(((R1_68_data) r1_68_data.get(i)).getYear(), ((R1_68_data) r1_68_data.get(i)).getMonth(), ((R1_68_data) r1_68_data.get(i)).getDay(), ((R1_68_data) r1_68_data.get(i)).getHour(), ((R1_68_data) r1_68_data.get(i)).getMin(), ((R1_68_data) r1_68_data.get(i)).getSeconds());
                            KLog.d("tset61s", "时间开始: " + startUTime + " - " + sportType);
                        }
                        int heart2 = ((R1_68_data) r1_68_data.get(i)).getAvg_hr();
                        if (heart2 != 0) {
                            int level2 = getHeartLev(maxHeart, heart2);
                            if (level2 != -1) {
                                he51[level2] = ((R1_68_data) r1_68_data.get(i)).getAvg_hr() + he51[level2];
                                ca51[level2] = ((R1_68_data) r1_68_data.get(i)).getCalorie() + ca51[level2];
                                date51[level2] = date51[level2] + 1;
                            }
                        }
                        long lastTime = Util.date2TimeStamp(((R1_68_data) r1_68_data.get(i)).getYear(), ((R1_68_data) r1_68_data.get(i)).getMonth(), ((R1_68_data) r1_68_data.get(i)).getDay(), ((R1_68_data) r1_68_data.get(i)).getHour(), ((R1_68_data) r1_68_data.get(i)).getMin(), ((R1_68_data) r1_68_data.get(i)).getSeconds());
                        int lastEt = (((R1_68_data) r1_68_data.get(i)).getHour() * 60) + ((R1_68_data) r1_68_data.get(i)).getMin();
                        if (((R1_68_data) r1_68_data.get(i)).getState_type() == 3 && !pause) {
                            pauseUt = lastTime;
                            KLog.d("tset61s", "pauseUt: " + pauseUt + " - " + sportType);
                            pause = true;
                        }
                        if (!(!pause || ((R1_68_data) r1_68_data.get(i)).getState_type() == 3 || ((R1_68_data) r1_68_data.get(i)).getState_type() == 0)) {
                            pauseTime += (int) (lastTime - pauseUt);
                            pause = false;
                            KLog.d("tset61s", "pauseTime: " + pauseTime + " - " + sportType);
                        }
                        if (((R1_68_data) r1_68_data.get(i)).getState_type() == 2) {
                            edTime = (((R1_68_data) r1_68_data.get(i)).getHour() * 60) + ((R1_68_data) r1_68_data.get(i)).getMin();
                            int i2 = ((int) (lastTime - startUTime)) - pauseTime;
                            long j = uid;
                            saveTb51Heart(j, ((R1_68_data) r1_68_data.get(i)).getYear(), ((R1_68_data) r1_68_data.get(i)).getMonth(), ((R1_68_data) r1_68_data.get(i)).getDay(), startUTime, lastTime, getDetail51(he51, ca51, date51, stTime, edTime, sportType), dataFrom);
                        } else {
                            edTime = edTime2;
                        }
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public static int getMaxHeart(int age) {
        return 220 - age;
    }

    private static Detail_data getDetail(int activity, int step, float distance) {
        Detail_data d = new Detail_data();
        d.setActivity(activity);
        d.setCount(0);
        d.setStep(step);
        d.setDistance(distance);
        return d;
    }

    private static String getDetail51(int[] he51, float[] ca51, int[] time, int stTime, int edTime, int sportType) {
        HeartRateDetial detial = new HeartRateDetial();
        detial.setR0(time[0]);
        detial.setR1(time[1]);
        detial.setR2(time[2]);
        detial.setR3(time[3]);
        detial.setR4(time[4]);
        detial.setR5(time[5]);
        KLog.e("=============================" + JsonTool.toJson(detial));
        return JsonTool.toJson(detial);
    }

    private static void saveTb51Heart(long uid, int year, int month, int day, long sUTime, long eUTime, String detail, String dataFrom) {
        TB_heartrate_r1_data heart = new TB_heartrate_r1_data();
        heart.setUid(uid);
        heart.setYear(year);
        heart.setMonth(month);
        heart.setDay(day);
        heart.setStart_time(sUTime);
        heart.setEnd_time(eUTime);
        heart.setDetail_data(detail);
        heart.setData_from(dataFrom);
        heart.saveOrUpdate("uid=? and start_time=? and data_from=?", uid + "", sUTime + "", dataFrom + "");
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

    public static boolean isP1Version2() {
        if (WristbandModel.MODEL_P1_V1.equals(DeviceUtils.getDeviceInfo().getModel())) {
            return false;
        }
        return true;
    }

    public static void saveTb53Heart(long uid, int year, int month, int day, int hour, List<Integer> data, String dataFrom) {
        if (data.size() < 60) {
            for (int i = data.size(); i < 60; i++) {
                data.add(Integer.valueOf(0));
            }
        } else if (data.size() > 60) {
            for (int i2 = data.size() - 1; i2 >= 59; i2--) {
                data.remove(i2);
            }
        }
        TB_v3_heartRate_data_r1_hours dataHour = new TB_v3_heartRate_data_r1_hours();
        dataHour.setUid(uid);
        dataHour.setYear(year);
        dataHour.setMonth(month);
        dataHour.setDay(day);
        dataHour.setHours(hour);
        dataHour.set_uploaded(0);
        dataHour.setRecord_date(Util.date2TimeStamp(year, month, day, hour, 0));
        dataHour.setData_from(dataFrom);
        dataHour.setDetail_data(JsonUtils.toJson(data));
        dataHour.saveOrUpdate("uid=? and record_date=? and data_from=?", uid + "", dataHour.getRecord_date() + "", dataFrom + "");
    }
}
