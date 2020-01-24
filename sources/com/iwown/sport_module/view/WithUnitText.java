package com.iwown.sport_module.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.iwown.lib_common.DensityUtil;
import com.iwown.sport_module.R;
import com.iwown.sport_module.SportInitUtils;

public class WithUnitText extends RelativeLayout {
    private boolean mIsUnitVisible;
    private int mNumFont;
    private int mNumTextSize;
    /* access modifiers changed from: private */
    public TextView mNumTv;
    private int mUnitLeftMargin;
    private int mUnitTextSize;
    private TextView mUnitTv;

    public WithUnitText(Context context) {
        super(context);
    }

    public WithUnitText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.sport_module_text_with_unit_layout, this);
        this.mNumTv = (TextView) findViewById(R.id.num_tv);
        this.mUnitTv = (TextView) findViewById(R.id.unit_tv);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.sport_module_unit_text_widget);
        String unit = a.getString(R.styleable.sport_module_unit_text_widget_sport_module_unit_text);
        String num = a.getString(R.styleable.sport_module_unit_text_widget_sport_module_num_text);
        int unit_text_color = a.getColor(R.styleable.sport_module_unit_text_widget_sport_module_unit_text_color, Color.parseColor("#4A4B4D"));
        int num_text_color = a.getColor(R.styleable.sport_module_unit_text_widget_sport_module_num_text_color, Color.parseColor("#4A4B4D"));
        this.mUnitTv.setTextColor(unit_text_color);
        this.mNumTv.setTextColor(num_text_color);
        this.mUnitLeftMargin = a.getDimensionPixelSize(R.styleable.sport_module_unit_text_widget_sport_module_unit_left_margin, DensityUtil.dip2px(context, 2.0f));
        this.mNumTextSize = a.getDimensionPixelSize(R.styleable.sport_module_unit_text_widget_sport_module_num_text_size, DensityUtil.dip2px(context, 36.0f));
        this.mUnitTextSize = a.getDimensionPixelSize(R.styleable.sport_module_unit_text_widget_sport_module_unit_text_size, DensityUtil.dip2px(context, 17.0f));
        this.mNumFont = a.getInt(R.styleable.sport_module_unit_text_widget_sport_module_num_font_stype, 0);
        this.mIsUnitVisible = a.getBoolean(R.styleable.sport_module_unit_text_widget_sport_module_is_unit_visible, true);
        a.recycle();
        this.mNumTv.setTextSize(0, (float) this.mNumTextSize);
        this.mNumTv.setText(num);
        this.mNumTv.setTextColor(num_text_color);
        if (this.mNumFont != 0) {
            if (this.mNumFont == 1) {
                this.mNumTv.setTypeface(SportInitUtils.mDincond_bold_font);
            } else if (this.mNumFont == 2) {
                this.mNumTv.setTypeface(SportInitUtils.mFujiboli_font);
                this.mUnitTv.setTypeface(SportInitUtils.mFujiboli_font);
            }
        }
        this.mUnitTv.setTextSize(0, (float) this.mUnitTextSize);
        this.mUnitTv.setText(unit);
        if (!this.mIsUnitVisible) {
            this.mUnitTv.setVisibility(8);
        }
        LayoutParams layoutParams = (LayoutParams) this.mUnitTv.getLayoutParams();
        layoutParams.setMargins(this.mUnitLeftMargin, 0, 0, 0);
        this.mUnitTv.setLayoutParams(layoutParams);
        this.mUnitTv.setTextColor(unit_text_color);
    }

    public void setNumTv(final String text) {
        this.mNumTv.post(new Runnable() {
            public void run() {
                WithUnitText.this.mNumTv.setText(text);
            }
        });
    }

    public String getNumText() {
        return this.mNumTv.getText().toString();
    }

    public void setUnitTv(String text) {
        this.mUnitTv.setText(text);
    }

    public TextView getNumTv() {
        return this.mNumTv;
    }

    public void setTextColor(int colorRes) {
        this.mUnitTv.setTextColor(colorRes);
        this.mNumTv.setTextColor(colorRes);
    }
}
