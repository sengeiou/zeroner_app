package com.iwown.sport_module.device_data;

import android.location.Location;
import com.github.mikephil.charting.utils.Utils;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class GoogleDouglas {
    private double dMax;
    private int end;
    private GoogleLatLngPoint latLngPoint;
    private ArrayList<GoogleLatLngPoint> mLineFilter = new ArrayList<>();
    private ArrayList<GoogleLatLngPoint> mLineInit = new ArrayList<>();
    private int start;

    public GoogleDouglas(List<LongitudeAndLatitude> lineInit, double dmax) {
        if (this.mLineInit == null) {
            throw new IllegalArgumentException("传入的经纬度坐标list == null");
        }
        this.dMax = dmax;
        this.start = 0;
        this.end = lineInit.size() - 1;
        this.mLineInit.clear();
        for (int i = 0; i < lineInit.size(); i++) {
            this.mLineInit.add(new GoogleLatLngPoint(i, (LongitudeAndLatitude) lineInit.get(i)));
        }
    }

    public ArrayList<LongitudeAndLatitude> compress() {
        int size = this.mLineInit.size();
        ArrayList<GoogleLatLngPoint> latLngPoints = compressLine((GoogleLatLngPoint[]) this.mLineInit.toArray(new GoogleLatLngPoint[size]), this.mLineFilter, this.start, this.end, this.dMax);
        latLngPoints.add(this.mLineInit.get(0));
        latLngPoints.add(this.mLineInit.get(size - 1));
        Collections.sort(latLngPoints, new Comparator<GoogleLatLngPoint>() {
            public int compare(GoogleLatLngPoint o1, GoogleLatLngPoint o2) {
                return o1.compareTo(o2);
            }
        });
        ArrayList<LongitudeAndLatitude> latLngs = new ArrayList<>();
        Iterator it = latLngPoints.iterator();
        while (it.hasNext()) {
            latLngs.add(((GoogleLatLngPoint) it.next()).latLng);
        }
        return latLngs;
    }

    private ArrayList<GoogleLatLngPoint> compressLine(GoogleLatLngPoint[] originalLatLngs, ArrayList<GoogleLatLngPoint> endLatLngs, int start2, int end2, double dMax2) {
        if (start2 < end2) {
            double maxDist = Utils.DOUBLE_EPSILON;
            int currentIndex = 0;
            for (int i = start2 + 1; i < end2; i++) {
                double currentDist = distToSegment(originalLatLngs[start2], originalLatLngs[end2], originalLatLngs[i]);
                if (currentDist > maxDist) {
                    maxDist = currentDist;
                    currentIndex = i;
                }
            }
            if (maxDist >= dMax2) {
                endLatLngs.add(originalLatLngs[currentIndex]);
                compressLine(originalLatLngs, endLatLngs, start2, currentIndex, dMax2);
                compressLine(originalLatLngs, endLatLngs, currentIndex, end2, dMax2);
            }
        }
        return endLatLngs;
    }

    private double distToSegment(GoogleLatLngPoint start2, GoogleLatLngPoint end2, GoogleLatLngPoint center) {
        double a = Math.abs(getDistance(start2.latLng.latitude, start2.latLng.longitude, end2.latLng.latitude, end2.latLng.longitude));
        double b = Math.abs(getDistance(start2.latLng.latitude, start2.latLng.longitude, center.latLng.latitude, center.latLng.longitude));
        double c = Math.abs(getDistance(end2.latLng.latitude, end2.latLng.longitude, center.latLng.latitude, center.latLng.longitude));
        double p = ((a + b) + c) / 2.0d;
        return (2.0d * Math.sqrt(Math.abs((((p - a) * p) * (p - b)) * (p - c)))) / a;
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] dis = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, dis);
        return (double) dis[0];
    }
}
