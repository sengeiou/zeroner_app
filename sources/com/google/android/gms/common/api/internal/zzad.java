package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Map;

final class zzad implements OnCompleteListener<Map<zzh<?>, String>> {
    private /* synthetic */ zzaa zzfqm;
    private zzcu zzfqn;

    zzad(zzaa zzaa, zzcu zzcu) {
        this.zzfqm = zzaa;
        this.zzfqn = zzcu;
    }

    /* access modifiers changed from: 0000 */
    public final void cancel() {
        this.zzfqn.zzabi();
    }

    public final void onComplete(@NonNull Task<Map<zzh<?>, String>> task) {
        this.zzfqm.zzfps.lock();
        try {
            if (!this.zzfqm.zzfqh) {
                this.zzfqn.zzabi();
                return;
            }
            if (task.isSuccessful()) {
                this.zzfqm.zzfqj = new ArrayMap(this.zzfqm.zzfpz.size());
                for (zzz zzagn : this.zzfqm.zzfpz.values()) {
                    this.zzfqm.zzfqj.put(zzagn.zzagn(), ConnectionResult.zzfkr);
                }
            } else if (task.getException() instanceof AvailabilityException) {
                AvailabilityException availabilityException = (AvailabilityException) task.getException();
                if (this.zzfqm.zzfqf) {
                    this.zzfqm.zzfqj = new ArrayMap(this.zzfqm.zzfpz.size());
                    for (zzz zzz : this.zzfqm.zzfpz.values()) {
                        zzh zzagn2 = zzz.zzagn();
                        ConnectionResult connectionResult = availabilityException.getConnectionResult(zzz);
                        if (this.zzfqm.zza(zzz, connectionResult)) {
                            this.zzfqm.zzfqj.put(zzagn2, new ConnectionResult(16));
                        } else {
                            this.zzfqm.zzfqj.put(zzagn2, connectionResult);
                        }
                    }
                } else {
                    this.zzfqm.zzfqj = availabilityException.zzagj();
                }
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                this.zzfqm.zzfqj = Collections.emptyMap();
            }
            if (this.zzfqm.isConnected()) {
                this.zzfqm.zzfqi.putAll(this.zzfqm.zzfqj);
                if (this.zzfqm.zzaht() == null) {
                    this.zzfqm.zzahr();
                    this.zzfqm.zzahs();
                    this.zzfqm.zzfqd.signalAll();
                }
            }
            this.zzfqn.zzabi();
            this.zzfqm.zzfps.unlock();
        } finally {
            this.zzfqm.zzfps.unlock();
        }
    }
}
