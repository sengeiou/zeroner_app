package com.iwown.data_link.sport_data.gps;

import com.iwown.data_link.base.RetCode;

public class GPSStatData extends RetCode {
    private ContentBean content;

    public static class ContentBean {
        private double cycleDistance;
        private int cycleDuration;
        private int cycleTimes;
        private double runDistance;
        private int runDuration;
        private int runTimes;
        private long uid;
        private double walkDistance;
        private int walkDuration;
        private int walkTimes;

        public long getUid() {
            return this.uid;
        }

        public void setUid(long uid2) {
            this.uid = uid2;
        }

        public int getRunTimes() {
            return this.runTimes;
        }

        public void setRunTimes(int runTimes2) {
            this.runTimes = runTimes2;
        }

        public int getCycleTimes() {
            return this.cycleTimes;
        }

        public void setCycleTimes(int cycleTimes2) {
            this.cycleTimes = cycleTimes2;
        }

        public int getWalkTimes() {
            return this.walkTimes;
        }

        public void setWalkTimes(int walkTimes2) {
            this.walkTimes = walkTimes2;
        }

        public double getRunDistance() {
            return this.runDistance;
        }

        public void setRunDistance(double runDistance2) {
            this.runDistance = runDistance2;
        }

        public double getCycleDistance() {
            return this.cycleDistance;
        }

        public void setCycleDistance(double cycleDistance2) {
            this.cycleDistance = cycleDistance2;
        }

        public double getWalkDistance() {
            return this.walkDistance;
        }

        public void setWalkDistance(double walkDistance2) {
            this.walkDistance = walkDistance2;
        }

        public int getRunDuration() {
            return this.runDuration;
        }

        public void setRunDuration(int runDuration2) {
            this.runDuration = runDuration2;
        }

        public int getWalkDuration() {
            return this.walkDuration;
        }

        public void setWalkDuration(int walkDuration2) {
            this.walkDuration = walkDuration2;
        }

        public int getCycleDuration() {
            return this.cycleDuration;
        }

        public void setCycleDuration(int cycleDuration2) {
            this.cycleDuration = cycleDuration2;
        }
    }

    public ContentBean getContent() {
        return this.content;
    }

    public void setContent(ContentBean content2) {
        this.content = content2;
    }
}
