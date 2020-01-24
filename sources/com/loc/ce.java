package com.loc;

import android.content.Context;
import android.os.Build;
import com.alibaba.android.arouter.utils.Consts;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.ByteArrayOutputStream;

/* compiled from: StatisticsHeaderDataStrategy */
public final class ce extends cg {
    public static int a = 13;
    public static int b = 6;
    private Context e;

    public ce(Context context, cg cgVar) {
        super(cgVar);
        this.e = context;
    }

    private static byte[] a(Context context) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[0];
        try {
            w.a(byteArrayOutputStream, "1.2." + a + Consts.DOT + b);
            w.a(byteArrayOutputStream, "Android");
            w.a(byteArrayOutputStream, p.v(context));
            w.a(byteArrayOutputStream, p.m(context));
            w.a(byteArrayOutputStream, p.h(context));
            w.a(byteArrayOutputStream, Build.MANUFACTURER);
            w.a(byteArrayOutputStream, Build.MODEL);
            w.a(byteArrayOutputStream, Build.DEVICE);
            w.a(byteArrayOutputStream, p.y(context));
            w.a(byteArrayOutputStream, l.c(context));
            w.a(byteArrayOutputStream, l.d(context));
            w.a(byteArrayOutputStream, l.f(context));
            byteArrayOutputStream.write(new byte[]{0});
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th) {
                th = th;
                ThrowableExtension.printStackTrace(th);
                return bArr;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        return bArr;
    }

    /* access modifiers changed from: protected */
    public final byte[] a(byte[] bArr) {
        byte[] a2 = a(this.e);
        byte[] bArr2 = new byte[(a2.length + bArr.length)];
        System.arraycopy(a2, 0, bArr2, 0, a2.length);
        System.arraycopy(bArr, 0, bArr2, a2.length, bArr.length);
        return bArr2;
    }
}
