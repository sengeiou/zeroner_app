package com.iwown.sport_module.gps.data;

public class MapActyUIEvent {
    public static final int Map_Can_Not_Operate_With_Gestures = 1;
    public static final int Map_Can_Operate_With_Gestures = 0;
    public static final int Pause_Location = 2;
    public static final int Should_Close_Acty = 3;
    private int state = -1;

    public MapActyUIEvent(int state2) {
        this.state = state2;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }
}
