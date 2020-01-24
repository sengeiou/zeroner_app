package com.iwown.sport_module.ui.weight.mvp;

import android.text.TextUtils;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.weight.ModuleRouteWeightService;
import com.iwown.data_link.weight.ScaleBodyFat;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.weightview.WeightShowData;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.utils.BaseSportUtils;
import com.iwown.sport_module.ui.weight.mvp.WeightContract.WPrecenter;
import com.iwown.sport_module.ui.weight.mvp.WeightContract.WeightView;
import com.iwown.sport_module.util.SPUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;

public class WeightPresenter implements WPrecenter {
    private List<ScaleBodyFat> scaleBodyFatList;
    /* access modifiers changed from: private */
    public WeightView weightView;

    public WeightPresenter(WeightView weightView2) {
        this.weightView = weightView2;
    }

    public void start(boolean b) {
    }

    public void onDestroy() {
    }

    public void getWifiScaleData(final long uid) {
        KLog.e(" --- getWifiScaleData" + uid);
        this.weightView.showLoading();
        long aLong = SPUtils.getLong(this.weightView.getContext(), SPUtils.Weight_Data_Request_Time + HelpFormatter.DEFAULT_OPT_PREFIX + uid);
        int size = 60;
        if (aLong != 0 && DateUtil.differentDaysByMillisecond(new Date(aLong), new Date()) == 0) {
            size = 0;
        }
        DateUtil dateUtil = new DateUtil();
        dateUtil.addDay(-size);
        NetFactory.getInstance().getClient(new MyCallback() {
            public void onSuccess(Object o) {
                SPUtils.save(WeightPresenter.this.weightView.getContext(), SPUtils.Weight_Data_Request_Time + HelpFormatter.DEFAULT_OPT_PREFIX + uid, System.currentTimeMillis());
                WeightPresenter.this.showWeightData(ModuleRouteWeightService.getInstance().getDatasSizeDay(uid, 60));
            }

            public void onFail(Throwable e) {
                WeightPresenter.this.showWeightData(ModuleRouteWeightService.getInstance().getDatasSizeDay(uid, 60));
            }
        }).getWifiScaleData(uid, size, dateUtil.getSyyyyMMddDate());
        final String mac = ModuleRouteWeightService.getInstance().getScaleMac(uid);
        if (TextUtils.isEmpty(mac) || uid != UserConfig.getInstance().getNewUID()) {
            KLog.e("mac is null 或者 uid不是当前登陆者 不用拉取未归档数据");
            return;
        }
        DateUtil dateUtil1 = new DateUtil(new Date());
        dateUtil1.addDay(-30);
        NetFactory.getInstance().getClient(new MyCallback<Integer>() {
            public void onSuccess(Integer integer) {
                KLog.e(" -- " + integer);
                int count = ModuleRouteWeightService.getInstance().getRawWeightCounts(mac);
                KLog.e(mac + "   " + count);
                WeightPresenter.this.weightView.showRWSize(count);
            }

            public void onFail(Throwable e) {
                WeightPresenter.this.weightView.showRWSize(ModuleRouteWeightService.getInstance().getRawWeightCounts(mac));
            }
        }).getWifiScaleRWData(UserConfig.getInstance().getNewUID(), dateUtil1.getTimestamp(), mac);
    }

    /* access modifiers changed from: private */
    public void showWeightData(List<ScaleBodyFat> scaleBodyFatList2) {
        KLog.e(" showWeightData " + scaleBodyFatList2);
        if (scaleBodyFatList2 == null) {
            scaleBodyFatList2 = new ArrayList<>();
        }
        List<WeightShowData> weightDataList = new ArrayList<>();
        Collections.reverse(scaleBodyFatList2);
        this.scaleBodyFatList = scaleBodyFatList2;
        int index = 0;
        boolean mertric = UserConfig.getInstance().isMertric();
        float max = 0.0f;
        for (ScaleBodyFat scaleBodyFat : scaleBodyFatList2) {
            float weight = scaleBodyFat.getWeight();
            if (!mertric) {
                weight = BaseSportUtils.getLbsFromKG(weight);
            }
            WeightShowData weightData = new WeightShowData(scaleBodyFat.getRecord_date(), (float) BaseSportUtils.getDouble1(weight));
            weightData.old_index = index;
            weightDataList.add(weightData);
            index++;
            if (weightData.real_weight > max) {
                max = weightData.real_weight;
            }
        }
        double double1 = (double) UserConfig.getInstance().getTarget_weight();
        if (!mertric) {
            double1 = BaseSportUtils.getDouble1(BaseSportUtils.getLbsFromKG((float) double1));
        }
        if (double1 > ((double) max)) {
            max = (float) double1;
        }
        this.weightView.showUIDatas(weightDataList, max);
    }

    public ScaleBodyFat getIndexScaleBodyFat(int index) {
        if (this.scaleBodyFatList == null || index < 0 || index > this.scaleBodyFatList.size()) {
            return null;
        }
        return (ScaleBodyFat) this.scaleBodyFatList.get(index);
    }
}
