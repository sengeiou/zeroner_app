package com.iwown.device_module.device_message_push.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.device_message_push.NotificationMonitor;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.List;

public class ServiceUtils {
    public static final int RETRIVE_SERVICE_COUNT = 50;

    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        List<RunningServiceInfo> serviceInfos = ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getRunningServices(50);
        if (serviceInfos == null || serviceInfos.size() < 1) {
            return false;
        }
        int i = 0;
        while (true) {
            if (i >= serviceInfos.size()) {
                break;
            } else if (((RunningServiceInfo) serviceInfos.get(i)).service.getClassName().contains(className)) {
                isRunning = true;
                break;
            } else {
                i++;
            }
        }
        return isRunning;
    }

    public static void toggleNotificationListenerService() {
        ComponentName thisComponent = new ComponentName(ContextUtil.app, NotificationMonitor.class);
        PackageManager pm = ContextUtil.app.getPackageManager();
        pm.setComponentEnabledSetting(thisComponent, 2, 1);
        pm.setComponentEnabledSetting(thisComponent, 1, 1);
    }

    public static boolean ensureCollectorRunning() {
        ComponentName collectorComponent = new ComponentName(ContextUtil.app, NotificationMonitor.class);
        boolean collectorRunning = false;
        List<RunningServiceInfo> runningServices = ((ActivityManager) ContextUtil.app.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null) {
            return false;
        }
        for (RunningServiceInfo service : runningServices) {
            if (service.service.equals(collectorComponent) && service.pid == Process.myPid()) {
                collectorRunning = true;
            }
        }
        if (collectorRunning) {
            return true;
        }
        return false;
    }
}
