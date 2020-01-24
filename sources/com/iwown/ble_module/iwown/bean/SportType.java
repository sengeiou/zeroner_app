package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import java.util.Arrays;

public class SportType {
    private int maxSuport;
    private int[] types;

    public int getMaxSuport() {
        return this.maxSuport;
    }

    public void setMaxSuport(int maxSuport2) {
        this.maxSuport = maxSuport2;
    }

    public int[] getTypes() {
        return this.types;
    }

    public void setTypes(int[] types2) {
        this.types = types2;
    }

    public void parseData(byte[] datas) {
        int[] types2 = new int[(datas[3] - 1)];
        for (int i = 0; i < types2.length; i++) {
            types2[i] = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 1) + 5, (i * 1) + 6));
        }
        setMaxSuport(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
        setTypes(types2);
    }
}
