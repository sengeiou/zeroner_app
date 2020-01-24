package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class Power {
    private int power;

    public Power() {
    }

    public Power(int keyCode) {
        this.power = this.power;
    }

    public int getPower() {
        return this.power;
    }

    public void setPower(int power2) {
        this.power = power2;
    }

    public void parseData(byte[] datas) {
        try {
            this.power = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
    }
}
