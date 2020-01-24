package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.device_data.MapUtil;
import com.iwown.sport_module.pojo.MapHealthyData;
import com.iwown.sport_module.util.Util;
import com.iwown.sport_module.view.WithUnitText;
import com.socks.library.KLog;
import org.apache.commons.cli.HelpFormatter;

public class RunMapDataItem extends LinearLayout {
    private DateUtil date;
    private boolean isMertric = true;
    /* access modifiers changed from: private */
    public WithUnitText mActiveTime_value;
    /* access modifiers changed from: private */
    public WithUnitText mCal_value;
    /* access modifiers changed from: private */
    public WithUnitText mDistance;
    /* access modifiers changed from: private */
    public View mHr;
    /* access modifiers changed from: private */
    public WithUnitText mHr_value;
    private View mPace;
    /* access modifiers changed from: private */
    public WithUnitText mPace_value;
    private View mRate;
    /* access modifiers changed from: private */
    public WithUnitText mRate_value;
    /* access modifiers changed from: private */
    public WithUnitText mSpeed_value;
    private int mSportType;
    private View mStride;
    /* access modifiers changed from: private */
    public WithUnitText mStride_value;
    private View mTotal_step;
    /* access modifiers changed from: private */
    public WithUnitText mTotal_steps_value;
    private View paceLine;

    public RunMapDataItem(Context context) {
        super(context);
    }

