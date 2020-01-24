package com.iwown.ble_module.zg_ble.test;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.data.DateUtil;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.socks.library.KLog;

public class ZGBaseUtils {
    public static int Heart = 100;
    public static int Over = 700;
    public static int Sleep = 300;
    public static int Sport = 200;
    private static final String TAG = ZGBaseUtils.class.getName();
    public static int Walking = 400;
    public static int Walking_2_Sport = ServiceErrorCode.YOU_AND_ME_IS_FRIEND;
    public static int alarm_mode1 = -1;
    public static int alarm_number1 = -1;
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    public static int progress_date = 0;

    public static void postSyncDataEventZg(int data_type, int year, int month, int day) {
        KLog.e("postSyncDataEventZg " + data_type + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + year + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + month + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + day);
        if (year == 0 && month == 0 && day == 0) {
            mHandler.removeCallbacksAndMessages(null);
            setProgress_date(0);
            if (data_type == Over) {
            }
            if (data_type == 0) {
            }
            return;
        }
        new DateUtil(year, month, day);
        if (data_type == Walking_2_Sport) {
        }
        KLog.e("handler 操作处理");
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                KLog.e("28S未更新 当做同步完成");
                ZGBaseUtils.postSyncDataEventZg(0, 0, 0, 0);
            }
        }, 28000);
    }

    private static void setProgress_date(int progress_date2) {
        progress_date = progress_date2;
    }

    public static void clearExtraAlarmSchedule() {
        KLog.e("clearExtraAlarmSchedule ok ");
    }

    public static void syncinitDataInfo(Context applicationContext) {
        BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().getDataDate()));
    }
}
