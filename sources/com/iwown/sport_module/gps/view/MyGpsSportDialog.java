package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.dialog.NewAbsCustomDialog;
import com.iwown.sport_module.R;

public class MyGpsSportDialog extends NewAbsCustomDialog {
    private ScaleAnimation anim;
    /* access modifiers changed from: private */
    public int chose;
    /* access modifiers changed from: private */
    public ImageView cycle;
    private LinearLayout gpsChoseLayout;
    private OnSportListener onSportListener;
    private int padding;
    /* access modifiers changed from: private */
    public ImageView run;
    /* access modifiers changed from: private */
    public ImageView walk;

    public interface OnSportListener {
        void onSport(int i);
    }

    public MyGpsSportDialog(Context context) {
        this(context, 0);
    }

    public MyGpsSportDialog(Context context, int init_choose) {
        super(context);
        this.chose = init_choose;
    }

    public int getLayoutResID() {
        return R.layout.sport_module_gps_sport_change;
    }

    public void initView() {
        this.padding = DensityUtil.dip2px(getContext(), 6.0f);
        this.run = (ImageView) findViewById(R.id.gps_change_run);
        this.cycle = (ImageView) findViewById(R.id.gps_change_cycle);
        this.walk = (ImageView) findViewById(R.id.gps_change_walk);
        this.gpsChoseLayout = (LinearLayout) findViewById(R.id.gps_chose_layout);
        switch (this.chose) {
            case 0:
                changeView(this.run, this.cycle, this.walk, 0);
                break;
            case 1:
                changeView(this.cycle, this.run, this.walk, 1);
                break;
            case 2:
                changeView(this.walk, this.run, this.cycle, 2);
                break;
        }
        this.run.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyGpsSportDialog.this.changeView(MyGpsSportDialog.this.run, MyGpsSportDialog.this.cycle, MyGpsSportDialog.this.walk, 0);
            }
        });
        this.cycle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyGpsSportDialog.this.changeView(MyGpsSportDialog.this.cycle, MyGpsSportDialog.this.run, MyGpsSportDialog.this.walk, 1);
            }
        });
        this.walk.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyGpsSportDialog.this.changeView(MyGpsSportDialog.this.walk, MyGpsSportDialog.this.cycle, MyGpsSportDialog.this.run, 2);
            }
        });
        this.gpsChoseLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyGpsSportDialog.this.dismiss();
            }
        });
    }

    /* access modifiers changed from: private */
    public void changeView(View v1, View v2, View v3, int chose2) {
        v1.setPadding(0, 0, 0, 0);
        v2.setPadding(this.padding, this.padding, this.padding, this.padding);
        v3.setPadding(this.padding, this.padding, this.padding, this.padding);
        v1.setAlpha(1.0f);
        v2.setAlpha(0.5f);
        v3.setAlpha(0.5f);
        this.chose = chose2;
        if (isShowing()) {
            startAnim(v1);
        }
    }

    private void startAnim(View view) {
        if (this.anim == null) {
            this.anim = new ScaleAnimation(0.6f, 1.0f, 0.6f, 1.0f, (float) (view.getWidth() / 2), (float) (view.getHeight() / 2));
            this.anim.setDuration(500);
            this.anim.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    SystemClock.sleep(300);
                    MyGpsSportDialog.this.callBack(MyGpsSportDialog.this.chose);
                    MyGpsSportDialog.this.dismiss();
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        view.startAnimation(this.anim);
    }

    public void initData() {
    }

    public void initListener() {
    }

    public boolean getCancelable() {
        return true;
    }

    public boolean getCanceledOnTouchOutside() {
        return super.getCanceledOnTouchOutside();
    }

    public int getWidth() {
        return -1;
    }

    public int getHeight() {
        return -1;
    }

    public void setSportListener(OnSportListener onSportListener2) {
        this.onSportListener = onSportListener2;
    }

    public boolean getDimEnabled() {
        return false;
    }

    /* access modifiers changed from: private */
    public void callBack(int chose2) {
        if (this.onSportListener != null) {
            this.onSportListener.onSport(chose2);
        }
    }
}
