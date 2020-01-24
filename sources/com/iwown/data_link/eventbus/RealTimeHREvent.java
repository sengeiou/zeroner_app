package com.iwown.data_link.eventbus;

public class RealTimeHREvent {
    private int hr;

    public RealTimeHREvent(int hr2) {
        this.hr = hr2;
    }

    public int getHr() {
        return this.hr;
    }

    public void setHr(int hr2) {
        this.hr = hr2;
    }
}
