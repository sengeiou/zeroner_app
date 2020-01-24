package com.iwown.sport_module.ui.repository.remote;

import android.content.Context;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.repository.DataSource;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.socks.library.KLog;
import java.util.Date;

public class RemoteSleepRepository implements DataSource {
    private static RemoteSleepRepository instance;
    private Context context;

    private RemoteSleepRepository(Context context1) {
        this.context = context1;
    }

    public static RemoteSleepRepository getInsatnce(Context context2) {
        if (instance == null) {
            instance = new RemoteSleepRepository(context2);
        }
        return instance;
    }

    public Context getContext() {
        return this.context;
    }

    public void loadDayDataByTime(final SleepDataDay sleepDataToday, final DataCallBack<SleepDataDay> dataCallBack) {
        DateUtil dateUtil = new DateUtil(sleepDataToday.time_unix, true);
        DateUtil date_month = new DateUtil(DateUtil.getFirstDayMonth(new Date(dateUtil.getTimestamp())), false);
        int daysOfMonth = DateUtil.getDaysOfMonth(new java.sql.Date(dateUtil.getTimestamp()));
        KLog.e(" -- " + date_month.getY_M_D());
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                if (integer.intValue() == 0) {
                    sleepDataToday.setData_repository(1);
                }
                dataCallBack.onResult(sleepDataToday);
            }

            public void onFail(Throwable e) {
                dataCallBack.onResult(sleepDataToday);
            }
        }).downloadSleepByDate(sleepDataToday.uid, daysOfMonth, date_month.getSyyyyMMddDate());
    }

    public void getSleepStatus(DateUtil dateUtil, final DataCallBack<Integer> dataCallBack) {
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                dataCallBack.onResult(integer);
            }

            public void onFail(Throwable e) {
                dataCallBack.onResult(Integer.valueOf(-1));
            }
        }).downloadSleepStatusByDate(UserConfig.getInstance().getNewUID(), DateUtil.getDaysOfMonth(new java.sql.Date(dateUtil.getTimestamp())), dateUtil.getSyyyyMMddDate());
    }
}
