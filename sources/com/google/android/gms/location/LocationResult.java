package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult extends zzbfm implements ReflectedParcelable {
    public static final Creator<LocationResult> CREATOR = new zzx();
    static final List<Location> zziju = Collections.emptyList();
    private final List<Location> zzijv;

    LocationResult(List<Location> list) {
        this.zzijv = list;
    }

    public static LocationResult create(List<Location> list) {
        if (list == null) {
            list = zziju;
        }
        return new LocationResult(list);
    }

    public static LocationResult extractResult(Intent intent) {
        if (!hasResult(intent)) {
            return null;
        }
        return (LocationResult) intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public static boolean hasResult(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof LocationResult)) {
            return false;
        }
        LocationResult locationResult = (LocationResult) obj;
        if (locationResult.zzijv.size() != this.zzijv.size()) {
            return false;
        }
        Iterator it = this.zzijv.iterator();
        for (Location time : locationResult.zzijv) {
            if (((Location) it.next()).getTime() != time.getTime()) {
                return false;
            }
        }
        return true;
    }

    public final Location getLastLocation() {
        int size = this.zzijv.size();
        if (size == 0) {
            return null;
        }
        return (Location) this.zzijv.get(size - 1);
    }

    @NonNull
    public final List<Location> getLocations() {
        return this.zzijv;
    }

    public final int hashCode() {
        int i = 17;
        Iterator it = this.zzijv.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            long time = ((Location) it.next()).getTime();
            i = ((int) (time ^ (time >>> 32))) + (i2 * 31);
        }
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zzijv);
        return new StringBuilder(String.valueOf(valueOf).length() + 27).append("LocationResult[locations: ").append(valueOf).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getLocations(), false);
        zzbfp.zzai(parcel, zze);
    }
}
