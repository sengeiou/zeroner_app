package com.iwown.device_module.device_long_sit.activity;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.receiver.iv.dao.DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.dao.Mtk_DeviceBaseInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.proto.dao.PbDeviceInfoSqlUtil;
import com.iwown.device_module.common.Bluetooth.receiver.zg.ZGDataParsePresenter;
import com.iwown.device_module.common.sql.DeviceBaseInfo;
import com.iwown.device_module.common.sql.Mtk_DeviceBaseInfo;
import com.iwown.device_module.common.sql.PbBaseInfo;
import com.iwown.device_module.common.sql.ZG_BaseInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_long_sit.bean.LongSitStatue;
import com.iwown.device_module.device_long_sit.iv.IvLongSit;
import com.iwown.device_module.device_long_sit.mtk.MtkLongSit;
import com.iwown.device_module.device_long_sit.pb.PbLongSit;
import com.iwown.device_module.device_long_sit.zg.ZgLongSit;

public class LongSeatPresenter implements Presenter {
    private View view;

    public LongSeatPresenter(View view2) {
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public void writeSedentaryIfLunchBreak(int startHour, int endHour, byte state, boolean lunchBreakOpen, int repeat, boolean longSitSwitch) {
        if (BluetoothOperation.isIv()) {
            if (longSitSwitch) {
                IvLongSit.getInstance().writeSedentaryIfLunchBreak(startHour, endHour, state, lunchBreakOpen, repeat);
            } else {
                IvLongSit.getInstance().writeSedentaryIfLunchBreak(startHour, endHour, state, lunchBreakOpen, 0);
            }
        } else if (BluetoothOperation.isMtk()) {
            if (longSitSwitch) {
                MtkLongSit.getInstance().writeSedentaryIfLunchBreak(startHour, endHour, state, lunchBreakOpen, repeat);
            } else {
                MtkLongSit.getInstance().writeSedentaryIfLunchBreak(startHour, endHour, state, lunchBreakOpen, 0);
            }
        } else if (BluetoothOperation.isZg()) {
            if (!longSitSwitch) {
                ZgLongSit.writeSedentaryAccordingApi(startHour, endHour, false);
            } else if (lunchBreakOpen) {
                ZgLongSit.writeSedentaryIfLunchBreak(startHour, endHour);
            } else {
                ZgLongSit.writeSedentaryAccordingApi(startHour, endHour, true);
            }
        } else if (!BluetoothOperation.isProtoBuf()) {
        } else {
            if (longSitSwitch) {
                PbLongSit.getInstance().writeSedentaryIfLunchBreak(startHour, endHour, state, lunchBreakOpen, repeat);
            } else {
                PbLongSit.getInstance().writeSedentaryIfLunchBreak(startHour, endHour, state, lunchBreakOpen, 0);
            }
        }
    }

    public byte judgeWhatState(int startHour, int endHour) {
        if (BluetoothOperation.isIv()) {
            return IvLongSit.getInstance().judgeWhatState(startHour, endHour);
        }
        if (BluetoothOperation.isMtk()) {
            return MtkLongSit.getInstance().judgeWhatState(startHour, endHour);
        }
        if (BluetoothOperation.isProtoBuf()) {
            return PbLongSit.getInstance().judgeWhatState(startHour, endHour);
        }
        return 0;
    }

    public LongSitStatue longSeatStatue() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Long_Sit);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (LongSitStatue) JsonUtils.fromJson(info.getContent(), LongSitStatue.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Long_Sit);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (LongSitStatue) JsonUtils.fromJson(info2.getContent(), LongSitStatue.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Long_Sit);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (LongSitStatue) JsonUtils.fromJson(info3.getContent(), LongSitStatue.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo info4 = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Long_Sit);
                if (info4 != null && !TextUtils.isEmpty(info4.getContent())) {
                    return (LongSitStatue) JsonUtils.fromJson(info4.getContent(), LongSitStatue.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new LongSitStatue();
    }

    public void saveLongSeatStatue(LongSitStatue longSit) {
        if (longSit != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Long_Sit, JsonUtils.toJson(longSit));
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Long_Sit, JsonUtils.toJson(longSit));
            } else if (BluetoothOperation.isZg()) {
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Long_Sit, JsonUtils.toJson(longSit));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Long_Sit, JsonUtils.toJson(longSit));
            }
        }
    }

    public String getWeekRepeatStr(byte weakRepeat) {
        String[] daysOfWeek = ContextUtil.app.getResources().getStringArray(R.array.device_module_day_of_week);
        StringBuilder sb = new StringBuilder();
        if (weakRepeat == -1 || weakRepeat == Byte.MAX_VALUE) {
            sb.append(ContextUtil.app.getString(R.string.device_module_every_day));
            return sb.toString();
        }
        if ((weakRepeat & 64) != 0) {
            sb.append(daysOfWeek[0]);
            sb.append(",");
        }
        if ((weakRepeat & 32) != 0) {
            sb.append(daysOfWeek[1]);
            sb.append(",");
        }
        if ((weakRepeat & 16) != 0) {
            sb.append(daysOfWeek[2]);
            sb.append(",");
        }
        if ((weakRepeat & 8) != 0) {
            sb.append(daysOfWeek[3]);
            sb.append(",");
        }
        if ((weakRepeat & 4) != 0) {
            sb.append(daysOfWeek[4]);
            sb.append(",");
        }
        if ((weakRepeat & 2) != 0) {
            sb.append(daysOfWeek[5]);
            sb.append(",");
        }
        if ((weakRepeat & 1) != 0) {
            sb.append(daysOfWeek[6]);
            sb.append(",");
        }
        if (sb.length() == 0) {
            return "";
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
