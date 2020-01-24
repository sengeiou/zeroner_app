package com.iwown.device_module.device_setting.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Preconditions;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.common.base.Charsets;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.blood.BpPreUpload;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.resp.DeviceSettingRemote;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean.SettingBean;
import com.iwown.device_module.common.sql.DevicePref;
import com.iwown.device_module.common.sql.TB_DeviceSettings;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.HealthyUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.UI;
import com.iwown.device_module.common.view.LSettingItem;
import com.iwown.device_module.common.view.LSettingItem.OnLSettingItemClick;
import com.iwown.device_module.common.view.LSubSettingItem;
import com.iwown.device_module.common.view.LSubSettingItem.OnLSubSettingItemClick;
import com.iwown.device_module.common.view.SettingItems;
import com.iwown.device_module.common.view.dialog.EditTextDialog;
import com.iwown.device_module.common.view.dialog.EditTextDialog.OnTextConfirmListener;
import com.iwown.device_module.common.view.dialog.PreDialog;
import com.iwown.device_module.device_add_sport.activity.AddSupportSportsActivity;
import com.iwown.device_module.device_alarm_schedule.activity.AlarmScheduleActivity;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddSchedulePresenter;
import com.iwown.device_module.device_blood.BloodOldsetActivity;
import com.iwown.device_module.device_blood.BloodSettingActivity;
import com.iwown.device_module.device_blood.BloodTwoSetActivity;
import com.iwown.device_module.device_camera.activity.CameraActivity;
import com.iwown.device_module.device_camera.activity.CameraActivity2;
import com.iwown.device_module.device_firmware_upgrade.activity.FirmwareUpgradeActivity;
import com.iwown.device_module.device_long_sit.activity.LongSeatActivity;
import com.iwown.device_module.device_message_push.activity.MsgPushActivity;
import com.iwown.device_module.device_setting.backlight.BackLightActivity;
import com.iwown.device_module.device_setting.configure.DevicePrefBiz;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.SettingDefault;
import com.iwown.device_module.device_setting.configure.eventbus.UpdateConfigUI;
import com.iwown.device_module.device_setting.fragment.SettingContract.Presenter;
import com.iwown.device_module.device_setting.fragment.SettingContract.View;
import com.iwown.device_module.device_setting.gueture.GestureActivity;
import com.iwown.device_module.device_setting.heart.AutoHeartActivity;
import com.iwown.device_module.device_setting.heart.HeartGuidanceActivity;
import com.iwown.device_module.device_setting.heart.HeartPresenter;
import com.iwown.device_module.device_setting.heart.bean.AutoHeartStatue;
import com.iwown.device_module.device_setting.language.LanguageActivity;
import com.iwown.device_module.device_setting.language.LanguageUtil;
import com.iwown.device_module.device_setting.view.dialog.TipDialogRemind;
import com.iwown.device_module.device_setting.wifi_scale.util.S2WifiUtils;
import com.iwown.device_module.device_vibration.VibrationSettingActivity;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.lib_common.fragment.SupportFragment;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.iwown.lib_common.toast.CustomToast;
import com.socks.library.KLog;
import com.tencent.connect.common.Constants;
import java.util.List;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;

