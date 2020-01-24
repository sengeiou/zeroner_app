package com.tencent.bugly.beta.ui;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.os.Build.VERSION;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.beta.global.d;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.beta.upgrade.c;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* compiled from: BUGLY */
public class g {
    public static final Map<Integer, b> a = new ConcurrentHashMap();
    public static final Map<Integer, d> b = new ConcurrentHashMap();
    public static final Map<Integer, d> c = new ConcurrentHashMap();
    private static d d;

    public static synchronized void a(b bVar, boolean z) {
        synchronized (g.class) {
            if (VERSION.SDK_INT >= 14) {
                a(bVar, z, false, BootloaderScanner.TIMEOUT);
            } else {
                try {
                    ActivityManager activityManager = (ActivityManager) e.E.s.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
                    if (activityManager != null) {
                        activityManager.getRunningTasks(1);
                        a(bVar, z, false, BootloaderScanner.TIMEOUT);
                    }
                } catch (SecurityException e) {
                    if (z) {
                        a(bVar, z, true, 0);
                    } else {
                        an.e("无法获取GET_TASK权限，将在通知栏提醒升级，如需弹窗提醒，请在AndroidManifest.xml中添加GET_TASKS权限：\n<uses-permission android:name=\"android.permission.GET_TASKS\" />\n", new Object[0]);
                        if (!(c.a.b == null || c.a.b.a == null)) {
                            c.a.a(c.a.b.a, bVar);
                        }
                    }
                }
            }
        }
    }

    public static synchronized void a(b bVar, boolean z, boolean z2, long j) {
        synchronized (g.class) {
            if (bVar != null) {
                if (!bVar.b()) {
                    int hashCode = bVar.hashCode();
                    if (bVar instanceof h) {
                        com.tencent.bugly.beta.utils.e.b(d);
                        if (((h) bVar).p.g == 2) {
                            d = new d(15, bVar, Boolean.valueOf(z), Boolean.valueOf(z2), Long.valueOf(j));
                            com.tencent.bugly.beta.utils.e.a(d, 3000);
                        }
                        if (z || b()) {
                            com.tencent.bugly.beta.utils.e.b((Runnable) b.remove(Integer.valueOf(hashCode)));
                        } else {
                            d dVar = (d) b.get(Integer.valueOf(hashCode));
                            if (dVar == null) {
                                dVar = new d(11, bVar, Boolean.valueOf(z), Boolean.valueOf(z2), Long.valueOf(j));
                                b.put(Integer.valueOf(hashCode), dVar);
                            }
                            com.tencent.bugly.beta.utils.e.b(dVar);
                            com.tencent.bugly.beta.utils.e.a(dVar, j);
                        }
                    }
                    if (z2 || ap.b(e.E.s)) {
                        com.tencent.bugly.beta.utils.e.b((Runnable) c.remove(Integer.valueOf(hashCode)));
                        d dVar2 = new d(17, a, Integer.valueOf(hashCode), bVar);
                        FragmentActivity activity = bVar.getActivity();
                        if (activity != null) {
                            if (activity instanceof BetaActivity) {
                                ((BetaActivity) activity).onDestroyRunnable = dVar2;
                            } else {
                                com.tencent.bugly.beta.utils.e.a(dVar2, 400);
                            }
                            activity.finish();
                        } else {
                            dVar2.run();
                        }
                    } else {
                        d dVar3 = (d) c.get(Integer.valueOf(hashCode));
                        if (dVar3 == null) {
                            dVar3 = new d(11, bVar, Boolean.valueOf(z), Boolean.valueOf(z2), Long.valueOf(j));
                            c.put(Integer.valueOf(hashCode), dVar3);
                        }
                        com.tencent.bugly.beta.utils.e.b(dVar3);
                        com.tencent.bugly.beta.utils.e.a(dVar3, j);
                    }
                }
            }
        }
    }

    public static String a() {
        try {
            if (VERSION.SDK_INT >= 14) {
                return a.b().u;
            }
            ActivityManager activityManager = (ActivityManager) e.E.s.getSystemService(EnvConsts.ACTIVITY_MANAGER_SRVNAME);
            if (activityManager != null) {
                List runningTasks = activityManager.getRunningTasks(1);
                if (runningTasks != null && !runningTasks.isEmpty()) {
                    return ((RunningTaskInfo) runningTasks.get(0)).topActivity.getClassName();
                }
            }
            return null;
        } catch (SecurityException e) {
            an.e("无法获取Activity信息，请在AndroidManifest.xml中添加GET_TASKS权限：\n<uses-permission android:name=\"android.permission.GET_TASKS\" />\n", new Object[0]);
        } catch (Exception e2) {
            if (!an.b(e2)) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
    }

    public static synchronized boolean b() {
        boolean z;
        Class cls;
        synchronized (g.class) {
            String a2 = a();
            if (a2 == null || a2.equals("background") || a2.equals("unknown")) {
                z = false;
            } else {
                Class cls2 = null;
                try {
                    cls = Class.forName(a2);
                } catch (ClassNotFoundException e) {
                    ThrowableExtension.printStackTrace(e);
                    cls = cls2;
                }
                if (!e.E.m.isEmpty()) {
                    Iterator it = e.E.m.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        Class cls3 = (Class) it.next();
                        if (TextUtils.equals(cls3.getName(), a2) || (cls != null && cls3.isAssignableFrom(cls))) {
                            z = true;
                        }
                    }
                } else if (!e.E.n.isEmpty()) {
                    Iterator it2 = e.E.n.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            z = true;
                            break;
                        }
                        Class cls4 = (Class) it2.next();
                        if (TextUtils.equals(cls4.getName(), a2) || (cls != null && cls4.isAssignableFrom(cls))) {
                            z = false;
                        }
                    }
                    z = false;
                } else {
                    z = true;
                }
            }
        }
        return z;
    }
}
