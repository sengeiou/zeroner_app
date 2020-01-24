package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
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
        this.keyCode = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
    }
}
