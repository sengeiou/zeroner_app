package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.tencent.open.SocialConstants;
import java.util.Arrays;

public class PlaceReport extends zzbfm implements ReflectedParcelable {
    public static final Creator<PlaceReport> CREATOR = new zzl();
    private final String mTag;
    private final String zzdrc;
    private int zzeck;
    private final String zzinh;

    PlaceReport(int i, String str, String str2, String str3) {
        this.zzeck = i;
        this.zzinh = str;
        this.mTag = str2;
        this.zzdrc = str3;
    }

    public static PlaceReport create(String str, String str2) {
        boolean z = false;
        String str3 = "unknown";
        zzbq.checkNotNull(str);
        zzbq.zzgm(str2);
        zzbq.zzgm(str3);
        char c = 65535;
        switch (str3.hashCode()) {
            case -1436706272:
                if (str3.equals("inferredGeofencing")) {
                    c = 2;
                    break;
                }
                break;
            case -1194968642:
                if (str3.equals("userReported")) {
                    c = 1;
                    break;
                }
                break;
            case -284840886:
                if (str3.equals("unknown")) {
                    c = 0;
                    break;
                }
                break;
            case -262743844:
                if (str3.equals("inferredReverseGeocoding")) {
                    c = 4;
                    break;
                }
                break;
            case 1164924125:
                if (str3.equals("inferredSnappedToRoad")) {
                    c = 5;
                    break;
                }
                break;
            case 1287171955:
                if (str3.equals("inferredRadioSignals")) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                z = true;
                break;
        }
        zzbq.checkArgument(z, "Invalid source");
        return new PlaceReport(1, str, str2, str3);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) obj;
        return zzbg.equal(this.zzinh, placeReport.zzinh) && zzbg.equal(this.mTag, placeReport.mTag) && zzbg.equal(this.zzdrc, placeReport.zzdrc);
    }

    public String getPlaceId() {
        return this.zzinh;
    }

    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzinh, this.mTag, this.zzdrc});
    }

    public String toString() {
        zzbi zzx = zzbg.zzx(this);
        zzx.zzg("placeId", this.zzinh);
        zzx.zzg("tag", this.mTag);
        if (!"unknown".equals(this.zzdrc)) {
            zzx.zzg(SocialConstants.PARAM_SOURCE, this.zzdrc);
        }
        return zzx.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, this.zzeck);
        zzbfp.zza(parcel, 2, getPlaceId(), false);
        zzbfp.zza(parcel, 3, getTag(), false);
        zzbfp.zza(parcel, 4, this.zzdrc, false);
        zzbfp.zzai(parcel, zze);
    }
}
