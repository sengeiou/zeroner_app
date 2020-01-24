package com.iwown.my_module.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public abstract class AbsCustomDialog extends Dialog {
    public Window mWindow;

    public abstract int getLayoutResID();

    public abstract void initData();

    public abstract void initListener();

    public abstract void initView();

    public AbsCustomDialog(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setCancelable(getCancelable());
        setCanceledOnTouchOutside(getCanceledOnTouchOutside());
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
        return true;
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
}
