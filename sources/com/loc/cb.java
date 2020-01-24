package com.loc;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* compiled from: EncryptRsaDataStrategy */
public final class cb extends cg {
    private aa a = new ad();

    public cb() {
    }

    public cb(cg cgVar) {
        super(cgVar);
    }

    /* access modifiers changed from: protected */
    public final byte[] a(byte[] bArr) throws CertificateException, NoSuchAlgorithmException, IOException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        return this.a.b(bArr);
    }
}
