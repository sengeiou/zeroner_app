package com.iwown.sport_module.gps.data;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class GpsLatLng {
    private List<LatLng> latLngList = new ArrayList();
    private int pauseType;

    public GpsLatLng(int pauseType2) {
        this.pauseType = pauseType2;
    }

    public int getPauseType() {
        return this.pauseType;
    }

    public void setPauseType(int pauseType2) {
        this.pauseType = pauseType2;
    }

    public List<LatLng> getLatLngList() {
        return this.latLngList;
    }

    public void setLatLngList(List<LatLng> latLngList2) {
        this.latLngList = latLngList2;
    }

    public void addLatList(LatLng latLng) {
        this.latLngList.add(latLng);
    }
}
