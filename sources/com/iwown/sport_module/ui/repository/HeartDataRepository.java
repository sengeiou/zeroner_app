package com.iwown.sport_module.ui.repository;

import android.content.Context;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.iwown.sport_module.ui.repository.local.LocalHeartRepository;
import com.iwown.sport_module.ui.repository.remote.RemoteHeartRepository;
import com.iwown.sport_module.ui.utils.BaseSportUtils;
import com.iwown.sport_module.util.SPUtils;
import com.socks.library.KLog;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.cli.HelpFormatter;

public class HeartDataRepository implements DataSource {
    private static HeartDataRepository Instance;
    /* access modifiers changed from: private */
    public Set<String> hasRequestDates = new HashSet();
    /* access modifiers changed from: private */
    public Set<String> hasRequestStatusDates = new HashSet();
    /* access modifiers changed from: private */
    public LocalHeartRepository localHeartRepository;
    /* access modifiers changed from: private */
    public RemoteHeartRepository remoteHeartRepository;

    public interface OnLoadingStart {
        void onStartLoading();
    }

    public HeartDataRepository(LocalHeartRepository localHeartRepository2, RemoteHeartRepository remoteHeartRepository2) {
        this.localHeartRepository = localHeartRepository2;
        this.remoteHeartRepository = remoteHeartRepository2;
    }

    public static HeartDataRepository getInstance(LocalHeartRepository localHeartRepository2, RemoteHeartRepository remoteHeartRepository2) {
        if (Instance == null) {
            Instance = new HeartDataRepository(localHeartRepository2, remoteHeartRepository2);
        }
        return Instance;
    }

    public Context getContext() {
        return this.localHeartRepository.getContext();
    }

    public void getHeartByTime(final HeartShowData heartShowData, final DataCallBack<Integer> dataCallBack, OnLoadingStart onLoadingStart) {
        KLog.e(this.hasRequestDates);
        if (this.hasRequestDates.contains(BaseSportUtils.getUI_DataFrom_Month(heartShowData.dateUtil))) {
            KLog.d("当月心率下载过 直接跳过");
            this.localHeartRepository.getHeartByTime(heartShowData);
            dataCallBack.onResult(Integer.valueOf(0));
            return;
        }
        if (onLoadingStart != null) {
            onLoadingStart.onStartLoading();
        }
        this.remoteHeartRepository.getHeartByTime(heartShowData, new DataCallBack<Integer>() {
            public void onResult(Integer integer) {
                KLog.i("getHeartByTime " + integer);
                HeartDataRepository.this.hasRequestDates.add(BaseSportUtils.getUI_DataFrom_Month(heartShowData.dateUtil));
                SPUtils.save(HeartDataRepository.this.getContext(), SPUtils.Heart_Download_Requests_Time, System.currentTimeMillis());
                HeartDataRepository.this.remoteHeartRepository.getHeartSports(heartShowData, new DataCallBack<Integer>() {
                    public void onResult(Integer integer) {
                        HeartDataRepository.this.localHeartRepository.getHeartByTime(heartShowData);
                        dataCallBack.onResult(Integer.valueOf(0));
                    }
                });
            }
        });
    }

    public void ondestory() {
        long aLong = SPUtils.getLong(getContext(), SPUtils.Heart_Download_Requests_Time);
        KLog.e(aLong + "   " + (System.currentTimeMillis() - aLong));
        if (System.currentTimeMillis() - aLong > 600000) {
            this.hasRequestDates.clear();
        }
    }

    public void getHeartSatatus(DateUtil dateUtil, final DataCallBack<Integer> dataCallBack) {
        final String ym = dateUtil.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + dateUtil.getMonth();
        if (this.hasRequestStatusDates.contains(ym)) {
            KLog.e("已经获取" + ym + "statusDatas");
            dataCallBack.onResult(Integer.valueOf(0));
            return;
        }
        KLog.e("getHeartSatatus DataRepository");
        this.remoteHeartRepository.getHeartSatatus(dateUtil, new DataCallBack<Integer>() {
            public void onResult(Integer integer) {
                if (integer.intValue() == 0) {
                    HeartDataRepository.this.hasRequestStatusDates.add(ym);
                }
                dataCallBack.onResult(Integer.valueOf(0));
            }
        });
    }
}
