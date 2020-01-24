package com.iwown.device_module.device_operation.search;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Preconditions;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.scan.IScanCallback;
import com.iwown.ble_module.scan.Scanner;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.SharedPreferencesAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.BluetoothCallbackReceiver;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity.ActionOnclickListener;
import com.iwown.device_module.common.adapter.ComViewHolder.OnItemClickListener;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.HealthyUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.common.utils.Utils;
import com.iwown.device_module.common.view.WrapContentLinearLayoutManager;
import com.iwown.device_module.common.view.dialog.ConnectingDialog;
import com.iwown.device_module.device_operation.bean.ModeItems;
import com.iwown.device_module.device_operation.bean.ModeItems.DataBean;
import com.iwown.device_module.device_operation.search.DeviceSearchContract.Presenter;
import com.iwown.device_module.device_operation.search.DeviceSearchContract.View;
import com.iwown.lib_common.log.L;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.iwown.lib_common.toast.CustomToast;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SearchActivity extends DeviceModuleBaseActivity implements View, IScanCallback, OnClickListener {
    private static final int Btn_Connect_Statue = 10002;
    private static final int Btn_Scan_Statue = 10001;
    public static final int ReqCode = 12520;
    private static int classId;
    private Runnable addDeviceUi = new Runnable() {
        public void run() {
            if (!SearchActivity.this.isDestroyed()) {
                SearchActivity.this.addListView();
            }
        }
    };
    private Button bleSearchAgain;
    /* access modifiers changed from: private */
    public ConnectingDialog connectDialog;
    private LinearLayout connectFailed;
    /* access modifiers changed from: private */
    public int connectFlag;
    /* access modifiers changed from: private */
    public List<DataBean> data;
    /* access modifiers changed from: private */
    public ArrayList<WristBand> devs = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<WristBand> devsCopy = new ArrayList<>();
    Runnable dialogDismiss = new Runnable() {
        public void run() {
            if (SearchActivity.this.connectDialog != null && !SearchActivity.this.isFinishing()) {
                SearchActivity.this.connectDialog.dismiss();
                SearchActivity.this.connectFailedViewShow(true);
            }
        }
    };
    private boolean isHealthy;
    /* access modifiers changed from: private */
    public CharSequence[] items;
    /* access modifiers changed from: private */
    public DeviceSearchAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.myLooper());
    private long mLastAddTime;
    private long mNowTime;
    Presenter mPresenter;
    private BleStatueReceiver receiver;
    private boolean sIsScrolling;
    /* access modifiers changed from: private */
    public int scanSize = 0;
    private boolean scrollFlag;
    /* access modifiers changed from: private */
    public RecyclerView searchList;
    private LinearLayout searchNothing;
    private Runnable smoothScrollTo = new Runnable() {
        public void run() {
            if (SearchActivity.this.devs.size() > 0) {
                SearchActivity.this.searchList.smoothScrollToPosition(0);
            }
        }
    };
    /* access modifiers changed from: private */
    public int statue = 10001;
    private android.view.View view;

    private class BleStatueReceiver extends BluetoothCallbackReceiver {
        private BleStatueReceiver() {
        }

        public void onBluetoothInit() {
            super.onBluetoothInit();
            KLog.e("----search activity initCommand-----");
            if (BluetoothOperation.getWristBand() == null) {
                KLog.i("=====BluetoothOperation.getWristBand()====");
                return;
            }
            SearchActivity.this.mPresenter.connectStatue(true);
            SearchActivity.this.connectFlag = 0;
        }

        public void connectStatue(boolean isConnect) {
            super.connectStatue(isConnect);
            if (BluetoothOperation.getWristBand() == null) {
                KLog.i("=====BluetoothOperation.getWristBand()==null");
            } else if (!isConnect) {
                SearchActivity.this.connectFlag = SearchActivity.this.connectFlag + 1;
                if (SearchActivity.this.connectFlag > 1) {
                    SearchActivity.this.connectFlag = 0;
                    SearchActivity.this.mPresenter.connectStatue(isConnect);
                }
            } else {
                KLog.e("connect status:" + isConnect + "\nname:" + BluetoothOperation.getWristBand().getName() + "\naddress:" + BluetoothOperation.getWristBand().getAddress());
                PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device, BluetoothOperation.getWristBand().getName());
                PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device, BluetoothOperation.getWristBand().getAddress());
                PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Alias_Current_Device, BluetoothOperation.getWristBand().getAlias());
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.view = LayoutInflater.from(this).inflate(R.layout.device_module_fragment_search, null);
        setContentView(this.view);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        Scanner.getInstance(this).setIScanCallback(this);
        this.mPresenter = new SearchPresenter(this);
        this.receiver = new BleStatueReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.receiver, BaseActionUtils.getIntentFilter());
        this.bleSearchAgain.setOnClickListener(this);
    }

    private void initData() {
        try {
            String datas = PrefUtil.getString(this, SharedPreferencesAction.APP_SDK_UPDATE_Content);
            KLog.i(datas);
            if (TextUtils.isEmpty(datas)) {
                datas = Utils.getFromAssets(this, "modesdklist2default.txt");
                PrefUtil.save((Context) this, SharedPreferencesAction.APP_SDK_UPDATE_Content, datas);
            }
            this.data = ((ModeItems) new Gson().fromJson(datas, ModeItems.class)).getData();
            if (!AppConfigUtil.isZeronerHealthPro()) {
                this.items = new CharSequence[this.data.size()];
                int index = 0;
                for (DataBean dataBean : this.data) {
                    this.items[index] = dataBean.getCategoryname();
                    index++;
                }
            } else {
                this.items = new CharSequence[3];
                this.items[0] = getString(R.string.device_module_tab_title_1);
                this.items[1] = getString(R.string.device_module_tab_title_5);
                this.items[2] = getString(R.string.device_module_tab_title_3);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (AppConfigUtil.isHealthy(this)) {
            this.isHealthy = true;
            HealthyUtil.alias = Utils.getFromAssets(this, "alias.txt");
        }
    }

    private void initView() {
        this.connectDialog = new ConnectingDialog(this);
        classId = getIntent().getIntExtra("classId", 0);
        setLeftBackTo();
        setTitleText(getString(R.string.home_device));
        this.bleSearchAgain = (Button) findViewById(R.id.ble_search_again);
        this.searchList = (RecyclerView) findViewById(R.id.search_list);
        this.searchNothing = (LinearLayout) findViewById(R.id.search_nothing);
        this.connectFailed = (LinearLayout) findViewById(R.id.connect_fail);
        setLeftBtn(new ActionOnclickListener() {
            public void onclick() {
                SearchActivity.this.mPresenter.stopScan(ContextUtil.app);
                SearchActivity.this.finish();
            }
        });
        this.mAdapter = new DeviceSearchAdapter(this, this.devs, R.layout.device_module_ble_search_item_layout);
        this.searchList.setAdapter(this.mAdapter);
        this.searchList.setLayoutManager(new WrapContentLinearLayoutManager(this, 1, false));
        this.mAdapter.notifyDataSetChanged();
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final int position, android.view.View view) {
                try {
                    if (SearchActivity.this.devs.size() > 0) {
                        if (((WristBand) SearchActivity.this.devs.get(position)).getSdkType() != -1) {
                            KLog.e("======setOnItemClickListener==========" + ((WristBand) SearchActivity.this.devs.get(position)).getSdkType());
                            SearchActivity.this.hideSearchAgain(false);
                            SearchActivity.this.mPresenter.stopScan(ContextUtil.app);
                            PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.User_Sdk_type, ((WristBand) SearchActivity.this.devs.get(position)).getSdkType());
                            if (((WristBand) SearchActivity.this.devs.get(position)).getSdkType() == 2) {
                                if (((WristBand) SearchActivity.this.devs.get(position)).getName().indexOf("Voice18") != -1) {
                                    PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.EARPHONE, 1);
                                } else {
                                    PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.EARPHONE, 2);
                                }
                            }
                            if (((WristBand) SearchActivity.this.devs.get(position)).getSdkType() == 4) {
                                if (((WristBand) SearchActivity.this.devs.get(position)).getName().indexOf("I7E") != -1) {
                                    PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.PROTOBUF, 2);
                                } else {
                                    PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.PROTOBUF, 1);
                                }
                            }
                            if (!TextUtils.isEmpty(((WristBand) SearchActivity.this.devs.get(position)).getAddress())) {
                                SearchActivity.this.mPresenter.connect(ContextUtil.app, (WristBand) SearchActivity.this.devs.get(position));
                                SearchActivity.this.showConnectDialog(true);
                                SearchActivity.this.devs.clear();
                            }
                        } else if (AppConfigUtil.isNewfit()) {
                            PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.User_Sdk_type, 3);
                            SearchActivity.this.mPresenter.connect(ContextUtil.app, (WristBand) SearchActivity.this.devs.get(position));
                            SearchActivity.this.showConnectDialog(true);
                            SearchActivity.this.devs.clear();
                        } else {
                            new Builder(SearchActivity.this).setTitle(SearchActivity.this.getString(R.string.device_module_pls_check_bracelet_type)).setSingleChoiceItems(SearchActivity.this.items, -1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    KLog.i("------------H " + ((DataBean) SearchActivity.this.data.get(which)).getCategoryname() + "  " + which + "   " + ((DataBean) SearchActivity.this.data.get(which)).getSdktype() + "  " + ((WristBand) SearchActivity.this.devs.get(position)).getName());
                                    if (!AppConfigUtil.isZeronerHealthPro()) {
                                        PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.User_Sdk_type, ((WristBand) SearchActivity.this.devs.get(position)).getSdkType());
                                    } else {
                                        PrefUtil.save((Context) ContextUtil.app, SharedPreferencesAction.User_Sdk_type, which + 1);
                                    }
                                    SearchActivity.this.mPresenter.connect(ContextUtil.app, (WristBand) SearchActivity.this.devs.get(position));
                                    SearchActivity.this.showConnectDialog(true);
                                    SearchActivity.this.devs.clear();
                                    dialog.dismiss();
                                }
                            }).create().show();
                        }
                    }
                } catch (Exception e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        this.mPresenter.subscribe();
        PermissionsUtils.handleLOCATION(this, new PermissinCallBack() {
            public void callBackOk() {
                SearchActivity.this.devs.clear();
                SearchActivity.this.devsCopy.clear();
                SearchActivity.this.scanSize = 0;
                SearchActivity.this.mAdapter.notifyDataSetChanged();
                SearchActivity.this.searchStartScan();
                SearchActivity.this.statue = 10001;
                SearchActivity.this.showSearchDialog(true);
            }

            public void callBackFial() {
                CustomToast.makeText(SearchActivity.this, SearchActivity.this.getString(R.string.device_module_gps_location_fail));
            }
        });
    }

    public void onPause() {
        super.onPause();
        this.mPresenter.unsubscribe();
    }

    @SuppressLint({"RestrictedApi"})
    public void setPresenter(@NonNull Presenter presenter) {
        this.mPresenter = (Presenter) Preconditions.checkNotNull(presenter);
    }

    /* access modifiers changed from: private */
    public void addListView() {
        this.devs.clear();
        this.devs.addAll(removeDuplicatedElements(this.devsCopy));
        if (this.scanSize >= 30) {
            this.mPresenter.stopScan(this);
            KLog.i("=================stopScan===================");
        }
        this.mAdapter.notifyDataSetChanged();
    }

    private static ArrayList<WristBand> removeDuplicatedElements(ArrayList<WristBand> list) {
        KLog.d(list);
        Collections.sort(list);
        return list;
    }

    public void onScanResult(BluetoothDevice device, int rssi, byte[] scanRecord, String nameFromScanRecord) {
        this.mNowTime = System.currentTimeMillis();
        WristBand wristBand = new WristBand();
        if (classId != 0 && !TextUtils.isEmpty(nameFromScanRecord) && !this.connectDialog.isShowing()) {
            this.mPresenter.searchSuccess();
            int sdkTypeByDeviceName = this.mPresenter.getSDKTypeByDeviceName(ContextUtil.app, classId, nameFromScanRecord);
            KLog.i(device.getName() + "=sdkType=" + sdkTypeByDeviceName + "deviceAddress" + device.getAddress() + "  rssi:" + rssi);
            if (sdkTypeByDeviceName >= 0) {
                wristBand.setSdkType(sdkTypeByDeviceName);
                if (this.isHealthy) {
                    wristBand.setAlias(HealthyUtil.getNewName(nameFromScanRecord));
                } else {
                    wristBand.setAlias(nameFromScanRecord);
                }
                wristBand.setName(nameFromScanRecord);
                wristBand.setAddress(device.getAddress());
                wristBand.setRssi(rssi);
                if (!this.devsCopy.contains(wristBand)) {
                    this.scanSize++;
                    this.devsCopy.add(0, wristBand);
                }
            } else if (nameFromScanRecord.contains("XXX")) {
                wristBand.setSdkType(-1);
                if (this.isHealthy) {
                    wristBand.setAlias(HealthyUtil.getNewName(nameFromScanRecord));
                } else {
                    wristBand.setAlias(nameFromScanRecord);
                }
                wristBand.setName(nameFromScanRecord);
                wristBand.setAddress(device.getAddress());
                if (!this.devsCopy.contains(wristBand)) {
                    this.devsCopy.add(wristBand);
                }
            }
            if (this.mNowTime - this.mLastAddTime > 1200) {
                addListView();
                this.mLastAddTime = this.mNowTime;
                if (!this.scrollFlag) {
                    this.searchList.smoothScrollToPosition(0);
                    this.scrollFlag = true;
                }
                this.mHandler.postDelayed(this.smoothScrollTo, 3000);
            }
        }
    }

    public void onError(int code) {
    }

    public void onClick(android.view.View v) {
        if (v.getId() == R.id.ble_search_again) {
            this.devs.clear();
            this.devsCopy.clear();
            this.scanSize = 0;
            if (this.statue == 10001) {
                this.mAdapter.notifyDataSetChanged();
                showSearchDialog(true);
                scanNothingViewShow(false);
                this.mPresenter.stopScan(ContextUtil.app);
                searchStartScan();
                this.statue = 10001;
                BluetoothOperation.setNeedReconnect(false);
            } else if (this.statue != Btn_Connect_Statue) {
            } else {
                if (!BluetoothOperation.isConnected()) {
                    showConnectDialog(true);
                    connectFailedViewShow(false);
                    if (BluetoothOperation.getWristBand() != null) {
                        this.mPresenter.connect(ContextUtil.app, BluetoothOperation.getWristBand());
                        return;
                    }
                    KLog.e(" no2855-->脸getWristBand()==null");
                    L.file(" search activity getWristBand()==null", 4);
                    BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                    return;
                }
                finish();
                postEventBus();
            }
        }
    }

    private void postEventBus() {
        EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Setting));
        EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Top_Switch_To_Setting));
    }

    public void searchTimeout() {
        scanNothingViewShow(true);
    }

    public void connectFail() {
        showConnectDialog(false);
        connectFailedViewShow(true);
        this.statue = Btn_Connect_Statue;
        KLog.e("====connectFail====");
    }

    public void connectSuccess() {
        showConnectDialog(false);
        KLog.i("=====connectSuccess()=====" + BluetoothOperation.isBind());
        finish();
        postEventBus();
    }

    public void bluetoothStatus() {
        KLog.i("没有打开蓝牙");
        BluetoothOperation.checkBluetooth(this, ReqCode);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data2) {
        super.onActivityResult(requestCode, resultCode, data2);
        if (requestCode == 12520) {
            showConnectDialog(false);
            showSearchDialog(true);
            scanNothingViewShow(false);
            searchStartScan();
            this.statue = 10001;
        }
    }

    /* access modifiers changed from: private */
    public void searchStartScan() {
        this.mPresenter.startScan(this);
        this.view.postDelayed(new Runnable() {
            public void run() {
            }
        }, 20000);
        scanNothingViewShow(false);
    }

    private void scanNothingViewShow(boolean flag) {
        if (flag) {
            this.searchNothing.setVisibility(0);
            this.searchList.setVisibility(8);
            return;
        }
        this.searchNothing.setVisibility(8);
        this.searchList.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void connectFailedViewShow(boolean flag) {
        if (flag) {
            this.connectFailed.setVisibility(0);
            this.searchList.setVisibility(8);
            this.bleSearchAgain.setVisibility(0);
            this.bleSearchAgain.setText(R.string.device_module_ble_connect_again);
            return;
        }
        this.connectFailed.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void hideSearchAgain(boolean flag) {
        if (flag) {
            this.bleSearchAgain.setVisibility(0);
        } else {
            this.bleSearchAgain.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    public void showSearchDialog(boolean show) {
        hideSearchAgain(true);
        if (show) {
            this.devs.clear();
            this.devsCopy.clear();
            this.scanSize = 0;
        }
    }

    /* access modifiers changed from: private */
    public void showConnectDialog(boolean show) {
        KLog.e("=======================showConnectDialog====================" + show);
        if (!BluetoothOperation.isEnabledBluetooth()) {
            try {
                if (this.connectDialog != null && this.connectDialog.isShowing()) {
                    this.connectDialog.dismiss();
                }
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
            KLog.i("蓝牙未打开");
        } else if (show) {
            this.connectDialog.show();
            this.mHandler.removeCallbacks(this.dialogDismiss);
            this.mHandler.postDelayed(this.dialogDismiss, 60000);
        } else if (!isFinishing()) {
            this.connectDialog.dismiss();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(ContextUtil.app).unregisterReceiver(this.receiver);
        Scanner.getInstance(this).setIScanCallback(null);
    }
}
