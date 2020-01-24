package com.iwown.device_module.common.network.data.req;

import java.util.List;

public class SportOtherSend {
    private List<OtherSend> data;
    private long uid;

    public static class OtherSend {
        private float calorie;
        private String data_from;
        private float distance;
        private int done_times;
        private int duration;
        private String end_time;
        private String hr_data_url;
        private int sport_type;
        private String start_time;

        public String getData_from() {
            return this.data_from;
        }

        public void setData_from(String data_from2) {
            this.data_from = data_from2;
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

        public int getDone_times() {
            return this.done_times;
        }

        public void setDone_times(int done_times2) {
            this.done_times = done_times2;
        }

        public String getHr_data_url() {
            return this.hr_data_url;
        }

        public void setHr_data_url(String hr_data_url2) {
            this.hr_data_url = hr_data_url2;
        }
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public List<OtherSend> getData() {
        return this.data;
    }

    public void setData(List<OtherSend> data2) {
        this.data = data2;
    }
}
