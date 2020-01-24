package com.tapadoo.alerter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.tapadoo.android.R;
import java.lang.ref.WeakReference;

public final class Alerter {
    private static WeakReference<Activity> activityWeakReference;
    private Alert alert;

    private Alerter() {
    }

    public static Alerter create(@NonNull Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null!");
        }
        Alerter alerter = new Alerter();
        clearCurrent(activity);
        alerter.setActivity(activity);
        alerter.setAlert(new Alert(activity));
        return alerter;
    }

    public static void clearCurrent(@NonNull Activity activity) {
        if (activity != null) {
            try {
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                for (int i = 0; i < decorView.getChildCount(); i++) {
                    Alert childView = decorView.getChildAt(i) instanceof Alert ? (Alert) decorView.getChildAt(i) : null;
                    if (!(childView == null || childView.getWindowToken() == null)) {
                        ViewCompat.animate(childView).alpha(0.0f).withEndAction(getRemoveViewRunnable(childView));
                    }
                }
            } catch (Exception ex) {
                Log.e(Alerter.class.getClass().getSimpleName(), Log.getStackTraceString(ex));
            }
        }
    }

    public static void hide() {
        if (activityWeakReference != null && activityWeakReference.get() != null) {
            clearCurrent((Activity) activityWeakReference.get());
        }
    }

    @NonNull
    private static Runnable getRemoveViewRunnable(final Alert childView) {
        return new Runnable() {
            public void run() {
                try {
                    ((ViewGroup) childView.getParent()).removeView(childView);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), Log.getStackTraceString(e));
                }
            }
        };
    }

    public static boolean isShowing() {
        if (activityWeakReference == null || activityWeakReference.get() == null) {
            return false;
        }
        return ((Activity) activityWeakReference.get()).findViewById(R.id.flAlertBackground) != null;
    }

    public Alert show() {
        if (getActivityWeakReference() != null) {
            ((Activity) getActivityWeakReference().get()).runOnUiThread(new Runnable() {
                public void run() {
                    ViewGroup decorView = Alerter.this.getActivityDecorView();
                    if (decorView != null && Alerter.this.getAlert().getParent() == null) {
                        decorView.addView(Alerter.this.getAlert());
                    }
                }
            });
        }
        return getAlert();
    }

    public Alerter setTitle(@StringRes int titleId) {
        if (getAlert() != null) {
            getAlert().setTitle(titleId);
        }
        return this;
    }

    public Alerter setTitle(String title) {
        if (getAlert() != null) {
            getAlert().setTitle(title);
        }
        return this;
    }

    public Alerter setTitleTypeface(@NonNull Typeface typeface) {
        if (getAlert() != null) {
            getAlert().setTitleTypeface(typeface);
        }
        return this;
    }

    public Alerter setTitleAppearance(@StyleRes int textAppearance) {
        if (getAlert() != null) {
            getAlert().setTitleAppearance(textAppearance);
        }
        return this;
    }

    public Alerter setContentGravity(int gravity) {
        if (getAlert() != null) {
            getAlert().setContentGravity(gravity);
        }
        return this;
    }

    public Alerter setText(@StringRes int textId) {
        if (getAlert() != null) {
            getAlert().setText(textId);
        }
        return this;
    }

    public Alerter setText(String text) {
        if (getAlert() != null) {
            getAlert().setText(text);
        }
        return this;
    }

    public Alerter setTextTypeface(@NonNull Typeface typeface) {
        if (getAlert() != null) {
            getAlert().setTextTypeface(typeface);
        }
        return this;
    }

    public Alerter setTextAppearance(@StyleRes int textAppearance) {
        if (getAlert() != null) {
            getAlert().setTextAppearance(textAppearance);
        }
        return this;
    }

    public Alerter setBackgroundColorInt(@ColorInt int colorInt) {
        if (getAlert() != null) {
            getAlert().setAlertBackgroundColor(colorInt);
        }
        return this;
    }

    public Alerter setBackgroundColorRes(@ColorRes int colorResId) {
        if (!(getAlert() == null || getActivityWeakReference() == null)) {
            getAlert().setAlertBackgroundColor(ContextCompat.getColor((Context) getActivityWeakReference().get(), colorResId));
        }
        return this;
    }

    public Alerter setBackgroundDrawable(Drawable drawable) {
        if (getAlert() != null) {
            getAlert().setAlertBackgroundDrawable(drawable);
        }
        return this;
    }

    public Alerter setBackgroundResource(@DrawableRes int drawableResId) {
        if (getAlert() != null) {
            getAlert().setAlertBackgroundResource(drawableResId);
        }
        return this;
    }

    public Alerter setIcon(@DrawableRes int iconId) {
        if (getAlert() != null) {
            getAlert().setIcon(iconId);
        }
        return this;
    }

    public Alerter setIcon(@NonNull Bitmap bitmap) {
        if (getAlert() != null) {
            getAlert().setIcon(bitmap);
        }
        return this;
    }

    public Alerter setIcon(@NonNull Drawable drawable) {
        if (getAlert() != null) {
            getAlert().setIcon(drawable);
        }
        return this;
    }

    public Alerter hideIcon() {
        if (getAlert() != null) {
            getAlert().getIcon().setVisibility(8);
        }
        return this;
    }

    public Alerter setOnClickListener(@NonNull OnClickListener onClickListener) {
        if (getAlert() != null) {
            getAlert().setOnClickListener(onClickListener);
        }
        return this;
    }

    public Alerter setDuration(@NonNull long milliseconds) {
        if (getAlert() != null) {
            getAlert().setDuration(milliseconds);
        }
        return this;
    }

    public Alerter enableIconPulse(boolean pulse) {
        if (getAlert() != null) {
            getAlert().pulseIcon(pulse);
        }
        return this;
    }

    public Alerter showIcon(boolean showIcon) {
        if (getAlert() != null) {
            getAlert().showIcon(showIcon);
        }
        return this;
    }

    public Alerter enableInfiniteDuration(boolean infiniteDuration) {
        if (getAlert() != null) {
            getAlert().setEnableInfiniteDuration(infiniteDuration);
        }
        return this;
    }

    public Alerter setOnShowListener(@NonNull OnShowAlertListener listener) {
        if (getAlert() != null) {
            getAlert().setOnShowListener(listener);
        }
        return this;
    }

    public Alerter setOnHideListener(@NonNull OnHideAlertListener listener) {
        if (getAlert() != null) {
            getAlert().setOnHideListener(listener);
        }
        return this;
    }

    public Alerter enableSwipeToDismiss() {
        if (getAlert() != null) {
            getAlert().enableSwipeToDismiss();
        }
        return this;
    }

    public Alerter enableVibration(boolean enable) {
        if (getAlert() != null) {
            getAlert().setVibrationEnabled(enable);
        }
        return this;
    }

    public Alerter disableOutsideTouch() {
        if (getAlert() != null) {
            getAlert().disableOutsideTouch();
        }
        return this;
    }

    public Alerter enableProgress(boolean enable) {
        if (getAlert() != null) {
            getAlert().setEnableProgress(enable);
        }
        return this;
    }

    /* access modifiers changed from: private */
    public Alert getAlert() {
        return this.alert;
    }

    private void setAlert(Alert alert2) {
        this.alert = alert2;
    }

    @Nullable
    private WeakReference<Activity> getActivityWeakReference() {
        return activityWeakReference;
    }

    /* access modifiers changed from: private */
    @Nullable
    public ViewGroup getActivityDecorView() {
        if (getActivityWeakReference() == null || getActivityWeakReference().get() == null) {
            return null;
        }
        return (ViewGroup) ((Activity) getActivityWeakReference().get()).getWindow().getDecorView();
    }

    private void setActivity(@NonNull Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
}
