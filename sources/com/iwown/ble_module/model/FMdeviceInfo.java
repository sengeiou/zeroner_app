package com.iwown.ble_module.model;

import com.alibaba.android.arouter.utils.Consts;
import com.google.common.primitives.UnsignedBytes;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.ble_module.utils.Util;
import java.util.Arrays;

public class FMdeviceInfo {
    private String bleAddr;
    private long intSwversion;
    private String model = "";
    private int oadmode;
    private String swversion;

    public String getBleAddr() {
        return this.bleAddr;
    }

    public void setBleAddr(String bleAddr2) {
        this.bleAddr = bleAddr2;
    }

    public long getIntSwversion() {
        return this.intSwversion;
    }

    public void setIntSwversion(long intSwversion2) {
        this.intSwversion = intSwversion2;
    }

    public String getSwversion() {
        return this.swversion;
    }

    public void setSwversion(String swversion2) {
        this.swversion = swversion2;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model2) {
        this.model = model2;
    }

    public int getOadmode() {
        return this.oadmode;
    }

    public void setOadmode(int oadmode2) {
        this.oadmode = oadmode2;
    }

    public void parseData(byte[] datas) {
        String[] version = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        try {
            setModel(Util.ascii2String(Arrays.copyOfRange(datas, 6, 10)));
            setOadmode((datas[10] * UnsignedBytes.MAX_VALUE) + datas[11]);
            setSwversion(datas[12] + Consts.DOT + changeVersion(datas[13], version) + Consts.DOT + datas[14] + Consts.DOT + datas[15]);
            setIntSwversion((long) ByteUtil.bytesToIntForVersion(Arrays.copyOfRange(datas, 12, 16)));
            setBleAddr(ByteUtil.byteArrayToString(Arrays.copyOfRange(datas, 16, 22)));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
    }

    private String changeVersion(int type, String[] version) {
        if (type < 10 || type > 36) {
            return type + "";
        }
        return version[type - 10];
    }
}
