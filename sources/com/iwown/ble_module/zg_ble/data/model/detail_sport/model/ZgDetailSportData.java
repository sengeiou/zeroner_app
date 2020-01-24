package com.iwown.ble_module.zg_ble.data.model.detail_sport.model;

import java.util.ArrayList;
import java.util.List;

public class ZgDetailSportData {
    private int count;
    private int day;
    private int month;
    private List<Sport> sports;
    private int year;

    public static class Sport {
        private float calories;
        private float distance;
        private int endMin;
        private List<Integer> heart = new ArrayList();
        private int heartAvg;
        private int heartMax;
        private int spmAvg;
        private int spmMax;
        private int sportType;
        private int startMin;
        private int steps;
        private int totalMin;

        public int getSportType() {
            return this.sportType;
        }

        public void setSportType(int sportType2) {
            this.sportType = sportType2;
        }

        public List<Integer> getHeart() {
            return this.heart;
        }

        public void setHeart(List<Integer> heart2) {
            this.heart = heart2;
        }

        public int getTotalMin() {
            return this.totalMin;
        }

        public void setTotalMin(int totalMin2) {
            this.totalMin = totalMin2;
        }

        public int getStartMin() {
            return this.startMin;
        }

        public void setStartMin(int startMin2) {
            this.startMin = startMin2;
        }

        public int getEndMin() {
            return this.endMin;
        }

        public void setEndMin(int endMin2) {
            this.endMin = endMin2;
        }

        public int getSteps() {
            return this.steps;
        }

        public void setSteps(int steps2) {
            this.steps = steps2;
        }

        public float getDistance() {
            return this.distance;
        }

        public void setDistance(float distance2) {
            this.distance = distance2;
        }

        public float getCalories() {
            return this.calories;
        }

        public void setCalories(float calories2) {
            this.calories = calories2;
        }

        public int getSpmMax() {
            return this.spmMax;
        }

        public void setSpmMax(int spmMax2) {
            this.spmMax = spmMax2;
        }

        public int getSpmAvg() {
            return this.spmAvg;
        }

        public void setSpmAvg(int spmAvg2) {
            this.spmAvg = spmAvg2;
        }

        public int getHeartMax() {
            return this.heartMax;
        }

        public void setHeartMax(int heartMax2) {
            this.heartMax = heartMax2;
        }

        public int getHeartAvg() {
            return this.heartAvg;
        }

        public void setHeartAvg(int heartAvg2) {
            this.heartAvg = heartAvg2;
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

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public List<Sport> getSports() {
        return this.sports;
    }

    public void setSports(List<Sport> sports2) {
        this.sports = sports2;
    }
}
