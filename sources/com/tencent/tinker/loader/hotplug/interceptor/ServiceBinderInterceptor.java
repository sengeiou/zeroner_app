package com.tencent.tinker.loader.hotplug.interceptor;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.IInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServiceBinderInterceptor extends Interceptor<IBinder> {
    private static final String TAG = "Tinker.SvcBndrIntrcptr";
    private static Method sGetServiceMethod;
    private static Field sSCacheField;
    private static Class<?> sServiceManagerClazz;
    private final Context mBaseContext;
    private final BinderInvocationHandler mBinderInvocationHandler;
    private final String mServiceName;

    public interface BinderInvocationHandler {
        Object invoke(Object obj, Method method, Object[] objArr) throws Throwable;
    }

    private static class FakeClientBinderHandler implements InvocationHandler {
        private final BinderInvocationHandler mBinderInvocationHandler;
        private final IBinder mOriginalClientBinder;

        FakeClientBinderHandler(IBinder originalClientBinder, BinderInvocationHandler binderInvocationHandler) {
            this.mOriginalClientBinder = originalClientBinder;
            this.mBinderInvocationHandler = binderInvocationHandler;
        }

        public Object invoke(Object fakeClientBinder, Method method, Object[] args) throws Throwable {
            String stubClassName;
            if (!"queryLocalInterface".equals(method.getName())) {
                return method.invoke(this.mOriginalClientBinder, args);
            }
            String itfName = this.mOriginalClientBinder.getInterfaceDescriptor();
            if (itfName.equals("android.app.IActivityManager")) {
                stubClassName = "android.app.ActivityManagerNative";
            } else {
                stubClassName = itfName + "$Stub";
            }
            IInterface originalInterface = (IInterface) ShareReflectUtil.findMethod(Class.forName(stubClassName), "asInterface", (Class<?>[]) new Class[]{IBinder.class}).invoke(null, new Object[]{this.mOriginalClientBinder});
            return ServiceBinderInterceptor.createProxy(ServiceBinderInterceptor.getAllInterfacesThroughDeriveChain(originalInterface.getClass()), new FakeInterfaceHandler(originalInterface, (IBinder) fakeClientBinder, this.mBinderInvocationHandler));
        }
    }

    private static class FakeInterfaceHandler implements InvocationHandler {
        private final BinderInvocationHandler mBinderInvocationHandler;
        private final IBinder mOriginalClientBinder;
        private final IInterface mOriginalInterface;

        FakeInterfaceHandler(IInterface originalInterface, IBinder originalClientBinder, BinderInvocationHandler binderInvocationHandler) {
            this.mOriginalInterface = originalInterface;
            this.mOriginalClientBinder = originalClientBinder;
            this.mBinderInvocationHandler = binderInvocationHandler;
        }

        public Object invoke(Object fakeIInterface, Method method, Object[] args) throws Throwable {
            if ("asBinder".equals(method.getName())) {
                return this.mOriginalClientBinder;
            }
            return this.mBinderInvocationHandler.invoke(this.mOriginalInterface, method, args);
        }
    }

    static {
        sServiceManagerClazz = null;
        sSCacheField = null;
        sGetServiceMethod = null;
        synchronized (ServiceBinderInterceptor.class) {
            if (sServiceManagerClazz == null) {
                try {
                    sServiceManagerClazz = Class.forName("android.os.ServiceManager");
                    sSCacheField = ShareReflectUtil.findField(sServiceManagerClazz, "sCache");
                    sGetServiceMethod = ShareReflectUtil.findMethod(sServiceManagerClazz, "getService", (Class<?>[]) new Class[]{String.class});
                } catch (Throwable thr) {
                    Log.e(TAG, "unexpected exception.", thr);
                }
            }
        }
    }

    public ServiceBinderInterceptor(Context context, String serviceName, BinderInvocationHandler binderInvocationHandler) {
        while (context != null && (context instanceof ContextWrapper)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        this.mBaseContext = context;
        this.mServiceName = serviceName;
        this.mBinderInvocationHandler = binderInvocationHandler;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public IBinder fetchTarget() throws Throwable {
        return (IBinder) sGetServiceMethod.invoke(null, new Object[]{this.mServiceName});
    }

    /* access modifiers changed from: protected */
    @NonNull
    public IBinder decorate(@Nullable IBinder target) throws Throwable {
        if (target != null) {
            return ITinkerHotplugProxy.class.isAssignableFrom(target.getClass()) ? target : (IBinder) createProxy(getAllInterfacesThroughDeriveChain(target.getClass()), new FakeClientBinderHandler(target, this.mBinderInvocationHandler));
        }
        throw new IllegalStateException("target is null.");
    }

    /* access modifiers changed from: protected */
    public void inject(@Nullable IBinder decorated) throws Throwable {
        ((Map) sSCacheField.get(null)).put(this.mServiceName, decorated);
        if (EnvConsts.ACTIVITY_MANAGER_SRVNAME.equals(this.mServiceName)) {
            fixAMSBinderCache(decorated);
        } else if (EnvConsts.PACKAGE_MANAGER_SRVNAME.equals(this.mServiceName)) {
            fixPMSBinderCache(this.mBaseContext, decorated);
        }
    }

    private static void fixAMSBinderCache(IBinder fakeBinder) throws Throwable {
        Object singletonObj;
        try {
            singletonObj = ShareReflectUtil.findField(Class.forName("android.app.ActivityManagerNative"), "gDefault").get(null);
        } catch (Throwable th) {
            singletonObj = ShareReflectUtil.findField(Class.forName("android.app.ActivityManager"), "IActivityManagerSingleton").get(null);
        }
        Field mInstanceField = ShareReflectUtil.findField(singletonObj, "mInstance");
        IInterface originalInterface = (IInterface) mInstanceField.get(singletonObj);
        if (originalInterface != null && !ITinkerHotplugProxy.class.isAssignableFrom(originalInterface.getClass())) {
            IInterface fakeInterface = fakeBinder.queryLocalInterface(fakeBinder.getInterfaceDescriptor());
            if (fakeInterface == null || !ITinkerHotplugProxy.class.isAssignableFrom(fakeInterface.getClass())) {
                throw new IllegalStateException("fakeBinder does not return fakeInterface, binder: " + fakeBinder + ", itf: " + fakeInterface);
            }
            mInstanceField.set(singletonObj, fakeInterface);
        }
    }

    private static void fixPMSBinderCache(Context context, IBinder fakeBinder) throws Throwable {
        Field sPackageManagerField = ShareReflectUtil.findField(Class.forName("android.app.ActivityThread"), "sPackageManager");
        IInterface originalInterface = (IInterface) sPackageManagerField.get(null);
        if (originalInterface != null && !ITinkerHotplugProxy.class.isAssignableFrom(originalInterface.getClass())) {
            IInterface fakeInterface = fakeBinder.queryLocalInterface(fakeBinder.getInterfaceDescriptor());
            if (fakeInterface == null || !ITinkerHotplugProxy.class.isAssignableFrom(fakeInterface.getClass())) {
                throw new IllegalStateException("fakeBinder does not return fakeInterface, binder: " + fakeBinder + ", itf: " + fakeInterface);
            }
            sPackageManagerField.set(null, fakeInterface);
        }
        Field mPMField = ShareReflectUtil.findField(Class.forName("android.app.ApplicationPackageManager"), "mPM");
        PackageManager pm = context.getPackageManager();
        IInterface originalInterface2 = (IInterface) mPMField.get(pm);
        if (originalInterface2 != null && !ITinkerHotplugProxy.class.isAssignableFrom(originalInterface2.getClass())) {
            IInterface fakeInterface2 = fakeBinder.queryLocalInterface(fakeBinder.getInterfaceDescriptor());
            if (fakeInterface2 == null || !ITinkerHotplugProxy.class.isAssignableFrom(fakeInterface2.getClass())) {
                throw new IllegalStateException("fakeBinder does not return fakeInterface, binder: " + fakeBinder + ", itf: " + fakeInterface2);
            }
            mPMField.set(pm, fakeInterface2);
        }
    }

    /* access modifiers changed from: private */
    public static <T> T createProxy(Class<?>[] itfs, InvocationHandler handler) {
        ClassLoader cl;
        int i = 0;
        Class[] clsArr = new Class[(itfs.length + 1)];
        System.arraycopy(itfs, i, clsArr, i, itfs.length);
        clsArr[itfs.length] = ITinkerHotplugProxy.class;
        try {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), clsArr, handler);
        } catch (Throwable th) {
            throw new RuntimeException("cl: " + cl, thr);
        }
    }

    /* access modifiers changed from: private */
    public static Class<?>[] getAllInterfacesThroughDeriveChain(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Set<Class<?>> itfs = new HashSet<>(10);
        while (!Object.class.equals(clazz)) {
            itfs.addAll(Arrays.asList(clazz.getInterfaces()));
            clazz = clazz.getSuperclass();
        }
        return (Class[]) itfs.toArray(new Class[itfs.size()]);
    }
}
