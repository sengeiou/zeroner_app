package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class KeyModel {
    private int keyCode;

    public KeyModel() {
    }

    public KeyModel(int keyCode2) {
        this.keyCode = keyCode2;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public void setKeyCode(int keyCode2) {
        this.keyCode = keyCode2;
    }

    public void parseData(byte[] datas) {
        try {
            this.keyCode = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
    }
}
