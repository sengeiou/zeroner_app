package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@KeepName
public final class DataHolder extends zzbfm implements Closeable {
    public static final Creator<DataHolder> CREATOR = new zzf();
    private static final zza zzfwi = new zze(new String[0], null);
    private boolean mClosed;
    private final int zzcd;
    private int zzeck;
    private final String[] zzfwb;
    private Bundle zzfwc;
    private final CursorWindow[] zzfwd;
    private final Bundle zzfwe;
    private int[] zzfwf;
    int zzfwg;
    private boolean zzfwh;

    public static class zza {
        /* access modifiers changed from: private */
        public final String[] zzfwb;
        /* access modifiers changed from: private */
        public final ArrayList<HashMap<String, Object>> zzfwj;
        private final String zzfwk;
        private final HashMap<Object, Integer> zzfwl;
        private boolean zzfwm;
        private String zzfwn;

        private zza(String[] strArr, String str) {
            this.zzfwb = (String[]) zzbq.checkNotNull(strArr);
            this.zzfwj = new ArrayList<>();
            this.zzfwk = str;
            this.zzfwl = new HashMap<>();
            this.zzfwm = false;
            this.zzfwn = null;
        }

        /* synthetic */ zza(String[] strArr, String str, zze zze) {
            this(strArr, null);
        }

        public zza zza(ContentValues contentValues) {
            zzc.zzv(contentValues);
            HashMap hashMap = new HashMap(contentValues.size());
            for (Entry entry : contentValues.valueSet()) {
                hashMap.put((String) entry.getKey(), entry.getValue());
            }
            return zza(hashMap);
        }

        public zza zza(HashMap<String, Object> hashMap) {
            int intValue;
            zzc.zzv(hashMap);
            if (this.zzfwk == null) {
                intValue = -1;
            } else {
                Object obj = hashMap.get(this.zzfwk);
                if (obj == null) {
                    intValue = -1;
                } else {
                    Integer num = (Integer) this.zzfwl.get(obj);
                    if (num == null) {
                        this.zzfwl.put(obj, Integer.valueOf(this.zzfwj.size()));
                        intValue = -1;
                    } else {
                        intValue = num.intValue();
                    }
                }
            }
            if (intValue == -1) {
                this.zzfwj.add(hashMap);
            } else {
                this.zzfwj.remove(intValue);
                this.zzfwj.add(intValue, hashMap);
            }
            this.zzfwm = false;
            return this;
        }

        public final DataHolder zzcb(int i) {
            return new DataHolder(this, 0, (Bundle) null, (zze) null);
        }
    }

    public static class zzb extends RuntimeException {
        public zzb(String str) {
            super(str);
        }
    }

    DataHolder(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
        this.mClosed = false;
        this.zzfwh = true;
        this.zzeck = i;
        this.zzfwb = strArr;
        this.zzfwd = cursorWindowArr;
        this.zzcd = i2;
        this.zzfwe = bundle;
    }

    private DataHolder(zza zza2, int i, Bundle bundle) {
        this(zza2.zzfwb, zza(zza2, -1), i, (Bundle) null);
    }

    /* synthetic */ DataHolder(zza zza2, int i, Bundle bundle, zze zze) {
        this(zza2, 0, null);
    }

    private DataHolder(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
        this.mClosed = false;
        this.zzfwh = true;
        this.zzeck = 1;
        this.zzfwb = (String[]) zzbq.checkNotNull(strArr);
        this.zzfwd = (CursorWindow[]) zzbq.checkNotNull(cursorWindowArr);
        this.zzcd = i;
        this.zzfwe = bundle;
        zzajz();
    }

