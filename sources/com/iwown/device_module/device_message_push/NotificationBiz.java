package com.iwown.device_module.device_message_push;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.ble_module.iwown.task.MessageTask;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.task.MtkMessageTask;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.zg_ble.BleHandler;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.ble_module.zg_ble.task.BleMessage;
import com.iwown.ble_module.zg_ble.task.TaskHandler;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.PushAppPackName;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.sql.TB_PUSH_SOFT;
import com.iwown.device_module.common.sql.TB_PushSoft;
import com.iwown.device_module.common.utils.BLEInitTask;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_message_push.activity.MsgPushPresenter;
import com.iwown.device_module.device_message_push.biz.V3_sport_pushsoft_biz;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.litepal.crud.DataSupport;

public class NotificationBiz {
    public static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
    public static final int SEND_TYPE_1 = 10;
    public static final int SEND_TYPE_2 = 20;
    public static final String SKYPE_PACKAGE_NAME = "com.skype.rover";
    public static final int TYPE_FACEBOOK = 11;
    public static final int TYPE_GMAIL = 17;
    public static final int TYPE_KAKAOTALK = 16;
    public static final int TYPE_LINE = 15;
    public static final int TYPE_OTHER_SOFTWARE = 3;
    public static final int TYPE_QQ = 1;
    public static final int TYPE_SINA = 21;
    public static final int TYPE_SKYPE = 14;
    public static final int TYPE_SMS = 4;
    public static final int TYPE_TWITTER = 12;
    public static final int TYPE_WECHAT = 2;
    public static final int TYPE_WHATSAPP = 13;
    public static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    private static NotificationBiz instance;
    public static List<StatusBarNotification[]> mCurrentNotifications = new ArrayList();
    public static int mCurrentNotificationsCounts = 0;
    SendListener listener;
    Context mContext;
    private String mTempText;
    private MsgPushPresenter presenter;
    private V3_sport_pushsoft_biz v3_sport_pushsoft_biz = new V3_sport_pushsoft_biz();

    public interface SendListener {
        void send2DeviceListener(int i);
    }

