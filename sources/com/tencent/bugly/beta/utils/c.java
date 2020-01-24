package com.tencent.bugly.beta.utils;

import android.util.Log;
import com.tencent.bugly.proguard.an;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/* compiled from: BUGLY */
public class c {
    private static HashMap<Long, String> u = new HashMap<>();
    private a a = null;
    private String b = null;
    private long c = 0;
    private long d = 0;
    private HashMap<String, b> e = null;
    private byte[] f = new byte[16];
    private long g = 0;
    private long h = 0;
    private String i = null;
    private long j = 0;
    private long k = 0;
    private long l = 0;
    private long m = 0;
    private long n = 0;
    private long o = 0;
    private long p = 0;
    private long q = 0;
    private long r = 0;
    private long s = 0;
    private long t = 0;

    /* compiled from: BUGLY */
    public static class a {
        private long a = -1;
        private long b = 0;
        private long c = 0;
        private long d = -1;
        private long e = -1;
        private long f = 0;
        private long g = 0;
        private long h = 0;
        private long i = 0;
        private long j = 0;

        public long a() {
            return this.a;
        }

        public long b() {
            return this.d;
        }

        public long c() {
            return this.e;
        }

        public long d() {
            return this.f;
        }

        public long e() {
            return this.j;
        }

        public synchronized void a(long j2) {
            this.a = j2;
        }

        public synchronized void b(long j2) {
            this.b = j2;
        }

        public synchronized void c(long j2) {
            this.c = j2;
        }

        public synchronized void d(long j2) {
            this.e = j2;
        }

        public synchronized void e(long j2) {
            this.d = j2;
        }

        public synchronized void f(long j2) {
            this.f = j2;
        }

        public synchronized void g(long j2) {
            this.g = j2;
        }

        public synchronized void h(long j2) {
            this.h = j2;
        }

        public synchronized void i(long j2) {
            this.i = j2;
        }

        public synchronized void j(long j2) {
            this.j = j2;
        }
    }

    /* compiled from: BUGLY */
    public static class b {
        private String a = null;
        private long b = -1;
        private long c = -1;
        private long d = 0;
        private long e = 0;

        public long a() {
            return this.c;
        }

        public synchronized void a(String str) {
            this.a = str;
        }

        public synchronized void a(long j) {
            this.b = j;
        }

        public synchronized void b(long j) {
            this.c = j;
        }

        public synchronized void c(long j) {
            this.d = j;
        }

        public synchronized void d(long j) {
            this.e = j;
        }
    }

    public c(String str) {
        this.b = str;
        u.put(Long.valueOf(3), "x86");
        u.put(Long.valueOf(7), "x86");
        u.put(Long.valueOf(8), "mips");
        u.put(Long.valueOf(10), "mips");
        u.put(Long.valueOf(40), "armeabi");
        u.put(Long.valueOf(62), "x86_64");
        u.put(Long.valueOf(183), "arm64-v8a");
    }

    private String b() {
        return this.i;
    }

    private long c() {
        return this.m;
    }

    private long d() {
        return this.s;
    }

    private long e() {
        return this.t;
    }

    public synchronized String a() {
        String a2;
        if (!f()) {
            Log.e("ElfParser", "Failed to parseElfHeader elf header");
            a2 = null;
        } else {
            String b2 = b();
            if (!b2.equals("armeabi")) {
                a2 = b2;
            } else if (!i()) {
                Log.e("ElfParser", "Failed to parseElfHeader section table");
                a2 = b2;
            } else {
                b bVar = (b) this.e.get(".ARM.attributes");
                if (bVar == null) {
                    Log.e("ElfParser", "No .ARM.attributes section in the elf file");
                    a2 = b2;
                } else {
                    a2 = b.a(this.b, this.d, bVar.a());
                }
            }
        }
        return a2;
    }

    private static String a(long j2, long j3) {
        String str = (String) u.get(Long.valueOf(j2));
        if (64 != j3 || !str.equals("mips")) {
            return str;
        }
        return "mips64";
    }

    private boolean f() {
        if (!l()) {
            return false;
        }
        if (!g()) {
            j();
            return false;
        }
        j();
        return true;
    }

    private synchronized boolean g() {
        boolean z = false;
        synchronized (this) {
            if (!h()) {
                Log.e("ElfParser", "Faile to parseElfHeader header indent of elf");
            } else {
                try {
                    this.g = this.a.g();
                    this.h = this.a.g();
                    this.i = a(this.h, this.c);
                    this.j = this.a.h();
                    if (32 == this.c) {
                        long h2 = this.a.h();
                        this.j = h2;
                        this.k = h2;
                        this.l = this.a.h();
                        this.m = this.a.h();
                    } else if (64 == this.c) {
                        long i2 = this.a.i();
                        this.j = i2;
                        this.k = i2;
                        this.l = this.a.i();
                        this.m = this.a.i();
                    } else {
                        Log.e("ElfParser", "File format error");
                    }
                    this.n = this.a.h();
                    this.o = this.a.g();
                    this.p = this.a.g();
                    this.q = this.a.g();
                    this.r = this.a.g();
                    this.s = this.a.g();
                    this.t = this.a.g();
                    z = true;
                } catch (IOException e2) {
                    Log.e("ElfParser", e2.getMessage());
                }
            }
        }
        return z;
    }

