package com.iwown.sport_module.net.response;

import com.iwown.data_link.sport_data.ReturnCode;

public class AllSportSwimCode extends ReturnCode {
    private AllSportSwim Data;

    public static class AllSportSwim {
        private float Calorie;
        private int Duration;
        private int Times;
        private long Uid;

        public long getUid() {
            return this.Uid;
        }

        public void setUid(long uid) {
            this.Uid = uid;
        }

        public int getTimes() {
            return this.Times;
        }

        public void setTimes(int times) {
            this.Times = times;
        }

        public int getDuration() {
            return this.Duration;
        }

        public void setDuration(int duration) {
            this.Duration = duration;
        }

        public float getCalorie() {
            return this.Calorie;
        }

        public void setCalorie(float calorie) {
            this.Calorie = calorie;
        }
    }

    public AllSportSwim getData() {
        return this.Data;
    }

    public void setData(AllSportSwim data) {
        this.Data = data;
    }
}
