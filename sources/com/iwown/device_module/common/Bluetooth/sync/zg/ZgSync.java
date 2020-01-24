package com.iwown.device_module.common.Bluetooth.sync.zg;

import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.device_module.common.Bluetooth.receiver.zg.dao.AgpsStatue;
import com.iwown.device_module.common.Bluetooth.receiver.zg.handler.ZGBaseUtils;
import com.socks.library.KLog;

public class ZgSync {
    private static ZgSync instance;
    private long agpsTime = 0;

    private ZgSync() {
    }

    public static ZgSync getInstance() {
        if (instance == null) {
            instance = new ZgSync();
        }
        return instance;
    }

    public void syncDataInfo() {
        if (isAgps()) {
            KLog.e("no2855 正在写入agps，不能同步数据");
        } else {
            ZGBaseUtils.checkSync();
        }
    }

    public float getProgress() {
        return (float) ZGBaseUtils.progress_date;
    }

    public boolean isSync() {
        return ZGBaseUtils.isSysnc();
    }

    public boolean isAgps() {
        return System.currentTimeMillis() - this.agpsTime < 30000;
    }

    public void setAgpsTime() {
        this.agpsTime = System.currentTimeMillis();
        BleHandler.getInstance().setAgpsTime(this.agpsTime);
    }

    public void setAgpsTime2Zero() {
        this.agpsTime = 0;
        AgpsStatue.isAgps = false;
        BleHandler.getInstance().setAgpsTime(0);
    }
}
