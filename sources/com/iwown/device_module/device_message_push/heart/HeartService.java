package com.iwown.device_module.device_message_push.heart;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import com.iwown.device_module.R;
import com.iwown.device_module.common.Bluetooth.BluetoothOperation;
import com.iwown.device_module.common.Bluetooth.CommandOperation;

public class HeartService extends IntentService {
    public static final int ZERONER_HEALTH_NOTIFICATION_ID = 553029;
    private static NotificationManager nm;
    private String mWalkNotifyChannelId;

    public HeartService() {
        super("HeartService");
    }

    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService("notification");
        createNotificationChannel();
    }

    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 3);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
            startForeground(1, new Builder(getApplicationContext(), "11111").build());
        } else {
            startForeground(1, new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @NonNull
    private NotificationCompat.Builder getNotificationBuilder() {
        if (VERSION.SDK_INT >= 26) {
            return new NotificationCompat.Builder(this, this.mWalkNotifyChannelId);
        }
        return new NotificationCompat.Builder(this);
    }

    public void createNotificationChannel() {
        if (VERSION.SDK_INT >= 26) {
            this.mWalkNotifyChannelId = "heart_service";
            NotificationChannel channel = new NotificationChannel(this.mWalkNotifyChannelId, "MessagePushNotification", 3);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            if (nm != null) {
                nm.createNotificationChannel(channel);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        sendNotification("keep heart warm");
        if (BluetoothOperation.isMtk()) {
            CommandOperation.writeGpsParam2Dev();
        }
        MyAlarmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        createNotification();
    }

    public void createNotification() {
        NotificationCompat.Builder mBuilder = getNotificationBuilder();
        mBuilder.setTicker(null).setSmallIcon(R.mipmap.ic_launcher).setWhen(System.currentTimeMillis()).setPriority(0).setOngoing(true);
        nm.notify(ZERONER_HEALTH_NOTIFICATION_ID, mBuilder.build());
        nm.cancel(ZERONER_HEALTH_NOTIFICATION_ID);
    }
}