    public static NotificationBiz getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationBiz(context);
        }
        return instance;
    }

    public NotificationBiz(Context context) {
        this.mContext = context;
        this.presenter = new MsgPushPresenter();
    }

    @SuppressLint({"NewApi"})
    public void storeNotification(StatusBarNotification sbn) {
        String message;
        String message2 = null;
        String appName = null;
        try {
            String packageName = sbn.getPackageName();
            int msgid = sbn.getId();
            if (!packageName.equalsIgnoreCase("com.skype.raider") && !packageName.equalsIgnoreCase("com.skype.rover")) {
                appName = packageName;
                try {
                    appName = this.mContext.getPackageManager().getApplicationLabel(this.mContext.getPackageManager().getApplicationInfo(packageName, 0)).toString();
                } catch (NameNotFoundException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                if (VERSION.SDK_INT >= 19) {
                    KLog.e("Version" + VERSION.SDK_INT);
                    if (sbn.getNotification().extras != null) {
                        message2 = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TITLE);
                        if (TextUtils.isEmpty(message2)) {
                            message2 = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TEXT);
                        } else if (message2.equals(appName)) {
                            String otherMsg = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TEXT);
                            if (!TextUtils.isEmpty(otherMsg)) {
                                message2 = otherMsg;
                            }
                        }
                    }
                }
                if (TextUtils.isEmpty(message2)) {
                    if (sbn.getNotification() != null && sbn.getNotification().tickerText != null) {
                        message2 = sbn.getNotification().tickerText.toString();
                    } else {
                        return;
                    }
                } else if (message2.equals(appName)) {
                    String otherMsg2 = sbn.getNotification().tickerText.toString();
                    if (!TextUtils.isEmpty(otherMsg2)) {
                        message2 = otherMsg2;
                    }
                }
                String text = sbn.getNotification().extras.getCharSequence(NotificationCompat.EXTRA_TEXT) + "";
                if (packageName.equalsIgnoreCase("com.whatsapp") || packageName.equalsIgnoreCase("com.google.android.gm")) {
                    if (VERSION.SDK_INT < 19) {
                        KLog.e("小于19");
                        if (sbn.getNotification().tickerText != null) {
                            message = sbn.getNotification().tickerText.toString();
                        }
                    } else if (sbn.getNotification().extras != null) {
                        if (text != null && packageName.equalsIgnoreCase("com.google.android.gm")) {
                            message = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TITLE) + ":" + text;
                        }
                        if (packageName.equals("com.whatsapp")) {
                            CharSequence[] charSequences = sbn.getNotification().extras.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES);
                            if (charSequences != null && charSequences.length > 0) {
                                message = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TITLE) + ":" + charSequences[charSequences.length - 1];
                                KLog.e("NotificationBiz", "whatsapp的内容: " + message);
                            } else if (!text.equals(this.mTempText)) {
                                message = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TITLE) + ":" + text;
                                KLog.e("NotificationBiz", "!text.equals(mTempText)" + text + "/" + this.mTempText);
                                this.mTempText = text;
                                KLog.e("NotificationBiz", "whatsapp--charSequences[] 为空");
                            } else {
                                return;
                            }
                            if (message.length() > 9 && message.substring(0, 9).indexOf("WhatsApp:") > -1) {
                                message = message.substring(9);
                            }
                        }
                    }
                } else if (sbn.getNotification().tickerText != null) {
                    message = sbn.getNotification().tickerText.toString();
                }
            } else if (sbn.getNotification().tickerText != null) {
                message = sbn.getNotification().tickerText.toString();
            } else {
                String title = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TITLE);
                StringBuffer skpStr = new StringBuffer();
                skpStr.append(title).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(sbn.getNotification().extras.getString(NotificationCompat.EXTRA_TEXT));
                message = skpStr.toString();
            }
            if (sbn.getNotification().extras != null && packageName.equals("com.whatsapp")) {
                String summaryText = sbn.getNotification().extras.getString(NotificationCompat.EXTRA_SUMMARY_TEXT);
                if (!TextUtils.isEmpty(summaryText) && message.contains(summaryText)) {
                    KLog.e("summaryText", summaryText);
                    return;
                }
            }
            if (message != null) {
                message = message.replace("\n", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).trim();
            }
            switchMessageType(message, packageName, msgid, 20);
            KLog.e("NotificationBiz", "消息方式一:message: " + message + "<appName: " + appName + "<apkPackage: " + packageName);
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            KLog.e("NotificationBiz", "storeNotification err" + e2.toString());
        }
    }

    public void updateCurrentNotifications(StatusBarNotification[] activeNos) {
        try {
            if (mCurrentNotifications.size() == 0) {
                mCurrentNotifications.add(null);
            }
            mCurrentNotifications.set(0, activeNos);
            mCurrentNotificationsCounts = activeNos.length;
            StatusBarNotification[] notifications = (StatusBarNotification[]) mCurrentNotifications.get(0);
            if (notifications.length != 0) {
                int i = 0;
                while (i < notifications.length) {
                    if (notifications[i].getNotification().tickerText != null) {
                        switchMessageType(notifications[i].getNotification().tickerText.toString().replace("\n", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).trim(), notifications[i].getPackageName(), notifications[i].getId(), 10);
                        i++;
                    } else if (this.listener != null) {
                        this.listener.send2DeviceListener(20);
                        return;
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            if (this.listener != null) {
                this.listener.send2DeviceListener(20);
            }
            ThrowableExtension.printStackTrace(e);
        }
    }

    private synchronized void switchMessageType(String message, String packageName, int msgId, int sendType) {
        if (!DeviceUtils.localDeviceSetting().isMsgEnable()) {
            KLog.i("--------消息总开关没有开-------------");
        } else if (this.v3_sport_pushsoft_biz.queryMessagePush(ContextUtil.getUID() + "", message, msgId, packageName) <= 0) {
            KLog.e("NotificationBiz", "消息数据库不存在此消息： " + message + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + packageName + " sendType:" + sendType);
            if (AppConfigUtil.isHealthy()) {
                sendHealthyMsg(message, packageName, sendType);
            } else {
                sendOverseaMsg(message, packageName, sendType);
            }
        } else {
            KLog.e("NotificationBiz", "数据库已存在此消息" + message);
        }
    }

    private void sendHealthyMsg(String message, String packageName, int sendType) {
        boolean qqFlag;
        boolean wechatFlag;
        String[] smsPackageNames;
        if (!packageName.equalsIgnoreCase("com.tencent.mobileqq") || (!findPackageInTB_PushSoft(packageName) && !this.presenter.messageSwitchStatue().isQq())) {
            qqFlag = false;
        } else {
            qqFlag = true;
        }
        if (!packageName.equalsIgnoreCase("com.tencent.mm") || (!findPackageInTB_PushSoft(packageName) && !this.presenter.messageSwitchStatue().isWhchat())) {
            wechatFlag = false;
        } else {
            wechatFlag = true;
        }
        if (qqFlag) {
            sendMsgToDevice(1, checkDataLength(message), sendType);
            KLog.i("NotificationBiz", "【QQ消息推送成功】");
        } else if (wechatFlag) {
            sendMsgToDevice(2, checkDataLength(message), sendType);
            KLog.i("【微信消息推送成功】");
        } else if (!packageName.equalsIgnoreCase(PushAppPackName.SINA) || !this.presenter.messageSwitchStatue().isSina()) {
            if (DataSupport.where("packagename=?", packageName).find(TB_PushSoft.class).size() > 0) {
                KLog.e("NotificationBiz其他软件: " + packageName);
                if (!DeviceUtils.localDeviceSetting().isMsgEnable()) {
                    KLog.i("--------消息总开关没有开-------------");
                } else {
                    sendMsgToDevice(3, checkDataLength(message), sendType);
                }
            } else if (this.presenter.messageSwitchStatue().isSms()) {
                for (String name : this.presenter.smsPackageNames()) {
                    if (packageName.equalsIgnoreCase(name) && (name.contains("sms") || name.contains("mms") || name.contains("conversations"))) {
                        if (message.startsWith("+86")) {
                            message = message.substring(3, message.length());
                        }
                        sendMsgToDevice(4, checkDataLength(message), sendType);
                        KLog.i("【短信消息推送成功】");
                    }
                }
            } else {
                KLog.e("NotificationBiz其他软件:全部匹配不到？？？ " + packageName);
            }
        } else {
            sendMsgToDevice(21, checkDataLength(message), sendType);
            KLog.i("【微博消息推送成功】");
        }
    }

    private void sendOverseaMsg(String message, String packageName, int sendType) {
        boolean qqFlag;
        boolean wechatFlag;
        boolean isPush;
        String[] smsPackageNames;
        if (!packageName.equalsIgnoreCase("com.tencent.mobileqq") || !findPackageInTB_PushSoft(packageName)) {
            qqFlag = false;
        } else {
            qqFlag = true;
        }
        if (!packageName.equalsIgnoreCase("com.tencent.mm") || !findPackageInTB_PushSoft(packageName)) {
            wechatFlag = false;
        } else {
            wechatFlag = true;
        }
        if (DataSupport.where("packagename=?", packageName).find(TB_PushSoft.class).size() > 0) {
            isPush = true;
        } else {
            isPush = false;
        }
        if (qqFlag) {
            sendMsgToDevice(1, checkDataLength(message), sendType);
            KLog.i("NotificationBiz", "【QQ消息推送成功】");
        } else if (wechatFlag) {
            sendMsgToDevice(2, checkDataLength(message), sendType);
            KLog.i("【微信消息推送成功】");
        } else if (packageName.equalsIgnoreCase("com.facebook.orca") && isPush) {
            sendMsgToDevice(11, checkDataLength(message), sendType);
            KLog.i("【Facebook消息推送成功】");
        } else if (packageName.equalsIgnoreCase("com.twitter.android") && isPush) {
            sendMsgToDevice(12, checkDataLength(message), sendType);
            KLog.i("【Twitter消息推送成功】");
        } else if (packageName.equalsIgnoreCase("com.whatsapp") && isPush) {
            sendMsgToDevice(13, checkDataLength(message), sendType);
            KLog.i("【whatsapp消息推送成功】");
        } else if (packageName.equalsIgnoreCase("com.skype.rover") && isPush) {
            sendMsgToDevice(14, checkDataLength(message), sendType);
            KLog.i("【skype1消息推送成功】");
        } else if (packageName.equalsIgnoreCase("com.skype.raider") && isPush) {
            sendMsgToDevice(14, checkDataLength(message), sendType);
            KLog.i("【skype2消息推送成功】");
        } else if (packageName.equalsIgnoreCase("jp.naver.line.android") && isPush) {
            sendMsgToDevice(15, checkDataLength(message), sendType);
            KLog.i("【Line消息推送成功】");
        } else if (packageName.equalsIgnoreCase("com.kakao.talk") && isPush) {
            sendMsgToDevice(16, checkDataLength(message), sendType);
            KLog.i("【KakaoTalk消息推送成功】");
        } else if (packageName.equalsIgnoreCase("com.google.android.gm") && isPush) {
            sendMsgToDevice(17, checkDataLength(message), sendType);
            KLog.i("【Gmail消息推送成功】");
        } else if (packageName.equalsIgnoreCase(PushAppPackName.SINA) && isPush) {
            sendMsgToDevice(21, checkDataLength(message), sendType);
            KLog.i("【微博消息推送成功】");
        } else if (isPush) {
            KLog.e("NotificationBiz其他软件: " + packageName);
            if (!DeviceUtils.localDeviceSetting().isMsgEnable()) {
                KLog.i("--------消息总开关没有开-------------");
            } else {
                sendMsgToDevice(3, checkDataLength(message), sendType);
            }
        } else if (this.presenter.messageSwitchStatue().isSms()) {
            for (String name : this.presenter.smsPackageNames()) {
                if (packageName.equalsIgnoreCase(name) && (name.contains("sms") || name.contains("mms") || name.contains("conversations"))) {
                    if (message.startsWith("+86")) {
                        message = message.substring(3, message.length());
                    }
                    sendMsgToDevice(4, checkDataLength(message), sendType);
                    KLog.i("【短信消息推送成功】");
                }
            }
        } else {
            KLog.e("NotificationBiz其他软件:全部匹配不到？？？ " + packageName);
        }
    }

    public boolean findPackageInTB_PushSoft(String packagename) {
        return DataSupport.where("packagename=?", packagename).find(TB_PushSoft.class).size() > 0;
    }

    private String checkDataLength(String msg) {
        int index = 0;
        for (int j = 0; j < msg.length(); j++) {
            if (msg.charAt(j) < '@' || (msg.charAt(j) < 128 && msg.charAt(j) > '`')) {
                index++;
            } else {
                index += 3;
            }
            if (index > 192) {
                return msg.substring(0, j);
            }
        }
        return msg;
    }

    public int saveAndDelMessage(String message, int msgId, String packageName) {
        TB_PUSH_SOFT entity = new TB_PUSH_SOFT();
        DateUtil date = new DateUtil(new Date());
        entity.setUid(ContextUtil.getUID());
        entity.setYear(date.getYear());
        entity.setMonth(date.getMonth());
        entity.setDay(date.getDay());
        entity.setHour(date.getHour());
        entity.setMessage(message.trim());
        entity.setAppName(packageName + ".com");
        entity.setMsgid(msgId);
        entity.save();
        return this.v3_sport_pushsoft_biz.deleteDatabyDate(ContextUtil.getUID() + "", date.getHour());
    }

    public void sendMsgToDevice(int type, String msg, int sendType) {
        String str = "";
        switch (type) {
            case 1:
                str = "QQ|";
                break;
            case 2:
                str = ContextUtil.app.getString(R.string.wechat) + "|";
                break;
            case 4:
                str = "SMS|";
                break;
            case 11:
                str = "Facebook|";
                break;
            case 12:
                str = "Twitter|";
                break;
            case 13:
                str = "WhatsApp|";
                break;
            case 14:
                str = "Skype|";
                break;
            case 15:
                str = "Line|";
                break;
            case 16:
                str = "KakaoTalk|";
                break;
            case 17:
                str = "G-mail|";
                break;
            case 21:
                str = "微博|";
                break;
        }
        if (this.listener != null && sendType == 10) {
            this.listener.send2DeviceListener(10);
        }
        KLog.e("NotificationBiz写入手环内容: " + msg);
        addMsg(2, str + checkDataLength(msg));
    }

    public static void addMsg(int type, String msg) {
        addMsg(type, msg, System.currentTimeMillis() + 1800000, true);
    }

    public static void addMsg(int type, String msg, long time, boolean isMessage) {
        if (!BluetoothOperation.isIBleNotNull()) {
            final int i = type;
            final String str = msg;
            final long j = time;
            final boolean z = isMessage;
            BLEInitTask.postRunnable(new Runnable() {
                public void run() {
                    NotificationBiz.postMessage(i, str, j, z);
                }
            });
            return;
        }
        postMessage(type, msg, time, isMessage);
    }

    /* access modifiers changed from: private */
    public static void postMessage(int type, String msg, long time, boolean isMessage) {
        String title;
        String msgContent;
        String title2;
        String msgContent2;
        String title3;
        String msgContent3;
        String title4;
        String msgContent4;
        KLog.e("postMessage  " + BluetoothOperation.isConnected());
        if (BluetoothOperation.isConnected()) {
            KLog.e("==========postMessage===========");
            if (isMessage) {
                BluetoothOperation.postHeartData(0);
            }
            KLog.d("UserConfig.getInstance().isScreenOn()&&isScreenOn()&&!isScreenLock())");
            if (BluetoothOperation.isIv()) {
                MessageTask.getInstance(ContextUtil.app).addMessage(type, msg, time);
            } else if (BluetoothOperation.isMtk()) {
                MtkMessageTask.getInstance(ContextUtil.app).addMessage(type, msg, time);
            } else if (BluetoothOperation.isZg()) {
                if (type == 1) {
                    BleDataOrderHandler.getInstance().callNotification("", msg, false);
                } else {
                    BleDataOrderHandler.getInstance().messageNotification("", msg, false);
                }
            } else if (!BluetoothOperation.isProtoBuf()) {
            } else {
                if (type == 1) {
                    String[] msgs = new String[2];
                    try {
                        msgs = msg.split("\\|");
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                    if (msgs == null || msgs.length < 2) {
                        title4 = "Call";
                        msgContent4 = msg;
                    } else {
                        title4 = msgs[0];
                        msgContent4 = msgs[1];
                    }
                    BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationNotifyByCall(999999, 0, true, true, true, title4, msgContent4));
                } else if (type == 2) {
                    KLog.i("======9999========" + msg);
                    String[] msgs2 = new String[2];
                    try {
                        msgs2 = msg.split("\\|");
                    } catch (Exception e2) {
                        ThrowableExtension.printStackTrace(e2);
                    }
                    if (msgs2 == null || msgs2.length < 2) {
                        title3 = "unknown";
                        msgContent3 = msg;
                    } else {
                        title3 = msgs2[0];
                        msgContent3 = msgs2[1];
                    }
                    KLog.i("==============" + title3 + "================" + msgContent3);
                    BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationNotifyBySMS((int) new DateUtil().getUnixTimestamp(), title3, msgContent3));
                }
            }
        } else {
            KLog.e("==========postMessage2 =========== " + PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name));
            if (TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name)) || TextUtils.isEmpty(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address))) {
                KLog.e("bluetooth address is null");
            } else if (ContextUtil.isFirmwareUp) {
                KLog.i("==========正在固件升级===========");
                L.file("==========正在固件升级===========", 4);
            } else {
                KLog.e("==========收到消息建立连接===========");
                if (BluetoothOperation.isEnabledBluetooth() && !BluetoothOperation.isConnected()) {
                    BluetoothOperation.connect(ContextUtil.app, new WristBand(PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name_Current_Device), PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Address_Current_Device)));
                    if (BluetoothOperation.isIv()) {
                        ZeronerBackgroundThreadManager.getInstance().needWait();
                    } else if (BluetoothOperation.isMtk()) {
                        MtkBackgroundThreadManager.getInstance().needWait();
                    } else if (BluetoothOperation.isZg()) {
                        TaskHandler.getInstance().getTaskConsumer().needWait();
                    } else if (BluetoothOperation.isProtoBuf()) {
                        BackgroundThreadManager.getInstance().needWait();
                    }
                }
                if (isMessage) {
                    BluetoothOperation.postHeartData(0);
                }
                if (BluetoothOperation.isIv()) {
                    MessageTask.getInstance(ContextUtil.app).addMessage(type, msg, time);
                } else if (BluetoothOperation.isMtk()) {
                    MtkMessageTask.getInstance(ContextUtil.app).addMessage(type, msg, time);
                } else if (BluetoothOperation.isZg()) {
                    BleHandler.getInstance().addMessageFirstImmediately(new BleMessage(BleDataOrderHandler.getInstance().setConnectMode()));
                    if (type == 1) {
                        BleDataOrderHandler.getInstance().callNotification("", msg, true);
                    } else {
                        BleDataOrderHandler.getInstance().messageNotification("", msg, true);
                    }
                } else if (!BluetoothOperation.isProtoBuf()) {
                } else {
                    if (type == 1) {
                        String[] msgs3 = new String[2];
                        try {
                            msgs3 = msg.split("\\|");
                        } catch (Exception e3) {
                            ThrowableExtension.printStackTrace(e3);
                        }
                        if (msgs3 == null || msgs3.length < 2) {
                            title2 = "Call";
                            msgContent2 = msg;
                        } else {
                            title2 = msgs3[0];
                            msgContent2 = msgs3[1];
                        }
                        BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationNotifyByCall(999999, 0, true, true, true, title2, msgContent2));
                    } else if (type == 2) {
                        String[] msgs4 = new String[2];
                        try {
                            msgs4 = msg.split("\\|");
                        } catch (Exception e4) {
                            ThrowableExtension.printStackTrace(e4);
                        }
                        if (msgs4 == null || msgs4.length < 2) {
                            title = "unknown";
                            msgContent = msg;
                        } else {
                            title = msgs4[0];
                            msgContent = msgs4[1];
                        }
                        BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationNotifyBySMS((int) new DateUtil().getUnixTimestamp(), title, msgContent));
                    }
                }
            }
        }
    }

    public static boolean isScreenOn() {
        return ((PowerManager) ContextUtil.app.getSystemService("power")).isScreenOn();
    }

    public static boolean isScreenLock() {
        return ((KeyguardManager) ContextUtil.app.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public SendListener getListener() {
        return this.listener;
    }

    public void setListener(SendListener listener2) {
        this.listener = listener2;
    }
}
