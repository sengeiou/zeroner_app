package com.iwown.my_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.my_module.R;

public class NewSelectinfoView extends LinearLayout {
    private String labelText;
    private ImageView titleImg;
    private TextView titleName;

    public NewSelectinfoView(Context context) {
        super(context);
    }

    public NewSelectinfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.my_module_view_common_select_new, this);
        this.titleName = (TextView) findViewById(R.id.common_title_tv);
        this.titleImg = (ImageView) findViewById(R.id.common_title_img);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.button_resourse);
        this.labelText = a.getString(R.styleable.button_resourse_labelText);
        this.titleName.setText(this.labelText);
        this.titleName.setTextColor(a.getColor(R.styleable.button_resourse_labelTextColor, -1));
        this.titleImg.setImageResource(a.getResourceId(R.styleable.button_resourse_imageResouse, R.mipmap.setting_icon_3x));
        a.recycle();
    }

    public void setLaberText(String label) {
        this.titleName.setText(label);
    }

    public String getLaberText() {
        return this.titleName.getText().toString();
    }

    public void setControlTextColor(int resColor) {
        this.titleName.setTextColor(resColor);
    }

    public void setTitleImg(int drawIcon) {
        this.titleImg.setImageResource(drawIcon);
    }
}
