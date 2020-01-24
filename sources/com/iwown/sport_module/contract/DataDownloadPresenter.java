package com.iwown.sport_module.contract;

import android.content.Context;
import android.os.Handler;
import com.iwown.data_link.data.CopySportAll;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.HeartStatusData.ContentBean;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepStatusData;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.fatigueview2.FatigueDataBean2;
import com.iwown.sport_module.gps.contract.SportDetailContract.SportDetailView;
import com.iwown.sport_module.gps.data.SportDetailItem;
import com.iwown.sport_module.gps.presenter.SportDetailPresenter;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.response.Sport28MonthCode;
import com.iwown.sport_module.ui.fatigue.FatigueContract.FatiguePresenter1;
import com.iwown.sport_module.ui.fatigue.FatigueContract.FatigueView;
import com.iwown.sport_module.ui.fatigue.FatiguePresenter;
import com.iwown.sport_module.ui.heart.HeartContract.HeartPresenter1;
import com.iwown.sport_module.ui.heart.HeartContract.HeartView;
import com.iwown.sport_module.ui.heart.HeartPresnter;
import com.iwown.sport_module.ui.repository.DataRepositoryHelper;
import com.iwown.sport_module.ui.sleep.mvp.SleepContract.SPrecenter;
import com.iwown.sport_module.ui.sleep.mvp.SleepContract.SleepView;
import com.iwown.sport_module.ui.sleep.mvp.SleepPresenter;
import com.socks.library.KLog;
import java.util.List;
import java.util.Map;

public class DataDownloadPresenter {
    private DataDownLoadListener listener;
    private Handler mHandler;
    private final int progressBlock = 14;
    private Runnable runnable = new Runnable() {
        public void run() {
            DataDownloadPresenter.this.downloadTimeout();
        }
    };

    private class MyHeart implements HeartView {
        private MyHeart() {
        }

        public Context getContext() {
            return null;
        }

        public void showDatas(HeartShowData datas) {
            KLog.e("MyHeart---showDatas");
        }

        public void dismissLoading() {
            KLog.e("MyHeart---dismissLoading");
        }

        public void showLoading() {
            KLog.e("MyHeart---showLoading");
        }

        public void updateCalendar(Map<String, ContentBean> map) {
            KLog.e("MyHeart---updateCalendar");
            KLog.e("finish");
            DataDownloadPresenter.this.downloadProgress(100);
            DataDownloadPresenter.this.downloadFinish();
        }

        public void setPresenter(HeartPresenter1 presenter) {
            KLog.e("MyHeart---setPresenter");
        }
    }

    private class MyHrv implements FatigueView {
        private MyHrv() {
        }

        public void showDataOver() {
            KLog.e("MyHrv---showDataOver");
        }

        public void showDatas(List<FatigueDataBean2> list, boolean isStart) {
            KLog.e("MyHrv---showDatas");
            DataDownloadPresenter.this.downData(6);
        }

        public void showLoading() {
            KLog.e("MyHrv---showLoading");
        }

        public void dismissLoading() {
            KLog.e("MyHrv---dismissLoading");
        }

        public Context getContext() {
            return null;
        }

        public void setPresenter(FatiguePresenter1 presenter) {
        }
    }

    private class MySleep implements SleepView {
        private MySleep() {
        }

        public void showSleepData(SleepDataDay sleepDataToday) {
            KLog.e("MySleep---showSleepData");
        }

        public void showLoading() {
            KLog.e("MySleep---showLoading");
        }

        public void dismissLoading() {
            KLog.e("MySleep---dismissLoading");
        }

        public void updateCalendar(Map<String, SleepStatusData.ContentBean> map) {
            KLog.e("MySleep---updateCalendar");
            DataDownloadPresenter.this.downData(5);
        }

        public void setPresenter(SPrecenter presenter) {
        }
    }

    private class MySport implements SportDetailView {
        private int index;

        private MySport() {
            this.index = 0;
        }

        public void loadPageDataSuccess(List<SportDetailItem> list) {
            KLog.e("MySport---loadPageDataSuccess");
        }

        public void loadPageDataFail() {
            KLog.e("MySport---loadPageDataFail");
            this.index++;
            if (this.index >= 7) {
                DataDownloadPresenter.this.downData(2);
                KLog.e("mysport finish");
            }
        }

        public void loadPageServiceDataSuccess(int netNum, List<SportDetailItem> list) {
            KLog.e("MySport---loadPageServiceDataSuccess");
            this.index++;
            if (this.index >= 7) {
                KLog.e("mysport finish");
                DataDownloadPresenter.this.downData(2);
            }
        }

        public void loadAllSportSuccess(CopySportAll copySportAll) {
            KLog.e("MySport---loadAllSportSuccess");
        }

        public void loadAllSportFail() {
            KLog.e("MySport---loadAllSportFail");
        }

        public void loadServiceAllSportSuccess(CopySportAll copySportAll) {
            KLog.e("MySport---loadServiceAllSportSuccess");
        }
    }

    public DataDownloadPresenter(DataDownLoadListener listener2) {
        this.listener = listener2;
        this.mHandler = new Handler();
    }

    public void downloadAll() {
        downData(0);
    }

