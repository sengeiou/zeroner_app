package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class IWHeartWarning {
    private int enable;
    private int interval;
    private int threshold_high;
    private int threshold_low;
    private int time_out;

    public int getEnable() {
        return this.enable;
    }

    public void setEnable(int enable2) {
        this.enable = enable2;
    }

    public int getThreshold_high() {
        return this.threshold_high;
    }

    public void setThreshold_high(int threshold_high2) {
        this.threshold_high = threshold_high2;
    }

    public int getThreshold_low() {
        return this.threshold_low;
    }

    public void setThreshold_low(int threshold_low2) {
        this.threshold_low = threshold_low2;
    }

    public int getTime_out() {
        return this.time_out;
    }

    public void setTime_out(int time_out2) {
        this.time_out = time_out2;
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval2) {
        this.interval = interval2;
    }

    public void parseData(byte[] datas) {
        try {
            setEnable(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
            setThreshold_high(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)));
            setThreshold_low(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)));
            setTime_out(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)));
            setInterval(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
    }
}
