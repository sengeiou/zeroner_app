package com.tencent.bugly.proguard;

import com.github.mikephil.charting.utils.Utils;
import com.google.common.base.Ascii;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: BUGLY */
public final class k {
    protected String a = "GBK";
    private ByteBuffer b;

    /* compiled from: BUGLY */
    public static class a {
        public byte a;
        public int b;
    }

    public k() {
    }

    public k(byte[] bArr) {
        this.b = ByteBuffer.wrap(bArr);
    }

    public k(byte[] bArr, int i) {
        this.b = ByteBuffer.wrap(bArr);
        this.b.position(i);
    }

    public void a(byte[] bArr) {
        if (this.b != null) {
            this.b.clear();
        }
        this.b = ByteBuffer.wrap(bArr);
    }

    public static int a(a aVar, ByteBuffer byteBuffer) {
        byte b2 = byteBuffer.get();
        aVar.a = (byte) (b2 & Ascii.SI);
        aVar.b = (b2 & 240) >> 4;
        if (aVar.b != 15) {
            return 1;
        }
        aVar.b = byteBuffer.get();
        return 2;
    }

    public void a(a aVar) {
        a(aVar, this.b);
    }

    private int b(a aVar) {
        return a(aVar, this.b.duplicate());
    }

    private void b(int i) {
        this.b.position(this.b.position() + i);
    }

    public boolean a(int i) {
        try {
            a aVar = new a();
            while (true) {
                int b2 = b(aVar);
                if (i > aVar.b && aVar.a != 11) {
                    b(b2);
                    a(aVar.a);
                }
            }
            if (i == aVar.b) {
                return true;
            }
            return false;
        } catch (h | BufferUnderflowException e) {
            return false;
        }
    }

    public void a() {
        a aVar = new a();
        do {
            a(aVar);
            a(aVar.a);
        } while (aVar.a != 11);
    }

    private void b() {
        a aVar = new a();
        a(aVar);
        a(aVar.a);
    }

    private void a(byte b2) {
        int i = 0;
        switch (b2) {
            case 0:
                b(1);
                return;
            case 1:
                b(2);
                return;
            case 2:
                b(4);
                return;
            case 3:
                b(8);
                return;
            case 4:
                b(4);
                return;
            case 5:
                b(8);
                return;
            case 6:
                int i2 = this.b.get();
                if (i2 < 0) {
                    i2 += 256;
                }
                b(i2);
                return;
            case 7:
                b(this.b.getInt());
                return;
            case 8:
                int a2 = a(0, 0, true);
                while (i < a2 * 2) {
                    b();
                    i++;
                }
                return;
            case 9:
                int a3 = a(0, 0, true);
                while (i < a3) {
                    b();
                    i++;
                }
                return;
            case 10:
                a();
                return;
            case 11:
            case 12:
                return;
            case 13:
                a aVar = new a();
                a(aVar);
                if (aVar.a != 0) {
                    throw new h("skipField with invalid type, type value: " + b2 + ", " + aVar.a);
                }
                b(a(0, 0, true));
                return;
            default:
                throw new h("invalid type.");
        }
    }

    public boolean a(boolean z, int i, boolean z2) {
        if (a(0, i, z2) != 0) {
            return true;
        }
        return false;
    }

