package com.iwown.my_module.feedback.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.iwown.my_module.utility.Constants;

public class AndroidBug5497Workaround {
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private LayoutParams frameLayoutParams;
    private View mChildOfContent;
    private boolean mIsNeedStatusBarHeight;
    private int mNavigationBarHeight;
    private int mStatusBarHeight;
    private int usableHeightPrevious;

    public interface CallBack {
        void onSoftKeyboardHide();

        void onSoftKeyboardShow();
    }

    public static void assistActivity(Activity activity) {
        assistActivity(activity, false);
    }

    public static void assistActivity(Activity activity, CallBack callBack) {
        new AndroidBug5497Workaround(activity, callBack);
    }

    private AndroidBug5497Workaround(Activity activity, final CallBack callBack) {
        int i = 0;
        this.mChildOfContent = ((FrameLayout) activity.findViewById(16908290)).getChildAt(0);
        if (hasSoftKeys(activity.getWindowManager())) {
            i = getNavigationBarHeight(activity);
        }
        this.mNavigationBarHeight = i;
        this.mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                AndroidBug5497Workaround.this.refreshSoftMode(callBack);
            }
        });
        this.frameLayoutParams = (LayoutParams) this.mChildOfContent.getLayoutParams();
    }

    /* access modifiers changed from: private */
    public void refreshSoftMode(CallBack callBack) {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != this.usableHeightPrevious) {
            int usableHeightSansKeyboard = this.mChildOfContent.getRootView().getHeight() - this.mNavigationBarHeight;
            if (usableHeightSansKeyboard - usableHeightNow > usableHeightSansKeyboard / 4) {
                if (callBack != null) {
                    callBack.onSoftKeyboardShow();
                }
            } else if (callBack != null) {
                callBack.onSoftKeyboardHide();
            }
            this.mChildOfContent.requestLayout();
            this.usableHeightPrevious = usableHeightNow;
        }
    }

    public static void assistActivity(Activity activity, boolean isNeedStatusBarHeight) {
        new AndroidBug5497Workaround(activity, isNeedStatusBarHeight);
    }

    private AndroidBug5497Workaround(Activity activity, boolean isNeedStatusBarHeight) {
        int i;
        int i2 = 0;
        this.mChildOfContent = ((FrameLayout) activity.findViewById(16908290)).getChildAt(0);
        this.mIsNeedStatusBarHeight = isNeedStatusBarHeight;
        if (this.mIsNeedStatusBarHeight) {
            i = getStatusBarHeight();
        } else {
            i = 0;
        }
        this.mStatusBarHeight = i;
        if (hasSoftKeys(activity.getWindowManager())) {
            i2 = getNavigationBarHeight(activity);
        }
        this.mNavigationBarHeight = i2;
        this.mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                AndroidBug5497Workaround.this.possiblyResizeChildOfContent();
            }
        });
        this.frameLayoutParams = (LayoutParams) this.mChildOfContent.getLayoutParams();
    }

    /* access modifiers changed from: private */
    public void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != this.usableHeightPrevious) {
            int usableHeightSansKeyboard = this.mChildOfContent.getRootView().getHeight() - this.mNavigationBarHeight;
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > usableHeightSansKeyboard / 4) {
                this.frameLayoutParams.height = (usableHeightSansKeyboard - heightDifference) + this.mStatusBarHeight;
            } else {
                this.frameLayoutParams.height = usableHeightSansKeyboard;
            }
            this.mChildOfContent.requestLayout();
            this.usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        this.mChildOfContent.getWindowVisibleDisplayFrame(r);
        return r.bottom - r.top;
    }

    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int resourceId = res.getIdentifier(key, "dimen", Constants.APPSYSTEM);
        if (resourceId > 0) {
            return res.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    private int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", Constants.APPSYSTEM));
    }

    @TargetApi(17)
    private boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        return realWidth - displayMetrics.widthPixels > 0 || realHeight - displayMetrics.heightPixels > 0;
    }
}