public class IvSettingFragment extends SupportFragment implements OnClickListener, View {
    public static int[] historyBp;
    public static int retry;
    private boolean AutoHeartTimeSelection;
    private SettingItems addAlarm;
    private SettingItems addSport;
    LottieAnimationView animView;
    /* access modifiers changed from: private */
    public TipDialogRemind appBackgroundDialog;
    private LSettingItem autoBlackTime;
    private LSettingItem autoBlood;
    private LSettingItem autoCamera;
    private LSettingItem autoDateFormat;
    /* access modifiers changed from: private */
    public LSettingItem autoFirmwareUpgrade;
    private LSettingItem autoHeartGuidance;
    private LSettingItem autoHeartRate;
    private LSettingItem autoLanguageSwitch;
    private LSettingItem autoLed;
    private LSettingItem autoPalmGesture;
    private LSettingItem autoRecognition1;
    private LSettingItem autoScreenColor;
    private LSettingItem autoTimeFormat;
    private LSettingItem autoUnitSwitch;
    private LSettingItem autoVibration;
    private LSettingItem autoWearingManager;
    private LSettingItem autoWeatherFormat;
    private LSettingItem auto_af;
    private TextView bandNameTv;
    private ImageView batteryIv;
    private TextView batteryPercent;
    private ImageView bracelIv;
    private TextView braceletReconnect;
    private TextView braceletUnbind;
    private TextView braceletUnbind_1;
    private RelativeLayout centerContentRl;
    /* access modifiers changed from: private */
    public boolean checkFirmware;
    private Runnable connectDeviceRunnable = new Runnable() {
        public void run() {
            BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
            BluetoothOperation.setNeedReconnect(true);
        }
    };
    private Runnable connectRunnable = new Runnable() {
        public void run() {
            IvSettingFragment.this.connectDevice();
        }
    };
    private TextView connectStateTv;
    /* access modifiers changed from: private */
    public ConstraintLayout constraintLayout;
    private LinearLayout containerLayout;
    private Context context;
    private ImageView dotIv;
    /* access modifiers changed from: private */
    public LSubSettingItem doubleTouchSwitch;
    private ConstraintLayout errorFailTip;
    /* access modifiers changed from: private */
    public TipDialogRemind firmWareDialog;
    /* access modifiers changed from: private */
    public boolean gestureTimeSelection;
    /* access modifiers changed from: private */
    public boolean isNoDisturbSelect;
    private boolean isVisible;
    /* access modifiers changed from: private */
    public EditTextDialog mEditTextDialog = null;
    private TextView mErrorTipTextView;
    private Handler mHandler = new Handler(Looper.myLooper());
    private HeartPresenter mHeartPresenter = new HeartPresenter();
    /* access modifiers changed from: private */
    public boolean mOpenHr = false;
    /* access modifiers changed from: private */
    public Presenter mPresenter = new SettingPresenter(this);
    private String[] mTimeArr;
    private String[] mTimeArr2;
    private LSettingItem noDisturb;
    private android.view.View paddingView;
    Runnable preDialogDismiss = new Runnable() {
        public void run() {
            IvSettingFragment.this.constraintLayout.setVisibility(8);
            if (IvSettingFragment.this.animView != null) {
                IvSettingFragment.this.animView.cancelAnimation();
            }
        }
    };
    BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (IvSettingFragment.this.autoFirmwareUpgrade != null) {
                IvSettingFragment.this.autoFirmwareUpgrade.setFirmwareTag(false);
            }
        }
    };
    private SettingItems sedentaryReminder;
    private SettingItems smartReminder;
    /* access modifiers changed from: private */
    public LSettingItem standardHeartSwitch;
    /* access modifiers changed from: private */
    public TipDialogRemind tipDialog;
    TextView toast_msg;
    /* access modifiers changed from: private */
    public PreDialog unbindDialog;
    Runnable unbindDialogDismiss = new Runnable() {
        public void run() {
            if (IvSettingFragment.this.unbindDialog != null) {
                IvSettingFragment.this.unbindDialog.dismiss();
            }
            BluetoothOperation.disconnectWhenUnbindTimeOut(false);
            if (S2WifiUtils.s2WifiConfigMacIsOK(ContextUtil.getLUID())) {
                EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Setting));
            } else {
                EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Check_List));
            }
            EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Top_Unbind_Device));
        }
    };
    android.view.View view;
    /* access modifiers changed from: private */
    public LSettingItem wearableRecognize;
    private LSettingItem welcomePageSetting;

    public static IvSettingFragment newInstance() {
        Bundle args = new Bundle();
        IvSettingFragment fragment = new IvSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.receiver, new IntentFilter("com.hardware.update.success"));
        this.mPresenter.subscribe();
    }

    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.device_module_fragment_iv_setting, container, false);
        initView(this.view);
        this.isVisible = true;
        updateModel();
        checkBluetoothSwitch();
        return this.view;
    }

    public void onResume() {
        super.onResume();
        initData();
        this.isVisible = true;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mPresenter.unsubscribe();
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.receiver);
    }

    private void initView(android.view.View view2) {
        this.mEditTextDialog = new EditTextDialog(getActivity(), "");
        this.mTimeArr = view2.getResources().getStringArray(R.array.device_module_time);
        this.mTimeArr2 = getResources().getStringArray(R.array.device_module_time_1_24);
        this.bracelIv = (ImageView) view2.findViewById(R.id.bracel_iv);
        this.centerContentRl = (RelativeLayout) view2.findViewById(R.id.center_content_rl);
        this.bandNameTv = (TextView) view2.findViewById(R.id.band_name_tv);
        this.dotIv = (ImageView) view2.findViewById(R.id.dot_iv);
        this.connectStateTv = (TextView) view2.findViewById(R.id.connect_state_tv);
        this.batteryIv = (ImageView) view2.findViewById(R.id.battery_iv);
        this.batteryPercent = (TextView) view2.findViewById(R.id.battery_percent);
        this.addAlarm = (SettingItems) view2.findViewById(R.id.add_alarm);
        this.sedentaryReminder = (SettingItems) view2.findViewById(R.id.sedentary_reminder);
        this.smartReminder = (SettingItems) view2.findViewById(R.id.smart_reminder);
        this.addSport = (SettingItems) view2.findViewById(R.id.add_sport);
        this.autoCamera = (LSettingItem) view2.findViewById(R.id.auto_camera);
        this.autoHeartRate = (LSettingItem) view2.findViewById(R.id.auto_heart_rate);
        this.autoHeartGuidance = (LSettingItem) view2.findViewById(R.id.auto_heart_guidance);
        this.autoVibration = (LSettingItem) view2.findViewById(R.id.auto_vibration);
        this.autoLanguageSwitch = (LSettingItem) view2.findViewById(R.id.auto_language_switch);
        this.autoTimeFormat = (LSettingItem) view2.findViewById(R.id.auto_time_format);
        this.autoDateFormat = (LSettingItem) view2.findViewById(R.id.auto_date_format);
        this.autoWeatherFormat = (LSettingItem) view2.findViewById(R.id.auto_weather_format);
        this.autoPalmGesture = (LSettingItem) view2.findViewById(R.id.auto_palm_gesture);
        this.autoWearingManager = (LSettingItem) view2.findViewById(R.id.auto_wearing_manager);
        this.autoRecognition1 = (LSettingItem) view2.findViewById(R.id.auto_recognition_1);
        this.autoScreenColor = (LSettingItem) view2.findViewById(R.id.auto_screen_color);
        this.autoBlackTime = (LSettingItem) view2.findViewById(R.id.auto_black_time);
        this.autoFirmwareUpgrade = (LSettingItem) view2.findViewById(R.id.auto_firmware_upgrade);
        this.doubleTouchSwitch = (LSubSettingItem) view2.findViewById(R.id.double_touch_switch);
        this.autoBlood = (LSettingItem) view2.findViewById(R.id.auto_blood);
        this.wearableRecognize = (LSettingItem) view2.findViewById(R.id.wear_recognize);
        this.standardHeartSwitch = (LSettingItem) view2.findViewById(R.id.standard_hr_switch);
        this.welcomePageSetting = (LSettingItem) view2.findViewById(R.id.set_welcome);
        this.noDisturb = (LSettingItem) view2.findViewById(R.id.no_disturb);
        this.auto_af = (LSettingItem) view2.findViewById(R.id.auto_af);
        this.paddingView = view2.findViewById(R.id.padding_container);
        this.constraintLayout = (ConstraintLayout) view2.findViewById(R.id.dialog_show);
        this.animView = (LottieAnimationView) view2.findViewById(R.id.pre_loading);
        this.toast_msg = (TextView) view2.findViewById(R.id.toast_msg);
        this.containerLayout = (LinearLayout) view2.findViewById(R.id.container_layout);
        this.autoUnitSwitch = (LSettingItem) view2.findViewById(R.id.auto_unit_switch);
        this.autoLed = (LSettingItem) view2.findViewById(R.id.auto_led);
        this.braceletUnbind = (TextView) view2.findViewById(R.id.bracelet_unbind);
        this.braceletUnbind_1 = (TextView) view2.findViewById(R.id.bracelet_unbind_1);
        this.braceletReconnect = (TextView) view2.findViewById(R.id.bracelet_reconnect);
        this.errorFailTip = (ConstraintLayout) view2.findViewById(R.id.connect_fail_bracelet);
        this.braceletUnbind_1.setOnClickListener(this);
        this.braceletReconnect.setOnClickListener(this);
        this.addAlarm.setOnClickListener(this);
        this.sedentaryReminder.setOnClickListener(this);
        this.addSport.setOnClickListener(this);
        this.smartReminder.setOnClickListener(this);
        this.braceletUnbind.setOnClickListener(this);
        this.doubleTouchSwitch.setOnClickListener(this);
        this.wearableRecognize.setOnClickListener(this);
        this.standardHeartSwitch.setOnClickListener(this);
        this.welcomePageSetting.setOnClickListener(this);
        if (PrefUtil.getInt(this._mActivity, SharedPreferencesAction.EARPHONE) == 1) {
            this.bracelIv.setImageResource(R.mipmap.earphone_head_3x);
        }
        view2.findViewById(R.id.head_iv_bracelet).setOnClickListener(this);
        this.mErrorTipTextView = (TextView) view2.findViewById(R.id.connect_fail_error_tip);
        if (BluetoothOperation.isMTKEarphone()) {
            KLog.e("is earphone");
            this.mErrorTipTextView.setText(getActivity().getResources().getText(R.string.device_module_connect_fail_tip) + "\n" + getActivity().getResources().getText(R.string.device_module_connect_fail_tip_headset));
        }
        initData();
        initEvent();
        Factory.fromAssetFileName(this._mActivity, "loading.json", new OnCompositionLoadedListener() {
            @SuppressLint({"WrongConstant"})
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                IvSettingFragment.this.animView.setComposition(composition);
                IvSettingFragment.this.animView.setImageAssetsFolder("airbnb_loading/");
                IvSettingFragment.this.animView.setRepeatMode(-1);
                IvSettingFragment.this.animView.loop(true);
            }
        });
        this.bracelIv.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View v) {
            }
        });
    }

    private void initEvent() {
        this.mEditTextDialog.setOnTextConfirmListener(new OnTextConfirmListener() {
            public void OnConfirm(String text) {
                KLog.e("OnConfirm", text);
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                if (TextUtils.isEmpty(text)) {
                    local.setWelcome_text("there");
                } else {
                    local.setWelcome_text(text);
                }
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setWelcomePage();
                DeviceUtils.writeCommandToDevice(35);
                IvSettingFragment.this.mEditTextDialog.dismiss();
            }

            public void OnTextChanged(String text) {
                if (TextUtils.isEmpty(text)) {
                    IvSettingFragment.this.mEditTextDialog.setCanConfirm(true);
                } else if (text.getBytes(Charsets.UTF_8).length > 10) {
                    IvSettingFragment.this.mEditTextDialog.setCanConfirm(false);
                } else {
                    IvSettingFragment.this.mEditTextDialog.setCanConfirm(true);
                }
            }

            public void OnCancel() {
            }
        });
        this.autoHeartRate.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                UI.startActivity(IvSettingFragment.this._mActivity, AutoHeartActivity.class);
            }
        });
        this.autoHeartGuidance.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                UI.startActivity(IvSettingFragment.this._mActivity, HeartGuidanceActivity.class);
            }
        });
        this.autoCamera.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                PermissionsUtils.handleCAMER(IvSettingFragment.this._mActivity, new PermissinCallBack() {
                    public void callBackOk() {
                        if (VERSION.SDK_INT >= 26) {
                            UI.startActivity(IvSettingFragment.this._mActivity, CameraActivity2.class);
                        } else {
                            UI.startActivity(IvSettingFragment.this._mActivity, CameraActivity.class);
                        }
                    }

                    public void callBackFial() {
                    }
                });
            }
        });
        this.autoVibration.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                UI.startActivity(IvSettingFragment.this._mActivity, VibrationSettingActivity.class);
            }
        });
        this.autoBlood.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                SharedPreferences read = IvSettingFragment.this.getActivity().getSharedPreferences("bloodhistory", 0);
                int Onesbp_lb = read.getInt("Onesbp_lb", 0);
                int Onedbp_lb = read.getInt("Onedbp_lb", 0);
                int Twosbp_lb = read.getInt("Twosbp_lb", 0);
                int Twidbp_lb = read.getInt("Twodbp_lb", 0);
                int src_sbp = read.getInt("src_sbp", 0);
                int src_dbp = read.getInt("src_dbp", 0);
                int isupload = read.getInt("Isuoload", 0);
                if (Onesbp_lb <= 0 || Onedbp_lb <= 0 || Twosbp_lb <= 0 || Twidbp_lb <= 0 || src_sbp <= 0 || src_dbp <= 0) {
                    Log.e(AddSchedulePresenter.TAG, "click: 没有血压校准数据");
                    UI.startActivity(IvSettingFragment.this._mActivity, BloodSettingActivity.class);
                    return;
                }
                IvSettingFragment.historyBp = new int[]{Onesbp_lb, Onedbp_lb, Twosbp_lb, Twidbp_lb, src_sbp, src_dbp};
                if (isupload == 0) {
                    BpPreUpload prebloodData = new BpPreUpload();
                    prebloodData.setStandard_sbp_1st(BloodSettingActivity.Onesbp_lb);
                    prebloodData.setStandard_dbp_1st(BloodSettingActivity.Onedbp_lb);
                    prebloodData.setStandard_sbp_2nd(BloodTwoSetActivity.Twosbp_lb);
                    prebloodData.setStandard_dbp_2nd(BloodTwoSetActivity.Twidbp_lb);
                    prebloodData.setMeasured_sbp(src_sbp);
                    prebloodData.setMeasured_dbp(src_dbp);
                    prebloodData.setUid(UserConfig.getInstance().getNewUID());
                    NetFactory.getInstance().getClient(new MyCallback<Integer>() {
                        public void onSuccess(Integer integer) {
                            KLog.e("88808  uploadBloodpressure ---------111   :  " + integer);
                        }

                        public void onFail(Throwable e) {
                            KLog.e("88808  uploadBloodpressure data fai1");
                        }
                    }).uploadBloodpressure(prebloodData);
                }
                UI.startActivity(IvSettingFragment.this._mActivity, BloodOldsetActivity.class);
            }
        });
        this.autoLanguageSwitch.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                UI.startActivity(IvSettingFragment.this._mActivity, LanguageActivity.class);
            }
        });
        this.autoTimeFormat.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setTimeFormat(!local.isTimeFormat());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setTimeFormat();
                DeviceUtils.writeCommandToDevice(3);
            }
        });
        this.auto_af.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setIs24AfSwitch(!local.isIs24AfSwitch());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.set24aF();
                DeviceUtils.writeCommandToDevice(41);
            }
        });
        this.autoDateFormat.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setDateFormat(!local.isDateFormat());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setDateFormat();
                DeviceUtils.writeCommandToDevice(9);
            }
        });
        this.autoWeatherFormat.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                if (PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Weather_Int) == 0 && PrefUtil.getInt(ContextUtil.app, FirmwareAction.Firmware_Temperature_Int) == 0) {
                    CustomToast.makeText(IvSettingFragment.this._mActivity, IvSettingFragment.this.getString(R.string.device_module_cannot_set));
                    return;
                }
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setWeatherFormat(!local.isWeatherFormat());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setWeatherFormat();
                DeviceUtils.writeCommandToDevice(14);
            }
        });
        this.autoPalmGesture.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                Intent intent = new Intent(IvSettingFragment.this._mActivity, GestureActivity.class);
                intent.putExtra(GestureActivity.TimeSelect, IvSettingFragment.this.gestureTimeSelection);
                IvSettingFragment.this.startActivity(intent);
            }
        });
        this.autoWearingManager.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setWearingManager(!local.isWearingManager());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setWearingManager();
                DeviceUtils.writeCommandToDevice(27);
            }
        });
        this.autoRecognition1.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setAutoRecognitionMotion(!local.isAutoRecognitionMotion());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setAutoRecognitionManager();
                DeviceUtils.writeCommandToDevice(24);
            }
        });
        this.autoUnitSwitch.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setUnit(!local.isUnit());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setAutoUnitSwitch();
                DeviceUtils.writeCommandToDevice(2);
            }
        });
        this.autoScreenColor.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setScreenColor(!local.isScreenColor());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setScreenColor();
                DeviceUtils.writeCommandToDevice(6);
            }
        });
        this.autoLed.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                boolean z;
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                if (!local.isLed()) {
                    z = true;
                } else {
                    z = false;
                }
                local.setLed(z);
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                IvSettingFragment.this.setLed();
                DeviceUtils.writeCommandToDevice(0);
            }
        });
        this.autoBlackTime.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                UI.startActivity(IvSettingFragment.this._mActivity, BackLightActivity.class);
            }
        });
        this.autoFirmwareUpgrade.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                if (CommandOperation.isSync()) {
                    CustomToast.makeText(IvSettingFragment.this._mActivity, IvSettingFragment.this.getString(R.string.device_module_sync_now));
                    return;
                }
                if (PrefUtil.getBoolean(IvSettingFragment.this._mActivity, FirmwareAction.Firmware_post_version)) {
                    KLog.i("-----正在固件升级-----");
                }
                if (!SettingPresenter.isFastClick()) {
                    PrefUtil.save((Context) IvSettingFragment.this._mActivity, FirmwareAction.Firmware_post_version, true);
                    IvSettingFragment.this.view.postDelayed(new Runnable() {
                        public void run() {
                            PrefUtil.save((Context) IvSettingFragment.this._mActivity, FirmwareAction.Firmware_post_version, false);
                        }
                    }, 20000);
                    IvSettingFragment.this.mPresenter.checkFirmwareVersion();
                    IvSettingFragment.this.checkFirmware = true;
                    return;
                }
                KLog.i("快速点击");
            }
        });
        this.doubleTouchSwitch.setmOnLSettingItemClick(new OnLSubSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setDouble_touch_switch(!local.isDouble_touch_switch());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                if (local.isDouble_touch_switch()) {
                    IvSettingFragment.this.doubleTouchSwitch.setRightText(IvSettingFragment.this.getString(R.string.device_module_on));
                } else {
                    IvSettingFragment.this.doubleTouchSwitch.setRightText(IvSettingFragment.this.getString(R.string.device_module_off));
                }
                byte[] datas = MtkCmdAssembler.getInstance().setR1Switch(local.isDouble_touch_switch(), local.isWear_recognize_switch());
                KLog.e("---r1 setting write to device: " + DeviceUtils.byte2str(datas));
                MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, datas);
            }
        });
        this.wearableRecognize.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                DeviceSettingLocal local = IvSettingFragment.this.mPresenter.localDeviceSetting();
                local.setWear_recognize_switch(!local.isWear_recognize_switch());
                IvSettingFragment.this.mPresenter.saveLocalDeviceSetting(local);
                if (local.isWear_recognize_switch()) {
                    IvSettingFragment.this.wearableRecognize.setRightText(IvSettingFragment.this.getString(R.string.device_module_on));
                } else {
                    IvSettingFragment.this.wearableRecognize.setRightText(IvSettingFragment.this.getString(R.string.device_module_off));
                }
                byte[] datas = MtkCmdAssembler.getInstance().setR1Switch(local.isDouble_touch_switch(), local.isWear_recognize_switch());
                KLog.e("---r1 setting write to device: " + DeviceUtils.byte2str(datas));
                MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, datas);
            }
        });
        this.standardHeartSwitch.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                if (BluetoothOperation.isMTKEarphone()) {
                    IvSettingFragment.this.mOpenHr = !IvSettingFragment.this.mOpenHr;
                    MTKBle.getInstance().switchStandardHeartRate(IvSettingFragment.this.mOpenHr);
                    if (IvSettingFragment.this.mOpenHr) {
                        IvSettingFragment.this.standardHeartSwitch.setRightText(IvSettingFragment.this.getString(R.string.device_module_on));
                    } else {
                        IvSettingFragment.this.standardHeartSwitch.setRightText(IvSettingFragment.this.getString(R.string.device_module_off));
                    }
                }
            }
        });
        this.welcomePageSetting.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                IvSettingFragment.this.mEditTextDialog.show();
            }
        });
        this.noDisturb.setmOnLSettingItemClick(new OnLSettingItemClick() {
            public void click(int id, boolean isChecked) {
                Intent intent = new Intent(IvSettingFragment.this._mActivity, GestureActivity.class);
                intent.putExtra(GestureActivity.TimeSelect, IvSettingFragment.this.isNoDisturbSelect);
                intent.putExtra("TYPE", 1);
                IvSettingFragment.this.startActivity(intent);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public void checkBluetoothSwitch() {
        if (BluetoothOperation.isEnabledBluetooth()) {
            try {
                this.view.postDelayed(new Runnable() {
                    public void run() {
                        if (!TextUtils.isEmpty(PrefUtil.getString(IvSettingFragment.this._mActivity, BleAction.Bluetooth_Device_Name_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(IvSettingFragment.this._mActivity, BleAction.Bluetooth_Device_Address_Current_Device))) {
                            IvSettingFragment.this.connectDevice();
                        }
                        IvSettingFragment.this.connectViewSwitch();
                    }
                }, (long) 3000);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setTimeFormat() {
        if (this.mPresenter.localDeviceSetting().isTimeFormat()) {
            this.autoTimeFormat.setRightText(String.format(getResources().getString(R.string.device_module_time_format_24h), new Object[]{Constants.VIA_REPORT_TYPE_SET_AVATAR}));
            return;
        }
        this.autoTimeFormat.setRightText(String.format(getResources().getString(R.string.device_module_time_format_24h), new Object[]{"24"}));
    }

    /* access modifiers changed from: private */
    public void setAutoUnitSwitch() {
        if (this.mPresenter.localDeviceSetting().isUnit()) {
            this.autoUnitSwitch.setRightText(getString(R.string.unit_imperial));
        } else {
            this.autoUnitSwitch.setRightText(getString(R.string.unit_metric));
        }
    }

    private void setR1SettingControl() {
        if (this.mPresenter.localDeviceSetting().isDouble_touch_switch()) {
            this.doubleTouchSwitch.setRightText(getString(R.string.device_module_on));
        } else {
            this.doubleTouchSwitch.setRightText(getString(R.string.device_module_off));
        }
        if (this.mPresenter.localDeviceSetting().isWear_recognize_switch()) {
            this.wearableRecognize.setRightText(getString(R.string.device_module_on));
        } else {
            this.wearableRecognize.setRightText(getString(R.string.device_module_off));
        }
        this.standardHeartSwitch.setRightText(getString(R.string.device_module_off));
    }

    public void setDateFormat() {
        if (this.mPresenter.localDeviceSetting().isDateFormat()) {
            this.autoDateFormat.setRightText(getString(R.string.device_module_date_format_day));
        } else {
            this.autoDateFormat.setRightText(getString(R.string.device_module_date_format_month));
        }
    }

    public void setWelcomePage() {
        this.welcomePageSetting.setRightText(this.mPresenter.localDeviceSetting().getWelcome_text());
    }

    /* access modifiers changed from: private */
    public void setWeatherFormat() {
        if (this.mPresenter.localDeviceSetting().isWeatherFormat()) {
            this.autoWeatherFormat.setRightText(getString(R.string.i6_fahrenheit));
        } else {
            this.autoWeatherFormat.setRightText(getString(R.string.i6_centigrade));
        }
    }

    /* access modifiers changed from: private */
    public void setWearingManager() {
        if (this.mPresenter.localDeviceSetting().isWearingManager()) {
            this.autoWearingManager.setRightText(getString(R.string.device_module_right));
        } else {
            this.autoWearingManager.setRightText(getString(R.string.device_module_left));
        }
    }

    /* access modifiers changed from: private */
    public void setAutoRecognitionManager() {
        if (this.mPresenter.localDeviceSetting().isAutoRecognitionMotion()) {
            this.autoRecognition1.setRightText(getString(R.string.device_module_on));
        } else {
            this.autoRecognition1.setRightText(getString(R.string.device_module_off));
        }
    }

    /* access modifiers changed from: private */
    public void setLed() {
        if (this.mPresenter.localDeviceSetting().isLed()) {
            this.autoLed.setRightText(getString(R.string.device_module_on));
        } else {
            this.autoLed.setRightText(getString(R.string.device_module_off));
        }
    }

    /* access modifiers changed from: private */
    public void connectViewSwitch() {
        if (BluetoothOperation.isConnected()) {
            this.containerLayout.setVisibility(0);
            this.braceletUnbind_1.setVisibility(8);
            this.braceletReconnect.setVisibility(8);
            this.errorFailTip.setVisibility(8);
            return;
        }
        this.containerLayout.setVisibility(8);
        this.braceletUnbind_1.setVisibility(0);
        this.braceletReconnect.setVisibility(0);
        this.errorFailTip.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void setScreenColor() {
        if (this.mPresenter.localDeviceSetting().isScreenColor()) {
            this.autoScreenColor.setRightText(getString(R.string.device_module_white));
        } else {
            this.autoScreenColor.setRightText(getString(R.string.device_module_black));
        }
    }

    private void setAutoHeartAndHeartGuidance() {
        if (!this.mHeartPresenter.autoHeartStatue().isHeart_switch()) {
            this.autoHeartRate.setRightText(getString(R.string.device_module_off));
        } else if (this.AutoHeartTimeSelection) {
            if (this.mHeartPresenter.autoHeartStatue().getHeart_endTime() > 23) {
                AutoHeartStatue ads = DeviceUtils.autoHeartStatue();
                ads.setHeart_endTime(23);
                DeviceUtils.saveAutoHeartStatue(ads);
            }
            String start = this.mTimeArr[this.mHeartPresenter.autoHeartStatue().getHeart_startTime()];
            this.autoHeartRate.setRightText(start + HelpFormatter.DEFAULT_OPT_PREFIX + this.mTimeArr2[this.mHeartPresenter.autoHeartStatue().getHeart_endTime()]);
        } else {
            this.autoHeartRate.setRightText(getString(R.string.device_module_on));
        }
        if (this.mHeartPresenter.heartGuidanceStatue().isHeart_guidance_switch()) {
            this.autoHeartGuidance.setRightText(this.mHeartPresenter.heartGuidanceStatue().getMinHeart() + HelpFormatter.DEFAULT_OPT_PREFIX + this.mHeartPresenter.heartGuidanceStatue().getMaxHeart());
            return;
        }
        this.autoHeartGuidance.setRightText(getString(R.string.device_module_off));
    }

    private void setNoDisturb() {
        if (!this.mPresenter.localDeviceSetting().isNoDisturb()) {
            this.noDisturb.setRightText(getString(R.string.device_module_off));
        } else if (this.isNoDisturbSelect) {
            int startNoDisturbTime = this.mPresenter.localDeviceSetting().getStartNoDisturbTime();
            this.noDisturb.setRightText(this.mTimeArr[startNoDisturbTime < this.mTimeArr.length ? startNoDisturbTime : this.mTimeArr.length - 1] + HelpFormatter.DEFAULT_OPT_PREFIX + this.mTimeArr2[startNoDisturbTime < this.mTimeArr2.length ? this.mPresenter.localDeviceSetting().getEndNoDisturbTime() : this.mTimeArr2.length - 1]);
        } else {
            this.noDisturb.setRightText(getString(R.string.device_module_on));
        }
    }

    /* access modifiers changed from: private */
    public void set24aF() {
        if (this.mPresenter.localDeviceSetting().isIs24AfSwitch()) {
            this.auto_af.setRightText(getString(R.string.device_module_on));
        } else {
            this.auto_af.setRightText(getString(R.string.device_module_off));
        }
    }

    private void setBackLightTime() {
        String start = this.mTimeArr[this.mPresenter.localDeviceSetting().getBackLightStartTime()];
        this.autoBlackTime.setRightText(start + HelpFormatter.DEFAULT_OPT_PREFIX + this.mTimeArr2[this.mPresenter.localDeviceSetting().getBackLightEndTime()]);
    }

    private void setGestureTime() {
        if (!this.mPresenter.localDeviceSetting().isPalmingGesture()) {
            this.autoPalmGesture.setRightText(getString(R.string.device_module_off));
        } else if (this.gestureTimeSelection) {
            this.autoPalmGesture.setRightText(this.mTimeArr[this.mPresenter.localDeviceSetting().getPalmingGestureStart() < this.mTimeArr.length ? this.mPresenter.localDeviceSetting().getPalmingGestureStart() : this.mTimeArr.length - 1] + HelpFormatter.DEFAULT_OPT_PREFIX + this.mTimeArr2[this.mPresenter.localDeviceSetting().getPalmingGestureEnd() < this.mTimeArr2.length ? this.mPresenter.localDeviceSetting().getPalmingGestureEnd() : this.mTimeArr2.length - 1]);
        } else {
            this.autoPalmGesture.setRightText(getString(R.string.device_module_on));
        }
    }

    private void setLanguageStr() {
        String msg = LanguageUtil.getLanguageString(getContext(), this.mPresenter.localDeviceSetting().getLanguage());
        KLog.i("---------------------" + this.mPresenter.localDeviceSetting().getLanguage() + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + msg);
        this.autoLanguageSwitch.setRightText(msg);
    }

    private void initData() {
        if (AppConfigUtil.isHealthy()) {
            this.bandNameTv.setText(HealthyUtil.getNewName(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)));
        } else {
            this.bandNameTv.setText(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device));
        }
        connectViewSwitch();
        initBattery();
        setWelcomePage();
        setTimeFormat();
        setDateFormat();
        setWeatherFormat();
        setWearingManager();
        setGestureTime();
        setLanguageStr();
        setAutoRecognitionManager();
        setScreenColor();
        setAutoHeartAndHeartGuidance();
        setBackLightTime();
        setAutoUnitSwitch();
        setLed();
        showBraceletStatue();
        setR1SettingControl();
        setNoDisturb();
        set24aF();
        if (BluetoothOperation.isBind() && !BluetoothOperation.isConnected()) {
            showDialog(true);
            if (DeviceInitUtils.getInstance().getmBle() == null) {
                KLog.i("ZGIBle 未完成初始化");
                this.view.postDelayed(new Runnable() {
                    public void run() {
                        IvSettingFragment.this.connectDevice();
                    }
                }, 3000);
                return;
            }
            BluetoothOperation.startScan(this._mActivity);
            this.view.removeCallbacks(this.connectRunnable);
            this.view.postDelayed(this.connectRunnable, 3000);
        }
    }

    private void updateModel() {
        setDeviceViewVisible(DeviceUtils.getDeviceInfo().getModel());
    }

    private void initBattery() {
        int battery = this.mPresenter.getBatteryValue();
        KLog.i("battery" + battery);
        this.batteryPercent.setText(battery + "%");
        if (battery == 0) {
            this.batteryIv.setImageResource(R.mipmap.battery0);
        } else if (battery > 0 && battery < 20) {
            this.batteryIv.setImageResource(R.mipmap.battery20);
        } else if (battery >= 20 && battery < 40) {
            this.batteryIv.setImageResource(R.mipmap.battery40);
        } else if (battery >= 40 && battery < 60) {
            this.batteryIv.setImageResource(R.mipmap.battery60);
        } else if (battery >= 60 && battery < 80) {
            this.batteryIv.setImageResource(R.mipmap.battery80);
        } else if (battery >= 80 && battery <= 100) {
            this.batteryIv.setImageResource(R.mipmap.battery100);
        }
        this.autoFirmwareUpgrade.setRightText(DeviceUtils.getDeviceInfo().getSwversion());
    }

    private void showBraceletStatue() {
        if (BluetoothOperation.isConnected()) {
            this.connectStateTv.setText(R.string.device_module_ble_connect_statue_1);
        } else {
            this.connectStateTv.setText(R.string.device_module_ble_connect_statue_2);
        }
    }

    private void viewConnectStatue(boolean connect) {
        if (connect) {
            this.connectStateTv.setText(R.string.device_module_ble_connect_statue_1);
        } else {
            this.connectStateTv.setText(R.string.device_module_ble_connect_statue_2);
        }
        if (!connect) {
            showDialog(true);
        } else {
            showDialog(false);
        }
        connectViewSwitch();
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (this.mPresenter != null) {
            DeviceSettingRemote remote = new DeviceSettingRemote();
            remote.setUid(ContextUtil.getLUID());
            remote.setModel(DeviceUtils.getDeviceInfo().getModel());
            DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
            if (devicePref != null) {
                if (!TextUtils.isEmpty(devicePref.getSetting())) {
                    remote.setSetting(JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class));
                }
                this.mPresenter.uploadDevicePref(remote);
            }
            updateModel();
        }
    }

    public void onClick(android.view.View v) {
        int i = v.getId();
        if (i == R.id.add_alarm) {
            UI.startActivity(this._mActivity, AlarmScheduleActivity.class);
        } else if (i == R.id.sedentary_reminder) {
            UI.startActivity(this._mActivity, LongSeatActivity.class);
        } else if (i == R.id.add_sport) {
            UI.startActivity(this._mActivity, AddSupportSportsActivity.class);
        } else if (i == R.id.smart_reminder) {
            UI.startActivity(this._mActivity, MsgPushActivity.class);
        } else if (i == R.id.bracelet_unbind || i == R.id.bracelet_unbind_1) {
            showTipDialog();
        } else if (i != R.id.bracelet_reconnect) {
        } else {
            if (TextUtils.isEmpty(PrefUtil.getString(this._mActivity, BleAction.Bluetooth_Device_Name_Current_Device)) || TextUtils.isEmpty(PrefUtil.getString(this._mActivity, BleAction.Bluetooth_Device_Address_Current_Device))) {
                CustomToast.makeText(this._mActivity, getString(R.string.device_module_unbind_and_connect));
                return;
            }
            BluetoothOperation.checkBluetooth(this._mActivity, SettingPresenter.Bluetooth_Error_Open_Code);
            if (BluetoothOperation.isEnabledBluetooth()) {
                showDialog(true);
                KLog.i("============i==R.id.bracelet_reconnect================");
                BluetoothOperation.startScan(this._mActivity);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        IvSettingFragment.this.connectDevice();
                    }
                }, 2000);
                return;
            }
            KLog.e("蓝牙没有打开");
        }
    }

    public void onSupportVisible() {
        super.onSupportVisible();
        this.isVisible = true;
    }

    private void showDialog(boolean show) {
        if (UserConfig.getInstance().getTab() == 1) {
            try {
                if (!BluetoothOperation.isEnabledBluetooth()) {
                    if (this.constraintLayout.getVisibility() == 0) {
                        this.constraintLayout.setVisibility(8);
                        return;
                    }
                    return;
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            if (!show) {
                this.constraintLayout.setVisibility(8);
                if (this.animView != null) {
                    this.animView.cancelAnimation();
                    return;
                }
                return;
            }
            this.constraintLayout.setVisibility(0);
            this.animView.playAnimation();
            this.toast_msg.setText(getString(R.string.device_module_connecting));
            this.mHandler.postDelayed(this.preDialogDismiss, 90000);
        }
    }

    /* access modifiers changed from: private */
    public void showUnbindDialog(boolean show) {
        if (this.unbindDialog == null) {
            if (show) {
                this.unbindDialog = new PreDialog(this._mActivity);
                this.unbindDialog.show();
                this.unbindDialog.setMessage(getString(R.string.device_module_unbinding));
                this.mHandler.postDelayed(this.unbindDialogDismiss, BootloaderScanner.TIMEOUT);
            }
        } else if (!show) {
            this.unbindDialog.dismiss();
        } else {
            this.unbindDialog.show();
            this.unbindDialog.setMessage(getString(R.string.device_module_unbinding));
            this.mHandler.postDelayed(this.unbindDialogDismiss, BootloaderScanner.TIMEOUT);
        }
    }

    public void showTipDialog() {
        if (this.tipDialog == null) {
            this.tipDialog = new TipDialogRemind(this._mActivity, false);
            this.tipDialog.setClickCallBack(new ClickCallBack() {
                public void onOk() {
                    PrefUtil.save((Context) ContextUtil.app, UserAction.User_Firmware_Tag, false);
                    IvSettingFragment.this.mPresenter.unbindDevice();
                    IvSettingFragment.this.tipDialog.dismiss();
                    if (BluetoothOperation.isConnected()) {
                        IvSettingFragment.this.showUnbindDialog(true);
                        return;
                    }
                    if (S2WifiUtils.s2WifiConfigMacIsOK(ContextUtil.getLUID())) {
                        EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Setting));
                    } else {
                        EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Check_List));
                    }
                    EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Top_Unbind_Device));
                }

                public void onCancel() {
                    IvSettingFragment.this.tipDialog.dismiss();
                }
            });
        }
        this.tipDialog.show();
        this.tipDialog.setTitleMsg("");
        this.tipDialog.setContentMsg(getString(R.string.device_module_scale_wifi_data_belong_to_unbind));
        this.tipDialog.setBt_okText(getString(R.string.device_module_common_cormfir_yes));
        this.tipDialog.setBt_cancel(getString(R.string.device_module_device_setting_unbind_no));
    }

    public void showAppBackgroundDialog() {
        if (!AppConfigUtil.isUpfit() && !AppConfigUtil.isNanfei_TRAX_GPS() && !AppConfigUtil.isNewfit() && !this._mActivity.isFinishing() && BluetoothOperation.isBind()) {
            try {
                if (this.appBackgroundDialog == null) {
                    this.appBackgroundDialog = new TipDialogRemind(this._mActivity, false);
                    this.appBackgroundDialog.setClickCallBack(new ClickCallBack() {
                        public void onOk() {
                            String url;
                            IvSettingFragment.this.appBackgroundDialog.dismiss();
                            if (AppConfigUtil.isRussia(IvSettingFragment.this._mActivity)) {
                                url = "https://search.iwown.com/guide/bracelet/bracelet.html#/";
                            } else if (AppConfigUtil.isIwownFitPro()) {
                                url = "https://api4.iwown.com/setting/dist/index.html#/";
                            } else if (AppConfigUtil.isZeronerHealthPro()) {
                                url = "https://api4.iwown.com/setting/dist/index.html#/zhp/app";
                            } else if (AppConfigUtil.isHealthy(IvSettingFragment.this.getContext())) {
                                url = "https://api2.iwown.com/setting/zhushou/index.html#/china/app ";
                            } else if (AppConfigUtil.isDrviva()) {
                                url = "https://api4.iwown.com/setting/viva/index.html#/common/drviva";
                            } else {
                                url = "https://api4.iwown.com/setting/dist/index.html#/";
                            }
                            ARouter.getInstance().build(RouteUtils.Activity_my_app_background_Activity).withString("url", url).withString("title", ContextUtil.app.getString(R.string.my_module_background_permission)).navigation();
                        }

                        public void onCancel() {
                            IvSettingFragment.this.appBackgroundDialog.dismiss();
                        }
                    });
                }
                this.appBackgroundDialog.setOnlyOneBT(true);
                this.appBackgroundDialog.show();
                this.appBackgroundDialog.setTitleMsg(getString(R.string.device_module_app_background_title));
                this.appBackgroundDialog.setContentMsg(getString(R.string.device_module_app_background_content, AppConfigUtil.app_name));
                this.appBackgroundDialog.setBt_okText(getString(R.string.device_module_app_background_ok));
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public void showFirmwareTipDialog(String content, int cancelSwitch) {
        if (TextUtils.isEmpty(content)) {
            content = "";
        }
        if (BluetoothOperation.isBind() && BluetoothOperation.isConnected()) {
            if (DeviceUtils.getBattery() < 50) {
                CustomToast.makeText(this._mActivity, getString(R.string.device_module_dfu_prower_low));
                CommandOperation.getBattery();
                return;
            }
            KLog.e("============showDialog=============");
            if (this.firmWareDialog == null) {
                this.firmWareDialog = new TipDialogRemind(this._mActivity, false);
                this.firmWareDialog.setClickCallBack(new ClickCallBack() {
                    public void onOk() {
                        IvSettingFragment.this.firmWareDialog.dismiss();
                        KLog.e("============showDialog=============dismiss");
                        if (DeviceUtils.getBattery() < 50) {
                            CustomToast.makeText(IvSettingFragment.this._mActivity, IvSettingFragment.this.getString(R.string.device_module_dfu_prower_low));
                        } else {
                            UI.startActivity(IvSettingFragment.this._mActivity, FirmwareUpgradeActivity.class);
                        }
                    }

                    public void onCancel() {
                        PrefUtil.save((Context) ContextUtil.app, UserAction.User_Firmware_Tag, true);
                        IvSettingFragment.this.firmWareDialog.dismiss();
                    }
                });
            }
            if (cancelSwitch == 10) {
                this.firmWareDialog.setOnlyOneBT(true);
            }
            if (!this.firmWareDialog.isShowing()) {
                this.firmWareDialog.show();
                this.firmWareDialog.setTitleMsg(getString(R.string.device_module_setting_firmware_upgrade));
                this.firmWareDialog.setContentMsg(content);
                this.firmWareDialog.setBt_okText(getString(R.string.device_module_common_cormfir_yes));
                this.firmWareDialog.setBt_cancel(getString(R.string.device_module_device_setting_unbind_no));
            }
        }
    }

    @SuppressLint({"RestrictedApi"})
    public void setPresenter(@NonNull Presenter presenter) {
        this.mPresenter = (Presenter) Preconditions.checkNotNull(presenter);
    }

    public void connectStatue(boolean statue) {
        viewConnectStatue(statue);
    }

    public void updateConfigUi(String action) {
        initData();
        updateModel();
        if (action.equals(UpdateConfigUI.Config_Device_Fragment_Visable)) {
            checkBluetoothSwitch();
        }
    }

    public void upDateFirmwareUi(String content, int cancelSwitch, int errorCode) {
        updateModel();
        if (errorCode != 0) {
            if (!PrefUtil.getBoolean(ContextUtil.app, UserAction.User_Phone_App_Background)) {
                showAppBackgroundDialog();
                PrefUtil.save((Context) ContextUtil.app, UserAction.User_Phone_App_Background, true);
            }
            this.autoFirmwareUpgrade.setFirmwareTag(false);
            if (this.checkFirmware) {
                this.checkFirmware = false;
                CustomToast.makeText(this._mActivity, getString(R.string.device_module_setting_last_version));
                return;
            }
            return;
        }
        this.autoFirmwareUpgrade.setFirmwareTag(true);
        if (this.checkFirmware) {
            this.checkFirmware = false;
            if (DeviceUtils.getBattery() < 50) {
                CommandOperation.getBattery();
                CustomToast.makeText(this._mActivity, getString(R.string.device_module_dfu_prower_low));
                return;
            }
            UI.startActivity(this._mActivity, FirmwareUpgradeActivity.class);
        } else if (!PrefUtil.getBoolean(ContextUtil.app, UserAction.User_Firmware_Tag) || cancelSwitch == 10) {
            showFirmwareTipDialog(content, cancelSwitch);
        } else if (!PrefUtil.getBoolean(ContextUtil.app, UserAction.User_Phone_App_Background)) {
            showAppBackgroundDialog();
            PrefUtil.save((Context) ContextUtil.app, UserAction.User_Phone_App_Background, true);
        }
    }

    public void connectingView(int type) {
        if (type == 1) {
            showDialog(true);
        } else if (type == 2) {
            BluetoothOperation.startScan(this._mActivity);
            this.view.postDelayed(new Runnable() {
                public void run() {
                    IvSettingFragment.this.connectDevice();
                }
            }, BootloaderScanner.TIMEOUT);
        }
    }

    public void updateFirmwareUpgradeSuccess() {
        this.autoFirmwareUpgrade.setRightText(DeviceUtils.getDeviceInfo().getSwversion());
        this.autoFirmwareUpgrade.setFirmwareTag(false);
    }

    public void keyDownDismissDialog() {
        if (this.constraintLayout.getVisibility() == 0) {
            this.constraintLayout.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void connectDevice() {
        this.view.removeCallbacks(this.connectDeviceRunnable);
        this.view.postDelayed(this.connectDeviceRunnable, 500);
    }

    private void setDeviceViewVisible(String model) {
        TB_DeviceSettings tb_deviceSettings = DeviceSettingsBiz.getInstance().queryDevSettings();
        if (tb_deviceSettings == null) {
            KLog.e("tb_deviceSettings==null!!!!");
            DeviceSettingsBiz.getInstance().remoteDevice();
            retry++;
            if (retry > 3 && new DateUtil().getUnixTimestamp() - PrefUtil.getLong(ContextUtil.app, UserAction.User_Request_Remote_Setting_Time) > 0) {
                CommandOperation.getModel();
                PrefUtil.save((Context) ContextUtil.app, UserAction.User_Request_Remote_Setting_Time, new DateUtil().getUnixTimestamp() + 60);
                return;
            }
            return;
        }
        retry = 0;
        if (PrefUtil.getInt(getActivity(), SharedPreferencesAction.User_Sdk_type) == 2 && PrefUtil.getInt(this._mActivity, SharedPreferencesAction.EARPHONE) == 1) {
            this.addAlarm.setVisibility(8);
            this.sedentaryReminder.setVisibility(8);
            this.smartReminder.setVisibility(8);
            this.paddingView.setVisibility(0);
        }
        List<SettingBean> dev_settings = (List) new Gson().fromJson(tb_deviceSettings.getSetting(), new TypeToken<List<SettingBean>>() {
        }.getType());
        if (dev_settings == null || dev_settings.size() == 0) {
            DeviceSettingsBiz.getInstance().remoteDevice();
            return;
        }
        for (SettingBean setting : dev_settings) {
            if (setting != null) {
                switch (setting.getType()) {
                    case 0:
                        this.autoLed.setVisibility(0);
                        break;
                    case 1:
                        this.autoPalmGesture.setVisibility(0);
                        break;
                    case 2:
                        this.autoUnitSwitch.setVisibility(0);
                        break;
                    case 3:
                        this.autoTimeFormat.setVisibility(0);
                        break;
                    case 5:
                        this.autoBlackTime.setVisibility(0);
                        break;
                    case 6:
                        this.autoScreenColor.setVisibility(0);
                        break;
                    case 7:
                        this.autoLanguageSwitch.setVisibility(0);
                        break;
                    case 9:
                        this.autoDateFormat.setVisibility(0);
                        break;
                    case 10:
                        this.gestureTimeSelection = true;
                        break;
                    case 11:
                        this.autoHeartRate.setVisibility(0);
                        break;
                    case 12:
                        this.autoVibration.setVisibility(0);
                        break;
                    case 13:
                        this.autoHeartGuidance.setVisibility(0);
                        break;
                    case 14:
                        this.autoWeatherFormat.setVisibility(0);
                        break;
                    case 17:
                        this.addSport.setVisibility(0);
                        break;
                    case 18:
                        this.autoCamera.setVisibility(0);
                        break;
                    case 20:
                        this.autoFirmwareUpgrade.setVisibility(0);
                        break;
                    case 21:
                        HealthDataEventBus.updateAllUI();
                        break;
                    case 24:
                        this.autoRecognition1.setVisibility(0);
                        break;
                    case 26:
                        this.AutoHeartTimeSelection = true;
                        break;
                    case 27:
                        this.autoWearingManager.setVisibility(0);
                        break;
                    case 32:
                        this.doubleTouchSwitch.setVisibility(0);
                        break;
                    case 33:
                        this.wearableRecognize.setVisibility(0);
                        break;
                    case 34:
                        this.standardHeartSwitch.setVisibility(0);
                        break;
                    case 35:
                        this.welcomePageSetting.setVisibility(0);
                        break;
                    case 38:
                        this.autoBlood.setVisibility(0);
                        break;
                    case 39:
                        this.isNoDisturbSelect = true;
                        this.noDisturb.setVisibility(0);
                        break;
                    case 41:
                        this.isNoDisturbSelect = true;
                        this.auto_af.setVisibility(0);
                        break;
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0055 A[SYNTHETIC, Splitter:B:16:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005a A[SYNTHETIC, Splitter:B:19:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0080 A[SYNTHETIC, Splitter:B:37:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0085 A[SYNTHETIC, Splitter:B:40:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void copyBinFile() {
        /*
            r11 = this;
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.io.File r10 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r10 = r10.getAbsolutePath()
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r10 = "/Zeroner/cep_pak.bin"
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r8 = r9.toString()
            java.io.File r3 = new java.io.File
            r3.<init>(r8)
            boolean r9 = r3.exists()
            if (r9 != 0) goto L_0x005d
            android.support.v4.app.FragmentActivity r9 = r11.getActivity()
            android.content.res.AssetManager r0 = r9.getAssets()
            r4 = 0
            r6 = 0
            java.lang.String r9 = "cep_pak.bin"
            java.io.InputStream r4 = r0.open(r9)     // Catch:{ IOException -> 0x0096 }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0096 }
            r7.<init>(r3)     // Catch:{ IOException -> 0x0096 }
            r9 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r9]     // Catch:{ IOException -> 0x004e, all -> 0x0093 }
            r5 = -1
        L_0x0042:
            int r5 = r4.read(r1)     // Catch:{ IOException -> 0x004e, all -> 0x0093 }
            r9 = -1
            if (r5 == r9) goto L_0x005e
            r9 = 0
            r7.write(r1, r9, r5)     // Catch:{ IOException -> 0x004e, all -> 0x0093 }
            goto L_0x0042
        L_0x004e:
            r2 = move-exception
            r6 = r7
        L_0x0050:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ all -> 0x007d }
            if (r4 == 0) goto L_0x0058
            r4.close()     // Catch:{ IOException -> 0x0073 }
        L_0x0058:
            if (r6 == 0) goto L_0x005d
            r6.close()     // Catch:{ IOException -> 0x0078 }
        L_0x005d:
            return
        L_0x005e:
            if (r4 == 0) goto L_0x0063
            r4.close()     // Catch:{ IOException -> 0x006e }
        L_0x0063:
            if (r7 == 0) goto L_0x005d
            r7.close()     // Catch:{ IOException -> 0x0069 }
            goto L_0x005d
        L_0x0069:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x005d
        L_0x006e:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0063
        L_0x0073:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0058
        L_0x0078:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x005d
        L_0x007d:
            r9 = move-exception
        L_0x007e:
            if (r4 == 0) goto L_0x0083
            r4.close()     // Catch:{ IOException -> 0x0089 }
        L_0x0083:
            if (r6 == 0) goto L_0x0088
            r6.close()     // Catch:{ IOException -> 0x008e }
        L_0x0088:
            throw r9
        L_0x0089:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0083
        L_0x008e:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)
            goto L_0x0088
        L_0x0093:
            r9 = move-exception
            r6 = r7
            goto L_0x007e
        L_0x0096:
            r2 = move-exception
            goto L_0x0050
        */
        throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_setting.fragment.IvSettingFragment.copyBinFile():void");
    }
}
