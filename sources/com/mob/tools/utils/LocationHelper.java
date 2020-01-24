package com.mob.tools.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.mob.tools.MobHandlerThread;

public class LocationHelper implements LocationListener, Callback {
    private int GPSTimeoutSec;
    private boolean gpsRequesting;
    private Handler handler;
    private LocationManager lm;
    private boolean networkRequesting;
    private int networkTimeoutSec;
    private Location res;

    public LocationHelper() {
        MobHandlerThread thread = new MobHandlerThread();
        thread.start();
        this.handler = new Handler(thread.getLooper(), this);
    }

    public Location getLocation(Context context) throws Throwable {
        return getLocation(context, 0);
    }

    public Location getLocation(Context context, int GPSTimeoutSec2) throws Throwable {
        return getLocation(context, GPSTimeoutSec2, 0);
    }

    public Location getLocation(Context context, int GPSTimeoutSec2, int networkTimeoutSec2) throws Throwable {
        return getLocation(context, GPSTimeoutSec2, networkTimeoutSec2, true);
    }

    public Location getLocation(Context context, int GPSTimeoutSec2, int networkTimeoutSec2, boolean useLastKnown) throws Throwable {
        boolean preferGPS;
        boolean preferNetwork = true;
        this.GPSTimeoutSec = GPSTimeoutSec2;
        this.networkTimeoutSec = networkTimeoutSec2;
        this.lm = (LocationManager) context.getSystemService("location");
        if (this.lm == null) {
            return null;
        }
        synchronized (this) {
            this.handler.sendEmptyMessageDelayed(0, 50);
            wait();
        }
        if (this.res == null && useLastKnown) {
            if (GPSTimeoutSec2 != 0) {
                preferGPS = true;
            } else {
                preferGPS = false;
            }
            if (networkTimeoutSec2 == 0) {
                preferNetwork = false;
            }
            if (preferGPS && this.lm.isProviderEnabled("gps")) {
                this.res = this.lm.getLastKnownLocation("gps");
            } else if (preferNetwork && this.lm.isProviderEnabled("network")) {
                this.res = this.lm.getLastKnownLocation("network");
            }
        }
        return this.res;
    }

    public boolean handleMessage(Message msg) {
        if (msg.what == 0) {
            onRequest();
        } else if (this.gpsRequesting) {
            onGPSTimeout();
        } else if (this.networkRequesting) {
            this.lm.removeUpdates(this);
            synchronized (this) {
                notifyAll();
            }
            this.handler.getLooper().quit();
        }
        return false;
    }

    private void onRequest() {
        boolean preferGPS;
        boolean preferNetwork;
        if (this.GPSTimeoutSec != 0) {
            preferGPS = true;
        } else {
            preferGPS = false;
        }
        if (this.networkTimeoutSec != 0) {
            preferNetwork = true;
        } else {
            preferNetwork = false;
        }
        if (preferGPS && this.lm.isProviderEnabled("gps")) {
            this.gpsRequesting = true;
            this.lm.requestLocationUpdates("gps", 1000, 0.0f, this);
            if (this.GPSTimeoutSec > 0) {
                this.handler.sendEmptyMessageDelayed(1, (long) (this.GPSTimeoutSec * 1000));
            }
        } else if (!preferNetwork || !this.lm.isProviderEnabled("network")) {
            synchronized (this) {
                notifyAll();
            }
            this.handler.getLooper().quit();
        } else {
            this.networkRequesting = true;
            this.lm.requestLocationUpdates("network", 1000, 0.0f, this);
            if (this.networkTimeoutSec > 0) {
                this.handler.sendEmptyMessageDelayed(1, (long) (this.networkTimeoutSec * 1000));
            }
        }
    }

    private void onGPSTimeout() {
        boolean preferNetwork = false;
        this.lm.removeUpdates(this);
        this.gpsRequesting = false;
        if (this.networkTimeoutSec != 0) {
            preferNetwork = true;
        }
        if (!preferNetwork || !this.lm.isProviderEnabled("network")) {
            synchronized (this) {
                notifyAll();
            }
            this.handler.getLooper().quit();
            return;
        }
        this.networkRequesting = true;
        this.lm.requestLocationUpdates("network", 1000, 0.0f, this);
        if (this.networkTimeoutSec > 0) {
            this.handler.sendEmptyMessageDelayed(1, (long) (this.networkTimeoutSec * 1000));
        }
    }

    public void onLocationChanged(Location location) {
        synchronized (this) {
            this.lm.removeUpdates(this);
            this.res = location;
            notifyAll();
        }
        this.handler.getLooper().quit();
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }
}
