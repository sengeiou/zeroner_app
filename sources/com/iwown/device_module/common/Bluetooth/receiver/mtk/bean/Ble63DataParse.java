package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import com.iwown.ble_module.utils.ByteUtil;
import java.util.Arrays;

public class Ble63DataParse {
    private int altitude;
    private int day;
    private int gps_speed;
    private int hour;
    private int latitude_degree;
    private int latitude_direction;
    private int latitude_minute;
    private int latitude_preci;
    private int latitude_second;
    private int longitude_degree;
    private int longitude_direction;
    private int longitude_minute;
    private int longitude_preci;
    private int longitude_second;
    private int min;
    private int month;
    private int sec;
    private int year;

    public static Ble63DataParse parse(byte[] datas) {
        Ble63DataParse ble63DataParse = new Ble63DataParse();
        int year2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
        int month2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
        int day2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
        int hour2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
        int min2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
        int sec2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
        int longitude_degree2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
        int longitude_minute2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
        int longitude_second2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
        int longitude_direction2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
        int longitude_preci2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 14, 15));
        int latitude_degree2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 15, 16));
        int latitude_minute2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 16, 17));
        int latitude_second2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 17, 18));
        int latitude_direction2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 18, 19));
        int latitude_preci2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 19, 20));
        int gps_speed2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 20, 21));
        int altitude2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 21, 23));
        ble63DataParse.setYear(year2);
        ble63DataParse.setMonth(month2);
        ble63DataParse.setDay(day2);
        ble63DataParse.setHour(hour2);
        ble63DataParse.setMin(min2);
        ble63DataParse.setSec(sec2);
        ble63DataParse.setLongitude_degree(longitude_degree2);
        ble63DataParse.setLongitude_minute(longitude_minute2);
        ble63DataParse.setLongitude_second(longitude_second2);
        ble63DataParse.setLongitude_direction(longitude_direction2);
        ble63DataParse.setLongitude_preci(longitude_preci2);
        ble63DataParse.setLatitude_degree(latitude_degree2);
        ble63DataParse.setLatitude_minute(latitude_minute2);
        ble63DataParse.setLatitude_second(latitude_second2);
        ble63DataParse.setLatitude_direction(latitude_direction2);
        ble63DataParse.setLatitude_preci(latitude_preci2);
        ble63DataParse.setGps_speed(gps_speed2);
        ble63DataParse.setAltitude(altitude2);
        return ble63DataParse;
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

    public int getSec() {
        return this.sec;
    }

    public void setSec(int sec2) {
        this.sec = sec2;
    }

    public int getLongitude_degree() {
        return this.longitude_degree;
    }

    public void setLongitude_degree(int longitude_degree2) {
        this.longitude_degree = longitude_degree2;
    }

    public int getLongitude_minute() {
        return this.longitude_minute;
    }

    public void setLongitude_minute(int longitude_minute2) {
        this.longitude_minute = longitude_minute2;
    }

    public int getLongitude_second() {
        return this.longitude_second;
    }

    public void setLongitude_second(int longitude_second2) {
        this.longitude_second = longitude_second2;
    }

    public int getLongitude_direction() {
        return this.longitude_direction;
    }

    public void setLongitude_direction(int longitude_direction2) {
        this.longitude_direction = longitude_direction2;
    }

    public int getLatitude_degree() {
        return this.latitude_degree;
    }

    public void setLatitude_degree(int latitude_degree2) {
        this.latitude_degree = latitude_degree2;
    }

    public int getLatitude_minute() {
        return this.latitude_minute;
    }

    public void setLatitude_minute(int latitude_minute2) {
        this.latitude_minute = latitude_minute2;
    }

    public int getLatitude_second() {
        return this.latitude_second;
    }

    public void setLatitude_second(int latitude_second2) {
        this.latitude_second = latitude_second2;
    }

    public int getLatitude_direction() {
        return this.latitude_direction;
    }

    public void setLatitude_direction(int latitude_direction2) {
        this.latitude_direction = latitude_direction2;
    }

    public int getAltitude() {
        return this.altitude;
    }

    public void setAltitude(int altitude2) {
        this.altitude = altitude2;
    }

    public int getLongitude_preci() {
        return this.longitude_preci;
    }

    public void setLongitude_preci(int longitude_preci2) {
        this.longitude_preci = longitude_preci2;
    }

    public int getLatitude_preci() {
        return this.latitude_preci;
    }

    public void setLatitude_preci(int latitude_preci2) {
        this.latitude_preci = latitude_preci2;
    }

    public int getGps_speed() {
        return this.gps_speed;
    }

    public void setGps_speed(int gps_speed2) {
        this.gps_speed = gps_speed2;
    }

    public String toString() {
        return "Ble63DataParse{year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", min=" + this.min + ", sec=" + this.sec + ", longitude_degree=" + this.longitude_degree + ", longitude_minute=" + this.longitude_minute + ", longitude_second=" + this.longitude_second + ", longitude_direction=" + this.longitude_direction + ", latitude_degree=" + this.latitude_degree + ", latitude_minute=" + this.latitude_minute + ", latitude_second=" + this.latitude_second + ", latitude_direction=" + this.latitude_direction + ", altitude=" + this.altitude + '}';
    }
}
