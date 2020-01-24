package com.iwown.device_module.device_message_push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.ITelephony;
import com.android.internal.telephony.ITelephony.Stub;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.cmd.ZeronerSendBluetoothCmdImpl;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.ble_module.zg_ble.data.BleDataOrderHandler;
import com.iwown.data_link.consts.UserConst;
import com.iwown.device_module.common.BaseActionUtils.KeyCodeAction;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;

public class CallReceiver extends BroadcastReceiver {
    public static final boolean D = true;
    private static String incomingNumber = null;
    private static ITelephony mITelephony;
    private static TelephonyManager manager;
    private final String TAG = getClass().getSimpleName();
    private AudioManager aManager;
    private CallMessage callMessage = new CallMessage();
    private WeakReference<Context> mApp;
    private int ringModel;
    private TelephonyManager tm;

    public static class Contact {
        private String displayName;
        private String number;

        public Contact(String phoneNumber) {
            this.number = phoneNumber;
            this.displayName = phoneNumber;
        }

        public String getNumber() {
            return this.number;
        }

        public void setNumber(String number2) {
            this.number = number2;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public void setDisplayName(String displayName2) {
            this.displayName = displayName2;
        }
    }

    private class MyPhoneListener extends PhoneStateListener {
        private MyPhoneListener() {
        }

        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            KLog.i("no2855=============" + incomingNumber + " == " + state);
            if (state == 1) {
                CallReceiver.this.inComingNumber(incomingNumber);
            }
        }
    }

