package com.iwown.data_link.sport_data;

import java.util.List;

public class SportSwimCode extends ReturnCode {
    private List<SwimCode> Data;

    public static class SwimCode {
        private float Calorie;
        private String Data_from;
        private float Distance;
        private int Duration;
        private String End_time;
        private String Start_time;
        private int Stroke_times;
        private String Swim_detail_data;
        private int Swim_laps;
        private long Uid;

        public long getUid() {
            return this.Uid;
        }

        public void setUid(long uid) {
            this.Uid = uid;
        }

        public String getData_from() {
            return this.Data_from;
        }

        public void setData_from(String data_from) {
            this.Data_from = data_from;
        }

        public String getStart_time() {
            return this.Start_time;
        }

        public void setStart_time(String start_time) {
            this.Start_time = start_time;
        }

        public String getEnd_time() {
            return this.End_time;
        }

        public void setEnd_time(String end_time) {
            this.End_time = end_time;
        }

        public float getDistance() {
            return this.Distance;
        }

        public void setDistance(float distance) {
            this.Distance = distance;
        }

        public float getCalorie() {
            return this.Calorie;
        }

        public void setCalorie(float calorie) {
            this.Calorie = calorie;
        }

        public int getDuration() {
            return this.Duration;
        }

        public void setDuration(int duration) {
            this.Duration = duration;
        }

        public int getStroke_times() {
            return this.Stroke_times;
        }

        public void setStroke_times(int stroke_times) {
            this.Stroke_times = stroke_times;
        }

        public int getSwim_laps() {
            return this.Swim_laps;
        }

        public void setSwim_laps(int swim_laps) {
            this.Swim_laps = swim_laps;
        }

        public String getSwim_detail_data() {
            return this.Swim_detail_data;
        }

        public void setSwim_detail_data(String swim_detail_data) {
            this.Swim_detail_data = swim_detail_data;
        }
    }

    public List<SwimCode> getData() {
        return this.Data;
    }

    public void setData(List<SwimCode> data) {
        this.Data = data;
    }
}
