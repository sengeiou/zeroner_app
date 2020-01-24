package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import java.util.List;

public abstract class RunMapLayout extends RelativeLayout {
    public static final int AMAP_MAP = 1;
    public static final int GOOGLE_MAP = 0;
    protected int mapType = 0;

    public abstract void drawAmapMap(List<LongitudeAndLatitude> list, int i, int i2);

    public abstract int getMapHeight();

    public abstract void initView(Context context, Bundle bundle);

    public abstract void onDestroy();

    public abstract void onPause();

    public abstract void onResume();

    public abstract void onStart();

    public abstract void onStop();

    public abstract void refreshDataView(int i, String str);

    public RunMapLayout(Context context) {
        super(context);
    }

    public RunMapLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RunMapLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RunMapLayout(Context context, int mapType2) {
        super(context);
        this.mapType = mapType2;
    }
}
