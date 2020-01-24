package com.iwown.sport_module.gps.data;

import android.location.Location;

public class GoogleGpsEvent {
    public GpsMsgData data;
    public boolean isRun;
    public Location location;
    public int pauseType;

    public GoogleGpsEvent(Location location2, GpsMsgData data2, boolean isRun2, int pauseType2) {
        this.location = location2;
        this.data = data2;
        this.isRun = isRun2;
        this.pauseType = pauseType2;
    }
}
