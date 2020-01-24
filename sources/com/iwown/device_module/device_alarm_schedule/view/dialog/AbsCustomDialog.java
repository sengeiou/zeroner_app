package com.iwown.device_module.device_alarm_schedule.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.socks.library.KLog;

public abstract class AbsCustomDialog extends Dialog {
    public Window mWindow;
    public int margin;
    private boolean outTouschCancel;

    public abstract int getLayoutResID();

    public abstract void initData();

    public abstract void initListener();

    public abstract void initView();

    public AbsCustomDialog(Context context) {
        super(context);
        this.outTouschCancel = true;
    }

    public AbsCustomDialog(Context context, boolean isOutCancel) {
        this(context);
        this.outTouschCancel = isOutCancel;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        KLog.e("outTouschCancle " + this.outTouschCancel);
        setCancelable(getCancelable());
        setCanceledOnTouchOutside(this.outTouschCancel);
        setContentView(getLayoutResID());
        this.mWindow = getWindow();
        this.mWindow.setBackgroundDrawableResource(getBackgroundDrawableResourceId());
        if (getWindowAnimationsResId() != -1) {
            this.mWindow.setWindowAnimations(getWindowAnimationsResId());
        }
        if (getDimEnabled()) {
            this.mWindow.addFlags(2);
        } else {
            this.mWindow.clearFlags(2);
        }
        LayoutParams lp = this.mWindow.getAttributes();
        lp.width = getWidth();
        lp.height = getHeight();
        lp.gravity = getGravity();
        onWindowAttributesChanged(lp);
        initView();
        initData();
        initListener();
    }

    public boolean getDimEnabled() {
        return true;
    }

    public boolean getCancelable() {
        return true;
    }

    public boolean getCanceledOnTouchOutside() {
        KLog.e("outTouschCancle " + this.outTouschCancel);
        return this.outTouschCancel;
    }

    public int getBackgroundDrawableResourceId() {
        return 17170445;
    }

    public int getWindowAnimationsResId() {
        return -1;
    }

    public int getWidth() {
        return -1;
    }

    public int getHeight() {
        return -2;
    }

    public int getGravity() {
        return 17;
    }

    public float getMargin() {
        return 0.0f;
    }
}
