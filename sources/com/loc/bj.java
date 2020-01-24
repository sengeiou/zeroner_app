package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* compiled from: StrictLineReader */
public final class bj implements Closeable {
    private final InputStream a;
    /* access modifiers changed from: private */
    public final Charset b;
    private byte[] c;
    private int d;
    private int e;

    public bj(InputStream inputStream, Charset charset) {
        this(inputStream, charset, 0);
    }

    private bj(InputStream inputStream, Charset charset, byte b2) {
        if (inputStream == null || charset == null) {
            throw new NullPointerException();
        } else if (!charset.equals(bk.a)) {
            throw new IllegalArgumentException("Unsupported encoding");
        } else {
            this.a = inputStream;
            this.b = charset;
            this.c = new byte[8192];
        }
    }

    private void b() throws IOException {
        int read = this.a.read(this.c, 0, this.c.length);
        if (read == -1) {
            throw new EOFException();
        }
        this.d = 0;
        this.e = read;
    }

    public final String a() throws IOException {
        int i;
        String byteArrayOutputStream;
        synchronized (this.a) {
            if (this.c == null) {
                throw new IOException("LineReader is closed");
            }
            if (this.d >= this.e) {
                b();
            }
            int i2 = this.d;
            while (true) {
                if (i2 == this.e) {
                    AnonymousClass1 r1 = new ByteArrayOutputStream((this.e - this.d) + 80) {
                        public final String toString() {
                            try {
                                return new String(this.buf, 0, (this.count <= 0 || this.buf[this.count + -1] != 13) ? this.count : this.count - 1, bj.this.b.name());
                            } catch (UnsupportedEncodingException e) {
                                throw new AssertionError(e);
                            }
                        }
                    };
                    loop1:
                    while (true) {
                        r1.write(this.c, this.d, this.e - this.d);
                        this.e = -1;
                        b();
                        i = this.d;
                        while (true) {
                            if (i != this.e) {
                                if (this.c[i] == 10) {
                                    break loop1;
                                }
                                i++;
                            }
                        }
                    }
                    if (i != this.d) {
                        r1.write(this.c, this.d, i - this.d);
                    }
                    this.d = i + 1;
                    byteArrayOutputStream = r1.toString();
                } else if (this.c[i2] == 10) {
                    byteArrayOutputStream = new String(this.c, this.d, ((i2 == this.d || this.c[i2 + -1] != 13) ? i2 : i2 - 1) - this.d, this.b.name());
                    this.d = i2 + 1;
                } else {
                    i2++;
                }
            }
        }
        return byteArrayOutputStream;
    }

    public final void close() throws IOException {
        synchronized (this.a) {
            if (this.c != null) {
                this.c = null;
                this.a.close();
            }
        }
    }
}
