package com.iwown.device_module.common.network.data.resp;

public class AllSportBallCode extends ReturnCode {
    private AllSportBall Data;

    public static class AllSportBall {
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

    public AllSportBall getData() {
        return this.Data;
    }

    public void setData(AllSportBall data) {
        this.Data = data;
    }
}
