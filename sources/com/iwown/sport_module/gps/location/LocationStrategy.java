package com.iwown.sport_module.gps.location;

public interface LocationStrategy {
    void requestLocation();

    void setListener(UpdateLocationListener updateLocationListener);

    void stopLocation();
}
