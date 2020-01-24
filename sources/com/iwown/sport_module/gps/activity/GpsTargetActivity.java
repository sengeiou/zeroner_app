package com.iwown.sport_module.gps.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.gps.GpsAmapService;
import com.iwown.sport_module.gps.service.GpsGoogleNewService;
import com.iwown.sport_module.gps.service.LocationImpl;
import com.iwown.sport_module.gps.view.CircleIndicator;
import com.iwown.sport_module.gps.view.GpsFreeView;
import com.iwown.sport_module.gps.view.GpsGoalView;
import com.iwown.sport_module.gps.view.GpsGoalView.OnGpsGoalListener;
import com.iwown.sport_module.gps.view.ModelLayout;
import com.iwown.sport_module.gps.view.MyGpsSportDialog;
import com.iwown.sport_module.gps.view.MyGpsSportDialog.OnSportListener;
import com.iwown.sport_module.gps.view.MyInputDialog;
import com.iwown.sport_module.gps.view.MyInputDialog.OnInputListener;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.util.XPermissionUtils;
import com.iwown.sport_module.util.XPermissionUtils.OnPermissionListener;
import com.iwown.sport_module.view.MyTextView;
import com.socks.library.KLog;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GpsTargetActivity extends Activity implements OnInputListener, OnSportListener, OnClickListener, OnGpsGoalListener {
    /* access modifiers changed from: private */
    public String TAG = getClass().getSimpleName();
    /* access modifiers changed from: private */
    public GradientDrawable[] colorBg;
    Callback gnss;
    /* access modifiers changed from: private */
    public MyTextView gpsGo;
    private ImageView gpsModelBack;
    private TextView gpsModelTitle;
    /* access modifiers changed from: private */
    public RelativeLayout gpsRelayoutBg;
    Listener gpsS = new Listener() {
        public void onGpsStatusChanged(int event) {
            KLog.e(GpsTargetActivity.this.TAG, "onGpsStatusChanged--event" + event);
            if (event == 3) {
                KLog.e(GpsTargetActivity.this.TAG, "onGpsStatusChanged--gps微星初始化: ");
            } else if (event == 4 && ActivityCompat.checkSelfPermission(GpsTargetActivity.this.getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0) {
                KLog.e(GpsTargetActivity.this.TAG, "onGpsStatusChanged--GPS_EVENT_SATELLITE_STATUS");
                GpsStatus gpsStauts = GpsTargetActivity.this.locationManager.getGpsStatus(null);
                int maxSatellites = gpsStauts.getMaxSatellites();
                GpsTargetActivity.this.gpscount = 0;
                Iterator<GpsSatellite> it = gpsStauts.getSatellites().iterator();
                while (it.hasNext() && GpsTargetActivity.this.gpscount <= maxSatellites) {
                    if (((GpsSatellite) it.next()).usedInFix()) {
                        GpsTargetActivity.this.gpscount = GpsTargetActivity.this.gpscount + 1;
                    }
                }
                Log.d(GpsTargetActivity.this.TAG, "onGpsStatusChanged--gps微星初始化数量:" + maxSatellites + " -- " + GpsTargetActivity.this.gpscount);
            }
        }
    };
    private LinearLayout gpsSetTitle;
    private ImageView gpsTitleImg;
    /* access modifiers changed from: private */
    public int gpscount;
    /* access modifiers changed from: private */
    public boolean hasLocation = true;
    /* access modifiers changed from: private */
    public boolean hasWrite = true;
    /* access modifiers changed from: private */
    public CircleIndicator indicator;
    private MyInputDialog inputDialog;
    /* access modifiers changed from: private */
    public boolean isHealthy = false;
    OnPermissionListener locationListener = new OnPermissionListener() {
        public void onPermissionGranted() {
            Log.d("testLocation", "地理位置权限成功");
            GpsTargetActivity.this.hasLocation = true;
            if (VERSION.SDK_INT >= 26) {
                if (GpsTargetActivity.this.isHealthy) {
                    GpsTargetActivity.this.startForegroundService(new Intent(GpsTargetActivity.this.getApplicationContext(), GpsAmapService.class).putExtra("type", GpsTargetActivity.this.sportType));
                } else {
                    GpsTargetActivity.this.startForegroundService(new Intent(GpsTargetActivity.this, GpsGoogleNewService.class).putExtra("type", GpsTargetActivity.this.sportType));
                }
            } else if (GpsTargetActivity.this.isHealthy) {
                GpsTargetActivity.this.startService(new Intent(GpsTargetActivity.this.getApplicationContext(), GpsAmapService.class).putExtra("type", GpsTargetActivity.this.sportType));
            } else {
                GpsTargetActivity.this.startService(new Intent(GpsTargetActivity.this.getApplicationContext(), GpsGoogleNewService.class).putExtra("type", GpsTargetActivity.this.sportType));
            }
            GpsTargetActivity.this.initsdWirte();
        }

        public void onPermissionDenied() {
            GpsTargetActivity.this.hasLocation = false;
            Log.d("testLocation", "地理位置权限失败");
        }
    };
    /* access modifiers changed from: private */
    public LocationManager locationManager;
    private RelativeLayout mGpsTitleRelayout;
    private ModelLayout mModelLayout;
    private RelativeLayout mPagerLayout;
    /* access modifiers changed from: private */
    public int mPosition;
    /* access modifiers changed from: private */
    public boolean mShow_hr;
    private int nums = 0;
    PagerAdapter pagerAdapter = new PagerAdapter() {
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public int getCount() {
            return GpsTargetActivity.this.viewList.size();
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) GpsTargetActivity.this.viewList.get(position));
        }

        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        public CharSequence getPageTitle(int position) {
            return "title";
        }

        public Object instantiateItem(ViewGroup container, int position) {
            container.addView((View) GpsTargetActivity.this.viewList.get(position));
            return GpsTargetActivity.this.viewList.get(position);
        }
    };
    /* access modifiers changed from: private */
    public int[] roundBg = {R.drawable.gps_round_bg, R.drawable.gps_round2_bg, R.drawable.gps_round3_bg, R.drawable.gps_round4_bg};
    private MyGpsSportDialog sportDialog;
    /* access modifiers changed from: private */
    public int sportType = 0;
    /* access modifiers changed from: private */
    public float targetNum = 0.0f;
    private ViewPager targetViewpager;
    /* access modifiers changed from: private */
    public List<View> viewList;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.setTopWindows(getWindow());
        setContentView(R.layout.sport_module_activity_gps_target);
        initData();
        initView();
        initLocation();
        addPagerListener();
        this.locationManager = (LocationManager) getSystemService("location");
        getSatellite();
    }

    private void addPagerListener() {
        this.targetViewpager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                GpsTargetActivity.this.indicator.setChanging(position, positionOffset);
            }

            public void onPageSelected(int position) {
                GpsTargetActivity.this.mPosition = position;
                Log.d("testGoal", "viewPager11滑动: " + position);
                GpsTargetActivity.this.indicator.setChangeOver(position);
                if (position < GpsTargetActivity.this.colorBg.length) {
                    GpsTargetActivity.this.gpsRelayoutBg.setBackground(GpsTargetActivity.this.colorBg[position]);
                    GpsTargetActivity.this.gpsGo.setBackgroundResource(GpsTargetActivity.this.roundBg[position]);
                    if (position > 0) {
                        GpsTargetActivity.this.targetNum = ((GpsGoalView) GpsTargetActivity.this.viewList.get(position)).getmNumber();
                    }
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initData() {
        if (AppConfigUtil.isHealthy(this)) {
            this.isHealthy = true;
        } else {
            this.isHealthy = false;
        }
        this.mShow_hr = getIntent().getBooleanExtra("show_hr", false);
        this.sportType = getIntent().getIntExtra("sport_type", 0);
        KLog.e(this.TAG, "sport_type:" + this.sportType);
        this.mPosition = 0;
        GradientDrawable defaultDr = new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.GpsTargetActy_Default_Bg_Color);
        GradientDrawable durationDr = new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.GpsTargetActy_Duration_Bg_Color);
        this.colorBg = new GradientDrawable[]{defaultDr, new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.GpsTargetActy_Distance_Bg_Color), durationDr, new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.GpsTargetActy_Calories_Bg_Color)};
        this.viewList = new ArrayList();
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                this.viewList.add(new GpsFreeView(this));
            } else {
                this.viewList.add(new GpsGoalView(this, i, this));
            }
        }
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.gps_model_back) {
            LocationImpl.getInstance().stopLocationAndServer(this);
            finish();
        } else if (i == R.id.gps_set_title) {
            showSportDialog();
        } else if (i == R.id.gps_go) {
            if (!XPermissionUtils.lacksPermissions(getApplicationContext(), "android.permission.ACCESS_COARSE_LOCATION", "android.permission.WRITE_EXTERNAL_STORAGE")) {
                gpsIsOk();
                return;
            }
            showMyDialog(0);
            this.nums = 1;
        }
    }

    public void saveGoal() {
        ((GpsGoalView) this.viewList.get(this.mPosition)).setStrToFloat();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        try {
            if (VERSION.SDK_INT >= 24) {
                this.gnss.onStopped();
                this.locationManager.unregisterGnssStatusCallback(this.gnss);
                this.gnss = null;
            } else {
                this.locationManager.removeGpsStatusListener(this.gpsS);
            }
            this.gpsS = null;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        super.onDestroy();
    }

    public void showInputDialog(int type) {
        KLog.d("no2855-->回调 " + type);
        if (this.inputDialog == null) {
            this.inputDialog = new MyInputDialog(this);
            this.inputDialog.setInputListener(this);
        }
        this.inputDialog.setType(type);
        this.inputDialog.show();
    }

    public void onInput(int chose) {
        ((GpsGoalView) this.viewList.get(this.mPosition)).setTxtNum(chose);
    }

    public void showSportDialog() {
        if (this.sportDialog == null) {
            this.sportDialog = new MyGpsSportDialog(this, this.sportType);
            this.sportDialog.setSportListener(this);
        }
        this.sportDialog.show();
    }

    public void onSport(int chose) {
        this.sportType = chose;
        changeSportTypeTitle(chose);
    }

    private void changeSportTypeTitle(int chose) {
        if (chose == 0) {
            this.gpsModelTitle.setText(R.string.sport_module_gps_chose_run);
        } else if (chose == 1) {
            this.gpsModelTitle.setText(R.string.sport_module_gps_chose_cycle);
        } else {
            this.gpsModelTitle.setText(R.string.sport_module_gps_chose_walk);
        }
    }

    private void initLocation() {
        XPermissionUtils.requestPermissions(this, 1, new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, this.locationListener);
    }

    /* access modifiers changed from: private */
    public void initsdWirte() {
        XPermissionUtils.requestPermissions(this, 4, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, new OnPermissionListener() {
            public void onPermissionGranted() {
                GpsTargetActivity.this.hasWrite = true;
            }

            public void onPermissionDenied() {
                GpsTargetActivity.this.hasWrite = false;
                Log.d("testLocation", "读写文件权限失败");
            }
        });
    }

    /* access modifiers changed from: private */
    public void getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(268435456);
        if (VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts(EnvConsts.PACKAGE_MANAGER_SRVNAME, getPackageName(), null));
        } else if (VERSION.SDK_INT <= 8) {
            localIntent.setAction("android.intent.action.VIEW");
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XPermissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void gpsIsOk() {
        LocationManager mLocationManager = (LocationManager) getSystemService("location");
        if (!mLocationManager.isProviderEnabled("gps") || !mLocationManager.isProviderEnabled("network")) {
            showMyDialog(1);
        } else {
            showGpsOkDialog();
        }
    }

    private void showMyDialog(int type) {
        Builder alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setTitle((CharSequence) getString(R.string.sport_module_prompt));
        alertDialogBuilder.setPositiveButton((CharSequence) getString(R.string.sport_module_ok), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                GpsTargetActivity.this.getAppDetailSettingIntent();
            }
        });
        if (type == 0) {
            alertDialogBuilder.setMessage((CharSequence) getString(R.string.sport_module_gps_go_location));
        } else {
            alertDialogBuilder.setMessage((CharSequence) getString(R.string.sport_module_gps_no_location));
        }
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void showGpsOkDialog() {
        this.gpscount = 3;
        if (this.gpscount <= 1) {
            Builder alertDialogBuilder = new Builder(this);
            alertDialogBuilder.setPositiveButton((CharSequence) getString(R.string.sport_module_carry_on), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("testGoal", "运动完设置的目标1: " + GpsTargetActivity.this.targetNum + " - " + GpsTargetActivity.this.mPosition);
                    LocationImpl.getInstance().setTarget(GpsTargetActivity.this.mPosition, GpsTargetActivity.this.targetNum);
                    Intent intent = new Intent(GpsTargetActivity.this, RunPrepareActivity.class);
                    intent.putExtra("position", GpsTargetActivity.this.mPosition);
                    intent.putExtra("type", 0);
                    intent.putExtra("sportType", GpsTargetActivity.this.sportType);
                    intent.putExtra("show_hr", GpsTargetActivity.this.mShow_hr);
                    GpsTargetActivity.this.startActivity(intent);
                    GpsTargetActivity.this.finish();
                }
            });
            alertDialogBuilder.setNegativeButton((CharSequence) getString(R.string.sport_module_cancel), (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialogBuilder.setMessage((CharSequence) getString(R.string.sport_module_gps_no_state));
            alertDialogBuilder.setCancelable(true);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();
            return;
        }
        Log.d("testGoal", "运动完设置的目标: " + this.targetNum + " - " + this.mPosition);
        LocationImpl.getInstance().setTarget(this.mPosition, this.targetNum);
        Intent intent = new Intent(this, RunPrepareActivity.class);
        intent.putExtra("position", this.mPosition);
        intent.putExtra("type", 0);
        intent.putExtra("sportType", this.sportType);
        intent.putExtra("show_hr", this.mShow_hr);
        startActivity(intent);
        finish();
    }

    public void setTargetNum(float targetNum2) {
        Log.d("testGoal", "no2855回调设置的目标: " + targetNum2);
        this.targetNum = targetNum2;
    }

    public void onBackPressed() {
        super.onBackPressed();
        LocationImpl.getInstance().stopLocationAndServer(this);
    }

    private void getSatellite() {
        if (VERSION.SDK_INT >= 24) {
            KLog.e(this.TAG, "getSatellite--getGnss");
            getGnss();
            return;
        }
        KLog.e(this.TAG, "getSatellite--getGpsSta");
        getGpsSta();
    }

    @RequiresApi(24)
    private void getGnss() {
        Log.d("testMain", "android 版本号: " + VERSION.SDK_INT);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            KLog.e(this.TAG, "getSatellite--getGnss1111");
            this.gnss = new Callback() {
                public void onStarted() {
                    super.onStarted();
                }

                public void onStopped() {
                    super.onStopped();
                }

                public void onFirstFix(int ttffMillis) {
                    super.onFirstFix(ttffMillis);
                }

                public void onSatelliteStatusChanged(GnssStatus status) {
                    super.onSatelliteStatusChanged(status);
                    GpsTargetActivity.this.gpscount = status.getSatelliteCount();
                    Log.d("testMain", "gnss微星数量: " + status.getSatelliteCount());
                }
            };
            this.locationManager.registerGnssStatusCallback(this.gnss);
        }
    }

    private void getGpsSta() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            KLog.e(this.TAG, "getSatellite--getGpsSta1111");
            this.locationManager.addGpsStatusListener(this.gpsS);
        }
    }

    private void initView() {
        this.gpsRelayoutBg = (RelativeLayout) findViewById(R.id.gps_relayout_bg);
        this.mGpsTitleRelayout = (RelativeLayout) findViewById(R.id.gps_title_relayout);
        this.gpsModelBack = (ImageView) findViewById(R.id.gps_model_back);
        this.gpsSetTitle = (LinearLayout) findViewById(R.id.gps_set_title);
        this.gpsModelTitle = (TextView) findViewById(R.id.gps_model_title);
        this.gpsTitleImg = (ImageView) findViewById(R.id.gps_title_img);
        this.mPagerLayout = (RelativeLayout) findViewById(R.id.pager_layout);
        this.targetViewpager = (ViewPager) findViewById(R.id.target_viewpager);
        this.indicator = (CircleIndicator) findViewById(R.id.indicator);
        this.gpsGo = (MyTextView) findViewById(R.id.gps_go);
        this.mModelLayout = (ModelLayout) findViewById(R.id.model_layout);
        this.gpsRelayoutBg.setPadding(0, WindowUtil.getStatusBarHeight(), 0, 0);
        this.targetViewpager.setAdapter(this.pagerAdapter);
        this.indicator.setViewPager(this.viewList.size());
        this.gpsModelBack.setOnClickListener(this);
        this.gpsSetTitle.setOnClickListener(this);
        this.gpsGo.setOnClickListener(this);
        this.mModelLayout.changeSurfaceQuadrangleBg(RunActivitySkin.RunActy_Item_BG);
        RelativeLayout dev_rl = (RelativeLayout) findViewById(R.id.hr_dev_connect_state);
        TextView dev_tv = (TextView) findViewById(R.id.dev_text);
        ImageView dot_iv = (ImageView) findViewById(R.id.dot_iv);
        if (this.mShow_hr) {
            dev_rl.setVisibility(0);
            if (ModuleRouteDeviceInfoService.getInstance().isWristConnected()) {
                dot_iv.setSelected(false);
                dev_tv.setText(UserConfig.getInstance().getDevice() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.device_module_ble_connect_statue_1));
            } else {
                dot_iv.setSelected(true);
                dev_tv.setText(UserConfig.getInstance().getDevice() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.device_module_ble_connect_statue_2));
            }
        } else {
            dev_rl.setVisibility(8);
        }
        changeSportTypeTitle(this.sportType);
    }

    private void onPermission() {
    }
}
