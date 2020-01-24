package com.iwown.sport_module.gps.location;

public class LocationController {
    private LocationStrategy locationStrategy;

    public void setLocationStrategy(LocationStrategy locationStrategy2) {
        if (this.locationStrategy != null) {
            stopLocation();
        }
        this.locationStrategy = locationStrategy2;
        requestLocation();
    }

    public LocationStrategy getLocationStrategy() {
        return this.locationStrategy;
    }

    public void requestLocation() {
        this.locationStrategy.requestLocation();
    }

    public void stopLocation() {
        this.locationStrategy.stopLocation();
    }

    public void setListener(UpdateLocationListener listener) {
        this.locationStrategy.setListener(listener);
    }
}
