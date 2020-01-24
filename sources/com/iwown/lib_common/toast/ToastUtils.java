package com.iwown.lib_common.toast;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {
    private static boolean isJumpWhenMore;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Toast sToast;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(boolean isJumpWhenMore2) {
        isJumpWhenMore = isJumpWhenMore2;
    }

    public static void showShortToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(text, 0);
            }
        });
    }

    public static void showShortToastSafe(@StringRes final int resId) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(resId, 0);
            }
        });
    }

    public static void showShortToastSafe(@StringRes final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(resId, 0, args);
            }
        });
    }

    public static void showShortToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(format, 0, args);
            }
        });
    }

    public static void showLongToastSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(text, 1);
            }
        });
    }

    public static void showLongToastSafe(@StringRes final int resId) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(resId, 1);
            }
        });
    }

    public static void showLongToastSafe(@StringRes final int resId, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(resId, 1, args);
            }
        });
    }

    public static void showLongToastSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            public void run() {
                ToastUtils.showToast(format, 1, args);
            }
        });
    }

    public static void showShortToast(CharSequence text) {
        showToast(text, 0);
    }

    public static void showShortToast(@StringRes int resId) {
        showToast(resId, 0);
    }

    public static void showShortToast(@StringRes int resId, Object... args) {
        showToast(resId, 0, args);
    }

    public static void showShortToast(String format, Object... args) {
        showToast(format, 0, args);
    }

    public static void showLongToast(CharSequence text) {
        showToast(text, 1);
    }

    public static void showLongToast(@StringRes int resId) {
        showToast(resId, 1);
    }

    public static void showLongToast(@StringRes int resId, Object... args) {
        showToast(resId, 1, args);
    }

    public static void showLongToast(String format, Object... args) {
        showToast(format, 1, args);
    }

    /* access modifiers changed from: private */
    public static void showToast(@StringRes int resId, int duration) {
        showToast((CharSequence) Utils.getContext().getResources().getText(resId).toString(), duration);
    }

    /* access modifiers changed from: private */
    public static void showToast(@StringRes int resId, int duration, Object... args) {
        showToast((CharSequence) String.format(Utils.getContext().getResources().getString(resId), args), duration);
    }

    /* access modifiers changed from: private */
    public static void showToast(String format, int duration, Object... args) {
        showToast((CharSequence) String.format(format, args), duration);
    }

    /* access modifiers changed from: private */
    public static void showToast(CharSequence text, int duration) {
        if (isJumpWhenMore) {
            cancelToast();
        }
        if (sToast == null) {
            sToast = Toast.makeText(Utils.getContext(), text, duration);
            ((TextView) sToast.getView().findViewById(16908299)).setTextSize(18.0f);
            sToast.setGravity(17, 0, 0);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
