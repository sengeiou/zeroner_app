package com.tencent.tinker.loader.hotplug.interceptor;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.tencent.tinker.loader.shareutil.ShareReflectUtil;
import java.lang.reflect.Field;

public class HandlerMessageInterceptor extends Interceptor<Callback> {
    private static Field sMCallbackField;
    private final MessageHandler mMessageHandler;
    private final Handler mTarget;

    private static class CallbackWrapper implements Callback, ITinkerHotplugProxy {
        private volatile boolean mIsInHandleMethod = false;
        private final MessageHandler mMessageHandler;
        private final Callback mOrigCallback;

        CallbackWrapper(MessageHandler messageHandler, Callback origCallback) {
            this.mMessageHandler = messageHandler;
            this.mOrigCallback = origCallback;
        }

        public boolean handleMessage(Message message) {
            boolean result = false;
            if (this.mIsInHandleMethod) {
                return false;
            }
            this.mIsInHandleMethod = true;
            if (this.mMessageHandler.handleMessage(message)) {
                result = true;
            } else if (this.mOrigCallback != null) {
                result = this.mOrigCallback.handleMessage(message);
            }
            this.mIsInHandleMethod = false;
            return result;
        }
    }

    public interface MessageHandler {
        boolean handleMessage(Message message);
    }

    static {
        sMCallbackField = null;
        synchronized (HandlerMessageInterceptor.class) {
            if (sMCallbackField == null) {
                try {
                    sMCallbackField = ShareReflectUtil.findField(Handler.class, "mCallback");
                } catch (Throwable th) {
                }
            }
        }
    }

    public HandlerMessageInterceptor(Handler target, MessageHandler messageHandler) {
        this.mTarget = target;
        this.mMessageHandler = messageHandler;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public Callback fetchTarget() throws Throwable {
        return (Callback) sMCallbackField.get(this.mTarget);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Callback decorate(@Nullable Callback callback) throws Throwable {
        return (callback == null || !ITinkerHotplugProxy.class.isAssignableFrom(callback.getClass())) ? new CallbackWrapper(this.mMessageHandler, callback) : callback;
    }

    /* access modifiers changed from: protected */
    public void inject(@Nullable Callback decorated) throws Throwable {
        sMCallbackField.set(this.mTarget, decorated);
    }
}
