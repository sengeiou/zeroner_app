package com.iwown.device_module.device_alarm_schedule.common;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.dinuscxj.refresh.IRefreshStatus;

public class SimpleHead extends AppCompatTextView implements IRefreshStatus {
    public SimpleHead(Context context) {
        super(context);
    }

    public SimpleHead(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void reset() {
    }

    public void refreshing() {
    }

    public void pullToRefresh() {
    }

    public void releaseToRefresh() {
    }

    public void pullProgress(float pullDistance, float pullProgress) {
    }
}