    private synchronized boolean h() {
        boolean z = false;
        synchronized (this) {
            if (!this.a.a(this.f)) {
                Log.e("ElfParser", "Fail to parseElfHeader elf indentification");
            } else if (!a(this.f)) {
                Log.e("ElfParser", "Not a elf file: " + this.b);
            } else {
                this.c = a(this.f[4]);
                if (0 == this.c) {
                    Log.e("ElfParser", "File format error: " + this.f[4]);
                } else {
                    this.d = b(this.f[5]);
                    if (a.a == this.d) {
                        Log.e("ElfParser", "Endian error: " + this.f[5]);
                    } else {
                        this.a.a(this.d);
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    private static boolean a(byte[] bArr) {
        if (bArr.length >= 3 && Byte.MAX_VALUE == bArr[0] && 69 == bArr[1] && 76 == bArr[2] && 70 == bArr[3]) {
            return true;
        }
        return false;
    }

    private long a(byte b2) {
        if (1 == b2) {
            return 32;
        }
        if (2 == b2) {
            return 64;
        }
        return 0;
    }

    private long b(byte b2) {
        if (1 == b2) {
            return a.c;
        }
        if (2 == b2) {
            return a.b;
        }
        return a.a;
    }

    private synchronized boolean i() {
        boolean z;
        this.e = a(c(), d(), e());
        if (this.e == null) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private synchronized void j() {
        if (this.a != null) {
            if (this.a.a()) {
                this.a = null;
            }
        }
    }

    private synchronized boolean k() {
        boolean z;
        if (this.a != null) {
            j();
        }
        try {
            this.a = new a(this.b, this.d);
            z = true;
        } catch (Exception e2) {
            Log.e("ElfParser", e2.getMessage());
            z = false;
        }
        return z;
    }

    private synchronized boolean l() {
        boolean z;
        if (this.a != null) {
            j();
        }
        try {
            this.a = new a(this.b);
            z = true;
        } catch (Exception e2) {
            Log.e("ElfParser", e2.getMessage());
            z = false;
        }
        return z;
    }

    private synchronized HashMap<String, b> a(long j2, long j3, long j4) {
        HashMap<String, b> hashMap = null;
        synchronized (this) {
            if (!k()) {
                j();
            } else if (!this.a.b(j2)) {
                j();
            } else {
                hashMap = b(j3, j4);
                j();
            }
        }
        return hashMap;
    }

    private synchronized HashMap<String, b> b(long j2, long j3) {
        HashMap<String, b> hashMap;
        synchronized (this) {
            if (j2 <= 0 || j3 <= 0) {
                an.d("The SO file is invalid or has a shell.", new Object[0]);
                hashMap = null;
            } else {
                Vector vector = new Vector();
                for (int i2 = 0; ((long) i2) < j2; i2++) {
                    vector.add(m());
                }
                a aVar = (a) vector.get((int) j3);
                long length = new File(this.b).length();
                an.c("File length = %d", Long.valueOf(length));
                if (aVar.c() >= length) {
                    an.d("The SO file is invalid or has a shell.", new Object[0]);
                    hashMap = null;
                } else {
                    d dVar = new d(this.b, aVar.c(), aVar.d());
                    HashMap<String, b> hashMap2 = new HashMap<>();
                    Iterator it = vector.iterator();
                    while (it.hasNext()) {
                        a aVar2 = (a) it.next();
                        String a2 = dVar.a(aVar2.a());
                        b bVar = new b();
                        bVar.a(a2);
                        bVar.a(aVar2.b());
                        bVar.b(aVar2.c());
                        bVar.c(aVar2.d());
                        bVar.d(aVar2.e());
                        hashMap2.put(a2, bVar);
                    }
                    dVar.a();
                    hashMap = hashMap2;
                }
            }
        }
        return hashMap;
    }

    private synchronized a m() {
        a aVar = null;
        synchronized (this) {
            a aVar2 = new a();
            try {
                aVar2.a(this.a.h());
                aVar2.b(this.a.h());
                if (32 == this.c) {
                    aVar2.c(this.a.h());
                    aVar2.e(this.a.h());
                    aVar2.d(this.a.h());
                    aVar2.f(this.a.h());
                } else if (64 == this.c) {
                    aVar2.c(this.a.i());
                    aVar2.e(this.a.i());
                    aVar2.d(this.a.i());
                    aVar2.f(this.a.i());
                } else {
                    Log.e("ElfParser", "File format error");
                }
                aVar2.g(this.a.h());
                aVar2.h(this.a.h());
                if (32 == this.c) {
                    aVar2.i(this.a.h());
                    aVar2.j(this.a.h());
                } else if (64 == this.c) {
                    aVar2.i(this.a.i());
                    aVar2.j(this.a.i());
                } else {
                    Log.e("ElfParser", "File format error");
                }
                aVar = aVar2;
            } catch (IOException e2) {
                Log.e("ElfParser", e2.getMessage());
            }
        }
        return aVar;
    }
}
