package com.iwown.healthy;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.RemoteViews;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.healthy.zeroner_pro.R;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.walk_29_data.ModuleRouteWalkService;
import com.iwown.data_link.walk_29_data.V3_walk;
import com.iwown.device_module.common.Bluetooth.receiver.iv.bean.CurrData_0x29;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.util.Calendar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TimeService extends Service {
    public static final String TAG = "com.kunekt.healthy.common.TimeService";
    boolean isRuning = true;
    boolean isSend = true;
    private String mWalkNotifyChannelId;
    NotificationManager nm;
    public Notification notification;
    private int num = 0;
    RemoteViews remoteView;
    int state = 0;
    private Thread workThread;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        KLog.d("TimeService--", "TimeService------>启动");
        EventBus.getDefault().register(this);
        this.nm = (NotificationManager) getSystemService("notification");
        createNotificationChannel();
        Calendar calendar = Calendar.getInstance();
        V3_walk walk = ModuleRouteWalkService.getInstance().getWalk(UserConfig.getInstance().getNewUID(), new DateUtil(calendar.get(1), calendar.get(2) + 1, calendar.get(5), 8, 0, 0).getSyyyyMMddDate(), UserConfig.getInstance().getDevice());
        int steps = 0;
        float cal = 0.0f;
        if (walk != null) {
            steps = walk.getStep();
            cal = Util.doubleToFloat(1, (double) walk.getCalorie());
        }
        Builder mBuilder = getWalkNotificationBuilder();
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(this, LoadingActivity.class), 0);
        mBuilder.setSmallIcon(R.mipmap.small_icon_for_notification2).setContentTitle(steps + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.sport_module_unit_step)).setContentText(cal + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.sport_module_unit_cal)).setContentIntent(pendingintent).setWhen(System.currentTimeMillis()).setPriority(0).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setOngoing(true);
        this.notification = mBuilder.build();
        this.nm.notify(BaseQuickAdapter.HEADER_VIEW, this.notification);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.e("TimeService--", "TimeService------>启动111");
        if (this.notification != null) {
            startForeground(BaseQuickAdapter.HEADER_VIEW, this.notification);
        } else {
            startForeground(BaseQuickAdapter.HEADER_VIEW, new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CurrData_0x29 event) {
        Log.d("TimeService--", "TimeService--收到29hsunixn ");
        Calendar calendar = Calendar.getInstance();
        V3_walk walk = ModuleRouteWalkService.getInstance().getWalk(UserConfig.getInstance().getNewUID(), new DateUtil(calendar.get(1), calendar.get(2) + 1, calendar.get(5), 8, 0, 0).getSyyyyMMddDate(), UserConfig.getInstance().getDevice());
        KLog.e(walk);
        int steps = 0;
        float cal = 0.0f;
        if (walk != null) {
            steps = walk.getStep();
            cal = Util.doubleToFloat(1, (double) walk.getCalorie());
        }
        Builder mBuilder = getWalkNotificationBuilder();
        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(this, LoadingActivity.class), 0);
        mBuilder.setSmallIcon(R.mipmap.small_icon_for_notification2).setContentTitle(steps + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.sport_module_unit_step)).setContentText(cal + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getString(R.string.sport_module_unit_cal)).setContentIntent(pendingintent).setWhen(System.currentTimeMillis()).setPriority(0).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setOngoing(true);
        this.notification = mBuilder.build();
        this.nm.notify(BaseQuickAdapter.HEADER_VIEW, this.notification);
    }

    @NonNull
    private Builder getWalkNotificationBuilder() {
        if (VERSION.SDK_INT >= 26) {
            return new Builder(this, this.mWalkNotifyChannelId);
        }
        return new Builder(this);
    }

    public void onDestroy() {
        L.file("TimeService------>关闭", 3);
        this.isRuning = false;
        this.state = 1;
        stopForeground(true);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void createNotificationChannel() {
        if (VERSION.SDK_INT >= 26) {
            this.mWalkNotifyChannelId = "walk_data_notify";
            NotificationChannel channel = new NotificationChannel(this.mWalkNotifyChannelId, "WalKDataNotification", 3);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            if (this.nm != null) {
                this.nm.createNotificationChannel(channel);
            }
        }
    }
}
