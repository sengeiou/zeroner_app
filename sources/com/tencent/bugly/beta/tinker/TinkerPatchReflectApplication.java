package com.tencent.bugly.beta.tinker;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.Application.OnProvideAssistDataListener;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.tinker.loader.TinkerLoader;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: BUGLY */
public class TinkerPatchReflectApplication extends TinkerApplication {
    private static final String TAG = "Tinker.ReflectApp";
    private boolean isReflectFailure = false;
    private String rawApplicationName = null;
    private Application realApplication;

    public TinkerPatchReflectApplication() {
        super(7, "com.tencent.bugly.beta.tinker.TinkerApplicationLike", TinkerLoader.class.getName(), false);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            String rawApplicationName2 = getRawApplicationName(base);
            if (rawApplicationName2 == null) {
                throw new RuntimeException("can get real realApplication from manifest!");
            }
            this.realApplication = (Application) Class.forName(rawApplicationName2, false, getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
            if (this.realApplication != null) {
                try {
                    Method declaredMethod = ContextWrapper.class.getDeclaredMethod("attachBaseContext", new Class[]{Context.class});
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(this.realApplication, new Object[]{base});
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        } catch (Exception e2) {
            throw new IllegalStateException(e2);
        }
    }

    public void onCreate() {
        Class cls;
        Object activityThread;
        Class cls2;
        if (this.realApplication != null) {
            try {
                cls = Class.forName("android.app.ActivityThread");
                activityThread = ShareReflectUtil.getActivityThread(this, cls);
                Field declaredField = cls.getDeclaredField("mInitialApplication");
                declaredField.setAccessible(true);
                Application application = (Application) declaredField.get(activityThread);
                if (this.realApplication != null && application == this) {
                    declaredField.set(activityThread, this.realApplication);
                }
                if (this.realApplication != null) {
                    Field declaredField2 = cls.getDeclaredField("mAllApplications");
                    declaredField2.setAccessible(true);
                    List list = (List) declaredField2.get(activityThread);
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) == this) {
                            list.set(i, this.realApplication);
                        }
                    }
                }
                cls2 = Class.forName("android.app.LoadedApk");
            } catch (ClassNotFoundException e) {
                cls2 = Class.forName("android.app.ActivityThread$PackageInfo");
            } catch (Throwable th) {
                Log.e(TAG, "Error, reflect Application fail, result:" + th);
                this.isReflectFailure = true;
            }
            Field declaredField3 = cls2.getDeclaredField("mApplication");
            declaredField3.setAccessible(true);
            Field field = null;
            try {
                field = Application.class.getDeclaredField("mLoadedApk");
            } catch (NoSuchFieldException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
            String[] strArr = {"mPackages", "mResourcePackages"};
            for (int i2 = 0; i2 < 2; i2++) {
                Field declaredField4 = cls.getDeclaredField(strArr[i2]);
                declaredField4.setAccessible(true);
                for (Entry value : ((Map) declaredField4.get(activityThread)).entrySet()) {
                    Object obj = ((WeakReference) value.getValue()).get();
                    if (obj != null && declaredField3.get(obj) == this) {
                        if (this.realApplication != null) {
                            declaredField3.set(obj, this.realApplication);
                        }
                        if (!(this.realApplication == null || field == null)) {
                            field.set(this.realApplication, obj);
                        }
                    }
                }
            }
            if (!this.isReflectFailure) {
                try {
                    Class cls3 = Class.forName("com.tencent.bugly.beta.tinker.TinkerApplicationLike", false, getClassLoader());
                    Log.e(TAG, "replaceApplicationLike delegateClass:" + cls3);
                    ShareReflectUtil.findField(cls3, "application").set(cls3.getDeclaredMethod("getTinkerPatchApplicationLike", new Class[0]).invoke(cls3, new Object[0]), this.realApplication);
                } catch (Throwable th2) {
                    Log.e(TAG, "replaceApplicationLike exception:" + th2.getMessage());
                }
            }
        }
        super.onCreate();
        if (this.realApplication != null) {
            this.realApplication.onCreate();
        }
    }

