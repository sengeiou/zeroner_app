package com.iwown.device_module.device_gps;

import java.util.List;

public class ZgGpsDetailData extends ZgGpsParent {
    private int day;
    private List<DetailData> detailData;
    private int month;
    private int year;

    public static class DetailData {
        private int hour;
        private double latitude;
        private double longitude;
        private int minute;
        private int second;
        private float speed;

        public int getHour() {
            return this.hour;
        }

        public void setHour(int hour2) {
            this.hour = hour2;
        }

        public int getMinute() {
            return this.minute;
        }

        public void setMinute(int minute2) {
            this.minute = minute2;
        }

        public int getSecond() {
            return this.second;
        }

        public void setSecond(int second2) {
            this.second = second2;
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

        public float getSpeed() {
            return this.speed;
        }

        public void setSpeed(float speed2) {
            this.speed = speed2;
        }
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

    public List<DetailData> getDetailData() {
        return this.detailData;
    }

    public void setDetailData(List<DetailData> detailData2) {
        this.detailData = detailData2;
    }
}
