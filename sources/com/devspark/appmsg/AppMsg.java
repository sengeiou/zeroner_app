package com.devspark.appmsg;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

public class AppMsg {
    public static final int LENGTH_LONG = 5000;
    public static final int LENGTH_SHORT = 3000;
    public static final int LENGTH_STICKY = -1;
    public static final int PRIORITY_HIGH = Integer.MAX_VALUE;
    public static final int PRIORITY_LOW = Integer.MIN_VALUE;
    public static final int PRIORITY_NORMAL = 0;
    public static final Style STYLE_ALERT = new Style(5000, R.color.alert);
    public static final Style STYLE_CONFIRM = new Style(3000, R.color.confirm);
    public static final Style STYLE_INFO = new Style(3000, R.color.info);
    private final Activity mActivity;
    private int mDuration = 3000;
    private boolean mFloating;
    Animation mInAnimation;
    private LayoutParams mLayoutParams;
    Animation mOutAnimation;
    private ViewGroup mParent;
    int mPriority = 0;
    private View mView;

    public static class Style {
        /* access modifiers changed from: private */
        public final int background;
        /* access modifiers changed from: private */
        public final int duration;

        public Style(int duration2, int resId) {
            this.duration = duration2;
            this.background = resId;
        }

        public int getDuration() {
            return this.duration;
        }

        public int getBackground() {
            return this.background;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Style)) {
                return false;
            }
            Style style = (Style) o;
            if (style.duration == this.duration && style.background == this.background) {
                return true;
            }
            return false;
        }
    }

    public AppMsg(Activity activity) {
        this.mActivity = activity;
    }

    public static AppMsg makeText(Activity context, CharSequence text, Style style) {
        return makeText(context, text, style, R.layout.app_msg);
    }

    public static AppMsg makeText(Activity context, CharSequence text, Style style, float textSize) {
        return makeText(context, text, style, R.layout.app_msg, textSize);
    }

    public static AppMsg makeText(Activity context, CharSequence text, Style style, int layoutId) {
        return makeText(context, text, style, ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(layoutId, null), true);
    }

    public static AppMsg makeText(Activity context, CharSequence text, Style style, int layoutId, float textSize) {
        return makeText(context, text, style, ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(layoutId, null), true, textSize);
    }

    public static AppMsg makeText(Activity context, CharSequence text, Style style, View customView) {
        return makeText(context, text, style, customView, false);
    }

    private static AppMsg makeText(Activity context, CharSequence text, Style style, View view, boolean floating) {
        return makeText(context, text, style, view, floating, 0.0f);
    }

    private static AppMsg makeText(Activity context, CharSequence text, Style style, View view, boolean floating, float textSize) {
        AppMsg result = new AppMsg(context);
        view.setBackgroundResource(style.background);
        TextView tv = (TextView) view.findViewById(16908299);
        if (textSize > 0.0f) {
            tv.setTextSize(textSize);
        }
        tv.setText(text);
        result.mView = view;
        result.mDuration = style.duration;
        result.mFloating = floating;
        return result;
    }

    public static AppMsg makeText(Activity context, int resId, Style style, View customView, boolean floating) {
        return makeText(context, context.getResources().getText(resId), style, customView, floating);
    }

    public static AppMsg makeText(Activity context, int resId, Style style) throws NotFoundException {
        return makeText(context, context.getResources().getText(resId), style);
    }

    public static AppMsg makeText(Activity context, int resId, Style style, int layoutId) throws NotFoundException {
        return makeText(context, context.getResources().getText(resId), style, layoutId);
    }

    public void show() {
        MsgManager.obtain(this.mActivity).add(this);
    }

    public boolean isShowing() {
        if (this.mFloating) {
            if (this.mView == null || this.mView.getParent() == null) {
                return false;
            }
            return true;
        } else if (this.mView.getVisibility() != 0) {
            return false;
        } else {
            return true;
        }
    }

    public void cancel() {
        MsgManager.obtain(this.mActivity).clearMsg(this);
    }

    public static void cancelAll() {
        MsgManager.clearAll();
    }

    public static void cancelAll(Activity activity) {
        MsgManager.release(activity);
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public void setView(View view) {
        this.mView = view;
    }

    public View getView() {
        return this.mView;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setText(int resId) {
        setText(this.mActivity.getText(resId));
    }

    public void setText(CharSequence s) {
        if (this.mView == null) {
            throw new RuntimeException("This AppMsg was not created with AppMsg.makeText()");
        }
        TextView tv = (TextView) this.mView.findViewById(16908299);
        if (tv == null) {
            throw new RuntimeException("This AppMsg was not created with AppMsg.makeText()");
        }
        tv.setText(s);
    }

    public LayoutParams getLayoutParams() {
        if (this.mLayoutParams == null) {
            this.mLayoutParams = new LayoutParams(-1, -2);
        }
        return this.mLayoutParams;
    }

    public AppMsg setLayoutParams(LayoutParams layoutParams) {
        this.mLayoutParams = layoutParams;
        return this;
    }

    public AppMsg setLayoutGravity(int gravity) {
        this.mLayoutParams = new FrameLayout.LayoutParams(-1, -2, gravity);
        return this;
    }

    public boolean isFloating() {
        return this.mFloating;
    }

    public void setFloating(boolean mFloating2) {
        this.mFloating = mFloating2;
    }

    public AppMsg setAnimation(int inAnimation, int outAnimation) {
        return setAnimation(AnimationUtils.loadAnimation(this.mActivity, inAnimation), AnimationUtils.loadAnimation(this.mActivity, outAnimation));
    }

    public AppMsg setAnimation(Animation inAnimation, Animation outAnimation) {
        this.mInAnimation = inAnimation;
        this.mOutAnimation = outAnimation;
        return this;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public void setPriority(int priority) {
        this.mPriority = priority;
    }

    public ViewGroup getParent() {
        return this.mParent;
    }

    public void setParent(ViewGroup parent) {
        this.mParent = parent;
    }

    public void setParent(int parentId) {
        setParent((ViewGroup) this.mActivity.findViewById(parentId));
    }
}
