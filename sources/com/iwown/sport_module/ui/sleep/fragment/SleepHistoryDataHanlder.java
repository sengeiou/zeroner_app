package com.iwown.sport_module.ui.sleep.fragment;

import android.content.Context;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.sport_module.ui.repository.DataRepositoryHelper;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.iwown.sport_module.ui.repository.SleepDataRepository;
import com.socks.library.KLog;
import java.util.HashMap;
import java.util.Map;

public class SleepHistoryDataHanlder {
    public static int SIZE = 15;
    public static Map<String, Long> recordDays = new HashMap();

    public static void getDaySleeps(Context context, DateUtil endDate) {
        endDate.addDay(-SIZE);
        KLog.e(" start Date " + endDate.getSyyyyMMddDate() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + SIZE);
        Long aLong = (Long) recordDays.get(endDate.getSyyyyMMddDate());
        if (aLong == null || System.currentTimeMillis() - aLong.longValue() > 300000) {
            SleepDataRepository sleepDataRepository = DataRepositoryHelper.getSleepDataRepository(context);
            SleepDataDay sleepDataToday = new SleepDataDay();
            long timestamp = endDate.getTimestamp();
            sleepDataToday.date = DataTimeUtils.getyyyyMMddHHmmss(timestamp);
            sleepDataToday.uid = UserConfig.getInstance().getNewUID();
            sleepDataToday.data_from = UserConfig.getInstance().getDev_mac();
            sleepDataToday.time_unix = timestamp / 1000;
            sleepDataToday.size = 15;
            sleepDataRepository.getRemoteSleepRepository().loadDayDataByTime(sleepDataToday, new DataCallBack<SleepDataDay>() {
                public void onResult(SleepDataDay sleepDataToday) {
                    ModuleRouteSleepService.getInstance().getDaySleep(sleepDataToday);
                }
            });
            return;
        }
        KLog.e("5分钟内不在请求");
    }

    public static void destory() {
        recordDays.clear();
    }
}
