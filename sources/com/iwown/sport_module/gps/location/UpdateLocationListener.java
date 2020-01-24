package com.iwown.sport_module.gps.location;

import android.location.Location;

public interface UpdateLocationListener {
    void updateLocationChanged(Location location, int i);
}
