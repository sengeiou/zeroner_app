package com.iwown.sport_module.gps.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.activity.MapActivity;
import com.iwown.sport_module.gps.data.GoogleGpsEvent;
import com.iwown.sport_module.gps.data.Gps;
import com.iwown.sport_module.gps.service.GoogleLocationManger;
import com.iwown.sport_module.map.PositionUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class GoogleFragment extends SupportMapFragment implements OnMapReadyCallback, OnMarkerClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private int clearNum = 1;
    private Gps firstGps;
    private boolean isCanUser;
    boolean isMove = false;
    private boolean isReady;
    private Gps lastGps;
    OnCameraChangeListener listener = new OnCameraChangeListener() {
        public void onCameraChange(CameraPosition cp) {
            GoogleFragment.this.isMove = true;
        }
    };
    private Location mLastLocation;
    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private View mView;
    private List<PolylineOptions> polyList = new ArrayList();
    private PolylineOptions polylineOptions;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        EventBus.getDefault().register(this);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("testmains", "GoogleFragment11 is Oncreate");
        getMapAsync(this);
        this.isCanUser = true;
        this.isReady = false;
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void onMapReady(GoogleMap googleMap) {
        boolean z;
        boolean z2;
        this.mMap = googleMap;
        this.mUiSettings = this.mMap.getUiSettings();
        this.mUiSettings.setZoomControlsEnabled(false);
        this.mMap.setOnMarkerClickListener(this);
        this.mMap.setOnCameraChangeListener(this.listener);
        String str = "googleFragment";
        Object[] objArr = new Object[1];
        StringBuilder sb = new StringBuilder();
        if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION") != 0) {
            z = true;
        } else {
            z = false;
        }
        StringBuilder append = sb.append(z).append("  ");
        if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        objArr[0] = append.append(z2).toString();
        KLog.d(str, objArr);
        this.mUiSettings.setCompassEnabled(false);
        this.mMap.setMyLocationEnabled(this.isCanUser);
        this.mUiSettings.setZoomGesturesEnabled(this.isCanUser);
        this.mUiSettings.setScrollGesturesEnabled(this.isCanUser);
        this.mUiSettings.setMyLocationButtonEnabled(false);
        Gps gps = GoogleLocationManger.getInstance().getNowGps();
        if (gps != null) {
            Gps nGps = PositionUtil.transform2(gps.getWgLat(), gps.getWgLon());
            this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(nGps.getWgLat(), nGps.getWgLon()), 16.0f));
        } else {
            Location nowLocation = GoogleLocationManger.getInstance().getFirstLocation();
            Gps nGps2 = PositionUtil.transform2(nowLocation.getLatitude(), nowLocation.getLongitude());
            this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(nGps2.getWgLat(), nGps2.getWgLon()), 16.0f));
        }
        KLog.d("no2855准备就绪googleFragment");
        this.isReady = true;
    }

    private void showMaps(Location location) {
        if (this.isReady) {
            if (this.lastGps == null) {
                this.firstGps = PositionUtil.transform2(location.getLatitude(), location.getLongitude());
                this.lastGps = new Gps(location.getLatitude(), location.getLongitude());
                this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(this.firstGps.getWgLat(), this.firstGps.getWgLon()), 16.0f));
                this.mMap.addMarker(new MarkerOptions().position(new LatLng(this.firstGps.getWgLat(), this.firstGps.getWgLon())).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_marker)).anchor(0.5f, 0.5f));
                this.polylineOptions = new PolylineOptions();
                this.polylineOptions.geodesic(true);
                this.polylineOptions.width(20.0f);
                this.polylineOptions.color(-11961);
                this.polylineOptions.add(new LatLng(this.firstGps.getWgLat(), this.firstGps.getWgLon()));
            } else if (this.lastGps.getWgLat() != location.getLatitude() || this.lastGps.getWgLon() != location.getLongitude()) {
                Gps gps2 = PositionUtil.transform2(location.getLatitude(), location.getLongitude());
                this.polylineOptions.add(new LatLng(gps2.getWgLat(), gps2.getWgLon()));
                this.mMap.clear();
                this.mMap.addMarker(new MarkerOptions().position(new LatLng(this.firstGps.getWgLat(), this.firstGps.getWgLon())).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_marker)).anchor(0.5f, 0.5f));
                if (!this.isMove) {
                    this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps2.getWgLat(), gps2.getWgLon()), 16.0f));
                } else {
                    this.mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gps2.getWgLat(), gps2.getWgLon())));
                }
                if (this.polyList.size() > 0) {
                    for (int i = 0; i < this.polyList.size(); i++) {
                        this.mMap.addPolyline((PolylineOptions) this.polyList.get(i)).setJointType(2);
                    }
                }
                this.clearNum++;
                if (this.clearNum % 6 == 0) {
                    this.mMap.clear();
                    this.mMap.addMarker(new MarkerOptions().position(new LatLng(this.firstGps.getWgLat(), this.firstGps.getWgLon())).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_marker)).anchor(0.5f, 0.5f));
                }
                this.mMap.addPolyline(this.polylineOptions).setJointType(2);
                this.lastGps.setWgLon(location.getLongitude());
                this.lastGps.setWgLat(location.getLatitude());
            }
        }
    }

    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public void resetLocation() {
        if (this.isReady && this.lastGps != null) {
            Gps g1 = PositionUtil.transform2(this.lastGps.getWgLat(), this.lastGps.getWgLon());
            LatLng sydney = new LatLng(g1.getWgLat(), g1.getWgLon());
            if (!this.isMove) {
                this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16.0f));
            } else {
                this.mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
            }
            this.mMap.addMarker(new MarkerOptions().position(new LatLng(this.firstGps.getWgLat(), this.firstGps.getWgLon())).icon(BitmapDescriptorFactory.fromResource(R.mipmap.map_marker)).anchor(0.5f, 0.5f));
        }
    }

    public void resetUser(boolean isCanUser2) {
        this.isCanUser = isCanUser2;
        if (this.mLastLocation == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mUiSettings.setCompassEnabled(isCanUser2);
            this.mMap.setMyLocationEnabled(isCanUser2);
            this.mUiSettings.setZoomGesturesEnabled(isCanUser2);
            this.mUiSettings.setScrollGesturesEnabled(isCanUser2);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoogleGpsEvent event) {
        Log.d("googleFragment", "no2855google收到地理位置--------->");
        if (event.location != null && this.mMap != null) {
            Log.d("testMain", "google地图页面收到广播-->" + event.data.getDistance());
            if (event.isRun) {
                showMaps(event.location);
            } else {
                this.polylineOptions = new PolylineOptions().width(15.0f).color(-11961);
                this.mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(event.location.getLatitude(), event.location.getLongitude())));
            }
            if (event.data != null && (getActivity() instanceof MapActivity)) {
                ((MapActivity) getActivity()).setMainMsg(event.data);
            }
        }
    }

    private void saveLocation(Location location) {
        if (this.lastGps == null) {
            this.firstGps = PositionUtil.transform2(location.getLatitude(), location.getLongitude());
            this.lastGps = new Gps(location.getLatitude(), location.getLongitude());
        } else if (this.lastGps.getWgLat() != location.getLatitude() || this.lastGps.getWgLon() != location.getLongitude()) {
            this.lastGps.setWgLon(location.getLongitude());
            this.lastGps.setWgLat(location.getLatitude());
        }
    }

    public void pauseLocation() {
        this.polyList.add(this.polylineOptions);
        this.polylineOptions = new PolylineOptions().width(20.0f).color(-11961);
    }

    public void restartLocation() {
    }
}
