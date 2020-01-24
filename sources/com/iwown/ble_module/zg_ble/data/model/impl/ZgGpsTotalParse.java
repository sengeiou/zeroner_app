package com.iwown.ble_module.zg_ble.data.model.impl;

import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.ZgGpsStatue;
import com.iwown.ble_module.zg_ble.data.model.ZgGpsTotalData;
import com.iwown.ble_module.zg_ble.data.model.ZgGpsTotalData.GpsDay;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.socks.library.KLog;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ZgGpsTotalParse {
    private static ZgGpsTotalParse instance = null;
    private List<Byte> mData = new LinkedList();

    public static synchronized ZgGpsTotalParse getInstance() {
        ZgGpsTotalParse zgGpsTotalParse;
        synchronized (ZgGpsTotalParse.class) {
            if (instance == null) {
                instance = new ZgGpsTotalParse();
            }
            zgGpsTotalParse = instance;
        }
        return zgGpsTotalParse;
    }

    private ZgGpsTotalParse() {
    }

    public void clear() {
        instance = null;
        this.mData.clear();
    }

    public void addList(byte[] data) {
        byte[] mDa;
        if (data[2] == 1) {
            this.mData.clear();
            mDa = data;
        } else {
            mDa = Arrays.copyOfRange(data, 4, data.length);
        }
        for (byte valueOf : mDa) {
            this.mData.add(Byte.valueOf(valueOf));
        }
    }

    public boolean isOver(byte over) {
        if (over == -124) {
            return true;
        }
        return false;
    }

    public String parse(byte[] data) {
        addList(data);
        if (this.mData.size() < 10) {
            KLog.e("no2855", "解析gps总数据数据过短,解析异常" + this.mData.size());
            return null;
        }
        byte[] datas = new byte[this.mData.size()];
        int count = 0;
        for (Byte mDatum : this.mData) {
            datas[count] = mDatum.byteValue();
            count++;
        }
        int totalDay = ByteUtil.byteToInt(datas[8]);
        int length = datas.length;
        int lastPosition = ByteUtil.byteToInt(datas[9]);
        List<GpsDay> dataList = new LinkedList<>();
        int number = 0;
        int i = 10;
        while (i < length && length - i >= 7) {
            int position = lastPosition - number;
            if (position < 1) {
                position += 7;
            }
            number++;
            GpsDay gpsDay = new GpsDay();
            gpsDay.setYear(ByteUtil.bytesToInt(new byte[]{datas[i], datas[i + 1]}));
            gpsDay.setMonth(ByteUtil.byteToInt(datas[i + 2]));
            gpsDay.setDay(ByteUtil.byteToInt(datas[i + 3]));
            gpsDay.setPosition(position);
            if (!(gpsDay.getYear() == 0 || gpsDay.getMonth() == 0 || gpsDay.getDay() == 0)) {
                dataList.add(gpsDay);
            }
            i += 7;
        }
        ZgGpsTotalData totalData = new ZgGpsTotalData();
        totalData.setTotalDay(totalDay);
        totalData.setDetail(dataList);
        String result = JsonTool.toJson(totalData);
        KLog.e("no2855 Gps总数据解析: " + result);
        return result;
    }

    public String parseGpsStatue(byte[] data) {
        ZgGpsStatue zgGpsStatue = new ZgGpsStatue();
        if (data != null && data.length >= 5) {
            zgGpsStatue.setStatue(ByteUtil.byteToInt(data[4]));
        }
        return JsonTool.toJson(zgGpsStatue);
    }
}
