package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.pojo.data.SwimHealthyData;
import com.iwown.sport_module.view.WithUnitText;

public class RunSwimItem extends LinearLayout {
    /* access modifiers changed from: private */
    public WithUnitText avgRateFree;
    /* access modifiers changed from: private */
    public WithUnitText avgRatePool;
    /* access modifiers changed from: private */
    public WithUnitText avgSpeedPool;
    /* access modifiers changed from: private */
    public WithUnitText avgTimes;
    private ConstraintLayout freeLayout;
    private boolean isMetric;
    /* access modifiers changed from: private */
    public WithUnitText loopTimes;
    /* access modifiers changed from: private */
    public WithUnitText maxRateFree;
    private TextView msg1Unit;
    /* access modifiers changed from: private */
    public WithUnitText oneDistance;
    private LinearLayout poolLayout;
    /* access modifiers changed from: private */
    public WithUnitText swimPool;
    private TextView swimTitle1;
    private View swimTitle2;
    private View swimTitle3;
    /* access modifiers changed from: private */
    public int swimType = 0;
    /* access modifiers changed from: private */
    public WithUnitText swolf;
    /* access modifiers changed from: private */
    public TextView timeTxt;
    /* access modifiers changed from: private */
    public TextView title1Num;
    /* access modifiers changed from: private */
    public WithUnitText title2Num;
    /* access modifiers changed from: private */
    public WithUnitText title3Num;
    /* access modifiers changed from: private */
    public WithUnitText totalTimes;

    public RunSwimItem(Context context) {
        super(context);
        initView(context);
    }

