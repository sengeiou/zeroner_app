package com.iwown.device_module.device_firmware_upgrade.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.zg_ble.data.DateUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.activity.DeviceModuleBaseActivity;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.socks.library.KLog;

public class HardwareUpdateSuccessActivity extends DeviceModuleBaseActivity implements OnClickListener {
    public static final String EXTRA_YES_OR_NO = "extra_yes_or_no";
    private Button btNo;
    private Button btYes;
    private ImageView ivImg;
    private TextView tvComplete;
    private TextView tvCompleteTip;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        setLeftBackTo();
        setContentView(R.layout.device_module_activity_hardware_success);
        setTitleText(R.string.device_module_firmware_update);
        this.ivImg = (ImageView) findViewById(R.id.iv_img);
        this.tvComplete = (TextView) findViewById(R.id.tv_complete);
        this.tvCompleteTip = (TextView) findViewById(R.id.tv_complete_tip);
        this.btYes = (Button) findViewById(R.id.bt_yes);
        this.btNo = (Button) findViewById(R.id.bt_no);
    }

    private void initListener() {
        this.btYes.setOnClickListener(this);
        this.btNo.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_yes) {
            Intent intent1 = new Intent();
            intent1.setAction("com.hardware.update.success");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
            KLog.e("固件升级成功后蓝牙重连");
            PrefUtil.save((Context) this, BleAction.Bluetooth_Device_Address_Current_Device, PrefUtil.getString(this, FirmwareAction.Firmware_Update_Device_Mac));
            ContextUtil.isFirmwareUp = false;
            BluetoothOperation.connect(this, new WristBand(PrefUtil.getString(this, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(this, BleAction.Bluetooth_Device_Address_Current_Device)));
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Last_Sync_Setting_Time, 0);
            PrefUtil.save((Context) ContextUtil.app, FirmwareAction.Firmware_Weather_Update, 0);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_YES_OR_NO, true);
            setResult(-1, intent);
            finish();
        } else if (id == R.id.bt_no) {
            Intent intent2 = new Intent();
            intent2.putExtra(EXTRA_YES_OR_NO, false);
            setResult(-1, intent2);
            finish();
            Intent activity = new Intent(this, FirmwareUpgradeActivity.class);
            activity.putExtra(FirmwareUpgradeActivity.NOW_STEP, 7);
            startActivity(activity);
        }
    }

    public void back() {
        ToastUtil.showToast(getString(R.string.device_module_update_step_complete_tip));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        PrefUtil.save((Context) ContextUtil.app, UserAction.Bluetooth_Check_Firmware_Update_Time, new DateUtil().getUnixTimestamp() + 600);
    }
}
