package com.iwown.ble_module.zg_ble.data.model;

import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.util.Arrays;

public class BleSpeed extends Result {
    private int speed_mode = -1;

    public int getSpeed_mode() {
        return this.speed_mode;
    }

    public void setSpeed_mode(int speed_mode2) {
        this.speed_mode = speed_mode2;
    }

    public void parseData(byte[] datas) {
        this.speed_mode = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
    }
}
