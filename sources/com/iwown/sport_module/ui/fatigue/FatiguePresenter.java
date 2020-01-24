package com.iwown.sport_module.ui.fatigue;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.data_link.fatigue.FatigueShowData;
import com.iwown.data_link.fatigue.ModuleRouteFatigueService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.lib_common.views.fatigueview2.FatigueDataBean2;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.fatigue.FatigueContract.FatiguePresenter1;
import com.iwown.sport_module.ui.fatigue.FatigueContract.FatigueView;
import com.iwown.sport_module.util.SPUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FatiguePresenter implements FatiguePresenter1 {
    /* access modifiers changed from: private */
    public final FatigueView fatigueView;
    private boolean isOver = false;
    private int pageSize = 30;
    private long startTime = System.currentTimeMillis();

    public FatiguePresenter(FatigueView fatigueView2) {
        this.fatigueView = fatigueView2;
    }

    public void start(boolean b) {
    }

    public void onDestroy() {
    }

    public void loadFatigueDatas() {
        if (this.isOver) {
            this.fatigueView.showDataOver();
            return;
        }
        long aLong = SPUtils.getLong(ContextUtil.app, SPUtils.Fatigue_Data_Request_Time);
        final DateUtil dateUtil = new DateUtil(this.startTime, false);
        dateUtil.setHour(23);
        dateUtil.setMinute(59);
        dateUtil.setSecond(59);
        if (DateUtil.isSameDay(new Date(), new Date(this.startTime))) {
            KLog.e("  " + (this.startTime - aLong) + "  " + aLong);
            if (this.startTime - aLong <= 900000) {
                KLog.e("----距离上次当天30天 15分钟 不再去请求");
                loadLocalFatigue(dateUtil);
                return;
            }
        }
        this.fatigueView.showLoading();
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SPUtils.save(FatiguePresenter.this.fatigueView.getContext(), SPUtils.Fatigue_Data_Request_Time, System.currentTimeMillis());
                FatiguePresenter.this.loadLocalFatigue(dateUtil);
            }

            public void onFail(Throwable e) {
                FatiguePresenter.this.loadLocalFatigue(dateUtil);
            }
        }).getFatigueData(UserConfig.getInstance().getNewUID(), this.pageSize, dateUtil.getY_M_D());
    }

    /* access modifiers changed from: private */
    public void loadLocalFatigue(DateUtil dateUtil) {
        this.fatigueView.dismissLoading();
        List<FatigueShowData> fatigueDatas = ModuleRouteFatigueService.getIsnatnce().getFatigueDatas(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDev_mac(), dateUtil.getTimestamp(), this.pageSize);
        KLog.e(fatigueDatas);
        if (fatigueDatas == null || fatigueDatas.size() < this.pageSize) {
            this.isOver = true;
        }
        List<FatigueDataBean2> fatigueDataBean2s = new ArrayList<>();
        for (FatigueShowData fatigueShowData : fatigueDatas) {
            try {
                List<FatigueData> fatigues = JsonUtils.getListJson(fatigueShowData.getFatigues(), FatigueData.class);
                if (fatigues == null) {
                    fatigues = new ArrayList<>();
                }
                int max = 0;
                int min = 1000;
                for (FatigueData data : fatigues) {
                    if (data.getValue() > max) {
                        max = data.getValue();
                    }
                    if (data.getValue() < min) {
                        min = data.getValue();
                    }
                }
                if (max == 0 && min == 1000) {
                    KLog.e(" max " + max + "  min " + min);
                } else {
                    DateUtil dateUtil1 = new DateUtil(fatigueShowData.getTime(), false);
                    FatigueDataBean2 fatigueDataBean2 = new FatigueDataBean2(max, min, dateUtil1.getMonth() + "/" + dateUtil1.getDay(), fatigueShowData.getData_from());
                    fatigueDataBean2.json_details = fatigueShowData.getFatigues();
                    if (fatigueDataBean2s.size() > 0) {
                        fatigueDataBean2.left_data = (FatigueDataBean2) fatigueDataBean2s.get(fatigueDataBean2s.size() - 1);
                        fatigueDataBean2.left_data.right_data = fatigueDataBean2;
                    }
                    fatigueDataBean2s.add(fatigueDataBean2);
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        KLog.e(fatigueDataBean2s);
        if (dateUtil.isToday()) {
            this.fatigueView.showDatas(fatigueDataBean2s, true);
            dateUtil.addDay(-this.pageSize);
            this.startTime = dateUtil.getTimestamp();
            return;
        }
        this.fatigueView.showDatas(fatigueDataBean2s, false);
        dateUtil.addDay(-this.pageSize);
        this.startTime = dateUtil.getTimestamp();
    }
}
