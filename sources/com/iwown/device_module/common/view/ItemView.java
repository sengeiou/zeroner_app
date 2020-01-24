package com.iwown.device_module.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintLayout.LayoutParams;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.device_module.R;

public class ItemView extends ConstraintLayout {
    private String inputText;
    private String labelText;
    private ImageView leftImag;
    private Context mContext;
    private ImageView rightImag;
    private TextView titleMessage;
    private TextView titleName;
    private TextView unit;
    private View view;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.device_module_view_common_select, this);
        this.titleName = (TextView) findViewById(R.id.common_title_tv);
        this.titleMessage = (TextView) findViewById(R.id.common_input_tv);
        this.rightImag = (ImageView) findViewById(R.id.right_imag);
        this.leftImag = (ImageView) findViewById(R.id.left_imag);
        this.unit = (TextView) findViewById(R.id.common_unit);
        this.view = findViewById(R.id.item_underline);
        this.unit.setVisibility(8);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.device_module_button_resourse);
        this.labelText = a.getString(R.styleable.device_module_button_resourse_device_module_labelText);
        this.inputText = a.getString(R.styleable.device_module_button_resourse_device_module_messageText);
        Drawable drawable = a.getDrawable(R.styleable.device_module_button_resourse_device_module_imageResouse);
        if (drawable == null) {
            this.leftImag.setVisibility(8);
        } else {
            this.leftImag.setVisibility(0);
        }
        if (a.getBoolean(R.styleable.device_module_button_resourse_device_module_isUnderLine, true)) {
            this.view.setVisibility(0);
        } else {
            this.view.setVisibility(8);
        }
        this.titleName.setText(this.labelText);
        this.titleMessage.setText(this.inputText);
        this.leftImag.setImageDrawable(drawable);
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

    public void setLineShow(boolean flag) {
        if (flag) {
            this.view.setVisibility(0);
        } else {
            this.view.setVisibility(8);
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

    public void setMsgColor(int color) {
        this.titleMessage.setTextColor(color);
    }

    public void setTitleColor(int color) {
        this.titleName.setTextColor(color);
    }
}
