package com.iwown.sport_module.ui.heart;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.sql.SportSqlHelper;
import com.iwown.sport_module.ui.heart.HeartContract.HeartPresenter1;
import com.iwown.sport_module.ui.heart.HeartContract.HeartView;
import com.iwown.sport_module.ui.repository.DataSource.DataCallBack;
import com.iwown.sport_module.ui.repository.HeartDataRepository;
import com.iwown.sport_module.ui.repository.HeartDataRepository.OnLoadingStart;
import com.socks.library.KLog;

public class HeartPresnter implements HeartPresenter1 {
    private final HeartDataRepository heartDataRepository;
    /* access modifiers changed from: private */
    public final HeartView heartView;
    private String ym;

    public HeartPresnter(HeartView heartView2, HeartDataRepository heartDataRepository2) {
        this.heartView = heartView2;
        this.heartDataRepository = heartDataRepository2;
    }

    public void start(boolean b) {
    }

    public void onDestroy() {
        this.heartDataRepository.ondestory();
    }

    public void loadData(final DateUtil dateUtil) {
        final HeartShowData heartShowData = new HeartShowData();
        heartShowData.uid = UserConfig.getInstance().getNewUID();
        heartShowData.data_from = UserConfig.getInstance().getDevice();
        heartShowData.dateUtil = dateUtil;
        KLog.e(" loadData " + dateUtil.getY_M_D() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + heartShowData);
        this.heartDataRepository.getHeartByTime(heartShowData, new DataCallBack<Integer>() {
            public void onResult(Integer integer) {
                HeartPresnter.this.heartView.dismissLoading();
                int sportTimes = SportSqlHelper.getInstance().getActiveTimeExecludeWalk(UserConfig.getInstance().getNewUID(), dateUtil.getYear(), dateUtil.getMonth(), dateUtil.getDay(), UserConfig.getInstance().getDevice());
                KLog.e("  " + sportTimes);
                heartShowData.sportTimes = sportTimes;
                HeartPresnter.this.updateUI(heartShowData);
                HeartPresnter.this.loadHeartSatus(dateUtil, heartShowData);
            }
        }, new OnLoadingStart() {
            public void onStartLoading() {
                HeartPresnter.this.heartView.showLoading();
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadHeartSatus(DateUtil dateUtil, HeartShowData heartShowData) {
        this.heartView.updateCalendar(ModuleRouteHeartService.getInstance().getStatusDatas(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDevice(), dateUtil));
    }

    /* access modifiers changed from: private */
    public void updateUI(HeartShowData heartShowData) {
        KLog.e(" updateUI ");
        this.heartView.showDatas(heartShowData);
    }
}
