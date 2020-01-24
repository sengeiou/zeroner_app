package com.iwown.data_link.eventbus;

public class GpsTotalEvent {
    private float calorie;
    private int cardType;
    private int code = 0;
    private int count;
    private float distance;
    private int time;

    public GpsTotalEvent(int code2) {
        this.code = code2;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public int getCardType() {
        return this.cardType;
    }

    public void setCardType(int cardType2) {
        this.cardType = cardType2;
    }
}
