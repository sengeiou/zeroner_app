package com.iwown.ble_module.zg_ble.data.model.detail_sport;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.util.Arrays;

public class Zeroner28Base {
    private int index;
    private boolean last;
    int type = 0;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public boolean isLast() {
        return this.last;
    }

    public void setLast(boolean last2) {
        this.last = last2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public static String parseBase(byte[] datas) {
        Zeroner28Base zeroner28Base = new Zeroner28Base();
        int year = ByteUtil.byteToInt(datas[6]) + 2000;
        int month = ByteUtil.byteToInt(datas[7]) + 1;
        int day = ByteUtil.byteToInt(datas[8]) + 1;
        zeroner28Base.setIndex(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6)));
        if (year - 2000 == 255 && month - 1 == 255 && day - 1 == 255) {
            zeroner28Base.setLast(true);
        }
        return JsonTool.toJson(zeroner28Base);
    }
}
