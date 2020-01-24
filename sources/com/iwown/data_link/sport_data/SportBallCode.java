package com.iwown.data_link.sport_data;

import java.util.List;

public class SportBallCode extends ReturnCode {
    private List<BallCode> Data;

    public static class BallCode {
        private float Calorie;
        private String Data_from;
        private int Duration;
        private String End_time;
        private String Hr_data_url;
        private int Sport_type;
        private String Start_time;
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

        public int getSport_type() {
            return this.Sport_type;
        }

        public void setSport_type(int sport_type) {
            this.Sport_type = sport_type;
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

        public String getHr_data_url() {
            return this.Hr_data_url;
        }

        public void setHr_data_url(String hr_data_url) {
            this.Hr_data_url = hr_data_url;
        }
    }

    public List<BallCode> getData() {
        return this.Data;
    }

    public void setData(List<BallCode> data) {
        this.Data = data;
    }
}
