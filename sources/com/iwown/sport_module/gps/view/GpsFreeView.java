package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.iwown.sport_module.R;

public class GpsFreeView extends RelativeLayout {
    private int type;

    public GpsFreeView(Context context) {
        super(context);
        initView(context);
    }

    public GpsFreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /* access modifiers changed from: protected */
    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sport_module_gps_model_base, this);
    }
}
