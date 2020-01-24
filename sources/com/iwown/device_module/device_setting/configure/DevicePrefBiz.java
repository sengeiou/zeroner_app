package com.iwown.device_module.device_setting.configure;

import android.text.TextUtils;
import com.iwown.device_module.common.BaseActionUtils.SETTING_INDEXS;
import com.iwown.device_module.common.network.NetFactory;
import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.data.resp.DeviceSettingsDownCode.DataBean.SettingBean;
import com.iwown.device_module.common.network.data.resp.PrefServerResponse;
import com.iwown.device_module.common.sql.DevicePref;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_setting.fragment.SettingPresenter;
import com.iwown.device_module.device_setting.heart.HeartPresenter;
import com.iwown.device_module.device_setting.heart.bean.AutoHeartStatue;
import com.iwown.device_module.device_setting.heart.bean.HeartGuidanceStatue;
import com.iwown.device_module.device_setting.language.LanguageUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.litepal.crud.DataSupport;

public class DevicePrefBiz {
    private static DevicePrefBiz instance = null;
    private HeartPresenter heartPresenter = new HeartPresenter();
    private SettingPresenter settingPresenter = new SettingPresenter();

    private DevicePrefBiz() {
    }

    public static DevicePrefBiz getInstance() {
        if (instance == null) {
            synchronized (DevicePrefBiz.class) {
                if (instance == null) {
                    instance = new DevicePrefBiz();
                }
            }
        }
        return instance;
    }

    public void saveDevicePref(DevicePref devicePref) {
        devicePref.saveOrUpdate("uid=? and model=?", devicePref.getUid() + "", devicePref.getModel());
    }

    public DevicePref queryByUidModel(long uid, String model) {
        return (DevicePref) DataSupport.where("uid=? and model=?", uid + "", model).findFirst(DevicePref.class);
    }

    public void downloadDevicePref(final List<SettingBean> settingBeans) {
        NetFactory.getInstance().getClient(new MyCallback<PrefServerResponse>() {
            /* JADX WARNING: Removed duplicated region for block: B:14:? A[Catch:{ Exception -> 0x0058 }, RETURN, SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:9:0x004a A[Catch:{ Exception -> 0x0058 }] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onSuccess(com.iwown.device_module.common.network.data.resp.PrefServerResponse r7) {
                /*
                    r6 = this;
                    if (r7 == 0) goto L_0x0050
                    int r3 = r7.getRetCode()     // Catch:{ Exception -> 0x0058 }
                    if (r3 != 0) goto L_0x0050
                    com.iwown.device_module.common.network.data.resp.DeviceSettingRemote r3 = r7.getData()     // Catch:{ Exception -> 0x0058 }
                    if (r3 == 0) goto L_0x0037
                    com.iwown.device_module.common.network.data.resp.DeviceSettingRemote r2 = r7.getData()     // Catch:{ Exception -> 0x0058 }
                    com.iwown.device_module.common.sql.DevicePref r0 = new com.iwown.device_module.common.sql.DevicePref     // Catch:{ Exception -> 0x0058 }
                    r0.<init>()     // Catch:{ Exception -> 0x0058 }
                    long r4 = r2.getUid()     // Catch:{ Exception -> 0x0058 }
                    r0.setUid(r4)     // Catch:{ Exception -> 0x0058 }
                    java.lang.String r3 = r2.getModel()     // Catch:{ Exception -> 0x0058 }
                    r0.setModel(r3)     // Catch:{ Exception -> 0x0058 }
                    java.util.List r3 = r2.getSetting()     // Catch:{ Exception -> 0x0058 }
                    java.lang.String r3 = com.iwown.device_module.common.utils.JsonUtils.toJson(r3)     // Catch:{ Exception -> 0x0058 }
                    r0.setSetting(r3)     // Catch:{ Exception -> 0x0058 }
                    com.iwown.device_module.device_setting.configure.DevicePrefBiz r3 = com.iwown.device_module.device_setting.configure.DevicePrefBiz.getInstance()     // Catch:{ Exception -> 0x0058 }
                    r3.saveDevicePref(r0)     // Catch:{ Exception -> 0x0058 }
                L_0x0037:
                    android.app.Application r3 = com.iwown.device_module.common.utils.ContextUtil.app     // Catch:{ Exception -> 0x0058 }
                    java.lang.String r4 = "com.iwown.Firmware_Command_To_Device"
                    r5 = 0
                    com.iwown.device_module.common.utils.PrefUtil.save(r3, r4, r5)     // Catch:{ Exception -> 0x0058 }
                    com.iwown.ble_module.iwown.bean.FMdeviceInfo r3 = com.iwown.device_module.device_setting.configure.DeviceUtils.getDeviceInfo()     // Catch:{ Exception -> 0x0058 }
                    java.lang.String r3 = r3.getModel()     // Catch:{ Exception -> 0x0058 }
                    if (r3 == 0) goto L_0x004f
                    com.iwown.device_module.device_setting.configure.DevicePrefBiz r3 = com.iwown.device_module.device_setting.configure.DevicePrefBiz.this     // Catch:{ Exception -> 0x0058 }
                    r3.devicePrefToLocal()     // Catch:{ Exception -> 0x0058 }
                L_0x004f:
                    return
                L_0x0050:
                    com.iwown.device_module.device_setting.configure.DevicePrefBiz r3 = com.iwown.device_module.device_setting.configure.DevicePrefBiz.this     // Catch:{ Exception -> 0x0058 }
                    java.util.List r4 = r5     // Catch:{ Exception -> 0x0058 }
                    r3.updateModelSettingToPref(r4)     // Catch:{ Exception -> 0x0058 }
                    goto L_0x0037
                L_0x0058:
                    r1 = move-exception
                    com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
                    goto L_0x004f
                */
                throw new UnsupportedOperationException("Method not decompiled: com.iwown.device_module.device_setting.configure.DevicePrefBiz.AnonymousClass1.onSuccess(com.iwown.device_module.common.network.data.resp.PrefServerResponse):void");
            }

            public void onFail(Throwable e) {
                DevicePrefBiz.this.updateModelSettingToPref(settingBeans);
                if (DeviceUtils.getDeviceInfo().getModel() != null) {
                    DevicePrefBiz.this.devicePrefToLocal();
                }
                KLog.e(e.toString());
            }
        }).downloadDeviceRef(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
    }