    public RunSwimItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RunSwimItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public RunSwimItem(Context context, int swimType2, boolean isMetric2) {
        super(context);
        this.swimType = swimType2;
        this.isMetric = isMetric2;
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_swim_item, this);
        this.timeTxt = (TextView) findViewById(R.id.swim_time);
        this.swimTitle1 = (TextView) findViewById(R.id.swim_title);
        this.swimTitle2 = findViewById(R.id.swim_msg2);
        this.swimTitle3 = findViewById(R.id.swim_msg3);
        this.msg1Unit = (TextView) findViewById(R.id.swim_msg1_unit);
        TextView title2Str = (TextView) this.swimTitle2.findViewById(R.id.title);
        ((TextView) this.swimTitle3.findViewById(R.id.title)).setText(context.getString(R.string.sport_module_calories));
        this.title1Num = (TextView) findViewById(R.id.swim_msg1);
        this.title2Num = (WithUnitText) this.swimTitle2.findViewById(R.id.value);
        this.title3Num = (WithUnitText) this.swimTitle3.findViewById(R.id.value);
        this.title3Num.setUnitTv(context.getString(R.string.sport_module_unit_cal));
        this.poolLayout = (LinearLayout) findViewById(R.id.pool_layout);
        View poolLength = findViewById(R.id.pool_length);
        this.swimPool = (WithUnitText) poolLength.findViewById(R.id.value);
        View totalStep = findViewById(R.id.total_step);
        this.loopTimes = (WithUnitText) totalStep.findViewById(R.id.value);
        View avgSwolf = findViewById(R.id.avg_swolf);
        this.swolf = (WithUnitText) avgSwolf.findViewById(R.id.value);
        View total_Times = findViewById(R.id.total_times);
        this.totalTimes = (WithUnitText) total_Times.findViewById(R.id.value);
        View avg_Times = findViewById(R.id.avg_times);
        this.avgTimes = (WithUnitText) avg_Times.findViewById(R.id.value);
        View one_Dis = findViewById(R.id.one_dis);
        this.oneDistance = (WithUnitText) one_Dis.findViewById(R.id.value);
        View swim_Speed = findViewById(R.id.swim_speed);
        this.avgSpeedPool = (WithUnitText) swim_Speed.findViewById(R.id.value);
        View swim_Rate = findViewById(R.id.swim_rate);
        this.avgRatePool = (WithUnitText) swim_Rate.findViewById(R.id.value);
        this.freeLayout = (ConstraintLayout) findViewById(R.id.free_layout);
        View avg_Speed2 = findViewById(R.id.avg_speed2);
        this.avgRateFree = (WithUnitText) avg_Speed2.findViewById(R.id.value);
        View max_Speed2 = findViewById(R.id.max_speed2);
        this.maxRateFree = (WithUnitText) max_Speed2.findViewById(R.id.value);
        if (this.swimType == 0) {
            this.swimTitle1.setText(context.getString(R.string.sport_module_Distance));
            this.msg1Unit.setVisibility(0);
            title2Str.setText(context.getString(R.string.sport_module_Exercise_time));
            if (this.isMetric) {
                this.msg1Unit.setText(context.getString(R.string.sport_swimming_distance_unit));
                this.swimPool.setUnitTv(context.getString(R.string.sport_swimming_distance_unit));
                this.oneDistance.setUnitTv(context.getString(R.string.sport_swimming_distance_unit));
                this.avgSpeedPool.setUnitTv(context.getString(R.string.sport_swimming_time_100mi));
            } else {
                this.msg1Unit.setText(context.getString(R.string.sport_swimming_swim_ft));
                this.swimPool.setUnitTv(context.getString(R.string.sport_swimming_swim_ft));
                this.oneDistance.setUnitTv(context.getString(R.string.sport_swimming_swim_ft));
                this.avgSpeedPool.setUnitTv(context.getString(R.string.sport_swimming_time_100mi_ft));
            }
            this.title2Num.setUnitTv("");
            this.poolLayout.setVisibility(0);
            this.freeLayout.setVisibility(8);
            ((TextView) poolLength.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_pool_lenght));
            ((TextView) totalStep.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_distance_laps));
            this.loopTimes.setUnitTv(context.getString(R.string.sport_swimming_times));
            ((TextView) avgSwolf.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_avg_swolf));
            this.swolf.setUnitTv("");
            ((TextView) total_Times.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_total_stroke));
            this.totalTimes.setUnitTv(context.getString(R.string.sport_swimming_times));
            ((TextView) avg_Times.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_avg_stroke));
            this.avgTimes.setUnitTv(context.getString(R.string.sport_swimming_stroke_pool));
            ((TextView) one_Dis.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_one_distance));
            ((TextView) swim_Speed.findViewById(R.id.title)).setText(context.getString(R.string.sport_module_avg_pace));
            ((TextView) swim_Rate.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_rate_avg));
            this.avgRatePool.setUnitTv(context.getString(R.string.sport_swimming_rate_unit2));
        } else {
            this.swimTitle1.setText(context.getString(R.string.sport_module_Exercise_time));
            this.msg1Unit.setVisibility(8);
            title2Str.setText(context.getString(R.string.sport_swimming_total_stroke));
            this.title2Num.setUnitTv(context.getString(R.string.sport_swimming_times));
            this.poolLayout.setVisibility(8);
            this.freeLayout.setVisibility(0);
            ((TextView) avg_Speed2.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_rate_avg));
            this.avgRateFree.setUnitTv(context.getString(R.string.sport_swimming_rate_unit2));
            ((TextView) max_Speed2.findViewById(R.id.title)).setText(context.getString(R.string.sport_swimming_rate_max));
            this.maxRateFree.setUnitTv(context.getString(R.string.sport_swimming_rate_unit2));
        }
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.title1Num, this.title2Num.getNumTv(), this.title3Num.getNumTv(), this.swimPool.getNumTv(), this.loopTimes.getNumTv(), this.swolf.getNumTv(), this.totalTimes.getNumTv(), this.avgTimes.getNumTv(), this.oneDistance.getNumTv(), this.avgSpeedPool.getNumTv(), this.avgRatePool.getNumTv(), this.avgRateFree.getNumTv(), this.maxRateFree.getNumTv());
    }

    public void refreshView(final SwimHealthyData data) {
        if (this.title1Num != null) {
            this.title1Num.post(new Runnable() {
                public void run() {
                    RunSwimItem.this.title3Num.setNumTv(String.valueOf(data.getCalories()));
                    RunSwimItem.this.timeTxt.setText(data.getStartTime());
                    if (RunSwimItem.this.swimType == 0) {
                        RunSwimItem.this.title1Num.setText(String.valueOf(data.getDistance()));
                        RunSwimItem.this.title2Num.setNumTv(data.getDurationStr());
                        RunSwimItem.this.swimPool.setNumTv(String.valueOf(data.getPoolLength()));
                        RunSwimItem.this.loopTimes.setNumTv(String.valueOf(data.getLaps()));
                        RunSwimItem.this.swolf.setNumTv(String.valueOf(data.getAvgSwolf()));
                        RunSwimItem.this.totalTimes.setNumTv(String.valueOf(data.getTotalStroke()));
                        RunSwimItem.this.avgTimes.setNumTv(String.valueOf(data.getAvgStroke()));
                        RunSwimItem.this.oneDistance.setNumTv(String.valueOf(data.getAvgDps()));
                        RunSwimItem.this.avgSpeedPool.setNumTv(data.getAvgPaceStr());
                        RunSwimItem.this.avgRatePool.setNumTv(String.valueOf(data.getAvgRate()));
                        return;
                    }
                    RunSwimItem.this.title1Num.setText(data.getDurationStr());
                    RunSwimItem.this.title2Num.setNumTv(String.valueOf(data.getTotalStroke()));
                    RunSwimItem.this.avgRateFree.setNumTv(String.valueOf(data.getAvgRate()));
                }
            });
        }
    }

    public void refreshMaxRate(final int maxValue) {
        if (this.title1Num != null) {
            this.title1Num.post(new Runnable() {
                public void run() {
                    RunSwimItem.this.maxRateFree.setNumTv(String.valueOf(maxValue));
                }
            });
        }
    }
}
