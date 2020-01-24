package com.iwown.sport_module.pojo.data;

public class SwimHealthyData {
    private float avgDps;
    private int avgPace;
    private String avgPaceStr;
    private int avgRate;
    private float avgStroke;
    private int avgSwolf;
    private float calories;
    private float distance;
    private int duration = 0;
    private String durationStr;
    private int laps;
    private int maxRate;
    private boolean metric;
    private int poolLength;
    private String startTime;
    private int totalStroke;
    private String url;

    public boolean isMetric() {
        return this.metric;
    }

    public void setMetric(boolean metric2) {
        this.metric = metric2;
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

    public float getCalories() {
        return this.calories;
    }

    public void setCalories(float calories2) {
        this.calories = calories2;
    }

    public int getPoolLength() {
        return this.poolLength;
    }

    public void setPoolLength(int poolLength2) {
        this.poolLength = poolLength2;
    }

    public int getLaps() {
        return this.laps;
    }

    public void setLaps(int laps2) {
        this.laps = laps2;
    }

    public int getAvgSwolf() {
        return this.avgSwolf;
    }

    public void setAvgSwolf(int avgSwolf2) {
        this.avgSwolf = avgSwolf2;
    }

    public int getTotalStroke() {
        return this.totalStroke;
    }

    public void setTotalStroke(int totalStroke2) {
        this.totalStroke = totalStroke2;
    }

    public float getAvgStroke() {
        return this.avgStroke;
    }

    public void setAvgStroke(float avgStroke2) {
        this.avgStroke = avgStroke2;
    }

    public float getAvgDps() {
        return this.avgDps;
    }

    public void setAvgDps(float avgDps2) {
        this.avgDps = avgDps2;
    }

    public int getAvgPace() {
        return this.avgPace;
    }

    public void setAvgPace(int avgPace2) {
        this.avgPace = avgPace2;
    }

    public int getAvgRate() {
        return this.avgRate;
    }

    public void setAvgRate(int avgRate2) {
        this.avgRate = avgRate2;
    }

    public int getMaxRate() {
        return this.maxRate;
    }

    public void setMaxRate(int maxRate2) {
        this.maxRate = maxRate2;
    }

    public String getDurationStr() {
        if (this.duration == 0) {
            this.durationStr = "00:00:00";
        } else {
            this.durationStr = String.format("%02d", new Object[]{Integer.valueOf(this.duration / 3600)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((this.duration % 3600) / 60)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(this.duration % 60)});
        }
        return this.durationStr;
    }

    public void setDurationStr(String durationStr2) {
        this.durationStr = durationStr2;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime2) {
        this.startTime = startTime2;
    }

    public String getAvgPaceStr() {
        this.avgPaceStr = (this.avgPace / 60) + "'" + (this.avgPace % 60) + "''";
        return this.avgPaceStr;
    }

    public void setAvgPaceStr(String avgPaceStr2) {
        this.avgPaceStr = avgPaceStr2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }
}
