package com.iwown.device_module.interactive_service;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.heart.HeartData;
import com.iwown.data_link.heart.HeartHoursData;
import com.iwown.data_link.heart.HeartHoursDownCode;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.HeartShowData.SportHeart;
import com.iwown.data_link.heart.HeartStatusData.ContentBean;
import com.iwown.data_link.heart.HeartUploadBean;
import com.iwown.data_link.heart.IHeartService;
import com.iwown.data_link.heart.heart_sport.HeartDownData1;
import com.iwown.data_link.heart.heart_sport.HeartRateDetial;
import com.iwown.data_link.heart.heart_sport.HeartUpSend;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.sql.heart.TB_HeartDataStatus;
import com.iwown.device_module.common.sql.heart.TB_heartrate_data;
import com.iwown.device_module.common.sql.heart.TB_heartrate_r1_data;
import com.iwown.device_module.common.sql.heart.TB_swimrate_data;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_hours;
import com.iwown.device_module.common.sql.heart.TB_v3_heartRate_data_r1_hours;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

@Route(path = "/device/heart_service")
public class HeartModuleService implements IHeartService {
    public void init(Context context) {
    }

    public boolean isExist53SomeDay(long uid, String data_from, DateUtil dateUtil) {
        List<TB_v3_heartRate_data_hours> tb_v3_heartRate_data_hours = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=?", uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", data_from + "").find(TB_v3_heartRate_data_hours.class);
        KLog.e("licl", uid + "/" + dateUtil.getYear() + "/" + dateUtil.getMonth() + "/" + dateUtil.getDay() + "/" + data_from);
        if (tb_v3_heartRate_data_hours == null || tb_v3_heartRate_data_hours.size() == 0) {
            return false;
        }
        return true;
    }

    public boolean isExist53DataByUid(long uid) {
        if (DataSupport.where("uid=? ", uid + "").count(TB_v3_heartRate_data_hours.class) > 0) {
            return true;
        }
        return false;
    }

    public boolean isExist51SomeTimeSegment(long uid, String data_from, long start, long end) {
        List<TB_heartrate_data> heartrate_data = DataSupport.where("uid=? and data_from=? and start_time>=? and end_time<=?", uid + "", data_from + "", (start / 1000) + "", (end / 1000) + "").find(TB_heartrate_data.class);
        KLog.e("licl", JsonTool.toJson(heartrate_data));
        return (heartrate_data == null || heartrate_data.size() == 0) ? false : true;
    }

    private int getLimitValue(int age, double precent) {
        return (int) (((double) (((float) (220 - age)) * 1.0f)) * precent);
    }

    public void getHeartByTime(HeartShowData heartShowData) {
        DateUtil dateUtil = heartShowData.dateUtil;
        List<TB_v3_heartRate_data_hours> tb_v3_heartRate_data_hours = new ArrayList<>();
        if (DateUtil.isSameDay(new Date(), new Date(heartShowData.dateUtil.getTimestamp()))) {
            tb_v3_heartRate_data_hours = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=?", heartShowData.uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", heartShowData.data_from + "").order("hours asc").find(TB_v3_heartRate_data_hours.class);
        } else {
            if (ModuleRouteDeviceInfoService.getInstance().isBind()) {
                tb_v3_heartRate_data_hours = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=?", heartShowData.uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", heartShowData.data_from + "").order("hours asc").find(TB_v3_heartRate_data_hours.class);
            }
            if (tb_v3_heartRate_data_hours.size() == 0) {
                tb_v3_heartRate_data_hours = DataSupport.where("uid=? and year=? and month=? and day=?", heartShowData.uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "").order("hours asc").find(TB_v3_heartRate_data_hours.class);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (TB_v3_heartRate_data_hours data : tb_v3_heartRate_data_hours) {
                    List<TB_v3_heartRate_data_hours> tb_v3_heartRate_data_hours1 = (List) linkedHashMap.get(data.getData_from());
                    if (tb_v3_heartRate_data_hours1 == null) {
                        tb_v3_heartRate_data_hours1 = new ArrayList<>();
                        linkedHashMap.put(data.getData_from(), tb_v3_heartRate_data_hours1);
                    }
                    tb_v3_heartRate_data_hours1.add(data);
                }
                Set<String> strings = linkedHashMap.keySet();
                if (strings.contains(heartShowData.data_from)) {
                    tb_v3_heartRate_data_hours = (List) linkedHashMap.get(heartShowData.data_from);
                } else {
                    int count_sum = 0;
                    int index = 0;
                    for (String key : strings) {
                        if (index == 0) {
                            heartShowData.data_from = key;
                        }
                        List<TB_v3_heartRate_data_hours> tb_v3_heartRate_data_hours2 = (List) linkedHashMap.get(key);
                        for (TB_v3_heartRate_data_hours data2 : tb_v3_heartRate_data_hours2) {
                            int temp_count_sum = 0;
                            Iterator it = JsonTool.getListJson(data2.getDetail_data(), Integer.class).iterator();
                            while (it.hasNext()) {
                                if (((Integer) it.next()).intValue() != 0) {
                                    temp_count_sum++;
                                }
                            }
                            if (temp_count_sum > count_sum) {
                                count_sum = temp_count_sum;
                                tb_v3_heartRate_data_hours = tb_v3_heartRate_data_hours2;
                                heartShowData.data_from = key;
                            }
                        }
                        index++;
                    }
                }
            }
        }
        if (tb_v3_heartRate_data_hours.size() == 0) {
            try {
                getHeartSportDetial(heartShowData, dateUtil);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        } else {
            Integer[] ints = new Integer[1440];
            for (int i = 0; i < ints.length; i++) {
                ints[i] = Integer.valueOf(0);
            }
            List<Integer> temps_values = Arrays.asList(ints);
            for (TB_v3_heartRate_data_hours dataHours : tb_v3_heartRate_data_hours) {
                int start_index = dataHours.getHours() * 60;
                try {
                    ArrayList<Integer> listJson = JsonTool.getListJson(dataHours.getDetail_data(), Integer.class);
                    int i2 = 0;
                    while (i2 < listJson.size() && start_index + i2 < 1440) {
                        temps_values.set(start_index + i2, listJson.get(i2));
                        i2++;
                    }
                } catch (Exception e2) {
                    ThrowableExtension.printStackTrace(e2);
                }
            }
            int pre_max_value = -1;
            int max_value_10 = 0;
            int min_value_10 = 1000;
            boolean is_max = false;
            int cum_count = 1;
            for (int i3 = 0; i3 < temps_values.size(); i3++) {
                if (!(((Integer) temps_values.get(i3)).intValue() == 255 || ((Integer) temps_values.get(i3)).intValue() == 0)) {
                    if (((Integer) temps_values.get(i3)).intValue() > max_value_10) {
                        max_value_10 = ((Integer) temps_values.get(i3)).intValue();
                    }
                    if (((Integer) temps_values.get(i3)).intValue() < min_value_10) {
                        min_value_10 = ((Integer) temps_values.get(i3)).intValue();
                    }
                    if (!is_max && ((Integer) temps_values.get(i3)).intValue() >= pre_max_value) {
                        is_max = true;
                    }
                }
                if (cum_count == 10) {
                    if (is_max) {
                        heartShowData.detail_data.add(Integer.valueOf(max_value_10));
                        pre_max_value = max_value_10;
                    } else {
                        if (min_value_10 == 1000) {
                            min_value_10 = 0;
                        }
                        heartShowData.detail_data.add(Integer.valueOf(min_value_10));
                        pre_max_value = min_value_10;
                    }
                    is_max = false;
                    max_value_10 = 0;
                    min_value_10 = 1000;
                    cum_count = 0;
                }
                cum_count++;
            }
            getHeartSportDetial(heartShowData, dateUtil);
        }
    }

    private void getHeartSportDetial(HeartShowData heartShowData, DateUtil dateUtil) {
        List<TB_heartrate_data> tb_heartrate_datas = DataSupport.where("uid=? and year=? and month=? and day=? and data_from=?", heartShowData.uid + "", dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "", heartShowData.data_from + "").find(TB_heartrate_data.class);
        int age = UserConfig.getInstance().getAge();
        int limit1 = getLimitValue(age, 0.5d);
        int limit2 = getLimitValue(age, 0.6d);
        int limit3 = getLimitValue(age, 0.7d);
        int limit4 = getLimitValue(age, 0.8d);
        int limit5 = getLimitValue(age, 0.9d);
        int limit6 = getLimitValue(age, 1.0d);
        Map<Integer, SportHeart> map = heartShowData.sportHeartMap;
        Integer valueOf = Integer.valueOf(1);
        SportHeart sportHeart = new SportHeart(1, 35, limit1);
        map.put(valueOf, sportHeart);
        Map<Integer, SportHeart> map2 = heartShowData.sportHeartMap;
        Integer valueOf2 = Integer.valueOf(2);
        SportHeart sportHeart2 = new SportHeart(2, limit1, limit2);
        map2.put(valueOf2, sportHeart2);
        Map<Integer, SportHeart> map3 = heartShowData.sportHeartMap;
        Integer valueOf3 = Integer.valueOf(3);
        SportHeart sportHeart3 = new SportHeart(3, limit2, limit3);
        map3.put(valueOf3, sportHeart3);
        Map<Integer, SportHeart> map4 = heartShowData.sportHeartMap;
        Integer valueOf4 = Integer.valueOf(4);
        SportHeart sportHeart4 = new SportHeart(4, limit3, limit4);
        map4.put(valueOf4, sportHeart4);
        Map<Integer, SportHeart> map5 = heartShowData.sportHeartMap;
        Integer valueOf5 = Integer.valueOf(5);
        SportHeart sportHeart5 = new SportHeart(5, limit4, limit5);
        map5.put(valueOf5, sportHeart5);
        Map<Integer, SportHeart> map6 = heartShowData.sportHeartMap;
        Integer valueOf6 = Integer.valueOf(6);
        SportHeart sportHeart6 = new SportHeart(6, limit5, limit6);
        map6.put(valueOf6, sportHeart6);
        heartShowData.y_titles.add(Integer.valueOf(limit1));
        heartShowData.y_titles.add(Integer.valueOf(limit2));
        heartShowData.y_titles.add(Integer.valueOf(limit3));
        heartShowData.y_titles.add(Integer.valueOf(limit4));
        heartShowData.y_titles.add(Integer.valueOf(limit5));
        heartShowData.y_titles.add(Integer.valueOf(limit6));
        tb_heartrate_datas.add(new TB_heartrate_data());
        Class<HeartRateDetial> cls = HeartRateDetial.class;
        for (TB_heartrate_data tb_heartrate_data1 : tb_heartrate_datas) {
            HeartRateDetial heartRateDetial = (HeartRateDetial) JsonUtils.fromJson(tb_heartrate_data1.getDetail_data(), HeartRateDetial.class);
            if (heartRateDetial != null) {
                for (Integer key : heartShowData.sportHeartMap.keySet()) {
                    SportHeart sportHeart7 = (SportHeart) heartShowData.sportHeartMap.get(key);
                    String meth_name = "";
                    try {
                        meth_name = "getR" + (key.intValue() - 1);
                        int value_time = ((Integer) cls.getMethod(meth_name, new Class[0]).invoke(heartRateDetial, new Object[0])).intValue();
                        KLog.e("clazz value_time " + value_time);
                        sportHeart7.activity += value_time;
                    } catch (Exception e) {
                        KLog.e(meth_name + " error " + e.getLocalizedMessage());
                    }
                }
            }
        }
        KLog.e("avg heart " + heartShowData.sportHeartMap + "  " + heartShowData.sportHeartMap.size());
    }

    public void saveNetHoursData(List<HeartHoursDownCode> heartHoursDownCodeList) throws Exception {
        boolean is5_0Data = false;
        for (HeartHoursDownCode downCode : heartHoursDownCodeList) {
            List<Integer> real_datas = new ArrayList<>();
            try {
                Iterator it = downCode.getDetail().iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((Integer) it.next()).intValue() > 500) {
                            is5_0Data = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (is5_0Data) {
                    for (Integer value : downCode.getDetail()) {
                        if (value.intValue() <= 500 || real_datas.size() <= 0) {
                            real_datas.add(value);
                        } else {
                            int temp_value = ((Integer) real_datas.get(real_datas.size() - 1)).intValue();
                            KLog.e(real_datas.size() + " temp_value " + temp_value + "  " + value);
                            for (int i = 0; i < value.intValue() - 500; i++) {
                                real_datas.add(Integer.valueOf(temp_value));
                            }
                        }
                    }
                    KLog.e("50Data new " + real_datas.size());
                    KLog.e("50Data new content " + real_datas);
                } else {
                    real_datas.addAll(downCode.getDetail());
                }
                int hour = 0;
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < real_datas.size(); i2++) {
                    if (i2 == 0) {
                        hour = ((Integer) real_datas.get(i2)).intValue();
                    } else {
                        arrayList.add(real_datas.get(i2));
                        if (arrayList.size() == 60) {
                            DateUtil dateUtil1 = new DateUtil(downCode.getRecord_date(), true);
                            dateUtil1.getZeroTime();
                            long unix_gmt_time = dateUtil1.getZeroTime() + ((long) (hour * 60 * 60));
                            if (DataSupport.where("uid=? and record_date=? and data_from=?", downCode.getUid() + "", unix_gmt_time + "", downCode.getData_from() + "").count(TB_v3_heartRate_data_hours.class) <= 0) {
                                boolean flag = false;
                                TB_v3_heartRate_data_hours data_hours = new TB_v3_heartRate_data_hours();
                                data_hours.setUid(downCode.getUid());
                                data_hours.setData_from(downCode.getData_from());
                                data_hours.setRecord_date(unix_gmt_time);
                                int j = 0;
                                while (true) {
                                    if (j >= arrayList.size()) {
                                        break;
                                    } else if (((Integer) arrayList.get(j)).intValue() != 0) {
                                        flag = true;
                                        break;
                                    } else {
                                        j++;
                                    }
                                }
                                data_hours.setDetail_data(new Gson().toJson((Object) arrayList));
                                DateUtil dateUtil = new DateUtil(downCode.getRecord_date(), true);
                                data_hours.setYear(dateUtil.getYear());
                                data_hours.setMonth(dateUtil.getMonth());
                                data_hours.setDay(dateUtil.getDay());
                                data_hours.setHours(hour);
                                data_hours.set_uploaded(1);
                                if (flag) {
                                    data_hours.save();
                                }
                            }
                            arrayList.clear();
                            hour++;
                        }
                    }
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public List<HeartHoursData> getUnUploadHeartDatas(long newUID, String device) {
        List<TB_v3_heartRate_data_hours> tb_v3_heartRate_data_hours = DataSupport.where("uid=? and _uploaded=0 ", newUID + "").order("hours asc").find(TB_v3_heartRate_data_hours.class);
        Map<String, List<TB_v3_heartRate_data_hours>> masp = new LinkedHashMap<>();
        for (TB_v3_heartRate_data_hours data : tb_v3_heartRate_data_hours) {
            List<TB_v3_heartRate_data_hours> tb3 = (List) masp.get(data.getData_from() + HelpFormatter.DEFAULT_OPT_PREFIX + data.getYear() + data.getMonth() + data.getDay());
            if (tb3 == null) {
                tb3 = new ArrayList<>();
                masp.put(data.getData_from() + HelpFormatter.DEFAULT_OPT_PREFIX + data.getYear() + data.getMonth() + data.getDay(), tb3);
            }
            tb3.add(data);
        }
        Set<String> strings = masp.keySet();
        List<HeartHoursData> heartHoursDataList = new ArrayList<>();
        for (String key : strings) {
            List<TB_v3_heartRate_data_hours> tb_v3_heartRate_data_hours1 = (List) masp.get(key);
            List<TB_v3_heartRate_data_hours> datas = new ArrayList<>();
            if (tb_v3_heartRate_data_hours1 != null && tb_v3_heartRate_data_hours1.size() > 1) {
                int start = ((TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(0)).getHours();
                int end = ((TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(tb_v3_heartRate_data_hours1.size() - 1)).getHours();
                for (int i = start; i <= end; i++) {
                    TB_v3_heartRate_data_hours t2 = new TB_v3_heartRate_data_hours();
                    TB_v3_heartRate_data_hours t1 = (TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(0);
                    t2.setUid(t1.getUid());
                    t2.setHours(t1.getHours());
                    t2.setWeek(t1.getWeek());
                    t2.setData_from(t1.getData_from());
                    t2.setDay(t1.getDay());
                    t2.setMonth(t1.getMonth());
                    t2.setYear(t1.getYear());
                    t2.setRecord_date(t1.getRecord_date());
                    t2.setReserved(t1.getReserved());
                    t2.setTime_stamp(t1.getTime_stamp());
                    t2.set_uploaded(t1.get_uploaded());
                    t2.setDetail_data(JsonUtils.toJson(new int[60]));
                    datas.add(t2);
                }
                for (TB_v3_heartRate_data_hours t : tb_v3_heartRate_data_hours1) {
                    datas.set(t.getHours() - start, t);
                }
            } else if (tb_v3_heartRate_data_hours1.size() == 1) {
                datas.addAll(tb_v3_heartRate_data_hours1);
            }
            KLog.i("---------------------" + datas.size());
            HeartHoursData heartHoursData = new HeartHoursData();
            heartHoursData.setUid(newUID);
            heartHoursData.setData_from(((TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(0)).getData_from());
            heartHoursData.setRecord_date(((TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(0)).getRecord_date());
            DateUtil dateUtil = new DateUtil(((TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(0)).getRecord_date(), true);
            heartHoursData.setDate(dateUtil.getSyyyyMMddDate());
            if (heartHoursData.getDetail() == null) {
                heartHoursData.setDetail(new ArrayList());
            }
            int value_500_tag = 500;
            List<Integer> heartPoint = new ArrayList<>();
            if (datas.size() > 0) {
                for (int i2 = 0; i2 < datas.size(); i2++) {
                    heartPoint.addAll(JsonUtils.getListJson(((TB_v3_heartRate_data_hours) datas.get(i2)).getDetail_data(), Integer.class));
                }
            }
            if (heartPoint.size() > 0) {
                for (int i3 = 0; i3 < heartPoint.size(); i3++) {
                    if (i3 == 0) {
                        KLog.i("------------heartPointhours------------" + ((TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(0)).getHours());
                        heartHoursData.getDetail().add(Integer.valueOf(((TB_v3_heartRate_data_hours) tb_v3_heartRate_data_hours1.get(0)).getHours()));
                    }
                    if (heartHoursData.getDetail().size() > 1) {
                        int pre_value = ((Integer) heartHoursData.getDetail().get(heartHoursData.getDetail().size() - 1)).intValue();
                        if (pre_value > 500) {
                            pre_value = ((Integer) heartHoursData.getDetail().get(heartHoursData.getDetail().size() - 2)).intValue();
                        }
                        if (((Integer) heartPoint.get(i3)).intValue() == pre_value) {
                            value_500_tag++;
                        } else {
                            if (value_500_tag > 500) {
                                heartHoursData.getDetail().add(Integer.valueOf(value_500_tag));
                            }
                            heartHoursData.getDetail().add(heartPoint.get(i3));
                            value_500_tag = 500;
                        }
                    } else {
                        heartHoursData.getDetail().add(heartPoint.get(i3));
                    }
                }
            }
            if (value_500_tag > 500) {
                heartHoursData.getDetail().add(Integer.valueOf(value_500_tag));
            }
            heartHoursDataList.add(heartHoursData);
        }
        return heartHoursDataList;
    }

    public HeartData getHeartDataByTime(long uid, String data_from, long start, long end, int age) {
        long start2 = start / 1000;
        KLog.e("no2855--> " + uid + "/" + data_from + "/" + start2 + "/" + (end / 1000));
        HeartData heartData = new HeartData();
        TB_heartrate_data heart51 = (TB_heartrate_data) DataSupport.where("uid=? and data_from=? and start_time=?", uid + "", data_from + "", start2 + "").findFirst(TB_heartrate_data.class);
        if (heart51 == null || TextUtils.isEmpty(heart51.getReserved())) {
            return null;
        }
        int max_bpm = 0;
        int min_bpm = 1000;
        int avg_sum = 0;
        int avg_num = 0;
        int mAge = age;
        if (heart51.getAge() != 0) {
            mAge = heart51.getAge();
        }
        int maxHeart = getMaxHeart(mAge);
        int[] mins = new int[6];
        List<Integer> heInt = JsonUtils.getListJson(heart51.getReserved(), Integer.class);
        for (Integer integer : heInt) {
            if (integer != null && integer.intValue() > 0 && integer.intValue() < 255) {
                avg_sum += integer.intValue();
                avg_num++;
                max_bpm = Math.max(integer.intValue(), max_bpm);
                min_bpm = Math.min(integer.intValue(), min_bpm);
            }
        }
        int avg_bpm = 0;
        if (avg_num > 0) {
            avg_bpm = avg_sum / avg_num;
        }
        HeartRateDetial heartRateDetial = (HeartRateDetial) JsonTool.fromJson(heart51.getDetail_data(), HeartRateDetial.class);
        mins[0] = mins[0] + heartRateDetial.getR0();
        mins[1] = mins[1] + heartRateDetial.getR1();
        mins[2] = mins[2] + heartRateDetial.getR2();
        mins[3] = mins[3] + heartRateDetial.getR3();
        mins[4] = mins[4] + heartRateDetial.getR4();
        mins[5] = mins[5] + heartRateDetial.getR5();
        heartData.setHeInt(heInt);
        heartData.setMax_bpm(max_bpm);
        if (min_bpm == 1000) {
            min_bpm = 0;
        }
        heartData.setMin_bpm(min_bpm);
        heartData.setAvg(avg_bpm);
        heartData.setTotal51(mins[0] + mins[1] + mins[2] + mins[3] + mins[4] + mins[5]);
        heartData.setMaxHeart(maxHeart);
        heartData.setMins(mins);
        KLog.e("no2855--> 得到手环的heart_data" + JsonTool.toJson(heartData));
        return heartData;
    }

    public HeartData getSwimDataByTime(long uid, String data_from, long start, long end, int age) {
        long start2 = start / 1000;
        KLog.e("no2855--> " + uid + "/" + data_from + "/" + start2 + "/" + (end / 1000));
        HeartData heartData = new HeartData();
        TB_swimrate_data heart51 = (TB_swimrate_data) DataSupport.where("uid=? and data_from=? and start_time=?", uid + "", data_from + "", start2 + "").findFirst(TB_swimrate_data.class);
        if (heart51 == null || TextUtils.isEmpty(heart51.getReserved())) {
            return null;
        }
        int max_bpm = 0;
        int min_bpm = 1000;
        int avg_sum = 0;
        int avg_num = 0;
        int mAge = age;
        if (heart51.getAge() != 0) {
            mAge = heart51.getAge();
        }
        int maxHeart = getMaxHeart(mAge);
        int[] mins = new int[6];
        List<Integer> heInt = JsonUtils.getListJson(heart51.getReserved(), Integer.class);
        for (Integer integer : heInt) {
            if (integer != null && integer.intValue() > 0 && integer.intValue() < 255) {
                avg_sum += integer.intValue();
                avg_num++;
                max_bpm = Math.max(integer.intValue(), max_bpm);
                min_bpm = Math.min(integer.intValue(), min_bpm);
            }
        }
        int avg_bpm = 0;
        if (avg_num > 0) {
            avg_bpm = avg_sum / avg_num;
        }
        HeartRateDetial heartRateDetial = (HeartRateDetial) JsonTool.fromJson(heart51.getDetail_data(), HeartRateDetial.class);
        mins[0] = mins[0] + heartRateDetial.getR0();
        mins[1] = mins[1] + heartRateDetial.getR1();
        mins[2] = mins[2] + heartRateDetial.getR2();
        mins[3] = mins[3] + heartRateDetial.getR3();
        mins[4] = mins[4] + heartRateDetial.getR4();
        mins[5] = mins[5] + heartRateDetial.getR5();
        heartData.setHeInt(heInt);
        heartData.setMax_bpm(max_bpm);
        if (min_bpm == 1000) {
            min_bpm = 0;
        }
        heartData.setMin_bpm(min_bpm);
        heartData.setAvg(avg_bpm);
        heartData.setTotal51(mins[0] + mins[1] + mins[2] + mins[3] + mins[4] + mins[5]);
        heartData.setMaxHeart(maxHeart);
        heartData.setMins(mins);
        KLog.e("no2855--> 得到手环的heart_data" + JsonTool.toJson(heartData));
        return heartData;
    }

    public HeartData getHeartOldDataByTime(long uid, String data_from, long start, long end, int age) {
        long start2 = start / 1000;
        long end2 = end / 1000;
        KLog.e("licl", uid + "/" + data_from + "/" + start2 + "/" + end2);
        HeartData heartData = new HeartData();
        List<Integer> heInt = getHeartHourByTime(uid, data_from, start2, end2);
        KLog.e("licl", JsonTool.toJson(heInt));
        if (heInt == null || heInt.size() == 0) {
            return null;
        }
        int max_bpm = 0;
        int min_bpm = 1000;
        int avg_sum = 0;
        int avg_num = 0;
        int maxHeart = getMaxHeart(age);
        int[] mins = new int[6];
        for (Integer integer : heInt) {
            if (!(integer.intValue() == 0 || integer.intValue() == 255)) {
                avg_sum += integer.intValue();
                avg_num++;
                max_bpm = Math.max(integer.intValue(), max_bpm);
                min_bpm = Math.min(integer.intValue(), min_bpm);
            }
            if (integer.intValue() > 35 && integer.intValue() < 255) {
                int lev = getHeartLev(maxHeart, integer.intValue());
                mins[lev] = mins[lev] + 1;
            }
        }
        int avg_bpm = 0;
        if (avg_num > 0) {
            avg_bpm = avg_sum / avg_num;
        }
        List<TB_heartrate_data> heart51 = DataSupport.where("uid=? and data_from=? and start_time>=? and end_time<=?", uid + "", data_from + "", start2 + "", end2 + "").find(TB_heartrate_data.class);
        if (!(heart51 == null || heart51.size() == 0)) {
            for (int i = 0; i < 6; i++) {
                mins[i] = 0;
            }
            for (TB_heartrate_data heartrate_data : heart51) {
                HeartRateDetial heartRateDetial = (HeartRateDetial) JsonTool.fromJson(heartrate_data.getDetail_data(), HeartRateDetial.class);
                mins[0] = mins[0] + heartRateDetial.getR0();
                mins[1] = mins[1] + heartRateDetial.getR1();
                mins[2] = mins[2] + heartRateDetial.getR2();
                mins[3] = mins[3] + heartRateDetial.getR3();
                mins[4] = mins[4] + heartRateDetial.getR4();
                mins[5] = mins[5] + heartRateDetial.getR5();
            }
        }
        heartData.setHeInt(heInt);
        heartData.setMax_bpm(max_bpm);
        if (min_bpm == 1000) {
            min_bpm = 0;
        }
        heartData.setMin_bpm(min_bpm);
        heartData.setAvg(avg_bpm);
        heartData.setTotal51(mins[0] + mins[1] + mins[2] + mins[3] + mins[4] + mins[5]);
        heartData.setMaxHeart(maxHeart);
        heartData.setMins(mins);
        KLog.e("licl", "得到手环的heart_data" + JsonTool.toJson(heartData));
        return heartData;
    }

    public HeartData getHeartDataByR1Time(long uid, String data_from, long start, long end, int age, List<Integer> heInt1) {
        long start2 = start / 1000;
        long end2 = end / 1000;
        KLog.e("licl", uid + "/" + data_from + "/" + start2 + "/" + end2);
        HeartData heartData = new HeartData();
        if (heInt1 == null || heInt1.size() == 0) {
            return null;
        }
        int max_bpm = 0;
        int min_bpm = 1000;
        int avg_sum = 0;
        int avg_num = 0;
        int maxHeart = getMaxHeart(age);
        int[] mins = new int[6];
        for (Integer integer : heInt1) {
            if (!(integer.intValue() == 0 || integer.intValue() == 255)) {
                avg_sum += integer.intValue();
                avg_num++;
                max_bpm = Math.max(integer.intValue(), max_bpm);
                min_bpm = Math.min(integer.intValue(), min_bpm);
            }
        }
        int avg_bpm = 0;
        if (avg_num > 0) {
            avg_bpm = avg_sum / avg_num;
        }
        List<TB_heartrate_r1_data> heart51 = DataSupport.where("uid=? and data_from=? and start_time>=?  and end_time<=? ", uid + "", data_from + "", start2 + "", end2 + "").find(TB_heartrate_r1_data.class);
        if (!(heart51 == null || heart51.size() == 0)) {
            for (TB_heartrate_r1_data heartrate_r1_data : heart51) {
                HeartRateDetial heartRateDetial = (HeartRateDetial) JsonTool.fromJson(heartrate_r1_data.getDetail_data(), HeartRateDetial.class);
                mins[0] = mins[0] + heartRateDetial.getR0();
                mins[1] = mins[1] + heartRateDetial.getR1();
                mins[2] = mins[2] + heartRateDetial.getR2();
                mins[3] = mins[3] + heartRateDetial.getR3();
                mins[4] = mins[4] + heartRateDetial.getR4();
                mins[5] = mins[5] + heartRateDetial.getR5();
            }
        }
        heartData.setHeInt(heInt1);
        heartData.setMax_bpm(max_bpm);
        if (min_bpm == 1000) {
            min_bpm = 0;
        }
        heartData.setMin_bpm(min_bpm);
        heartData.setAvg(avg_bpm);
        heartData.setTotal51(mins[0] + mins[1] + mins[2] + mins[3] + mins[4] + mins[5]);
        heartData.setMaxHeart(maxHeart);
        heartData.setMins(mins);
        KLog.e("licl", "得到手环的heart_data" + JsonTool.toJson(heartData));
        return heartData;
    }

    public void saveHeartStatus(List<ContentBean> content) {
        for (ContentBean contentBean : content) {
            DateUtil today = new DateUtil((long) contentBean.getUnix_time(), true);
            if (((TB_HeartDataStatus) DataSupport.where("date=? and uid=? and data_from=?", today.getY_M_D() + "", contentBean.getUid() + "", contentBean.getData_from() + "").findFirst(TB_HeartDataStatus.class)) == null) {
                TB_HeartDataStatus data = new TB_HeartDataStatus();
                data.setUid(contentBean.getUid());
                data.setData_from(contentBean.getData_from());
                data.setUnix_time((long) contentBean.getUnix_time());
                data.setDate(today.getY_M_D());
                data.save();
            }
        }
    }

    public Map<String, ContentBean> getStatusDatas(long newUID, String device, DateUtil dateUtil) {
        List<TB_v3_heartRate_data_hours> tb_v3_heartRate_data_hours;
        new ArrayList();
        if (ModuleRouteDeviceInfoService.getInstance().isBind()) {
            tb_v3_heartRate_data_hours = DataSupport.where("uid=? and year=? and month=?", newUID + "", dateUtil.getYear() + "", dateUtil.getMonth() + "").order("day desc").find(TB_v3_heartRate_data_hours.class);
        } else {
            tb_v3_heartRate_data_hours = DataSupport.where("uid=? and year=? and month=? ", newUID + "", dateUtil.getYear() + "", dateUtil.getMonth() + "").order("day desc").find(TB_v3_heartRate_data_hours.class);
        }
        Map<String, ContentBean> contentBeans = new LinkedHashMap<>();
        for (TB_v3_heartRate_data_hours data_hours : tb_v3_heartRate_data_hours) {
            DateUtil dateUtil1 = new DateUtil(data_hours.getYear(), data_hours.getMonth(), data_hours.getDay());
            ContentBean contentBean = (ContentBean) contentBeans.get(dateUtil1.getY_M_D());
            boolean temp_value_ok = false;
            try {
                Iterator it = JsonUtils.getListJson(data_hours.getDetail_data(), Integer.class).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Integer value = (Integer) it.next();
                    if (value.intValue() > 0 && value.intValue() < 255) {
                        temp_value_ok = true;
                        break;
                    }
                }
                if (temp_value_ok && contentBean == null) {
                    ContentBean contentBean2 = new ContentBean();
                    contentBean2.setUid(data_hours.getUid());
                    contentBean2.setUnix_time((int) dateUtil1.getUnixTimestamp());
                    contentBean2.setData_from(data_hours.getData_from());
                    contentBeans.put(dateUtil1.getY_M_D(), contentBean2);
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        KLog.e("step 1 " + contentBeans);
        return contentBeans;
    }

    public void clearHours() {
        DataSupport.deleteAll(TB_v3_heartRate_data_hours.class, new String[0]);
    }

    public void updateDataUploads(Map<String, HeartUploadBean> heartUploadBeanMap) {
        for (String key : heartUploadBeanMap.keySet()) {
            HeartUploadBean heartUploadBean = (HeartUploadBean) heartUploadBeanMap.get(key);
            TB_v3_heartRate_data_hours tb_v3_heartRate_data_hours = new TB_v3_heartRate_data_hours();
            tb_v3_heartRate_data_hours.set_uploaded(1);
            tb_v3_heartRate_data_hours.updateAll("uid=? and data_from=? and year=? and month=? and day=?", heartUploadBean.uid + "", heartUploadBean.data_from + "", heartUploadBean.year + "", heartUploadBean.month + "", heartUploadBean.day + "");
        }
    }

    public HeartUpSend getUnUploadHeartSportsDatas(long newUID) {
        List<TB_heartrate_data> tb_heartrate_data = DataSupport.where("uid=? and _uploaded=?", newUID + "", "0").find(TB_heartrate_data.class);
        List<HeartDownData1> heartDownList = new ArrayList<>();
        for (TB_heartrate_data heartrate_data : tb_heartrate_data) {
            HeartDownData1 downData1 = new HeartDownData1();
            downData1.setUid(newUID);
            downData1.setStart_time(heartrate_data.getStart_time());
            downData1.setEnd_time(heartrate_data.getEnd_time());
            downData1.setData_from(heartrate_data.getData_from());
            downData1.setDetail((HeartRateDetial) new Gson().fromJson(heartrate_data.getDetail_data(), HeartRateDetial.class));
            heartDownList.add(downData1);
        }
        HeartUpSend heartUpSend = new HeartUpSend();
        heartUpSend.setUid(newUID);
        heartUpSend.setContent(heartDownList);
        return heartUpSend;
    }

    public void updateUnUpload1HeartSportDatas(long uid) {
        TB_heartrate_data data = new TB_heartrate_data();
        data.set_uploaded(1);
        data.updateAll("uid=? and _uploaded=0", uid + "");
    }

    public void saveHeartSports51(List<HeartDownData1> temp_dates) {
        for (HeartDownData1 heartDownData1 : temp_dates) {
            DateUtil dateUtil = new DateUtil(heartDownData1.getEnd_time(), true);
            String detail = JsonUtils.toJson(heartDownData1.getDetail());
            if (!DataSupport.isExist(TB_heartrate_data.class, "uid=? and data_from=? and start_time=?", heartDownData1.getUid() + "", heartDownData1.getData_from() + "", heartDownData1.getStart_time() + "")) {
                TB_heartrate_data tb_heartrate_data = new TB_heartrate_data();
                tb_heartrate_data.setData_from(heartDownData1.getData_from());
                tb_heartrate_data.setDay(dateUtil.getDay());
                tb_heartrate_data.setMonth(dateUtil.getMonth());
                tb_heartrate_data.setYear(dateUtil.getYear());
                tb_heartrate_data.setDetail_data(detail);
                tb_heartrate_data.setUid(heartDownData1.getUid());
                tb_heartrate_data.set_uploaded(1);
                tb_heartrate_data.setStart_time(heartDownData1.getStart_time());
                tb_heartrate_data.setEnd_time(heartDownData1.getEnd_time());
                tb_heartrate_data.save();
            }
        }
    }

    public static int getMaxHeart(int age) {
        return 220 - age;
    }

    private int getHeartLev(int maxHeart, int heart) {
        if (((double) heart) <= ((double) maxHeart) * 0.5d) {
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
        return 5;
    }

    public List<Integer> getHeartHourByTime(long uid, String dataFrom, long start, long end) {
        int start_record = ((int) ((start / 60) / 60)) * 3600;
        int end_record = ((int) ((end / 60) / 60)) * 3600;
        int start_min = (int) ((start / 60) % 60);
        int end_min = (int) ((end / 60) % 60);
        List<Integer> integers = new ArrayList<>();
        KLog.e("getHeartHourByTime", "sm: " + start_min);
        KLog.e("getHeartHourByTime", "em: " + end_min);
        KLog.e("getHeartHourByTime", "getHeartHourByTime" + start_record + "/" + end_record);
        List<TB_v3_heartRate_data_hours> list = DataSupport.where("uid=? and record_date>=? and record_date<=? and data_from=?", uid + "", start_record + "", end_record + "", dataFrom + "").order("record_date").find(TB_v3_heartRate_data_hours.class);
        KLog.e(JsonTool.toJson(list));
        if (!(list == null || list.size() == 0)) {
            for (int i = 0; i < list.size(); i++) {
                ArrayList<Integer> hr_53_details = JsonTool.getListJson(((TB_v3_heartRate_data_hours) list.get(i)).getDetail_data(), Integer.class);
                if (i == 0) {
                    if (list.size() > 1) {
                        for (int i1 = start_min; i1 < 60; i1++) {
                            integers.add(hr_53_details.get(i1));
                        }
                    } else {
                        for (int i12 = start_min; i12 <= end_min; i12++) {
                            integers.add(hr_53_details.get(i12));
                        }
                    }
                } else if (i == list.size() - 1) {
                    for (int i13 = 0; i13 < end_min; i13++) {
                        integers.add(hr_53_details.get(i13));
                    }
                } else {
                    integers.addAll(hr_53_details);
                }
            }
        }
        return integers;
    }

    public List<Integer> getHeartHourByR1Time(long uid, String dataFrom, long start, long end) {
        int start_record = ((int) ((start / 60) / 60)) * 3600;
        int end_record = ((int) ((end / 60) / 60)) * 3600;
        int start_min = (int) ((start / 60) % 60);
        int end_min = (int) ((end / 60) % 60);
        List<Integer> integers = new ArrayList<>();
        KLog.e("getHeartHourByTime", "sm: " + start_min);
        KLog.e("getHeartHourByTime", "em: " + end_min);
        KLog.e("getHeartHourByTime", "getHeartHourByTime" + start_record + "/" + end_record);
        List<TB_v3_heartRate_data_r1_hours> list = DataSupport.where("uid=? and record_date>=? and record_date<=? and data_from=?", uid + "", start_record + "", end_record + "", dataFrom + "").order("record_date").find(TB_v3_heartRate_data_r1_hours.class);
        KLog.e(JsonTool.toJson(list));
        if (!(list == null || list.size() == 0)) {
            for (int i = 0; i < list.size(); i++) {
                ArrayList<Integer> hr_53_details = JsonTool.getListJson(((TB_v3_heartRate_data_r1_hours) list.get(i)).getDetail_data(), Integer.class);
                if (i == 0) {
                    if (list.size() > 1) {
                        for (int i1 = start_min; i1 < 60; i1++) {
                            integers.add(hr_53_details.get(i1));
                        }
                    } else {
                        for (int i12 = start_min; i12 <= end_min; i12++) {
                            integers.add(hr_53_details.get(i12));
                        }
                    }
                } else if (i == list.size() - 1) {
                    for (int i13 = 0; i13 < end_min; i13++) {
                        integers.add(hr_53_details.get(i13));
                    }
                } else {
                    integers.addAll(hr_53_details);
                }
            }
        }
        return integers;
    }
}
