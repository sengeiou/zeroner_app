package com.iwown.ble_module.iwown.receiver;

import android.util.Log;
import com.iwown.ble_module.iwown.bean.DataDetailHeart;
import com.iwown.ble_module.iwown.bean.DataHourHeart;
import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.utils.JsonTool;
import java.util.Arrays;

public class ZeronerHeartHandler {
    private static final String Tag = ZeronerHeartHandler.class.getName();

    public static String parseHour(byte[] datas) {
        int nowAdd53 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        int y = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)) + 2000;
        int m = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 1;
        int d = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1;
        int hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
        DataHourHeart dataHourHeart = new DataHourHeart();
        dataHourHeart.setNowAdd53(nowAdd53);
        dataHourHeart.setYear(y);
        dataHourHeart.setMonth(m);
        dataHourHeart.setDay(d);
        dataHourHeart.setHour(hour);
        if (y - 2000 == 255 && m - 1 == 255 && d - 1 == 255) {
            Log.d(Tag, "parseHour real-time data");
            dataHourHeart.setLast(true);
        }
        int[] time = new int[60];
        int i = 0;
        while (i < 60 && i + 10 <= datas.length) {
            time[i] = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, i + 10, i + 11));
            i++;
        }
        dataHourHeart.setRates(time);
        return JsonTool.toJson(dataHourHeart);
    }

    public static String parseDetail(byte[] datas) {
        int nowAdd51 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        int y = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7)) + 2000;
        int m = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 1;
        int d = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1;
        DataDetailHeart dataDetailHeart = new DataDetailHeart();
        dataDetailHeart.setYear(y);
        dataDetailHeart.setMonth(m);
        dataDetailHeart.setDay(d);
        dataDetailHeart.setNowAdd51(nowAdd51);
        if (y - 2000 == 255 && m - 1 == 255 && d - 1 == 255) {
            Log.d(Tag, "parseDetail real-time data");
            dataDetailHeart.setLast(true);
            return JsonTool.toJson(dataDetailHeart);
        }
        dataDetailHeart.setType(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10)));
        dataDetailHeart.setStart_time(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 12)));
        dataDetailHeart.setEnd_time(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 14)));
        dataDetailHeart.setEnergy(((float) Math.round((((double) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 16))) * 0.1d) * 10.0d)) / 10.0f);
        dataDetailHeart.setR1Time(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 18)));
        dataDetailHeart.setR2Time(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 20)));
        dataDetailHeart.setR3Time(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 22)));
        dataDetailHeart.setR4Time(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 22, 24)));
        dataDetailHeart.setR5Time(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 24, 26)));
        dataDetailHeart.setR1calorie(((float) Math.round((((double) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 26, 28))) * 0.1d) * 10.0d)) / 10.0f);
        dataDetailHeart.setR2calorie(((float) Math.round((((double) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 28, 30))) * 0.1d) * 10.0d)) / 10.0f);
        dataDetailHeart.setR3calorie(((float) Math.round((((double) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 30, 32))) * 0.1d) * 10.0d)) / 10.0f);
        dataDetailHeart.setR4calorie(((float) Math.round((((double) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 32, 34))) * 0.1d) * 10.0d)) / 10.0f);
        dataDetailHeart.setR5calorie(((float) Math.round((((double) ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 34, 36))) * 0.1d) * 10.0d)) / 10.0f);
        dataDetailHeart.setR1Hr(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 36, 37)));
        dataDetailHeart.setR2Hr(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 37, 38)));
        dataDetailHeart.setR3Hr(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 28, 39)));
        dataDetailHeart.setR4Hr(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 39, 40)));
        dataDetailHeart.setR5Hr(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 40, 41)));
        return JsonTool.toJson(dataDetailHeart);
    }
}
