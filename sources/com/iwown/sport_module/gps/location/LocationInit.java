package com.iwown.sport_module.gps.location;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import com.iwown.lib_common.toast.ToastUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocationInit implements UpdateLocationListener {
    public static LocationInit intance;
    private AMapLocationStrategy aMapLocationStrategy;
    private GpsLocationStrategy gpsLocationStrategy;
    /* access modifiers changed from: private */
    public Context mContext;
    private int mGpsCount = 0;
    private Listener mGpsStatusCallback = new Listener() {
        public void onGpsStatusChanged(int event) {
            if (ActivityCompat.checkSelfPermission(LocationInit.this.mContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                KLog.i("GPS权限不够");
                return;
            }
            LocationInit.this.updateGpsStatus(event, LocationInit.this.mLocationManager.getGpsStatus(null));
        }
    };
    private LocationController mLocationController;
    /* access modifiers changed from: private */
    public LocationManager mLocationManager;
    private List<GpsSatellite> numSatelliteList = new ArrayList();

    public static LocationInit getInstance(Context context) {
        if (intance == null) {
            intance = new LocationInit(context);
        }
        return intance;
    }

    private LocationInit(Context mContext2) {
        this.mContext = mContext2;
    }

    public void requestLocation() {
        if (this.gpsLocationStrategy == null) {
            this.gpsLocationStrategy = new GpsLocationStrategy(this.mContext);
        }
        if (this.mLocationController == null) {
            this.mLocationController = new LocationController();
        }
        this.mLocationController.setLocationStrategy(this.gpsLocationStrategy);
        this.mLocationController.setListener(this);
    }

    public void stopLocation() {
        if (this.mLocationController != null) {
            this.mLocationController.stopLocation();
        }
    }

    public void requestGpsCount() {
        if (this.mLocationManager == null) {
            this.mLocationManager = (LocationManager) this.mContext.getSystemService("location");
        }
        if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            KLog.i("GPS权限不够");
            ToastUtils.showLongToast((CharSequence) "GPS权限不够");
            return;
        }
        this.mLocationManager.addGpsStatusListener(this.mGpsStatusCallback);
    }

    /* access modifiers changed from: private */
    public void updateGpsStatus(int event, GpsStatus status) {
        if (event == 4) {
            int maxSatellites = status.getMaxSatellites();
            Iterator<GpsSatellite> it = status.getSatellites().iterator();
            this.numSatelliteList.clear();
            int count = 0;
            while (it.hasNext() && count <= maxSatellites) {
                GpsSatellite s = (GpsSatellite) it.next();
                if (s.getSnr() != 0.0f) {
                    this.numSatelliteList.add(s);
                    count++;
                }
            }
            this.mGpsCount = this.numSatelliteList.size();
            if (this.mLocationController == null) {
                this.mLocationController = new LocationController();
            }
            if (this.mGpsCount >= 3) {
                if (this.gpsLocationStrategy == null) {
                    this.gpsLocationStrategy = new GpsLocationStrategy(this.mContext);
                }
                this.gpsLocationStrategy.setGpsCount(this.mGpsCount);
                if (!(this.mLocationController.getLocationStrategy() instanceof GpsLocationStrategy)) {
                    this.mLocationController.setLocationStrategy(this.gpsLocationStrategy);
                } else {
                    return;
                }
            } else {
                if (this.aMapLocationStrategy == null) {
                    this.aMapLocationStrategy = new AMapLocationStrategy(this.mContext);
                }
                if (!(this.mLocationController.getLocationStrategy() instanceof AMapLocationStrategy)) {
                    this.mLocationController.setLocationStrategy(this.aMapLocationStrategy);
                } else {
                    return;
                }
            }
            this.mLocationController.setListener(this);
        }
    }

    public void updateLocationChanged(Location location, int gpsCount) {
    }
}
