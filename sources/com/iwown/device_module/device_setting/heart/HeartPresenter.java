package com.iwown.device_module.device_setting.heart;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.PbDeviceInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.sql.DeviceBaseInfo;
import com.iwown.device_module.common.sql.DevicePref;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.PbBaseInfo;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_setting.configure.DevicePrefBiz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.device_module.device_setting.configure.SettingDefault;
import com.iwown.device_module.device_setting.heart.bean.AutoHeartStatue;
import com.iwown.device_module.device_setting.heart.bean.HeartGuidanceStatue;
import com.socks.library.KLog;
import java.util.ArrayList;

public class HeartPresenter implements Presenter {
    public static int HEART_GUIDE_LEFT_START = 40;
    public static int HEART_GUIDE_RIGHT_START = 50;
    View view;

    public HeartPresenter() {
    }

    public HeartPresenter(View view2) {
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public AutoHeartStatue autoHeartStatue() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info.getContent(), AutoHeartStatue.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info2.getContent(), AutoHeartStatue.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info3.getContent(), AutoHeartStatue.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Rate);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (AutoHeartStatue) JsonUtils.fromJson(info4.getContent(), AutoHeartStatue.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new AutoHeartStatue();
    }

    public void saveAutoHeartStatue(AutoHeartStatue heart) {
        if (heart != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isZg()) {
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Rate, JsonUtils.toJson(heart));
            }
            DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
            if (devicePref != null && !TextUtils.isEmpty(devicePref.getSetting())) {
                ArrayList<SettingDefault> settingPref = JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class);
                if (settingPref != null && settingPref.size() > 0) {
                    DevicePrefBiz.getInstance().updatePrefToLocal(settingPref, null, devicePref, heart, null);
                }
            }
        }
    }

    public HeartGuidanceStatue heartGuidanceStatue() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info.getContent(), HeartGuidanceStatue.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info2.getContent(), HeartGuidanceStatue.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info3.getContent(), HeartGuidanceStatue.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Auto_Heart_Guidance);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (HeartGuidanceStatue) JsonUtils.fromJson(info4.getContent(), HeartGuidanceStatue.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new HeartGuidanceStatue();
    }

    public void saveHeartGuidance(HeartGuidanceStatue heart) {
        if (heart != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Guidance, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Guidance, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isZg()) {
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Message_Auto_Heart_Guidance, JsonUtils.toJson(heart));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Auto_Heart_Guidance, JsonUtils.toJson(heart));
            }
            DevicePref devicePref = DevicePrefBiz.getInstance().queryByUidModel(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
            if (devicePref != null && !TextUtils.isEmpty(devicePref.getSetting())) {
                ArrayList<SettingDefault> settingPref = JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class);
                if (settingPref != null && settingPref.size() > 0) {
                    DevicePrefBiz.getInstance().updatePrefToLocal(settingPref, null, devicePref, null, heart);
                }
            }
        }
    }

    public void writeCommandToDevice() {
        if (BluetoothOperation.isZg()) {
            DeviceUtils.writeCommandToDevice(13);
        } else if (BluetoothOperation.isIv()) {
            byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().setHeartRateWarming(DeviceUtils.heartGuidanceStatue().isHeart_guidance_switch(), DeviceUtils.heartGuidanceStatue().getMaxHeart(), DeviceUtils.heartGuidanceStatue().getMinHeart(), 30, 2);
            DataBean bean = new DataBean();
            bean.addData(data);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, bean);
        } else if (BluetoothOperation.isMtk()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setHeartRateWarming(DeviceUtils.heartGuidanceStatue().isHeart_guidance_switch(), DeviceUtils.heartGuidanceStatue().getMaxHeart(), DeviceUtils.heartGuidanceStatue().getMinHeart(), 30, 2));
        } else if (BluetoothOperation.isProtoBuf()) {
            DeviceUtils.writeCommandToDevice(13);
        }
    }

    public int[] getRemindInterval(int age, int type) {
        if (age == 0 || type == -1) {
            KLog.e("age=" + age + " remind_type" + type);
            return new int[]{0, 0};
        }
        switch (type) {
            case 0:
                return new int[]{(int) Math.round(0.5d * ((double) (220 - age))), (int) Math.round(((double) (220 - age)) * 0.6d)};
            case 1:
                return new int[]{((int) Math.round(((double) (220 - age)) * 0.6d)) + 1, (int) Math.round(((double) (220 - age)) * 0.7d)};
            case 2:
                return new int[]{((int) Math.round(((double) (220 - age)) * 0.7d)) + 1, (int) Math.round(0.8d * ((double) (220 - age)))};
            case 3:
                return new int[]{((int) Math.round(0.8d * ((double) (220 - age)))) + 1, (int) Math.round(0.9d * ((double) (220 - age)))};
            default:
                return new int[]{0, 0};
        }
    }

    public int isWhatRemindType(int age, int start1, int end1) {
        int start = start1;
        int end = end1;
        if (age == 0) {
            return -1;
        }
        if (Math.round(((double) (220 - age)) * 0.5d) == ((long) start) && Math.round(((double) (220 - age)) * 0.6d) == ((long) end)) {
            return 0;
        }
        if (Math.round(((double) (220 - age)) * 0.6d) + 1 == ((long) start) && Math.round(((double) (220 - age)) * 0.7d) == ((long) end)) {
            return 1;
        }
        if (Math.round(((double) (220 - age)) * 0.7d) + 1 == ((long) start) && Math.round(((double) (220 - age)) * 0.8d) == ((long) end)) {
            return 2;
        }
        if (Math.round(((double) (220 - age)) * 0.8d) + 1 == ((long) start) && Math.round(((double) (220 - age)) * 0.9d) == ((long) end)) {
            return 3;
        }
        return -1;
    }
}
