package com.tencent.tinker.loader.hotplug;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.hotplug.handler.AMSInterceptHandler;
import com.tencent.tinker.loader.hotplug.handler.MHMessageHandler;
import com.tencent.tinker.loader.hotplug.handler.PMSInterceptHandler;
import com.tencent.tinker.loader.hotplug.interceptor.HandlerMessageInterceptor;
import com.tencent.tinker.loader.hotplug.interceptor.ServiceBinderInterceptor;
import com.tencent.tinker.loader.hotplug.interceptor.TinkerHackInstrumentation;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import com.tencent.tinker.loader.shareutil.ShareSecurityCheck;

public final class ComponentHotplug {
    private static final String TAG = "Tinker.ComponentHotplug";
    private static ServiceBinderInterceptor sAMSInterceptor;
    private static volatile boolean sInstalled = false;
    private static HandlerMessageInterceptor sMHMessageInterceptor;
    private static ServiceBinderInterceptor sPMSInterceptor;
    private static TinkerHackInstrumentation sTinkerHackInstrumentation;

    public static synchronized void install(TinkerApplication app, ShareSecurityCheck checker) throws UnsupportedEnvironmentException {
        synchronized (ComponentHotplug.class) {
            if (!sInstalled) {
                try {
                    if (IncrementComponentManager.init(app, checker)) {
                        sAMSInterceptor = new ServiceBinderInterceptor(app, EnvConsts.ACTIVITY_MANAGER_SRVNAME, new AMSInterceptHandler(app));
                        sPMSInterceptor = new ServiceBinderInterceptor(app, EnvConsts.PACKAGE_MANAGER_SRVNAME, new PMSInterceptHandler());
                        sAMSInterceptor.install();
                        sPMSInterceptor.install();
                        if (VERSION.SDK_INT < 27) {
                            sMHMessageInterceptor = new HandlerMessageInterceptor(fetchMHInstance(app), new MHMessageHandler(app));
                            sMHMessageInterceptor.install();
                        } else {
                            sTinkerHackInstrumentation = TinkerHackInstrumentation.create(app);
                            sTinkerHackInstrumentation.install();
                        }
                        sInstalled = true;
                        Log.i(TAG, "installed successfully.");
                    }
                } catch (Throwable thr) {
                    uninstall();
                    throw new UnsupportedEnvironmentException(thr);
                }
            }
        }
    }

    public static synchronized void ensureComponentHotplugInstalled(TinkerApplication app) throws UnsupportedEnvironmentException {
        synchronized (ComponentHotplug.class) {
            if (sInstalled) {
                try {
                    sAMSInterceptor.install();
                    sPMSInterceptor.install();
                    if (VERSION.SDK_INT < 27) {
                        sMHMessageInterceptor.install();
                    } else {
                        sTinkerHackInstrumentation.install();
                    }
                } catch (Throwable thr) {
                    uninstall();
                    throw new UnsupportedEnvironmentException(thr);
                }
            } else {
                Log.i(TAG, "method install() is not invoked, ignore ensuring operations.");
            }
        }
    }

    private static Handler fetchMHInstance(Context context) {
        Object activityThread = ShareReflectUtil.getActivityThread(context, null);
        if (activityThread == null) {
            throw new IllegalStateException("failed to fetch instance of ActivityThread.");
        }
        try {
            return (Handler) ShareReflectUtil.findField(activityThread, "mH").get(activityThread);
        } catch (Throwable thr) {
            throw new IllegalStateException(thr);
        }
    }

    public static synchronized void uninstall() {
        synchronized (ComponentHotplug.class) {
            if (sInstalled) {
                try {
                    sAMSInterceptor.uninstall();
                    sPMSInterceptor.uninstall();
                    if (VERSION.SDK_INT < 27) {
                        sMHMessageInterceptor.uninstall();
                    } else {
                        sTinkerHackInstrumentation.uninstall();
                    }
                } catch (Throwable thr) {
                    Log.e(TAG, "exception when uninstall.", thr);
                }
                sInstalled = false;
            }
        }
        return;
    }

    private ComponentHotplug() {
        throw new UnsupportedOperationException();
    }
}
