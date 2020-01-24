package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.gps.data.GpsMsgData;
import com.iwown.sport_module.gps.data.MapActyUIEvent;
import com.iwown.sport_module.gps.service.LocationImpl;
import com.iwown.sport_module.pojo.WristConnectStateEvent;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.view.MyTextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.apache.commons.cli.HelpFormatter;
import org.greenrobot.eventbus.EventBus;

public class GpsMainLayout extends RelativeLayout {
    TranslateAnimation anim;
    private DecimalFormat decimalFormat;
    private TextView devInfo;
    TranslateAnimation enterAnim;
    private MyTextView gpsCalorie;
    private MyTextView gpsDistance;
    private GpsEndView gpsEndView;
    private MyTextView gpsHr;
    /* access modifiers changed from: private */
    public ImageView gpsLock;
    private TextView gpsMsg1Unit;
    private ImageView gpsMsg2Img;
    private TextView gpsMsg2Unit;
    private ImageView gpsMsg3Img;
    private TextView gpsMsg3Unit;
    private ImageView gpsMsg4Img;
    private TextView gpsMsg4Unit;
    private MyTextView gpsPaceOrHr;
    private GpsStartView gpsStartView;
    /* access modifiers changed from: private */
    public LockView gpsStop;
    private MyTextView gpsTime;
    private TextView gps_main_mid_unit;
    /* access modifiers changed from: private */
    public boolean isDown;
    public boolean isEnd;
    private boolean isEnglish;
    private boolean isHealthy;
    public boolean isLock;
    private boolean isStop = false;
    private boolean isSupportRealTimeHeart = false;
    private int lastY;
    private Context mContext;
    /* access modifiers changed from: private */
    public TextView mEndTip;
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private TextView mHr_monitor_tv;
    /* access modifiers changed from: private */
    public TextView mLockTip;
    private int padTop;
    private ConstraintLayout runLayout;
    /* access modifiers changed from: private */
    public ImageView showMapBtn;
    private int sportType = 0;
    private long stTime;
    private TranslateAnimation startAnim;
    private ImageView toSetBtn;

    public boolean isSupportRealTimeHeart() {
        return this.isSupportRealTimeHeart;
    }

    public void setSupportRealTimeHeart(boolean supportRealTimeHeart, int sportType2) {
        this.isSupportRealTimeHeart = supportRealTimeHeart;
        this.sportType = sportType2;
        if (!supportRealTimeHeart || sportType2 != 0) {
            this.devInfo.setVisibility(8);
            this.mHr_monitor_tv.setVisibility(8);
            return;
        }
        this.devInfo.setVisibility(0);
        this.gpsMsg3Img.setImageResource(R.mipmap.heart_rate3x);
        this.gpsMsg3Unit.setText(R.string.sport_module_heart_rate);
        ModuleRouteDeviceInfoService.getInstance().controlRealTimeHR(true);
    }

    public GpsMainLayout(Context context) {
        super(context);
        initView(context);
    }

