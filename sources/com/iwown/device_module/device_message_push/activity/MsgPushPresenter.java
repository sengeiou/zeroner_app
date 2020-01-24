package com.iwown.device_module.device_message_push.activity;

import android.content.pm.PackageInfo;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.common.BaseActionUtils.FirmwareAction;
import com.iwown.device_module.common.BaseActionUtils.PushAppPackName;
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
import com.iwown.device_module.device_message_push.bean.AppInfo;
import com.iwown.device_module.device_message_push.bean.MessagePushSwitchStatue;
import com.iwown.device_module.device_message_push.utils.SmsUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MsgPushPresenter implements Presenter {
    private View view;

    public MsgPushPresenter() {
    }

    public MsgPushPresenter(View view2) {
        this.view = view2;
    }

    public MessagePushSwitchStatue messageSwitchStatue() {
        try {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfo info = DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (info != null && !TextUtils.isEmpty(info.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(info.getContent(), MessagePushSwitchStatue.class);
                }
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfo info2 = Mtk_DeviceBaseInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (info2 != null && !TextUtils.isEmpty(info2.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(info2.getContent(), MessagePushSwitchStatue.class);
                }
            } else if (BluetoothOperation.isZg()) {
                ZG_BaseInfo info3 = ZGDataParsePresenter.getZGBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (info3 != null && !TextUtils.isEmpty(info3.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(info3.getContent(), MessagePushSwitchStatue.class);
                }
            } else if (BluetoothOperation.isProtoBuf()) {
                PbBaseInfo pbBaseInfo = PbDeviceInfoSqlUtil.getDeviceBaseInfoByKey(FirmwareAction.Firmware_Message_Push_Switch_Statue);
                if (pbBaseInfo != null && !TextUtils.isEmpty(pbBaseInfo.getContent())) {
                    return (MessagePushSwitchStatue) JsonUtils.fromJson(pbBaseInfo.getContent(), MessagePushSwitchStatue.class);
                }
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return new MessagePushSwitchStatue();
    }

    public void saveMessageSwitchStatue(MessagePushSwitchStatue messagePushSwitchStatue) {
        if (messagePushSwitchStatue != null) {
            if (BluetoothOperation.isIv()) {
                DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Push_Switch_Statue, JsonUtils.toJson(messagePushSwitchStatue));
            } else if (BluetoothOperation.isMtk()) {
                Mtk_DeviceBaseInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Push_Switch_Statue, JsonUtils.toJson(messagePushSwitchStatue));
            } else if (BluetoothOperation.isZg()) {
                ZGDataParsePresenter.updateZGBaseInfo(FirmwareAction.Firmware_Message_Push_Switch_Statue, JsonUtils.toJson(messagePushSwitchStatue));
            } else if (BluetoothOperation.isProtoBuf()) {
                PbDeviceInfoSqlUtil.updateDeviceBaseInfo(ContextUtil.app, FirmwareAction.Firmware_Message_Push_Switch_Statue, JsonUtils.toJson(messagePushSwitchStatue));
            }
        }
    }

    public String[] smsPackageNames() {
        return concat(concat(SmsUtil.getSmsApps(ContextUtil.app), SmsUtil.getSmsAppsTwo(ContextUtil.app)), new String[]{"com.android.mms.service"});
    }

    public List<String> mainAppPackNames() {
        List<String> mainAppPackNames = new ArrayList<>();
        if (AppConfigUtil.isHealthy()) {
            mainAppPackNames.add("com.tencent.mm");
            mainAppPackNames.add("com.tencent.mobileqq");
            mainAppPackNames.add(PushAppPackName.SINA);
        } else {
            mainAppPackNames.add("com.facebook.orca");
            mainAppPackNames.add("com.twitter.android");
            mainAppPackNames.add("com.whatsapp");
            mainAppPackNames.add("com.skype.rover");
            mainAppPackNames.add("com.skype.raider");
            mainAppPackNames.add("jp.naver.line.android");
            mainAppPackNames.add("com.kakao.talk");
            mainAppPackNames.add("com.google.android.gm");
        }
        return mainAppPackNames;
    }

    public List<AppInfo> getMainAppList(List<String> mainAppPackNames) {
        List<AppInfo> mAppInfos = new ArrayList<>();
        List<PackageInfo> packages = ContextUtil.app.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = (PackageInfo) packages.get(i);
            Iterator it = mainAppPackNames.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String packName = (String) it.next();
                if (packageInfo.packageName.equalsIgnoreCase(packName)) {
                    AppInfo tmpInfo = new AppInfo();
                    tmpInfo.appName = packageInfo.applicationInfo.loadLabel(ContextUtil.app.getPackageManager()).toString();
                    tmpInfo.packageName = packageInfo.packageName;
                    tmpInfo.versionName = packageInfo.versionName;
                    tmpInfo.versionCode = packageInfo.versionCode;
                    tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(ContextUtil.app.getPackageManager());
                    KLog.d("找到主推送软件-->" + tmpInfo.packageName);
                    tmpInfo.setCheck(checkTheAppIfChecked(packName));
                    mAppInfos.add(tmpInfo);
                    break;
                }
            }
        }
        return mAppInfos;
    }

    static String[] concat(String[] a, String[] b) {
        String[] c = new String[(a.length + b.length)];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    private boolean checkTheAppIfChecked(String name) {
        if (name.equalsIgnoreCase("com.facebook.orca")) {
            return messageSwitchStatue().isMessenger();
        }
        if (name.equalsIgnoreCase("com.twitter.android")) {
            return messageSwitchStatue().isTwitter();
        }
        if (name.equalsIgnoreCase("com.whatsapp")) {
            return messageSwitchStatue().isWhatsapp();
        }
        if (name.equalsIgnoreCase("com.skype.rover")) {
            return messageSwitchStatue().isSkeype1();
        }
        if (name.equalsIgnoreCase("com.skype.raider")) {
            return messageSwitchStatue().isSkeype2();
        }
        if (name.equalsIgnoreCase("jp.naver.line.android")) {
            return messageSwitchStatue().isLine();
        }
        if (name.equalsIgnoreCase("com.kakao.talk")) {
            return messageSwitchStatue().isKakaoTalk();
        }
        if (name.equalsIgnoreCase("com.google.android.gm")) {
            return messageSwitchStatue().isGmail();
        }
        if (name.equalsIgnoreCase("com.tencent.mm")) {
            return messageSwitchStatue().isWhchat();
        }
        if (name.equalsIgnoreCase("com.tencent.mobileqq")) {
            return messageSwitchStatue().isQq();
        }
        if (name.equalsIgnoreCase(PushAppPackName.SINA)) {
            return messageSwitchStatue().isSina();
        }
        return false;
    }

    public void switchMainAppState(String name, boolean isOn) {
        KLog.d(name + " no2855消息推送是否开启：" + isOn);
        if (name.equalsIgnoreCase("com.facebook.orca")) {
            MessagePushSwitchStatue statue = messageSwitchStatue();
            statue.setMessenger(isOn);
            saveMessageSwitchStatue(statue);
        }
        if (name.equalsIgnoreCase("com.twitter.android")) {
            MessagePushSwitchStatue statue2 = messageSwitchStatue();
            statue2.setTwitter(isOn);
            saveMessageSwitchStatue(statue2);
        }
        if (name.equalsIgnoreCase("com.whatsapp")) {
            MessagePushSwitchStatue statue3 = messageSwitchStatue();
            statue3.setWhatsapp(isOn);
            saveMessageSwitchStatue(statue3);
        }
        if (name.equalsIgnoreCase("com.skype.rover")) {
            MessagePushSwitchStatue statue4 = messageSwitchStatue();
            statue4.setSkeype1(isOn);
            saveMessageSwitchStatue(statue4);
        }
        if (name.equalsIgnoreCase("com.skype.raider")) {
            MessagePushSwitchStatue statue5 = messageSwitchStatue();
            statue5.setSkeype2(isOn);
            saveMessageSwitchStatue(statue5);
        }
        if (name.equalsIgnoreCase("jp.naver.line.android")) {
            MessagePushSwitchStatue statue6 = messageSwitchStatue();
            statue6.setLine(isOn);
            saveMessageSwitchStatue(statue6);
        }
        if (name.equalsIgnoreCase("com.kakao.talk")) {
            MessagePushSwitchStatue statue7 = messageSwitchStatue();
            statue7.setKakaoTalk(isOn);
            saveMessageSwitchStatue(statue7);
        }
        if (name.equalsIgnoreCase("com.google.android.gm")) {
            MessagePushSwitchStatue statue8 = messageSwitchStatue();
            statue8.setGmail(isOn);
            saveMessageSwitchStatue(statue8);
        }
        if (name.equalsIgnoreCase("com.tencent.mm")) {
            MessagePushSwitchStatue statue9 = messageSwitchStatue();
            statue9.setWhchat(isOn);
            saveMessageSwitchStatue(statue9);
        }
        if (name.equalsIgnoreCase("com.tencent.mobileqq")) {
            MessagePushSwitchStatue statue10 = messageSwitchStatue();
            statue10.setQq(isOn);
            saveMessageSwitchStatue(statue10);
        }
        if (name.equalsIgnoreCase(PushAppPackName.SINA)) {
            MessagePushSwitchStatue statue11 = messageSwitchStatue();
            statue11.setSina(isOn);
            saveMessageSwitchStatue(statue11);
        }
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }
}
