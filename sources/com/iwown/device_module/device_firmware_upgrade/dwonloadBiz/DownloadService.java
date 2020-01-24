package com.iwown.device_module.device_firmware_upgrade.dwonloadBiz;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class DownloadService extends Service {
    @TargetApi(26)
    public void onCreate() {
        super.onCreate();
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "ForegroundServiceChannel", 3);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(channel);
            startForeground(1, new Builder(getApplicationContext(), "11111").build());
            return;
        }
        startForeground(1, new Notification());
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            switch (intent.getIntExtra("type", 0)) {
                case 1:
                    String path = intent.getStringExtra("path");
                    String fileName = intent.getStringExtra("fileName");
                    if (path != null) {
                        try {
                            DownloadServiceBiz.getInstance().downloadFirmware(path, fileName);
                            break;
                        } catch (Exception e) {
                            ThrowableExtension.printStackTrace(e);
                            DownloadServiceBiz.getInstance().sendErrorMessage("失败 error " + e.getMessage());
                            break;
                        }
                    }
                    break;
            }
        } else {
            stopSelf();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
