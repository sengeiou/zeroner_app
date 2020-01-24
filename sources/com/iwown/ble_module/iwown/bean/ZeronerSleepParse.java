package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.utils.JsonTool;
import java.util.Arrays;

public class ZeronerSleepParse extends Zeroner28Base {
    private ZeronerSleepData data;

    public static String parse(byte[] datas) {
        ZeronerSleepParse sleepParse = new ZeronerSleepParse();
        ZeronerSleepData mData = new ZeronerSleepData();
        int index = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        int year = ByteUtil.byteToInt(datas[6]) + 2000;
        int month = ByteUtil.byteToInt(datas[7]) + 1;
        int day = ByteUtil.byteToInt(datas[8]) + 1;
        if (year - 2000 == 255 && month - 1 == 255 && day - 1 == 255) {
            sleepParse.setLast(true);
        }
        mData.setIndex(index);
        mData.setYear(year);
        mData.setMonth(month);
        mData.setDay(day);
        mData.setStart(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 12)));
        mData.setEnd(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 14)));
        mData.setTimes(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 16)));
        mData.setType(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 17)));
        if (datas.length >= 22) {
            int mode = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 17, 18));
            if (mode == 2 || mode == 5) {
                mData.setMode(mode);
                mData.setSleep_enter(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 20)));
                mData.setSleep_exit(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22)));
            }
        }
        sleepParse.setIndex(index);
        sleepParse.setType(1);
        sleepParse.setData(mData);
        return JsonTool.toJson(sleepParse);
    }

    public ZeronerSleepData getData() {
        return this.data;
    }

    public void setData(ZeronerSleepData data2) {
        this.data = data2;
    }
}
