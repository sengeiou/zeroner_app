package com.iwown.sport_module.gps.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.eventbus.RealTimeHREvent;
import com.iwown.data_link.user_pre.ModuleRouteUserInfoService;
import com.iwown.data_link.user_pre.UserInfo;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.MediaPlayerHelper;
import com.iwown.sport_module.gps.data.GpsMsgData;
import com.iwown.sport_module.gps.data.GpsTimeEvent;
import com.iwown.sport_module.gps.data.GpsVoiceLink;
import com.iwown.sport_module.gps.data.MapActyUIEvent;
import com.iwown.sport_module.gps.fragment.GaodeFragment;
import com.iwown.sport_module.gps.fragment.GoogleFragment;
import com.iwown.sport_module.gps.service.LocationImpl;
import com.iwown.sport_module.gps.view.GpsMainLayout;
import com.iwown.sport_module.pojo.WristConnectStateEvent;
import com.iwown.sport_module.util.WindowUtil;
import com.socks.library.KLog;
import java.util.Iterator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MapActivity extends FragmentActivity implements OnClickListener {
    private String TAG = getClass().getSimpleName();
    private FragmentManager fragmentManager;
    private GaodeFragment gaodeFragment;
    private Callback gnss;
    private GpsVoiceLink goodSport;
    private GoogleFragment googleFragment;
    Listener gpsS;
    public String gpsStatue = "";
    TextView gpsStatues;
    int gpscount = 0;
    private boolean hasHighHeart = false;
    private int heartNum = 0;
    private GpsVoiceLink highSport;
    private boolean isCanUser;
    private boolean isHealthy;
    private boolean isSportLow = true;
    private long lastHeartTime = 0;
    private long lastTime = 0;
    private int lastTime2 = 0;
    /* access modifiers changed from: private */
    public LocationManager locationManager;
    private GpsVoiceLink lowSport;
    private Context mContext;
    private LinearLayout mGpsRnssLl;
    private FrameLayout mMapLayout;
    private int mPosition;
    private BroadcastReceiver mScreenOffReceiver;
    private boolean mShowHr;
    RelativeLayout mapAllLayout;
    RelativeLayout mapMainLayout;
    GpsMainLayout mapMsgLayout;
    ImageView mapOut;
    ImageView mapReset;
    private int mapType;
    private int minHeart = 0;
    /* access modifiers changed from: private */
    public int sportType;
    ImageView statuesImg;
    private int timeCount = 1;
    private int times;
    /* access modifiers changed from: private */
    public float weight = 60.0f;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        getWindow().addFlags(67108864);
        setContentView(R.layout.sport_module_activity_map);
        if (AppConfigUtil.isHealthy(this)) {
            this.isHealthy = true;
        } else {
            this.isHealthy = false;
        }
        this.mShowHr = getIntent().getBooleanExtra("show_hr", false);
        initView();
        EventBus.getDefault().register(this);
        initData();
        UserInfo userInfo = ModuleRouteUserInfoService.getInstance().getUserInfo(this);
        int myAge = userInfo.age;
        int maxHeart = 220 - userInfo.age;
        this.weight = (float) userInfo.weight;
        KLog.d("no2855 gps年纪是多少 " + maxHeart + " - " + myAge);
        if (this.sportType == 0) {
            MediaPlayerHelper.getInstance().playStart();
        }
        this.lowSport = new GpsVoiceLink(17, (int) (((double) maxHeart) * 0.6d), 15);
        this.highSport = new GpsVoiceLink(200, (int) (((double) maxHeart) * 0.9d), 150);
        this.goodSport = new GpsVoiceLink(30, (int) (((double) maxHeart) * 0.6d), 20);
    }

    private void initView() {
        this.sportType = getIntent().getIntExtra("sportType", 0);
        this.mapType = getIntent().getIntExtra("type", 0);
        this.mPosition = getIntent().getIntExtra("position", 0);
        this.lastTime = System.currentTimeMillis();
        this.mapAllLayout = (RelativeLayout) findViewById(R.id.map_all_layout);
        this.mMapLayout = (FrameLayout) findViewById(R.id.map_layout);
        this.mapMainLayout = (RelativeLayout) findViewById(R.id.map_main_layout);
        this.mapOut = (ImageView) findViewById(R.id.map_out);
        this.mapReset = (ImageView) findViewById(R.id.map_reset);
        this.mapMsgLayout = (GpsMainLayout) findViewById(R.id.map_msg_layout);
        this.mapMsgLayout.setSupportRealTimeHeart(this.mShowHr, this.sportType);
        this.gpsStatues = (TextView) findViewById(R.id.gps_statues);
        this.statuesImg = (ImageView) findViewById(R.id.gps_statues_img);
        this.mapOut.setOnClickListener(this);
        this.mapReset.setOnClickListener(this);
        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        if (!this.isHealthy) {
            this.googleFragment = new GoogleFragment();
            transaction.add(R.id.map_layout, (Fragment) this.googleFragment);
        } else {
            this.gaodeFragment = new GaodeFragment();
            transaction.add(R.id.map_layout, (Fragment) this.gaodeFragment);
        }
        transaction.commitAllowingStateLoss();
        this.mGpsRnssLl = (LinearLayout) findViewById(R.id.gps_rnss_ll);
        this.mGpsRnssLl.setTranslationY((float) WindowUtil.getStatusBarHeight());
    }

    private void initData() {
        this.locationManager = (LocationManager) getSystemService("location");
        this.isCanUser = false;
        this.mapMsgLayout.setView(this.mPosition);
        this.mapMsgLayout.initData(this.mPosition);
        getSatellite();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                LocationImpl.getInstance().start(MapActivity.this.sportType, MapActivity.this.weight);
            }
        }, 200);
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.map_out) {
            this.mapMsgLayout.enterOfAnim();
        } else if (i == R.id.map_reset) {
            resetLocationMap();
        }
    }

    private void resetLocationMap() {
        if (this.isHealthy) {
            this.gaodeFragment.resetLocation();
        } else {
            this.googleFragment.resetLocation();
        }
    }

    public void resetUser(boolean isCanUser2) {
        this.isCanUser = isCanUser2;
        if (this.isHealthy) {
            this.gaodeFragment.resetUser(isCanUser2);
        } else {
            this.googleFragment.resetUser(isCanUser2);
        }
    }

    public void onBackPressed() {
        if (this.mapMsgLayout == null) {
            return;
        }
        if (this.isCanUser) {
            this.isCanUser = false;
            this.mapMsgLayout.enterOfAnim();
        } else if (!this.mapMsgLayout.isEnd) {
            Toast.makeText(this, getString(R.string.sport_module_gps_need_stop), 0).show();
        } else {
            finish();
        }
    }

    private void getSatellite() {
        if (VERSION.SDK_INT >= 24) {
            getGnss();
        } else if (this.mapType != 1) {
            getGpsSta();
        }
    }

    @RequiresApi(24)
    private void getGnss() {
        Log.d("testMain", "android 版本号: " + VERSION.SDK_INT);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
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
                    MapActivity.this.setGpsStatues(status.getSatelliteCount());
                    Log.d("testMain", "gnss微星数量: " + status.getSatelliteCount());
                }
            };
            this.locationManager.registerGnssStatusCallback(this.gnss);
        }
    }

    private void getGpsSta() {
        Log.d("testMain", "啦啦啦啦啦: ");
        this.gpsS = new Listener() {
            public void onGpsStatusChanged(int event) {
                if (event == 3) {
                    Log.d("testMain", "gps微星初始化: ");
                } else if (event == 4 && ActivityCompat.checkSelfPermission(MapActivity.this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                    GpsStatus gpsStauts = MapActivity.this.locationManager.getGpsStatus(null);
                    int maxSatellites = gpsStauts.getMaxSatellites();
                    MapActivity.this.gpscount = 0;
                    Iterator<GpsSatellite> it = gpsStauts.getSatellites().iterator();
                    while (it.hasNext() && MapActivity.this.gpscount <= maxSatellites) {
                        if (((GpsSatellite) it.next()).usedInFix()) {
                            MapActivity.this.gpscount++;
                        }
                    }
                    Log.d("testMain", "gps微星初始化数量:" + maxSatellites + " -- " + MapActivity.this.gpscount);
                    MapActivity.this.setGpsStatues(MapActivity.this.gpscount);
                }
            }
        };
        this.locationManager.addGpsStatusListener(this.gpsS);
    }

    public void setGpsStatues(int number) {
        this.gpsStatues.setText("GPS");
        if (number <= 0) {
            this.statuesImg.setImageResource(R.mipmap.gps_st0);
        } else if (number <= 2) {
            this.statuesImg.setImageResource(R.mipmap.gps_st1);
        } else if (number <= 5) {
            this.statuesImg.setImageResource(R.mipmap.gps_st2);
        } else {
            this.statuesImg.setImageResource(R.mipmap.gps_st3);
        }
    }

    public void setMainMsg(GpsMsgData data) {
        this.mapMsgLayout.reshView(data);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        try {
            if (ModuleRouteDeviceInfoService.getInstance().isWristConnected()) {
                L.file("mapActivity destroy->", 3);
            }
            if (this.mScreenOffReceiver != null) {
                unregisterReceiver(this.mScreenOffReceiver);
            }
            if (VERSION.SDK_INT >= 24) {
                this.gnss.onStopped();
                this.locationManager.unregisterGnssStatusCallback(this.gnss);
                this.gnss = null;
                return;
            }
            this.locationManager.removeGpsStatusListener(this.gpsS);
            this.gpsS = null;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GpsTimeEvent event) {
        boolean isTimeOk;
        boolean isCanPlay = true;
        this.times = event.getTime();
        this.mapMsgLayout.changeTime(event.getTime());
        if (this.sportType == 0 && this.mShowHr) {
            this.timeCount++;
            if (this.timeCount % 4 == 0) {
                this.timeCount = 1;
                if (this.highSport.isFull()) {
                    int sum = 0;
                    Iterator it = this.highSport.getHeartList().iterator();
                    while (it.hasNext()) {
                        if (((Integer) it.next()).intValue() >= this.highSport.getHeartNum()) {
                            sum++;
                            if (sum >= this.highSport.getPlayHeartNum()) {
                                break;
                            }
                        }
                    }
                    if (sum >= this.highSport.getPlayHeartNum()) {
                        this.highSport.clearList();
                        L.file("语音播报-->gps运动强度过高,放慢: ", 3);
                        MediaPlayerHelper.getInstance().playSlowDown();
                    }
                }
            } else if (this.timeCount % 3 == 0) {
                if (this.goodSport.getVoiceTime() == 0 || this.times - this.goodSport.getVoiceTime() > 300) {
                    isTimeOk = true;
                } else {
                    isTimeOk = false;
                }
                if (isTimeOk && this.goodSport.isFull()) {
                    boolean isOk = true;
                    int nums = 0;
                    int sum2 = 0;
                    KLog.e("no2855-> " + JsonTool.toJson(this.goodSport.getHeartList()));
                    Iterator it2 = this.goodSport.getHeartList().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Integer integer = (Integer) it2.next();
                        if (nums < this.goodSport.getPlayHeartNum()) {
                            if (integer.intValue() < this.goodSport.getHeartNum()) {
                                sum2++;
                            }
                        } else if (integer.intValue() < this.goodSport.getHeartNum()) {
                            isOk = false;
                            break;
                        }
                        nums++;
                    }
                    if (isOk && sum2 >= 10) {
                        this.goodSport.setVoiceTime(this.times);
                        L.file("语音播报-->gps运动强度继续保持: ", 3);
                        MediaPlayerHelper.getInstance().playHeightSport();
                    }
                }
            } else if (this.times <= 300 && this.times % 2 == 0 && this.lowSport.isFull() && this.isSportLow) {
                int sum3 = 0;
                Iterator it3 = this.lowSport.getHeartList().iterator();
                while (it3.hasNext()) {
                    if (((Integer) it3.next()).intValue() >= this.lowSport.getHeartNum()) {
                        sum3++;
                    }
                }
                if (sum3 >= this.lowSport.getPlayHeartNum()) {
                    this.isSportLow = false;
                }
            }
            if (this.times < 300 || this.times - 300 >= 5 || !this.isSportLow) {
                isCanPlay = false;
            }
            if (isCanPlay) {
                this.isSportLow = false;
                L.file("语音播报-->gps运动强度低，跑快一点: ", 3);
                MediaPlayerHelper.getInstance().playHurryUp();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUiEventArrive(MapActyUIEvent event) {
        KLog.e(this.TAG, "MapActyUIEvent: " + event.getState());
        switch (event.getState()) {
            case 0:
                resetUser(true);
                return;
            case 1:
                resetUser(false);
                return;
            case 2:
                pauseLocation();
                return;
            case 3:
                finish();
                return;
            default:
                return;
        }
    }

    private void resiScreen() {
        if (this.mScreenOffReceiver == null) {
            this.mScreenOffReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                        Intent mLockIntent = new Intent(context, LockScreenActivity.class);
                        mLockIntent.addFlags(536870912);
                        MapActivity.this.startActivity(mLockIntent);
                    } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                        Intent mLockIntent2 = new Intent(context, LockScreenActivity.class);
                        mLockIntent2.addFlags(536870912);
                        MapActivity.this.startActivity(mLockIntent2);
                    }
                }
            };
            IntentFilter mScreenOffFilter = new IntentFilter();
            mScreenOffFilter.addAction("android.intent.action.SCREEN_OFF");
            mScreenOffFilter.addAction("android.intent.action.SCREEN_ON");
            registerReceiver(this.mScreenOffReceiver, mScreenOffFilter);
        }
    }

    public void pauseLocation() {
        if (this.googleFragment != null) {
            this.googleFragment.pauseLocation();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RealTimeHREvent event) {
        this.mapMsgLayout.refreshHrValue(event.getHr());
        this.lastTime = System.currentTimeMillis();
        if (event.getHr() > 20 && this.sportType == 0) {
            if (this.times <= 300) {
                this.lowSport.addLinkList(event.getHr());
            }
            this.highSport.addLinkList(event.getHr());
            this.goodSport.addLinkList(event.getHr());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(WristConnectStateEvent event) {
        KLog.e(this.TAG, "设备连接状态改变：" + event.isConnected());
        this.mapMsgLayout.refreshConnectState(event);
    }

    public void restartLocation() {
    }
}