    public RunMapDataItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RunMapDataItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public RunMapDataItem(Context context, DateUtil date2, boolean isMertric2, int sportType) {
        super(context);
        this.date = date2;
        this.isMertric = isMertric2;
        this.mSportType = sportType;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_run_acty_map_healthy_data_item, this);
        this.paceLine = findViewById(R.id.pace_line);
        GradientDrawable drawable = new GradientDrawable(Orientation.LEFT_RIGHT, new int[]{Color.parseColor("#E0584A"), Color.parseColor("#FFD847"), Color.parseColor("#2DB407")});
        drawable.setCornerRadius((float) DensityUtil.dip2px(getContext(), 3.0f));
        this.paceLine.setBackground(drawable);
        findViewById(R.id.map_top_data_ll).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        findViewById(R.id.map_data1).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        findViewById(R.id.map_data2).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        TextView time_point = (TextView) findViewById(R.id.time_tv);
        if (this.date != null) {
            if (Util.shouldUseY_M_D()) {
                time_point.setText(getContext().getString(R.string.sport_module_time, new Object[]{this.date.getYear() + "", this.date.getMonth() + "", this.date.getDay() + ""}) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getContext().getString(R.string.sport_module_time_hh_mm, new Object[]{this.date.getHour() + "", Util.get02dStr(this.date.getMinute())}));
            } else {
                String[] mMonthArrSimple = getResources().getStringArray(R.array.sport_module_months_items);
                time_point.setText(getContext().getString(R.string.sport_module_time_west, new Object[]{mMonthArrSimple[this.date.getMonth() - 1] + "", this.date.getDay() + "", this.date.getYear() + ""}) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + getContext().getString(R.string.sport_module_time_hh_mm, new Object[]{this.date.getHour() + "", Util.get02dStr(this.date.getMinute())}));
            }
        }
        this.mDistance = (WithUnitText) findViewById(R.id.distance_tv);
        this.mDistance.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        if (!this.isMertric) {
            this.mDistance.setUnitTv(getContext().getString(R.string.sport_module_distance_unit_mi));
        } else {
            this.mDistance.setUnitTv(getContext().getString(R.string.sport_module_distance_unit_km));
        }
        View time = findViewById(R.id.time);
        ((TextView) time.findViewById(R.id.title)).setText(R.string.sport_module_Time);
        this.mActiveTime_value = (WithUnitText) time.findViewById(R.id.value);
        this.mActiveTime_value.setUnitTv("");
        this.mActiveTime_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.mPace = findViewById(R.id.pace);
        ((TextView) this.mPace.findViewById(R.id.title)).setText(R.string.sport_module_Pace);
        this.mPace_value = (WithUnitText) this.mPace.findViewById(R.id.value);
        this.mPace_value.setUnitTv("");
        this.mPace_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        View cal = findViewById(R.id.cal);
        ((TextView) cal.findViewById(R.id.title)).setText(R.string.sport_module_calories);
        this.mCal_value = (WithUnitText) cal.findViewById(R.id.value);
        this.mCal_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.mCal_value = (WithUnitText) cal.findViewById(R.id.value);
        this.mHr = findViewById(R.id.hr);
        ((TextView) this.mHr.findViewById(R.id.title)).setText(R.string.sport_module_heart_rate);
        this.mHr_value = (WithUnitText) this.mHr.findViewById(R.id.value);
        this.mHr_value.setUnitTv("");
        this.mHr_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.mTotal_step = findViewById(R.id.total_step);
        ((TextView) this.mTotal_step.findViewById(R.id.title)).setText(R.string.sport_module_Total_Steps);
        this.mTotal_steps_value = (WithUnitText) this.mTotal_step.findViewById(R.id.value);
        this.mTotal_steps_value.setUnitTv(getContext().getString(R.string.sport_module_unit_step));
        this.mTotal_steps_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        View speed = findViewById(R.id.speed);
        ((TextView) speed.findViewById(R.id.title)).setText(R.string.sport_module_Speed);
        this.mSpeed_value = (WithUnitText) speed.findViewById(R.id.value);
        if (this.isMertric) {
            this.mSpeed_value.setUnitTv(getContext().getString(R.string.sport_module_unit_km_per_h));
        } else {
            this.mSpeed_value.setUnitTv(getContext().getString(R.string.sport_module_unit_mi_per_h));
        }
        this.mSpeed_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.mStride = findViewById(R.id.stride);
        ((TextView) this.mStride.findViewById(R.id.title)).setText(R.string.sport_module_Stride);
        this.mStride_value = (WithUnitText) this.mStride.findViewById(R.id.value);
        if (this.isMertric) {
            this.mStride_value.setUnitTv(getContext().getString(R.string.sport_module_unit_cm));
        } else {
            this.mStride_value.setUnitTv(getContext().getString(R.string.sport_module_unit_in));
        }
        this.mStride_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.mRate = findViewById(R.id.rate);
        ((TextView) this.mRate.findViewById(R.id.title)).setText(R.string.sport_module_Rate);
        this.mRate_value = (WithUnitText) this.mRate.findViewById(R.id.value);
        this.mRate_value.setUnitTv(getContext().getString(R.string.sport_module_unit_steps_per_min));
        this.mRate_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.mDistance.getNumTv(), this.mActiveTime_value.getNumTv(), this.mPace_value.getNumTv(), this.mCal_value.getNumTv(), this.mTotal_steps_value.getNumTv(), this.mSpeed_value.getNumTv(), this.mRate_value.getNumTv(), this.mStride_value.getNumTv(), this.mHr_value.getNumTv());
    }

    public void adjustMapHealthyDataUi(int dev_type) {
        switch (dev_type) {
            case 1:
                if (this.mSportType == 136) {
                    this.mStride.setVisibility(8);
                    this.mRate.setVisibility(8);
                    this.mTotal_step.setVisibility(8);
                    this.mPace.setVisibility(8);
                    return;
                }
                return;
            case 2:
                this.mHr.setVisibility(8);
                if (this.mSportType == 1) {
                    this.mStride.setVisibility(8);
                    this.mRate.setVisibility(8);
                    this.mTotal_step.setVisibility(8);
                    this.mPace.setVisibility(8);
                    return;
                }
                return;
            case 4:
                if (this.mSportType == 136) {
                    this.mStride.setVisibility(8);
                    this.mRate.setVisibility(8);
                    this.mTotal_step.setVisibility(8);
                    this.mPace.setVisibility(8);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void refreshMapDataViews(final MapHealthyData healthyData) {
        KLog.e("no2855refreshDataViews333333");
        if (healthyData != null && this.mDistance != null) {
            this.mDistance.post(new Runnable() {
                public void run() {
                    RunMapDataItem.this.mDistance.setNumTv(healthyData.getDistance() + "");
                    if (RunMapDataItem.this.mActiveTime_value != null) {
                        RunMapDataItem.this.mActiveTime_value.setNumTv(Util.getHH_mm_ss_Str(healthyData.getActive_time()));
                    }
                    if (RunMapDataItem.this.mPace_value != null) {
                        RunMapDataItem.this.mPace_value.setNumTv(RunMapDataItem.this.getPaceStr(healthyData.getPace()));
                    }
                    if (RunMapDataItem.this.mCal_value != null) {
                        RunMapDataItem.this.mCal_value.setNumTv(healthyData.getCal() + "");
                    }
                    if (RunMapDataItem.this.mTotal_steps_value != null) {
                        RunMapDataItem.this.mTotal_steps_value.setNumTv(healthyData.getTotal_step() + "");
                    }
                    if (RunMapDataItem.this.mSpeed_value != null) {
                        RunMapDataItem.this.mSpeed_value.setNumTv(healthyData.getSpeed() + "");
                    }
                    if (RunMapDataItem.this.mStride_value != null) {
                        RunMapDataItem.this.mStride_value.setNumTv(healthyData.getStride() + "");
                    }
                    if (RunMapDataItem.this.mRate_value != null) {
                        RunMapDataItem.this.mRate_value.setNumTv(healthyData.getRate() + "");
                    }
                    if (RunMapDataItem.this.mHr != null) {
                        RunMapDataItem.this.mHr_value.setNumTv(healthyData.getHr() + "");
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public String getPaceStr(int time_double) {
        String numStr1 = String.valueOf(time_double / 60);
        return numStr1 + "'" + String.valueOf((int) ((double) (time_double - ((time_double / 60) * 60)))) + "''";
    }

    public void refreshPace(final float speed) {
        if (this.mPace_value != null) {
            this.mPace_value.post(new Runnable() {
                public void run() {
                    RunMapDataItem.this.mPace_value.setNumTv(MapUtil.number2mins(speed));
                }
            });
        }
    }

    public void refreshHeart(final int hr) {
        if (this.mHr_value != null) {
            this.mHr_value.post(new Runnable() {
                public void run() {
                    RunMapDataItem.this.mHr_value.setNumTv(hr + "");
                }
            });
        }
    }

    public void goneSomeView() {
        findViewById(R.id.hr).setVisibility(8);
        findViewById(R.id.total_step).setVisibility(8);
        findViewById(R.id.stride).setVisibility(8);
        findViewById(R.id.rate).setVisibility(8);
    }
}
