package com.iwown.sport_module.gps.data;

import android.support.annotation.RequiresApi;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.Objects;

public class SportDetailItem implements MultiItemEntity {
    private float calorie;
    private boolean canClick;
    private String dataFrom;
    private int dataType;
    private float distanceKm;
    private float distanceMile;
    private int doneTimes;
    private int duration;
    private String durationStr;
    private long endTime;
    private int sourceType;
    private int sportImg;
    private int sportType;
    private long startTime;
    private int step;
    private String timeTitle;
    private int upload;

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

    public String getDataFrom() {
        return this.dataFrom;
    }

    public void setDataFrom(String dataFrom2) {
        this.dataFrom = dataFrom2;
    }

    public int getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(int sourceType2) {
        this.sourceType = sourceType2;
    }

    public int getSportType() {
        return this.sportType;
    }

    public void setSportType(int sportType2) {
        this.sportType = sportType2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public float getDistanceKm() {
        return this.distanceKm;
    }

    public void setDistanceKm(float distanceKm2) {
        this.distanceKm = distanceKm2;
    }

    public float getDistanceMile() {
        return this.distanceMile;
    }

    public void setDistanceMile(float distanceMile2) {
        this.distanceMile = distanceMile2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration2) {
        this.duration = duration2;
    }

    public String getDurationStr() {
        return this.durationStr;
    }

    public void setDurationStr(String durationStr2) {
        this.durationStr = durationStr2;
    }

    public int getDoneTimes() {
        return this.doneTimes;
    }

    public void setDoneTimes(int doneTimes2) {
        this.doneTimes = doneTimes2;
    }

    public boolean isCanClick() {
        return this.canClick;
    }

    public void setCanClick(boolean canClick2) {
        this.canClick = canClick2;
    }

    public String getTimeTitle() {
        return this.timeTitle;
    }

    public void setTimeTitle(String timeTitle2) {
        this.timeTitle = timeTitle2;
    }

    public int getItemType() {
        return 1;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType2) {
        this.dataType = dataType2;
    }

    public int getSportImg() {
        return this.sportImg;
    }

    public void setSportImg(int sportImg2) {
        this.sportImg = sportImg2;
    }

    public int getUpload() {
        return this.upload;
    }

    public void setUpload(int upload2) {
        this.upload = upload2;
    }

    @RequiresApi(api = 19)
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SportDetailItem that = (SportDetailItem) o;
        if (this.startTime == that.startTime && this.endTime == that.endTime && this.sportType == that.sportType && Objects.equals(this.dataFrom, that.dataFrom)) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = 19)
    public int hashCode() {
        return Objects.hash(new Object[]{Long.valueOf(this.startTime), Long.valueOf(this.endTime), this.dataFrom, Integer.valueOf(this.sportType)});
    }
}
