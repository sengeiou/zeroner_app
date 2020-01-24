package com.iwown.device_module.device_alarm_schedule.activity.week;

import android.content.Context;
import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.device_alarm_schedule.bean.WeekRepeat;
import java.util.List;

public class WeekDaySelectContract {

    interface Presenter extends BasePresenter {
        List<WeekRepeat> getData(Context context, byte b);

        byte getWeekRepeat(List<WeekRepeat> list);
    }

    interface View extends BaseView<Presenter> {
    }
}
