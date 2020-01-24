package com.iwown.device_module.device_vibration;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.iwown.task.ZeronerBleWriteDataTask;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.BleWriteDataTask;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
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
import com.iwown.device_module.device_setting.configure.DeviceSettingsBiz;
import com.iwown.device_module.device_vibration.bean.VibrationIvMtk;
import com.iwown.device_module.device_vibration.bean.VibrationPb;
import com.iwown.device_module.device_vibration.bean.VibrationZg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VibrationSettingPresenter implements VibrationPresenter {
    public static final int TYPE_CLOCK = 2;
    public static final int TYPE_HEART_GUIDE = 6;
    public static final int TYPE_PHONE = 1;
    public static final int TYPE_SCHEDULE = 3;
    public static final int TYPE_SEDENTARY = 5;
    public static final int TYPE_SMS = 4;
    public static final int default_vibration = 99;
    private View view;

    public VibrationSettingPresenter(View view2) {
        this.view = view2;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }

    public void saveVibration(int mode, int num, int type) {
        VibrationIvMtk iv = new VibrationIvMtk();
        VibrationIvMtk mtk = new VibrationIvMtk();
        VibrationZg zg = new VibrationZg();
        VibrationPb pb = new VibrationPb();
        try {
            if (BluetoothOperation.isZg()) {
                ZG_BaseInfo zgBase = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                zg = (zgBase == null || TextUtils.isEmpty(zgBase.getContent())) ? new VibrationZg() : (VibrationZg) JsonUtils.fromJson(zgBase.getContent(), VibrationZg.class);
            } else if (BluetoothOperation.isIv()) {
                DeviceBaseInfo ivBase = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                iv = (ivBase == null || TextUtils.isEmpty(ivBase.getContent())) ? new VibrationIvMtk() : (VibrationIvMtk) JsonUtils.fromJson(ivBase.getContent(), VibrationIvMtk.class);
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo mtkBase = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                mtk = (mtkBase == null || TextUtils.isEmpty(mtkBase.getContent())) ? new VibrationIvMtk() : (VibrationIvMtk) JsonUtils.fromJson(mtkBase.getContent(), VibrationIvMtk.class);
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo pbBaseInfo = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                pb = (pbBaseInfo == null || TextUtils.isEmpty(pbBaseInfo.getContent())) ? new VibrationPb() : (VibrationPb) JsonUtils.fromJson(pbBaseInfo.getContent(), VibrationPb.class);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (mode != 99) {
            if (num == 99) {
                switch (type) {
                    case 1:
                        if (!BluetoothOperation.isZg()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (!BluetoothOperation.isIv()) {
                                    if (BluetoothOperation.isProtoBuf()) {
                                        pb.setPb_phoneShakeMode(mode);
                                        break;
                                    }
                                } else {
                                    iv.setPhoneShakeMode(mode);
                                    break;
                                }
                            } else {
                                mtk.setPhoneShakeMode(mode);
                                break;
                            }
                        } else {
                            zg.setZg_phoneShakeMode(mode);
                            break;
                        }
                        break;
                    case 2:
                        if (!BluetoothOperation.isZg()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (!BluetoothOperation.isIv()) {
                                    if (BluetoothOperation.isProtoBuf()) {
                                        pb.setPb_clockShakeMode(mode);
                                        break;
                                    }
                                } else {
                                    iv.setClockShakeMode(mode);
                                    break;
                                }
                            } else {
                                mtk.setClockShakeMode(mode);
                                break;
                            }
                        } else {
                            zg.setZg_clockShakeMode(mode);
                            break;
                        }
                        break;
                    case 3:
                        if (!BluetoothOperation.isZg()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (!BluetoothOperation.isIv()) {
                                    if (BluetoothOperation.isProtoBuf()) {
                                        pb.setPb_scheduleShakeMode(mode);
                                        break;
                                    }
                                } else {
                                    iv.setScheduleShakeMode(mode);
                                    break;
                                }
                            } else {
                                mtk.setScheduleShakeMode(mode);
                                break;
                            }
                        } else {
                            zg.setZg_scheduleShakeMode(mode);
                            break;
                        }
                        break;
                    case 4:
                        if (!BluetoothOperation.isZg()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (!BluetoothOperation.isIv()) {
                                    if (BluetoothOperation.isProtoBuf()) {
                                        pb.setPb_smsShakeMode(mode);
                                        break;
                                    }
                                } else {
                                    iv.setSmsShakeMode(mode);
                                    break;
                                }
                            } else {
                                mtk.setSmsShakeMode(mode);
                                break;
                            }
                        } else {
                            zg.setZg_smsShakeMode(mode);
                            break;
                        }
                        break;
                    case 5:
                        if (!BluetoothOperation.isZg()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (!BluetoothOperation.isIv()) {
                                    if (BluetoothOperation.isProtoBuf()) {
                                        pb.setPb_sedentaryShakeMode(mode);
                                        break;
                                    }
                                } else {
                                    iv.setSedentaryShakeMode(mode);
                                    break;
                                }
                            } else {
                                mtk.setSedentaryShakeMode(mode);
                                break;
                            }
                        } else {
                            zg.setZg_sedentaryShakeMode(mode);
                            break;
                        }
                        break;
                    case 6:
                        if (!BluetoothOperation.isZg()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (!BluetoothOperation.isIv()) {
                                    if (BluetoothOperation.isProtoBuf()) {
                                        pb.setPb_heartGuideShakeMode(mode);
                                        break;
                                    }
                                } else {
                                    iv.setHeartGuideShakeMode(mode);
                                    break;
                                }
                            } else {
                                mtk.setHeartGuideShakeMode(mode);
                                break;
                            }
                        } else {
                            zg.setZg_heartGuideShakeMode(mode);
                            break;
                        }
                        break;
                }
            }
        } else {
            switch (type) {
                case 1:
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isMtk()) {
                            if (!BluetoothOperation.isIv()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    pb.setPb_phoneShakeNum(num);
                                    break;
                                }
                            } else {
                                iv.setPhoneShakeNum(num);
                                break;
                            }
                        } else {
                            mtk.setPhoneShakeNum(num);
                            break;
                        }
                    } else {
                        zg.setZg_phoneShakeNum(num);
                        break;
                    }
                    break;
                case 2:
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isMtk()) {
                            if (!BluetoothOperation.isIv()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    pb.setPb_clockShakeNum(num);
                                    break;
                                }
                            } else {
                                iv.setClockShakeNum(num);
                                break;
                            }
                        } else {
                            mtk.setClockShakeNum(num);
                            break;
                        }
                    } else {
                        zg.setZg_clockShakeNum(num);
                        break;
                    }
                    break;
                case 3:
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isMtk()) {
                            if (!BluetoothOperation.isIv()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    pb.setPb_scheduleShakeNum(num);
                                    break;
                                }
                            } else {
                                iv.setScheduleShakeNum(num);
                                break;
                            }
                        } else {
                            mtk.setScheduleShakeNum(num);
                            break;
                        }
                    } else {
                        zg.setZg_scheduleShakeNum(num);
                        break;
                    }
                    break;
                case 4:
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isMtk()) {
                            if (!BluetoothOperation.isIv()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    pb.setPb_smsShakeNum(num);
                                    break;
                                }
                            } else {
                                iv.setSmsShakeNum(num);
                                break;
                            }
                        } else {
                            mtk.setSmsShakeNum(num);
                            break;
                        }
                    } else {
                        zg.setZg_smsShakeNum(num);
                        break;
                    }
                    break;
                case 5:
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isMtk()) {
                            if (!BluetoothOperation.isIv()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    pb.setPb_sedentaryShakeNum(num);
                                    break;
                                }
                            } else {
                                iv.setSedentaryShakeNum(num);
                                break;
                            }
                        } else {
                            mtk.setSedentaryShakeNum(num);
                            break;
                        }
                    } else {
                        zg.setZg_sedentaryShakeNum(num);
                        break;
                    }
                    break;
                case 6:
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isMtk()) {
                            if (!BluetoothOperation.isIv()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    pb.setPb_heartGuideShakeNum(num);
                                    break;
                                }
                            } else {
                                iv.setHeartGuideShakeNum(num);
                                break;
                            }
                        } else {
                            mtk.setHeartGuideShakeNum(num);
                            break;
                        }
                    } else {
                        zg.setZg_heartGuideShakeNum(num);
                        break;
                    }
                    break;
            }
        }
        if (BluetoothOperation.isIv()) {
            DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Vibration_Mode, JsonUtils.toJson(iv));
        } else if (BluetoothOperation.isZg()) {
            ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Vibration_Mode, JsonUtils.toJson(zg));
        } else if (BluetoothOperation.isMtk()) {
            Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Vibration_Mode, JsonUtils.toJson(mtk));
        } else if (BluetoothOperation.isProtoBuf()) {
            PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Vibration_Mode, JsonUtils.toJson(pb));
        }
    }

    public void writeVibrationToDevice() {
        VibrationPb pb;
        VibrationIvMtk mtk;
        VibrationIvMtk iv;
        VibrationZg zg;
        new VibrationIvMtk();
        new VibrationIvMtk();
        new VibrationZg();
        new VibrationPb();
        try {
            if (BluetoothOperation.isZg()) {
                ZG_BaseInfo zgBase = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                if (zgBase == null || TextUtils.isEmpty(zgBase.getContent())) {
                    zg = new VibrationZg();
                } else {
                    zg = (VibrationZg) JsonUtils.fromJson(zgBase.getContent(), VibrationZg.class);
                }
                BleDataOrderHandler.getInstance().setShake(ContextUtil.app, zg.getZg_phoneShakeMode(), zg.getZg_phoneShakeNum(), zg.getZg_smsShakeMode(), zg.getZg_smsShakeNum(), zg.getZg_sedentaryShakeMode(), zg.getZg_sedentaryShakeNum(), zg.getZg_heartGuideShakeMode(), zg.getZg_heartGuideShakeNum());
            } else if (BluetoothOperation.isIv()) {
                DeviceBaseInfo ivBase = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                if (ivBase == null || TextUtils.isEmpty(ivBase.getContent())) {
                    iv = new VibrationIvMtk();
                } else {
                    iv = (VibrationIvMtk) JsonUtils.fromJson(ivBase.getContent(), VibrationIvMtk.class);
                }
                ArrayList<Map<String, Integer>> map = new ArrayList<>();
                Map<String, Integer> phoneMap = createMap(iv.getPhoneShakeMode(), iv.getPhoneShakeNum(), 1);
                Map<String, Integer> clockMap = createMap(iv.getClockShakeMode(), iv.getClockShakeNum(), 0);
                Map<String, Integer> scheduleMap = createMap(iv.getScheduleShakeMode(), iv.getScheduleShakeNum(), 5);
                Map<String, Integer> smsMap = createMap(iv.getSmsShakeMode(), iv.getSmsShakeNum(), 2);
                Map<String, Integer> sedentaryMap = createMap(iv.getSedentaryShakeMode(), iv.getSedentaryShakeNum(), 3);
                Map<String, Integer> heartGuideMap = createMap(iv.getHeartGuideShakeMode(), iv.getHeartGuideShakeNum(), 7);
                map.add(phoneMap);
                map.add(clockMap);
                map.add(scheduleMap);
                map.add(smsMap);
                map.add(sedentaryMap);
                map.add(heartGuideMap);
                byte[] data = ZeronerSendBluetoothCmdImpl.getInstance().setShakeMode(3, 0, 0, map);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < data.length; i += 20) {
                    if (i + 20 > data.length) {
                        ArrayList arrayList2 = new ArrayList();
                        byte[] writeData = Arrays.copyOfRange(data, i, data.length);
                        DataBean bean = new DataBean();
                        arrayList2.add(writeData);
                        bean.setData(arrayList2);
                        bean.setFlag(false);
                        ZeronerBleWriteDataTask zeronerBleWriteDataTask = new ZeronerBleWriteDataTask(ContextUtil.app, bean);
                        arrayList2.add(writeData);
                        arrayList.add(zeronerBleWriteDataTask);
                    } else {
                        ArrayList arrayList3 = new ArrayList();
                        byte[] writeData2 = Arrays.copyOfRange(data, i, i + 20);
                        DataBean bean2 = new DataBean();
                        arrayList3.add(writeData2);
                        bean2.setData(arrayList3);
                        bean2.setFlag(true);
                        ZeronerBleWriteDataTask zeronerBleWriteDataTask2 = new ZeronerBleWriteDataTask(ContextUtil.app, bean2);
                        arrayList.add(zeronerBleWriteDataTask2);
                    }
                }
                ZeronerBackgroundThreadManager.getInstance().addAllTask(arrayList);
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo mtkBase = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                if (mtkBase == null || TextUtils.isEmpty(mtkBase.getContent())) {
                    mtk = new VibrationIvMtk();
                } else {
                    mtk = (VibrationIvMtk) JsonUtils.fromJson(mtkBase.getContent(), VibrationIvMtk.class);
                }
                ArrayList<Map<String, Integer>> map2 = new ArrayList<>();
                Map<String, Integer> phoneMap2 = createMap(mtk.getPhoneShakeMode(), mtk.getPhoneShakeNum(), 1);
                Map<String, Integer> clockMap2 = createMap(mtk.getClockShakeMode(), mtk.getClockShakeNum(), 0);
                Map<String, Integer> scheduleMap2 = createMap(mtk.getScheduleShakeMode(), mtk.getScheduleShakeNum(), 5);
                Map<String, Integer> smsMap2 = createMap(mtk.getSmsShakeMode(), mtk.getSmsShakeNum(), 2);
                Map<String, Integer> sedentaryMap2 = createMap(mtk.getSedentaryShakeMode(), mtk.getSedentaryShakeNum(), 3);
                Map<String, Integer> heartGuideMap2 = createMap(mtk.getHeartGuideShakeMode(), mtk.getHeartGuideShakeNum(), 7);
                map2.add(phoneMap2);
                map2.add(clockMap2);
                map2.add(scheduleMap2);
                map2.add(smsMap2);
                map2.add(sedentaryMap2);
                map2.add(heartGuideMap2);
                byte[] bytes = MtkCmdAssembler.getInstance().setShakeMode(3, 0, 0, map2);
                ArrayList arrayList4 = new ArrayList();
                for (int i2 = 0; i2 < bytes.length; i2 += 20) {
                    if (i2 + 20 > bytes.length) {
                        BleWriteDataTask bleWriteDataTask = new BleWriteDataTask(ContextUtil.app, Arrays.copyOfRange(bytes, i2, bytes.length));
                        bleWriteDataTask.setFlag(false);
                        arrayList4.add(bleWriteDataTask);
                    } else {
                        BleWriteDataTask bleWriteDataTask2 = new BleWriteDataTask(ContextUtil.app, Arrays.copyOfRange(bytes, i2, i2 + 20));
                        bleWriteDataTask2.setFlag(false);
                        arrayList4.add(bleWriteDataTask2);
                    }
                }
                MtkBackgroundThreadManager.getInstance().addAllTask(arrayList4);
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo pbBaseInfo = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
                if (pbBaseInfo == null || TextUtils.isEmpty(pbBaseInfo.getContent())) {
                    pb = new VibrationPb();
                } else {
                    pb = (VibrationPb) JsonUtils.fromJson(pbBaseInfo.getContent(), VibrationPb.class);
                }
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMototConf(pb.getPb_clockShakeMode(), pb.getPb_clockShakeNum(), 0));
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMototConf(pb.getPb_phoneShakeMode(), pb.getPb_phoneShakeNum(), 1));
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMototConf(pb.getPb_smsShakeMode(), pb.getPb_smsShakeNum(), 2));
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMototConf(pb.getPb_sedentaryShakeMode(), pb.getPb_sedentaryShakeNum(), 3));
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMototConf(pb.getPb_scheduleShakeMode(), pb.getPb_scheduleShakeNum(), 5));
                BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMototConf(pb.getPb_heartGuideShakeMode(), pb.getPb_heartGuideShakeNum(), 7));
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public VibrationIvMtk mtkVibration() {
        try {
            if (!BluetoothOperation.isMtk()) {
                return null;
            }
            Mtk_DeviceBaseInfo mtkBase = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
            if (mtkBase == null || TextUtils.isEmpty(mtkBase.getContent())) {
                return new VibrationIvMtk();
            }
            return (VibrationIvMtk) JsonUtils.fromJson(mtkBase.getContent(), VibrationIvMtk.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public VibrationIvMtk ivVibration() {
        try {
            if (!BluetoothOperation.isIv()) {
                return null;
            }
            DeviceBaseInfo ivBase = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
            if (ivBase == null || TextUtils.isEmpty(ivBase.getContent())) {
                return new VibrationIvMtk();
            }
            return (VibrationIvMtk) JsonUtils.fromJson(ivBase.getContent(), VibrationIvMtk.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public VibrationZg zgVibration() {
        try {
            if (!BluetoothOperation.isZg()) {
                return null;
            }
            ZG_BaseInfo zgBase = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
            if (zgBase == null || TextUtils.isEmpty(zgBase.getContent())) {
                return new VibrationZg();
            }
            return (VibrationZg) JsonUtils.fromJson(zgBase.getContent(), VibrationZg.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public VibrationPb pbVibration() {
        try {
            if (!BluetoothOperation.isProtoBuf()) {
                return null;
            }
            PbBaseInfo pbBaseInfo = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
            if (pbBaseInfo == null || TextUtils.isEmpty(pbBaseInfo.getContent())) {
                return new VibrationPb();
            }
            return (VibrationPb) JsonUtils.fromJson(pbBaseInfo.getContent(), VibrationPb.class);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    private static boolean isHaveShakeMode() {
        return DeviceSettingsBiz.getInstance().supportSomeSetting(12);
    }

    @NonNull
    private static Map<String, Integer> createMap(int mode, int num, int type) {
        Map<String, Integer> phoneMap = new HashMap<>();
        phoneMap.put("index", Integer.valueOf(mode));
        phoneMap.put("number", Integer.valueOf(num));
        phoneMap.put("type", Integer.valueOf(type));
        return phoneMap;
    }
}
