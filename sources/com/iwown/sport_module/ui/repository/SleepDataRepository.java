package com.iwown.sport_module.ui.repository;

import android.content.Context;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack1;
import com.iwown.sport_module.ui.repository.local.LocalSleepRepository;
import com.iwown.sport_module.ui.repository.remote.RemoteSleepRepository;
import com.iwown.sport_module.util.SPUtils;
import com.socks.library.KLog;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.cli.HelpFormatter;

public class SleepDataRepository implements DataSource {
    private static SleepDataRepository Instance;
    /* access modifiers changed from: private */
    public Set<String> hasRequestDates = new HashSet();
    /* access modifiers changed from: private */
    public Set<String> hasRequestStatusDates = new HashSet();
    private LocalSleepRepository localSleepRepository;
    private RemoteSleepRepository remoteSleepRepository;

    public RemoteSleepRepository getRemoteSleepRepository() {
        return this.remoteSleepRepository;
    }

    public SleepDataRepository(LocalSleepRepository localSleepRepository2, RemoteSleepRepository remoteSleepRepository2) {
        this.localSleepRepository = localSleepRepository2;
        this.remoteSleepRepository = remoteSleepRepository2;
    }

    public static SleepDataRepository getInstance(LocalSleepRepository localSleepRepository2, RemoteSleepRepository remoteSleepRepository2) {
        if (Instance == null) {
            Instance = new SleepDataRepository(localSleepRepository2, remoteSleepRepository2);
        }
        return Instance;
    }

    public void loadDayDataByTime(final SleepDataDay sleepDataToday, final DataCallBack<SleepDataDay> dataCallBack) {
        final DateUtil dateUtil = new DateUtil(sleepDataToday.time_unix, true);
        KLog.e(this.hasRequestDates);
        if (this.hasRequestDates.contains(dateUtil.getYyyyMMDate())) {
            KLog.d("当月睡眠下载过 直接跳过");
            loadRealData(sleepDataToday, dataCallBack);
            return;
        }
        this.remoteSleepRepository.loadDayDataByTime(sleepDataToday, new DataCallBack<SleepDataDay>() {
            public void onResult(SleepDataDay sleepDataDay) {
                SleepDataRepository.this.hasRequestDates.add(dateUtil.getYyyyMMDate());
                SPUtils.save(SleepDataRepository.this.getContext(), SPUtils.SLEEP_Download_Requests_Time, System.currentTimeMillis());
                SleepDataRepository.this.loadRealData(sleepDataToday, dataCallBack);
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadRealData(SleepDataDay sleepDataToday, final DataCallBack<SleepDataDay> dataCallBack) {
        KLog.e("  " + sleepDataToday);
        this.localSleepRepository.loadDayDataByTime(sleepDataToday, new DataCallBack1<SleepDataDay>() {
            public void onResult(SleepDataDay sleepDataToday) {
                dataCallBack.onResult(sleepDataToday);
            }

            public void onNoData(SleepDataDay sleepDataToday) {
                dataCallBack.onResult(sleepDataToday);
            }
        });
    }

    public Context getContext() {
        return this.localSleepRepository.getContext();
    }

    public void getSleepStatus(DateUtil dateUtil, final DataCallBack<Integer> dataCallBack) {
        final String ym = dateUtil.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + dateUtil.getMonth();
        if (this.hasRequestStatusDates.contains(ym)) {
            KLog.e("已经获取" + ym + "statusDatas");
            dataCallBack.onResult(Integer.valueOf(0));
            return;
        }
        this.remoteSleepRepository.getSleepStatus(dateUtil, new DataCallBack<Integer>() {
            public void onResult(Integer integer) {
                if (integer.intValue() == 0) {
                    SleepDataRepository.this.hasRequestStatusDates.add(ym);
                }
                dataCallBack.onResult(Integer.valueOf(0));
            }
        });
    }

    public void onDestroy() {
        this.hasRequestStatusDates.clear();
        if (System.currentTimeMillis() - SPUtils.getLong(getContext(), SPUtils.SLEEP_Download_Requests_Time) > 600000) {
            this.hasRequestDates.clear();
        }
    }
}
