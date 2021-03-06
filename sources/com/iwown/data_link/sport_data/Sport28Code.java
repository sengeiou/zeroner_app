package com.iwown.data_link.sport_data;

import java.util.List;

public class Sport28Code extends ReturnCode {
    private List<SportCode> Data;

    public static class SportCode {
        private float Calorie;
        private String Data_from;
        private float Distance;
        private int Done_times;
        private int Duration;
        private String End_time;
        private int Sport_type;
        private String Start_time;
        private int Step;
        private long Uid;

        public long getUid() {
            return this.Uid;
        }

        public void setUid(long uid) {
            this.Uid = uid;
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

        public String getData_from() {
            return this.Data_from;
        }

        public void setData_from(String data_from) {
            this.Data_from = data_from;
        }

        public int getSport_type() {
            return this.Sport_type;
        }

        public void setSport_type(int sport_type) {
            this.Sport_type = sport_type;
        }

        public int getStep() {
            return this.Step;
        }

        public void setStep(int step) {
            this.Step = step;
        }

        public float getDistance() {
            return this.Distance;
        }

        public void setDistance(float distance) {
            this.Distance = distance;
        }

        public int getDuration() {
            return this.Duration;
        }

        public void setDuration(int duration) {
            this.Duration = duration;
        }

        public int getDone_times() {
            return this.Done_times;
        }

        public void setDone_times(int done_times) {
            this.Done_times = done_times;
        }

        public float getCalorie() {
            return this.Calorie;
        }

        public void setCalorie(float calorie) {
            this.Calorie = calorie;
        }
    }

    public List<SportCode> getData() {
        return this.Data;
    }

    public void setData(List<SportCode> data) {
        this.Data = data;
    }
}
