package com.iwown.device_module.device_firmware_upgrade.eventbus;

public class WakeUpEvent {
    public static final int STATE_RELEASE = 1;
    public static final int STATE_WAKE_UP = 0;
    private int state;

    public WakeUpEvent(int state2) {
        this.state = state2;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }
}
