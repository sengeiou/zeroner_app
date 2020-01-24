package com.iwown.device_module.device_gps;

import java.util.List;

public class ZgGpsDay extends ZgGpsParent {
    private List<GpsDay> detail;
    private int totalDay;

    public static class GpsDay {
        private int day;
        private int month;
        private int position;
        private int year;

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

        public int getPosition() {
            return this.position;
        }

        public void setPosition(int position2) {
            this.position = position2;
        }
    }

    public int getTotalDay() {
        return this.totalDay;
    }

    public void setTotalDay(int totalDay2) {
        this.totalDay = totalDay2;
    }

    public List<GpsDay> getDetail() {
        return this.detail;
    }

    public void setDetail(List<GpsDay> detail2) {
        this.detail = detail2;
    }
}
