package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.data_link.FontChangeUtils;
import com.iwown.data_link.heart.HeartData;
import com.iwown.sport_module.R;
import com.iwown.sport_module.activity.RunActivitySkin;
import com.iwown.sport_module.view.WithUnitText;
import com.iwown.sport_module.view.run.HeartF151Layout;
import com.iwown.sport_module.view.run.RunHeart51View;
import com.iwown.sport_module.view.run.RunHeartChat;
import com.socks.library.KLog;
import org.apache.commons.cli.HelpFormatter;

public class RunHeartItem extends LinearLayout {
    /* access modifiers changed from: private */
    public WithUnitText avg_hr_value;
    /* access modifiers changed from: private */
    public WithUnitText mHeighest_hr;
    /* access modifiers changed from: private */
    public WithUnitText mLowest_hr;
    private RunHeartChat mRun_heart51_chat;
    private HeartF151Layout mRun_heart51_layout;
    private RunHeart51View mRun_heart_view;

    public RunHeartItem(Context context) {
        super(context);
        initView(context);
    }

    public RunHeartItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RunHeartItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_run_acty_hr_item, this);
        findViewById(R.id.heart_top_part_ll).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        findViewById(R.id.heart_part2).setBackgroundColor(RunActivitySkin.RunActy_Item_BG);
        View avg = findViewById(R.id.avg);
        ((TextView) avg.findViewById(R.id.title)).setText(R.string.sport_module_avg);
        this.avg_hr_value = (WithUnitText) avg.findViewById(R.id.value);
        this.avg_hr_value.setUnitTv(getContext().getString(R.string.sport_module_unit_bpm));
        this.avg_hr_value.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        View lowest = findViewById(R.id.lowest);
        ((TextView) lowest.findViewById(R.id.title)).setText(R.string.sport_module_Lowest);
        this.mLowest_hr = (WithUnitText) lowest.findViewById(R.id.value);
        this.mLowest_hr.setUnitTv(getContext().getString(R.string.sport_module_unit_bpm));
        this.mLowest_hr.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        View heighest = findViewById(R.id.highest);
        ((TextView) heighest.findViewById(R.id.title)).setText(R.string.sport_module_Highest);
        this.mHeighest_hr = (WithUnitText) heighest.findViewById(R.id.value);
        this.mHeighest_hr.setUnitTv(getContext().getString(R.string.sport_module_unit_bpm));
        this.mHeighest_hr.setNumTv(HelpFormatter.DEFAULT_LONG_OPT_PREFIX);
        this.mRun_heart51_chat = (RunHeartChat) findViewById(R.id.run_heart_view);
        this.mRun_heart_view = (RunHeart51View) findViewById(R.id.run_heart51_chat);
        this.mRun_heart51_layout = (HeartF151Layout) findViewById(R.id.run_heart51_layout);
        FontChangeUtils.setTypeFace(FontChangeUtils.getNumberTypeFace(), this.mHeighest_hr.getNumTv(), this.mLowest_hr.getNumTv(), this.avg_hr_value.getNumTv());
    }

    public void refreshHeartDataViews(final HeartData heartData) {
        KLog.e("no2855refreshHeartDataViews111111");
        if (heartData != null) {
            KLog.e("no2855refreshHeartDataViews222222");
            this.avg_hr_value.post(new Runnable() {
                public void run() {
                    RunHeartItem.this.avg_hr_value.setNumTv(heartData.getAvg() + "");
                    RunHeartItem.this.mLowest_hr.setNumTv(heartData.getMin_bpm() + "");
                    RunHeartItem.this.mHeighest_hr.setNumTv(heartData.getMax_bpm() + "");
                }
            });
            this.mRun_heart_view.setData(heartData.getTotal51(), heartData.getMins());
            this.mRun_heart51_chat.setData(heartData.getHeInt());
            this.mRun_heart51_layout.setHeartData(heartData.getMins());
            this.mRun_heart51_layout.setAreaData(heartData.getMaxHeart());
        }
    }

    public void refreshAvgHeart(final int heart) {
        this.avg_hr_value.post(new Runnable() {
            public void run() {
                RunHeartItem.this.avg_hr_value.setNumTv(heart + "");
            }
        });
    }
}
