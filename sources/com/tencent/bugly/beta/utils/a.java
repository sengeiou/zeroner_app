package com.tencent.bugly.beta.utils;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/* compiled from: BUGLY */
public class a {
    public static long a = -1;
    public static long b = 0;
    public static long c = 1;
    private byte[] d = new byte[2];
    private byte[] e = new byte[4];
    private byte[] f = new byte[8];
    private long g = c;
    private String h = null;
    private BufferedInputStream i = null;
    private long j = 0;
    private long k = 0;

    public synchronized void a(long j2) {
        this.g = j2;
    }

    public a(String str) throws Exception {
        this.h = str;
        this.i = new BufferedInputStream(new FileInputStream(this.h));
        this.j = 0;
        this.k = 0;
    }

    public a(String str, long j2) throws Exception {
        this.h = str;
        this.g = j2;
        this.i = new BufferedInputStream(new FileInputStream(this.h));
        this.j = 0;
        this.k = 0;
    }

    public synchronized boolean a() {
        boolean z;
        try {
            if (this.i != null) {
                this.i.close();
            }
            this.i = null;
            this.h = null;
            this.j = 0;
            this.k = 0;
            z = true;
        } catch (IOException e2) {
            Log.e("BinaryFileReader", e2.getMessage());
            z = false;
        }
        return z;
    }

    public synchronized boolean b(long j2) {
        boolean z = false;
        synchronized (this) {
            if (this.i == null) {
                Log.e("BinaryFileReader", "Please open file first！");
            } else if (j2 == 0) {
                z = true;
            } else {
                long j3 = j2;
                while (j3 > 0) {
                    try {
                        j3 -= this.i.skip(j3);
                    } catch (IOException e2) {
                        Log.e("BinaryFileReader", "Failed to skip file pointer！");
                    }
                }
                this.j += j2;
                z = true;
            }
        }
        return z;
    }

    public synchronized boolean a(byte[] bArr) {
        boolean z;
        try {
            this.i.read(bArr);
            this.j += (long) bArr.length;
            this.k += (long) bArr.length;
            z = true;
        } catch (IOException e2) {
            Log.e("BinaryFileReader", e2.getMessage());
            z = false;
        }
        return z;
    }

    public synchronized byte b() throws IOException {
        byte b2;
        b2 = 0;
        if (this.i == null) {
            Log.e("BinaryFileReader", "Failed to skip file pointer！");
        } else {
            b2 = (byte) this.i.read();
            this.j++;
            this.k++;
        }
        return b2;
    }

    public synchronized short c() throws IOException {
        short s;
        s = 0;
        if (this.i == null) {
            Log.e("BinaryFileReader", "Failed to skip file pointer！");
        } else {
            this.i.read(this.d);
            s = a(this.d, this.g);
            this.j += 2;
            this.k += 2;
        }
        return s;
    }

    public synchronized int d() throws IOException {
        int i2;
        i2 = 0;
        if (this.i == null) {
            Log.e("BinaryFileReader", "Failed to skip file pointer！");
        } else {
            this.i.read(this.e);
            i2 = b(this.e, this.g);
            this.j += 4;
            this.k += 4;
        }
        return i2;
    }

    public synchronized long e() throws IOException {
        long j2;
        j2 = 0;
        if (this.i == null) {
            Log.e("BinaryFileReader", "Failed to skip file pointer！");
        } else {
            this.i.read(this.f);
            j2 = c(this.f, this.g);
            this.j += 8;
            this.k += 8;
        }
        return j2;
    }

    public synchronized long f() throws IOException {
        return ((long) b()) & 255;
    }

    public synchronized long g() throws IOException {
        return ((long) c()) & 65535;
    }

    public synchronized long h() throws IOException {
        return ((long) d()) & 4294967295L;
    }

    public synchronized long i() throws IOException {
        return e();
    }

    private static short b(byte[] bArr) {
        if (bArr == null || bArr.length > 2) {
            return -1;
        }
        return (short) c(bArr);
    }

    private static int c(byte[] bArr) {
        if (bArr == null || bArr.length > 4) {
            return -1;
        }
        return (int) d(bArr);
    }

    private static long d(byte[] bArr) {
        if (bArr == null || bArr.length > 8) {
            return -1;
        }
        long j2 = 0;
        int length = bArr.length - 1;
        while (length >= 0) {
            long j3 = (((long) bArr[length]) & 255) | (j2 << 8);
            length--;
            j2 = j3;
        }
        return j2;
    }

    private static short e(byte[] bArr) {
        if (bArr == null || bArr.length > 2) {
            return -1;
        }
        return (short) f(bArr);
    }

    private static int f(byte[] bArr) {
        if (bArr == null || bArr.length > 4) {
            return -1;
        }
        return (int) g(bArr);
    }

    private static long g(byte[] bArr) {
        if (bArr == null || bArr.length > 8) {
            return -1;
        }
        long j2 = 0;
        int i2 = 0;
        while (i2 < bArr.length) {
            i2++;
            j2 = (((long) bArr[i2]) & 255) | (j2 << 8);
        }
        return j2;
    }

    public static short a(byte[] bArr, long j2) {
        if (j2 == c) {
            return b(bArr);
        }
        return e(bArr);
    }

    public static int b(byte[] bArr, long j2) {
        if (j2 == c) {
            return c(bArr);
        }
        return f(bArr);
    }

    public static long c(byte[] bArr, long j2) {
        if (j2 == c) {
            return d(bArr);
        }
        return g(bArr);
    }
}
