package com.iwown.ble_module.zg_ble.data.model.impl;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.detail_sport.model.ZgDetailWalkData;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.utils.Util;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZgDetailWalkParse {
    public static List<String> dataStr = new ArrayList();
    public static List<Byte> mData = new ArrayList();

    public static void addList(byte[] datas) {
        byte[] mDa;
        String msg = Util.bytesToString(datas);
        if (!dataStr.contains(msg)) {
            dataStr.add(msg);
            if (datas[2] == 1) {
                mData.clear();
                dataStr.clear();
                mDa = datas;
            } else {
                mDa = Arrays.copyOfRange(datas, 4, datas.length);
            }
            if (mDa != null) {
                for (byte valueOf : mDa) {
                    mData.add(Byte.valueOf(valueOf));
                }
            }
        }
    }

    public static String parse() {
        if (mData.size() < 8) {
            KLog.e("no2855", "解析步数数据过短,解析异常" + mData.size());
            return null;
        }
        byte[] datas = new byte[mData.size()];
        for (int i = 0; i < mData.size(); i++) {
            datas[i] = ((Byte) mData.get(i)).byteValue();
        }
        KLog.d("no2855", "zg步数：" + Util.bytesToString(datas));
        ZgDetailWalkData walkData = new ZgDetailWalkData();
        int year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        int month = ByteUtil.byteToInt(datas[6]);
        int day = ByteUtil.byteToInt(datas[7]);
        List<Integer> list = new ArrayList<>();
        for (int i2 = 8; i2 < datas.length; i2++) {
            list.add(Integer.valueOf(ByteUtil.byteToInt(datas[i2])));
        }
        if (list.size() > 1440) {
            for (int i3 = list.size(); i3 > 1440; i3--) {
                list.remove(list.size() - 1);
            }
        }
        if (list.size() < 1440) {
            int count = 1440 - list.size();
            for (int i4 = 0; i4 < count; i4++) {
                list.add(Integer.valueOf(0));
            }
        }
        walkData.setYear(year);
        walkData.setMonth(month);
        walkData.setDay(day);
        walkData.setData(list);
        mData.clear();
        dataStr.clear();
        return JsonTool.toJson(walkData);
    }
}
