package com.iwown.data_link.eventbus;

public class ShouldGetWeatherEvent {
    public static final int NowRequest = 1;
    public static final int TenMinRequest = 10;
    private int flag;

    public ShouldGetWeatherEvent() {
        setFlag(1);
    }

    public ShouldGetWeatherEvent(int flag2) {
        this.flag = flag2;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag2) {
        this.flag = flag2;
    }
}
