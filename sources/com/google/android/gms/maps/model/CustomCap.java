package com.google.android.gms.maps.model;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbq;

public final class CustomCap extends Cap {
    public final BitmapDescriptor bitmapDescriptor;
    public final float refWidth;

    public CustomCap(@NonNull BitmapDescriptor bitmapDescriptor2) {
        this(bitmapDescriptor2, 10.0f);
    }

    public CustomCap(@NonNull BitmapDescriptor bitmapDescriptor2, float f) {
        BitmapDescriptor bitmapDescriptor3 = (BitmapDescriptor) zzbq.checkNotNull(bitmapDescriptor2, "bitmapDescriptor must not be null");
        String str = "refWidth must be positive";
        if (f <= 0.0f) {
            throw new IllegalArgumentException(str);
        }
        super(bitmapDescriptor3, f);
        this.bitmapDescriptor = bitmapDescriptor2;
        this.refWidth = f;
    }

    public final String toString() {
        String valueOf = String.valueOf(this.bitmapDescriptor);
        return new StringBuilder(String.valueOf(valueOf).length() + 55).append("[CustomCap: bitmapDescriptor=").append(valueOf).append(" refWidth=").append(this.refWidth).append("]").toString();
    }
}
