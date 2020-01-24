package com.alibaba.android.arouter.launcher;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.android.arouter.core.InstrumentationHook;
import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.exception.InitException;
import com.alibaba.android.arouter.exception.NoRouteFoundException;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.service.AutowiredService;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.facade.service.InterceptorService;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.alibaba.android.arouter.facade.template.ILogger;
import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import com.alibaba.android.arouter.utils.DefaultLogger;
import com.alibaba.android.arouter.utils.TextUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;

final class _ARouter {
    private static volatile boolean autoInject = false;
    private static volatile boolean debuggable = false;
    private static volatile ThreadPoolExecutor executor = DefaultPoolExecutor.getInstance();
    private static volatile boolean hasInit = false;
    private static volatile _ARouter instance = null;
    private static InterceptorService interceptorService;
    static ILogger logger = new DefaultLogger("ARouter::");
    private static Context mContext;
    private static volatile boolean monitorMode = false;

    private _ARouter() {
    }

    protected static synchronized boolean init(Application application) {
        synchronized (_ARouter.class) {
            mContext = application;
            LogisticsCenter.init(mContext, executor);
            logger.info("ARouter::", "ARouter init success!");
            hasInit = true;
        }
        return true;
    }

    static synchronized void destroy() {
        synchronized (_ARouter.class) {
            if (debuggable()) {
                hasInit = false;
                LogisticsCenter.suspend();
                logger.info("ARouter::", "ARouter destroy success!");
            } else {
                logger.error("ARouter::", "Destroy can be used in debug mode only!");
            }
        }
    }

    protected static _ARouter getInstance() {
        if (!hasInit) {
            throw new InitException("ARouterCore::Init::Invoke init(context) first!");
        }
        if (instance == null) {
            synchronized (_ARouter.class) {
                if (instance == null) {
                    instance = new _ARouter();
                }
            }
        }
        return instance;
    }

    static synchronized void openDebug() {
        synchronized (_ARouter.class) {
            debuggable = true;
            logger.info("ARouter::", "ARouter openDebug");
        }
    }

    static synchronized void openLog() {
        synchronized (_ARouter.class) {
            logger.showLog(true);
            logger.info("ARouter::", "ARouter openLog");
        }
    }

    @Deprecated
    static synchronized void enableAutoInject() {
        synchronized (_ARouter.class) {
            autoInject = true;
        }
    }

    @Deprecated
    static boolean canAutoInject() {
        return autoInject;
    }

    @Deprecated
    static void attachBaseContext() {
        Log.i("ARouter::", "ARouter start attachBaseContext");
        try {
            Class<?> mMainThreadClass = Class.forName("android.app.ActivityThread");
            Method getMainThread = mMainThreadClass.getDeclaredMethod("currentActivityThread", new Class[0]);
            getMainThread.setAccessible(true);
            Object currentActivityThread = getMainThread.invoke(null, new Object[0]);
            Field mInstrumentationField = mMainThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            mInstrumentationField.set(currentActivityThread, new InstrumentationHook());
            Log.i("ARouter::", "ARouter hook instrumentation success!");
        } catch (Exception ex) {
            Log.e("ARouter::", "ARouter hook instrumentation failed! [" + ex.getMessage() + "]");
        }
    }

    static synchronized void printStackTrace() {
        synchronized (_ARouter.class) {
            logger.showStackTrace(true);
            logger.info("ARouter::", "ARouter printStackTrace");
        }
    }

    static synchronized void setExecutor(ThreadPoolExecutor tpe) {
        synchronized (_ARouter.class) {
            executor = tpe;
        }
    }

    static synchronized void monitorMode() {
        synchronized (_ARouter.class) {
            monitorMode = true;
            logger.info("ARouter::", "ARouter monitorMode on");
        }
    }

    static boolean isMonitorMode() {
        return monitorMode;
    }

    static boolean debuggable() {
        return debuggable;
    }

    static void setLogger(ILogger userLogger) {
        if (userLogger != null) {
            logger = userLogger;
        }
    }

    static void inject(Object thiz) {
        AutowiredService autowiredService = (AutowiredService) ARouter.getInstance().build("/arouter/service/autowired").navigation();
        if (autowiredService != null) {
            autowiredService.autowire(thiz);
        }
    }

