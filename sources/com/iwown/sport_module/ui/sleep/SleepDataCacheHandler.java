package com.iwown.sport_module.ui.sleep;

import android.content.Context;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.sleep.data.SleepBedTimeStatusBean;
import java.util.ArrayList;

public class SleepDataCacheHandler {
    private static ArrayList<SleepBedTimeStatusBean> sleepBedTimeStatusBeans;

    public static ArrayList<SleepBedTimeStatusBean> getSleepBedTimeStatusBeans() {
        return sleepBedTimeStatusBeans;
    }

    public static void init(Context context) {
        sleepBedTimeStatusBeans = new ArrayList<>();
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_coffee_tea), R.mipmap.sleep_bed_time_coffeeoff, R.mipmap.sleep_bed_time_coffeeon));
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_alcohol), R.mipmap.sleep_bedtime_alcoholoff, R.mipmap.sleep_bed_time_alcoholon));
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_ate_late), R.mipmap.sleep_bed_time_atelateoff, R.mipmap.sleep_bed_time_atelateon));
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_work_out), R.mipmap.sleep_bed_time_workoutoff, R.mipmap.sleep_bed_time_workouton));
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_entertainment), R.mipmap.sleep_bed_time_entertainentoff, R.mipmap.sleep_bed_time_entertainenton));
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_study_work_late), R.mipmap.sleep_bed_time_studyoff, R.mipmap.sleep_bed_time_studyon));
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_not_my_bed), R.mipmap.sleep_bed_time_bedoff, R.mipmap.sleep_bed_time_bedon));
        sleepBedTimeStatusBeans.add(new SleepBedTimeStatusBean(context.getResources().getString(R.string.sport_module_sleep_bedtime_status_stressful_day), R.mipmap.sleep_bed_time_stressfuloff, R.mipmap.sleep_bed_time_stressfulon));
    }

    public static void destory() {
        sleepBedTimeStatusBeans.clear();
    }
}