    public void onReceive(Context context, Intent intent) {
        KLog.e(this.TAG, "+++ ON RECEIVE +++" + intent.getAction().toString());
        L.file("电话CallReceiver：" + intent.getAction().toString(), 4);
        this.mApp = new WeakReference<>(context);
        this.tm = (TelephonyManager) ((Context) this.mApp.get()).getSystemService(UserConst.PHONE);
        this.aManager = (AudioManager) ((Context) this.mApp.get()).getSystemService("audio");
        if (VERSION.SDK_INT > 27) {
            this.tm.listen(new MyPhoneListener(), 32);
        }
        start();
        L.file("电话状态：" + this.tm.getCallState(), 4);
        KLog.e("=======================电话状态: " + this.tm.getCallState());
        switch (this.tm.getCallState()) {
            case 0:
                KLog.i("no2855TelephonyManager.CALL_STATE_IDLE");
                if (DeviceUtils.localDeviceSetting().isCallEnable()) {
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isIv()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationNotifyByCall(999999, 1, true, true, true, "", ""));
                                    break;
                                }
                            } else {
                                MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setShakeMode(0, 0, 0, null));
                                break;
                            }
                        } else {
                            byte[] datas = ZeronerSendBluetoothCmdImpl.getInstance().setShakeMode(0, 0, 0, null);
                            DataBean dataBean7 = new DataBean();
                            dataBean7.addData(datas);
                            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean7);
                            break;
                        }
                    } else {
                        BleDataOrderHandler.getInstance().closeCallPhone(context);
                        return;
                    }
                }
                break;
            case 1:
                incomingNumber = intent.getStringExtra("incoming_number");
                KLog.i("no2855TelephonyManager.CALL_STATE_RINGING " + incomingNumber);
                Contact contact = getContact(context, incomingNumber);
                if (!"".equals(contact.getDisplayName()) && contact != null && contact.getDisplayName() != null && DeviceUtils.localDeviceSetting().isCallEnable()) {
                    addMessage(0, contact.getDisplayName().trim());
                    break;
                }
            case 2:
                KLog.i("no2855TelephonyManager.CALL_STATE_OFFHOOK");
                if (DeviceUtils.localDeviceSetting().isCallEnable()) {
                    if (!BluetoothOperation.isZg()) {
                        if (!BluetoothOperation.isIv()) {
                            if (!BluetoothOperation.isMtk()) {
                                if (BluetoothOperation.isProtoBuf()) {
                                    BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setMsgNotificationNotifyByCall(999999, 1, true, true, true, "", ""));
                                    break;
                                }
                            } else {
                                MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setShakeMode(0, 0, 0, null));
                                break;
                            }
                        } else {
                            byte[] datas2 = ZeronerSendBluetoothCmdImpl.getInstance().setShakeMode(0, 0, 0, null);
                            DataBean dataBean72 = new DataBean();
                            dataBean72.addData(datas2);
                            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean72);
                            break;
                        }
                    } else {
                        BleDataOrderHandler.getInstance().closeCallPhone(context);
                        return;
                    }
                }
                break;
        }
        KLog.e("=====================================" + intent.getAction());
        if (intent.getAction().equals(KeyCodeAction.Action_Phone_Statue_Out)) {
            try {
                boolean isok = endCall(context);
                KLog.e("=================挂断电话结果？？" + isok);
                L.file("=================挂断电话结果？？" + isok, 4);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        } else if (!intent.getAction().equals(KeyCodeAction.Action_Phone_Ring) && intent.getAction().equals(KeyCodeAction.Action_Phone_Ring)) {
            answerRing();
        }
    }

    private synchronized void addMessage(int type, String msg) {
        synchronized (this.callMessage) {
            KLog.e("=====no2855电话什么 ");
            if (System.currentTimeMillis() - this.callMessage.getLast_receive_time() > 4000 || !msg.equals(this.callMessage.getLast_msg())) {
                this.callMessage.setLast_receive_time(System.currentTimeMillis());
                this.callMessage.setLast_msg(msg);
                NotificationBiz.addMsg(1, msg);
                L.file(type + "电话消息入队列" + msg + "==", 4);
                KLog.e(type + "电话消息入队列" + msg + "=====");
            } else {
                this.callMessage.setLast_receive_time(System.currentTimeMillis());
                this.callMessage.setLast_msg(msg);
                KLog.e("no2855-->遇到短时间内重复的电话广播, 不推送");
                L.file("遇到短时间内重复的电话广播, 不推送：" + msg, 4);
            }
        }
    }

    /* access modifiers changed from: private */
    public void inComingNumber(String incomingNumber2) {
        KLog.i("TelephonyManager.CALL_STATE_RINGING");
        Contact contact = getContact(ContextUtil.app, incomingNumber2);
        if (!"".equals(contact.getDisplayName()) && contact != null && contact.getDisplayName() != null && DeviceUtils.localDeviceSetting().isCallEnable()) {
            addMessage(1, contact.getDisplayName().trim());
        }
    }

    private void offHook() {
        KLog.i("TelephonyManager.CALL_STATE_OFFHOOK");
        if (!DeviceUtils.localDeviceSetting().isCallEnable()) {
            return;
        }
        if (BluetoothOperation.isZg()) {
            BleDataOrderHandler.getInstance().closeCallPhone(ContextUtil.app);
        } else if (BluetoothOperation.isIv()) {
            byte[] datas = ZeronerSendBluetoothCmdImpl.getInstance().setShakeMode(0, 0, 0, null);
            DataBean dataBean7 = new DataBean();
            dataBean7.addData(datas);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean7);
        } else if (BluetoothOperation.isMtk()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setShakeMode(0, 0, 0, null));
        }
    }

    private void idle() {
        KLog.i("TelephonyManager.CALL_STATE_IDLE");
        if (!DeviceUtils.localDeviceSetting().isCallEnable()) {
            return;
        }
        if (BluetoothOperation.isZg()) {
            BleDataOrderHandler.getInstance().closeCallPhone(ContextUtil.app);
        } else if (BluetoothOperation.isIv()) {
            byte[] datas = ZeronerSendBluetoothCmdImpl.getInstance().setShakeMode(0, 0, 0, null);
            DataBean dataBean7 = new DataBean();
            dataBean7.addData(datas);
            ZeronerBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, dataBean7);
        } else if (BluetoothOperation.isMtk()) {
            MtkBackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, MtkCmdAssembler.getInstance().setShakeMode(0, 0, 0, null));
        }
    }

    /* access modifiers changed from: 0000 */
    public void start() {
        if (manager == null) {
            manager = (TelephonyManager) ((Context) this.mApp.get()).getSystemService(UserConst.PHONE);
        }
    }

    /* access modifiers changed from: 0000 */
    public void toEnd() {
        KLog.i("info=====================挂断电话");
        try {
            Method getITelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", null);
            getITelephonyMethod.setAccessible(true);
            mITelephony = (ITelephony) getITelephonyMethod.invoke(manager, null);
            mITelephony.endCall();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public static boolean endCall(Context context) {
        boolean callSuccess = false;
        ITelephony telephonyService = getTelephonyService(context);
        if (telephonyService != null) {
            try {
                callSuccess = telephonyService.endCall();
            } catch (RemoteException e) {
                ThrowableExtension.printStackTrace(e);
                killCall(context);
            } catch (Exception e2) {
                ThrowableExtension.printStackTrace(e2);
                killCall(context);
            }
        }
        if (callSuccess) {
            return callSuccess;
        }
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            public void run() {
                CallReceiver.disconnectCall();
            }
        });
        return true;
    }

    private static ITelephony getTelephonyService(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
        try {
            Method method = Class.forName(telephonyManager.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
            method.setAccessible(true);
            return (ITelephony) method.invoke(telephonyManager, new Object[0]);
        } catch (ClassNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        } catch (NoSuchMethodException e2) {
            ThrowableExtension.printStackTrace(e2);
        } catch (IllegalArgumentException e3) {
            ThrowableExtension.printStackTrace(e3);
        } catch (IllegalAccessException e4) {
            ThrowableExtension.printStackTrace(e4);
        } catch (InvocationTargetException e5) {
            ThrowableExtension.printStackTrace(e5);
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static boolean disconnectCall() {
        try {
            Runtime.getRuntime().exec("service call phone 5 \n");
            return true;
        } catch (Exception exc) {
            ThrowableExtension.printStackTrace(exc);
            return false;
        }
    }

    public static boolean killCall(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(UserConst.PHONE);
            Method methodGetITelephony = Class.forName(telephonyManager.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
            methodGetITelephony.setAccessible(true);
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager, new Object[0]);
            Class.forName(telephonyInterface.getClass().getName()).getDeclaredMethod("endCall", new Class[0]).invoke(telephonyInterface, new Object[0]);
            return true;
        } catch (Exception ex) {
            ThrowableExtension.printStackTrace(ex);
            return false;
        }
    }

    private void endCall() {
        try {
            try {
                try {
                    Stub.asInterface((IBinder) CallReceiver.class.getClassLoader().loadClass("android.os.ServiceManager").getDeclaredMethod("getService", new Class[]{String.class}).invoke(null, new Object[]{UserConst.PHONE})).endCall();
                    KLog.e("=============挂断电话===============");
                    L.file("=============挂断电话===============", 4);
                } catch (RemoteException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            } catch (IllegalAccessException e2) {
                ThrowableExtension.printStackTrace(e2);
            } catch (InvocationTargetException e3) {
                ThrowableExtension.printStackTrace(e3);
            }
        } catch (ClassNotFoundException e4) {
            ThrowableExtension.printStackTrace(e4);
        } catch (NoSuchMethodException e5) {
            ThrowableExtension.printStackTrace(e5);
        } catch (Exception e6) {
            ThrowableExtension.printStackTrace(e6);
        }
    }

    /* access modifiers changed from: 0000 */
    public void answerRing() {
        KLog.i("info=====================接听电话");
        try {
            getITelephony(manager).answerRingingCall();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public ITelephony getITelephony(TelephonyManager telephony) throws Exception {
        Method getITelephonyMethod = telephony.getClass().getDeclaredMethod("getITelephony", new Class[0]);
        getITelephonyMethod.setAccessible(true);
        return (ITelephony) getITelephonyMethod.invoke(telephony, new Object[0]);
    }

    public static Contact getContact(Context context, String phoneNumber) {
        Contact contact = new Contact(phoneNumber);
        if (TextUtils.isEmpty(phoneNumber)) {
            contact.setDisplayName("");
        }
        Cursor cursor = null;
        try {
            Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
            Cursor cursor2 = context.getContentResolver().query(uri, new String[]{"display_name", "type", "label"}, null, null, "display_name LIMIT 1");
            if (cursor2.moveToNext()) {
                contact.setDisplayName(cursor2.getString(cursor2.getColumnIndex("display_name")));
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        } catch (Exception e) {
            contact.setDisplayName(phoneNumber);
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return contact;
    }
}
