package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class ScheduleBean {
    public int curSetableNum;
    public int maxSetableNum;
    public int perdaySetableNum;

    public int getCurSetableNum() {
        return this.curSetableNum;
    }

    public void setCurSetableNum(int curSetableNum2) {
        this.curSetableNum = curSetableNum2;
    }

    public int getMaxSetableNum() {
        return this.maxSetableNum;
    }

    public void setMaxSetableNum(int maxSetableNum2) {
        this.maxSetableNum = maxSetableNum2;
    }

    public int getPerdaySetableNum() {
        return this.perdaySetableNum;
    }

    public void setPerdaySetableNum(int perdaySetableNum2) {
        this.perdaySetableNum = perdaySetableNum2;
    }

    public ScheduleBean parseData(byte[] datas) {
        try {
            int curSetableNum2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            int maxSetableNum2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            int perdaySetableNum2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.curSetableNum = curSetableNum2;
            this.maxSetableNum = maxSetableNum2;
            this.perdaySetableNum = perdaySetableNum2;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
        return this;
    }
}
