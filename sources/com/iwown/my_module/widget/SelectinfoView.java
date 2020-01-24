package com.iwown.my_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.iwown.my_module.R;

public class SelectinfoView extends LinearLayout {
    private String inputText;
    private String labelText;
    private ImageView leftImag;
    private Context mContext;
    private ImageView rightImag;
    private String secondTitle;
    private TextView secondTitleTv;
    private TextView titleMessage;
    private TextView titleName;
    private TextView unit;

    public SelectinfoView(Context context) {
        super(context);
    }

    public SelectinfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.my_module_view_common_select, this);
        this.titleName = (TextView) findViewById(R.id.common_title_tv);
        this.titleMessage = (TextView) findViewById(R.id.common_input_tv);
        this.rightImag = (ImageView) findViewById(R.id.right_imag);
        this.leftImag = (ImageView) findViewById(R.id.left_imag);
        this.unit = (TextView) findViewById(R.id.common_unit);
        this.unit.setVisibility(8);
        this.secondTitleTv = (TextView) findViewById(R.id.second_title_tv);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.button_resourse);
        this.labelText = a.getString(R.styleable.button_resourse_labelText);
        this.inputText = a.getString(R.styleable.button_resourse_messageText);
        this.secondTitle = a.getString(R.styleable.button_resourse_secondTitle);
        this.titleName.setText(this.labelText);
        this.titleMessage.setText(this.inputText);
        Log.i("", String.format("-----------------second:%s--------------", new Object[]{this.secondTitle}));
        if (this.secondTitle == null || this.secondTitle.equals("")) {
            this.secondTitleTv.setVisibility(8);
            LayoutParams layoutParams = new LayoutParams(-1, -2);
            layoutParams.gravity = 17;
            this.titleName.setLayoutParams(layoutParams);
        } else {
            this.secondTitleTv.setText(this.secondTitle);
            this.secondTitleTv.setVisibility(0);
        }
        a.recycle();
    }

    public void setLaberText(String label) {
        this.titleName.setText(label);
    }

    public void setMessageText(String message) {
        this.titleMessage.setText(message);
    }

    public String getLaberText() {
        return this.titleName.getText().toString();
    }

    public String getMessageText() {
        return this.titleMessage.getText().toString();
    }

    public String getSecondTitle() {
        return this.secondTitle;
    }

    public void setSecondTitle(String secondTitle2) {
        this.secondTitle = secondTitle2;
        this.secondTitleTv.setText(secondTitle2);
    }

    public void showUtil(String msg) {
        this.unit.setVisibility(0);
        this.unit.setText(msg);
    }

    public void setRightImagVisible(boolean visible) {
        if (visible) {
            this.rightImag.setVisibility(0);
        } else {
            this.rightImag.setVisibility(8);
        }
    }

    public void setLeftImagVisible(boolean visible) {
        if (visible) {
            this.leftImag.setVisibility(0);
        } else {
            this.leftImag.setVisibility(8);
        }
    }

    public void setLeftImagRes(int ResId) {
        this.leftImag.setImageResource(ResId);
    }

    public void setTitleMsgRightMargin(int rightMarginPx) {
        LayoutParams params = (LayoutParams) this.titleMessage.getLayoutParams();
        params.rightMargin = rightMarginPx;
        this.titleMessage.setLayoutParams(params);
    }

    public void adjustWeight(float titleWeight, float msgWeight) {
        LayoutParams titleParams = (LayoutParams) this.titleName.getLayoutParams();
        LayoutParams msgParams = (LayoutParams) this.titleMessage.getLayoutParams();
        titleParams.weight = titleWeight;
        msgParams.weight = msgWeight;
        this.titleName.setLayoutParams(titleParams);
        this.titleMessage.setLayoutParams(msgParams);
    }

    public void setMsgColor(int color) {
        this.titleMessage.setTextColor(color);
    }

    public void setTitleColor(int color) {
        this.titleName.setTextColor(color);
    }
}
