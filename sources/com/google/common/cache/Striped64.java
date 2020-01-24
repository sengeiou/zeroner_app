package com.google.common.cache;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import sun.misc.Unsafe;

abstract class Striped64 extends Number {
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    static final ThreadHashCode threadHashCode = new ThreadHashCode();
    volatile transient long base;
    volatile transient int busy;
    volatile transient Cell[] cells;

    static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;
        volatile long p0;
        volatile long p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;
        volatile long q0;
        volatile long q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        volatile long value;

        Cell(long x) {
            this.value = x;
        }

        /* access modifiers changed from: 0000 */
        public final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        static {
            try {
                UNSAFE = Striped64.getUnsafe();
                valueOffset = UNSAFE.objectFieldOffset(Cell.class.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    static final class HashCode {
        static final Random rng = new Random();
        int code;

        HashCode() {
            int h = rng.nextInt();
            if (h == 0) {
                h = 1;
            }
            this.code = h;
        }
    }

    static final class ThreadHashCode extends ThreadLocal<HashCode> {
        ThreadHashCode() {
        }

        public HashCode initialValue() {
            return new HashCode();
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract long fn(long j, long j2);

    static {
        try {
            UNSAFE = getUnsafe();
            Class<Striped64> cls = Striped64.class;
            baseOffset = UNSAFE.objectFieldOffset(cls.getDeclaredField("base"));
            busyOffset = UNSAFE.objectFieldOffset(cls.getDeclaredField("busy"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    Striped64() {
    }

    /* access modifiers changed from: 0000 */
    public final boolean casBase(long cmp, long val) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, cmp, val);
    }

    /* access modifiers changed from: 0000 */
    public final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public final void retryUpdate(long x, HashCode hc, boolean wasUncontended) {
        int h = hc.code;
        boolean collide = false;
        while (true) {
            Cell[] as = this.cells;
            if (as != null) {
                int n = as.length;
                if (n > 0) {
                    Cell a = as[(n - 1) & h];
                    if (a != null) {
                        if (wasUncontended) {
                            long v = a.value;
                            if (a.cas(v, fn(v, x))) {
                                break;
                            } else if (n >= NCPU || this.cells != as) {
                                collide = false;
                            } else if (!collide) {
                                collide = true;
                            } else if (this.busy == 0 && casBusy()) {
                                try {
                                    if (this.cells == as) {
                                        Cell[] rs = new Cell[(n << 1)];
                                        for (int i = 0; i < n; i++) {
                                            rs[i] = as[i];
                                        }
                                        this.cells = rs;
                                    }
                                    this.busy = 0;
                                    collide = false;
                                } catch (Throwable th) {
                                    this.busy = 0;
                                    throw th;
                                }
                            }
                        } else {
                            wasUncontended = true;
                        }
                    } else {
                        if (this.busy == 0) {
                            Cell cell = new Cell(x);
                            if (this.busy == 0 && casBusy()) {
                                boolean created = false;
                                try {
                                    Cell[] rs2 = this.cells;
                                    if (rs2 != null) {
                                        int m = rs2.length;
                                        if (m > 0) {
                                            int j = (m - 1) & h;
                                            if (rs2[j] == null) {
                                                rs2[j] = cell;
                                                created = true;
                                            }
                                        }
                                    }
                                    if (created) {
                                        break;
                                    }
                                } finally {
                                    this.busy = 0;
                                }
                            }
                        }
                        collide = false;
                    }
                    int h2 = h ^ (h << 13);
                    int h3 = h2 ^ (h2 >>> 17);
                    h = h3 ^ (h3 << 5);
                }
            }
            if (this.busy == 0 && this.cells == as && casBusy()) {
                boolean init = false;
                try {
                    if (this.cells == as) {
                        Cell[] rs3 = new Cell[2];
                        int i2 = h & 1;
                        Cell cell2 = new Cell(x);
                        rs3[i2] = cell2;
                        this.cells = rs3;
                        init = true;
                    }
                    if (init) {
                        break;
                    }
                } finally {
                    this.busy = 0;
                }
            } else {
                long v2 = this.base;
                if (casBase(v2, fn(v2, x))) {
                    break;
                }
            }
        }
        hc.code = h;
    }

    /* access modifiers changed from: 0000 */
    public final void internalReset(long initialValue) {
        Cell[] as = this.cells;
        this.base = initialValue;
        if (as != null) {
            for (Cell a : as) {
                if (a != null) {
                    a.value = initialValue;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        } catch (SecurityException e) {
            try {
                return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                    public Unsafe run() throws Exception {
                        Field[] arr$;
                        Class<Unsafe> cls = Unsafe.class;
                        for (Field f : cls.getDeclaredFields()) {
                            f.setAccessible(true);
                            Object x = f.get(null);
                            if (cls.isInstance(x)) {
                                return (Unsafe) cls.cast(x);
                            }
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }
                });
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        }
    }
}
