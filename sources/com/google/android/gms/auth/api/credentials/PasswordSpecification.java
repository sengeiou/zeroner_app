package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

public final class PasswordSpecification extends zzbfm implements ReflectedParcelable {
    public static final Creator<PasswordSpecification> CREATOR = new zzj();
    public static final PasswordSpecification zzeft = new zza().zzj(12, 16).zzes("abcdefghijkmnopqrstxyzABCDEFGHJKLMNPQRSTXY3456789").zze("abcdefghijkmnopqrstxyz", 1).zze("ABCDEFGHJKLMNPQRSTXY", 1).zze("3456789", 1).zzaaw();
    private static PasswordSpecification zzefu = new zza().zzj(12, 16).zzes("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890").zze("abcdefghijklmnopqrstuvwxyz", 1).zze("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 1).zze("1234567890", 1).zzaaw();
    private final Random zzbfo;
    @VisibleForTesting
    private String zzefv;
    @VisibleForTesting
    private List<String> zzefw;
    @VisibleForTesting
    private List<Integer> zzefx;
    @VisibleForTesting
    private int zzefy;
    @VisibleForTesting
    private int zzefz;
    private final int[] zzega;

    public static class zza {
        private final List<String> zzefw = new ArrayList();
        private final List<Integer> zzefx = new ArrayList();
        private int zzefy = 12;
        private int zzefz = 16;
        private final TreeSet<Character> zzegb = new TreeSet<>();

        private static TreeSet<Character> zzn(String str, String str2) {
            char[] charArray;
            if (TextUtils.isEmpty(str)) {
                throw new zzb(String.valueOf(str2).concat(" cannot be null or empty"));
            }
            TreeSet<Character> treeSet = new TreeSet<>();
            for (char c : str.toCharArray()) {
                if (PasswordSpecification.zzc(c, 32, Opcodes.NOT_LONG)) {
                    throw new zzb(String.valueOf(str2).concat(" must only contain ASCII printable characters"));
                }
                treeSet.add(Character.valueOf(c));
            }
            return treeSet;
        }

        public final PasswordSpecification zzaaw() {
            if (this.zzegb.isEmpty()) {
                throw new zzb("no allowed characters specified");
            }
            int i = 0;
            for (Integer intValue : this.zzefx) {
                i = intValue.intValue() + i;
            }
            if (i > this.zzefz) {
                throw new zzb("required character count cannot be greater than the max password size");
            }
            boolean[] zArr = new boolean[95];
            for (String charArray : this.zzefw) {
                char[] charArray2 = charArray.toCharArray();
                int length = charArray2.length;
                int i2 = 0;
                while (true) {
                    if (i2 < length) {
                        char c = charArray2[i2];
                        if (zArr[c - ' ']) {
                            throw new zzb("character " + c + " occurs in more than one required character set");
                        }
                        zArr[c - ' '] = true;
                        i2++;
                    }
                }
            }
            return new PasswordSpecification(PasswordSpecification.zzb(this.zzegb), this.zzefw, this.zzefx, this.zzefy, this.zzefz);
        }

        public final zza zze(@NonNull String str, int i) {
            this.zzefw.add(PasswordSpecification.zzb(zzn(str, "requiredChars")));
            this.zzefx.add(Integer.valueOf(1));
            return this;
        }

        public final zza zzes(@NonNull String str) {
            this.zzegb.addAll(zzn(str, "allowedChars"));
            return this;
        }

        public final zza zzj(int i, int i2) {
            this.zzefy = 12;
            this.zzefz = 16;
            return this;
        }
    }

    public static class zzb extends Error {
        public zzb(String str) {
            super(str);
        }
    }

    PasswordSpecification(String str, List<String> list, List<Integer> list2, int i, int i2) {
        this.zzefv = str;
        this.zzefw = Collections.unmodifiableList(list);
        this.zzefx = Collections.unmodifiableList(list2);
        this.zzefy = i;
        this.zzefz = i2;
        int[] iArr = new int[95];
        Arrays.fill(iArr, -1);
        int i3 = 0;
        for (String charArray : this.zzefw) {
            char[] charArray2 = charArray.toCharArray();
            int length = charArray2.length;
            for (int i4 = 0; i4 < length; i4++) {
                iArr[charArray2[i4] - ' '] = i3;
            }
            i3++;
        }
        this.zzega = iArr;
        this.zzbfo = new SecureRandom();
    }

    /* access modifiers changed from: private */
    public static String zzb(Collection<Character> collection) {
        char[] cArr = new char[collection.size()];
        int i = 0;
        Iterator it = collection.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return new String(cArr);
            }
            i = i2 + 1;
            cArr[i2] = ((Character) it.next()).charValue();
        }
    }

    /* access modifiers changed from: private */
    public static boolean zzc(int i, int i2, int i3) {
        return i < 32 || i > 126;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzefv, false);
        zzbfp.zzb(parcel, 2, this.zzefw, false);
        zzbfp.zza(parcel, 3, this.zzefx, false);
        zzbfp.zzc(parcel, 4, this.zzefy);
        zzbfp.zzc(parcel, 5, this.zzefz);
        zzbfp.zzai(parcel, zze);
    }
}
