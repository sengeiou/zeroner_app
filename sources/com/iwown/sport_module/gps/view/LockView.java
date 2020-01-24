package com.iwown.sport_module.gps.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.view.LockProgressBar.onAnimEnd;

public class LockView extends FrameLayout implements onAnimEnd {
    GpsMainLayout gpsMainLayout;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LockView.this.progressBar.setVisibility(4);
            if (LockView.this.isLock) {
                LockView.this.flipAnimator(2);
            }
        }
    };
    private ImageView imageView;
    /* access modifiers changed from: private */
    public boolean isLock = false;
    private ImageView lockImage;
    /* access modifiers changed from: private */
    public LockProgressBar progressBar;

    public LockView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LockView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.imageView = new ImageView(context);
        this.lockImage = new ImageView(context);
        this.imageView.setPadding(22, 22, 22, 22);
        this.lockImage.setPadding(22, 22, 22, 22);
        this.imageView.setImageResource(R.mipmap.stop3x);
        this.lockImage.setImageResource(R.mipmap.lock_circle3x);
        this.progressBar = new LockProgressBar(context);
        this.progressBar.setAnimListener(this);
        addView(this.lockImage);
        addView(this.imageView);
        addView(this.progressBar);
        this.lockImage.setVisibility(8);
        this.progressBar.setVisibility(4);
        this.imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.d("testasdad", "点击了暂停按钮");
                if (LockView.this.gpsMainLayout != null) {
                    LockView.this.gpsMainLayout.showGpsPause();
                }
            }
        });
    }

    public void setGpsMainLayout(GpsMainLayout gpsMainLayout2) {
        this.gpsMainLayout = gpsMainLayout2;
    }

    public void setImageBg(int bg1, int bg2, int color1, int color2) {
        this.imageView.setImageResource(bg1);
        this.lockImage.setImageResource(bg2);
        this.progressBar.setPaintColor(color1, color2);
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                Log.d("testEvent", "LockView is Down");
                this.progressBar.startAnim();
                break;
            case 1:
                this.progressBar.setCut(true);
                break;
        }
        return true;
    }

    public void flipAnimator(int type) {
        final View view1;
        final View view2;
        if (type == 1) {
            view1 = this.imageView;
            view2 = this.lockImage;
        } else {
            view1 = this.lockImage;
            view2 = this.imageView;
        }
        view1.setVisibility(0);
        view2.setVisibility(8);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view1, "rotationY", new float[]{0.0f, 90.0f});
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(view2, "rotationY", new float[]{-90.0f, 0.0f});
        animator2.setInterpolator(new OvershootInterpolator(2.0f));
        animator1.setDuration(200).start();
        final int i = type;
        animator1.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                view1.setVisibility(8);
                view2.setVisibility(0);
                animator2.setDuration(200).start();
                if (i == 2 && LockView.this.gpsMainLayout != null) {
                    LockView.this.gpsMainLayout.showLock();
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void animEndListener(boolean isLock2) {
        this.isLock = isLock2;
        this.handler.sendEmptyMessage(1);
    }
}