    public String getRawApplicationName(Context context) {
        if (this.rawApplicationName != null) {
            return this.rawApplicationName;
        }
        try {
            Object obj = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.get("TINKER_PATCH_APPLICATION");
            if (obj != null) {
                this.rawApplicationName = String.valueOf(obj);
            } else {
                this.rawApplicationName = null;
            }
            Log.i(TAG, "with app realApplication from manifest applicationName:" + this.rawApplicationName);
            return this.rawApplicationName;
        } catch (Exception e) {
            Log.e(TAG, "getManifestApplication exception:" + e.getMessage());
            return null;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.onConfigurationChanged(configuration);
        } else {
            this.realApplication.onConfigurationChanged(configuration);
        }
    }

    public void onLowMemory() {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.onLowMemory();
        } else {
            this.realApplication.onLowMemory();
        }
    }

    @TargetApi(14)
    public void onTrimMemory(int level) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.onTrimMemory(level);
        } else {
            this.realApplication.onTrimMemory(level);
        }
    }

    public void onTerminate() {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.onTerminate();
        } else {
            this.realApplication.onTerminate();
        }
    }

    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        if (!this.isReflectFailure || this.realApplication == null) {
            return super.registerReceiver(receiver, filter);
        }
        return this.realApplication.registerReceiver(receiver, filter);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.unregisterReceiver(receiver);
        } else {
            this.realApplication.unregisterReceiver(receiver);
        }
    }

    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        if (!this.isReflectFailure || this.realApplication == null) {
            return super.bindService(service, conn, flags);
        }
        return this.realApplication.bindService(service, conn, flags);
    }

    public void unbindService(ServiceConnection conn) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.unbindService(conn);
        } else {
            this.realApplication.unbindService(conn);
        }
    }

    @TargetApi(14)
    public void registerComponentCallbacks(ComponentCallbacks var1) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.registerComponentCallbacks(var1);
        } else {
            this.realApplication.registerComponentCallbacks(var1);
        }
    }

    @TargetApi(14)
    public void unregisterComponentCallbacks(ComponentCallbacks var1) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.unregisterComponentCallbacks(var1);
        } else {
            this.realApplication.unregisterComponentCallbacks(var1);
        }
    }

    @TargetApi(14)
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks var1) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.registerActivityLifecycleCallbacks(var1);
        } else {
            this.realApplication.registerActivityLifecycleCallbacks(var1);
        }
    }

    @TargetApi(14)
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks var1) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.unregisterActivityLifecycleCallbacks(var1);
        } else {
            this.realApplication.unregisterActivityLifecycleCallbacks(var1);
        }
    }

    @TargetApi(18)
    public void registerOnProvideAssistDataListener(OnProvideAssistDataListener var1) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.registerOnProvideAssistDataListener(var1);
        } else {
            this.realApplication.registerOnProvideAssistDataListener(var1);
        }
    }

    @TargetApi(18)
    public void unregisterOnProvideAssistDataListener(OnProvideAssistDataListener var1) {
        if (!this.isReflectFailure || this.realApplication == null) {
            super.unregisterOnProvideAssistDataListener(var1);
        } else {
            this.realApplication.unregisterOnProvideAssistDataListener(var1);
        }
    }

    public Resources getResources() {
        if (!this.isReflectFailure || this.realApplication == null) {
            return super.getResources();
        }
        return this.realApplication.getResources();
    }

    public ClassLoader getClassLoader() {
        if (!this.isReflectFailure || this.realApplication == null) {
            return super.getClassLoader();
        }
        return this.realApplication.getClassLoader();
    }

    public AssetManager getAssets() {
        return (!this.isReflectFailure || this.realApplication == null) ? super.getAssets() : this.realApplication.getAssets();
    }

    public ContentResolver getContentResolver() {
        if (!this.isReflectFailure || this.realApplication == null) {
            return super.getContentResolver();
        }
        return this.realApplication.getContentResolver();
    }
}
