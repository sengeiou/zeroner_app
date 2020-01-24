package com.iwown.ble_module.zg_ble.data.model.impl;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.ZGHeartData;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.socks.library.KLog;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZGHeartHandler {
    private static Map<Byte, ZGHeartData> maps = new HashMap();

    public static String parseBaseHeart(byte[] datas) {
        ZGHeartData zgHeartData = (ZGHeartData) maps.get(Byte.valueOf(datas[1]));
        if (zgHeartData == null) {
            zgHeartData = new ZGHeartData();
            maps.put(Byte.valueOf(datas[1]), zgHeartData);
        }
        int index = 4;
        if (datas[2] == 1) {
            zgHeartData.year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
            int index2 = 4 + 2;
            zgHeartData.month = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, index2, 7));
            int index3 = index2 + 1;
            zgHeartData.day = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, index3, 8));
            int index4 = index3 + 1;
            int bytesToInt = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, index4, 9));
            int index5 = index4 + 1;
            zgHeartData.highestHeart = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, index5, 10));
            int index6 = index5 + 1;
            zgHeartData.lowHeart = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, index6, 11));
            int index7 = index6 + 1;
            zgHeartData.averageHeart = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, index7, 12));
            index = index7 + 1;
        }
        int i = index;
        while (index < datas.length && i + 1 <= datas.length) {
            zgHeartData.addStaticHeart(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, index, index + 1)));
            index++;
            i++;
        }
        if (datas[2] >= 0 && zgHeartData.index < 144) {
            return null;
        }
        maps.remove(Byte.valueOf(datas[1]));
        return JsonTool.toJson(zgHeartData);
    }

    public static byte readHeart(int marginSize) {
        if (marginSize >= 0 && marginSize <= 7) {
            return Byte.parseByte(marginSize + "2", 16);
        }
        KLog.e("size must 0<< <<7");
        return 2;
    }
}
