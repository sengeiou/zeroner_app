package com.youzan.sdk.web.bridge;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import com.youzan.sdk.YouzanLog;

public abstract class AbsDispatcher {
    private SparseArray<Event> mEvents = new SparseArray<>(5);
    private Handler mHandler;

    protected static final class HandlerRunnable implements Runnable {
        private final Event mCallback;
        private final String mData;
        private final View mView;

        public HandlerRunnable(Event callback, View view, String data) {
            this.mCallback = callback;
            this.mView = view;
            this.mData = data;
        }

        public void run() {
            if (this.mCallback != null) {
                this.mCallback.call(this.mView, this.mData);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean dispatch(String str, String str2);

    public Event get(String methodName) {
        if (!TextUtils.isEmpty(methodName)) {
            return (Event) this.mEvents.get(methodName.hashCode());
        }
        return null;
    }

    public AbsDispatcher add(@NonNull Event event) {
        if (TextUtils.isEmpty(event.subscribe())) {
            YouzanLog.e("Event method is null");
        } else {
            this.mEvents.put(event.subscribe().hashCode(), event);
        }
        return this;
    }

    public void addAll(@NonNull SparseArray<Event> events) {
        this.mEvents = events;
    }

    public void runOnUi(Runnable runnable) {
        if (runnable != null) {
            if (this.mHandler == null) {
                this.mHandler = new Handler(Looper.getMainLooper());
            }
            this.mHandler.post(runnable);
        }
    }
}
