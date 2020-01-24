package com.loc;

import android.content.Context;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* compiled from: HeaderAddStrategy */
public final class cc extends cg {
    private Context a;
    private String b;
    private aa e;
    private Object[] f;

    public cc(Context context, cg cgVar, aa aaVar, String str, Object... objArr) {
        super(cgVar);
        this.a = context;
        this.b = str;
        this.e = aaVar;
        this.f = objArr;
    }

    private String b() {
        String str = "";
        try {
            return String.format(w.c(this.b), this.f);
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            aj.b(th, "ofm", "gpj");
            return str;
        }
    }

    /* access modifiers changed from: protected */
    public final byte[] a(byte[] bArr) throws CertificateException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        String a2 = w.a(bArr);
        if (TextUtils.isEmpty(a2)) {
            return null;
        }
        Context context = this.a;
        String a3 = w.a(this.e.b(w.a(b())));
        StringBuilder sb = new StringBuilder();
        sb.append("{\"pinfo\":\"").append(a3).append("\",\"els\":[");
        sb.append(a2);
        sb.append("]}");
        return w.a(sb.toString());
    }
}
