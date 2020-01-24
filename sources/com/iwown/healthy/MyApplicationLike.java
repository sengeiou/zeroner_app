package com.iwown.healthy;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.healthy.zeroner_pro.R;
import com.iwown.ble_module.WristBand;
import com.iwown.ble_module.iwown.bluetooth.AbsBle;
import com.iwown.ble_module.iwown.bluetooth.BleService;
import com.iwown.ble_module.iwown.bluetooth.IBle;
import com.iwown.ble_module.proto.ble.BleService.LocalBinder;
import com.iwown.ble_module.proto.ble.ProtoBle;
import com.iwown.ble_module.scan.Scanner;
import com.iwown.ble_module.zg_ble.bluetooth.ZGAbsBle;
import com.iwown.ble_module.zg_ble.bluetooth.ZGIBle;
import com.iwown.ble_module.zg_ble.bluetooth.ZGService;
import com.iwown.data_link.Constants;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.DeviceInitUtils;
import com.iwown.device_module.callback.BluetoothDeviceCallback;
import com.iwown.device_module.callback.IBluetoothListener;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothDataParseReceiver;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.log.L;
import com.iwown.lib_common.network.utils.BaseUtils;
import com.iwown.my_module.MyInitUtils;
import com.iwown.sport_module.SportInitUtils;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.service.DataNetService;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.interfaces.BetaPatchListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.Locale;
import org.litepal.LitePal;
import org.litepal.LitePalDB;

