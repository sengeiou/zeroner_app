package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzfhz extends zzfhy<FieldDescriptorType, Object> {
    zzfhz(int i) {
        super(i, null);
    }

    public final void zzbiy() {
        if (!isImmutable()) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= zzczj()) {
                    break;
                }
                Entry zzmb = zzmb(i2);
                if (((zzffs) zzmb.getKey()).zzcxj()) {
                    zzmb.setValue(Collections.unmodifiableList((List) zzmb.getValue()));
                }
                i = i2 + 1;
            }
            for (Entry entry : zzczk()) {
                if (((zzffs) entry.getKey()).zzcxj()) {
                    entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                }
            }
        }
        super.zzbiy();
    }
}
