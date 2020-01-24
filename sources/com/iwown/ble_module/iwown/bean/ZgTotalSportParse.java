package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.utils.JsonTool;
import java.util.Arrays;

public class ZgTotalSportParse {
    public static String pares(byte[] datas) {
        TotalSportData sportData = new TotalSportData();
        byte[] aStep = new byte[3];
        byte[] bStep1 = Arrays.copyOfRange(datas, 8, 10);
        byte[] bStep2 = Arrays.copyOfRange(datas, 18, 19);
        System.arraycopy(bStep1, 0, aStep, 0, bStep1.length);
        aStep[2] = bStep2[0];
        int year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        int month = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
        int day = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
        int step = ByteUtil.bytesToInt(aStep);
        float calories = (float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 12));
        float distance = (float) (ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 12)) * 100);
        sportData.setYear(year);
        sportData.setMonth(month);
        sportData.setDay(day);
        sportData.setSteps(step);
        sportData.setCalories(calories);
        sportData.setDistance(distance);
        return JsonTool.toJson(sportData);
    }
}
