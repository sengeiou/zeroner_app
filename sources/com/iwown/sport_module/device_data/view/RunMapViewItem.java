package com.iwown.sport_module.device_data.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import com.iwown.sport_module.map.LongitudeAndLatitude;
import java.util.List;

public class RunMapViewItem extends RunMapLayout {
    public RunMapViewItem(Context context) {
        super(context);
    }

    public RunMapViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RunMapViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RunMapViewItem(Context context, int mapType) {
        super(context);
        this.mapType = mapType;
    }

    public void initView(Context context, Bundle savedInstanceState) {
    }

    private void initMapView() {
    }

    public void refreshDataView(int dev_type, String mDataFrom) {
    }

    public void drawAmapMap(List<LongitudeAndLatitude> list, int deviceType, int fromType) {
    }

    public int getMapHeight() {
        return 0;
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }
}
