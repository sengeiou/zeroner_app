package com.iwown.sport_module.gps.activity;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.data.TB_location;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.gps.view.GpsAnimView;
import com.iwown.sport_module.gps.view.SportTrailView;
import com.iwown.sport_module.gps.view.SportTrailView.OnTrailChangeListener;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.view.MyTextView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;

public class ShowMapActivity extends BaseShowMapActivity implements OnTrailChangeListener, OnMapReadyCallback {
    /* access modifiers changed from: private */
    public GoogleMap aMap;
    private GpsAnimView animView;
    private MyTextView calTxt;
    private DecimalFormat decimalFormat;
    private MyTextView distanceTxt;
    private ImageView itemBack;
    List<TB_location> locations;
    /* access modifiers changed from: private */
    public UiSettings mUiSettings;
    private MapView mapView;
    PolylineOptions polyine;
    List<PolylineOptions> polyines = new ArrayList();
    private TextView start2EndTxt;
    private MyTextView timeTxt;
    private SportTrailView trailView;
    private TextView txtUtil;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.setTopWindows(getWindow());
        setContentView(R.layout.sport_module_amap_fragment);
        findViewById(R.id.item_all_layout).setPadding(0, WindowUtil.getStatusBarHeight(), 0, 0);
        this.itemBack = (ImageView) findViewById(R.id.gps_item_back);
        this.distanceTxt = (MyTextView) findViewById(R.id.his_distance);
        this.timeTxt = (MyTextView) findViewById(R.id.his_time);
        this.calTxt = (MyTextView) findViewById(R.id.his_calore);
        this.start2EndTxt = (TextView) findViewById(R.id.his_st_ed);
        this.txtUtil = (TextView) findViewById(R.id.map_util);
        this.mapView = (MapView) findViewById(R.id.my_amap_view);
        this.trailView = (SportTrailView) findViewById(R.id.gps_trail_view);
        this.decimalFormat = new DecimalFormat("0.00");
        this.mapView.getMapAsync(this);
        this.mapView.onCreate(savedInstanceState);
        this.uid = UserConfig.getInstance().getNewUID();
        this.time = getIntent().getLongExtra("startTime", 0);
        this.target = getIntent().getIntExtra("target", 0);
        this.itemBack.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ShowMapActivity.this.finish();
            }
        });
    }

    private void showTargetDialog() {
        if (this.target != 0) {
            Builder alertDialogBuilder = new Builder(this);
            alertDialogBuilder.setPositiveButton((CharSequence) getString(R.string.sport_module_ok), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            if (this.target == 1) {
                alertDialogBuilder.setMessage((CharSequence) getString(R.string.sport_module_gps_target_ok));
            } else {
                alertDialogBuilder.setMessage((CharSequence) getString(R.string.sport_module_gps_target_no));
            }
            alertDialogBuilder.setCancelable(true);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
        }
    }

    /* access modifiers changed from: protected */
    public void showMap() {
        showTargetDialog();
        this.locations = DataSupport.where("uid=? and time_id=?", this.uid + "", this.time + "").find(TB_location.class);
        new ArrayList();
        double lat1 = Utils.DOUBLE_EPSILON;
        double lat2 = Utils.DOUBLE_EPSILON;
        double lon1 = Utils.DOUBLE_EPSILON;
        double lon2 = Utils.DOUBLE_EPSILON;
        for (int i = 0; i < this.locations.size(); i++) {
            if (i == 0) {
                this.polyine.add(new LatLng(((TB_location) this.locations.get(i)).getLat(), ((TB_location) this.locations.get(i)).getLon()));
            } else {
                if (((TB_location) this.locations.get(i)).getPause_type() == ((TB_location) this.locations.get(i - 1)).getPause_type()) {
                    this.polyine.add(new LatLng(((TB_location) this.locations.get(i)).getLat(), ((TB_location) this.locations.get(i)).getLon()));
                } else {
                    this.polyines.add(this.polyine);
                    this.polyine = new PolylineOptions().width(20.0f).color(-11961);
                    this.polyine.add(new LatLng(((TB_location) this.locations.get(i)).getLat(), ((TB_location) this.locations.get(i)).getLon()));
                }
            }
            if (i == this.locations.size() - 1) {
                this.polyines.add(this.polyine);
            }
            if (lat1 == Utils.DOUBLE_EPSILON || ((TB_location) this.locations.get(i)).getLat() < lat1) {
                lat1 = ((TB_location) this.locations.get(i)).getLat();
            }
            if (lat2 == Utils.DOUBLE_EPSILON || ((TB_location) this.locations.get(i)).getLat() > lat2) {
                lat2 = ((TB_location) this.locations.get(i)).getLat();
            }
            if (lon1 == Utils.DOUBLE_EPSILON || ((TB_location) this.locations.get(i)).getLon() < lon1) {
                lon1 = ((TB_location) this.locations.get(i)).getLon();
            }
            if (lon2 == Utils.DOUBLE_EPSILON || ((TB_location) this.locations.get(i)).getLon() > lat2) {
                lon2 = ((TB_location) this.locations.get(i)).getLon();
            }
        }
        if (this.locations.size() > 0) {
            Log.d("asdasdasd", lat1 + " - " + lon1 + " - " + lat2 + " - " + lon2);
            LatLng latLng = new LatLng(lat1, lon1);
            LatLng latLng2 = new LatLng(lat2, lon2);
            this.aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(latLng, latLng2), 250));
        }
        TB_location_history history = (TB_location_history) DataSupport.where("uid=? and time_id=?", this.uid + "", this.time + "").findFirst(TB_location_history.class);
        this.distanceTxt.setText(this.decimalFormat.format((double) history.getDistance()));
        int times = history.getTime();
        if (times <= 0) {
            times = (int) (history.getEnd_time() - history.getTime_id());
        }
        int in1 = times / 3600;
        int in2 = (times - (in1 * 3600)) / 60;
        int in3 = times % 60;
        if (UserConfig.getInstance().isMertric()) {
            this.distanceTxt.setText(this.decimalFormat.format((double) (history.getDistance() / 1000.0f)));
            this.txtUtil.setText(R.string.sport_module_distance_unit_km);
        } else {
            this.distanceTxt.setText(this.decimalFormat.format(Util.kmToMile((double) (history.getDistance() / 1000.0f))));
            this.txtUtil.setText(R.string.sport_module_distance_unit_mi);
        }
        this.timeTxt.setText(String.format("%02d", new Object[]{Integer.valueOf(in1)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in2)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in3)}));
        this.calTxt.setText(this.decimalFormat.format((double) history.getCalorie()));
        DateUtil dateUtil1 = new DateUtil(history.getTime_id(), true);
        DateUtil dateUtil2 = new DateUtil(history.getEnd_time(), true);
        String msg1 = "";
        if (history.getSport_type() == 0) {
            msg1 = getString(R.string.sport_module_gps_chose_run);
        } else if (history.getSport_type() == 1) {
            msg1 = getString(R.string.sport_module_gps_chose_cycle);
        } else if (history.getSport_type() == 3) {
            msg1 = getString(R.string.sport_module_gps_chose_walk);
        }
        this.start2EndTxt.setText(dateUtil1.getHHmmssDate() + " - " + dateUtil2.getHHmmssDate() + "  " + msg1);
        this.trailView.setVisibility(8);
        Handler handler = new Handler();
        AnonymousClass3 r0 = new Runnable() {
            public void run() {
                for (int i = 0; i < ShowMapActivity.this.polyines.size(); i++) {
                    ShowMapActivity.this.aMap.addPolyline((PolylineOptions) ShowMapActivity.this.polyines.get(i));
                }
                if (ShowMapActivity.this.locations != null && ShowMapActivity.this.locations.size() > 0) {
                    ShowMapActivity.this.addMarker(new LatLng(((TB_location) ShowMapActivity.this.locations.get(0)).getLat(), ((TB_location) ShowMapActivity.this.locations.get(0)).getLon()), R.mipmap.go_3x);
                    ShowMapActivity.this.addMarker(new LatLng(((TB_location) ShowMapActivity.this.locations.get(ShowMapActivity.this.locations.size() - 1)).getLat(), ((TB_location) ShowMapActivity.this.locations.get(ShowMapActivity.this.locations.size() - 1)).getLon()), R.mipmap.end_3x);
                    ShowMapActivity.this.mUiSettings.setAllGesturesEnabled(true);
                }
            }
        };
        handler.postDelayed(r0, 1000);
    }

    /* access modifiers changed from: private */
    public void addMarker(LatLng lat, int mapMarker) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.draggable(false);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), mapMarker)));
        markerOption.anchor(0.5f, 0.5f);
        markerOption.position(lat);
        this.aMap.addMarker(markerOption);
    }

    public void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    public void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mapView.onSaveInstanceState(outState);
    }

    public void onFinish() {
    }

    public void onMapReady(GoogleMap googleMap) {
        this.aMap = googleMap;
        this.mUiSettings = this.aMap.getUiSettings();
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.aMap.setMyLocationEnabled(false);
        }
        this.mUiSettings.setZoomGesturesEnabled(true);
        this.mUiSettings.setScrollGesturesEnabled(true);
        this.aMap.moveCamera(CameraUpdateFactory.zoomTo(16.0f));
        this.polyine = new PolylineOptions().width(20.0f).color(-11961);
        testUpFileAndDetailData();
    }
}