    public void onDestory() {
        this.mHandler.removeCallbacksAndMessages(Integer.valueOf(0));
    }

    private void downloadStart() {
        if (this.listener != null) {
            this.listener.downloadStart();
        }
    }

    /* access modifiers changed from: private */
    public void downloadProgress(int progress) {
        if (this.listener != null) {
            this.listener.downloadProgress(progress);
        }
    }

    /* access modifiers changed from: private */
    public void downloadFinish() {
        clearTime();
        if (this.listener != null) {
            this.listener.downloadFinish();
        }
    }

    /* access modifiers changed from: private */
    public void downloadTimeout() {
        clearTime();
        if (this.listener != null) {
            this.listener.downloadTimeOut();
        }
    }

    private void checkTimeOut() {
        this.mHandler.removeCallbacks(this.runnable);
        this.mHandler.postDelayed(this.runnable, 15000);
    }

    private void delay(long timeMils) {
        this.mHandler.removeCallbacks(this.runnable);
        this.mHandler.postDelayed(this.runnable, timeMils);
    }

    private void clearTime() {
        this.mHandler.removeCallbacks(this.runnable);
    }

    /* access modifiers changed from: private */
    public void downData(int position) {
        checkTimeOut();
        switch (position) {
            case 0:
                KLog.e("start sport");
                downloadStart();
                downloadSport();
                return;
            case 1:
                KLog.e("end sport");
                downloadProgress(14);
                downloadActive();
                KLog.e("start active");
                return;
            case 2:
                KLog.e("end active");
                downloadProgress(28);
                downloadBP();
                KLog.e("start bp");
                return;
            case 3:
                KLog.e("end bp");
                downloadProgress(42);
                downloadECG();
                KLog.e("start ECG");
                return;
            case 4:
                KLog.e("end ECG");
                downloadProgress(56);
                downloadSleep();
                KLog.e("start sleep");
                return;
            case 5:
                downloadProgress(70);
                KLog.e("end sleep");
                downloadHrv();
                KLog.e("start hrv");
                return;
            case 6:
                downloadProgress(84);
                KLog.e("start hrv");
                downloadHr();
                KLog.e("start hr");
                return;
            default:
                return;
        }
    }

    private void downloadSport() {
        boolean isEnglish;
        int cardType;
        if (UserConfig.getInstance().isMertric()) {
            isEnglish = false;
        } else {
            isEnglish = true;
        }
        long lastLoadTime = System.currentTimeMillis() / 1000;
        SportDetailPresenter sportDetailPresenter = new SportDetailPresenter(new MySport(), !isEnglish);
        for (int i = 0; i < 7; i++) {
            if (i < 4) {
                cardType = 0;
            } else if (i == 4) {
                cardType = 1;
            } else if (i == 6) {
                cardType = 3;
            } else {
                cardType = 2;
            }
            sportDetailPresenter.getDetailGpsServer(UserConfig.getInstance().getNewUID(), lastLoadTime, 20, cardType, i);
            KLog.e("下载....." + i);
        }
    }

    private void downloadActive() {
        DateUtil dateUtil = new DateUtil();
        NetFactory.getInstance().getClient(new MyCallback<Sport28MonthCode>() {
            public void onSuccess(Sport28MonthCode o) {
                DataDownloadPresenter.this.downData(2);
            }

            public void onFail(Throwable e) {
                DataDownloadPresenter.this.downData(2);
            }
        }).hasSport28DataNet(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth());
    }

    private void downloadHr() {
        new HeartPresnter(new MyHeart(), DataRepositoryHelper.getHeartDataRepository(ContextUtil.app)).loadData(new DateUtil());
    }

    private void downloadSleep() {
        new SleepPresenter(new MySleep(), DataRepositoryHelper.getSleepDataRepository(ContextUtil.app)).loadDayDataByTime(System.currentTimeMillis());
    }

    private void downloadECG() {
        DateUtil dateUtil = new DateUtil();
        NetFactory.getInstance().getClient(new MyCallback<Object>() {
            public void onSuccess(Object integer) {
                DataDownloadPresenter.this.downData(4);
            }

            public void onFail(Throwable e) {
                DataDownloadPresenter.this.downData(4);
            }
        }).downLoadEcgData(UserConfig.getInstance().getNewUID(), dateUtil.getYear() + "", dateUtil.getMonth() + "", dateUtil.getDay() + "");
    }

    private void downloadBP() {
        DateUtil dateUtil = new DateUtil();
        dateUtil.addMonth(-1);
        DateUtil dateUtil1 = new DateUtil();
        KLog.e("download----", dateUtil.getY_M_D_H_M_S() + "-----" + dateUtil1.getY_M_D_H_M_S());
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                KLog.e("88808  downloadAllBlood ---------111   :  ");
                DataDownloadPresenter.this.downData(3);
            }

            public void onFail(Throwable e) {
                KLog.e("88808  downloadAllBlood data fai1");
                DataDownloadPresenter.this.downData(3);
            }
        }).downloadAllBlood(UserConfig.getInstance().getNewUID(), dateUtil.getY_M_D_H_M_S(), dateUtil1.getY_M_D_H_M_S());
    }

    private void downloadHrv() {
        new FatiguePresenter(new MyHrv()).loadFatigueDatas();
    }
}
