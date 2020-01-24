package com.iwown.healthy;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.healthy.zeroner_pro.R;
import com.iwown.data_link.AppManger;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.consts.UserConst.DrViva;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService.DeviceStatusListener;
import com.iwown.data_link.eventbus.BluetoothStatus;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.data_link.eventbus.FeedbackServiceEvent;
import com.iwown.data_link.eventbus.GoogleFitEvent;
import com.iwown.data_link.eventbus.HaveGetModelEvent;
import com.iwown.data_link.eventbus.LogOutEventBus;
import com.iwown.data_link.eventbus.OnkeyDown;
import com.iwown.data_link.eventbus.ToDeviceListFragmentEvent;
import com.iwown.data_link.googlefit.GoogleFitUtility;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.data_link.utils.PreferenceUtility;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.CurrData_0x29;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.UI;
import com.iwown.device_module.device_guide.DeviceGuideAcitvity;
import com.iwown.device_module.device_message_push.activity.NotificationAccessUtil;
import com.iwown.device_module.device_message_push.utils.ServiceUtils;
import com.iwown.device_module.device_operation.type.DeviceListActivity;
import com.iwown.device_module.device_operation.type.DeviceListFragment;
import com.iwown.device_module.device_setting.fragment.DeviceFragment;
import com.iwown.device_module.device_setting.view.dialog.TipDialogRemind;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.healthy.view.ImageBtn;
import com.iwown.lib_common.activity.SupportActivity;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.lib_common.fragment.SupportFragment;
import com.iwown.lib_common.log.L;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.my_module.fragment.ProfileFragment;
import com.iwown.my_module.model.response.MarketInfo;
import com.iwown.my_module.model.response.MarketInfoResponse;
import com.iwown.my_module.network.MyRetrofitClient;
import com.iwown.my_module.network.UserService;
import com.iwown.sport_module.Fragment.SportFragment;
import com.iwown.sport_module.gps.GpsAmapService;
import com.iwown.sport_module.gps.service.GpsGoogleNewService;
import com.iwown.sport_module.service.IntentSendUtils;
import com.iwown.sport_module.util.WindowUtil;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Route(path = "/app/MainActivity")
public class MainActivity extends SupportActivity implements OnClickListener {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int SECOND_1 = 3;
    public static final int THIRD = 2;
    private int currTab;
    private ImageBtn device;
    int deviceList;
    long exitTime = 0;
    private FragmentManager fm;
    private DeviceStatusListener listener;
    private SupportFragment[] mFragments = new SupportFragment[4];
    private ImageBtn my;
    /* access modifiers changed from: private */
    public TipDialogRemind permissionDialog;
    private ImageView serviceBackImg;
    private ImageBtn sport;
    private TimeReceiver timeReceiver;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.setTopWindows(getWindow());
        setContentView((int) R.layout.activity_main);
        this.fm = getSupportFragmentManager();
        EventBus.getDefault().register(this);
        if (this.timeReceiver == null) {
            IntentFilter filter3 = new IntentFilter();
            filter3.addAction("android.intent.action.TIME_TICK");
            this.timeReceiver = new TimeReceiver();
            try {
                MyApplicationLike.getInstance().registerReceiver(this.timeReceiver, filter3);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        loadView();
        initView();
        ServiceUtils.toggleNotificationListenerService();
        uploadGoogleFit();
        IntentSendUtils.sendUploadAllData(this);
        AppManger.getAppManager().finishAllActivity(this);
        try {
            if (VERSION.SDK_INT >= 26) {
                startForegroundService(new Intent(this, TimeService.class));
            } else {
                startService(new Intent(this, TimeService.class));
            }
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        initEvent();
        tryStopGpsService();
        if (AppConfigUtil.isDrviva()) {
            getMarketInfo();
        }
    }

    private void tryStopGpsService() {
        stopService(new Intent(this, GpsAmapService.class));
        stopService(new Intent(this, GpsGoogleNewService.class));
    }

    private void initEvent() {
        this.device.performClick();
        if (this.deviceList == 1) {
            setTabSelection(3);
        } else {
            setTabSelection(0);
        }
        if (!NotificationAccessUtil.isEnabled(this)) {
            showConfirmDialog();
        }
        try {
            ModuleRouteDeviceInfoService.getInstance().userBindWifiScale(new PreferenceUtility(this).fetchLongValueWithKey(UserConst.UID));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    private void loadView() {
        this.deviceList = getIntent().getIntExtra(RouteUtils.Device_List_Key, 0);
        if (!UserConfig.getInstance().isPremissionDialog()) {
            this.permissionDialog = new TipDialogRemind(this, false);
            this.permissionDialog.setOnlyOneBT(true);
            this.permissionDialog.setClickCallBack(new ClickCallBack() {
                public void onOk() {
                    UserConfig.getInstance().setPremissionDialog(true);
                    PermissionsUtils.handPermission(MainActivity.this);
                    MainActivity.this.permissionDialog.dismiss();
                }

                public void onCancel() {
                }
            });
            this.permissionDialog.show();
            this.permissionDialog.setTitleMsg(getString(R.string.device_module_app_background_title));
            this.permissionDialog.setContentMsgLeft(getString(R.string.device_module_premission_dialog, new Object[]{AppConfigUtil.app_name}));
            this.permissionDialog.setBt_okText(getString(R.string.device_module_app_background_ok));
            this.permissionDialog.setCancelable(false);
            return;
        }
        PermissionsUtils.handPermission(this);
    }

    private void initView() {
        this.sport = (ImageBtn) findViewById(R.id.button_sport);
        this.device = (ImageBtn) findViewById(R.id.button_device);
        this.my = (ImageBtn) findViewById(R.id.button_my);
        this.serviceBackImg = (ImageView) findViewById(R.id.service_back_img);
        this.sport.setOnClickListener(this);
        this.device.setOnClickListener(this);
        this.my.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EventBus.getDefault().post(new BluetoothStatus(requestCode));
    }

    private void clearSelection() {
        this.sport.setImageIcon(R.mipmap.homepage_data_up);
        this.sport.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
        this.my.setImageIcon(R.mipmap.homepage_profile_up);
        this.my.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
        this.device.setImageIcon(R.mipmap.homepage_device_up);
        this.device.setTextColor(getResources().getColor(R.color.common_fragment_tab_whilt));
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        try {
            AppManger.getAppManager().getActivitys();
            UserConfig.getInstance().initInfoFromOtherModule();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    @SuppressLint({"NewApi"})
    private void showConfirmDialog() {
        new Builder(this).setMessage(getString(R.string.device_module_notification_push_open)).setTitle(getString(R.string.device_module_notification_push_title)).setIconAttribute(16843605).setCancelable(true).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                MainActivity.this.openNotificationAccess();
            }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).create().show();
    }

    /* access modifiers changed from: private */
    public void openNotificationAccess() {
        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    public void setTabSelection(int index) {
        this.currTab = index;
        FragmentTransaction transaction = this.fm.beginTransaction();
        hideFragments(transaction);
        clearSelection();
        switch (index) {
            case 0:
                UserConfig.getInstance().setTab(0);
                this.sport.setImageIcon(R.mipmap.homepage_data_down);
                this.sport.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
                if (this.mFragments[0] != null) {
                    transaction.show(this.mFragments[0]);
                    break;
                } else {
                    this.mFragments[0] = SportFragment.newInstance();
                    transaction.add((int) R.id.fl_tab_container, (Fragment) this.mFragments[0]);
                    break;
                }
            case 1:
                UserConfig.getInstance().setTab(1);
                this.device.setImageIcon(R.mipmap.homepage_device_down);
                this.device.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
                if (this.mFragments[1] != null) {
                    transaction.show(this.mFragments[1]);
                    break;
                } else {
                    this.mFragments[1] = DeviceFragment.newInstance();
                    transaction.add((int) R.id.fl_tab_container, (Fragment) this.mFragments[1]);
                    break;
                }
            case 2:
                UserConfig.getInstance().setTab(2);
                this.my.setImageIcon(R.mipmap.homepage_profile_down);
                this.my.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
                if (this.mFragments[2] != null) {
                    transaction.show(this.mFragments[2]);
                    break;
                } else {
                    this.mFragments[2] = ProfileFragment.newInstance();
                    transaction.add((int) R.id.fl_tab_container, (Fragment) this.mFragments[2]);
                    break;
                }
            case 3:
                UserConfig.getInstance().setTab(1);
                this.device.setImageIcon(R.mipmap.homepage_device_down);
                this.device.setTextColor(getResources().getColor(R.color.common_fragment_tab_blue));
                if (this.mFragments[3] != null) {
                    transaction.show(this.mFragments[3]);
                    break;
                } else {
                    this.mFragments[3] = DeviceListFragment.newInstance();
                    transaction.add((int) R.id.fl_tab_container, (Fragment) this.mFragments[3]);
                    break;
                }
        }
        transaction.commitAllowingStateLoss();
        UserConfig.getInstance().save();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (this.mFragments[0] != null) {
            transaction.hide(this.mFragments[0]);
        }
        if (this.mFragments[1] != null) {
            transaction.hide(this.mFragments[1]);
        }
        if (this.mFragments[2] != null) {
            transaction.hide(this.mFragments[2]);
        }
        if (this.mFragments[3] != null) {
            transaction.hide(this.mFragments[3]);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void toDevListFrag(ToDeviceListFragmentEvent event) {
        UI.startActivity(this, DeviceListActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchPage(DevicePageSwitch devicePageSwitch) {
        if (devicePageSwitch.getAction().equals(DevicePageSwitch.Device_Setting)) {
            setTabSelection(1);
        } else if (devicePageSwitch.getAction().equals(DevicePageSwitch.Device_Check_List)) {
            setTabSelection(3);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CurrData_0x29 event) {
        Log.d("MainActivity", "MainActivity--收到29hsunixn ");
        try {
            uploadGoogleFit();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HaveGetModelEvent event) {
        KLog.d("MainActivity", "MainActivity--收到HaveGetModelEvent");
        goToGuide(event.getModel());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventFeedback(FeedbackServiceEvent event) {
        if (event.type == 0) {
            if (this.mFragments[2] != null && (this.mFragments[2] instanceof ProfileFragment)) {
                ((ProfileFragment) this.mFragments[2]).getServiceFeedback();
            }
        } else if (event.type != 1) {
            if (this.mFragments[2] != null && (this.mFragments[2] instanceof ProfileFragment)) {
                ((ProfileFragment) this.mFragments[2]).resrshRobotView();
            }
            this.serviceBackImg.setVisibility(4);
        } else if (this.serviceBackImg != null) {
            this.serviceBackImg.setVisibility(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventLoginOut(LogOutEventBus event) {
        stopService(new Intent(this, TimeService.class));
        L.file("用户退出登录，解除绑定-->", 4);
        BluetoothOperation.unBund();
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventGoogleFit(GoogleFitEvent event) {
        GoogleFitUtility.createInstance().initContext(this);
        GoogleFitUtility.createInstance().uploadGoogleFitData();
    }

    private void goToGuide(String dev_model) {
        int guide_style;
        if (!TextUtils.isEmpty(dev_model)) {
            Intent intent = new Intent(this, DeviceGuideAcitvity.class);
            if (dev_model.equalsIgnoreCase("I6HC") || dev_model.equalsIgnoreCase("I6H6") || dev_model.equalsIgnoreCase("I6HA")) {
                guide_style = 1;
            } else {
                String str = "";
                if (dev_model.length() >= 3) {
                    String new_str = dev_model.substring(0, 3);
                    if (new_str.equalsIgnoreCase("I7F")) {
                        guide_style = 2;
                    } else if (new_str.equalsIgnoreCase("R1N")) {
                        guide_style = 3;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            if (guide_style == 1) {
                return;
            }
            if (guide_style == 2) {
                if (!UserConfig.getInstance().isHasGuideI7A()) {
                    intent.putExtra("guide_style", 2);
                    startActivity(intent);
                    UserConfig.getInstance().setHasGuideI7A(true);
                    UserConfig.getInstance().save();
                    KLog.d("手环支持新手引导：" + dev_model);
                }
            } else if (!UserConfig.getInstance().isHasGuideR1()) {
                intent.putExtra("guide_style", 3);
                startActivity(intent);
                UserConfig.getInstance().setHasGuideR1(true);
                UserConfig.getInstance().save();
                KLog.d("手环支持新手引导：" + dev_model);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (System.currentTimeMillis() - this.exitTime > 2000) {
                if (this.currTab == 1) {
                    EventBus.getDefault().post(new OnkeyDown());
                } else {
                    Toast.makeText(this, getResources().getString(R.string.app_into_the_background1, new Object[]{AppConfigUtil.app_name}), 0).show();
                }
                this.exitTime = System.currentTimeMillis();
            } else {
                moveTaskToBack(true);
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void uploadGoogleFit() {
        KLog.e("licl", "googlefit: " + GlobalUserDataFetcher.getGoogleFitConnectStatus(this));
        if (GlobalUserDataFetcher.getGoogleFitConnectStatus(this) == 1) {
            GoogleFitUtility.createInstance().initContext(this);
            GoogleFitUtility.createInstance().uploadGoogleFitData();
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_sport) {
            setTabSelection(0);
        } else if (id == R.id.button_device) {
            if (BluetoothOperation.isBind() || BluetoothOperation.isConnected() || S2WifiUtils.s2WifiConfigMacIsOK(ContextUtil.getLUID())) {
                setTabSelection(1);
            } else {
                setTabSelection(3);
            }
        } else if (id == R.id.button_my) {
            setTabSelection(2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void getMarketInfo() {
        Log.i("MainActivity", "getMarketInfo");
        UserService mUserService = (UserService) MyRetrofitClient.getAPIRetrofit().create(UserService.class);
        final PreferenceUtility mPrefUtil = new PreferenceUtility(this);
        mUserService.getMarketInfo(22).enqueue(new Callback<MarketInfoResponse>() {
            public void onResponse(Call<MarketInfoResponse> call, Response<MarketInfoResponse> response) {
                Log.i("MainActivity", "getMarketInfo-response");
                if (response != null && response.body() != null && ((MarketInfoResponse) response.body()).getRetCode() == 0 && ((MarketInfoResponse) response.body()).getData() != null) {
                    MarketInfo marketInfo = ((MarketInfoResponse) response.body()).getData();
                    if (marketInfo != null) {
                        mPrefUtil.updateStrValueWithKey(DrViva.FacebookMsg, marketInfo.getFb_sub());
                        mPrefUtil.updateStrValueWithKey(DrViva.YoutubeMsg, marketInfo.getYtb_sub());
                        mPrefUtil.updateStrValueWithKey(DrViva.TwitterMsg, marketInfo.getTwt_sub());
                        mPrefUtil.updateStrValueWithKey(DrViva.MarketNotice, marketInfo.getAd_msg());
                        mPrefUtil.updateStrValueWithKey(DrViva.MarketUrl, marketInfo.getAd_url());
                        if (!TextUtils.isEmpty(marketInfo.getAd_url())) {
                            Log.i("MainActivity", "adurl:" + marketInfo.getAd_url());
                        }
                    }
                }
            }

            public void onFailure(Call<MarketInfoResponse> call, Throwable t) {
            }
        });
    }
}
