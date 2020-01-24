package com.iwown.data_link.eventbus;

public class AgpsEvent {
    private int progress;

    public AgpsEvent(int progress2) {
        this.progress = progress2;
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress2) {
        this.progress = progress2;
    }
}
