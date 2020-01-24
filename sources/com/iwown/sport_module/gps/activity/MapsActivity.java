package com.iwown.sport_module.gps.activity;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.data.Gps;
import com.iwown.sport_module.map.PositionUtil;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, OnMarkerClickListener, LocationListener {
    private static final int LOCATION_LAYER_PERMISSION_REQUEST_CODE = 2;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int MY_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    /* access modifiers changed from: private */
    public GoogleApiClient mGoogleApiClient;
    /* access modifiers changed from: private */
    public Location mLastLocation;
    private boolean mLocationPermissionDenied = false;
    private LocationRequest mLocationRequest;
    /* access modifiers changed from: private */
    public boolean mLocationUpdateState;
    /* access modifiers changed from: private */
    public GoogleMap mMap;
    /* access modifiers changed from: private */
    public UiSettings mUiSettings;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_activity_maps);
        setRequestedOrientation(1);
        getWindow().addFlags(67108864);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        if (this.mGoogleApiClient == null) {
            this.mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
        createLocationRequest();
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.mGoogleApiClient.connect();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (this.mGoogleApiClient != null && this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.disconnect();
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        boolean z;
        this.mMap = googleMap;
        this.mUiSettings = this.mMap.getUiSettings();
        this.mUiSettings.setZoomControlsEnabled(true);
        this.mMap.setOnMarkerClickListener(this);
        String str = "tesasd";
        StringBuilder append = new StringBuilder().append(ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0).append("  ");
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            z = true;
        } else {
            z = false;
        }
        Log.d(str, append.append(z).toString());
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(MapsActivity.this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                        MapsActivity.this.mUiSettings.setMyLocationButtonEnabled(true);
                        MapsActivity.this.mUiSettings.setCompassEnabled(true);
                        MapsActivity.this.mMap.setMyLocationEnabled(true);
                        MapsActivity.this.mMap.setOnMapClickListener(new OnMapClickListener() {
                            public void onMapClick(LatLng latLng) {
                                Log.d("testMainasd", "点击坐标: " + latLng.toString());
                                MapsActivity.this.mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
                            }
                        });
                        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(MapsActivity.this.mGoogleApiClient);
                        if (locationAvailability != null && locationAvailability.isLocationAvailable()) {
                            MapsActivity.this.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(MapsActivity.this.mGoogleApiClient);
                            if (MapsActivity.this.mLastLocation != null) {
                                LatLng currentLocation = new LatLng(MapsActivity.this.mLastLocation.getLatitude(), MapsActivity.this.mLastLocation.getLongitude());
                                Log.d("testMainasd", MapsActivity.this.mLastLocation.getLatitude() + "  " + MapsActivity.this.mLastLocation.getLongitude());
                                MapsActivity.this.mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in Sydney"));
                                Gps g1 = PositionUtil.transform2(MapsActivity.this.mLastLocation.getLatitude(), MapsActivity.this.mLastLocation.getLongitude());
                                MapsActivity.this.mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.554292006172957d, 113.94851762574089d), 15.0f));
                                MapsActivity.this.mMap.addMarker(new MarkerOptions().position(new LatLng(g1.getWgLat(), g1.getWgLon())).title("Marker in Sydney"));
                            }
                        }
                    }
                }
            }, 2000);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }

    /* access modifiers changed from: protected */
    public void onResumeFragments() {
        super.onResumeFragments();
        if (this.mLocationPermissionDenied) {
            this.mLocationPermissionDenied = false;
        }
    }

    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
            if (this.mLastLocation != null) {
                new LatLng(this.mLastLocation.getLatitude(), this.mLastLocation.getLongitude());
            }
        }
    }

    public void onConnected(@Nullable Bundle bundle) {
        if (this.mLocationUpdateState) {
            startLocationUpdates();
        }
    }

    public void onConnectionSuspended(int i) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /* access modifiers changed from: protected */
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, (LocationListener) this);
    }

    /* access modifiers changed from: protected */
    public void createLocationRequest() {
        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(1000);
        this.mLocationRequest.setFastestInterval(1000);
        this.mLocationRequest.setPriority(100);
        LocationServices.SettingsApi.checkLocationSettings(this.mGoogleApiClient, new LocationSettingsRequest.Builder().addLocationRequest(this.mLocationRequest).build()).setResultCallback(new ResultCallback<LocationSettingsResult>() {
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case 0:
                        MapsActivity.this.mLocationUpdateState = true;
                        MapsActivity.this.startLocationUpdates();
                        return;
                    case 6:
                        try {
                            status.startResolutionForResult(MapsActivity.this, 2);
                            return;
                        } catch (SendIntentException e) {
                            return;
                        }
                    default:
                        return;
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == -1) {
            this.mLocationUpdateState = true;
            startLocationUpdates();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, (LocationListener) this);
    }

    public void onResume() {
        super.onResume();
        if (this.mGoogleApiClient.isConnected() && !this.mLocationUpdateState) {
            startLocationUpdates();
        }
    }
}
