package com.iwown.device_module.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.device_module.R;

public class SettingItems extends ConstraintLayout {
    View mView;
    ImageView picIcon;
    TextView title;

    public SettingItems(Context context) {
        super(context);
    }

    public SettingItems(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.device_module_settings_itemsView);
        this.picIcon.setImageDrawable(a.getDrawable(R.styleable.device_module_settings_itemsView_device_module_image_pic));
        this.title.setText(a.getString(R.styleable.device_module_settings_itemsView_device_module_title_text));
        a.recycle();
    }

    public SettingItems(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        this.mView = View.inflate(context, R.layout.device_module_setting_list_items, this);
        this.title = (TextView) this.mView.findViewById(R.id.item_title);
        this.picIcon = (ImageView) this.mView.findViewById(R.id.image_pic_logo);
    }

    public void setTitleText(String text) {
        this.title.setText(text);
    }

    public void setImagePic(Drawable drawable) {
        this.picIcon.setImageDrawable(drawable);
    }
}