    private static CursorWindow[] zza(zza zza2, int i) {
        int i2;
        boolean z;
        CursorWindow cursorWindow;
        if (zza2.zzfwb.length == 0) {
            return new CursorWindow[0];
        }
        ArrayList zzb2 = zza2.zzfwj;
        int size = zzb2.size();
        CursorWindow cursorWindow2 = new CursorWindow(false);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cursorWindow2);
        cursorWindow2.setNumColumns(zza2.zzfwb.length);
        int i3 = 0;
        boolean z2 = false;
        while (i3 < size) {
            try {
                if (!cursorWindow2.allocRow()) {
                    Log.d("DataHolder", "Allocating additional cursor window for large data set (row " + i3 + ")");
                    cursorWindow2 = new CursorWindow(false);
                    cursorWindow2.setStartPosition(i3);
                    cursorWindow2.setNumColumns(zza2.zzfwb.length);
                    arrayList.add(cursorWindow2);
                    if (!cursorWindow2.allocRow()) {
                        Log.e("DataHolder", "Unable to allocate row to hold data.");
                        arrayList.remove(cursorWindow2);
                        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                    }
                }
                Map map = (Map) zzb2.get(i3);
                boolean z3 = true;
                for (int i4 = 0; i4 < zza2.zzfwb.length && z3; i4++) {
                    String str = zza2.zzfwb[i4];
                    Object obj = map.get(str);
                    if (obj == null) {
                        z3 = cursorWindow2.putNull(i3, i4);
                    } else if (obj instanceof String) {
                        z3 = cursorWindow2.putString((String) obj, i3, i4);
                    } else if (obj instanceof Long) {
                        z3 = cursorWindow2.putLong(((Long) obj).longValue(), i3, i4);
                    } else if (obj instanceof Integer) {
                        z3 = cursorWindow2.putLong((long) ((Integer) obj).intValue(), i3, i4);
                    } else if (obj instanceof Boolean) {
                        z3 = cursorWindow2.putLong(((Boolean) obj).booleanValue() ? 1 : 0, i3, i4);
                    } else if (obj instanceof byte[]) {
                        z3 = cursorWindow2.putBlob((byte[]) obj, i3, i4);
                    } else if (obj instanceof Double) {
                        z3 = cursorWindow2.putDouble(((Double) obj).doubleValue(), i3, i4);
                    } else if (obj instanceof Float) {
                        z3 = cursorWindow2.putDouble((double) ((Float) obj).floatValue(), i3, i4);
                    } else {
                        String valueOf = String.valueOf(obj);
                        throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 32 + String.valueOf(valueOf).length()).append("Unsupported object for column ").append(str).append(": ").append(valueOf).toString());
                    }
                }
                if (z3) {
                    i2 = i3;
                    z = false;
                    cursorWindow = cursorWindow2;
                } else if (z2) {
                    throw new zzb("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                } else {
                    Log.d("DataHolder", "Couldn't populate window data for row " + i3 + " - allocating new window.");
                    cursorWindow2.freeLastRow();
                    CursorWindow cursorWindow3 = new CursorWindow(false);
                    cursorWindow3.setStartPosition(i3);
                    cursorWindow3.setNumColumns(zza2.zzfwb.length);
                    arrayList.add(cursorWindow3);
                    i2 = i3 - 1;
                    cursorWindow = cursorWindow3;
                    z = true;
                }
                z2 = z;
                cursorWindow2 = cursorWindow;
                i3 = i2 + 1;
            } catch (RuntimeException e) {
                RuntimeException runtimeException = e;
                int size2 = arrayList.size();
                for (int i5 = 0; i5 < size2; i5++) {
                    ((CursorWindow) arrayList.get(i5)).close();
                }
                throw runtimeException;
            }
        }
        return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
    }

    public static zza zzb(String[] strArr) {
        return new zza(strArr, null, null);
    }

    public static DataHolder zzca(int i) {
        return new DataHolder(zzfwi, i, null);
    }

    private final void zzh(String str, int i) {
        if (this.zzfwc == null || !this.zzfwc.containsKey(str)) {
            String str2 = "No such column: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (isClosed()) {
            throw new IllegalArgumentException("Buffer is closed.");
        } else if (i < 0 || i >= this.zzfwg) {
            throw new CursorIndexOutOfBoundsException(i, this.zzfwg);
        }
    }

    public final void close() {
        synchronized (this) {
            if (!this.mClosed) {
                this.mClosed = true;
                for (CursorWindow close : this.zzfwd) {
                    close.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            if (this.zzfwh && this.zzfwd.length > 0 && !isClosed()) {
                close();
                String obj = toString();
                Log.e("DataBuffer", new StringBuilder(String.valueOf(obj).length() + Opcodes.MUL_INT_2ADDR).append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ").append(obj).append(")").toString());
            }
        } finally {
            super.finalize();
        }
    }

    public final int getCount() {
        return this.zzfwg;
    }

    public final int getStatusCode() {
        return this.zzcd;
    }

    public final boolean isClosed() {
        boolean z;
        synchronized (this) {
            z = this.mClosed;
        }
        return z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzfwb, false);
        zzbfp.zza(parcel, 2, (T[]) this.zzfwd, i, false);
        zzbfp.zzc(parcel, 3, this.zzcd);
        zzbfp.zza(parcel, 4, this.zzfwe, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
        if ((i & 1) != 0) {
            close();
        }
    }

    public final void zza(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
        zzh(str, i);
        this.zzfwd[i2].copyStringToBuffer(i, this.zzfwc.getInt(str), charArrayBuffer);
    }

    public final Bundle zzagk() {
        return this.zzfwe;
    }

    public final void zzajz() {
        this.zzfwc = new Bundle();
        for (int i = 0; i < this.zzfwb.length; i++) {
            this.zzfwc.putInt(this.zzfwb[i], i);
        }
        this.zzfwf = new int[this.zzfwd.length];
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzfwd.length; i3++) {
            this.zzfwf[i3] = i2;
            i2 += this.zzfwd[i3].getNumRows() - (i2 - this.zzfwd[i3].getStartPosition());
        }
        this.zzfwg = i2;
    }

    public final long zzb(String str, int i, int i2) {
        zzh(str, i);
        return this.zzfwd[i2].getLong(i, this.zzfwc.getInt(str));
    }

    public final int zzbz(int i) {
        int i2 = 0;
        zzbq.checkState(i >= 0 && i < this.zzfwg);
        while (true) {
            if (i2 >= this.zzfwf.length) {
                break;
            } else if (i < this.zzfwf[i2]) {
                i2--;
                break;
            } else {
                i2++;
            }
        }
        return i2 == this.zzfwf.length ? i2 - 1 : i2;
    }

    public final int zzc(String str, int i, int i2) {
        zzh(str, i);
        return this.zzfwd[i2].getInt(i, this.zzfwc.getInt(str));
    }

    public final String zzd(String str, int i, int i2) {
        zzh(str, i);
        return this.zzfwd[i2].getString(i, this.zzfwc.getInt(str));
    }

    public final boolean zze(String str, int i, int i2) {
        zzh(str, i);
        return Long.valueOf(this.zzfwd[i2].getLong(i, this.zzfwc.getInt(str))).longValue() == 1;
    }

    public final float zzf(String str, int i, int i2) {
        zzh(str, i);
        return this.zzfwd[i2].getFloat(i, this.zzfwc.getInt(str));
    }

    public final byte[] zzg(String str, int i, int i2) {
        zzh(str, i);
        return this.zzfwd[i2].getBlob(i, this.zzfwc.getInt(str));
    }

    public final boolean zzga(String str) {
        return this.zzfwc.containsKey(str);
    }

    public final boolean zzh(String str, int i, int i2) {
        zzh(str, i);
        return this.zzfwd[i2].isNull(i, this.zzfwc.getInt(str));
    }
}
