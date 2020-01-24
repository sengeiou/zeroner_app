package com.iwown.sport_module.pojo.active;

public class SportDetailsData {
    private int activity;
    private boolean canGoToRun = false;
    private double distance;
    private long endTime;
    private int imgType;
    private long startTime;
    private int step;
    private String strCalories;
    private String strType;
    private int str_res = -1;
    private String time;
    private int type;

    public int getStr_res() {
        return this.str_res;
    }

    public void setStr_res(int str_res2) {
        this.str_res = str_res2;
    }

    public boolean isCanGoToRun() {
        return false;
    }

    public void setCanGoToRun(boolean canGoToRun2) {
        this.canGoToRun = canGoToRun2;
    }

    public boolean setCanGoToRun(String data_from) {
        return false;
    }

    public String getStrCalories() {
        return this.strCalories;
    }

    public void setStrCalories(String strCalories2) {
        this.strCalories = strCalories2;
    }

    public int getActivity() {
        return this.activity;
    }

    public void setActivity(int activity2) {
        this.activity = activity2;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time2) {
        this.time = time2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String getStrType() {
        return this.strType;
    }

    public void setStrType(String strType2) {
        this.strType = strType2;
    }

    public int getImgType() {
        return this.imgType;
    }

    public void setImgType(int imgType2) {
        this.imgType = imgType2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance2) {
        this.distance = distance2;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime2) {
        this.startTime = startTime2;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime2) {
        this.endTime = endTime2;
    }
}
