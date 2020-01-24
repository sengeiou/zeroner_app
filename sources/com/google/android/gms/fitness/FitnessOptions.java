package com.google.android.gms.fitness;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.api.Api.ApiOptions.HasGoogleSignInAccountOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class FitnessOptions implements GoogleSignInOptionsExtension, HasGoogleSignInAccountOptions {
    public static final int ACCESS_READ = 0;
    public static final int ACCESS_WRITE = 1;
    private final Map<Integer, List<DataType>> zzgzc;
    private final Set<Scope> zzgzd;
    private final GoogleSignInAccount zzgze;

    public static final class Builder {
        private final Map<Integer, List<DataType>> map;
        private GoogleSignInAccount zzgze;

        private Builder() {
            this.map = new HashMap();
        }

        /* access modifiers changed from: private */
        public final Builder zzc(@NonNull GoogleSignInAccount googleSignInAccount) {
            this.zzgze = googleSignInAccount;
            return this;
        }

        public final Builder addDataType(@NonNull DataType dataType) {
            return addDataType(dataType, 0);
        }

        public final Builder addDataType(@NonNull DataType dataType, int i) {
            if (i == 0 || i == 1) {
                List list = (List) this.map.get(Integer.valueOf(i));
                if (list == null) {
                    list = new ArrayList();
                    this.map.put(Integer.valueOf(i), list);
                }
                list.add(dataType);
                return this;
            }
            throw new IllegalArgumentException("vallie access types are FitnessOptions.ACCESS_READ or FitnessOptions,ACCESS_WRITE");
        }

        public final FitnessOptions build() {
            return new FitnessOptions(this.map, this.zzgze);
        }
    }

    private FitnessOptions(Map<Integer, List<DataType>> map, GoogleSignInAccount googleSignInAccount) {
        this.zzgzc = map;
        this.zzgze = googleSignInAccount;
        ArrayList arrayList = new ArrayList();
        for (Entry entry : map.entrySet()) {
            for (DataType dataType : (List) entry.getValue()) {
                if (((Integer) entry.getKey()).intValue() == 0 && dataType.zzaqn() != null) {
                    arrayList.add(new Scope(dataType.zzaqn()));
                } else if (((Integer) entry.getKey()).intValue() == 1 && dataType.zzaqo() != null) {
                    arrayList.add(new Scope(dataType.zzaqo()));
                }
            }
        }
        this.zzgzd = zzh.zzi(arrayList);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder zzb(GoogleSignInAccount googleSignInAccount) {
        return googleSignInAccount != null ? new Builder().zzc(googleSignInAccount) : new Builder();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FitnessOptions fitnessOptions = (FitnessOptions) obj;
        return zzbg.equal(this.zzgzc, fitnessOptions.zzgzc) && zzbg.equal(this.zzgze, fitnessOptions.zzgze);
    }

    public int getExtensionType() {
        return 3;
    }

    public GoogleSignInAccount getGoogleSignInAccount() {
        return this.zzgze;
    }

    @Nullable
    public List<Scope> getImpliedScopes() {
        return new ArrayList(this.zzgzd);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgzc, this.zzgze});
    }

    public Bundle toBundle() {
        return new Bundle();
    }
}
