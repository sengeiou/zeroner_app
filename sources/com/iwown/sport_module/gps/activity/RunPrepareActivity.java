package com.iwown.sport_module.gps.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.gps.MediaPlayerHelper;
import com.iwown.sport_module.view.MyTextView;

public class RunPrepareActivity extends Activity {
    /* access modifiers changed from: private */
    public TranslateAnimation animation1;
    private TranslateAnimation animation2;
    MyTextView downTxt1;
    MyTextView downTxt2;
    private View mCoverView;
    private MyTextView mDownTxt1;
    private MyTextView mDownTxt2;
    /* access modifiers changed from: private */
    public boolean mShowHr;
    private RelativeLayout mTotalBg;
    /* access modifiers changed from: private */
    public int number = 3;
    /* access modifiers changed from: private */
    public int position;
    /* access modifiers changed from: private */
    public int sportType;
    /* access modifiers changed from: private */
    public int type;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        getWindow().addFlags(67108864);
        setContentView(R.layout.sport_module_prepare_main);
        initView();
        this.type = getIntent().getIntExtra("type", 0);
        this.position = getIntent().getIntExtra("position", 0);
        this.sportType = getIntent().getIntExtra("sportType", 0);
        this.mShowHr = getIntent().getBooleanExtra("show_hr", false);
        startAnim1();
        if (ModuleRouteDeviceInfoService.getInstance().isWristConnected() && ModuleRouteDeviceInfoService.getInstance().isMTKHeadset()) {
            this.mShowHr = true;
        }
        if (AppConfigUtil.isHealthy(this)) {
            MediaPlayerHelper.getInstance().initMedia(getApplicationContext(), true);
        } else {
            MediaPlayerHelper.getInstance().initMedia(getApplicationContext());
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    private void startAnim1() {
        if (this.animation1 == null) {
            this.animation1 = new TranslateAnimation(0.0f, 0.0f, -300.0f, 0.0f);
            this.animation1.setInterpolator(new AnticipateOvershootInterpolator(4.0f));
            this.animation1.setDuration(800);
            this.animation1.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    RunPrepareActivity.this.downTxt1.setText(String.valueOf(RunPrepareActivity.this.number));
                    if (RunPrepareActivity.this.sportType == 0) {
                        L.file("语音播报-->123播报 is : " + RunPrepareActivity.this.number, 3);
                        MediaPlayerHelper.getInstance().playNumber(RunPrepareActivity.this.number);
                    }
                }

                public void onAnimationEnd(Animation animation) {
                    SystemClock.sleep(300);
                    RunPrepareActivity.this.number = RunPrepareActivity.this.number - 1;
                    if (RunPrepareActivity.this.number >= 1) {
                        RunPrepareActivity.this.downTxt1.startAnimation(RunPrepareActivity.this.animation1);
                        RunPrepareActivity.this.startAnim2();
                        return;
                    }
                    ModuleRouteDeviceInfoService.getInstance().setGpsSportTime(System.currentTimeMillis());
                    RunPrepareActivity.this.startActivity(new Intent(RunPrepareActivity.this, MapActivity.class).putExtra("type", RunPrepareActivity.this.type).putExtra("position", RunPrepareActivity.this.position).putExtra("sportType", RunPrepareActivity.this.sportType).putExtra("show_hr", RunPrepareActivity.this.mShowHr));
                    RunPrepareActivity.this.finish();
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.downTxt1.startAnimation(this.animation1);
    }

    /* access modifiers changed from: private */
    public void startAnim2() {
        this.downTxt2.setText(String.valueOf(this.number + 1));
        if (this.animation2 == null) {
            this.animation2 = new TranslateAnimation(0.0f, 0.0f, 0.0f, 300.0f);
            this.animation2.setDuration(300);
            this.animation2.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    RunPrepareActivity.this.downTxt2.setText("");
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.downTxt2.startAnimation(this.animation2);
    }

    private void initView() {
        this.downTxt1 = (MyTextView) findViewById(R.id.down_txt1);
        this.downTxt2 = (MyTextView) findViewById(R.id.down_txt2);
        this.mTotalBg = (RelativeLayout) findViewById(R.id.total_bg);
        this.mCoverView = findViewById(R.id.cover_view);
        this.mTotalBg.setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        this.mCoverView.setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
    }
}
