package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.iwown.sport_module.R;

public class GpsStartView extends FrameLayout {
    ImageView imageView;

    public GpsStartView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public GpsStartView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /* access modifiers changed from: protected */
    public void initView(Context context) {
        this.imageView = new ImageView(context);
        this.imageView.setPadding(22, 22, 22, 22);
        this.imageView.setImageResource(R.mipmap.start_circle3x);
        addView(this.imageView);
    }
}
