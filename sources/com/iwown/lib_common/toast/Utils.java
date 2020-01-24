package com.iwown.lib_common.toast;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;

public class Utils {
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Context context2) {
        context = context2.getApplicationContext();
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }

    @NonNull
    public static Activity getActivity(View view) {
        for (Context context2 = view.getContext(); context2 instanceof ContextWrapper; context2 = ((ContextWrapper) context2).getBaseContext()) {
            if (context2 instanceof Activity) {
                return (Activity) context2;
            }
        }
        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    public static String getString(@StringRes int id) {
        return context.getResources().getString(id);
    }

    public static <T> T checkNotNull(T obj) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException();
    }
}
