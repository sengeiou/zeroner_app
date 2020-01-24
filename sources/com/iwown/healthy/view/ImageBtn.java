package com.iwown.healthy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.healthy.zeroner_pro.R;

public class ImageBtn extends LinearLayout {
    private ImageView image;
    private int imageResouse;
    private String imageText;
    private int imgTextSize;
    private int textColor;
    private TextView tvText;

    public ImageBtn(Context context) {
        super(context);
    }

    public ImageBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.button, this);
        this.image = (ImageView) findViewById(R.id.imageView);
        this.tvText = (TextView) findViewById(R.id.imageText);
        TypedArray a = context.obtainStyledAttributes(attrs, com.iwown.healthy.R.styleable.app_button_resourse);
        this.imageResouse = a.getResourceId(2, 5);
        this.imageText = a.getString(3);
        this.textColor = a.getColor(4, 3);
        this.imgTextSize = a.getDimensionPixelSize(5, 17);
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
