package com.iwown.sport_module.ui.sleep.mvp;

import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepStatusData.ContentBean;
import com.iwown.sport_module.ui.base.DBasePresenter;
import com.iwown.sport_module.ui.base.DBaseView;
import java.util.Map;

public class SleepContract {

    public interface SPrecenter extends DBasePresenter {
        void loadDayDataByTime(long j);
    }

    public interface SleepView extends DBaseView<SPrecenter> {
        void dismissLoading();

        void showLoading();

        void showSleepData(SleepDataDay sleepDataDay);

        void updateCalendar(Map<String, ContentBean> map);
    }
}
