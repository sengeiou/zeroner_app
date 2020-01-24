package com.iwown.my_module.utility.flynstatusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.iwown.my_module.utility.Constants;

@TargetApi(21)
class EyesLollipop {
    EyesLollipop() {
    }

    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(statusColor);
        window.getDecorView().setSystemUiVisibility(0);
        View mChildView = ((ViewGroup) window.findViewById(16908290)).getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        window.addFlags(Integer.MIN_VALUE);
        if (hideStatusBarBackground) {
            window.clearFlags(67108864);
            window.setStatusBarColor(0);
            window.getDecorView().setSystemUiVisibility(1280);
        } else {
            window.addFlags(67108864);
            window.getDecorView().setSystemUiVisibility(0);
        }
        View mChildView = ((ViewGroup) window.findViewById(16908290)).getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    static void setStatusBarColorForCollapsingToolbar(final Activity activity, AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, final int statusColor) {
        Window window = activity.getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(0);
        ViewCompat.setOnApplyWindowInsetsListener(collapsingToolbarLayout, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return insets;
            }
        });
        View mChildView = ((ViewGroup) window.findViewById(16908290)).getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
        ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
        appBarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);
        collapsingToolbarLayout.setStatusBarScrimColor(statusColor);
        toolbar.setFitsSystemWindows(false);
        if (toolbar.getTag() == null) {
            LayoutParams lp = (LayoutParams) toolbar.getLayoutParams();
            int statusBarHeight = getStatusBarHeight(activity);
            lp.height += statusBarHeight;
            toolbar.setLayoutParams(lp);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.setTag(Boolean.valueOf(true));
        }
        appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            private static final int COLLAPSED = 1;
            private static final int EXPANDED = 0;
            private int appBarLayoutState;

            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    if (this.appBarLayoutState != 1) {
                        this.appBarLayoutState = 1;
                        EyesLollipop.setStatusBarColor(activity, statusColor);
                    }
                } else if (this.appBarLayoutState != 0) {
                    this.appBarLayoutState = 0;
                    EyesLollipop.translucentStatusBar(activity, true);
                }
            }
        });
    }

    static void setStatusBarWhiteForCollapsingToolbar(final Activity activity, AppBarLayout appBarLayout, final CollapsingToolbarLayout collapsingToolbarLayout, Toolbar toolbar, final int statusBarColor) {
        Window window = activity.getWindow();
        window.clearFlags(67108864);
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(0);
        ViewCompat.setOnApplyWindowInsetsListener(collapsingToolbarLayout, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return insets;
            }
        });
        View mChildView = ((ViewGroup) window.findViewById(16908290)).getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
        ((View) appBarLayout.getParent()).setFitsSystemWindows(false);
        appBarLayout.setFitsSystemWindows(false);
        toolbar.setFitsSystemWindows(false);
        if (toolbar.getTag() == null) {
            LayoutParams lp = (LayoutParams) toolbar.getLayoutParams();
            int statusBarHeight = getStatusBarHeight(activity);
            lp.height += statusBarHeight;
            toolbar.setLayoutParams(lp);
            toolbar.setPadding(toolbar.getPaddingLeft(), toolbar.getPaddingTop() + statusBarHeight, toolbar.getPaddingRight(), toolbar.getPaddingBottom());
            toolbar.setTag(Boolean.valueOf(true));
        }
        collapsingToolbarLayout.setFitsSystemWindows(false);
        collapsingToolbarLayout.getChildAt(0).setFitsSystemWindows(false);
        collapsingToolbarLayout.setStatusBarScrimColor(statusBarColor);
        appBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            private static final int COLLAPSED = 1;
            private static final int EXPANDED = 0;
            private int appBarLayoutState;

            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > collapsingToolbarLayout.getScrimVisibleHeightTrigger()) {
                    if (this.appBarLayoutState != 1) {
                        this.appBarLayoutState = 1;
                        if (!Eyes.MIUISetStatusBarLightMode(activity, true)) {
                            if (VERSION.SDK_INT >= 23) {
                                activity.getWindow().setBackgroundDrawableResource(17170445);
                                activity.getWindow().getDecorView().setSystemUiVisibility(9216);
                                activity.getWindow().setStatusBarColor(statusBarColor);
                            } else if (!Eyes.FlymeSetStatusBarLightMode(activity, true)) {
                                EyesLollipop.setStatusBarColor(activity, statusBarColor);
                            }
                        }
                    }
                } else if (this.appBarLayoutState != 0) {
                    this.appBarLayoutState = 0;
                    if (Eyes.MIUISetStatusBarLightMode(activity, false)) {
                        EyesLollipop.translucentStatusBar(activity, true);
                        return;
                    }
                    if (VERSION.SDK_INT >= 23) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(256);
                    } else if (Eyes.FlymeSetStatusBarLightMode(activity, true)) {
                    }
                    EyesLollipop.translucentStatusBar(activity, true);
                }
            }
        });
    }

    private static int getStatusBarHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", Constants.APPSYSTEM);
        if (resId > 0) {
            return context.getResources().getDimensionPixelOffset(resId);
        }
        return 0;
    }
}
