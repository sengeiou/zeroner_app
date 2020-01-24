package com.iwown.sport_module.ui.sleep.data;

import android.content.Context;
import com.iwown.sport_module.R;

public class SleepBean {
    public int time;
    public String title;
    public int type;

    public SleepBean(Context context, int type2, int time2) {
        this.time = time2;
        if (type2 == 0) {
            this.title = context.getResources().getString(R.string.sport_module_sleep_total_time);
        } else if (type2 == 1) {
            this.title = context.getResources().getString(R.string.sport_module_sleep_deep_time);
        } else if (type2 == 2) {
            this.title = context.getResources().getString(R.string.sport_module_sleep_light_time);
        } else {
            this.title = context.getResources().getString(R.string.sport_module_sleep_awake_time);
        }
    }
}
