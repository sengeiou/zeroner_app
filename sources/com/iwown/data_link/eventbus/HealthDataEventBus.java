package com.iwown.data_link.eventbus;

import com.google.android.gms.fitness.FitnessActivities;
import com.iwown.device_module.device_setting.configure.WristbandModel.DownType;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

public class HealthDataEventBus {
    private static Map<Integer, String> masp = new HashMap<Integer, String>() {
        {
            put(Integer.valueOf(1), FitnessActivities.SLEEP);
            put(Integer.valueOf(2), "heart");
            put(Integer.valueOf(3), "weight");
            put(Integer.valueOf(4), DownType.FATIGUE);
            put(Integer.valueOf(5), "all upload");
            put(Integer.valueOf(6), "all update");
            put(Integer.valueOf(7), "ecg");
            put(Integer.valueOf(100), "updateTraningData");
            put(Integer.valueOf(101), "traning adater notify");
            put(Integer.valueOf(103), "公英制切换");
            put(Integer.valueOf(8), "blood");
            put(Integer.valueOf(9), "af");
        }
    };
    public String date;
    public int type;

    public static String getTypeName(int key) {
        return (String) masp.get(Integer.valueOf(key));
    }

    public HealthDataEventBus(int type2) {
        this.type = type2;
    }

    public HealthDataEventBus(int type2, String date2) {
        this.type = type2;
        this.date = date2;
    }

    public static void updateHealthSleepEvent() {
        EventBus.getDefault().post(new HealthDataEventBus(1));
    }

    public static void updateHealthHeartEvent() {
        EventBus.getDefault().post(new HealthDataEventBus(2));
    }

    public static void updateHealthWeightEvent() {
        EventBus.getDefault().post(new HealthDataEventBus(3));
    }

    public static void updateHealthFatigueEvent() {
        EventBus.getDefault().post(new HealthDataEventBus(4));
    }

    public static void updateHealthEcg() {
        EventBus.getDefault().post(new HealthDataEventBus(7));
    }

    public static void updateHealthBloodEvent() {
        EventBus.getDefault().post(new HealthDataEventBus(8));
    }

    public static void updateAfEvent(String date2) {
        EventBus.getDefault().post(new HealthDataEventBus(9, date2));
    }

    public static void updateAfEvent() {
        EventBus.getDefault().post(new HealthDataEventBus(9));
    }

    public static void updateAllDataEvent() {
        EventBus.getDefault().post(new HealthDataEventBus(5));
    }

    public static void updateAllUI() {
        EventBus.getDefault().post(new HealthDataEventBus(6));
    }

    public static void updateTraningData() {
        EventBus.getDefault().post(new HealthDataEventBus(100));
    }

    public static void updateMertricUIChange() {
        EventBus.getDefault().post(new HealthDataEventBus(103));
    }
}
