package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.zzbg;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public final class zza {
    public static <T> ArrayList<T> zza(T[] tArr) {
        ArrayList<T> arrayList = new ArrayList<>(r1);
        for (T add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static void zza(StringBuilder sb, double[] dArr) {
        int length = dArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Double.toString(dArr[i]));
        }
    }

    public static void zza(StringBuilder sb, float[] fArr) {
        int length = fArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Float.toString(fArr[i]));
        }
    }

    public static void zza(StringBuilder sb, long[] jArr) {
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Long.toString(jArr[i]));
        }
    }

    public static <T> void zza(StringBuilder sb, T[] tArr) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(tArr[i].toString());
        }
    }

    public static void zza(StringBuilder sb, String[] strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("\"").append(strArr[i]).append("\"");
        }
    }

    public static void zza(StringBuilder sb, boolean[] zArr) {
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(Boolean.toString(zArr[i]));
        }
    }

    public static byte[] zza(byte[]... bArr) {
        if (bArr.length == 0) {
            return new byte[0];
        }
        int i = 0;
        for (byte[] length : bArr) {
            i += length.length;
        }
        byte[] copyOf = Arrays.copyOf(bArr[0], i);
        int length2 = bArr[0].length;
        for (int i2 = 1; i2 < bArr.length; i2++) {
            byte[] bArr2 = bArr[i2];
            System.arraycopy(bArr2, 0, copyOf, length2, bArr2.length);
            length2 += bArr2.length;
        }
        return copyOf;
    }

    public static <T> T[] zza(T[] tArr, T... tArr2) {
        int i;
        int i2;
        int i3;
        if (tArr == null) {
            return null;
        }
        if (tArr2.length == 0) {
            return Arrays.copyOf(tArr, tArr.length);
        }
        T[] tArr3 = (Object[]) Array.newInstance(tArr2.getClass().getComponentType(), tArr.length);
        if (tArr2.length == 1) {
            int length = tArr.length;
            int i4 = 0;
            i = 0;
            while (i4 < length) {
                T t = tArr[i4];
                if (!zzbg.equal(tArr2[0], t)) {
                    i3 = i + 1;
                    tArr3[i] = t;
                } else {
                    i3 = i;
                }
                i4++;
                i = i3;
            }
        } else {
            int length2 = tArr.length;
            int i5 = 0;
            int i6 = 0;
            while (i5 < length2) {
                T t2 = tArr[i5];
                int length3 = tArr2.length;
                int i7 = 0;
                while (true) {
                    if (i7 >= length3) {
                        i7 = -1;
                        break;
                    } else if (zzbg.equal(tArr2[i7], t2)) {
                        break;
                    } else {
                        i7++;
                    }
                }
                if (!(i7 >= 0)) {
                    i2 = i + 1;
                    tArr3[i] = t2;
                } else {
                    i2 = i;
                }
                i5++;
                i6 = i2;
            }
        }
        if (tArr3 == null) {
            return null;
        }
        return i != tArr3.length ? Arrays.copyOf(tArr3, i) : tArr3;
    }

    public static Integer[] zzb(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        Integer[] numArr = new Integer[length];
        for (int i = 0; i < length; i++) {
            numArr[i] = Integer.valueOf(iArr[i]);
        }
        return numArr;
    }
}
