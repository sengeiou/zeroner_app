package com.google.android.gms.internal;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

final class zzan extends FilterInputStream {
    private long bytesRead;
    private final long zzcc;

    zzan(InputStream inputStream, long j) {
        super(inputStream);
        this.zzcc = j;
    }

    public final int read() throws IOException {
        int read = super.read();
        if (read != -1) {
            this.bytesRead++;
        }
        return read;
    }

    public final int read(byte[] bArr, int i, int i2) throws IOException {
        int read = super.read(bArr, i, i2);
        if (read != -1) {
            this.bytesRead += (long) read;
        }
        return read;
    }

    /* access modifiers changed from: 0000 */
    public final long zzn() {
        return this.zzcc - this.bytesRead;
    }
}
