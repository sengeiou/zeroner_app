package com.iwown.device_module.device_operation.search;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import com.iwown.device_module.R;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.view.dialog.ConnectingDialog;
import com.peng.ppscalelibrary.BleManager.Interface.BleDataProtocoInterface;
import com.peng.ppscalelibrary.BleManager.Manager.BleManager;
import com.peng.ppscalelibrary.BleManager.Model.BleDeviceModel;
import com.peng.ppscalelibrary.BleManager.Model.BleEnum.BleSex;
import com.peng.ppscalelibrary.BleManager.Model.BleEnum.BleUnit;
import com.peng.ppscalelibrary.BleManager.Model.BleUserModel;
import com.peng.ppscalelibrary.BleManager.Model.LFPeopleGeneral;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class HealthScaleConnectActivity extends DeviceModuleBaseActivity {
    /* access modifiers changed from: private */
    public BleManager bleManager;
    private Button bleSearchAgain;
    private ConnectingDialog connectDialog;
    private LinearLayout connectFailed;
    private LinearLayout searchNothing;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_fragment_search);
        initView();
        initData();
    }

    private void initView() {
        this.connectDialog = new ConnectingDialog(this);
        this.bleSearchAgain = (Button) findViewById(R.id.ble_search_again);
        this.connectDialog.show();
        setLeftBackTo();
        setTitleText(getString(R.string.home_device));
        this.bleSearchAgain = (Button) findViewById(R.id.ble_search_again);
        this.searchNothing = (LinearLayout) findViewById(R.id.search_nothing);
        this.connectFailed = (LinearLayout) findViewById(R.id.connect_fail);
    }

    private void initData() {
        List<BleDeviceModel> deviceList = new ArrayList<>();
        this.bleManager = BleManager.shareInstance(getApplication());
        this.bleManager.searchDevice(true, deviceList, new BleUserModel(180, 18, BleSex.Male, BleUnit.BLE_UNIT_KG, 0), new BleDataProtocoInterface() {
            public void progressData(LFPeopleGeneral lfPeopleGeneral) {
                KLog.e("no2855--> 返回的变化的体重: " + JsonUtils.toJson(lfPeopleGeneral));
            }

            public void lockedData(LFPeopleGeneral lfPeopleGeneral, BleDeviceModel bleDeviceModel) {
                KLog.e("no2855--> 返回的体重数据: " + JsonUtils.toJson(lfPeopleGeneral));
                KLog.e("no2855--> 返回的体重数据22: " + JsonUtils.toJson(bleDeviceModel));
                HealthScaleConnectActivity.this.bleManager.stopSearch();
            }

            public void historyData(boolean b, LFPeopleGeneral lfPeopleGeneral, String s) {
                KLog.e("no2855--> 历史的体重: " + b);
            }

            public void deviceInfo(BleDeviceModel bleDeviceModel) {
                KLog.e("no2855--> 设备: " + JsonUtils.toJson(bleDeviceModel));
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.bleManager.stopSearch();
    }
}
