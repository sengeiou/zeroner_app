package com.iwown.my_module.utility.flynstatusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.my_module.R;
import com.iwown.my_module.utility.Constants;
import java.lang.reflect.Field;

@TargetApi(19)
class EyesKitKat {
    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    EyesKitKat() {
    }

    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(67108864);
        View mContentChild = ((ViewGroup) window.findViewById(16908290)).getChildAt(0);
        int statusBarHeight = getStatusBarHeight(activity);
        removeFakeStatusBarViewIfExist(activity);
        addFakeStatusBarView(activity, statusColor, statusBarHeight);
        addMarginTopToContentChild(mContentChild, statusBarHeight);
        if (mContentChild != null) {
            mContentChild.setFitsSystemWindows(false);
        }
        if (activity.findViewById(activity.getResources().getIdentifier("action_bar", "id", activity.getPackageName())) != null) {
            TypedValue typedValue = new TypedValue();
            if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)) {
                Eyes.setContentTopPadding(activity, TypedValue.complexToDimensionPixelSize(typedValue.data, activity.getResources().getDisplayMetrics()));
            }
        }
    }

    static void translucentStatusBar(Activity activity) {
        activity.getWindow().addFlags(67108864);
        View mContentChild = ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
        removeFakeStatusBarViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));
        if (mContentChild != null) {
            mContentChild.setFitsSystemWindows(false);
        }
    }

    static void setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, int statusColor) {
        Window window = activity.getWindow();
        window.addFlags(67108864);
        View mContentChild = ((ViewGroup) window.findViewById(16908290)).getChildAt(0);
        mContentChild.setFitsSystemWindows(false);
        ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
        appBarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);
        toolbar.setFitsSystemWindows(false);
        if (toolbar.getTag() == null) {
            LayoutParams lp = (LayoutParams) toolbar.getLayoutParams();
            int statusBarHeight = getStatusBarHeight(activity);
            lp.height += statusBarHeight;
            toolbar.setLayoutParams(lp);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.setTag(Boolean.valueOf(true));
        }
        int statusBarHeight2 = getStatusBarHeight(activity);
        removeFakeStatusBarViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, statusBarHeight2);
        final View statusView = addFakeStatusBarView(activity, statusColor, statusBarHeight2);
        Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        if (behavior == null || !(behavior instanceof AppBarLayout.Behavior)) {
            statusView.setAlpha(0.0f);
        } else if (Math.abs(((AppBarLayout.Behavior) behavior).getTopAndBottomOffset()) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
            statusView.setAlpha(1.0f);
        } else {
            statusView.setAlpha(0.0f);
        }
        appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    if (statusView.getAlpha() == 0.0f) {
                        statusView.animate().cancel();
                        statusView.animate().alpha(1.0f).setDuration(collapsingToolbarLayout.getScrimAnimationDuration()).start();
                    }
                } else if (statusView.getAlpha() == 1.0f) {
                    statusView.animate().cancel();
                    statusView.animate().alpha(0.0f).setDuration(collapsingToolbarLayout.getScrimAnimationDuration()).start();
                }
            }
        });
    }

    static void setStatusBarWhiteForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, int statusBarColor) {
        Window window = activity.getWindow();
        window.addFlags(67108864);
        ((ViewGroup) window.findViewById(16908290)).getChildAt(0).setFitsSystemWindows(false);
        ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
        appBarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);
        toolbar.setFitsSystemWindows(false);
        if (toolbar.getTag() == null) {
            LayoutParams lp = (LayoutParams) toolbar.getLayoutParams();
            int statusBarHeight = getStatusBarHeight(activity);
            lp.height += statusBarHeight;
            toolbar.setLayoutParams(lp);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.setTag(Boolean.valueOf(true));
        }
        int statusBarHeight2 = getStatusBarHeight(activity);
        int color = ViewCompat.MEASURED_STATE_MASK;
        try {
            Class cls = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            color = statusBarColor;
        } catch (ClassNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
        }
        try {
            Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            color = statusBarColor;
        } catch (NoSuchFieldException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        final View statusView = addFakeStatusBarView(activity, color, statusBarHeight2);
        Behavior behavior = ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).getBehavior();
        if (behavior == null || !(behavior instanceof AppBarLayout.Behavior)) {
            statusView.setAlpha(0.0f);
        } else {
            if (Math.abs(((AppBarLayout.Behavior) behavior).getTopAndBottomOffset()) > appBarLayout.getHeight() - collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                statusView.setAlpha(1.0f);
            } else {
                statusView.setAlpha(0.0f);
            }
        }
        final Activity activity2 = activity;
        final CollapsingToolbarLayout collapsingToolbarLayout2 = collapsingToolbarLayout;
        AnonymousClass2 r0 = new OnOffsetChangedListener() {
            private static final int COLLAPSED = 1;
            private static final int EXPANDED = 0;
            private int appBarLayoutState;

            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() - Eyes.getPxFromDp(activity2, 56.0f)) {
                    if (this.appBarLayoutState != 1) {
                        this.appBarLayoutState = 1;
                        if (Eyes.MIUISetStatusBarLightMode(activity2, true) || Eyes.FlymeSetStatusBarLightMode(activity2, true)) {
                        }
                        if (statusView.getAlpha() == 0.0f) {
                            statusView.animate().cancel();
                            statusView.animate().alpha(1.0f).setDuration(collapsingToolbarLayout2.getScrimAnimationDuration()).start();
                        }
                    }
                } else if (this.appBarLayoutState != 0) {
                    this.appBarLayoutState = 0;
                    if (Eyes.MIUISetStatusBarLightMode(activity2, false) || Eyes.FlymeSetStatusBarLightMode(activity2, false)) {
                    }
                    if (statusView.getAlpha() == 1.0f) {
                        statusView.animate().cancel();
                        statusView.animate().alpha(0.0f).setDuration(collapsingToolbarLayout2.getScrimAnimationDuration()).start();
                    }
                    EyesKitKat.translucentStatusBar(activity2);
                }
            }
        };
        appBarLayout.addOnOffsetChangedListener(r0);
    }

    private static void removeFakeStatusBarViewIfExist(Activity activity) {
        ViewGroup mDecorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeView = mDecorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            mDecorView.removeView(fakeView);
        }
    }

    private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
        ViewGroup mDecorView = (ViewGroup) activity.getWindow().getDecorView();
        View mStatusBarView = new View(activity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, statusBarHeight);
        layoutParams.gravity = 48;
        mStatusBarView.setLayoutParams(layoutParams);
        mStatusBarView.setBackgroundColor(statusBarColor);
        mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
        mDecorView.addView(mStatusBarView);
        return mStatusBarView;
    }

    private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild != null && !TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(TAG_MARGIN_ADDED);
        }
    }

    private static int getStatusBarHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", Constants.APPSYSTEM);
        if (resId > 0) {
            return context.getResources().getDimensionPixelOffset(resId);
        }
        return 0;
    }

    private static void removeMarginTopOfContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild != null && TAG_MARGIN_ADDED.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(null);
        }
    }
}
