package com.iwown.device_module.device_message_push.heart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {
    MyAlarmReceiver alarm = new MyAlarmReceiver();

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            this.alarm.setAlarm(context);
        }
    }
}
