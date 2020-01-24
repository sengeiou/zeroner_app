package com.iwown.sport_module.contract;

import com.iwown.data_link.heart.HeartData;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.sport_module.gps.data.GpsDetailItem;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.pojo.R1DataBean;
import com.iwown.sport_module.view.run.DlineDataBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class R1DataPresenter {
    private DecimalFormat decimal0Format = new DecimalFormat("0", new DecimalFormatSymbols(Locale.US));
    private DecimalFormat decimal1Format = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
    private DecimalFormat decimal2Format = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    private TB_location_history history;
    private GpsDetailItem item;
    private R1DataImpl r1Data;

    public interface R1DataImpl {
        void showHrData(HeartData heartData);

        void showR1Data(R1DataBean r1DataBean);
    }

    public R1DataPresenter(GpsDetailItem item2, R1DataImpl r1Data2) {
        this.r1Data = r1Data2;
        this.item = item2;
    }

    public R1DataPresenter() {
    }

    public void setR1Data(int mYear, int mMonth, int mDay, String data_from, long startTime, long endTime) {
        R1DataBean r1DataBean = initHistoryData();
        if (r1DataBean != null) {
            setHrData(data_from, startTime, endTime, r1DataBean.getHrLists());
        }
    }

    private void setHrData(String data_from, long startTime, long endTime, List<Integer> hrList) {
        if (data_from == null || data_from.equals("")) {
            this.r1Data.showHrData(null);
            return;
        }
        this.r1Data.showHrData(ModuleRouteHeartService.getInstance().getHeartDataR1ByTime(UserConfig.getInstance().getNewUID(), data_from, startTime, endTime, UserConfig.getInstance().getAge(), hrList));
    }

    public R1DataBean initHistoryData(TB_location_history history2) {
        R1DataBean r1DataBean = new R1DataBean();
        if (history2 != null) {
            try {
                DlineDataBean bean = new DlineDataBean(System.currentTimeMillis(), 0.0f);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                List<DlineDataBean> flight_avgs = new ArrayList<>();
                ArrayList arrayList3 = new ArrayList();
                new ArrayList();
                arrayList.add(bean);
                arrayList2.add(bean);
                flight_avgs.add(bean);
                arrayList3.add(bean);
                ArrayList listJson = JsonTool.getListJson(history2.getRateOfStride_avg(), DlineDataBean.class);
                ArrayList listJson2 = JsonTool.getListJson(history2.getTouchDown_avg(), DlineDataBean.class);
                List<DlineDataBean> flight = JsonTool.getListJson(history2.getFlight_avg(), DlineDataBean.class);
                ArrayList listJson3 = JsonTool.getListJson(history2.getSpeedList(), DlineDataBean.class);
                List<Integer> avgHr_avgs = JsonTool.getListJson(history2.getAvg_hr(), Integer.class);
                arrayList.addAll(listJson);
                arrayList2.addAll(listJson2);
                flight_avgs.addAll(flight);
                arrayList3.addAll(listJson3);
                float rateOfStride_avg = addAvgData(arrayList);
                float touchDown_avg = addAvgData(arrayList2);
                float flight_avg = addAvgData(flight_avgs);
                List<DlineDataBean> list = flightTimeToVerticalLists(flight_avgs);
                float touchDow_balance_avg = ((float) history2.getTouchDownPower_balance()) / 10.0f;
                float avg_distance = addAvgData(arrayList3);
                int maxRate = (int) maxValue(arrayList);
                int maxEarth_time = (int) maxValue(arrayList2);
                int maxFlight_avg = (int) maxValue(flight_avgs);
                float min_speed = minValue(arrayList3);
                float max_speed = maxValue(arrayList3);
                int maxHr_avg = (int) maxIntValue(avgHr_avgs);
                int minHr_avg = (int) minIntValue(avgHr_avgs);
                r1DataBean.setRate_avg(this.decimal0Format.format((double) rateOfStride_avg));
                r1DataBean.setEarth_time_avg(String.valueOf(this.decimal0Format.format((double) touchDown_avg)));
                r1DataBean.setSky_time_avg(String.valueOf(this.decimal0Format.format((double) flight_avg)));
                r1DataBean.setMaxRate(maxRate);
                r1DataBean.setMax_earth_time(maxEarth_time);
                r1DataBean.setMax_vertical((int) flyTimeToVertical((float) maxFlight_avg));
                r1DataBean.setVertical_avg(this.decimal1Format.format((double) flyTimeToVertical(flight_avg)));
                r1DataBean.setEarth_balance(this.decimal1Format.format((double) touchDow_balance_avg) + "% - " + this.decimal1Format.format((double) Math.abs(100.0f - touchDow_balance_avg)) + "%");
                r1DataBean.setSpeed_min(min_speed);
                if (arrayList3.size() > 0) {
                    r1DataBean.setSpeed_avg(avg_distance);
                }
                r1DataBean.setSpeedLists(arrayList3);
                r1DataBean.setStepRateLists(arrayList);
                r1DataBean.setEarthTimeLists(arrayList2);
                r1DataBean.setVerticalLists(list);
                r1DataBean.setSpeed_max(max_speed);
                r1DataBean.setMin_hr(minHr_avg);
                r1DataBean.setMax_hr(maxHr_avg);
                r1DataBean.setAvg_hr(0);
                r1DataBean.setHrLists(avgHr_avgs);
                return r1DataBean;
            } catch (Exception e) {
            }
        }
        return null;
    }

    public HeartData initHrData(R1DataBean bean, String data_from, List<Integer> hrList, long startTime, long endTime) {
        if (bean == null) {
            return null;
        }
        return ModuleRouteHeartService.getInstance().getHeartDataR1ByTime(UserConfig.getInstance().getNewUID(), data_from, startTime, endTime, UserConfig.getInstance().getAge(), hrList);
    }

    private R1DataBean initHistoryData() {
        R1DataBean r1DataBean = new R1DataBean();
        if (this.item != null) {
            try {
                DlineDataBean bean = new DlineDataBean(System.currentTimeMillis(), 0.0f);
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                List<DlineDataBean> flight_avgs = new ArrayList<>();
                ArrayList arrayList3 = new ArrayList();
                new ArrayList();
                arrayList.add(bean);
                arrayList2.add(bean);
                flight_avgs.add(bean);
                arrayList3.add(bean);
                ArrayList listJson = JsonTool.getListJson(this.item.getRateOfStride_avg(), DlineDataBean.class);
                ArrayList listJson2 = JsonTool.getListJson(this.item.getTouchDown_avg(), DlineDataBean.class);
                List<DlineDataBean> flight = JsonTool.getListJson(this.item.getFlight_avg(), DlineDataBean.class);
                ArrayList listJson3 = JsonTool.getListJson(this.item.getSpeedList(), DlineDataBean.class);
                List<Integer> avgHr_avgs = JsonTool.getListJson(this.item.getAvg_hr(), Integer.class);
                arrayList.addAll(listJson);
                arrayList2.addAll(listJson2);
                flight_avgs.addAll(flight);
                arrayList3.addAll(listJson3);
                float rateOfStride_avg = addAvgData(arrayList);
                float touchDown_avg = addAvgData(arrayList2);
                float flight_avg = addAvgData(flight_avgs);
                List<DlineDataBean> list = flightTimeToVerticalLists(flight_avgs);
                float touchDow_balance_avg = ((float) this.item.getTouchDownPower_balance()) / 10.0f;
                float avg_distance = addAvgData(arrayList3);
                int maxRate = (int) maxValue(arrayList);
                int maxEarth_time = (int) maxValue(arrayList2);
                int maxFlight_avg = (int) maxValue(flight_avgs);
                float min_speed = minValue(arrayList3);
                float max_speed = maxValue(arrayList3);
                int maxHr_avg = (int) maxIntValue(avgHr_avgs);
                int minHr_avg = (int) minIntValue(avgHr_avgs);
                r1DataBean.setRate_avg(this.decimal0Format.format((double) rateOfStride_avg));
                r1DataBean.setEarth_time_avg(String.valueOf(this.decimal0Format.format((double) touchDown_avg)));
                r1DataBean.setSky_time_avg(String.valueOf(this.decimal0Format.format((double) flight_avg)));
                r1DataBean.setMaxRate(maxRate);
                r1DataBean.setMax_earth_time(maxEarth_time);
                r1DataBean.setMax_vertical((int) flyTimeToVertical((float) maxFlight_avg));
                r1DataBean.setVertical_avg(this.decimal1Format.format((double) flyTimeToVertical(flight_avg)));
                r1DataBean.setEarth_balance(this.decimal1Format.format((double) touchDow_balance_avg) + "% - " + this.decimal1Format.format((double) Math.abs(100.0f - touchDow_balance_avg)) + "%");
                r1DataBean.setSpeed_min(min_speed);
                if (arrayList3.size() > 0) {
                    r1DataBean.setSpeed_avg(avg_distance);
                }
                r1DataBean.setSpeedLists(arrayList3);
                r1DataBean.setStepRateLists(arrayList);
                r1DataBean.setEarthTimeLists(arrayList2);
                r1DataBean.setVerticalLists(list);
                r1DataBean.setSpeed_max(max_speed);
                r1DataBean.setMin_hr(minHr_avg);
                r1DataBean.setMax_hr(maxHr_avg);
                r1DataBean.setAvg_hr(0);
                r1DataBean.setHrLists(avgHr_avgs);
                this.r1Data.showR1Data(r1DataBean);
                return r1DataBean;
            } catch (Exception e) {
            }
        }
        return null;
    }

    private float addAvgData(List<DlineDataBean> dlineDataBeans) {
        float value = 0.0f;
        float index = 0.0f;
        for (DlineDataBean bean : dlineDataBeans) {
            if (bean.value > 0.0f) {
                value += bean.value;
                index += 1.0f;
            }
        }
        if (index != 0.0f) {
            return (value * 1.0f) / index;
        }
        return index;
    }

    private float addAvgIntData(List<Float> integers) {
        int value = 0;
        int index = 0;
        for (Float floatValue : integers) {
            float bean = floatValue.floatValue();
            if (bean > 0.0f) {
                value = (int) (((float) value) + bean);
                index++;
            }
        }
        if (index != 0) {
            return (((float) value) * 1.0f) / ((float) index);
        }
        return (float) index;
    }

    private float maxValue(List<DlineDataBean> dlineDataBeans) {
        float maxValue = 0.0f;
        for (DlineDataBean bean : dlineDataBeans) {
            maxValue = Math.max(bean.value, maxValue);
        }
        return maxValue;
    }

    private float minValue(List<DlineDataBean> dlineDataBeans) {
        float minValue = 1000.0f;
        for (DlineDataBean bean : dlineDataBeans) {
            if (bean.value > 0.0f) {
                minValue = Math.min(bean.value, minValue);
            }
        }
        return minValue;
    }

    private float maxIntValue(List<Integer> dlineDataBeans) {
        if (dlineDataBeans == null) {
            return 0.0f;
        }
        float maxValue = 0.0f;
        for (Integer bean : dlineDataBeans) {
            maxValue = Math.max((float) bean.intValue(), maxValue);
        }
        return maxValue;
    }

    private float minIntValue(List<Integer> dlineDataBeans) {
        if (dlineDataBeans == null) {
            return 0.0f;
        }
        float minValue = 1000.0f;
        for (Integer bean : dlineDataBeans) {
            if (bean.intValue() > 0) {
                minValue = Math.min((float) bean.intValue(), minValue);
            }
        }
        return minValue;
    }

    private List<DlineDataBean> flightTimeToVerticalLists(List<DlineDataBean> lists) {
        List<DlineDataBean> list = new ArrayList<>();
        for (DlineDataBean bean : lists) {
            DlineDataBean bean1 = new DlineDataBean();
            bean1.time = bean.time;
            bean1.value = flyTimeToVertical(bean.value);
            list.add(bean1);
        }
        return list;
    }

    private float flyTimeToVertical(float value) {
        return new BigDecimal((double) (((5.0f * value) * value) / 10000.0f)).setScale(2, 4).floatValue();
    }
}
