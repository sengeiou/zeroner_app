package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.utils.JsonTool;
import java.util.Arrays;

public class ZeronerDetailSportParse extends Zeroner28Base {
    private ZeronerDetailSportData data;

    public static String parse(byte[] datas) {
        ZeronerDetailSportParse sportParse = new ZeronerDetailSportParse();
        ZeronerDetailSportData sportData = new ZeronerDetailSportData();
        int index = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        int year = ByteUtil.byteToInt(datas[6]) + 2000;
        int month = ByteUtil.byteToInt(datas[7]) + 1;
        int day = ByteUtil.byteToInt(datas[8]) + 1;
        int sport_type = ByteUtil.byteToInt(datas[9]);
        int startMin = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 12));
        int endMin = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 14));
        float cal = ((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 18))) * 0.1f;
        sportData.setYear(year);
        sportData.setMonth(month);
        sportData.setDay(day);
        sportData.setStartMin(startMin);
        sportData.setEndMin(endMin);
        sportData.setCalories(cal);
        sportData.setIndex(index);
        sportData.setSport_type(sport_type);
        sportParse.setIndex(index);
        if (year - 2000 == 255 && month - 1 == 255 && day - 1 == 255) {
            sportParse.setLast(true);
        }
        if (sport_type == 1 || sport_type == 7) {
            sportData.setSteps(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 20)));
            sportData.setDistance(((float) Math.round(10.0f * (((float) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22))) * 0.1f))) / 10.0f);
            sportData.setActivity(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 16)));
        } else {
            sportData.setActivity(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 16)));
            sportData.setOtherCount(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 20)));
        }
        sportParse.setType(2);
        sportParse.setData(sportData);
        return JsonTool.toJson(sportParse);
    }

    public ZeronerDetailSportData getData() {
        return this.data;
    }

    public void setData(ZeronerDetailSportData data2) {
        this.data = data2;
    }
}
