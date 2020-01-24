package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class AlarmClockBean {
    public int conf;
    public String data;
    public int hour;
    public int id;
    public int len;
    public int mintue;
    public int type;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getConf() {
        return this.conf;
    }

    public void setConf(int conf2) {
        this.conf = conf2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMintue() {
        return this.mintue;
    }

    public void setMintue(int mintue2) {
        this.mintue = mintue2;
    }

    public int getLen() {
        return this.len;
    }

    public void setLen(int len2) {
        this.len = len2;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data2) {
        this.data = data2;
    }

    public AlarmClockBean parseData(byte[] datas) {
        try {
            this.id = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            this.type = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            this.conf = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            this.hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
            this.mintue = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
            this.len = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
            this.data = ByteUtil.bytesToString(Arrays.copyOfRange(datas, 9, datas.length));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
        return this;
    }
}
