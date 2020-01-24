package com.tencent.tinker.lib.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import com.tencent.tinker.lib.service.TinkerPatchService;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;
import java.util.List;

public class TinkerServiceInternals extends ShareTinkerInternals {
    private static final String TAG = "Tinker.ServiceInternals";
    private static String patchServiceProcessName = null;

    public static void killTinkerPatchServiceProcess(Context context) {
        String serverProcessName = getTinkerPatchServiceName(context);
        if (serverProcessName != null) {
            List<RunningAppProcessInfo> appProcessList = ((ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME)).getRunningAppProcesses();
            if (appProcessList != null) {
                for (RunningAppProcessInfo appProcess : appProcessList) {
                    if (appProcess.processName.equals(serverProcessName)) {
                        Process.killProcess(appProcess.pid);
                    }
                }
            }
        }
    }

    public static boolean isTinkerPatchServiceRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
        String serverName = getTinkerPatchServiceName(context);
        if (serverName == null) {
            return false;
        }
        try {
            List<RunningAppProcessInfo> appProcessList = am.getRunningAppProcesses();
            if (appProcessList == null) {
                return false;
            }
            for (RunningAppProcessInfo appProcess : appProcessList) {
                if (appProcess.processName.equals(serverName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "isTinkerPatchServiceRunning Exception: " + e.toString());
            return false;
        } catch (Error e2) {
            Log.e(TAG, "isTinkerPatchServiceRunning Error: " + e2.toString());
            return false;
        }
    }

    public static String getTinkerPatchServiceName(Context context) {
        if (patchServiceProcessName != null) {
            return patchServiceProcessName;
        }
        String serviceName = getServiceProcessName(context, TinkerPatchService.getRealRunnerClass());
        if (serviceName == null) {
            return null;
        }
        patchServiceProcessName = serviceName;
        return patchServiceProcessName;
    }

    public static boolean isInTinkerPatchServiceProcess(Context context) {
        String process = getProcessName(context);
        String service = getTinkerPatchServiceName(context);
        if (service == null || service.length() == 0) {
            return false;
        }
        return process.equals(service);
    }

    private static String getServiceProcessName(Context context, Class<? extends Service> serviceClass) {
        try {
            return context.getPackageManager().getServiceInfo(new ComponentName(context, serviceClass), 0).processName;
        } catch (Throwable th) {
            return null;
        }
    }
}
