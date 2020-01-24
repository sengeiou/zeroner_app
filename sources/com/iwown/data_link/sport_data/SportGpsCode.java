package com.iwown.data_link.sport_data;

import java.util.List;

public class SportGpsCode extends ReturnCode {
    private List<GpsCode> Data;

    public static class GpsCode {
        private int Avg_pace;
        private int Cadence;
        private float Calorie;
        private String Data_from;
        private float Distance;
        private int Duration;
        private String End_time;
        private String Gps_data_url;
        private String Headset_data_url;
        private String Hr_data_url;
        private int Source_type;
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

        public String getData_from() {
            return this.Data_from;
        }

        public void setData_from(String data_from) {
            this.Data_from = data_from;
        }

        public int getSource_type() {
            return this.Source_type;
        }

        public void setSource_type(int source_type) {
            this.Source_type = source_type;
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

        public int getStep() {
            return this.Step;
        }

        public void setStep(int step) {
            this.Step = step;
        }

        public float getCalorie() {
            return this.Calorie;
        }

        public void setCalorie(float calorie) {
            this.Calorie = calorie;
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

        public int getAvg_pace() {
            return this.Avg_pace;
        }

        public void setAvg_pace(int avg_pace) {
            this.Avg_pace = avg_pace;
        }

        public int getCadence() {
            return this.Cadence;
        }

        public void setCadence(int cadence) {
            this.Cadence = cadence;
        }

        public String getGps_data_url() {
            return this.Gps_data_url;
        }

        public void setGps_data_url(String gps_data_url) {
            this.Gps_data_url = gps_data_url;
        }

        public String getHr_data_url() {
            return this.Hr_data_url;
        }

        public void setHr_data_url(String hr_data_url) {
            this.Hr_data_url = hr_data_url;
        }

        public String getHeadset_data_url() {
            return this.Headset_data_url;
        }

        public void setHeadset_data_url(String headset_data_url) {
            this.Headset_data_url = headset_data_url;
        }
    }

    public List<GpsCode> getData() {
        return this.Data;
    }

    public void setData(List<GpsCode> data) {
        this.Data = data;
    }
}
