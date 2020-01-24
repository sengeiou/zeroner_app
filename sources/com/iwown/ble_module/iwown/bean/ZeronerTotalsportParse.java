package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.DateUtil;
import java.util.Arrays;

public class ZeronerTotalsportParse {
    public static String parse(byte[] datas) {
        TotalSportData sportData = new TotalSportData();
        int year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)) + 2000;
        int month = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)) + 1;
        int day = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)) + 1;
        int sport_type = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
        sportData.setSport_type(sport_type);
        if (year - 2000 == 255 && month - 1 == 255 && day - 1 == 255) {
            DateUtil dateUtil = new DateUtil();
            sportData.setYear(dateUtil.getYear());
            sportData.setMonth(dateUtil.getMonth());
            sportData.setDay(dateUtil.getDay());
            sportData.setLast(true);
            sportData.setCalories(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 12))) * 0.1f);
            sportData.setSteps(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 16)));
            sportData.setDistance(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 20))) * 0.1f);
        } else if (sport_type == 1) {
            sportData.setYear(year);
            sportData.setMonth(month);
            sportData.setDay(day);
            sportData.setCalories(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 12))) * 0.1f);
            sportData.setSteps(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 16)));
            sportData.setDistance(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 20))) * 0.1f);
        }
        return JsonTool.toJson(sportData);
    }
}
