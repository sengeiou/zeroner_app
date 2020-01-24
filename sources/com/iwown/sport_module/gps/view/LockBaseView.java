package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.view.LockProgressBar.onAnimEnd;

public class LockBaseView extends FrameLayout implements onAnimEnd {
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LockBaseView.this.animBack();
        }
    };
    ImageView imageView;
    boolean isLock = false;
    LockProgressBar progressBar;

    public LockBaseView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LockBaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /* access modifiers changed from: protected */
    public void initView(Context context) {
        this.imageView = new ImageView(context);
        this.imageView.setPadding(22, 22, 22, 22);
        this.imageView.setImageResource(R.mipmap.end_circle3x);
        this.progressBar = new LockProgressBar(context, -2074550, 1306548298);
        this.progressBar.setAnimListener(this);
        addView(this.imageView);
        addView(this.progressBar);
        this.progressBar.setVisibility(4);
    }

    public void setImageBg(int bg1, int color1, int color2) {
        this.imageView.setImageResource(bg1);
        this.progressBar.setPaintColor(color1, color2);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.progressBar.startAnim();
                break;
            case 1:
                this.progressBar.setCut(true);
                break;
        }
        return true;
    }

    public void animEndListener(boolean isLock2) {
        this.isLock = isLock2;
        this.handler.sendEmptyMessage(1);
    }

    /* access modifiers changed from: protected */
    public void animBack() {
        this.progressBar.setVisibility(4);
    }
}
