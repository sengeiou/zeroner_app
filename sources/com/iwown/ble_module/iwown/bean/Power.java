package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
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
        this.power = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
    }
}
