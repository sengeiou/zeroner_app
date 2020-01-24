package com.google.android.gms.internal;

import com.github.mikephil.charting.utils.Utils;

public enum zzfjd {
    INT(Integer.valueOf(0)),
    LONG(Long.valueOf(0)),
    FLOAT(Float.valueOf(0.0f)),
    DOUBLE(Double.valueOf(Utils.DOUBLE_EPSILON)),
    BOOLEAN(Boolean.valueOf(false)),
    STRING(""),
    BYTE_STRING(zzfes.zzpfg),
    ENUM(null),
    MESSAGE(null);
    
    private final Object zzpmt;

    private zzfjd(Object obj) {
        this.zzpmt = obj;
    }
}
