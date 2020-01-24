package com.iwown.sport_module.map;

import android.content.Context;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.iwown.lib_common.log.L;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MapHelper {
    private static Context context;
    private static MapHelper instance = null;
    private int endIconRes = -1;
    private boolean inChina = false;
    private float lineWidth = 13.0f;
    private int line_color = -1;
    private GoogleMap map = null;
    private List<LatLng> myLatLngs = null;
    private int startIconRes = -1;
    private LatLng ui_center_latlng = null;
    private LatLngBounds ui_latlng_bound = null;

    public GoogleMap getMap() {
        return this.map;
    }

    public MapHelper setMap(GoogleMap map2) {
        this.map = map2;
        return instance;
    }

    public float getLineWidth() {
        return this.lineWidth;
    }

    public MapHelper setLineWidth(float lineWidth2) {
        this.lineWidth = lineWidth2;
        return instance;
    }

    public int getLine_color() {
        return this.line_color;
    }

    public MapHelper setLine_color(int line_color2) {
        this.line_color = line_color2;
        return instance;
    }

    public int getStartIconRes() {
        return this.startIconRes;
    }

    public MapHelper setStartIconRes(int startIconRes2) {
        this.startIconRes = startIconRes2;
        return instance;
    }

    public int getEndIconRes() {
        return this.endIconRes;
    }

    public MapHelper setEndIconRes(int endIconRes2) {
        this.endIconRes = endIconRes2;
        return instance;
    }

    private MapHelper(Context context1) {
        context = context1;
    }

    public static MapHelper getInstance(Context context1) {
        context = context1;
        if (instance == null) {
            synchronized (MapHelper.class) {
                if (instance == null) {
                    instance = new MapHelper(context);
                }
            }
        }
        return instance;
    }

    public static MapHelper getInstance() {
        return instance;
    }

    public boolean isSupportGoogleServer(Context context2) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context2);
        if (resultCode == 0) {
            return true;
        }
        if (googleApiAvailability.isUserResolvableError(resultCode)) {
        }
        return false;
    }

    public MapHelper setMapStyle(int jsonStyleStrResId) {
        this.map.setMapStyle(new MapStyleOptions(context.getResources().getString(jsonStyleStrResId)));
        return instance;
    }

    public MapHelper addTile() {
        this.map.addTileOverlay(new TileOverlayOptions().tileProvider(new MapTileProvider(context)).zIndex(-1.0f));
        return instance;
    }

    public void drawSingleLine(int fromType, List<LatLng> latLngs) {
        if (latLngs != null && latLngs.size() != 0 && this.map != null) {
            List<LatLng> latLngs1 = new ArrayList<>();
            boolean isOutChina = true;
            int num = 0;
            for (LatLng latLng : latLngs) {
                if (fromType == 1) {
                    Gps gps = new Gps(latLng.latitude, latLng.longitude);
                    latLngs1.add(new LatLng(gps.getWgLat(), gps.getWgLon()));
                } else {
                    if (!(num != 0 || latLng.latitude == Utils.DOUBLE_EPSILON || latLng.longitude == Utils.DOUBLE_EPSILON)) {
                        num++;
                        if (!PositionUtil.outOfChina(latLng.latitude, latLng.longitude)) {
                            isOutChina = false;
                            L.file("no2855 地理坐标在中国内: " + latLng.latitude + "," + latLng.latitude, 3);
                        }
                    }
                    if (isOutChina) {
                        latLngs1.add(latLng);
                    } else {
                        Gps gps2 = PositionUtil.transformMust(latLng.latitude, latLng.longitude);
                        latLngs1.add(new LatLng(gps2.getWgLat(), gps2.getWgLon()));
                    }
                }
            }
            Polyline polyline = this.map.addPolyline(new PolylineOptions().geodesic(true).addAll(latLngs1).width(this.lineWidth).color(this.line_color).zIndex(2.0f));
            polyline.setStartCap(new RoundCap());
            polyline.setEndCap(new RoundCap());
            polyline.setJointType(2);
            this.map.addMarker(new MarkerOptions().position((LatLng) latLngs1.get(0)).title("start").zIndex(2.0f).icon(BitmapDescriptorFactory.fromResource(this.startIconRes)).anchor(0.5f, 0.4f));
            if (latLngs.size() != 1) {
                this.map.addMarker(new MarkerOptions().position((LatLng) latLngs1.get(latLngs.size() - 1)).title("end").icon(BitmapDescriptorFactory.fromResource(this.endIconRes)).zIndex(2.0f).anchor(0.5f, 0.4f));
            }
            findMapUiCenter(latLngs1);
            moveCameraToBoundCenter(this.map);
        }
    }

    public void showBoundMap() {
        if (this.myLatLngs != null) {
            findMapUiCenter(this.myLatLngs);
            moveCameraToBoundCenter(this.map);
        }
    }

    public void showPolyLineMap() {
        if (this.myLatLngs != null && this.map != null) {
            Polyline polyline = this.map.addPolyline(new PolylineOptions().geodesic(true).addAll(this.myLatLngs).width(this.lineWidth).color(this.line_color).zIndex(2.0f));
            polyline.setStartCap(new RoundCap());
            polyline.setEndCap(new RoundCap());
            polyline.setJointType(2);
            this.map.addMarker(new MarkerOptions().position((LatLng) this.myLatLngs.get(0)).title("start").zIndex(2.0f).icon(BitmapDescriptorFactory.fromResource(this.startIconRes)).anchor(0.5f, 0.4f));
            if (this.myLatLngs.size() != 1) {
                this.map.addMarker(new MarkerOptions().position((LatLng) this.myLatLngs.get(this.myLatLngs.size() - 1)).title("end").icon(BitmapDescriptorFactory.fromResource(this.endIconRes)).zIndex(2.0f).anchor(0.5f, 0.4f));
            }
        }
    }

    private List<LatLng> createRectangle(LatLng center, double halfWidth, double halfHeight) {
        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude - halfWidth));
        latLngs.add(new LatLng(center.latitude - halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude + halfWidth));
        latLngs.add(new LatLng(center.latitude + halfHeight, center.longitude - halfWidth));
        return latLngs;
    }

    public void findMapUiCenter(List<LatLng> latLngs) {
        double max_lat = -1.0d;
        double min_lat = -1.0d;
        double max_lng = -1.0d;
        double min_lng = -1.0d;
        for (LatLng latLng : latLngs) {
            if (max_lat == -1.0d) {
                max_lat = latLng.latitude;
            }
            if (min_lat == -1.0d) {
                min_lat = latLng.latitude;
            }
            if (max_lng == -1.0d) {
                max_lng = latLng.longitude;
            }
            if (min_lng == -1.0d) {
                min_lng = latLng.longitude;
            }
            if (max_lat <= latLng.latitude) {
                max_lat = latLng.latitude;
            }
            if (min_lat >= latLng.latitude) {
                min_lat = latLng.latitude;
            }
            if (max_lng <= latLng.longitude) {
                max_lng = latLng.longitude;
            }
            if (min_lng >= latLng.longitude) {
                min_lng = latLng.longitude;
            }
        }
        this.ui_center_latlng = new LatLng(((max_lat - min_lat) / 2.0d) + min_lat, ((max_lng - min_lng) / 2.0d) + min_lng);
        this.ui_latlng_bound = new LatLngBounds(new LatLng(min_lat, min_lng), new LatLng(max_lat, max_lng));
    }

    public MapHelper moveCameraToBoundCenter(GoogleMap map2) {
        if (!(this.ui_center_latlng == null || map2 == null)) {
            map2.moveCamera(CameraUpdateFactory.newLatLngBounds(this.ui_latlng_bound, 200));
        }
        return instance;
    }

    public MapHelper zoomTo(int zoomLev) {
        if (this.map != null) {
            this.map.moveCamera(CameraUpdateFactory.zoomTo((float) zoomLev));
        }
        return instance;
    }

    public MapHelper setMapType(int type) {
        this.map.setMapType(type);
        return instance;
    }

    public void gnssDataToFloatLatLng(int fromType, List<LongitudeAndLatitude> longitudeAndLatitudes) {
        if (longitudeAndLatitudes != null && longitudeAndLatitudes.size() != 0) {
            List<LatLng> latLngs1 = new ArrayList<>();
            boolean isOutChina = true;
            this.inChina = false;
            int num = 0;
            for (int i = 0; i < longitudeAndLatitudes.size(); i++) {
                if (fromType == 1) {
                    Gps gps = new Gps(((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).latitude, ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).longitude);
                    latLngs1.add(new LatLng(gps.getWgLat(), gps.getWgLon()));
                } else {
                    if (!(num != 0 || ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).latitude == Utils.DOUBLE_EPSILON || ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).longitude == Utils.DOUBLE_EPSILON)) {
                        num++;
                        if (!PositionUtil.outOfChina(((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).latitude, ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).longitude)) {
                            isOutChina = false;
                            L.file("no2855 地理坐标在中国内: " + ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).latitude + "," + ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).latitude, 3);
                        }
                    }
                    if (isOutChina) {
                        Gps gps2 = new Gps(((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).latitude, ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).longitude);
                        latLngs1.add(new LatLng(gps2.getWgLat(), gps2.getWgLon()));
                    } else {
                        Gps gps3 = PositionUtil.transformMust(((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).latitude, ((LongitudeAndLatitude) longitudeAndLatitudes.get(i)).longitude);
                        latLngs1.add(new LatLng(gps3.getWgLat(), gps3.getWgLon()));
                    }
                }
            }
            if (isOutChina) {
                this.inChina = false;
            } else {
                this.inChina = true;
            }
            this.myLatLngs = latLngs1;
        }
    }

    public float gnssDegreeToFloat(int degree, int minute, int second) {
        return new BigDecimal(((double) degree) + ((((double) minute) * 1.0d) / 60.0d) + ((((double) second) * 1.0d) / 3600.0d)).setScale(3).floatValue();
    }

    public LatLng gnssDataToLatLng(GnssData gnssDatas) {
        float lngFloat;
        float latFloat;
        int longitude_degree = gnssDatas.getLongitude_degree();
        int longitude_minute = gnssDatas.getLongitude_minute();
        int longitude_second = gnssDatas.getLongitude_second();
        int latitude_degree = gnssDatas.getLatitude_degree();
        int latitude_minute = gnssDatas.getLatitude_minute();
        int latitude_second = gnssDatas.getLatitude_second();
        if (gnssDatas.getLongitude_direction() == 0) {
            lngFloat = gnssDegreeToFloat(longitude_degree, longitude_minute, longitude_second);
        } else {
            lngFloat = -gnssDegreeToFloat(longitude_degree, longitude_minute, longitude_second);
        }
        if (gnssDatas.getLatitude_direction() == 0) {
            latFloat = gnssDegreeToFloat(latitude_degree, latitude_minute, latitude_second);
        } else {
            latFloat = -gnssDegreeToFloat(latitude_degree, latitude_minute, latitude_second);
        }
        return new LatLng((double) lngFloat, (double) latFloat);
    }

    public void reset() {
        context = null;
        this.map = null;
    }

    public boolean isInChina() {
        return this.inChina;
    }

    public void setInChina(boolean inChina2) {
        this.inChina = inChina2;
    }
}
