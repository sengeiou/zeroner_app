package com.iwown.sport_module.net.response;

import com.iwown.data_link.sport_data.ReturnCode;

public class AllSportGpsCode extends ReturnCode {
    private AllSportGps Data;

    public static class AllSportGps {
        private float ClimbDistance;
        private int ClimbDuration;
        private int ClimbTimes;
        private float CycleDistance;
        private int CycleDuration;
        private int CycleTimes;
        private float RunDistance;
        private int RunDuration;
        private int RunTimes;
        private long Uid;
        private float WalkDistance;
        private int WalkDuration;
        private int WalkTimes;

        public long getUid() {
            return this.Uid;
        }

        public void setUid(long uid) {
            this.Uid = uid;
        }

        public int getRunTimes() {
            return this.RunTimes;
        }

        public void setRunTimes(int runTimes) {
            this.RunTimes = runTimes;
        }

        public int getCycleTimes() {
            return this.CycleTimes;
        }

        public void setCycleTimes(int cycleTimes) {
            this.CycleTimes = cycleTimes;
        }

        public int getWalkTimes() {
            return this.WalkTimes;
        }

        public void setWalkTimes(int walkTimes) {
            this.WalkTimes = walkTimes;
        }

        public int getClimbTimes() {
            return this.ClimbTimes;
        }

        public void setClimbTimes(int climbTimes) {
            this.ClimbTimes = climbTimes;
        }

        public float getWalkDistance() {
            return this.WalkDistance;
        }

        public void setWalkDistance(float walkDistance) {
            this.WalkDistance = walkDistance;
        }

        public float getCycleDistance() {
            return this.CycleDistance;
        }

        public void setCycleDistance(float cycleDistance) {
            this.CycleDistance = cycleDistance;
        }

        public float getRunDistance() {
            return this.RunDistance;
        }

        public void setRunDistance(float runDistance) {
            this.RunDistance = runDistance;
        }

        public float getClimbDistance() {
            return this.ClimbDistance;
        }

        public void setClimbDistance(float climbDistance) {
            this.ClimbDistance = climbDistance;
        }

        public int getRunDuration() {
            return this.RunDuration;
        }

        public void setRunDuration(int runDuration) {
            this.RunDuration = runDuration;
        }

        public int getWalkDuration() {
            return this.WalkDuration;
        }

        public void setWalkDuration(int walkDuration) {
            this.WalkDuration = walkDuration;
        }

        public int getCycleDuration() {
            return this.CycleDuration;
        }

        public void setCycleDuration(int cycleDuration) {
            this.CycleDuration = cycleDuration;
        }

        public int getClimbDuration() {
            return this.ClimbDuration;
        }

        public void setClimbDuration(int climbDuration) {
            this.ClimbDuration = climbDuration;
        }
    }

    public AllSportGps getData() {
        return this.Data;
    }

    public void setData(AllSportGps data) {
        this.Data = data;
    }
}