    public void updateModelSettingToPref(List<SettingBean> settingBeans) {
        DevicePref devicePref = new DevicePref();
        devicePref.setUid(ContextUtil.getLUID());
        devicePref.setModel(DeviceUtils.getDeviceInfo().getModel());
        devicePref.setSetting(JsonUtils.toJson(settingBeans));
        getInstance().saveDevicePref(devicePref);
    }

    public void devicePrefToLocal() {
        DevicePref devicePref = queryByUidModel(ContextUtil.getLUID(), DeviceUtils.getDeviceInfo().getModel());
        int language = DeviceUtils.localDeviceSetting().getLanguage();
        if (devicePref != null && !TextUtils.isEmpty(devicePref.getSetting())) {
            List<SettingDefault> settingDefault = JsonUtils.getListJson(devicePref.getSetting(), SettingDefault.class);
            KLog.i("settingDefault" + JsonUtils.toJson(settingDefault));
            DeviceSettingLocal settingLocal = this.settingPresenter.localDeviceSetting();
            settingLocal.setLanguage(language);
            AutoHeartStatue status = this.heartPresenter.autoHeartStatue();
            HeartGuidanceStatue guidanceStatue = this.heartPresenter.heartGuidanceStatue();
            if (settingDefault != null && settingDefault.size() > 0) {
                for (SettingDefault setting : settingDefault) {
                    switch (setting.type) {
                        case 0:
                            settingLocal.setLed(setting.valueInt == 1);
                            break;
                        case 1:
                            settingLocal.setPalmingGesture(setting.valueInt == 1);
                            if (setting.start == -1) {
                                setting.start = 0;
                            }
                            if (setting.end == -1) {
                                setting.end = 0;
                            }
                            if (setting.start >= 24) {
                                setting.start = 0;
                            }
                            if (setting.end >= 24) {
                                setting.end = 0;
                            }
                            settingLocal.setPalmingGestureStart(setting.start);
                            settingLocal.setPalmingGestureEnd(setting.end);
                            break;
                        case 2:
                            settingLocal.setUnit(setting.valueInt == 1);
                            break;
                        case 3:
                            settingLocal.setTimeFormat(setting.valueInt == 1);
                            break;
                        case 5:
                            if (setting.start == -1) {
                                setting.start = 18;
                            }
                            if (setting.end == -1) {
                                setting.end = 6;
                            }
                            if (setting.start >= 24) {
                                setting.start = 0;
                            }
                            if (setting.end >= 24) {
                                setting.end = 0;
                            }
                            settingLocal.setBackLightStartTime(setting.start);
                            settingLocal.setBackLightEndTime(setting.end);
                            break;
                        case 6:
                            settingLocal.setScreenColor(setting.valueInt == 1);
                            break;
                        case 7:
                            int ls = setting.valueInt;
                            for (int count = LanguageUtil.LANGUAGE_SUPPORT_COUNT; count >= 0; count--) {
                                if (((ls >> count) & 1) == 1) {
                                    KLog.e("", "language字节第" + count + "为1");
                                }
                            }
                            KLog.i("-------------------------");
                            if (!DeviceUtils.getDeviceInfo().getModel().equalsIgnoreCase("I6RU")) {
                                break;
                            } else {
                                settingLocal.setLanguage(8);
                                KLog.i("---------devicebiz---------------I6RU---");
                                break;
                            }
                        case 9:
                            if (setting.valueInt != 1) {
                                if (setting.valueInt != 2) {
                                    break;
                                } else {
                                    settingLocal.setDateFormat(true);
                                    break;
                                }
                            } else {
                                settingLocal.setDateFormat(false);
                                break;
                            }
                        case 10:
                            if (setting.start == -1) {
                                setting.start = 0;
                            }
                            if (setting.end == -1) {
                                setting.end = 0;
                            }
                            if (setting.start >= 24) {
                                setting.start = 0;
                            }
                            if (setting.end >= 24) {
                                setting.end = 0;
                            }
                            settingLocal.setPalmingGestureStart(setting.start);
                            settingLocal.setPalmingGestureEnd(setting.end);
                            break;
                        case 11:
                            status.setHeart_switch(setting.valueInt == 1);
                            if (setting.start == -1) {
                                setting.start = 0;
                            }
                            if (setting.end == -1) {
                                setting.end = 0;
                            }
                            if (setting.start >= 24) {
                                setting.start = 0;
                            }
                            if (setting.end >= 24) {
                                setting.end = 0;
                            }
                            status.setHeart_startTime(setting.start);
                            status.setHeart_endTime(setting.end);
                            break;
                        case 13:
                            guidanceStatue.setHeart_guidance_switch(setting.valueInt == 1);
                            if (setting.start == -1) {
                                setting.start = 0;
                            }
                            if (setting.end == -1) {
                                setting.end = 0;
                            }
                            if (setting.start >= 24) {
                                setting.start = 0;
                            }
                            if (setting.end >= 24) {
                                setting.end = 0;
                            }
                            guidanceStatue.setMinHeart(setting.start);
                            guidanceStatue.setMaxHeart(setting.end);
                            break;
                        case 14:
                            settingLocal.setWeatherFormat(setting.valueInt == 1);
                            break;
                        case 21:
                            settingLocal.setSupportHeart(setting.valueInt == 1);
                            break;
                        case 24:
                            settingLocal.setAutoRecognitionMotion(setting.valueInt == 1);
                            break;
                        case 25:
                            if (setting.start == -1) {
                                setting.start = 0;
                            }
                            if (setting.end == -1) {
                                setting.end = 0;
                            }
                            if (setting.start >= 24) {
                                setting.start = 0;
                            }
                            if (setting.end >= 24) {
                                setting.end = 0;
                            }
                            settingLocal.setCallStart(setting.start);
                            settingLocal.setCallEnd(setting.end);
                            break;
                        case 27:
                            if (setting.valueInt != 1) {
                                if (setting.valueInt != 2) {
                                    break;
                                } else {
                                    settingLocal.setWearingManager(true);
                                    break;
                                }
                            } else {
                                settingLocal.setWearingManager(false);
                                break;
                            }
                        case 30:
                            if (setting.start == -1) {
                                setting.start = 0;
                            }
                            if (setting.end == -1) {
                                setting.end = 0;
                            }
                            if (setting.start >= 24) {
                                setting.start = 0;
                            }
                            if (setting.end >= 24) {
                                setting.end = 0;
                            }
                            settingLocal.setMsgStart(setting.start);
                            settingLocal.setMsgEnd(setting.end);
                            break;
                        case 32:
                            settingLocal.setDouble_touch_switch(setting.valueInt == 1);
                            break;
                        case 33:
                            settingLocal.setWear_recognize_switch(setting.valueInt == 1);
                            break;
                        case 35:
                            if (!TextUtils.isEmpty(setting.valueStr)) {
                                settingLocal.setWelcome_text(setting.valueStr);
                                break;
                            } else {
                                settingLocal.setWelcome_text("there");
                                break;
                            }
                        case 39:
                            settingLocal.setNoDisturb(setting.valueInt == 1);
                            settingLocal.setStartNoDisturbTime(setting.start);
                            settingLocal.setEndNoDisturbTime(setting.end);
                            break;
                        case 41:
                            settingLocal.setIs24AfSwitch(setting.valueInt == 1);
                            break;
                    }
                }
                this.settingPresenter.saveLocalDeviceSetting(settingLocal);
                this.heartPresenter.saveAutoHeartStatue(status);
                this.heartPresenter.saveHeartGuidance(guidanceStatue);
                DeviceUtils.writeCommandToDevice(SETTING_INDEXS.All_Of_Them);
            }
        }
    }

