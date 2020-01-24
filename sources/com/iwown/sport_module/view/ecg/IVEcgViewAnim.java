package com.iwown.sport_module.view.ecg;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class IVEcgViewAnim extends IVEcgView {
    public IVEcgViewAnim(Context context) {
        super(context);
        this.context = context;
    }

    public IVEcgViewAnim(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public IVEcgViewAnim(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(21)
    public IVEcgViewAnim(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mWidth = w;
        this.mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
