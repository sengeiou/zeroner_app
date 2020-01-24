package com.iwown.sport_module.gps.service;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.gps.data.GoogleGpsEvent;
import com.iwown.sport_module.gps.data.GpsLatLng;
import com.iwown.sport_module.gps.data.TB_location;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class GoogleLocationManger extends BaseLocationManger {
    public static GoogleLocationManger instance = null;
    private Location firstLocation;
    private Location lastLocation;
    private long lastTime = 0;
    protected List<GpsLatLng> latLngList = new ArrayList();
    private Location nowLocation;
    private Location pauseLocation;

    private GoogleLocationManger() {
    }

    public static GoogleLocationManger getInstance() {
        if (instance == null) {
            synchronized (GoogleLocationManger.class) {
                if (instance == null) {
                    instance = new GoogleLocationManger();
                }
            }
        }
        return instance;
    }

    public void initLocation(Context context) {
        super.initLocation(context);
        startLocation();
    }

    public void start() {
        clear();
        super.start();
        this.latLngList.add(new GpsLatLng(this.latLngList.size()));
    }

    public void restartLocation() {
        this.latLngList.add(new GpsLatLng(this.latLngList.size()));
        super.restartLocation();
    }

    public void clear() {
        super.clear();
        if (this.latLngList != null) {
            this.latLngList.clear();
        }
    }

    public int getStateNums() {
        return super.getStateNums();
    }

    /* access modifiers changed from: protected */
    public float getLatDistance() {
        if (this.lastLocation == null) {
            return 0.0f;
        }
        return this.lastLocation.distanceTo(this.nowLocation);
    }

    public void saveLocationChange(Location location) {
        if (location != null) {
            this.nowGps.setWgLon(location.getLongitude());
            this.nowGps.setWgLat(location.getLatitude());
            if (this.lastTime == 0 || ((double) (getLatDistance() / ((float) ((System.currentTimeMillis() / 1000) - this.lastTime)))) <= this.maxSpeed) {
                this.pauseType = this.latLngList.size() - 1;
                Log.d("testMain11111", "google定位成功" + location.getLatitude() + " -- " + location.getLongitude());
                if (this.isRunning) {
                    if (this.nums % 2 == 0) {
                        KLog.e(this.timeId + "", "google定位成功" + location.getLatitude() + " -- " + location.getLongitude());
                        reshNotification();
                    }
                    this.nums++;
                    if (this.lastLocation == null || this.lastLocation.getLatitude() != location.getLatitude() || this.lastLocation.getLongitude() != location.getLongitude()) {
                        this.nowLocation = location;
                        if (this.min2Avg == Utils.DOUBLE_EPSILON) {
                            setMin2Avg();
                        }
                        long nowTime = System.currentTimeMillis() / 1000;
                        if (this.isOk) {
                            if (this.lastLocation == null && this.pauseLocation != null) {
                                this.lastLocation = this.pauseLocation;
                            }
                            if (this.firstCheck) {
                                int sda = 0;
                                for (int g = 0; g < 3; g++) {
                                    if (this.checkTime[g] > 0) {
                                        if (getDistan(this.checkGps[g].getWgLat(), this.checkGps[g].getWgLon(), location.getLatitude(), location.getLongitude()) / ((double) (nowTime - this.checkTime[g])) <= this.maxSpeed) {
                                            sda++;
                                        }
                                    }
                                }
                                if (sda >= 2) {
                                    this.firstCheck = false;
                                }
                            }
                            if (!this.firstCheck && ((double) (getLatDistance() / ((float) ((System.currentTimeMillis() / 1000) - this.lastTime)))) > this.maxSpeed) {
                                L.file("no2855 gps 存在飘逸点: " + location.getLatitude() + " -- " + location.getLongitude(), 8);
                                KLog.e(this.timeId + "", "此点定位漂移点: " + location.getLatitude() + " -- " + location.getLongitude());
                                if (this.lastLocation != null) {
                                    this.nowLocation = this.lastLocation;
                                    return;
                                }
                                return;
                            }
                        }
                        if (this.firstCheck) {
                            this.checkGps[this.checkNum].setWgLon(location.getLongitude());
                            this.checkGps[this.checkNum].setWgLat(location.getLatitude());
                            this.checkTime[this.checkNum] = System.currentTimeMillis() / 1000;
                            this.checkNum++;
                            if (this.checkNum > 2) {
                                this.checkNum = 0;
                            }
                        }
                        this.lastTime = nowTime;
                        Location location2 = location;
                        EventBus.getDefault().post(new GoogleGpsEvent(location2, changeMain(), true, this.latLngList.size() - 1));
                        if (this.latLngList.size() > 0) {
                            ((GpsLatLng) this.latLngList.get(this.latLngList.size() - 1)).addLatList(new LatLng(location.getLatitude(), location.getLongitude()));
                        }
                        L.file("no2855 gps 开始存点--> " + location.getLatitude() + " -- " + location.getLongitude(), 8);
                        saveTbGps(location.getLatitude(), location.getLongitude(), this.sportType, this.timeId, this.uid, this.latLngList.size() - 1, this.mStep);
                        saveHisTB();
                        this.lastLocation = location;
                        return;
                    }
                    return;
                }
                L.file("no2855 isrunning is false", 8);
                if (this.pauseLocation == null || this.pauseLocation.getLatitude() != location.getLatitude() || this.pauseLocation.getLongitude() != location.getLongitude()) {
                    if (this.isOk) {
                        if (getDistan(this.pauseLocation.getLatitude(), this.pauseLocation.getLongitude(), location.getLatitude(), location.getLongitude()) / ((double) ((int) ((System.currentTimeMillis() / 1000) - this.lastTime))) > this.maxSpeed) {
                            return;
                        }
                    }
                    this.pauseLocation = location;
                    this.lastTime = System.currentTimeMillis() / 1000;
                    return;
                }
                return;
            }
            L.file("no2855google定位成功 有点漂移" + location.getLatitude() + " -- " + location.getLongitude(), 8);
            if (this.lastLocation != null) {
                this.nowLocation = this.lastLocation;
            }
        }
    }

    private double getDistan(double lat1, double lon1, double lat2, double lon2) {
        float[] dis = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, dis);
        return (double) dis[0];
    }

    public void pauseLocation() {
        super.pauseLocation();
        if (this.lastLocation != null) {
            this.pauseLocation = this.lastLocation;
        }
        this.lastLocation = null;
    }

    public static void saveTbGps(double lat, double lon, int type, long timeId, long uid, int pause, int step) {
        TB_location location = new TB_location(lat, lon);
        location.setTime(System.currentTimeMillis() / 1000);
        location.setSport_type(type);
        location.setUid(uid);
        location.setTime_id(timeId);
        location.setPause_type(pause);
        location.setStep(step);
        location.save();
    }

    public void saveFirstLocation(Location location) {
        this.firstLocation = location;
    }

    public Location getFirstLocation() {
        return this.firstLocation;
    }
}
