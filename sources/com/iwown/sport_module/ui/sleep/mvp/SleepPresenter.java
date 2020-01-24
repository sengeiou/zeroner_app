package com.iwown.sport_module.ui.sleep.mvp;

import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.iwown.sport_module.ui.repository.SleepDataRepository;
import com.iwown.sport_module.ui.sleep.mvp.SleepContract.SPrecenter;
import com.iwown.sport_module.ui.sleep.mvp.SleepContract.SleepView;
import com.socks.library.KLog;

public class SleepPresenter implements SPrecenter {
    private final SleepDataRepository sleepDataRepository;
    /* access modifiers changed from: private */
    public final SleepView sleepView;
    private String ym = "";

    public SleepPresenter(SleepView sleepView2, SleepDataRepository sleepDataRepository2) {
        this.sleepView = sleepView2;
        sleepView2.setPresenter(this);
        this.sleepDataRepository = sleepDataRepository2;
    }

    public void start(boolean b) {
    }

    public void onDestroy() {
        this.ym = "";
        this.sleepDataRepository.onDestroy();
    }

    public void loadDayDataByTime(final long preOrNextTimeByDay) {
        KLog.d("loadDayDataByTime " + DataTimeUtils.getyyyyMMddHHmmss(preOrNextTimeByDay) + "  " + UserConfig.getInstance().getNewUID());
        SleepDataDay sleepDataToday = new SleepDataDay();
        sleepDataToday.date = DataTimeUtils.getyyyyMMddHHmmss(preOrNextTimeByDay);
        sleepDataToday.uid = UserConfig.getInstance().getNewUID();
        sleepDataToday.data_from = UserConfig.getInstance().getDevice();
        sleepDataToday.time_unix = preOrNextTimeByDay / 1000;
        this.sleepView.showLoading();
        KLog.e(" loadDayDataByTime " + sleepDataToday);
        this.sleepDataRepository.loadDayDataByTime(sleepDataToday, new DataCallBack<SleepDataDay>() {
            public void onResult(SleepDataDay sleepDataToday) {
                SleepPresenter.this.sleepView.dismissLoading();
                SleepPresenter.this.sleepView.showSleepData(sleepDataToday);
                SleepPresenter.this.loadSleepStatus(preOrNextTimeByDay, sleepDataToday);
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadSleepStatus(long preOrNextTimeByDay, SleepDataDay sleepDataToday) {
        showSleepStatusDatas(new DateUtil(preOrNextTimeByDay, false), sleepDataToday);
    }

    private void showSleepStatusDatas(DateUtil dateUtil, SleepDataDay sleepDataToday) {
        this.sleepView.updateCalendar(ModuleRouteSleepService.getInstance().getStatusDatas(UserConfig.getInstance().getNewUID(), dateUtil, UserConfig.getInstance().getDevice()));
    }
}
