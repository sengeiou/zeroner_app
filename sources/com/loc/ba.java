package com.loc;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.lang.reflect.Constructor;

/* compiled from: InstanceFactory */
public final class ba {
    public static Class a(Context context, v vVar, String str) {
        bd c = c(context, vVar);
        try {
            if (a(c)) {
                return c.loadClass(str);
            }
            return null;
        } catch (Throwable th) {
            ag.a(th, "InstanceFactory", "loadpn");
            return null;
        }
    }

    public static <T> T a(Context context, v vVar, String str, Class cls, Class[] clsArr, Object[] objArr) throws k {
        T a = a(c(context, vVar), str, clsArr, objArr);
        if (a == null) {
            a = a(cls, clsArr, objArr);
            if (a == null) {
                throw new k("获取对象错误");
            }
        }
        return a;
    }

    private static <T> T a(bd bdVar, String str, Class[] clsArr, Object[] objArr) {
        try {
            if (a(bdVar)) {
                Class loadClass = bdVar.loadClass(str);
                if (loadClass != null) {
                    Constructor declaredConstructor = loadClass.getDeclaredConstructor(clsArr);
                    declaredConstructor.setAccessible(true);
                    return declaredConstructor.newInstance(objArr);
                }
            }
        } catch (Throwable th) {
            ag.a(th, "IFactory", "getWrap");
        }
        return null;
    }

    private static <T> T a(Class cls, Class[] clsArr, Object[] objArr) {
        T t = null;
        if (cls == null) {
            return t;
        }
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(clsArr);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(objArr);
        } catch (Throwable th) {
            ag.a(th, "IFactory", "gIns2()");
            return t;
        }
    }

    public static String a(Context context, v vVar) {
        try {
            if (!new File(aw.a(context)).exists()) {
                return null;
            }
            File file = new File(aw.b(context, vVar.a(), vVar.b()));
            if (file.exists()) {
                return file.getAbsolutePath();
            }
            aw.a(context, file, vVar);
            return null;
        } catch (Throwable th) {
            ag.a(th, "IFactory", "isdowned");
            return null;
        }
    }

    public static void a(final Context context, final String str) {
        try {
            bb.b().a().submit(new Runnable() {
                public final void run() {
                    try {
                        aw.a(new ao(context, ay.b()), context, str);
                    } catch (Throwable th) {
                        ag.a(th, "InstanceFactory", "rollBack");
                    }
                }
            });
        } catch (Throwable th) {
            ag.a(th, "InstanceFactory", "rollBack");
        }
    }

    public static boolean a(Context context, av avVar, v vVar) {
        boolean z = avVar != null && avVar.c();
        try {
            if (!bc.a(vVar, avVar) || !bc.a(avVar) || !bc.a(context, z) || bc.a(context, avVar, vVar)) {
                return false;
            }
            aw.b(context, vVar.a());
            return true;
        } catch (Throwable th) {
            ag.a(th, "dDownLoad", "isNeedDownload()");
            return false;
        }
    }

    private static boolean a(bd bdVar) {
        return bdVar != null && bdVar.a() && bdVar.d;
    }

    public static void b(Context context, av avVar, v vVar) {
        if (avVar != null) {
            try {
                if (!TextUtils.isEmpty(avVar.a()) && !TextUtils.isEmpty(avVar.b()) && !TextUtils.isEmpty(avVar.d)) {
                    new au(context, avVar, vVar).a();
                }
            } catch (Throwable th) {
                ag.a(th, "IFactory", "dDownload()");
            }
        }
    }

    public static boolean b(Context context, v vVar) {
        try {
            if (!new File(aw.a(context)).exists()) {
                return false;
            }
            File file = new File(aw.b(context, vVar.a(), vVar.b()));
            if (file.exists()) {
                return true;
            }
            aw.a(context, file, vVar);
            return false;
        } catch (Throwable th) {
            ag.a(th, "IFactory", "isdowned");
            return false;
        }
    }

    private static bd c(Context context, v vVar) {
        if (context == null) {
            return null;
        }
        try {
            if (b(context, vVar)) {
                return bb.b().a(context, vVar);
            }
            return null;
        } catch (Throwable th) {
            ag.a(th, "IFactory", "gIns1");
            return null;
        }
    }
}
