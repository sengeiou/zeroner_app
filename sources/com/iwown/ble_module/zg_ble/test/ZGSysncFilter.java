package com.iwown.ble_module.zg_ble.test;

import android.content.Context;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.data.model.SevenDayStore;
import com.iwown.ble_module.zg_ble.data.model.TDay;
import com.iwown.ble_module.zg_ble.data.model.bh_totalinfo;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.socks.library.KLog;

public class ZGSysncFilter {
    /* access modifiers changed from: private */
    public static int totalDays = 7;
    public static bh_totalinfo totalinfo = new bh_totalinfo();

    public static void initSyncCondition(final Context context, SevenDayStore sevenDayStore) {
        KLog.d("total Day Sync" + totalDays);
        ExecutorUtils.getExecutorService().execute(new Runnable() {
            public void run() {
                for (int i = 0; i <= ZGSysncFilter.totalDays; i++) {
                    ZGSysncFilter.syncData(context, i);
                }
                KLog.e("initSyncCondition 发送 最后一条接收指令");
                BleDataOrderHandler.getInstance().syncDataOver(context);
            }
        });
    }

    public static void syncTodayData(Context applicationContext, bh_totalinfo newTotalInfo) {
        if (totalDays != 0) {
            KLog.e("no Today");
            return;
        }
        if (!(newTotalInfo.getCalorie() == 0 || totalinfo.getCalorie() == newTotalInfo.getCalorie())) {
            KLog.d("syncTodayData Sport");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSport(totalDays)));
        }
        KLog.e(newTotalInfo.getSleepMinutes() + "  sleep " + totalinfo.getSleepMinutes());
        if (!(newTotalInfo.getSleepMinutes() == 0 || totalinfo.getSleepMinutes() == newTotalInfo.getSleepMinutes())) {
            KLog.d("syncTodayData Sleep");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSleep(totalDays)));
        }
        if (totalinfo.getLatestHeart() != newTotalInfo.getLatestHeart()) {
            KLog.d("syncTodayData Heart");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().readHeartData(totalDays)));
        }
        if (!(newTotalInfo.getStep() == 0 || totalinfo.getStep() == newTotalInfo.getStep())) {
            KLog.d("syncTodayData Step");
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailWalk(totalDays)));
        }
        KLog.e("发送 最后一条接收指令");
        BleDataOrderHandler.getInstance().syncDataOver(applicationContext);
        totalinfo = newTotalInfo;
    }

    public static void syncData(Context applicationContext, int day) {
        KLog.e("ZG syncinitDataInfo " + day);
        TDay day1 = null;
        if (day == 0) {
            day1 = TDay.Today;
        } else if (day == 1) {
            day1 = TDay.T_1;
        } else if (day == 2) {
            day1 = TDay.T_2;
        } else if (day == 3) {
            day1 = TDay.T_3;
        } else if (day == 4) {
            day1 = TDay.T_4;
        } else if (day == 5) {
            day1 = TDay.T_5;
        } else if (day == 6) {
            day1 = TDay.T_6;
        } else if (day == 7) {
            day1 = TDay.T_7;
        }
        if (day1 != null) {
            BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getTotalData(day1)));
        }
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSport(day)));
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailSleep(day)));
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().readHeartData(day)));
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDetailWalk(day)));
    }
}
