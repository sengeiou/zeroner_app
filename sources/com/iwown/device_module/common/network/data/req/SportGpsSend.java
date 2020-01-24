package com.iwown.device_module.common.network.data.req;

import java.util.List;

public class SportGpsSend {
    private List<GpsSend> data;
    private long uid;

    public static class GpsSend {
        private int avg_pace;
        private int cadence;
        private float calorie;
        private String data_from;
        private float distance;
        private int duration;
        private String end_time;
        private String gps_data_url;
        private String headset_data_url;
        private String hr_data_url;
        private int source_type;
        private int sport_type;
        private String start_time;
        private int step;

        public String getData_from() {
            return this.data_from;
        }

        public void setData_from(String data_from2) {
            this.data_from = data_from2;
        }

        public int getSource_type() {
            return this.source_type;
        }

        public void setSource_type(int source_type2) {
            this.source_type = source_type2;
        }

        public String getStart_time() {
            return this.start_time;
        }

        public void setStart_time(String start_time2) {
            this.start_time = start_time2;
        }

        public String getEnd_time() {
            return this.end_time;
        }

        public void setEnd_time(String end_time2) {
            this.end_time = end_time2;
        }

        public int getSport_type() {
            return this.sport_type;
        }

        public void setSport_type(int sport_type2) {
            this.sport_type = sport_type2;
        }

        public float getCalorie() {
            return this.calorie;
        }

        public void setCalorie(float calorie2) {
            this.calorie = calorie2;
        }

        public float getDistance() {
            return this.distance;
        }

        public void setDistance(float distance2) {
            this.distance = distance2;
        }

        public int getDuration() {
            return this.duration;
        }

        public void setDuration(int duration2) {
            this.duration = duration2;
        }

        public int getAvg_pace() {
            return this.avg_pace;
        }

        public void setAvg_pace(int avg_pace2) {
            this.avg_pace = avg_pace2;
        }

        public int getCadence() {
            return this.cadence;
        }

        public void setCadence(int cadence2) {
            this.cadence = cadence2;
        }

        public String getGps_data_url() {
            return this.gps_data_url;
        }

        public void setGps_data_url(String gps_data_url2) {
            this.gps_data_url = gps_data_url2;
        }

        public String getHr_data_url() {
            return this.hr_data_url;
        }

        public void setHr_data_url(String hr_data_url2) {
            this.hr_data_url = hr_data_url2;
        }

        public String getHeadset_data_url() {
            return this.headset_data_url;
        }

        public void setHeadset_data_url(String headset_data_url2) {
            this.headset_data_url = headset_data_url2;
        }

        public int getStep() {
            return this.step;
        }

        public void setStep(int step2) {
            this.step = step2;
        }
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public List<GpsSend> getData() {
        return this.data;
    }

    public void setData(List<GpsSend> data2) {
        this.data = data2;
    }
}
