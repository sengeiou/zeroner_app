package com.iwown.sport_module.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.activity.LockScreenActivity;
import com.iwown.sport_module.gps.data.GpsMsgData;
import com.iwown.sport_module.gps.data.MapActyUIEvent;
import com.iwown.sport_module.gps.view.GpsEndView;
import com.iwown.sport_module.gps.view.LockView;
import com.iwown.sport_module.gps.view.RunLayout;
import com.iwown.sport_module.util.Util;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;

public class ScreenMainLayout extends RelativeLayout {
    public static ScreenMainLayout instance = null;
    TranslateAnimation anim;
    private DecimalFormat decimalFormat;
    TranslateAnimation enterAnim;
    private MyTextView gpsCalorie;
    private MyTextView gpsDistance;
    private GpsEndView gpsEndView;
    /* access modifiers changed from: private */
    public ImageView gpsLock;
    private TextView gpsMsg1Unit;
    private TextView gpsMsg2Unit;
    private ImageView gpsMsg3Img;
    private TextView gpsMsg3Unit;
    private ImageView gpsMsg4Img;
    private TextView gpsMsg4Unit;
    private RelativeLayout gpsRelay;
    private MyTextView gpsSpeed;
    private ImageView gpsStartView;
    /* access modifiers changed from: private */
    public LockView gpsStop;
    private MyTextView gpsTime;
    private TextView gps_main_mid_unit;
    private boolean isDown;
    public boolean isEnd;
    private boolean isEnglish;
    public boolean isLock;
    private boolean isStop = false;
    private int lastX;
    private int padTop;
    private RunLayout runLayout;
    private long stTime;
    private TranslateAnimation startAnim;

    public ScreenMainLayout(Context context) {
        super(context);
        initView(context);
    }

