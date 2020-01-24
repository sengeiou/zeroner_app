package com.iwown.device_module.device_firmware_upgrade.activity;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bluetooth.BleService;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.data.DateUtil;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.task.TaskHandler;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothCallbackReceiver;
import com.iwown.device_module.common.Bluetooth.receiver.zg.bean.ZGFirmwareUpgrade;
import com.iwown.device_module.common.Bluetooth.sync.iv.SyncData;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MtkSync;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_firmware_upgrade.Util;
import com.iwown.device_module.device_firmware_upgrade.bean.FwUpdateInfo;
import com.iwown.device_module.device_firmware_upgrade.dialog.UpgradeFailDialogRemind;
import com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler;
import com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadService;
import com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.DownloadServiceBiz;
import com.iwown.device_module.device_firmware_upgrade.eventbus.WakeUpEvent;
import com.iwown.device_module.device_firmware_upgrade.service.DfuService;
import com.iwown.device_module.device_firmware_upgrade.service.HardwareUpdateService;
import com.iwown.device_module.device_firmware_upgrade.service.IwownFotaService;
import com.iwown.device_module.device_firmware_upgrade.service.NewDfuService;
import com.iwown.device_module.device_firmware_upgrade.service.ScannerServiceParser;
import com.iwown.device_module.device_firmware_upgrade.view.ProgressBar;
import com.iwown.device_module.device_firmware_upgrade.view.UpdateTextView;
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.lib_common.dialog.DialogRemindStyle.ClickCallBack;
import com.iwown.lib_common.log.L;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.socks.library.KLog;
import coms.mediatek.wearable.WearableManager;
import java.io.File;
import no.nordicsemi.android.dfu.DfuBaseService;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = "/device/FirmwareUpgradeActivity")
public class FirmwareUpgradeActivity extends DeviceModuleBaseActivity implements View, OnClickListener {
    public static final String NOW_STEP = "now_step";
    private static final int REQUEST_BLUETOOTH = 222;
    private static final int REQUEST_UPDATE_SUCCESS = 331;
    private static final long SCAN_DURATION = 10000;
    /* access modifiers changed from: private */
    public boolean DFUFail_10 = false;
    /* access modifiers changed from: private */
    public boolean DFUFail_20 = false;
    /* access modifiers changed from: private */
    public boolean DFUFail_40 = false;
    /* access modifiers changed from: private */
    public boolean DFUFail_50 = false;
    /* access modifiers changed from: private */
    public boolean DFUFail_60 = false;
    private TextView errorText;
    UpgradeFailDialogRemind exitDialog;
    /* access modifiers changed from: private */
    public LottieAnimationView imgAnimationFirmware;
    private ValueAnimator mAnimator;
    private BleReceiver mBleReceiver = new BleReceiver();
    /* access modifiers changed from: private */
    public BluetoothAdapter mBluetoothAdapter;
    /* access modifiers changed from: private */
    public Context mContext;
    private BroadcastReceiver mDfuUpdateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS".equals(action)) {
                int progress = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", 0);
                int currentPart = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_PART_CURRENT", 1);
                int totalParts = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_PARTS_TOTAL", 1);
                KLog.e("num : " + progress);
                FirmwareUpgradeActivity.this.updateProgressBar(progress, currentPart, totalParts, false);
            } else if ("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR".equals(action)) {
                int error = intent.getIntExtra("no.nordicsemi.android.dfu.extra.EXTRA_DATA", 0);
                FirmwareUpgradeActivity.this.updateProgressBar(error, 0, 0, true);
                KLog.e("升级发生错误 code: " + error);
            }
        }
    };
    Runnable mFirmwareAidRunnable = new Runnable() {
        public void run() {
            FirmwareUpgradeActivity.this.goToStep(1);
        }
    };
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.myLooper());
    /* access modifiers changed from: private */
    public boolean mIsBacked = false;
    /* access modifiers changed from: private */
    public boolean mIsStartUpdate = false;
    /* access modifiers changed from: private */
    public LeScanCallback mLEScanCallback = new LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            try {
                String deviceName = device.getName();
                String str = "3";
                String address = device.getAddress();
                KLog.e("mac", device.getAddress() + "  = = " + PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac));
                if ((BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) && device.getAddress().equals(PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac))) {
                    if (!FirmwareUpgradeActivity.this.judgeRepeat(address)) {
                        L.file("==============MTK===========", 6);
                        FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mScanTimeOutRunnable);
                        FirmwareUpgradeActivity.this.stopScan();
                        FirmwareUpgradeActivity.this.updateUI(8, false);
                        Intent updateIntent = new Intent(FirmwareUpgradeActivity.this.mContext, IwownFotaService.class);
                        KLog.e("==============onlescan()============");
                        FirmwareUpgradeActivity.this.stopService(updateIntent);
                        File fir = FirmwareUpgradeActivity.this.createFile();
                        updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                        L.file("MTK搜索到MAC=================>application：" + address, 6);
                        updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", device.getName());
                        updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                        updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir.getPath());
                        updateIntent.putExtra(NewDfuService.EXTRA_DEVICE, device);
                        try {
                            if (VERSION.SDK_INT >= 26) {
                                FirmwareUpgradeActivity.this.startForegroundService(updateIntent);
                            } else {
                                FirmwareUpgradeActivity.this.startService(updateIntent);
                            }
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                        }
                        FirmwareUpgradeActivity.this.startHardwareUpdateService(false);
                        FirmwareUpgradeActivity.this.presenter.saveConnectTime();
                    }
                } else if (!FirmwareUpgradeActivity.this.presenter.isDialog() || !device.getAddress().equals(PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac))) {
                    if (!ScannerServiceParser.decodeDeviceAdvData(scanRecord, BaseActionUtils.UPDATE_SERVICE_MAIN_)) {
                        if (!ScannerServiceParser.decodeDeviceAdvData(scanRecord, BaseActionUtils.UPDATE_SERVICE_MAIN_DFU)) {
                            if (!FirmwareUpgradeActivity.this.judgeRepeat(address)) {
                                String fm_mac = PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac);
                                if (!FirmwareUpgradeActivity.this.isDestroyed() && !TextUtils.isEmpty(fm_mac)) {
                                    if (device.getAddress().equalsIgnoreCase(Util.getNewMac(fm_mac, 1))) {
                                        FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mScanTimeOutRunnable);
                                        FirmwareUpgradeActivity.this.stopScan();
                                        FirmwareUpgradeActivity.this.updateUI(8, false);
                                        Intent updateIntent2 = new Intent(FirmwareUpgradeActivity.this.mContext, DfuService.class);
                                        File fir2 = FirmwareUpgradeActivity.this.createFile();
                                        updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                                        KLog.e("====================" + fir2.getPath());
                                        updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir2.getPath());
                                        updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", ContextUtil.getDeviceNameCurr());
                                        updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE", 0);
                                        try {
                                            if (VERSION.SDK_INT >= 26) {
                                                FirmwareUpgradeActivity.this.startForegroundService(updateIntent2);
                                            } else {
                                                FirmwareUpgradeActivity.this.startService(updateIntent2);
                                            }
                                        } catch (Exception e2) {
                                            ThrowableExtension.printStackTrace(e2);
                                        }
                                        FirmwareUpgradeActivity.this.startHardwareUpdateService(false);
                                        FirmwareUpgradeActivity.this.presenter.saveConnectTime();
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                    }
                    if (Util.isNumber(deviceName.substring(deviceName.length() - 1, deviceName.length()))) {
                        KLog.e("==============8个长度application=========== 2 " + device.getAddress());
                        if (FirmwareUpgradeActivity.this.judgeRepeat(address)) {
                            KLog.i("==judgeRepeat return==");
                            return;
                        }
                        FirmwareUpgradeActivity.this.stopScan();
                        FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mScanTimeOutRunnable);
                        FirmwareUpgradeActivity.this.updateUI(8, false);
                        Intent updateIntent3 = new Intent(FirmwareUpgradeActivity.this.mContext, DfuService.class);
                        File fir3 = FirmwareUpgradeActivity.this.createFile();
                        Uri uri = Uri.fromFile(fir3);
                        updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                        KLog.e("搜索的MAC地址=================>application：" + address);
                        updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", device.getName());
                        updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                        updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir3.getPath());
                        KLog.i("=================" + fir3.getPath());
                        updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE", 0);
                        updateIntent3.putExtra(DfuBaseService.EXTRA_FILE_URI, uri);
                        try {
                            if (VERSION.SDK_INT >= 26) {
                                FirmwareUpgradeActivity.this.startForegroundService(updateIntent3);
                            } else {
                                FirmwareUpgradeActivity.this.startService(updateIntent3);
                            }
                        } catch (Exception e3) {
                            ThrowableExtension.printStackTrace(e3);
                        }
                        FirmwareUpgradeActivity.this.startHardwareUpdateService(false);
                        FirmwareUpgradeActivity.this.presenter.saveConnectTime();
                        return;
                    }
                    KLog.e("==============不是字母8个长度application=========== 3 " + device.getAddress());
                    if (FirmwareUpgradeActivity.this.judgeRepeat(address)) {
                        KLog.i("==judgeRepeat return==");
                        return;
                    }
                    FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mScanTimeOutRunnable);
                    FirmwareUpgradeActivity.this.stopScan();
                    FirmwareUpgradeActivity.this.updateUI(8, false);
                    Intent updateIntent4 = new Intent(FirmwareUpgradeActivity.this.mContext, DfuService.class);
                    File fir4 = FirmwareUpgradeActivity.this.createFile();
                    Uri uri2 = Uri.fromFile(fir4);
                    updateIntent4.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                    KLog.e("搜索的MAC=================>application：" + address);
                    updateIntent4.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", device.getName());
                    updateIntent4.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                    updateIntent4.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir4.getPath());
                    KLog.i("======================3===" + fir4.getPath());
                    updateIntent4.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE", 0);
                    updateIntent4.putExtra(DfuBaseService.EXTRA_FILE_URI, uri2);
                    try {
                        if (VERSION.SDK_INT >= 26) {
                            FirmwareUpgradeActivity.this.startForegroundService(updateIntent4);
                        } else {
                            FirmwareUpgradeActivity.this.startService(updateIntent4);
                        }
                    } catch (Exception e4) {
                        ThrowableExtension.printStackTrace(e4);
                    }
                    FirmwareUpgradeActivity.this.startHardwareUpdateService(false);
                    FirmwareUpgradeActivity.this.presenter.saveConnectTime();
                } else if (!FirmwareUpgradeActivity.this.judgeRepeat(address)) {
                    L.file("==============I6HR===========", 6);
                    FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mScanTimeOutRunnable);
                    FirmwareUpgradeActivity.this.stopScan();
                    FirmwareUpgradeActivity.this.updateUI(8, false);
                    Intent updateIntent5 = new Intent(FirmwareUpgradeActivity.this.mContext, NewDfuService.class);
                    File fir5 = FirmwareUpgradeActivity.this.createFile();
                    Uri fromFile = Uri.fromFile(fir5);
                    updateIntent5.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                    L.file("I6HR搜索的MAC=================>application：" + address, 6);
                    KLog.e("================================>application：" + address, Integer.valueOf(6));
                    updateIntent5.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", device.getName());
                    updateIntent5.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", address);
                    updateIntent5.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir5.getPath());
                    updateIntent5.putExtra(NewDfuService.EXTRA_DEVICE, device);
                    try {
                        if (VERSION.SDK_INT >= 26) {
                            FirmwareUpgradeActivity.this.startForegroundService(updateIntent5);
                        } else {
                            FirmwareUpgradeActivity.this.startService(updateIntent5);
                        }
                    } catch (Exception e5) {
                        ThrowableExtension.printStackTrace(e5);
                    }
                    FirmwareUpgradeActivity.this.startHardwareUpdateService(false);
                    FirmwareUpgradeActivity.this.presenter.saveConnectTime();
                }
            } catch (Exception e6) {
                ThrowableExtension.printStackTrace(e6);
                FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mScanTimeOutRunnable);
                FirmwareUpgradeActivity.this.stopScan();
                FirmwareUpgradeActivity.this.updateUI(8, true);
                KLog.e("Invalid data in Advertisement packet=+异常抛出 " + e6.toString());
            }
        }
    };
    private long mLastClickTime;
    private int mLastProgress;
    private long mLastTime = 0;
    /* access modifiers changed from: private */
    public int mNowStep;
    Runnable mScanTimeOutRunnable = new Runnable() {
        public void run() {
            String fm_mac = PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac);
            if (!FirmwareUpgradeActivity.this.isDestroyed() && !TextUtils.isEmpty(fm_mac)) {
                String newMac = Util.getNewMac(fm_mac, 1);
                L.file("没有搜索到手环,直连蓝牙地址 : " + newMac, 6);
                KLog.e("没有搜索到手环,直连蓝牙地址 : " + newMac, Integer.valueOf(6));
                FirmwareUpgradeActivity.this.stopScan();
                FirmwareUpgradeActivity.this.connect(newMac);
            }
        }
    };
    Runnable mWriteDFUTimeoutRunnable = new Runnable() {
        public void run() {
            KLog.i("------------mWriteDFUTimeoutRunnable-------------");
            FirmwareUpgradeActivity.this.updateUI(6, true);
        }
    };
    MyCallbackHandler myCallbackHandler = new MyCallbackHandler();
    /* access modifiers changed from: private */
    public FirmwareUpgradePresenter presenter;
    /* access modifiers changed from: private */
    public ProgressBar progressBar;
    /* access modifiers changed from: private */
    public TextView progressText;
    /* access modifiers changed from: private */
    public RelativeLayout startLayout;
    private Button startUpgrade;
    private UpdateTextView tvStep;
    private TextView tvTipDec;
    private TextView tvTipTitle;

    private class BleReceiver extends BluetoothCallbackReceiver {
        private BleReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            String action = intent.getAction();
            if (BleService.BLE_CHARACTERISTIC_WRITE.equals(action)) {
                byte[] data = intent.getByteArrayExtra("DATA");
                if (data.length == 4 && data[0] == 33 && data[1] == -1 && data[2] == 3 && data[3] == 0) {
                    KLog.e("写入DFU指令成功");
                    FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mWriteDFUTimeoutRunnable);
                    FirmwareUpgradeActivity.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!FirmwareUpgradeActivity.this.isDestroyed()) {
                                FirmwareUpgradeActivity.this.goToStep(7);
                            }
                        }
                    }, 3000);
                }
            } else if (com.iwown.ble_module.proto.ble.BleService.BLE_CHARACTERISTIC_WRITE.equals(action)) {
                byte[] data2 = intent.getByteArrayExtra("DATA");
                if (data2.length == 1 && data2[0] == 1) {
                    KLog.e("---------protobuf写入DFU指令成功");
                    FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mWriteDFUTimeoutRunnable);
                    FirmwareUpgradeActivity.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!FirmwareUpgradeActivity.this.isDestroyed()) {
                                FirmwareUpgradeActivity.this.goToStep(7);
                            }
                        }
                    }, 3000);
                }
            }
        }
    }

    private class MyCallbackHandler extends CallbackHandler {
        private int mProgress;

        private MyCallbackHandler() {
        }

        public void onLoading(long curr, long total) {
            super.onLoading(curr, total);
            if (!FirmwareUpgradeActivity.this.isBack()) {
                this.mProgress = (int) ((100 * curr) / total);
                int progress = (this.mProgress / 10) + 20;
                FirmwareUpgradeActivity.this.progressBar.setProgress(progress);
                FirmwareUpgradeActivity.this.progressText.setText(FirmwareUpgradeActivity.this.getString(R.string.device_module_update_progress, new Object[]{Integer.valueOf(progress)}));
                KLog.e("总文件大小：" + total + "=======================当前:" + curr);
                if (curr == 0) {
                    KLog.e("总文件大小：" + total + "=======================当前:" + curr);
                }
                if (curr == total) {
                    KLog.e("总文件大小：" + total + "=======================当前:" + curr);
                }
            }
        }

        public void onSuccess() {
            super.onSuccess();
            if (!FirmwareUpgradeActivity.this.isBack()) {
                FirmwareUpgradeActivity.this.stopService(new Intent(FirmwareUpgradeActivity.this.mContext, DownloadService.class));
                FirmwareUpgradeActivity.this.goToStep(6);
            }
        }

        public void onFailure(String strFail, int errorCode) {
            super.onFailure(strFail, errorCode);
            if (!FirmwareUpgradeActivity.this.isBack()) {
                L.file("错误信息" + strFail, 6);
                if (errorCode >= 400) {
                    KLog.e("文件下载地址错误 服务器error : " + errorCode);
                }
                FirmwareUpgradeActivity.this.stopService(new Intent(FirmwareUpgradeActivity.this.mContext, DownloadService.class));
                FirmwareUpgradeActivity.this.updateUI(5, true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_activity_new_hardware);
        init();
        initView();
        initListener();
        checkUpdateInfo();
        PermissionsUtils.handPermissionSTORAGE(this);
    }

    private void initView() {
        setLeftBackTo();
        setTitleText(getString(R.string.device_module_firmware_update));
        this.presenter = new FirmwareUpgradePresenter(this);
        this.imgAnimationFirmware = (LottieAnimationView) findViewById(R.id.img_animation_firmware);
        this.tvStep = (UpdateTextView) findViewById(R.id.tv_step);
        this.progressText = (TextView) findViewById(R.id.progress_text);
        this.progressBar = (ProgressBar) findViewById(R.id.progress);
        this.tvTipTitle = (TextView) findViewById(R.id.tv_tip_title);
        this.tvTipDec = (TextView) findViewById(R.id.tv_tip_dec);
        this.startLayout = (RelativeLayout) findViewById(R.id.start_layout);
        this.startUpgrade = (Button) findViewById(R.id.start_upgrade);
        this.errorText = (TextView) findViewById(R.id.error_message);
        this.startUpgrade.setOnClickListener(this);
        Factory.fromAssetFileName(this, "firmware.json", new OnCompositionLoadedListener() {
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                FirmwareUpgradeActivity.this.imgAnimationFirmware.setComposition(composition);
                FirmwareUpgradeActivity.this.imgAnimationFirmware.setImageAssetsFolder("airbnb_firmware/");
                FirmwareUpgradeActivity.this.imgAnimationFirmware.setRepeatMode(-1);
                FirmwareUpgradeActivity.this.imgAnimationFirmware.loop(true);
                FirmwareUpgradeActivity.this.imgAnimationFirmware.useHardwareAcceleration(true);
                FirmwareUpgradeActivity.this.imgAnimationFirmware.enableMergePathsForKitKatAndAbove(true);
            }
        });
    }

    private void init() {
        ContextUtil.isFirmwareUp = true;
        EventBus.getDefault().register(this);
        this.mNowStep = getIntent().getIntExtra(NOW_STEP, -1);
        this.mContext = this;
        this.mBluetoothAdapter = ((BluetoothManager) getSystemService("bluetooth")).getAdapter();
        if (this.mBluetoothAdapter == null) {
            finish();
            Toast.makeText(this, R.string.device_module_no_support_for_bluetooth, 0).show();
        }
    }

    private void initListener() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(this.mDfuUpdateReceiver, makeDfuUpdateIntentFilter());
        localBroadcastManager.registerReceiver(this.mBleReceiver, new IntentFilter(BleService.BLE_CHARACTERISTIC_WRITE));
        localBroadcastManager.registerReceiver(this.mBleReceiver, new IntentFilter(com.iwown.ble_module.proto.ble.BleService.BLE_CHARACTERISTIC_WRITE));
    }

    private IntentFilter makeDfuUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("no.nordicsemi.android.dfu.broadcast.BROADCAST_PROGRESS");
        intentFilter.addAction("no.nordicsemi.android.dfu.broadcast.BROADCAST_ERROR");
        intentFilter.addAction(DfuBaseService.BROADCAST_LOG);
        return intentFilter;
    }

    private void checkUpdateInfo() {
        if (this.mNowStep != -1) {
            goToStep(this.mNowStep);
            this.mIsStartUpdate = true;
            KLog.e("=======checkUpdateInfo==========");
        } else if (BluetoothOperation.isConnected()) {
            goToStep(0);
        } else {
            int state = FwUpdateInfo.getInstance().getState();
            if (state == 0) {
                goToStep(1);
            } else if (state == 1) {
                if (System.currentTimeMillis() - FwUpdateInfo.getInstance().getLastProgressTime() > 180000) {
                    goToStep(1);
                } else {
                    updateProgressBar(FwUpdateInfo.getInstance().getLastProgress(), 0, 0, false);
                }
            } else if (state != 2) {
                goToStep(1);
            } else if (System.currentTimeMillis() - FwUpdateInfo.getInstance().getLastProgressTime() > 60000) {
                goToStep(1);
            } else {
                updateUI(8, false);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean isBack() {
        return this.mIsBacked || isDestroyed();
    }

    /* access modifiers changed from: private */
    public void goToStep(final int step) {
        if (!isBack()) {
            this.mNowStep = step;
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if (step == 0 || FirmwareUpgradeActivity.this.checkBluetooth()) {
                        if (step == 8 || step == 9) {
                            FirmwareUpgradeActivity.this.updateUI(7, false);
                        } else {
                            FirmwareUpgradeActivity.this.updateUI(step, false);
                        }
                        KLog.e("---fw upgrade step:" + String.valueOf(step));
                        switch (step) {
                            case 0:
                                FirmwareUpgradeActivity.this.presenter.checkVersion();
                                return;
                            case 2:
                                FirmwareUpgradeActivity.this.mIsStartUpdate = true;
                                FirmwareUpgradeActivity.this.presenter.uploadUserInfo();
                                return;
                            case 3:
                                FirmwareUpgradeActivity.this.presenter.downloadUserInfo();
                                return;
                            case 4:
                                FirmwareUpgradeActivity.this.presenter.createFileDir();
                                return;
                            case 5:
                                FirmwareUpgradeActivity.this.presenter.downloadUpgradle(FirmwareUpgradeActivity.this.presenter.fwUpdateDetail().getDownload_link(), FirmwareUpgradeActivity.this.myCallbackHandler);
                                return;
                            case 6:
                                FirmwareUpgradeActivity.this.writeDfuCommond();
                                return;
                            case 7:
                                FirmwareUpgradeActivity.this.startScan();
                                return;
                            case 8:
                                FirmwareUpgradeActivity.this.startScan();
                                return;
                            case 9:
                                FirmwareUpgradeActivity.this.startScan();
                                return;
                            default:
                                return;
                        }
                    }
                }
            }, 500);
        }
    }

    /* access modifiers changed from: private */
    public boolean checkBluetooth() {
        if (this.mBluetoothAdapter.isEnabled()) {
            return true;
        }
        startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 222);
        return false;
    }

    public void setPresenter(Presenter presenter2) {
    }

    public void pGoToStep(int step) {
        goToStep(step);
    }

    public void pUpdateUI(int step, boolean flag) {
        updateUI(step, flag);
    }

    public void onClick(View v) {
        int id = v.getId();
        long nowClickTime = System.currentTimeMillis();
        if (nowClickTime - this.mLastClickTime >= 500) {
            this.mLastClickTime = nowClickTime;
            processNowStep();
            if (id == R.id.start_upgrade) {
                this.startLayout.setVisibility(8);
                this.imgAnimationFirmware.playAnimation();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222) {
            if (resultCode == -1) {
                processNowStep();
            } else if (this.mNowStep == 1) {
                updateUI(1, false);
            }
        } else if (requestCode == 331 && data != null) {
            if (data.getBooleanExtra(HardwareUpdateSuccessActivity.EXTRA_YES_OR_NO, true)) {
                finish();
                return;
            }
            KLog.e("重新升级");
            goToStep(7);
        }
    }

    /* access modifiers changed from: private */
    public void writeDfuCommond() {
        if (BluetoothOperation.isConnected()) {
            if (BluetoothOperation.isIv()) {
                ZeronerBackgroundThreadManager.getInstance().clearQueue();
                SyncData.getInstance().stopSyncDataAll();
                BluetoothOperation.setNeedReconnect(false);
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                MtkBackgroundThreadManager.getInstance().clearQueue();
                MtkSync.getInstance().stopSyncDataAll();
                BluetoothOperation.setNeedReconnect(false);
            } else if (BluetoothOperation.isZg()) {
                BluetoothOperation.setNeedReconnect(false);
                TaskHandler.getInstance().clearTask();
            } else if (BluetoothOperation.isProtoBuf()) {
                BackgroundThreadManager.getInstance().clearQueue();
                BluetoothOperation.setNeedReconnect(false);
            }
            ContextUtil.isFirmwareUp = true;
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_Sync_Setting_Time, 0);
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Weather_Update, 0);
            if (this.presenter.isNordic()) {
                if (BluetoothOperation.isIv()) {
                    L.file("非i6hr写入升级指令", 6);
                    byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().setUpgrade();
                    DataBean bean = new DataBean();
                    bean.addData(data);
                    ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, bean);
                } else if (BluetoothOperation.isZg()) {
                    BleHandler.getInstance().addTaskMessage(new BleMessage(BleDataOrderHandler.getInstance().setUpgrade()));
                } else if (BluetoothOperation.isProtoBuf()) {
                    final boolean flag = ProtoBufSendBluetoothCmdImpl.getInstance().setUpgradeNotification();
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (flag) {
                                ProtoBufSendBluetoothCmdImpl.getInstance().setUpgradeCmd();
                                FirmwareUpgradeActivity.this.mHandler.removeCallbacks(FirmwareUpgradeActivity.this.mWriteDFUTimeoutRunnable);
                                FirmwareUpgradeActivity.this.mHandler.postDelayed(FirmwareUpgradeActivity.this.mWriteDFUTimeoutRunnable, BootloaderScanner.TIMEOUT);
                            }
                        }
                    }, 3000);
                    return;
                }
                this.mHandler.removeCallbacks(this.mWriteDFUTimeoutRunnable);
                this.mHandler.postDelayed(this.mWriteDFUTimeoutRunnable, BootloaderScanner.TIMEOUT);
            } else if (this.presenter.isDialog()) {
                L.file("i6hr 断开连接", 6);
                BluetoothOperation.FirmwareUnbindDevice(false);
                this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (!FirmwareUpgradeActivity.this.isDestroyed()) {
                            FirmwareUpgradeActivity.this.goToStep(7);
                        }
                    }
                }, 3000);
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                if (!WearableManager.getInstance().isAvailable()) {
                    this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            if (!FirmwareUpgradeActivity.this.isDestroyed()) {
                                FirmwareUpgradeActivity.this.goToStep(7);
                            }
                        }
                    }, 1000);
                    return;
                }
                Intent updateIntent = new Intent(this, IwownFotaService.class);
                stopService(updateIntent);
                File fir = createFile();
                KLog.e("===============================" + fir.getPath());
                KLog.e("==============writeDfuCommond()============" + new File(Environment.getExternalStorageDirectory(), "/Zeroner/F1_Firmware_1.1.0.7.bin").getPath());
                updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir.getPath());
                KLog.e("====" + WearableManager.getInstance().getRemoteDevice().getName() + "====" + WearableManager.getInstance().getRemoteDevice().getAddress());
                updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", ContextUtil.getDeviceNameCurr());
                updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", ContextUtil.getDeviceAddressCurr());
                startService(updateIntent);
                startHardwareUpdateService(false);
                this.presenter.saveConnectTime();
                updateUI(9, false);
                return;
            }
        } else {
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if (!FirmwareUpgradeActivity.this.isDestroyed()) {
                        FirmwareUpgradeActivity.this.goToStep(7);
                    }
                }
            }, 1000);
        }
        PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device, (String) null);
    }

    /* access modifiers changed from: private */
    public void startHardwareUpdateService(boolean mustStart) {
        if (mustStart || !Util.isServiceRunning(this, HardwareUpdateService.class.getName())) {
            Intent service = new Intent(this, HardwareUpdateService.class);
            try {
                if (VERSION.SDK_INT >= 26) {
                    startForegroundService(service);
                } else {
                    startService(service);
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        EventBus.getDefault().post(new WakeUpEvent(0));
    }

    /* access modifiers changed from: private */
    public void startScan() {
        if (!isBack()) {
            if (!this.presenter.checkFile()) {
                goToStep(3);
                return;
            }
            BluetoothOperation.stopScan(ContextUtil.app);
            Intent intent = null;
            if (this.presenter.isDialog()) {
                intent = new Intent(this, NewDfuService.class);
            } else if (this.presenter.isNordic()) {
                intent = new Intent(this, DfuService.class);
            } else if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
                intent = new Intent(this, IwownFotaService.class);
            }
            if (intent == null) {
                L.file("升级intent：" + JsonUtils.toJson(DeviceSettingsBiz.getInstance().queryDevSettings(DeviceUtils.getDeviceInfo().getModel())), 6);
                return;
            }
            stopService(intent);
            startHardwareUpdateService(true);
            L.file("固件升级 startScan   " + Thread.currentThread().getId(), 6);
            this.mBluetoothAdapter.startLeScan(this.mLEScanCallback);
            this.mHandler.postDelayed(this.mScanTimeOutRunnable, SCAN_DURATION);
            this.mLastTime = 0;
        }
    }

    /* access modifiers changed from: private */
    public synchronized boolean judgeRepeat(String address) {
        boolean z = true;
        synchronized (this) {
            long nowTime = System.currentTimeMillis();
            if (nowTime - this.mLastTime >= 60000) {
                this.mLastTime = nowTime;
                if (TextUtils.isEmpty(address)) {
                    z = false;
                } else if (address.equals(Util.getNewMac(PrefUtil.getString(ContextUtil.app, FirmwareAction.Firmware_Update_Device_Mac), 1))) {
                    z = false;
                } else {
                    this.mLastTime = 0;
                }
            }
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void stopScan() {
        this.mHandler.removeCallbacks(this.mScanTimeOutRunnable);
        this.mHandler.post(new Runnable() {
            public void run() {
                try {
                    L.file("固件升级 stopScan    " + Thread.currentThread().getId(), 6);
                    if (FirmwareUpgradeActivity.this.mLEScanCallback == null) {
                        L.file("固件升级 stopScan    mLEScanCallback==null", 6);
                    }
                    FirmwareUpgradeActivity.this.mBluetoothAdapter.stopLeScan(FirmwareUpgradeActivity.this.mLEScanCallback);
                } catch (Exception e) {
                    L.file("固件升级时 停止扫描出错 : " + e.toString(), 6);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void connect(String deviceAddress) {
        if (BluetoothOperation.isMtk() || BluetoothOperation.isMTKEarphone()) {
            L.file("==============Mtk===========", 6);
            this.mHandler.removeCallbacks(this.mScanTimeOutRunnable);
            stopScan();
            updateUI(8, false);
            Intent updateIntent = new Intent(this.mContext, IwownFotaService.class);
            KLog.e("========connect(String deviceAddress) ============");
            stopService(updateIntent);
            File fir = createFile();
            updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
            KLog.e("====================" + fir.getPath());
            updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir.getPath());
            updateIntent.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", ContextUtil.getDeviceNameCurr());
            startService(updateIntent);
            startHardwareUpdateService(false);
            this.presenter.saveConnectTime();
        } else if (this.presenter.isDialog()) {
            KLog.e("==============dialog===========");
            this.mHandler.removeCallbacks(this.mScanTimeOutRunnable);
            stopScan();
            updateUI(8, false);
            Intent updateIntent2 = new Intent(this.mContext, NewDfuService.class);
            File fir2 = createFile();
            Uri fromFile = Uri.fromFile(fir2);
            updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
            KLog.e("dialog 直连=================>application：" + deviceAddress);
            updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", PrefUtil.getString(this.mContext, BleAction.Bluetooth_Device_Name));
            updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
            updateIntent2.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir2.getPath());
            updateIntent2.putExtra(NewDfuService.EXTRA_DEVICE, ((BluetoothManager) this.mContext.getSystemService("bluetooth")).getAdapter().getRemoteDevice(deviceAddress));
            this.presenter.saveConnectTime();
            startService(updateIntent2);
        } else {
            KLog.e("==============非dialog平台===========");
            this.mHandler.removeCallbacks(this.mScanTimeOutRunnable);
            stopScan();
            updateUI(8, false);
            Intent updateIntent3 = new Intent(this.mContext, DfuService.class);
            File fir3 = createFile();
            Uri fromFile2 = Uri.fromFile(fir3);
            updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
            updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_NAME", PrefUtil.getString(this.mContext, BleAction.Bluetooth_Device_Name));
            updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_DEVICE_ADDRESS", deviceAddress);
            updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_PATH", fir3.getPath());
            updateIntent3.putExtra("no.nordicsemi.android.dfu.extra.EXTRA_FILE_TYPE", 0);
            this.presenter.saveConnectTime();
            try {
                if (VERSION.SDK_INT >= 26) {
                    startForegroundService(updateIntent3);
                } else {
                    startService(updateIntent3);
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateUI(int step, final boolean isError) {
        KLog.i("----updateUI--" + step + "----" + isError);
        this.mNowStep = step;
        this.mHandler.post(new Runnable() {
            public void run() {
                if (!FirmwareUpgradeActivity.this.isBack()) {
                    switch (FirmwareUpgradeActivity.this.mNowStep) {
                        case 1:
                            FirmwareUpgradeActivity.this.startLayout.setVisibility(0);
                            return;
                        case 2:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_save_user_info, R.string.device_module_update_time_tip, R.string.device_module_update_step_save_user_info_error, R.string.device_module_update_step_save_user_info_error_tip, 10);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_10) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_10 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_save_user_info_error, isError);
                            return;
                        case 3:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_download_user_info, R.string.device_module_update_time_tip, R.string.device_module_update_step_download_user_info_error, R.string.device_module_update_step_download_user_info_error_tip, 10);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_10) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_10 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_download_user_info_error, isError);
                            return;
                        case 4:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_create_file_dir, R.string.device_module_update_time_tip, R.string.device_module_update_step_create_file_dir_error, R.string.device_module_update_step_create_file_dir_error_tip, 20);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_20) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_20 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_create_file_dir_error, isError);
                            return;
                        case 5:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_download_hardware, R.string.device_module_update_time_tip, R.string.device_module_update_step_download_hardware_error, R.string.device_module_update_step_download_hardware_error_tip, 20);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_20) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_20 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_download_hardware_error, isError);
                            return;
                        case 6:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_write_dfu, R.string.device_module_update_time_tip, R.string.device_module_update_step_write_dfu_error, R.string.device_module_update_step_write_dfu_error_tip, 40);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_40) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_40 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_write_dfu_error, isError);
                            return;
                        case 7:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_search_device, R.string.device_module_update_time_tip, R.string.device_module_update_step_search_device_error, R.string.device_module_update_step_search_device_error_tip, 50);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_50) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_50 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_search_device_error, isError);
                            return;
                        case 8:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_connect_device, R.string.device_module_update_time_tip, R.string.device_module_update_step_connect_device_error, R.string.device_module_update_step_connect_device_error_tip, 60);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_60) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_60 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_connect_device_error, isError);
                            return;
                        case 9:
                            FirmwareUpgradeActivity.this.updateTextUI(isError, R.string.device_module_update_step_write_device, R.string.device_module_update_time_tip, R.string.device_module_update_step_write_device_error, R.string.device_module_update_step_write_device_error_tip, 60);
                            if (!isError) {
                                return;
                            }
                            if (!FirmwareUpgradeActivity.this.DFUFail_60) {
                                FirmwareUpgradeActivity.this.processNowStep();
                                FirmwareUpgradeActivity.this.DFUFail_60 = true;
                                return;
                            }
                            FirmwareUpgradeActivity.this.showFailDialog(R.string.device_module_update_step_write_device_error, isError);
                            return;
                        default:
                            return;
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateTextUI(boolean isError, int normalId, int normalId2, int errorId, int errorId2, int progress) {
        if (!isBack()) {
            if (isError) {
                this.tvStep.setUpdateText(errorId);
                this.tvStep.stop();
                this.errorText.setText(errorId2);
                return;
            }
            this.errorText.setText(normalId2);
            this.tvStep.setUpdateText(normalId);
            this.mLastProgress = this.progressBar.getProgress();
            if (this.mLastProgress >= progress) {
                this.progressText.setText(getString(R.string.device_module_update_progress, new Object[]{Integer.valueOf(progress)}));
                this.progressBar.setProgress(progress);
                return;
            }
            if (this.mAnimator == null) {
                this.mAnimator = ValueAnimator.ofInt(new int[]{this.mLastProgress, progress});
                this.mAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (!FirmwareUpgradeActivity.this.isBack()) {
                            int nowProgress = ((Integer) animation.getAnimatedValue()).intValue();
                            FirmwareUpgradeActivity.this.progressText.setText(FirmwareUpgradeActivity.this.getString(R.string.device_module_update_progress, new Object[]{Integer.valueOf(nowProgress)}));
                            FirmwareUpgradeActivity.this.progressBar.setProgress(nowProgress);
                        }
                    }
                });
                this.mAnimator.setDuration(500);
            } else {
                this.mAnimator.setIntValues(new int[]{this.mLastProgress, progress});
            }
            this.mAnimator.start();
        }
    }

    /* access modifiers changed from: private */
    public void processNowStep() {
        switch (this.mNowStep) {
            case 0:
                startUpdate();
                return;
            case 1:
                this.mIsStartUpdate = true;
                goToStep(3);
                return;
            case 2:
                goToStep(2);
                return;
            case 3:
                goToStep(3);
                return;
            case 4:
                goToStep(4);
                return;
            case 5:
                goToStep(5);
                return;
            case 6:
                goToStep(6);
                return;
            case 7:
                goToStep(7);
                return;
            case 8:
                goToStep(7);
                return;
            case 9:
                goToStep(7);
                return;
            default:
                return;
        }
    }

    private void startUpdate() {
        if (BluetoothOperation.isConnected()) {
        }
        if (!checkBluetooth()) {
            KLog.e("bluetooth:close");
            return;
        }
        KLog.e("=================startUpdate=======================");
        goToStep(2);
    }

    /* access modifiers changed from: private */
    public void updateProgressBar(int progress, int part, int total, boolean error) {
        this.mIsStartUpdate = true;
        if (!isDestroyed()) {
            this.mHandler.removeCallbacks(this.mFirmwareAidRunnable);
            if (error) {
                KLog.i("-----" + this.mNowStep + "--error:true");
                updateUI(this.mNowStep, true);
            }
            switch (progress) {
                case -7:
                    return;
                case -6:
                    this.tvStep.setUpdateText(getString(R.string.device_module_activity_update_over));
                    this.presenter.deleteFile();
                    finish();
                    return;
                case -5:
                    this.tvStep.setUpdateText(getString(R.string.device_module_dfu_status_disconnecting));
                    return;
                case -4:
                    this.tvStep.setUpdateText(getString(R.string.device_module_dfu_status_validating));
                    return;
                case -2:
                    this.tvStep.setUpdateText(getString(R.string.device_module_dfu_status_starting));
                    return;
                case -1:
                    stopScan();
                    return;
                default:
                    if (error) {
                        updateUI(9, true);
                        return;
                    }
                    int result = (int) ((((double) progress) * 0.4d) + 60.0d);
                    this.progressBar.setProgress(result);
                    this.tvStep.setUpdateText(R.string.device_module_update_step_write_device);
                    this.progressText.setText(getString(R.string.device_module_update_progress, new Object[]{Integer.valueOf(result)}));
                    if (progress >= 100) {
                        this.progressBar.setProgress(100);
                        this.progressText.setText(getString(R.string.device_module_update_progress, new Object[]{Integer.valueOf(100)}));
                        this.tvStep.setUpdateText(getString(R.string.device_module_activity_update_over));
                        return;
                    }
                    return;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ZGFirmwareUpgrade event) {
        L.file("写入DFU指令成功", 6);
        KLog.e("onEventMainThread+写入DFU指令成功");
        this.mHandler.removeCallbacks(this.mWriteDFUTimeoutRunnable);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                if (!FirmwareUpgradeActivity.this.isDestroyed()) {
                    FirmwareUpgradeActivity.this.goToStep(7);
                }
            }
        }, 3000);
    }

    public void back() {
        if (!this.mIsStartUpdate) {
            finish();
            return;
        }
        if (this.exitDialog == null) {
            this.exitDialog = new UpgradeFailDialogRemind(this.mContext, false);
            this.exitDialog.setClickCallBack(new ClickCallBack() {
                public void onOk() {
                    if (TextUtils.isEmpty(PrefUtil.getString(FirmwareUpgradeActivity.this.mContext, BleAction.Bluetooth_Device_Address_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(FirmwareUpgradeActivity.this.mContext, FirmwareAction.Firmware_Update_Device_Mac)) && FirmwareUpgradeActivity.this.isBeforeDfu()) {
                        KLog.i("固件升级过程中退出了");
                        PrefUtil.save(FirmwareUpgradeActivity.this.mContext, BleAction.Bluetooth_Device_Address_Current_Device, PrefUtil.getString(FirmwareUpgradeActivity.this.mContext, FirmwareAction.Firmware_Update_Device_Mac));
                    }
                    FirmwareUpgradeActivity.this.mIsBacked = true;
                    FirmwareUpgradeActivity.this.exitDialog.dismiss();
                    FirmwareUpgradeActivity.this.finish();
                }

                public void onCancel() {
                    FirmwareUpgradeActivity.this.exitDialog.dismiss();
                }
            });
        }
        this.exitDialog.show();
        String text = getString(R.string.sport_module_fatigue_tips_tag);
        this.exitDialog.setTitleMsg(getString(R.string.device_module_firmware_dialog, new Object[]{text}));
        this.exitDialog.setContentMsg(getString(R.string.device_module_update_back_tip));
        this.exitDialog.setBt_okText(getString(R.string.device_module_update_dialog_ok));
        this.exitDialog.setBt_cancel(getString(R.string.device_module_update_dialog_cancel));
    }

    /* access modifiers changed from: private */
    public void showFailDialog(int id, boolean show) {
        if (show) {
            if (this.exitDialog == null) {
                this.exitDialog = new UpgradeFailDialogRemind(this.mContext, false);
                this.exitDialog.setClickCallBack(new ClickCallBack() {
                    public void onOk() {
                        FirmwareUpgradeActivity.this.exitDialog.dismiss();
                        FirmwareUpgradeActivity.this.processNowStep();
                    }

                    public void onCancel() {
                        FirmwareUpgradeActivity.this.exitDialog.dismiss();
                        PrefUtil.save(FirmwareUpgradeActivity.this.mContext, BleAction.Bluetooth_Device_Address_Current_Device, PrefUtil.getString(FirmwareUpgradeActivity.this.mContext, FirmwareAction.Firmware_Update_Device_Mac));
                        FirmwareUpgradeActivity.this.finish();
                    }
                });
            }
            this.exitDialog.show();
            this.exitDialog.setTopImage(R.drawable.upgrade_fail_2x);
            String text = getString(R.string.failed);
            this.exitDialog.setTitleMsg(getString(R.string.device_module_firmware_dialog, new Object[]{text}));
            this.exitDialog.setContentMsg(getString(id));
            this.exitDialog.setBt_okText(getString(R.string.device_module_update_dialog_ok_1));
            this.exitDialog.setBt_cancel(getString(R.string.device_module_update_dialog_cancel_1));
        } else if (this.exitDialog != null) {
            this.exitDialog.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        try {
            stopScan();
            EventBus.getDefault().unregister(this);
            this.imgAnimationFirmware.cancelAnimation();
            EventBus.getDefault().unregister(this);
            EventBus.getDefault().post(new WakeUpEvent(1));
            this.mHandler.removeCallbacks(this.mWriteDFUTimeoutRunnable);
            this.mHandler.removeCallbacks(this.mFirmwareAidRunnable);
            DownloadServiceBiz.getInstance().setmHandler(null);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mDfuUpdateReceiver);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBleReceiver);
            PrefUtil.save((Context) ContextUtil.app, UserAction.Bluetooth_Check_Firmware_Update_Time, new DateUtil().getUnixTimestamp() + 600);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public boolean isBeforeDfu() {
        return this.mNowStep == 0 || this.mNowStep == 4 || this.mNowStep == 2 || this.mNowStep == 5;
    }

    public File createFile() {
        String model = DeviceUtils.getDeviceInfo().getModel();
        return new File(Environment.getExternalStorageDirectory().toString(), LogPath.ZERONER + model + "_firmware_" + DeviceSettingsBiz.getInstance().getSuffix(model));
    }
}