public class MyApplicationLike extends DefaultApplicationLike {
    /* access modifiers changed from: private */
    public static Application instance;
    private static final Object sObject = new Object();
    /* access modifiers changed from: private */
    public IBle mBle;
    private MyLifecycleHandler mMyLifecycleHandler;
    private final ServiceConnection mProtoServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            try {
                MyApplicationLike.this.protoService = ((LocalBinder) rawBinder).getService();
                MyApplicationLike.this.protoBle = MyApplicationLike.this.protoService.getBle();
                DeviceInitUtils.getInstance().addProBle(MyApplicationLike.this.protoBle);
                L.file("蓝牙进行重连 : 绑定服务成功", 4);
                KLog.e("蓝牙进行重连 : 绑定服务成功");
                if (!TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device))) {
                    ((ProtoBle) MyApplicationLike.this.protoBle).setWristBand(new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                }
                synchronized (MyApplicationLike.getObject()) {
                    MyApplicationLike.getObject().notifyAll();
                }
                if (MyApplicationLike.this.protoBle == null || !MyApplicationLike.this.protoBle.adapterEnabled()) {
                }
            } catch (Exception e) {
                KLog.e("初始化异常 : " + e.toString());
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
            KLog.i("onServiceDisconnected");
            MyApplicationLike.this.protoService = null;
        }
    };
    /* access modifiers changed from: private */
    public BleService mService;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            try {
                MyApplicationLike.this.mService = ((BleService.LocalBinder) rawBinder).getService();
                MyApplicationLike.this.mBle = MyApplicationLike.this.mService.getBle();
                DeviceInitUtils.getInstance().addBle(MyApplicationLike.this.mBle);
                L.file("蓝牙进行重连 : 绑定服务成功", 4);
                KLog.e("蓝牙进行重连 : 绑定服务成功");
                if (!TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device))) {
                    ((AbsBle) MyApplicationLike.this.mBle).setWristBand(new com.iwown.ble_module.iwown.bean.WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                }
                synchronized (MyApplicationLike.getObject()) {
                    MyApplicationLike.getObject().notifyAll();
                }
                if (MyApplicationLike.this.mBle == null || !MyApplicationLike.this.mBle.adapterEnabled()) {
                }
            } catch (Exception e) {
                KLog.e("初始化异常 : " + e.toString());
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
            KLog.i("onServiceDisconnected");
            MyApplicationLike.this.mService = null;
        }
    };
    private final ServiceConnection mZGServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            try {
                MyApplicationLike.this.zgService = ((ZGService.LocalBinder) rawBinder).getService();
                MyApplicationLike.this.zgiBle = MyApplicationLike.this.zgService.getBle();
                DeviceInitUtils.getInstance().addZGBle(MyApplicationLike.this.zgiBle);
                L.file("蓝牙进行重连 : 绑定服务成功", 4);
                KLog.e("蓝牙进行重连 : 绑定服务成功");
                if (!TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device)) && !TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device))) {
                    ((ZGAbsBle) MyApplicationLike.this.zgiBle).setWristBand(new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                }
                synchronized (MyApplicationLike.getObject()) {
                    MyApplicationLike.getObject().notifyAll();
                }
                if (MyApplicationLike.this.zgiBle == null || !MyApplicationLike.this.zgiBle.adapterEnabled()) {
                }
            } catch (Exception e) {
                KLog.e("初始化异常 : " + e.toString());
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
            KLog.i("onServiceDisconnected");
            MyApplicationLike.this.zgService = null;
        }
    };
    /* access modifiers changed from: private */
    public com.iwown.ble_module.proto.ble.IBle protoBle;
    /* access modifiers changed from: private */
    public com.iwown.ble_module.proto.ble.BleService protoService;
    /* access modifiers changed from: private */
    public ZGService zgService;
    /* access modifiers changed from: private */
    public ZGIBle zgiBle;

    public MyApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    public void onCreate() {
        super.onCreate();
        if (isMainProcess() && !LeakCanary.isInAnalyzerProcess(getApplication())) {
            Beta.enableHotfix = true;
            Beta.canAutoDownloadPatch = true;
            Beta.canAutoPatch = true;
            Beta.canNotifyUserRestart = false;
            Beta.betaPatchListener = new BetaPatchListener() {
                public void onPatchReceived(String patchFile) {
                    KLog.e("-----------补丁下载地址----------" + patchFile);
                }

                public void onDownloadReceived(long savedLength, long totalLength) {
                }

                public void onDownloadSuccess(String msg) {
                    KLog.e("------------补丁下载成功-------------");
                }

                public void onDownloadFailure(String msg) {
                    KLog.e("------------补丁下载失败------------");
                }

                public void onApplySuccess(String msg) {
                    KLog.e("------------补丁应用成功------------");
                }

                public void onApplyFailure(String msg) {
                    KLog.e("------------补丁应用失败------------");
                }

                public void onPatchRollback() {
                }
            };
            Bugly.setIsDevelopmentDevice(getApplication(), false);
            Bugly.init(getApplication(), AppConfigUtil.Bugly_Key, true);
            try {
                Locale locale = getApplication().getResources().getConfiguration().locale;
                String language = locale.getLanguage().toUpperCase();
                String country = locale.getCountry().toUpperCase();
                Log.e("iwownfit", String.format("language:%s,country:%s", new Object[]{language, country}));
                if (TextUtils.equals("RU", country)) {
                    updateRuHttpURl(true);
                    Log.i("MyApplication", "---------change ru url---------");
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            FontChangeUtils.initFonts(getApplication());
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.init(getApplication());
            Scanner.getInstance(getApplication());
            LeakCanary.install(getApplication());
            CrashReport.initCrashReport(getApplication(), AppConfigUtil.Bugly_Key, true);
            KLog.init(true, "iwown");
            KLog.e("MyApplication");
            L.deleteOverdueFile(L.SDPATH + "Zeroner/zeroner/log/");
            L.deleteLog(2);
            instance = getApplication();
            BaseUtils.init(getApplication());
            LitePal.initialize(getApplication());
            LitePalDB litePalDB = LitePalDB.fromDefault(Constants.DB_Name);
            DeviceInitUtils.getInstance().init(getApplication());
            MyInitUtils.getInstance().init(getApplication(), litePalDB);
            SportInitUtils.getInstance().init(getApplication(), litePalDB);
            try {
                Intent data_service = new Intent(getApplication(), DataNetService.class);
                if (VERSION.SDK_INT >= 26) {
                    getApplication().startForegroundService(data_service);
                } else {
                    getApplication().startService(data_service);
                }
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
            }
            Intent bindIntent = new Intent(getApplication(), BleService.class);
            getApplication().stopService(bindIntent);
            try {
                if (VERSION.SDK_INT >= 26) {
                    getApplication().startForegroundService(bindIntent);
                } else {
                    getApplication().startService(bindIntent);
                }
            } catch (Exception e3) {
                ThrowableExtension.printStackTrace(e3);
            }
            getApplication().bindService(bindIntent, this.mServiceConnection, 1);
            Intent zgBindIntent = new Intent(getApplication(), ZGService.class);
            getApplication().stopService(zgBindIntent);
            try {
                if (VERSION.SDK_INT >= 26) {
                    getApplication().startForegroundService(zgBindIntent);
                } else {
                    getApplication().startService(zgBindIntent);
                }
            } catch (Exception e4) {
                ThrowableExtension.printStackTrace(e4);
            }
            getApplication().bindService(zgBindIntent, this.mZGServiceConnection, 1);
            Intent proIntent = new Intent(getApplication(), com.iwown.ble_module.proto.ble.BleService.class);
            getApplication().stopService(proIntent);
            try {
                if (VERSION.SDK_INT >= 26) {
                    getApplication().startForegroundService(proIntent);
                } else {
                    getApplication().startService(proIntent);
                }
            } catch (Exception e5) {
                ThrowableExtension.printStackTrace(e5);
            }
            getApplication().bindService(proIntent, this.mProtoServiceConnection, 1);
            IntentFilter intentFilter = BaseActionUtils.getIntentFilter();
            LocalBroadcastManager.getInstance(getApplication()).registerReceiver(new BluetoothDataParseReceiver(), intentFilter);
            Stetho.initializeWithDefaults(getApplication());
            Constants.initServerMsg(getApplication());
            UserConfig.getInstance(getApplication()).initInfoFromOtherModule();
            BluetoothDeviceCallback.init(getApplication(), new IBluetoothListener() {
                public void onDataArrived(int ble_sdk_type, int dataType, String data) {
                    Intent intent = new Intent(BaseActionUtils.ON_DATA_ARRIVED);
                    intent.putExtra(BaseActionUtils.BLE_SDK_TYPE, ble_sdk_type);
                    intent.putExtra(BaseActionUtils.BLE_DATA_TYPE, dataType);
                    intent.putExtra(BaseActionUtils.BLE_ARRIVED_DATA, data);
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(intent);
                }

                public void onScanResult(com.iwown.ble_module.iwown.bean.WristBand dev) {
                    Intent intent = new Intent(BaseActionUtils.ON_SCAN_RESULT);
                    intent.putExtra(BaseActionUtils.BLE_SCAN_RESULT_DEVICE, dev);
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(intent);
                }

                public void onBluetoothInit() {
                    KLog.i("初始化回调：onBluetoothInit");
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(new Intent(BaseActionUtils.ON_BLUETOOTH_INIT));
                }

                public void onPreConnect() {
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(new Intent(BaseActionUtils.BLE_PRE_CONNECT));
                }

                public void connectStatue(boolean isConnect) {
                    KLog.i("连接状态：" + isConnect);
                    Intent intent = new Intent(BaseActionUtils.ON_CONNECT_STATUE);
                    intent.putExtra(BaseActionUtils.BLE_CONNECT_STATUE, isConnect);
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(intent);
                }

                public void onDiscoverService(String serviceUUID) {
                    Intent intent = new Intent(BaseActionUtils.ON_DISCOVER_SERVICE);
                    intent.putExtra(BaseActionUtils.BLE_SERVICE_UUID, serviceUUID);
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(intent);
                }

                public void onDiscoverCharacter(String characterUUID) {
                    Intent intent = new Intent(BaseActionUtils.ON_DISCOVER_CHARACTER);
                    intent.putExtra(BaseActionUtils.BLE_CHARACTER_UUID, characterUUID);
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(intent);
                }

                public void onCommonSend(byte[] data) {
                    Intent intent = new Intent(BaseActionUtils.ON_COMMON_SEND);
                    intent.putExtra(BaseActionUtils.BLE_COMMON_SEND, data);
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(intent);
                }

                public void onCharacteristicChange(String address) {
                    Intent intent = new Intent(BaseActionUtils.ON_CHARACTERISTIC_CHANGE);
                    intent.putExtra(BaseActionUtils.BLE_BLUETOOTH_ADDRESS, address);
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(intent);
                }

                public void noCallBack() {
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(new Intent(BaseActionUtils.BLE_NO_CALLBACK));
                }

                public void onBluetoothError() {
                    LocalBroadcastManager.getInstance(MyApplicationLike.instance).sendBroadcast(new Intent(BaseActionUtils.ON_BLUETOOTH_ERROR));
                }
            });
            this.mMyLifecycleHandler = new MyLifecycleHandler();
            getApplication().registerActivityLifecycleCallbacks(this.mMyLifecycleHandler);
        }
    }

    public void updateRuHttpURl(boolean isRu) {
        if (isRu) {
            SportInitUtils.getInstance().changeURLRU(true);
            DeviceInitUtils.getInstance().changeURLRU(true);
            MyInitUtils.getInstance().changeURLRU(true);
        }
    }

    public IBle getmBle() {
        return this.mBle;
    }

    public static Object getObject() {
        return sObject;
    }

    public static Application getInstance() {
        return instance;
    }

    private boolean isMainProcess() {
        String mProcessName = getCurrentProcessName(getApplication().getApplicationContext());
        KLog.i("onCreategetProcessName：" + mProcessName);
        KLog.i("init_all_process");
        AppConfigUtil.getInstance(getApplication().getApplicationContext());
        AppConfigUtil.getInstance(getApplication().getApplicationContext());
        AppConfigUtil.setApp_name(getApplication().getString(R.string.app_name));
        if (!AppConfigUtil.isHealthy()) {
            NetFactory.CheckStatus = 1;
        } else {
            NetFactory.CheckStatus = 0;
        }
        return TextUtils.equals(mProcessName, getApplication().getApplicationContext().getPackageName());
    }

    private String getCurrentProcessName(Context context) {
        int pid = Process.myPid();
        for (RunningAppProcessInfo appProcess : ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    @TargetApi(14)
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        Beta.installTinker(this);
    }

    @TargetApi(14)
    public void registerActivityLifecycleCallback(ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }
}
