package com.iwown.device_module.device_message_push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.iwown.ble_module.model.FMdeviceInfo;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_message_push.bean.SmsInfo;
import com.iwown.device_module.device_setting.configure.DeviceUtils;
import com.iwown.lib_common.log.L;
import com.iwown.my_module.utility.Constants;
import com.socks.library.KLog;

public class SmsReceiver extends BroadcastReceiver {
    public static final boolean D = true;
    private static final String PHONE_360_RECEIVED = "com.qiku.android.action.NEW_MESSAGE";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String SMS_RECEIVED_NEW = "android.provider.Telephony.SMS_DELIVER";
    private String TAG = getClass().getSimpleName();
    private FMdeviceInfo fMdeviceInfo;
    Handler mHandler = new Handler(Looper.getMainLooper());

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

    public static class Sms {
        private String body;
        private Contact contact;
        private String number;

        public Sms() {
        }

        public Sms(String number2, String body2, Contact contact2) {
            this.number = number2;
            this.body = body2;
            this.contact = contact2;
        }

        public String getNumber() {
            return this.number;
        }

        public void setNumber(String number2) {
            this.number = number2;
        }

        public String getBody() {
            return this.body;
        }

        public void setBody(String body2) {
            this.body = body2;
        }

        public Contact getContact() {
            return this.contact;
        }

        public void setContact(Contact contact2) {
            this.contact = contact2;
        }
    }

    public void onReceive(Context context, Intent intent) {
        Log.e(this.TAG, "+++ ON RECEIVE +++");
        L.file("短信广播收到短信", 4);
        if (intent.getAction().equalsIgnoreCase(PHONE_360_RECEIVED) && DeviceUtils.messageSwitchStatue().isSms()) {
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    Cursor mCursor = ContextUtil.app.getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"_id", "address", Constants.NEW_MAP_KEY, "date", "person"}, "read=?", new String[]{"0"}, "date desc");
                    SmsInfo _smsInfo = new SmsInfo();
                    if (mCursor != null) {
                        if (mCursor.moveToNext()) {
                            _smsInfo = new SmsInfo();
                            int _inIndex = mCursor.getColumnIndex("_id");
                            if (_inIndex != -1) {
                                _smsInfo._id = mCursor.getString(_inIndex);
                            }
                            int addressIndex = mCursor.getColumnIndex("address");
                            if (addressIndex != -1) {
                                _smsInfo.smsAddress = mCursor.getString(addressIndex);
                            }
                            int bodyIndex = mCursor.getColumnIndex(Constants.NEW_MAP_KEY);
                            if (bodyIndex != -1) {
                                _smsInfo.smsBody = mCursor.getString(bodyIndex);
                            }
                            int dateIndex = mCursor.getColumnIndex("date");
                            if (dateIndex != -1) {
                                _smsInfo.date = mCursor.getString(dateIndex);
                            }
                            int personIndex = mCursor.getColumnIndex("person");
                            if (dateIndex != -1) {
                                _smsInfo.person = mCursor.getString(personIndex);
                            }
                            L.file("特殊手机获取的短信内容为：" + _smsInfo.toString(), 4);
                        }
                        mCursor.close();
                        String name = SmsReceiver.getContact(ContextUtil.app, _smsInfo.smsAddress).getDisplayName();
                        if (!TextUtils.isEmpty(name)) {
                            NotificationBiz.addMsg(2, "SMS|" + name + ": " + _smsInfo.smsBody);
                        } else {
                            NotificationBiz.addMsg(2, "SMS|" + _smsInfo.smsAddress + ": " + _smsInfo.smsBody);
                        }
                    }
                }
            }, 1000);
        } else if (intent.getAction().equals(SMS_RECEIVED) || intent.getAction().equals(SMS_RECEIVED_NEW)) {
            Sms sms = getSms(context, intent);
            if (sms != null) {
                String number = sms.getContact().getDisplayName();
                String smsBody = sms.getBody();
                if (number.length() != 0 && number.startsWith("+86")) {
                    number = number.substring(3, number.length());
                }
                if (DeviceUtils.messageSwitchStatue().isSms()) {
                    KLog.i("info" + sms.getBody() + "==========================================");
                    if (sms.getBody() != null) {
                        number = number + "：" + smsBody;
                    }
                    if (number == null) {
                        number = sms.getNumber() + smsBody;
                    }
                    int index = 0;
                    int j = 0;
                    while (true) {
                        if (j >= number.length()) {
                            break;
                        }
                        if (number.charAt(j) < '@' || (number.charAt(j) < 128 && number.charAt(j) > '`')) {
                            index++;
                        } else {
                            index += 3;
                        }
                        if (index > 190) {
                            number = number.substring(0, j);
                            break;
                        }
                        j++;
                    }
                    NotificationBiz.addMsg(2, "SMS|" + number);
                    L.file("短信广播收到的短信内容为：" + number, 4);
                }
            }
        }
    }

    private FMdeviceInfo jsonToFMdeviceInfo(String jsonString) {
        return (FMdeviceInfo) new Gson().fromJson(jsonString, FMdeviceInfo.class);
    }

    public static Sms getSms(Context context, Intent intent) {
        StringBuilder number = new StringBuilder("");
        StringBuilder body = new StringBuilder("");
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            return null;
        }
        Object[] _pdus = (Object[]) bundle.get("pdus");
        if (_pdus == null || _pdus.length == 0) {
            return null;
        }
        SmsMessage[] message = new SmsMessage[_pdus.length];
        for (int i = 0; i < _pdus.length; i++) {
            message[i] = SmsMessage.createFromPdu((byte[]) _pdus[i]);
        }
        for (SmsMessage currentMessage : message) {
            if (!number.toString().equals(currentMessage.getDisplayOriginatingAddress())) {
                number.append(currentMessage.getDisplayOriginatingAddress());
            }
            body.append(currentMessage.getDisplayMessageBody());
        }
        return new Sms(number.toString(), body.toString(), getContact(context, number.toString()));
    }

    public static Contact getContact(Context context, String phoneNumber) {
        Contact contact = new Contact(phoneNumber);
        if (TextUtils.isEmpty(phoneNumber)) {
            contact.setDisplayName("Unknown Number");
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
