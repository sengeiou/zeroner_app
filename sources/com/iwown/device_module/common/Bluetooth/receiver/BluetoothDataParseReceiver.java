package com.iwown.device_module.common.Bluetooth.receiver;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.CommandOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.IVDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.MtkDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.receiver.proto.ProtoBufDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MTKHeadSetSync;
import com.iwown.device_module.common.Bluetooth.sync.mtk.MtkSync;
import com.iwown.device_module.common.Bluetooth.sync.proto.ProtoBufSync;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;

public class BluetoothDataParseReceiver extends BluetoothCallbackReceiver {
    public static Application app = ContextUtil.app;
    private Runnable initRunable = new Runnable() {
        public void run() {
            if (!BluetoothOperation.isMTKEarphone() || !MTKHeadSetSync.getInstance().isGpsSporting()) {
                CommandOperation.initCommand();
                return;
            }
            L.file("gps运动下 耳机突然断连后重连", 4);
            BluetoothOperation.switchStandardHeartRate(true);
        }
    };
    private Handler mHandler = new Handler(Looper.myLooper());

    public void onDataArrived(Context context, int ble_sdk_type, int dataType, String data) {
        super.onDataArrived(context, ble_sdk_type, dataType, data);
        switch (ble_sdk_type) {
            case 1:
                IVDataParsePresenter.parseProtocalData(context, dataType, data);
                return;
            case 2:
                MtkDataParsePresenter.parseProtoclData(context, dataType, data);
                return;
            case 3:
                ZGDataParsePresenter.parseProtoclData(context, dataType, data);
                return;
            case 4:
                ProtoBufDataParsePresenter.parseProtocolData(context, dataType, data);
                return;
            default:
                return;
        }
    }

    public void onBluetoothInit() {
        super.onBluetoothInit();
        try {
            if (BluetoothOperation.getWristBand() != null) {
                PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device, BluetoothOperation.getWristBand().getName());
                PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device, BluetoothOperation.getWristBand().getAddress());
                PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Device_Alias_Current_Device, BluetoothOperation.getWristBand().getAlias());
            }
            this.mHandler.removeCallbacks(this.initRunable);
            this.mHandler.postDelayed(this.initRunable, 1000);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void connectStatue(boolean isConnect) {
        super.connectStatue(isConnect);
        if (!isConnect) {
            PrefUtil.save((Context) ContextUtil.app, BleAction.Bluetooth_Sync_Heart_Beat_Time, 0);
            MtkSync.getInstance().setmIsSyncDataInfo(false);
            ProtoBufSync.getInstance().setSync(false);
            ProtoBufSync.isFirstSync = false;
        }
    }

    public static void setNewConnectProtocol() {
        if (PrefUtil.getBoolean(ContextUtil.app, FirmwareAction.Firmware_New_Protocol)) {
            L.file("设置新的连接协议", 4);
            KLog.e("设置新的连接协议");
            byte[] bytes = ZeronerSendBluetoothCmdImpl.getInstance().setWristBandBle(0, 0, 0, 0, 0, 0);
            DataBean dataBean = new DataBean();
            dataBean.addData(bytes);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(app, dataBean);
            BluetoothOperation.postHeartData(0);
        }
    }
}
