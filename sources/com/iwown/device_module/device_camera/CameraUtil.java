package com.iwown.device_module.device_camera;

import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.utils.ContextUtil;

public class CameraUtil {
    public static void sendCaneraCommand(boolean flag) {
        if (BluetoothOperation.isIv()) {
            byte[] datas = ZeronerSendBluetoothCmdImpl.getInstance().setWristBandSelfie(flag);
            DataBean dataBean3 = new DataBean();
            dataBean3.addData(datas);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean3);
        } else if (BluetoothOperation.isMtk()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setWristBandSelfie(flag));
        } else if (!BluetoothOperation.isProtoBuf()) {
        } else {
            if (flag) {
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setSmartShotData(1));
                return;
            }
            BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setSmartShotData(0));
        }
    }
}
