package com.loc;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/* compiled from: StatisticsPubDataStrategy */
public final class cf extends cg {
    public cf() {
    }

    public cf(cg cgVar) {
        super(cgVar);
    }

    /* access modifiers changed from: protected */
    public final byte[] a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date()));
        stringBuffer.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        stringBuffer.append(UUID.randomUUID().toString());
        stringBuffer.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
        if (stringBuffer.length() != 53) {
            return new byte[0];
        }
        byte[] a = w.a(stringBuffer.toString());
        byte[] bArr2 = new byte[(a.length + bArr.length)];
        System.arraycopy(a, 0, bArr2, 0, a.length);
        System.arraycopy(bArr, 0, bArr2, a.length, bArr.length);
        return bArr2;
    }
}
