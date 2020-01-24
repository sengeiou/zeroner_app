package com.google.android.gms.auth.api.credentials;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.List;

public class Credential extends zzbfm implements ReflectedParcelable {
    public static final Creator<Credential> CREATOR = new zza();
    public static final String EXTRA_KEY = "com.google.android.gms.credentials.Credential";
    /* access modifiers changed from: private */
    @Nullable
    public final String mName;
    /* access modifiers changed from: private */
    public final String zzbuz;
    /* access modifiers changed from: private */
    @Nullable
    public final String zzeem;
    /* access modifiers changed from: private */
    @Nullable
    public final Uri zzeew;
    /* access modifiers changed from: private */
    public final List<IdToken> zzeex;
    /* access modifiers changed from: private */
    @Nullable
    public final String zzeey;
    /* access modifiers changed from: private */
    @Nullable
    public final String zzeez;
    /* access modifiers changed from: private */
    @Nullable
    public final String zzefa;
    /* access modifiers changed from: private */
    @Nullable
    public final String zzefb;
    /* access modifiers changed from: private */
    @Nullable
    public final String zzefc;

    public static class Builder {
        private String mName;
        private final String zzbuz;
        private String zzeem;
        private Uri zzeew;
        private List<IdToken> zzeex;
        private String zzeey;
        private String zzeez;
        private String zzefa;
        private String zzefb;
        private String zzefc;

        public Builder(Credential credential) {
            this.zzbuz = credential.zzbuz;
            this.mName = credential.mName;
            this.zzeew = credential.zzeew;
            this.zzeex = credential.zzeex;
            this.zzeey = credential.zzeey;
            this.zzeem = credential.zzeem;
            this.zzeez = credential.zzeez;
            this.zzefa = credential.zzefa;
            this.zzefb = credential.zzefb;
            this.zzefc = credential.zzefc;
        }

        public Builder(String str) {
            this.zzbuz = str;
        }

        public Credential build() {
            return new Credential(this.zzbuz, this.mName, this.zzeew, this.zzeex, this.zzeey, this.zzeem, this.zzeez, this.zzefa, this.zzefb, this.zzefc);
        }

        public Builder setAccountType(String str) {
            this.zzeem = str;
            return this;
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setPassword(String str) {
            this.zzeey = str;
            return this;
        }

        public Builder setProfilePictureUri(Uri uri) {
            this.zzeew = uri;
            return this;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    Credential(java.lang.String r6, java.lang.String r7, android.net.Uri r8, java.util.List<com.google.android.gms.auth.api.credentials.IdToken> r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, java.lang.String r15) {
        /*
            r5 = this;
            r1 = 0
            r5.<init>()
            java.lang.String r0 = "credential identifier cannot be null"
            java.lang.Object r0 = com.google.android.gms.common.internal.zzbq.checkNotNull(r6, r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r2 = r0.trim()
            java.lang.String r0 = "credential identifier cannot be empty"
            com.google.android.gms.common.internal.zzbq.zzh(r2, r0)
            if (r10 == 0) goto L_0x0028
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 == 0) goto L_0x0028
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Password must not be empty if set"
            r0.<init>(r1)
            throw r0
        L_0x0028:
            if (r11 == 0) goto L_0x0086
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x0084
            android.net.Uri r0 = android.net.Uri.parse(r11)
            boolean r3 = r0.isAbsolute()
            if (r3 == 0) goto L_0x0054
            boolean r3 = r0.isHierarchical()
            if (r3 == 0) goto L_0x0054
            java.lang.String r3 = r0.getScheme()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0054
            java.lang.String r3 = r0.getAuthority()
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 == 0) goto L_0x0068
        L_0x0054:
            r0 = r1
        L_0x0055:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L_0x0086
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Account type must be a valid Http/Https URI"
            r0.<init>(r1)
            throw r0
        L_0x0068:
            java.lang.String r3 = "http"
            java.lang.String r4 = r0.getScheme()
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 != 0) goto L_0x0082
            java.lang.String r3 = "https"
            java.lang.String r0 = r0.getScheme()
            boolean r0 = r3.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0084
        L_0x0082:
            r0 = 1
            goto L_0x0055
        L_0x0084:
            r0 = r1
            goto L_0x0055
        L_0x0086:
            boolean r0 = android.text.TextUtils.isEmpty(r11)
            if (r0 != 0) goto L_0x009b
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 != 0) goto L_0x009b
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Password and AccountType are mutually exclusive"
            r0.<init>(r1)
            throw r0
        L_0x009b:
            if (r7 == 0) goto L_0x00a8
            java.lang.String r0 = r7.trim()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x00a8
            r7 = 0
        L_0x00a8:
            r5.mName = r7
            r5.zzeew = r8
            if (r9 != 0) goto L_0x00c3
            java.util.List r0 = java.util.Collections.emptyList()
        L_0x00b2:
            r5.zzeex = r0
            r5.zzbuz = r2
            r5.zzeey = r10
            r5.zzeem = r11
            r5.zzeez = r12
            r5.zzefa = r13
            r5.zzefb = r14
            r5.zzefc = r15
            return
        L_0x00c3:
            java.util.List r0 = java.util.Collections.unmodifiableList(r9)
            goto L_0x00b2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.credentials.Credential.<init>(java.lang.String, java.lang.String, android.net.Uri, java.util.List, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Credential)) {
            return false;
        }
        Credential credential = (Credential) obj;
        return TextUtils.equals(this.zzbuz, credential.zzbuz) && TextUtils.equals(this.mName, credential.mName) && zzbg.equal(this.zzeew, credential.zzeew) && TextUtils.equals(this.zzeey, credential.zzeey) && TextUtils.equals(this.zzeem, credential.zzeem) && TextUtils.equals(this.zzeez, credential.zzeez);
    }

    @Nullable
    public String getAccountType() {
        return this.zzeem;
    }

    @Nullable
    public String getFamilyName() {
        return this.zzefc;
    }

    @Nullable
    public String getGeneratedPassword() {
        return this.zzeez;
    }

    @Nullable
    public String getGivenName() {
        return this.zzefb;
    }

    public String getId() {
        return this.zzbuz;
    }

    public List<IdToken> getIdTokens() {
        return this.zzeex;
    }

    @Nullable
    public String getName() {
        return this.mName;
    }

    @Nullable
    public String getPassword() {
        return this.zzeey;
    }

    @Nullable
    public Uri getProfilePictureUri() {
        return this.zzeew;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzbuz, this.mName, this.zzeew, this.zzeey, this.zzeem, this.zzeez});
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, getId(), false);
        zzbfp.zza(parcel, 2, getName(), false);
        zzbfp.zza(parcel, 3, (Parcelable) getProfilePictureUri(), i, false);
        zzbfp.zzc(parcel, 4, getIdTokens(), false);
        zzbfp.zza(parcel, 5, getPassword(), false);
        zzbfp.zza(parcel, 6, getAccountType(), false);
        zzbfp.zza(parcel, 7, getGeneratedPassword(), false);
        zzbfp.zza(parcel, 8, this.zzefa, false);
        zzbfp.zza(parcel, 9, getGivenName(), false);
        zzbfp.zza(parcel, 10, getFamilyName(), false);
        zzbfp.zzai(parcel, zze);
    }
}