    public ScreenMainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScreenMainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        instance = this;
        this.isLock = false;
        this.isEnd = false;
        LayoutInflater.from(context).inflate(R.layout.sport_module_gps_main, this);
        this.padTop = DensityUtil.dip2px(context, 130.0f);
        this.runLayout = (RunLayout) findViewById(R.id.gps_main_bg2);
        this.gpsRelay = (RelativeLayout) findViewById(R.id.gps_main_relay);
        this.gpsLock = (ImageView) findViewById(R.id.gps_main_lock);
        this.gpsStop = (LockView) findViewById(R.id.gps_main_stop);
        this.gpsMsg1Unit = (TextView) findViewById(R.id.gps_main_dis_unit);
        this.gpsMsg3Img = (ImageView) findViewById(R.id.gps_pace_img);
        this.gpsMsg4Img = (ImageView) findViewById(R.id.gps_calories_img);
        this.gpsMsg2Unit = (TextView) findViewById(R.id.gps_main_mid_unit);
        this.gpsMsg3Unit = (TextView) findViewById(R.id.gps_pace_txt_unit);
        this.gpsMsg4Unit = (TextView) findViewById(R.id.gps_calories_txt_unit);
        this.gpsStartView = (ImageView) findViewById(R.id.gps_main_continue);
        this.gpsEndView = (GpsEndView) findViewById(R.id.gps_main_end);
        this.gpsStop.setVisibility(8);
        this.gpsLock.setVisibility(8);
        if (UserConfig.getInstance().isMertric()) {
            this.isEnglish = false;
        } else {
            this.isEnglish = true;
        }
        this.gpsLock.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ScreenMainLayout.this.isLock = true;
                ScreenMainLayout.this.gpsStop.flipAnimator(1);
                ScreenMainLayout.this.gpsLock.setVisibility(8);
            }
        });
        this.gpsStartView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ScreenMainLayout.this.startViewAnim();
            }
        });
    }

    public void setView(int type) {
        if (type == 0 || type == 1) {
            this.gpsDistance = (MyTextView) findViewById(R.id.gps_main_dis);
            this.gpsTime = (MyTextView) findViewById(R.id.gps_main_mid);
            this.gpsSpeed = (MyTextView) findViewById(R.id.gps_pace_txt);
            this.gpsCalorie = (MyTextView) findViewById(R.id.gps_calories_txt);
        } else if (type == 2) {
            this.gpsTime = (MyTextView) findViewById(R.id.gps_main_dis);
            this.gpsDistance = (MyTextView) findViewById(R.id.gps_main_mid);
            this.gpsSpeed = (MyTextView) findViewById(R.id.gps_pace_txt);
            this.gpsCalorie = (MyTextView) findViewById(R.id.gps_calories_txt);
            this.gpsTime.setTextSize(38.0f);
        } else {
            this.gpsCalorie = (MyTextView) findViewById(R.id.gps_main_dis);
            this.gpsTime = (MyTextView) findViewById(R.id.gps_main_mid);
            this.gpsDistance = (MyTextView) findViewById(R.id.gps_pace_txt);
            this.gpsSpeed = (MyTextView) findViewById(R.id.gps_calories_txt);
        }
        this.gpsDistance.setText("0.0");
        this.gpsTime.setText("00:00:00");
        this.gpsSpeed.setText(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.gpsCalorie.setText("0");
        this.stTime = System.currentTimeMillis() / 1000;
    }

    public void reshView(GpsMsgData data) {
        if (this.isEnglish) {
            this.gpsDistance.setText(this.decimalFormat.format(Util.kmToMile((double) data.getDistance())));
        } else {
            this.gpsDistance.setText(this.decimalFormat.format((double) data.getDistance()));
        }
        this.gpsSpeed.setText(data.getPace());
        this.gpsCalorie.setText(this.decimalFormat.format((double) data.getCalorie()));
        int in1 = data.getTime() / 3600;
        int in2 = (data.getTime() - (in1 * 3600)) / 60;
        int in3 = data.getTime() % 60;
        this.gpsTime.setText(String.format("%02d", new Object[]{Integer.valueOf(in1)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in2)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in3)}));
    }

    public void setStop(boolean isStop2) {
        this.isStop = isStop2;
    }

    public void changeTime(int time) {
        int in1 = time / 3600;
        int in2 = (time - (in1 * 3600)) / 60;
        int in3 = time % 60;
        this.gpsTime.setText(String.format("%02d", new Object[]{Integer.valueOf(in1)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in2)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in3)}));
    }

    public void initData(int type) {
        this.decimalFormat = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
        switch (type) {
            case 0:
                this.gpsRelay.setBackgroundResource(R.drawable.gps_round_bg);
                this.gpsMsg1Unit.setVisibility(0);
                if (this.isEnglish) {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_km);
                }
                this.gpsMsg2Unit.setVisibility(8);
                this.gpsMsg3Img.setImageResource(R.mipmap.map_pace);
                this.gpsMsg3Unit.setText(R.string.sport_module_avg_pace);
                this.gpsMsg4Img.setImageResource(R.mipmap.map_calories);
                this.gpsMsg4Unit.setText(R.string.sport_module_distance_unit_km);
                this.gpsStop.setImageBg(R.mipmap.huge_stop_blue, R.mipmap.huge_lock_blue, -15502124, 1293120724);
                return;
            case 1:
                this.gpsRelay.setBackgroundResource(R.drawable.gps_round2_bg);
                this.gpsMsg1Unit.setVisibility(0);
                if (this.isEnglish) {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_km);
                }
                this.gpsMsg2Unit.setVisibility(8);
                this.gpsMsg3Img.setImageResource(R.mipmap.map_pace);
                this.gpsMsg3Unit.setText(R.string.sport_module_avg_pace);
                this.gpsMsg4Img.setImageResource(R.mipmap.map_calories);
                this.gpsMsg4Unit.setText(R.string.sport_module_unit_cal);
                this.gpsStop.setImageBg(R.mipmap.huge_stop_green, R.mipmap.huge_lock_green, -14046971, 1294575877);
                return;
            case 2:
                this.gpsRelay.setBackgroundResource(R.drawable.gps_round3_bg);
                this.gpsMsg1Unit.setVisibility(8);
                this.gpsMsg2Unit.setVisibility(0);
                if (this.isEnglish) {
                    this.gpsMsg2Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg2Unit.setText(R.string.sport_module_distance_unit_km);
                }
                this.gpsMsg3Img.setImageResource(R.mipmap.map_pace);
                this.gpsMsg3Unit.setText(R.string.sport_module_avg_pace);
                this.gpsMsg4Img.setImageResource(R.mipmap.map_calories);
                this.gpsMsg4Unit.setText(R.string.sport_module_unit_cal);
                this.gpsStop.setImageBg(R.mipmap.huge_stop_purple, R.mipmap.huge_lock_purple, -9417310, 1299205538);
                return;
            case 3:
                this.gpsRelay.setBackgroundResource(R.drawable.gps_round4_bg);
                this.gpsMsg1Unit.setVisibility(0);
                this.gpsMsg1Unit.setText(R.string.sport_module_unit_cal);
                this.gpsMsg2Unit.setVisibility(8);
                this.gpsMsg3Img.setImageResource(R.mipmap.map_distance);
                if (this.isEnglish) {
                    this.gpsMsg3Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg3Unit.setText(R.string.sport_module_distance_unit_km);
                }
                this.gpsMsg4Img.setImageResource(R.mipmap.map_pace);
                this.gpsMsg4Unit.setText(R.string.sport_module_avg_pace);
                this.gpsStop.setImageBg(R.mipmap.huge_stop_red, R.mipmap.huge_lock_red, -2074550, 1306548298);
                return;
            default:
                return;
        }
    }

    public void setMessage() {
    }

    private void startAnim() {
        if (this.anim == null) {
            this.anim = new TranslateAnimation(0.0f, (float) getMeasuredWidth(), 0.0f, 0.0f);
            this.anim.setDuration(300);
            this.anim.setFillAfter(true);
            this.anim.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (LockScreenActivity.instance != null) {
                        LockScreenActivity.instance.finish();
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        startAnimation(this.anim);
    }

    public void enterOfAnim() {
        setVisibility(0);
        if (this.enterAnim == null) {
            this.enterAnim = new TranslateAnimation(0.0f, 0.0f, (float) getMeasuredHeight(), 0.0f);
            this.enterAnim.setDuration(200);
            this.enterAnim.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    EventBus.getDefault().post(new MapActyUIEvent(1));
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.isDown = false;
        startAnimation(this.enterAnim);
    }

    public void showLock() {
        this.isLock = false;
        this.gpsLock.setVisibility(0);
    }

    public void showGpsStart() {
        this.gpsEndView.setVisibility(0);
        this.gpsStartView.setVisibility(0);
        this.gpsLock.setVisibility(8);
        this.gpsStop.setVisibility(4);
        this.gpsStop.setEnabled(false);
        endViewAnim();
    }

    public void showGpsEnd() {
        this.isStop = true;
        this.gpsEndView.setVisibility(8);
        this.gpsStartView.setVisibility(8);
        this.gpsLock.setVisibility(0);
        this.gpsStop.setVisibility(0);
        this.gpsStop.setEnabled(true);
        this.isEnd = true;
    }

    private void endViewAnim() {
        TranslateAnimation anim2 = new TranslateAnimation((float) this.gpsStop.getMeasuredWidth(), 0.0f, 0.0f, 0.0f);
        anim2.setDuration(300);
        this.gpsEndView.startAnimation(anim2);
        TranslateAnimation anim22 = new TranslateAnimation((float) (-this.gpsStop.getMeasuredWidth()), 0.0f, 0.0f, 0.0f);
        anim22.setDuration(300);
        this.gpsStartView.startAnimation(anim22);
    }

    /* access modifiers changed from: private */
    public void startViewAnim() {
        TranslateAnimation anim2 = new TranslateAnimation(0.0f, (float) this.gpsStop.getMeasuredWidth(), 0.0f, 0.0f);
        anim2.setDuration(300);
        if (this.startAnim == null) {
            this.startAnim = new TranslateAnimation(0.0f, (float) (-this.gpsStop.getMeasuredWidth()), 0.0f, 0.0f);
            this.startAnim.setDuration(300);
            this.startAnim.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    ScreenMainLayout.this.showGpsEnd();
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.gpsEndView.startAnimation(anim2);
        this.gpsStartView.startAnimation(this.startAnim);
    }
}
