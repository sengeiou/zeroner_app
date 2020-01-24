package com.iwown.sport_module.gps.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import com.socks.library.KLog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GpsLocationStrategy implements LocationStrategy {
    public static final String TAG = "GpsLocationStrategy";
    private AMapLocationStrategy aMapLocationStrategy;
    /* access modifiers changed from: private */
    public Context context;
    private boolean isRequestAmap = false;
    private boolean isRequestGps = false;
    /* access modifiers changed from: private */
    public UpdateLocationListener listener;
    LocationListener mGPSLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            KLog.i("GPSLocationListener ，location ：经    度：" + location.getLongitude() + " 纬    度：" + location.getLatitude());
            if (GpsLocationStrategy.this.listener != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                StringBuilder sb = new StringBuilder();
                sb.append("GPS定位成功\n");
                sb.append("定位类型: GPS\n");
                sb.append("经度：");
                sb.append(location.getLongitude());
                sb.append("\n纬度：");
                sb.append(location.getLatitude());
                sb.append("\n高度：");
                sb.append(location.getAltitude());
                sb.append("\n速度：");
                sb.append(location.getSpeed());
                sb.append("\n时间：");
                sb.append(location.getTime());
                sb.append("\n精度：");
                sb.append(location.getAccuracy());
                sb.append("\n方位：");
                sb.append(location.getBearing());
                sb.append("\n时间：");
                sb.append(simpleDateFormat.format(new Date(location.getTime())));
                sb.append("\n星数：");
                sb.append(GpsLocationStrategy.this.mGpsCount);
                if (GpsLocationStrategy.this.listener != null) {
                    GpsLocationStrategy.this.listener.updateLocationChanged(location, GpsLocationStrategy.this.mGpsCount);
                }
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            KLog.i(GpsLocationStrategy.TAG, "GPSLocationListener onStatusChanged：" + status);
            switch (status) {
            }
        }

        public void onProviderEnabled(String provider) {
            KLog.i(GpsLocationStrategy.TAG, "GPSLocationListener onProviderEnabled GPS开启了");
        }

        public void onProviderDisabled(String provider) {
            KLog.i(GpsLocationStrategy.TAG, "GPSLocationListener onProviderDisabled GPS关闭了");
        }
    };
    /* access modifiers changed from: private */
    public int mGpsCount;
    private Listener mGpsStatusCallback = new Listener() {
        @SuppressLint({"MissingPermission"})
        public void onGpsStatusChanged(int event) {
            GpsLocationStrategy.this.updateGpsStatus(event, ((LocationManager) GpsLocationStrategy.this.context.getSystemService("location")).getGpsStatus(null));
        }
    };
    private LocationManager mLocationManager;
    private float mMinDistance = 0.0f;
    private List<GpsSatellite> numSatelliteList = new ArrayList();

    public GpsLocationStrategy(Context context2) {
        this.context = context2;
        this.mLocationManager = (LocationManager) context2.getSystemService("location");
    }

    public void setListener(UpdateLocationListener listener2) {
        this.listener = listener2;
    }

    public void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mLocationManager.requestLocationUpdates("gps", 1000, this.mMinDistance, this.mGPSLocationListener);
            this.mLocationManager.addGpsStatusListener(this.mGpsStatusCallback);
            this.isRequestGps = true;
            return;
        }
        KLog.i("=======requestLocation()========");
    }

    public void stopLocation() {
        this.mLocationManager.removeUpdates(this.mGPSLocationListener);
        this.mLocationManager.removeGpsStatusListener(this.mGpsStatusCallback);
        this.isRequestGps = false;
        if (this.aMapLocationStrategy != null) {
            this.aMapLocationStrategy.stopLocation();
        }
    }

    public void setGpsCount(int mGpsCount2) {
        this.mGpsCount = mGpsCount2;
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
            KLog.i(TAG, "updateGpsStatus mGpsCount：" + this.mGpsCount);
            changeLocationMode();
        }
    }

    @SuppressLint({"MissingPermission"})
    private void changeLocationMode() {
        if (this.mGpsCount >= 3) {
            if (this.isRequestAmap) {
                this.aMapLocationStrategy.stopLocation();
                this.aMapLocationStrategy.setListener(null);
                this.isRequestAmap = false;
            }
            if (!this.isRequestGps) {
                this.mLocationManager.requestLocationUpdates("gps", 1000, this.mMinDistance, this.mGPSLocationListener);
                this.isRequestGps = true;
                return;
            }
            return;
        }
        if (this.isRequestGps) {
            this.mLocationManager.removeUpdates(this.mGPSLocationListener);
            this.isRequestGps = false;
        }
        if (!this.isRequestAmap) {
            if (this.aMapLocationStrategy == null) {
                this.aMapLocationStrategy = new AMapLocationStrategy(this.context);
            }
            this.aMapLocationStrategy.requestLocation();
            this.aMapLocationStrategy.setListener(this.listener);
            this.isRequestAmap = true;
        }
    }
}
