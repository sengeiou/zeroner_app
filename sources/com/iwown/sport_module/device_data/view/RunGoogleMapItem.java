package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.InputDeviceCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.iwown.sport_module.R;
import com.iwown.sport_module.device_data.GoogleDouglas;
import com.iwown.sport_module.gps.view.SportTrailView;
import com.iwown.sport_module.gps.view.SportTrailView.OnTrailChangeListener;
import com.iwown.sport_module.map.Gps;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import com.iwown.sport_module.map.MapHelper;
import com.iwown.sport_module.map.PositionUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class RunGoogleMapItem extends RunMapLayout implements OnMapReadyCallback, OnTrailChangeListener {
    private int deviceType;
    /* access modifiers changed from: private */
    public int fromType;
    /* access modifiers changed from: private */
    public TextView mData_frome_tv;
    private ImageView mIcon;
    /* access modifiers changed from: private */
    public List<LongitudeAndLatitude> mLongitudeAndLatitudes;
    /* access modifiers changed from: private */
    public GoogleMap mMap = null;
    private MapView mMapView;
    private ImageView mTo_map_center_btn;
    private UiSettings mUiSettings;
    PolylineOptions polyine;
    /* access modifiers changed from: private */
    public SportTrailView sportTrailView;

    public RunGoogleMapItem(Context context) {
        super(context);
    }

    public RunGoogleMapItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RunGoogleMapItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RunGoogleMapItem(Context context, int mapType) {
        super(context, mapType);
    }

    public void initView(Context context, Bundle savedInstanceState) {
        KLog.e("no2855 谷歌地图初始化完成-->");
        LayoutInflater.from(context).inflate(R.layout.sport_module_run_googlemap_view, this);
        this.mMapView = (MapView) findViewById(R.id.google_map_view);
        this.sportTrailView = (SportTrailView) findViewById(R.id.gps_trailView);
        this.mTo_map_center_btn = (ImageView) findViewById(R.id.to_map_center_btn);
        this.mIcon = (ImageView) findViewById(R.id.data_from_icon);
        this.mData_frome_tv = (TextView) findViewById(R.id.data_from_tv);
        this.mData_frome_tv.setVisibility(4);
        this.mMapView.getMapAsync(this);
        this.mMapView.onCreate(savedInstanceState);
        this.mTo_map_center_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (RunGoogleMapItem.this.mMap != null) {
                    MapHelper.getInstance().moveCameraToBoundCenter(RunGoogleMapItem.this.mMap);
                }
            }
        });
        this.mIcon.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (RunGoogleMapItem.this.mData_frome_tv.getVisibility() == 4) {
                    RunGoogleMapItem.this.mData_frome_tv.setVisibility(0);
                } else {
                    RunGoogleMapItem.this.mData_frome_tv.setVisibility(4);
                }
            }
        });
    }

    private void initMapView() {
        this.mMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.mUiSettings = this.mMap.getUiSettings();
        this.mUiSettings.setZoomControlsEnabled(false);
        this.mUiSettings.setMyLocationButtonEnabled(false);
        this.polyine = new PolylineOptions().width(17.0f).color(InputDeviceCompat.SOURCE_ANY);
        this.polyine.zIndex(1.0f);
        this.polyine.visible(true);
    }

    public void refreshDataView(int dev_type, String mDataFrom) {
        switch (dev_type) {
            case 0:
                this.mIcon.setBackgroundResource(R.mipmap.from_band3x);
                break;
            case 1:
                this.mIcon.setBackgroundResource(R.mipmap.from_watch3x);
                break;
            case 2:
                this.mIcon.setBackgroundResource(R.mipmap.from_phone3x);
                break;
            case 3:
                this.mIcon.setBackgroundResource(R.mipmap.from_earphone3x);
                break;
            case 4:
                break;
        }
        this.mIcon.setBackgroundResource(R.mipmap.from_band3x);
        this.mData_frome_tv.setText(getContext().getString(R.string.sport_module_data_from) + "\n" + mDataFrom);
    }

    public void drawAmapMap(List<LongitudeAndLatitude> longitudeAndLatitudes, int deviceType2, int fromType2) {
        this.mLongitudeAndLatitudes = longitudeAndLatitudes;
        this.deviceType = deviceType2;
        this.fromType = fromType2;
        refreshMap();
    }

    private void refreshMap() {
        if (this.mLongitudeAndLatitudes != null && this.mLongitudeAndLatitudes.size() != 0 && this.mMap != null && this.mMapView != null) {
            this.mMapView.post(new Runnable() {
                public void run() {
                    MapHelper.getInstance(RunGoogleMapItem.this.getContext().getApplicationContext()).gnssDataToFloatLatLng(RunGoogleMapItem.this.fromType, RunGoogleMapItem.this.mLongitudeAndLatitudes);
                    MapHelper.getInstance(RunGoogleMapItem.this.getContext().getApplicationContext()).showBoundMap();
                    RunGoogleMapItem.this.showAnimLine();
                    KLog.e("refreshMap2222222");
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void showAnimLine() {
        this.mMapView.postDelayed(new Runnable() {
            public void run() {
                LatLng latLng;
                List<LongitudeAndLatitude> newLongList = new GoogleDouglas(RunGoogleMapItem.this.mLongitudeAndLatitudes, 8.0d).compress();
                KLog.e("no2855 --> 压缩的数据前后对比: " + RunGoogleMapItem.this.mLongitudeAndLatitudes.size() + " == " + newLongList.size());
                List<Point> linePoint = new ArrayList<>();
                boolean isInChina = MapHelper.getInstance(RunGoogleMapItem.this.getContext().getApplicationContext()).isInChina();
                for (LongitudeAndLatitude longitudeAndLatitude : newLongList) {
                    if (isInChina) {
                        Gps gps = PositionUtil.transformMust(longitudeAndLatitude.latitude, longitudeAndLatitude.longitude);
                        latLng = new LatLng(gps.getWgLat(), gps.getWgLon());
                    } else {
                        latLng = new LatLng(longitudeAndLatitude.latitude, longitudeAndLatitude.longitude);
                    }
                    linePoint.add(RunGoogleMapItem.this.mMap.getProjection().toScreenLocation(latLng));
                }
                RunGoogleMapItem.this.sportTrailView.drawSportLine(linePoint, R.mipmap.go_3x, R.mipmap.green_dot3x, RunGoogleMapItem.this);
            }
        }, 500);
    }

    public int getMapHeight() {
        if (this.mMapView != null) {
            return this.mMapView.getMeasuredHeight();
        }
        return 0;
    }

    public void onStart() {
        KLog.e("mapview onStart");
        if (this.mMapView != null) {
            this.mMapView.onStart();
        }
    }

    public void onResume() {
        KLog.e("mapview onResume");
        if (this.mMapView != null) {
            this.mMapView.onResume();
        }
    }

    public void onPause() {
        if (this.mMapView != null) {
            this.mMapView.onPause();
        }
    }

    public void onStop() {
        if (this.mMapView != null) {
            this.mMapView.onStop();
        }
    }

    public void onDestroy() {
        if (MapHelper.getInstance() != null) {
            MapHelper.getInstance().reset();
        }
        if (this.mMapView != null) {
            this.mMapView.onDestroy();
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (this.mMap == null) {
            KLog.e("licl", "map==null");
        }
        this.mUiSettings = this.mMap.getUiSettings();
        this.mUiSettings.setAllGesturesEnabled(false);
        MapHelper.getInstance(getContext()).setMap(this.mMap).zoomTo(15).setMapType(1).addTile().setMapStyle(R.string.sport_module_map_style).setLine_color(Color.parseColor("#FFD147")).setStartIconRes(R.mipmap.go_3x).setEndIconRes(R.mipmap.end_3x);
        refreshMap();
    }

    public void onFinish() {
        KLog.d("no2855 --> gps压缩 结束动画了？？");
        if (this.mUiSettings != null) {
            this.mUiSettings.setAllGesturesEnabled(true);
        }
        MapHelper.getInstance(getContext().getApplicationContext()).showPolyLineMap();
        this.mMapView.postDelayed(new Runnable() {
            public void run() {
                RunGoogleMapItem.this.sportTrailView.setVisibility(8);
            }
        }, 100);
    }
}
