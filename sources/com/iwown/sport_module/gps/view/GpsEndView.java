package com.iwown.sport_module.gps.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import com.iwown.sport_module.gps.service.LocationImpl;

public class GpsEndView extends LockBaseView {
    public GpsEndView(@NonNull Context context) {
        super(context);
    }

    public GpsEndView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void animBack() {
        super.animBack();
        if (this.isLock) {
            LocationImpl.getInstance().finishLocation(getContext());
        }
    }
}
