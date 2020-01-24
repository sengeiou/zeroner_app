package com.inuker.bluetooth.library.utils.proxy;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyInvocationHandler implements InvocationHandler, ProxyInterceptor, Callback {
    private Handler handler;
    private ProxyInterceptor interceptor;
    private boolean postUI;
    private Object subject;
    private boolean weakRef;

    public ProxyInvocationHandler(Object subject2) {
        this(subject2, null);
    }

    public ProxyInvocationHandler(Object subject2, ProxyInterceptor interceptor2) {
        this(subject2, interceptor2, false);
    }

    public ProxyInvocationHandler(Object subject2, ProxyInterceptor interceptor2, boolean weakRef2) {
        this(subject2, interceptor2, weakRef2, false);
    }

    public ProxyInvocationHandler(Object subject2, ProxyInterceptor interceptor2, boolean weakRef2, boolean postUI2) {
        this.weakRef = weakRef2;
        this.interceptor = interceptor2;
        this.postUI = postUI2;
        this.subject = getObject(subject2);
        this.handler = new Handler(Looper.getMainLooper(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object subject2 = getObject();
        if (onIntercept(subject2, method, args)) {
            return null;
        }
        ProxyBulk bulk = new ProxyBulk(subject2, method, args);
        return this.postUI ? postSafeInvoke(bulk) : safeInvoke(bulk);
    }

    public boolean onIntercept(Object object, Method method, Object[] args) {
        if (this.interceptor != null) {
            try {
                return this.interceptor.onIntercept(object, method, args);
            } catch (Exception e) {
                BluetoothLog.e((Throwable) e);
            }
        }
        return false;
    }

    private Object getObject(Object object) {
        return this.weakRef ? new WeakReference(object) : object;
    }

    private Object getObject() {
        if (this.weakRef) {
            return ((WeakReference) this.subject).get();
        }
        return this.subject;
    }

    private Object postSafeInvoke(ProxyBulk bulk) {
        this.handler.obtainMessage(0, bulk).sendToTarget();
        return null;
    }

    private Object safeInvoke(ProxyBulk bulk) {
        try {
            return bulk.safeInvoke();
        } catch (Throwable th) {
            return null;
        }
    }

    public boolean handleMessage(Message msg) {
        ProxyBulk.safeInvoke(msg.obj);
        return true;
    }
}
