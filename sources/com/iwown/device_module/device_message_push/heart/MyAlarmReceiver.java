package com.iwown.device_module.device_message_push.heart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class MyAlarmReceiver extends WakefulBroadcastReceiver {
    private PendingIntent alarmIntent;
    private AlarmManager alarmMgr;

    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, HeartService.class);
        try {
            if (VERSION.SDK_INT >= 26) {
                context.startForegroundService(service);
                startWakefulService(context, service);
                return;
            }
            startWakefulService(context, service);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void setAlarm(Context context) {
        this.alarmMgr = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.alarmIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, MyAlarmReceiver.class), 0);
        this.alarmMgr.cancel(this.alarmIntent);
        this.alarmMgr.setInexactRepeating(2, 900000, 900000, this.alarmIntent);
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, MyBootReceiver.class), 1, 1);
    }

    public void cancelAlarm(Context context) {
        if (this.alarmMgr != null) {
            this.alarmMgr.cancel(this.alarmIntent);
        }
        context.getPackageManager().setComponentEnabledSetting(new ComponentName(context, MyBootReceiver.class), 2, 1);
    }
}
