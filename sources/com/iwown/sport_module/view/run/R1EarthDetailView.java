package com.iwown.sport_module.view.run;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.sport_module.R;

public class R1EarthDetailView extends LinearLayout {
    private String defaultBad = "<151spm";
    private String defaultFirstGood = ">185spm";
    private String defaultGeneral = "151-162spm";
    private String defaultGood = "162-174spm";
    private String defaultSecondGood = "174-185spm";
    private int defaultTips = 0;
    private String defaultTitle = getResources().getString(R.string.sport_module_step_rate_range);
    private TextView r1_item_amazing;
    private TextView r1_item_bad;
    private TextView r1_item_excellent;
    private TextView r1_item_good;
    private TextView r1_item_superb;
    private TextView tipsMsgView;
    private TextView titleView;

    public R1EarthDetailView(Context context) {
        super(context);
        initView(context);
    }

    public R1EarthDetailView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.sport_module_r1_DetailView);
        this.defaultFirstGood = typedArray.getString(R.styleable.sport_module_r1_DetailView_sport_module_first_good);
        this.defaultSecondGood = typedArray.getString(R.styleable.sport_module_r1_DetailView_sport_module_second_good);
        this.defaultGood = typedArray.getString(R.styleable.sport_module_r1_DetailView_sport_module_good);
        this.defaultGeneral = typedArray.getString(R.styleable.sport_module_r1_DetailView_sport_module_general);
        this.defaultBad = typedArray.getString(R.styleable.sport_module_r1_DetailView_sport_module_bad);
        this.defaultTitle = typedArray.getString(R.styleable.sport_module_r1_DetailView_sport_module_title);
        this.defaultTips = typedArray.getInt(R.styleable.sport_module_r1_DetailView_sport_module_tips, 0);
        typedArray.recycle();
        initView(context);
    }

    public R1EarthDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_r1_details_item, this);
        this.titleView = (TextView) findViewById(R.id.ri_item_title);
        this.r1_item_excellent = (TextView) findViewById(R.id.r1_item_excellent);
        this.r1_item_superb = (TextView) findViewById(R.id.r1_item_superb);
        this.r1_item_amazing = (TextView) findViewById(R.id.r1_item_amazing);
        this.r1_item_good = (TextView) findViewById(R.id.r1_item_good);
        this.r1_item_bad = (TextView) findViewById(R.id.r1_item_bad);
        this.tipsMsgView = (TextView) findViewById(R.id.r1_tips_msg);
        this.r1_item_excellent.setText(this.defaultFirstGood);
        this.r1_item_superb.setText(this.defaultSecondGood);
        this.r1_item_amazing.setText(this.defaultGood);
        this.r1_item_good.setText(this.defaultGeneral);
        this.r1_item_bad.setText(this.defaultBad);
        this.titleView.setText(this.defaultTitle);
        showTips(this.defaultTips);
    }

    private void showTips(int index) {
        switch (index) {
            case 0:
                this.tipsMsgView.setText(R.string.sport_module_tips_step_rate);
                return;
            case 1:
                this.tipsMsgView.setText(R.string.sport_module_tips_vertical_height);
                return;
            case 2:
                this.tipsMsgView.setText(R.string.sport_module_tips_earth_time);
                return;
            default:
                this.tipsMsgView.setText(R.string.sport_module_tips_step_rate);
                return;
        }
    }
}
