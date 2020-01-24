package com.youzan.sdk.tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: HttpCookie */
public final class c {
    /* access modifiers changed from: private */

    /* renamed from: ˊ reason: contains not printable characters */
    public static final TimeZone f333 = TimeZone.getTimeZone("GMT");

    /* renamed from: ˋ reason: contains not printable characters */
    private static final ThreadLocal<DateFormat> f334 = new ThreadLocal<DateFormat>() {
        /* access modifiers changed from: protected */
        /* renamed from: ˊ reason: contains not printable characters */
        public DateFormat initialValue() {
            DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            rfc1123.setLenient(false);
            rfc1123.setTimeZone(c.f333);
            return rfc1123;
        }
    };

    /* renamed from: ʻ reason: contains not printable characters */
    private final String f335;

    /* renamed from: ʼ reason: contains not printable characters */
    private final String f336;

    /* renamed from: ʽ reason: contains not printable characters */
    private final boolean f337;

    /* renamed from: ʾ reason: contains not printable characters */
    private final boolean f338;

    /* renamed from: ˎ reason: contains not printable characters */
    private final String f339;

    /* renamed from: ˏ reason: contains not printable characters */
    private final String f340;

    /* renamed from: ͺ reason: contains not printable characters */
    private final boolean f341;

    /* renamed from: ι reason: contains not printable characters */
    private final boolean f342;

    /* renamed from: ᐝ reason: contains not printable characters */
    private final long f343;

    /* compiled from: HttpCookie */
    public static final class a {

        /* renamed from: ι reason: contains not printable characters */
        private static final long f344 = 253402300799999L;

        /* renamed from: ʻ reason: contains not printable characters */
        boolean f345;

        /* renamed from: ʼ reason: contains not printable characters */
        boolean f346;

        /* renamed from: ʽ reason: contains not printable characters */
        boolean f347;

        /* renamed from: ˊ reason: contains not printable characters */
        String f348;

        /* renamed from: ˋ reason: contains not printable characters */
        String f349;

        /* renamed from: ˎ reason: contains not printable characters */
        long f350 = 253402300799999L;

        /* renamed from: ˏ reason: contains not printable characters */
        String f351;

        /* renamed from: ͺ reason: contains not printable characters */
        boolean f352;

        /* renamed from: ᐝ reason: contains not printable characters */
        String f353 = "/";

        /* renamed from: ˊ reason: contains not printable characters */
        public a m102(String name) {
            this.f348 = name;
            return this;
        }

        /* renamed from: ˋ reason: contains not printable characters */
        public a m104(String value) {
            this.f349 = value != null ? value.trim() : null;
            return this;
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public a m101(long expiresAt) {
            if (expiresAt <= 0) {
                expiresAt = Long.MIN_VALUE;
            }
            if (expiresAt > 253402300799999L) {
                expiresAt = 253402300799999L;
            }
            this.f350 = expiresAt;
            this.f347 = true;
            return this;
        }

        /* renamed from: ˎ reason: contains not printable characters */
        public a m105(String domain) {
            return m99(domain, false);
        }

        /* renamed from: ˏ reason: contains not printable characters */
        public a m107(String domain) {
            return m99(domain, true);
        }

        /* renamed from: ˊ reason: contains not printable characters */
        private a m99(String domain, boolean hostOnly) {
            this.f351 = domain;
            this.f352 = hostOnly;
            return this;
        }

        /* renamed from: ᐝ reason: contains not printable characters */
        public a m108(String path) {
            this.f353 = path;
            return this;
        }

        /* renamed from: ˊ reason: contains not printable characters */
        public a m100() {
            this.f345 = true;
            return this;
        }

        /* renamed from: ˋ reason: contains not printable characters */
        public a m103() {
            this.f346 = true;
            return this;
        }

        /* renamed from: ˎ reason: contains not printable characters */
        public c m106() {
            return new c(this);
        }
    }

    private c(a builder) {
        this.f339 = builder.f348;
        this.f340 = builder.f349;
        this.f343 = builder.f350;
        this.f335 = builder.f351;
        this.f336 = builder.f353;
        this.f337 = builder.f345;
        this.f341 = builder.f346;
        this.f342 = builder.f347;
        this.f338 = builder.f352;
    }

    /* renamed from: ˊ reason: contains not printable characters */
    private static String m87(Date value) {
        return ((DateFormat) f334.get()).format(value);
    }

    /* renamed from: ˊ reason: contains not printable characters */
    public String m92() {
        return this.f339;
    }

    /* renamed from: ˋ reason: contains not printable characters */
    public String m93() {
        return this.f340;
    }

    /* renamed from: ˎ reason: contains not printable characters */
    public boolean m94() {
        return this.f342;
    }

    /* renamed from: ˏ reason: contains not printable characters */
    public long m95() {
        return this.f343;
    }

    /* renamed from: ᐝ reason: contains not printable characters */
    public boolean m97() {
        return this.f338;
    }

    /* renamed from: ʻ reason: contains not printable characters */
    public String m89() {
        return this.f335;
    }

    /* renamed from: ʼ reason: contains not printable characters */
    public String m90() {
        return this.f336;
    }

    /* renamed from: ʽ reason: contains not printable characters */
    public boolean m91() {
        return this.f341;
    }

    /* renamed from: ͺ reason: contains not printable characters */
    public boolean m96() {
        return this.f337;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.f339);
        result.append('=');
        result.append(this.f340);
        if (this.f342) {
            if (this.f343 == Long.MIN_VALUE) {
                result.append("; max-age=0");
            } else {
                result.append("; expires=").append(m87(new Date(this.f343)));
            }
        }
        if (!this.f338) {
            result.append("; domain=").append(this.f335);
        }
        result.append("; path=").append(this.f336);
        if (this.f337) {
            result.append("; secure");
        }
        if (this.f341) {
            result.append("; httponly");
        }
        return result.toString();
    }
}
