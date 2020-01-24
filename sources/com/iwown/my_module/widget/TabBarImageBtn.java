package com.iwown.my_module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.iwown.my_module.R;

public class TabBarImageBtn extends LinearLayout {
    private ImageView image;
    private int imageResouse;
    private String imageText;
    private int imgTextSize;
    private int textColor;
    private TextView tvText;

    public TabBarImageBtn(Context context) {
        super(context);
    }

    public TabBarImageBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.my_module_tabarbutton, this);
        this.image = (ImageView) findViewById(R.id.imageView);
        this.tvText = (TextView) findViewById(R.id.imageText);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.button_resourse);
        this.imageResouse = a.getResourceId(R.styleable.button_resourse_imageResouse, 5);
        this.imageText = a.getString(R.styleable.button_resourse_imageText);
        this.textColor = a.getColor(R.styleable.button_resourse_imageTextColor, 3);
        this.imgTextSize = a.getDimensionPixelSize(R.styleable.button_resourse_imageTextSize, 17);
        this.image.setBackgroundResource(this.imageResouse);
        this.tvText.setText(this.imageText);
        this.tvText.setTextColor(this.textColor);
        a.recycle();
    }

    public void setImageIcon(int id) {
        this.image.setBackgroundResource(id);
    }

    public void setTextViewText(int id) {
        this.tvText.setText(id);
    }

    public void setTextViewTextStr(String text) {
        this.tvText.setText(text);
    }

    public void setTextColor(int color) {
        this.tvText.setTextColor(color);
    }

    public void setImgTextSize(int dp) {
        this.tvText.setTextSize((float) dp);
    }
}
