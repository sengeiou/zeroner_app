package com.iwown.device_module.device_vibration;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
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
import com.iwown.device_module.device_vibration.bean.VibrationIvMtk;
import com.iwown.device_module.device_vibration.bean.VibrationPb;
import com.iwown.device_module.device_vibration.bean.VibrationZg;

public class VibrationSettingUtils {
    public static VibrationIvMtk mtkVibration() {
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
            VibrationIvMtk mtk = new VibrationIvMtk();
            ThrowableExtension.printStackTrace(e);
            return mtk;
        }
    }

    public static VibrationIvMtk ivVibration() {
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
            VibrationIvMtk iv = new VibrationIvMtk();
            ThrowableExtension.printStackTrace(e);
            return iv;
        }
    }

    public static VibrationZg zgVibration() {
        try {
            if (!BluetoothOperation.isZg()) {
                return new VibrationZg();
            }
            ZG_BaseInfo zgBase = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Vibration_Mode);
            if (zgBase == null || TextUtils.isEmpty(zgBase.getContent())) {
                return new VibrationZg();
            }
            return (VibrationZg) JsonUtils.fromJson(zgBase.getContent(), VibrationZg.class);
        } catch (Exception e) {
            VibrationZg zg = new VibrationZg();
            ThrowableExtension.printStackTrace(e);
            return zg;
        }
    }

    public static void saveVibration(int mode, int num, int type) {
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
}
