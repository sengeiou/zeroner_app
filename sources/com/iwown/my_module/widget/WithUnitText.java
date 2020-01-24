package com.iwown.my_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.iwown.my_module.R;
import com.iwown.my_module.utility.CommonUtility;
import com.iwown.my_module.utility.FontProvider;

public class WithUnitText extends RelativeLayout {
    private int mNumTextSize;
    private TextView mNumTv;
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
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.my_module_text_with_unit_layout, this);
        this.mNumTv = (TextView) findViewById(R.id.num_tv);
        this.mUnitTv = (TextView) findViewById(R.id.unit_tv);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.unit_text_widget);
        String unit = a.getString(R.styleable.unit_text_widget_unit_text);
        String num = a.getString(R.styleable.unit_text_widget_num_text);
        int unit_text_color = a.getColor(R.styleable.unit_text_widget_unit_text_color, Color.parseColor("#FFFFFF"));
        int num_text_color = a.getColor(R.styleable.unit_text_widget_num_text_color, Color.parseColor("#FFFFFF"));
        this.mUnitTv.setTextColor(unit_text_color);
        this.mNumTv.setTextColor(num_text_color);
        this.mUnitLeftMargin = a.getDimensionPixelSize(R.styleable.unit_text_widget_unit_left_margin, CommonUtility.dip2px(context, 2.0f));
        this.mNumTextSize = a.getDimensionPixelSize(R.styleable.unit_text_widget_num_text_size, CommonUtility.dip2px(context, 36.0f));
        this.mUnitTextSize = a.getDimensionPixelSize(R.styleable.unit_text_widget_unit_text_size, CommonUtility.dip2px(context, 17.0f));
        a.recycle();
        this.mNumTv.setTextSize(0, (float) this.mNumTextSize);
        this.mNumTv.setText(num);
        this.mNumTv.setTypeface(FontProvider.getFont_DINPRO_MEDIUM(getContext()));
        this.mUnitTv.setTextSize(0, (float) this.mUnitTextSize);
        this.mUnitTv.setText(unit);
        LayoutParams layoutParams = (LayoutParams) this.mUnitTv.getLayoutParams();
        layoutParams.setMargins(this.mUnitLeftMargin, 0, 0, 0);
        this.mUnitTv.setLayoutParams(layoutParams);
    }

    public void setNumTv(String text) {
        this.mNumTv.setText(text);
    }

    public String getNumText() {
        return this.mNumTv.getText().toString();
    }

    public void setUnitTv(String text) {
        this.mUnitTv.setText(text);
    }
}
