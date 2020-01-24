package com.iwown.sport_module.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.iwown.sport_module.R;
import com.iwown.sport_module.view.swipetoloadlayout.SwipeRefreshTrigger;
import com.iwown.sport_module.view.swipetoloadlayout.SwipeToLoadLayout;
import com.iwown.sport_module.view.swipetoloadlayout.SwipeTrigger;
import com.socks.library.KLog;

public class SportRefreshHeader extends RelativeLayout implements SwipeRefreshTrigger, SwipeTrigger {
    int last_y;
    private AnimationDrawable mAnimationDrawable;
    private Context mContext;
    /* access modifiers changed from: private */
    public AnimationDrawable mDrawable;
    /* access modifiers changed from: private */
    public ShowTipListener mShowTipListener;
    private ImageView mSync_anim;

    public interface ShowTipListener {
        void shouldShowTip();
    }

    public void setShowTipListener(ShowTipListener showTipListener) {
        this.mShowTipListener = showTipListener;
    }

    public SportRefreshHeader(Context context) {
        this(context, null);
    }

    public SportRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.last_y = 0;
        this.mContext = context;
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sport_module_refresh_header_view, this);
        this.mSync_anim = (ImageView) findViewById(R.id.refresh_icon);
        this.mDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.sport_module_sync_head_anmation);
        KLog.e("SportRefreshHeader1....");
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
    }

    public void onRefresh() {
        KLog.e("onRefresh...");
        this.mSync_anim.setBackground(this.mDrawable);
        this.mDrawable.start();
        this.mSync_anim.postDelayed(new Runnable() {
            public void run() {
                if (SportRefreshHeader.this.mDrawable != null) {
                    SportRefreshHeader.this.mDrawable.stop();
                    if ((SportRefreshHeader.this.getParent() instanceof SwipeToLoadLayout) && SportRefreshHeader.this.mShowTipListener != null) {
                        SportRefreshHeader.this.mShowTipListener.shouldShowTip();
                    }
                }
            }
        }, 2000);
    }

    public void onPrepare() {
        KLog.e("onPrepare...");
        this.mDrawable.stop();
    }

    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (y > 0 && this.last_y >= 0 && y < this.last_y && y < 5) {
            KLog.e("onMove---->进入限制范围");
        }
        this.last_y = y;
    }

    public void onRelease() {
        KLog.e("onRelease...");
        this.last_y = 0;
    }

    public void onComplete() {
        KLog.e("onComplete...");
        this.mDrawable.stop();
        this.last_y = 0;
    }

    public void onReset() {
        KLog.e("onReset...");
    }
}