    public GpsMainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public GpsMainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.isHealthy = AppConfigUtil.isHealthy(context);
        this.isLock = false;
        this.isEnd = false;
        LayoutInflater.from(context).inflate(R.layout.sport_module_gps_main2, this);
        this.padTop = DensityUtil.dip2px(context, 130.0f);
        this.runLayout = (ConstraintLayout) findViewById(R.id.gps_main_bg);
        this.runLayout.setBackground(new GradientDrawable(Orientation.TOP_BOTTOM, RunActivitySkin.MapActy_Bg_Color));
        this.gpsLock = (ImageView) findViewById(R.id.gps_main_lock);
        this.gpsStop = (LockView) findViewById(R.id.gps_main_stop);
        this.gpsMsg1Unit = (TextView) findViewById(R.id.gps_main_dis_unit);
        this.gpsMsg3Img = (ImageView) findViewById(R.id.gps_pace_img);
        this.gpsMsg4Img = (ImageView) findViewById(R.id.gps_calories_img);
        this.gpsMsg2Img = (ImageView) findViewById(R.id.gps_mid_img);
        this.gpsMsg2Unit = (TextView) findViewById(R.id.gps_main_mid_unit);
        this.gpsMsg3Unit = (TextView) findViewById(R.id.gps_pace_txt_unit);
        this.gpsMsg4Unit = (TextView) findViewById(R.id.gps_calories_txt_unit);
        this.gpsStartView = (GpsStartView) findViewById(R.id.gps_main_continue);
        this.gpsEndView = (GpsEndView) findViewById(R.id.gps_main_end);
        this.mHr_monitor_tv = (TextView) findViewById(R.id.hr_monitor_tv);
        this.gpsStop.setGpsMainLayout(this);
        this.devInfo = (TextView) findViewById(R.id.dev_info);
        if (ModuleRouteDeviceInfoService.getInstance().isWristConnected()) {
            this.devInfo.setText(context.getResources().getString(R.string.sport_module_f1_connected, new Object[]{UserConfig.getInstance().getDevice()}));
        } else {
            this.devInfo.setText(UserConfig.getInstance().getDevice() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + context.getResources().getString(R.string.device_module_ble_connect_statue_2));
        }
        this.devInfo.setTranslationY((float) WindowUtil.getStatusBarHeight());
        this.toSetBtn = (ImageView) findViewById(R.id.set_btn);
        this.toSetBtn.setTranslationY((float) WindowUtil.getStatusBarHeight());
        this.showMapBtn = (ImageView) findViewById(R.id.show_map_btn);
        if (UserConfig.getInstance().isMertric()) {
            this.isEnglish = false;
        } else {
            this.isEnglish = true;
        }
        this.gpsLock.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GpsMainLayout.this.isLock = true;
                GpsMainLayout.this.mLockTip.setVisibility(0);
                GpsMainLayout.this.gpsStop.flipAnimator(1);
                GpsMainLayout.this.gpsLock.setVisibility(8);
                GpsMainLayout.this.showMapBtn.setVisibility(8);
            }
        });
        this.gpsStartView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GpsMainLayout.this.mEndTip.setVisibility(8);
                GpsMainLayout.this.startViewAnim();
            }
        });
        this.showMapBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                GpsMainLayout.this.isDown = true;
                GpsMainLayout.this.startAnim();
            }
        });
        this.mLockTip = (TextView) findViewById(R.id.lock_tip);
        this.mEndTip = (TextView) findViewById(R.id.end_tip);
    }

    public void setView(int type) {
        if (type == 0 || type == 1) {
            this.gpsDistance = (MyTextView) findViewById(R.id.gps_main_dis);
            this.gpsTime = (MyTextView) findViewById(R.id.gps_main_mid);
            this.gpsCalorie = (MyTextView) findViewById(R.id.gps_calories_txt);
            this.gpsPaceOrHr = (MyTextView) findViewById(R.id.gps_pace_txt);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.gpsTime, this.gpsCalorie, this.gpsPaceOrHr);
        } else if (type == 2) {
            this.gpsTime = (MyTextView) findViewById(R.id.gps_main_dis);
            this.gpsDistance = (MyTextView) findViewById(R.id.gps_main_mid);
            this.gpsCalorie = (MyTextView) findViewById(R.id.gps_calories_txt);
            this.gpsPaceOrHr = (MyTextView) findViewById(R.id.gps_pace_txt);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.gpsDistance, this.gpsCalorie, this.gpsPaceOrHr);
        } else {
            this.gpsCalorie = (MyTextView) findViewById(R.id.gps_main_dis);
            this.gpsTime = (MyTextView) findViewById(R.id.gps_main_mid);
            this.gpsDistance = (MyTextView) findViewById(R.id.gps_pace_txt);
            this.gpsPaceOrHr = (MyTextView) findViewById(R.id.gps_calories_txt);
            FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.gpsDistance, this.gpsTime, this.gpsPaceOrHr);
        }
        this.gpsDistance.setText("0.00");
        this.gpsTime.setText("00:00");
        this.gpsPaceOrHr.setText(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.gpsCalorie.setText("0");
        this.stTime = System.currentTimeMillis() / 1000;
    }

    public void reshView(GpsMsgData data) {
        if (this.isEnglish) {
            this.gpsDistance.setText(this.decimalFormat.format(Util.kmToMile((double) data.getDistance())));
        } else {
            this.gpsDistance.setText(this.decimalFormat.format((double) data.getDistance()));
        }
        if (!this.isSupportRealTimeHeart) {
            this.gpsPaceOrHr.setText(data.getPace());
        }
        this.gpsCalorie.setText(this.decimalFormat.format((double) data.getCalorie()));
        int in1 = data.getTime() / 3600;
        int in2 = (data.getTime() - (in1 * 3600)) / 60;
        int in3 = data.getTime() % 60;
        if (in1 != 0) {
            this.gpsTime.setText(String.format("%02d", new Object[]{Integer.valueOf(in1)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in2)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in3)}));
            if (this.gpsTime.getId() == R.id.gps_main_dis) {
                this.gpsTime.setTextSize((float) DensityUtil.dip2px(this.mContext, 25.0f));
                return;
            }
            return;
        }
        this.gpsTime.setText(String.format("%02d", new Object[]{Integer.valueOf(in2)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in3)}));
    }

    public void refreshHrValue(int heart) {
        if (this.isSupportRealTimeHeart) {
            this.gpsPaceOrHr.setText(heart + "");
        }
    }

    public void refreshConnectState(WristConnectStateEvent event) {
        if (!this.isSupportRealTimeHeart) {
            return;
        }
        if (event.isConnected()) {
            this.devInfo.setText(this.mContext.getResources().getString(R.string.sport_module_f1_connected, new Object[]{UserConfig.getInstance().getDevice()}));
            return;
        }
        this.devInfo.setText(UserConfig.getInstance().getDevice() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.mContext.getResources().getString(R.string.device_module_ble_connect_statue_2));
    }

    public void setStop(boolean isStop2) {
        this.isStop = isStop2;
    }

    public void changeTime(int time) {
        int in1 = time / 3600;
        int in2 = (time - (in1 * 3600)) / 60;
        int in3 = time % 60;
        if (in1 != 0) {
            this.gpsTime.setText(String.format("%02d", new Object[]{Integer.valueOf(in1)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in2)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in3)}));
            if (this.gpsTime.getId() == R.id.gps_main_dis) {
                this.gpsTime.setTextSize((float) DensityUtil.dip2px(this.mContext, 25.0f));
                return;
            }
            return;
        }
        this.gpsTime.setText(String.format("%02d", new Object[]{Integer.valueOf(in2)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(in3)}));
    }

    public void initData(int type) {
        this.decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        switch (type) {
            case 0:
                this.gpsMsg1Unit.setVisibility(0);
                if (this.isEnglish) {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_km);
                }
                this.gpsMsg2Img.setImageResource(R.mipmap.time3x);
                this.gpsMsg2Unit.setText(R.string.sport_module_Time);
                if (!this.isSupportRealTimeHeart || this.sportType != 0) {
                    this.gpsMsg3Img.setImageResource(R.mipmap.pace3x);
                    this.gpsMsg3Unit.setText(R.string.sport_module_avg_pace);
                } else {
                    this.gpsMsg3Img.setImageResource(R.mipmap.heart_rate3x);
                    this.gpsMsg3Unit.setText(R.string.sport_module_heart_rate);
                }
                this.gpsMsg4Img.setImageResource(R.mipmap.calories3x);
                this.gpsMsg4Unit.setText(R.string.sport_module_unit_cal);
                this.gpsStop.setImageBg(R.mipmap.huge_stop_blue, R.mipmap.huge_lock_blue, -15502124, 1293120724);
                return;
            case 1:
                this.gpsMsg1Unit.setVisibility(0);
                if (this.isEnglish) {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg1Unit.setText(R.string.sport_module_distance_unit_km);
                }
                this.gpsMsg2Img.setImageResource(R.mipmap.time3x);
                this.gpsMsg2Unit.setText(R.string.sport_module_Time);
                if (!this.isSupportRealTimeHeart || this.sportType != 0) {
                    this.gpsMsg3Img.setImageResource(R.mipmap.pace3x);
                    this.gpsMsg3Unit.setText(R.string.sport_module_avg_pace);
                } else {
                    this.gpsMsg3Img.setImageResource(R.mipmap.heart_rate3x);
                    this.gpsMsg3Unit.setText(R.string.sport_module_heart_rate);
                }
                this.gpsMsg4Img.setImageResource(R.mipmap.calories3x);
                this.gpsMsg4Unit.setText(R.string.sport_module_unit_cal);
                this.gpsStop.setImageBg(R.mipmap.huge_stop_green, R.mipmap.huge_lock_green, -14046971, 1294575877);
                return;
            case 2:
                this.gpsMsg1Unit.setVisibility(8);
                this.gpsMsg2Unit.setVisibility(0);
                this.gpsMsg2Img.setImageResource(R.mipmap.distance3x);
                if (this.isEnglish) {
                    this.gpsMsg2Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg2Unit.setText(R.string.sport_module_distance_unit_km);
                }
                if (!this.isSupportRealTimeHeart || this.sportType != 0) {
                    this.gpsMsg3Img.setImageResource(R.mipmap.pace3x);
                    this.gpsMsg3Unit.setText(R.string.sport_module_avg_pace);
                } else {
                    this.gpsMsg3Img.setImageResource(R.mipmap.heart_rate3x);
                    this.gpsMsg3Unit.setText(R.string.sport_module_heart_rate);
                }
                this.gpsMsg4Img.setImageResource(R.mipmap.calories3x);
                this.gpsMsg4Unit.setText(R.string.sport_module_unit_cal);
                this.gpsStop.setImageBg(R.mipmap.huge_stop_purple, R.mipmap.huge_lock_purple, -9417310, 1299205538);
                return;
            case 3:
                this.gpsMsg1Unit.setVisibility(0);
                this.gpsMsg1Unit.setText(R.string.sport_module_unit_cal);
                this.gpsMsg2Img.setImageResource(R.mipmap.time3x);
                this.gpsMsg2Unit.setText(R.string.sport_module_Time);
                this.gpsMsg3Img.setImageResource(R.mipmap.distance3x);
                if (this.isEnglish) {
                    this.gpsMsg3Unit.setText(R.string.sport_module_distance_unit_mi);
                } else {
                    this.gpsMsg3Unit.setText(R.string.sport_module_distance_unit_km);
                }
                if (!this.isSupportRealTimeHeart || this.sportType != 0) {
                    this.gpsMsg4Img.setImageResource(R.mipmap.pace3x);
                    this.gpsMsg4Unit.setText(R.string.sport_module_avg_pace);
                } else {
                    this.gpsMsg4Img.setImageResource(R.mipmap.heart_rate3x);
                    this.gpsMsg4Unit.setText(R.string.sport_module_heart_rate);
                }
                this.gpsStop.setImageBg(R.mipmap.huge_stop_red, R.mipmap.huge_lock_red, -2074550, 1306548298);
                return;
            default:
                return;
        }
    }

    public void setMessage() {
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isLock) {
            int y = (int) event.getY();
            switch (event.getAction()) {
                case 0:
                    this.lastY = y;
                    break;
                case 2:
                    if (y - this.lastY <= 80 || !this.isDown) {
                    }
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void startAnim() {
        if (this.anim == null) {
            this.anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) getMeasuredHeight());
            this.anim.setDuration(200);
            this.anim.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    GpsMainLayout.this.setVisibility(8);
                    EventBus.getDefault().post(new MapActyUIEvent(0));
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
        this.showMapBtn.setVisibility(0);
        this.mLockTip.setVisibility(8);
    }

    public void showGpsPause() {
        LocationImpl.getInstance().pauseLocation();
        EventBus.getDefault().post(new MapActyUIEvent(2));
        this.gpsEndView.setVisibility(0);
        this.gpsStartView.setVisibility(0);
        this.gpsLock.setVisibility(8);
        this.showMapBtn.setVisibility(8);
        this.gpsStop.setVisibility(4);
        this.gpsStop.setEnabled(false);
        endViewAnim();
    }

    public void showGpsEnd() {
        this.isStop = true;
        this.gpsEndView.setVisibility(8);
        this.gpsStartView.setVisibility(8);
        this.gpsLock.setVisibility(0);
        this.showMapBtn.setVisibility(0);
        this.gpsStop.setVisibility(0);
        this.gpsStop.setEnabled(true);
        this.isEnd = true;
    }

    private void endViewAnim() {
        TranslateAnimation anim2 = new TranslateAnimation((float) this.gpsStop.getMeasuredWidth(), 0.0f, 0.0f, 0.0f);
        anim2.setDuration(300);
        this.gpsEndView.startAnimation(anim2);
        TranslateAnimation anim22 = new TranslateAnimation((float) (-this.gpsStop.getMeasuredWidth()), 0.0f, 0.0f, 0.0f);
        anim2.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                GpsMainLayout.this.mEndTip.setVisibility(0);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        anim22.setDuration(300);
        this.gpsStartView.startAnimation(anim22);
    }

    /* access modifiers changed from: private */
    public void startViewAnim() {
        LocationImpl.getInstance().restartLocation();
        TranslateAnimation anim2 = new TranslateAnimation(0.0f, (float) this.gpsStop.getMeasuredWidth(), 0.0f, 0.0f);
        anim2.setDuration(300);
        anim2.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                GpsMainLayout.this.mEndTip.setVisibility(8);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        if (this.startAnim == null) {
            this.startAnim = new TranslateAnimation(0.0f, (float) (-this.gpsStop.getMeasuredWidth()), 0.0f, 0.0f);
            this.startAnim.setDuration(300);
            this.startAnim.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    GpsMainLayout.this.showGpsEnd();
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        this.gpsEndView.startAnimation(anim2);
        this.gpsStartView.startAnimation(this.startAnim);
    }
}