    /* access modifiers changed from: protected */
    public Postcard build(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new HandlerException("ARouter::Parameter is invalid!");
        }
        PathReplaceService pService = (PathReplaceService) ARouter.getInstance().navigation(PathReplaceService.class);
        if (pService != null) {
            path = pService.forString(path);
        }
        return build(path, extractGroup(path));
    }

    /* access modifiers changed from: protected */
    public Postcard build(Uri uri) {
        if (uri == null || TextUtils.isEmpty(uri.toString())) {
            throw new HandlerException("ARouter::Parameter invalid!");
        }
        PathReplaceService pService = (PathReplaceService) ARouter.getInstance().navigation(PathReplaceService.class);
        if (pService != null) {
            uri = pService.forUri(uri);
        }
        return new Postcard(uri.getPath(), extractGroup(uri.getPath()), uri, null);
    }

    /* access modifiers changed from: protected */
    public Postcard build(String path, String group) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(group)) {
            throw new HandlerException("ARouter::Parameter is invalid!");
        }
        PathReplaceService pService = (PathReplaceService) ARouter.getInstance().navigation(PathReplaceService.class);
        if (pService != null) {
            path = pService.forString(path);
        }
        return new Postcard(path, group);
    }

    private String extractGroup(String path) {
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new HandlerException("ARouter::Extract the default group failed, the path must be start with '/' and contain more than 2 '/'!");
        }
        try {
            String defaultGroup = path.substring(1, path.indexOf("/", 1));
            if (!TextUtils.isEmpty(defaultGroup)) {
                return defaultGroup;
            }
            throw new HandlerException("ARouter::Extract the default group failed! There's nothing between 2 '/'!");
        } catch (Exception e) {
            logger.warning("ARouter::", "Failed to extract default group! " + e.getMessage());
            return null;
        }
    }

    static void afterInit() {
        interceptorService = (InterceptorService) ARouter.getInstance().build("/arouter/service/interceptor").navigation();
    }

    /* access modifiers changed from: protected */
    public <T> T navigation(Class<? extends T> service) {
        try {
            Postcard postcard = LogisticsCenter.buildProvider(service.getName());
            if (postcard == null) {
                postcard = LogisticsCenter.buildProvider(service.getSimpleName());
            }
            LogisticsCenter.completion(postcard);
            return postcard.getProvider();
        } catch (NoRouteFoundException ex) {
            logger.warning("ARouter::", ex.getMessage());
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Object navigation(Context context, Postcard postcard, int requestCode, NavigationCallback callback) {
        try {
            LogisticsCenter.completion(postcard);
            if (callback != null) {
                callback.onFound(postcard);
            }
            if (postcard.isGreenChannel()) {
                return _navigation(context, postcard, requestCode, callback);
            }
            final Context context2 = context;
            final int i = requestCode;
            final NavigationCallback navigationCallback = callback;
            final Postcard postcard2 = postcard;
            interceptorService.doInterceptions(postcard, new InterceptorCallback() {
                public void onContinue(Postcard postcard) {
                    _ARouter.this._navigation(context2, postcard, i, navigationCallback);
                }

                public void onInterrupt(Throwable exception) {
                    if (navigationCallback != null) {
                        navigationCallback.onInterrupt(postcard2);
                    }
                    _ARouter.logger.info("ARouter::", "Navigation failed, termination by interceptor : " + exception.getMessage());
                }
            });
            return null;
        } catch (NoRouteFoundException ex) {
            logger.warning("ARouter::", ex.getMessage());
            if (debuggable()) {
                Toast.makeText(mContext, "There's no route matched!\n Path = [" + postcard.getPath() + "]\n Group = [" + postcard.getGroup() + "]", 1).show();
            }
            if (callback != null) {
                callback.onLost(postcard);
            } else {
                DegradeService degradeService = (DegradeService) ARouter.getInstance().navigation(DegradeService.class);
                if (degradeService != null) {
                    degradeService.onLost(context, postcard);
                }
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public Object _navigation(Context context, Postcard postcard, int requestCode, NavigationCallback callback) {
        final Context currentContext;
        if (context == null) {
            currentContext = mContext;
        } else {
            currentContext = context;
        }
        switch (postcard.getType()) {
            case ACTIVITY:
                final Intent intent = new Intent(currentContext, postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                int flags = postcard.getFlags();
                if (-1 != flags) {
                    intent.setFlags(flags);
                } else if (!(currentContext instanceof Activity)) {
                    intent.setFlags(268435456);
                }
                final int i = requestCode;
                final Postcard postcard2 = postcard;
                final NavigationCallback navigationCallback = callback;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (i > 0) {
                            ActivityCompat.startActivityForResult((Activity) currentContext, intent, i, postcard2.getOptionsBundle());
                        } else {
                            ActivityCompat.startActivity(currentContext, intent, postcard2.getOptionsBundle());
                        }
                        if (!(postcard2.getEnterAnim() == 0 && postcard2.getExitAnim() == 0) && (currentContext instanceof Activity)) {
                            ((Activity) currentContext).overridePendingTransition(postcard2.getEnterAnim(), postcard2.getExitAnim());
                        }
                        if (navigationCallback != null) {
                            navigationCallback.onArrival(postcard2);
                        }
                    }
                });
                return null;
            case PROVIDER:
                return postcard.getProvider();
            case BOARDCAST:
            case CONTENT_PROVIDER:
            case FRAGMENT:
                try {
                    Object instance2 = postcard.getDestination().getConstructor(new Class[0]).newInstance(new Object[0]);
                    if (instance2 instanceof Fragment) {
                        ((Fragment) instance2).setArguments(postcard.getExtras());
                        return instance2;
                    } else if (!(instance2 instanceof android.support.v4.app.Fragment)) {
                        return instance2;
                    } else {
                        ((android.support.v4.app.Fragment) instance2).setArguments(postcard.getExtras());
                        return instance2;
                    }
                } catch (Exception ex) {
                    logger.error("ARouter::", "Fetch fragment instance error, " + TextUtils.formatStackTrace(ex.getStackTrace()));
                    break;
                }
        }
        return null;
    }
}
