package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.ble_module.utils.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GnssMinData {
    private String cmd;
    private int ctrl;
    private int day;
    private int freq;
    private int hour;
    private int index;
    private List<Gnss> mGnssMinDataList = new ArrayList();
    private int min;
    private int month;
    private int num;
    private int year;

    public class Gnss {
        private int altitude;
        private int gps_speed;
        private double latitude;
        private double longitude;

        public Gnss() {
        }

        public double getLongitude() {
            return this.longitude;
        }

        public void setLongitude(double longitude2) {
            this.longitude = longitude2;
        }

        public double getLatitude() {
            return this.latitude;
        }

        public void setLatitude(double latitude2) {
            this.latitude = latitude2;
        }

        public int getGps_speed() {
            return this.gps_speed;
        }

        public void setGps_speed(int gps_speed2) {
            this.gps_speed = gps_speed2;
        }

        public int getAltitude() {
            return this.altitude;
        }

        public void setAltitude(int altitude2) {
            this.altitude = altitude2;
        }
    }

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String cmd2) {
        this.cmd = cmd2;
    }

    public List<Gnss> getmGnssMinDataList() {
        return this.mGnssMinDataList;
    }

    public void setmGnssMinDataList(List<Gnss> mGnssMinDataList2) {
        this.mGnssMinDataList = mGnssMinDataList2;
    }

    public int getCtrl() {
        return this.ctrl;
    }

    public void setCtrl(int ctrl2) {
        this.ctrl = ctrl2;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year2) {
        this.year = year2;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month2) {
        this.month = month2;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day2) {
        this.day = day2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min2) {
        this.min = min2;
    }

    public int getFreq() {
        return this.freq;
    }

    public void setFreq(int freq2) {
        this.freq = freq2;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num2) {
        this.num = num2;
    }

    public GnssMinData parseData(byte[] datas) {
        try {
            int ctrl2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            int seq = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 7));
            int year2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 2000;
            int month2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1;
            int day2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10)) + 1;
            int hour2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
            int min2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
            int freq2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
            int num2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
            setCtrl(ctrl2);
            setIndex(seq);
            setYear(year2);
            setMonth(month2);
            setDay(day2);
            setHour(hour2);
            setMin(min2);
            setFreq(freq2);
            setNum(num2);
            setCmd(Util.bytesToString(datas, false));
            for (int i = 0; i < num2; i++) {
                Gnss gnss = new Gnss();
                int long_degree = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 14, (i * 14) + 15));
                int long_minute = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 15, (i * 14) + 16));
                int long_second = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 16, (i * 14) + 17));
                int long_preci = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 17, (i * 14) + 18));
                int long_direction = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 18, (i * 14) + 19)) == 0 ? 1 : -1;
                int lat_degree = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 19, (i * 14) + 20));
                int lat_minute = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 20, (i * 14) + 21));
                int lat_second = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 21, (i * 14) + 22));
                int lat_preci = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 22, (i * 14) + 23));
                int lat_direction = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 23, (i * 14) + 24)) == 0 ? 1 : -1;
                int gps_speed = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 24, (i * 14) + 26));
                int altitude = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 26, (i * 14) + 28));
                double latitude = (double) (((float) lat_direction) * (((float) lat_degree) + (((float) lat_minute) / 60.0f) + ((((float) lat_second) + (((float) lat_preci) / 100.0f)) / 3600.0f)));
                gnss.setLongitude((double) (((float) long_direction) * (((float) long_degree) + (((float) long_minute) / 60.0f) + ((((float) long_second) + (((float) long_preci) / 100.0f)) / 3600.0f))));
                gnss.setLatitude(latitude);
                gnss.setAltitude(altitude);
                gnss.setGps_speed(gps_speed);
                this.mGnssMinDataList.add(gnss);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
        return this;
    }
}
