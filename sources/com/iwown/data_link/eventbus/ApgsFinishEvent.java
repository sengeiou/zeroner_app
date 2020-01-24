package com.iwown.data_link.eventbus;

public class ApgsFinishEvent {
    private boolean status;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status2) {
        this.status = status2;
    }
}
