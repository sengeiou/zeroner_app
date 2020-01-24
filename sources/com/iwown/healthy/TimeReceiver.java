package com.iwown.healthy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.utils.DataTimeUtils;
import com.iwown.sport_module.util.SPUtils;
import com.socks.library.KLog;

public class TimeReceiver extends BroadcastReceiver {
    private static final long POWER_TIME = 1800000;

    public void onReceive(Context context, Intent intent) {
        if (new DateUtil().getZeroTime1() > SPUtils.getLong(context, SPUtils.TODAY_UI_First_Update)) {
            KLog.e(DataTimeUtils.getyyyyMMddHHmm(new DateUtil().getTimestamp()) + " 今天第一次 进来 刷新界面");
            HealthDataEventBus.updateAllUI();
        }
    }
}
