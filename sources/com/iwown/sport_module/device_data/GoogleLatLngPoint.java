package com.iwown.sport_module.device_data;

import android.support.annotation.NonNull;
import com.iwown.sport_module.map.LongitudeAndLatitude;

public class GoogleLatLngPoint implements Comparable<GoogleLatLngPoint> {
    public int id;
    public LongitudeAndLatitude latLng;

    public GoogleLatLngPoint(int id2, LongitudeAndLatitude latLng2) {
        this.id = id2;
        this.latLng = latLng2;
    }

    public int compareTo(@NonNull GoogleLatLngPoint o) {
        if (this.id < o.id) {
            return -1;
        }
        if (this.id > o.id) {
            return 1;
        }
        return 0;
    }
}