    public void updatePrefToLocal(ArrayList<SettingDefault> settingPref, DeviceSettingLocal setting, DevicePref devicePref, AutoHeartStatue autoHeartStatue, HeartGuidanceStatue heartGuidanceStatue) {
        int i;
        int i2;
        int i3;
        Iterator it = settingPref.iterator();
        while (it.hasNext()) {
            SettingDefault settingDefault = (SettingDefault) it.next();
            switch (settingDefault.type) {
                case 0:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isLed() ? 1 : 0;
                        break;
                    } else {
                        return;
                    }
                case 1:
                    if (setting != null) {
                        if (setting.isPalmingGesture()) {
                            i3 = 1;
                        } else {
                            i3 = 0;
                        }
                        settingDefault.valueInt = i3;
                        settingDefault.start = setting.getPalmingGestureStart();
                        settingDefault.end = setting.getPalmingGestureEnd();
                        break;
                    } else {
                        return;
                    }
                case 2:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isUnit() ? 1 : 2;
                        break;
                    } else {
                        return;
                    }
                case 3:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isTimeFormat() ? 1 : 2;
                        break;
                    } else {
                        return;
                    }
                case 5:
                    if (setting != null) {
                        settingDefault.start = setting.getBackLightStartTime();
                        settingDefault.end = setting.getBackLightEndTime();
                        break;
                    } else {
                        return;
                    }
                case 6:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isScreenColor() ? 1 : 2;
                        break;
                    } else {
                        return;
                    }
                case 9:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isDateFormat() ? 1 : 2;
                        break;
                    } else {
                        return;
                    }
                case 10:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isAutoRecognitionMotion() ? 1 : 0;
                        break;
                    } else {
                        return;
                    }
                case 11:
                    if (autoHeartStatue != null) {
                        settingDefault.valueInt = autoHeartStatue.isHeart_switch() ? 1 : 0;
                        break;
                    } else {
                        return;
                    }
                case 13:
                    if (heartGuidanceStatue != null) {
                        if (heartGuidanceStatue.isHeart_guidance_switch()) {
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                        settingDefault.valueInt = i2;
                        settingDefault.start = heartGuidanceStatue.getMinHeart();
                        settingDefault.end = heartGuidanceStatue.getMaxHeart();
                        break;
                    } else {
                        return;
                    }
                case 14:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isWeatherFormat() ? 1 : 2;
                        break;
                    } else {
                        return;
                    }
                case 17:
                    if (setting != null) {
                        break;
                    } else {
                        return;
                    }
                case 18:
                    if (setting != null) {
                        break;
                    } else {
                        return;
                    }
                case 21:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isSupportHeart() ? 1 : 0;
                        break;
                    } else {
                        return;
                    }
                case 24:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isAutoRecognitionMotion() ? 1 : 0;
                        break;
                    } else {
                        return;
                    }
                case 27:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isWearingManager() ? 1 : 2;
                        break;
                    } else {
                        return;
                    }
                case 35:
                    if (setting != null) {
                        if (!TextUtils.isEmpty(setting.getWelcome_text())) {
                            settingDefault.valueStr = setting.getWelcome_text();
                            break;
                        } else {
                            settingDefault.valueStr = "there";
                            break;
                        }
                    } else {
                        return;
                    }
                case 39:
                    if (setting != null) {
                        if (setting.isNoDisturb()) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        settingDefault.valueInt = i;
                        settingDefault.start = setting.getStartNoDisturbTime();
                        settingDefault.end = setting.getEndNoDisturbTime();
                        break;
                    } else {
                        return;
                    }
                case 41:
                    if (setting != null) {
                        settingDefault.valueInt = setting.isIs24AfSwitch() ? 1 : 0;
                        break;
                    } else {
                        return;
                    }
            }
            devicePref.setSetting(JsonUtils.toJson(settingPref));
            getInstance().saveDevicePref(devicePref);
        }
    }
}