    public byte a(byte b2, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 0:
                    return this.b.get();
                case 12:
                    return 0;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return b2;
        } else {
            throw new h("require field not exist.");
        }
    }

    public short a(short s, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 0:
                    return (short) this.b.get();
                case 1:
                    return this.b.getShort();
                case 12:
                    return 0;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return s;
        } else {
            throw new h("require field not exist.");
        }
    }

    public int a(int i, int i2, boolean z) {
        if (a(i2)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 0:
                    return this.b.get();
                case 1:
                    return this.b.getShort();
                case 2:
                    return this.b.getInt();
                case 12:
                    return 0;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return i;
        } else {
            throw new h("require field not exist.");
        }
    }

    public long a(long j, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 0:
                    return (long) this.b.get();
                case 1:
                    return (long) this.b.getShort();
                case 2:
                    return (long) this.b.getInt();
                case 3:
                    return this.b.getLong();
                case 12:
                    return 0;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return j;
        } else {
            throw new h("require field not exist.");
        }
    }

    public float a(float f, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 4:
                    return this.b.getFloat();
                case 12:
                    return 0.0f;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return f;
        } else {
            throw new h("require field not exist.");
        }
    }

    public double a(double d, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 4:
                    return (double) this.b.getFloat();
                case 5:
                    return this.b.getDouble();
                case 12:
                    return Utils.DOUBLE_EPSILON;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return d;
        } else {
            throw new h("require field not exist.");
        }
    }

    public String a(int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 6:
                    int i2 = this.b.get();
                    if (i2 < 0) {
                        i2 += 256;
                    }
                    byte[] bArr = new byte[i2];
                    this.b.get(bArr);
                    try {
                        return new String(bArr, this.a);
                    } catch (UnsupportedEncodingException e) {
                        return new String(bArr);
                    }
                case 7:
                    int i3 = this.b.getInt();
                    if (i3 > 104857600 || i3 < 0) {
                        throw new h("String too long: " + i3);
                    }
                    byte[] bArr2 = new byte[i3];
                    this.b.get(bArr2);
                    try {
                        return new String(bArr2, this.a);
                    } catch (UnsupportedEncodingException e2) {
                        return new String(bArr2);
                    }
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new h("require field not exist.");
        }
    }

    public <K, V> HashMap<K, V> a(Map<K, V> map, int i, boolean z) {
        return (HashMap) a(new HashMap(), map, i, z);
    }

    private <K, V> Map<K, V> a(Map<K, V> map, Map<K, V> map2, int i, boolean z) {
        if (map2 == null || map2.isEmpty()) {
            return new HashMap();
        }
        Entry entry = (Entry) map2.entrySet().iterator().next();
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 8:
                    int a2 = a(0, 0, true);
                    if (a2 < 0) {
                        throw new h("size invalid: " + a2);
                    }
                    for (int i2 = 0; i2 < a2; i2++) {
                        map.put(a((T) key, 0, true), a((T) value, 1, true));
                    }
                    return map;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return map;
        } else {
            throw new h("require field not exist.");
        }
    }

    public boolean[] a(boolean[] zArr, int i, boolean z) {
        boolean[] zArr2 = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        zArr2 = new boolean[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            zArr2[i2] = a(zArr2[0], 0, true);
                        }
                        break;
                    } else {
                        throw new h("size invalid: " + a2);
                    }
                default:
                    throw new h("type mismatch.");
            }
        } else if (z) {
            throw new h("require field not exist.");
        }
        return zArr2;
    }

    public byte[] a(byte[] bArr, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 < 0) {
                        throw new h("size invalid: " + a2);
                    }
                    byte[] bArr2 = new byte[a2];
                    for (int i2 = 0; i2 < a2; i2++) {
                        bArr2[i2] = a(bArr2[0], 0, true);
                    }
                    return bArr2;
                case 13:
                    a aVar2 = new a();
                    a(aVar2);
                    if (aVar2.a != 0) {
                        throw new h("type mismatch, tag: " + i + ", type: " + aVar.a + ", " + aVar2.a);
                    }
                    int a3 = a(0, 0, true);
                    if (a3 < 0) {
                        throw new h("invalid size, tag: " + i + ", type: " + aVar.a + ", " + aVar2.a + ", size: " + a3);
                    }
                    byte[] bArr3 = new byte[a3];
                    this.b.get(bArr3);
                    return bArr3;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new h("require field not exist.");
        }
    }

    public short[] a(short[] sArr, int i, boolean z) {
        short[] sArr2 = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        sArr2 = new short[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            sArr2[i2] = a(sArr2[0], 0, true);
                        }
                        break;
                    } else {
                        throw new h("size invalid: " + a2);
                    }
                default:
                    throw new h("type mismatch.");
            }
        } else if (z) {
            throw new h("require field not exist.");
        }
        return sArr2;
    }

    public int[] a(int[] iArr, int i, boolean z) {
        int[] iArr2 = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        iArr2 = new int[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            iArr2[i2] = a(iArr2[0], 0, true);
                        }
                        break;
                    } else {
                        throw new h("size invalid: " + a2);
                    }
                default:
                    throw new h("type mismatch.");
            }
        } else if (z) {
            throw new h("require field not exist.");
        }
        return iArr2;
    }

    public long[] a(long[] jArr, int i, boolean z) {
        long[] jArr2 = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        jArr2 = new long[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            jArr2[i2] = a(jArr2[0], 0, true);
                        }
                        break;
                    } else {
                        throw new h("size invalid: " + a2);
                    }
                default:
                    throw new h("type mismatch.");
            }
        } else if (z) {
            throw new h("require field not exist.");
        }
        return jArr2;
    }

    public float[] a(float[] fArr, int i, boolean z) {
        float[] fArr2 = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        fArr2 = new float[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            fArr2[i2] = a(fArr2[0], 0, true);
                        }
                        break;
                    } else {
                        throw new h("size invalid: " + a2);
                    }
                default:
                    throw new h("type mismatch.");
            }
        } else if (z) {
            throw new h("require field not exist.");
        }
        return fArr2;
    }

    public double[] a(double[] dArr, int i, boolean z) {
        double[] dArr2 = null;
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 >= 0) {
                        dArr2 = new double[a2];
                        for (int i2 = 0; i2 < a2; i2++) {
                            dArr2[i2] = a(dArr2[0], 0, true);
                        }
                        break;
                    } else {
                        throw new h("size invalid: " + a2);
                    }
                default:
                    throw new h("type mismatch.");
            }
        } else if (z) {
            throw new h("require field not exist.");
        }
        return dArr2;
    }

    public <T> T[] a(T[] tArr, int i, boolean z) {
        if (tArr != null && tArr.length != 0) {
            return b(tArr[0], i, z);
        }
        throw new h("unable to get type of key and value.");
    }

    public <T> List<T> a(List<T> list, int i, boolean z) {
        if (list == null || list.isEmpty()) {
            return new ArrayList();
        }
        Object[] b2 = b(list.get(0), i, z);
        if (b2 == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object add : b2) {
            arrayList.add(add);
        }
        return arrayList;
    }

    private <T> T[] b(T t, int i, boolean z) {
        if (a(i)) {
            a aVar = new a();
            a(aVar);
            switch (aVar.a) {
                case 9:
                    int a2 = a(0, 0, true);
                    if (a2 < 0) {
                        throw new h("size invalid: " + a2);
                    }
                    T[] tArr = (Object[]) Array.newInstance(t.getClass(), a2);
                    for (int i2 = 0; i2 < a2; i2++) {
                        tArr[i2] = a(t, 0, true);
                    }
                    return tArr;
                default:
                    throw new h("type mismatch.");
            }
        } else if (!z) {
            return null;
        } else {
            throw new h("require field not exist.");
        }
    }

    public m a(m mVar, int i, boolean z) {
        m mVar2 = null;
        if (a(i)) {
            try {
                mVar2 = (m) mVar.getClass().newInstance();
                a aVar = new a();
                a(aVar);
                if (aVar.a != 10) {
                    throw new h("type mismatch.");
                }
                mVar2.a(this);
                a();
            } catch (Exception e) {
                throw new h(e.getMessage());
            }
        } else if (z) {
            throw new h("require field not exist.");
        }
        return mVar2;
    }

    public <T> Object a(T t, int i, boolean z) {
        if (t instanceof Byte) {
            return Byte.valueOf(a(0, i, z));
        }
        if (t instanceof Boolean) {
            return Boolean.valueOf(a(false, i, z));
        }
        if (t instanceof Short) {
            return Short.valueOf(a(0, i, z));
        }
        if (t instanceof Integer) {
            return Integer.valueOf(a(0, i, z));
        }
        if (t instanceof Long) {
            return Long.valueOf(a(0, i, z));
        }
        if (t instanceof Float) {
            return Float.valueOf(a(0.0f, i, z));
        }
        if (t instanceof Double) {
            return Double.valueOf(a((double) Utils.DOUBLE_EPSILON, i, z));
        }
        if (t instanceof String) {
            return String.valueOf(a(i, z));
        }
        if (t instanceof Map) {
            return a((Map) t, i, z);
        }
        if (t instanceof List) {
            return a((List) t, i, z);
        }
        if (t instanceof m) {
            return a((m) t, i, z);
        }
        if (!t.getClass().isArray()) {
            throw new h("read object error: unsupport type.");
        } else if ((t instanceof byte[]) || (t instanceof Byte[])) {
            return a((byte[]) null, i, z);
        } else {
            if (t instanceof boolean[]) {
                return a((boolean[]) null, i, z);
            }
            if (t instanceof short[]) {
                return a((short[]) null, i, z);
            }
            if (t instanceof int[]) {
                return a((int[]) null, i, z);
            }
            if (t instanceof long[]) {
                return a((long[]) null, i, z);
            }
            if (t instanceof float[]) {
                return a((float[]) null, i, z);
            }
            if (t instanceof double[]) {
                return a((double[]) null, i, z);
            }
            return a((T[]) (Object[]) t, i, z);
        }
    }

    public int a(String str) {
        this.a = str;
        return 0;
    }
}
