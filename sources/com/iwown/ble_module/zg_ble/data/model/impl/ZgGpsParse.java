package com.iwown.ble_module.zg_ble.data.model.impl;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.ZgGpsData;
import com.iwown.ble_module.zg_ble.data.model.ZgGpsData.DetailData;
import com.iwown.ble_module.zg_ble.data.model.ZgGpsDayOver;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.utils.Util;
import com.socks.library.KLog;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ZgGpsParse {
    private static ZgGpsParse instance = null;
    private List<String> dataStr = new LinkedList();
    public int day;
    private int lastC = 0;
    private List<Byte> mData = new LinkedList();
    public int month;
    public int year;

    public static synchronized ZgGpsParse getInstance() {
        ZgGpsParse zgGpsParse;
        synchronized (ZgGpsParse.class) {
            if (instance == null) {
                instance = new ZgGpsParse();
            }
            zgGpsParse = instance;
        }
        return zgGpsParse;
    }

    private ZgGpsParse() {
    }

    public void clear() {
        this.mData.clear();
        this.dataStr.clear();
        this.lastC = 0;
    }

    public void addList(byte[] data) {
        byte[] mDa;
        String msg = Util.bytesToString(data);
        if (!this.dataStr.contains(msg)) {
            this.dataStr.add(msg);
            this.lastC = data[2];
            if (data[2] == 1) {
                this.mData.clear();
                this.dataStr.clear();
                this.dataStr.add(msg);
                mDa = data;
            } else {
                mDa = Arrays.copyOfRange(data, 4, data.length);
            }
            for (byte valueOf : mDa) {
                this.mData.add(Byte.valueOf(valueOf));
            }
        }
    }

    public boolean isOver(byte over) {
        if (over == -113) {
            return true;
        }
        return false;
    }

    public String parse(byte[] data) {
        addList(data);
        if (this.mData.size() < 8) {
            KLog.e("no2855", "解析gps数据过短,解析异常" + this.mData.size());
            clear();
            return null;
        }
        byte[] datas = new byte[this.mData.size()];
        int count = 0;
        for (Byte mDatum : this.mData) {
            datas[count] = mDatum.byteValue();
            count++;
        }
        int year2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 6));
        int month2 = ByteUtil.byteToInt(datas[6]);
        int day2 = ByteUtil.byteToInt(datas[7]);
        if (year2 == 0 || month2 == 0 || day2 == 0) {
            clear();
            return null;
        }
        if (!(month2 == 255 || day2 == 255)) {
            this.year = year2;
            this.month = month2;
            this.day = day2;
        }
        ZgGpsData zgGpsData = new ZgGpsData();
        zgGpsData.setYear(year2);
        zgGpsData.setMonth(month2);
        zgGpsData.setDay(day2);
        int length = datas.length;
        int number = 8;
        List<DetailData> dataList = new LinkedList<>();
        for (int i = 8; i < length && length - number >= 15; i += 15) {
            number += 15;
            DetailData detailData = new DetailData();
            detailData.setHour(ByteUtil.byteToInt(datas[i]));
            detailData.setMinute(ByteUtil.byteToInt(datas[i + 1]));
            detailData.setSecond(ByteUtil.byteToInt(datas[i + 2]));
            int long_degree = ByteUtil.byteToInt(datas[i + 3]);
            int long_minute = ByteUtil.byteToInt(datas[i + 4]);
            int long_second = ByteUtil.byteToInt(datas[i + 5]);
            int long_preci = ByteUtil.byteToInt(datas[i + 6]);
            int long_direction = ByteUtil.byteToInt(datas[i + 7]) == 0 ? 1 : -1;
            int lat_degree = ByteUtil.byteToInt(datas[i + 8]);
            int lat_minute = ByteUtil.byteToInt(datas[i + 9]);
            int lat_second = ByteUtil.byteToInt(datas[i + 10]);
            int lat_preci = ByteUtil.byteToInt(datas[i + 11]);
            int lat_direction = ByteUtil.byteToInt(datas[i + 12]) == 0 ? 1 : -1;
            int speedInt = ByteUtil.byteToInt(datas[i + 13]);
            int speedPoint = ByteUtil.byteToInt(datas[i + 14]);
            double longitude = (double) (((float) long_direction) * (((float) long_degree) + (((float) long_minute) / 60.0f) + ((((float) long_second) + (((float) long_preci) / 100.0f)) / 3600.0f)));
            double latitude = (double) (((float) lat_direction) * (((float) lat_degree) + (((float) lat_minute) / 60.0f) + ((((float) lat_second) + (((float) lat_preci) / 100.0f)) / 3600.0f)));
            detailData.setSpeed((float) (((double) speedInt) + (((double) speedPoint) / Math.pow(10.0d, (double) String.valueOf(speedPoint).length()))));
            detailData.setLongitude(longitude);
            detailData.setLatitude(latitude);
            dataList.add(detailData);
        }
        zgGpsData.setDetailData(dataList);
        String json = JsonTool.toJson(zgGpsData);
        clear();
        return json;
    }

    public String parseOver() {
        ZgGpsDayOver dayOver = new ZgGpsDayOver();
        dayOver.setYear(this.year);
        dayOver.setMonth(this.month);
        dayOver.setDay(this.day);
        String result = JsonTool.toJson(dayOver);
        KLog.d("no2855 Gps结束解析: " + result);
        this.year = 0;
        this.month = 0;
        this.day = 0;
        instance = null;
        return result;
    }
}
