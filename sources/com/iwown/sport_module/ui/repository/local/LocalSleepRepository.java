package com.iwown.sport_module.ui.repository.local;

import android.content.Context;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.sport_module.ui.repository.DataSource;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack1;

public class LocalSleepRepository implements DataSource {
    private static LocalSleepRepository instance;
    private Context context;

    private LocalSleepRepository(Context context1) {
        this.context = context1;
    }

    public static LocalSleepRepository getInsatnce(Context context2) {
        if (instance == null) {
            instance = new LocalSleepRepository(context2);
        }
        return instance;
    }

    public Context getContext() {
        return this.context;
    }

    public void loadDayDataByTime(SleepDataDay sleepDataToday, DataCallBack1<SleepDataDay> dataCallBack1) {
        ModuleRouteSleepService.getInstance().getDaySleep(sleepDataToday);
        if (sleepDataToday.isLocalRepository()) {
            dataCallBack1.onResult(sleepDataToday);
        } else {
            dataCallBack1.onNoData(sleepDataToday);
        }
    }
}
