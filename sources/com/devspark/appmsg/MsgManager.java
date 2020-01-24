package com.devspark.appmsg;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.WeakHashMap;

class MsgManager extends Handler implements Comparator<AppMsg> {
    private static final int MESSAGE_ADD_VIEW = -1040157475;
    private static final int MESSAGE_DISPLAY = 794631;
    private static final int MESSAGE_REMOVE = -1040155167;
    private static WeakHashMap<Activity, MsgManager> sManagers;
    private static ReleaseCallbacks sReleaseCallbacks;
    private final Queue<AppMsg> msgQueue = new PriorityQueue(1, this);
    private final Queue<AppMsg> stickyQueue = new LinkedList();

    private static class OutAnimationListener implements AnimationListener {
        private final AppMsg appMsg;

        private OutAnimationListener(AppMsg appMsg2) {
            this.appMsg = appMsg2;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            final View view = this.appMsg.getView();
            if (this.appMsg.isFloating()) {
                final ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.post(new Runnable() {
                        public void run() {
                            parent.removeView(view);
                        }
                    });
                    return;
                }
                return;
            }
            view.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    interface ReleaseCallbacks {
        void register(Application application);
    }

    @TargetApi(14)
    static class ReleaseCallbacksIcs implements ActivityLifecycleCallbacks, ReleaseCallbacks {
        private WeakReference<Application> mLastApp;

        ReleaseCallbacksIcs() {
        }

        public void register(Application app) {
            if (this.mLastApp == null || this.mLastApp.get() != app) {
                this.mLastApp = new WeakReference<>(app);
                app.registerActivityLifecycleCallbacks(this);
            }
        }

        public void onActivityDestroyed(Activity activity) {
            MsgManager.release(activity);
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }
    }

    private MsgManager() {
    }

    static synchronized MsgManager obtain(Activity activity) {
        MsgManager manager;
        synchronized (MsgManager.class) {
            if (sManagers == null) {
                sManagers = new WeakHashMap<>(1);
            }
            manager = (MsgManager) sManagers.get(activity);
            if (manager == null) {
                manager = new MsgManager();
                ensureReleaseOnDestroy(activity);
                sManagers.put(activity, manager);
            }
        }
        return manager;
    }

    static void ensureReleaseOnDestroy(Activity activity) {
        if (VERSION.SDK_INT >= 14) {
            if (sReleaseCallbacks == null) {
                sReleaseCallbacks = new ReleaseCallbacksIcs();
            }
            sReleaseCallbacks.register(activity.getApplication());
        }
    }

    static synchronized void release(Activity activity) {
        synchronized (MsgManager.class) {
            if (sManagers != null) {
                MsgManager manager = (MsgManager) sManagers.remove(activity);
                if (manager != null) {
                    manager.clearAllMsg();
                }
            }
        }
    }

    static synchronized void clearAll() {
        synchronized (MsgManager.class) {
            if (sManagers != null) {
                Iterator<MsgManager> iterator = sManagers.values().iterator();
                while (iterator.hasNext()) {
                    MsgManager manager = (MsgManager) iterator.next();
                    if (manager != null) {
                        manager.clearAllMsg();
                    }
                    iterator.remove();
                }
                sManagers.clear();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void add(AppMsg appMsg) {
        this.msgQueue.add(appMsg);
        if (appMsg.mInAnimation == null) {
            appMsg.mInAnimation = AnimationUtils.loadAnimation(appMsg.getActivity(), 17432576);
        }
        if (appMsg.mOutAnimation == null) {
            appMsg.mOutAnimation = AnimationUtils.loadAnimation(appMsg.getActivity(), 17432577);
        }
        displayMsg();
    }

    /* access modifiers changed from: 0000 */
    public void clearMsg(AppMsg appMsg) {
        if (this.msgQueue.contains(appMsg) || this.stickyQueue.contains(appMsg)) {
            removeMessages(MESSAGE_DISPLAY, appMsg);
            removeMessages(MESSAGE_ADD_VIEW, appMsg);
            removeMessages(MESSAGE_REMOVE, appMsg);
            this.msgQueue.remove(appMsg);
            this.stickyQueue.remove(appMsg);
            removeMsg(appMsg);
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearAllMsg() {
        removeMessages(MESSAGE_DISPLAY);
        removeMessages(MESSAGE_ADD_VIEW);
        removeMessages(MESSAGE_REMOVE);
        clearShowing();
        this.msgQueue.clear();
        this.stickyQueue.clear();
    }

    /* access modifiers changed from: 0000 */
    public void clearShowing() {
        Collection<AppMsg> showing = new HashSet<>();
        obtainShowing(this.msgQueue, showing);
        obtainShowing(this.stickyQueue, showing);
        for (AppMsg msg : showing) {
            clearMsg(msg);
        }
    }

    static void obtainShowing(Collection<AppMsg> from, Collection<AppMsg> appendTo) {
        for (AppMsg msg : from) {
            if (msg.isShowing()) {
                appendTo.add(msg);
            }
        }
    }

    private void displayMsg() {
        if (!this.msgQueue.isEmpty()) {
            AppMsg appMsg = (AppMsg) this.msgQueue.peek();
            if (!appMsg.isShowing()) {
                Message msg = obtainMessage(MESSAGE_ADD_VIEW);
                msg.obj = appMsg;
                sendMessage(msg);
            } else if (appMsg.getDuration() != -1) {
                sendMessageDelayed(obtainMessage(MESSAGE_DISPLAY), ((long) appMsg.getDuration()) + appMsg.mInAnimation.getDuration() + appMsg.mOutAnimation.getDuration());
            }
        }
    }

    private void removeMsg(AppMsg appMsg) {
        clearMsg(appMsg);
        View view = appMsg.getView();
        if (((ViewGroup) view.getParent()) != null) {
            appMsg.mOutAnimation.setAnimationListener(new OutAnimationListener(appMsg));
            view.clearAnimation();
            view.startAnimation(appMsg.mOutAnimation);
        }
        sendMessage(obtainMessage(MESSAGE_DISPLAY));
    }

    private void addMsgToView(AppMsg appMsg) {
        View view = appMsg.getView();
        if (view.getParent() == null) {
            ViewGroup targetParent = appMsg.getParent();
            LayoutParams params = appMsg.getLayoutParams();
            if (targetParent != null) {
                targetParent.addView(view, params);
            } else {
                appMsg.getActivity().addContentView(view, params);
            }
        }
        view.clearAnimation();
        view.startAnimation(appMsg.mInAnimation);
        if (view.getVisibility() != 0) {
            view.setVisibility(0);
        }
        int duration = appMsg.getDuration();
        if (duration != -1) {
            Message msg = obtainMessage(MESSAGE_REMOVE);
            msg.obj = appMsg;
            sendMessageDelayed(msg, (long) duration);
            return;
        }
        this.stickyQueue.add(this.msgQueue.poll());
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_ADD_VIEW /*-1040157475*/:
                addMsgToView((AppMsg) msg.obj);
                return;
            case MESSAGE_REMOVE /*-1040155167*/:
                removeMsg((AppMsg) msg.obj);
                return;
            case MESSAGE_DISPLAY /*794631*/:
                displayMsg();
                return;
            default:
                super.handleMessage(msg);
                return;
        }
    }

    public int compare(AppMsg lhs, AppMsg rhs) {
        return inverseCompareInt(lhs.mPriority, rhs.mPriority);
    }

    static int inverseCompareInt(int lhs, int rhs) {
        if (lhs < rhs) {
            return 1;
        }
        return lhs == rhs ? 0 : -1;
    }
}
