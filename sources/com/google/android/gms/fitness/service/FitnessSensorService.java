package com.google.android.gms.fitness.service;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.internal.zzbwz;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzcak;
import com.google.android.gms.internal.zzcam;
import com.google.android.gms.internal.zzcap;
import java.util.List;

public abstract class FitnessSensorService extends Service {
    public static final String SERVICE_INTERFACE = "com.google.android.gms.fitness.service.FitnessSensorService";
    private zza zzhiq;

    static class zza extends zzcap {
        private final FitnessSensorService zzhir;

        private zza(FitnessSensorService fitnessSensorService) {
            this.zzhir = fitnessSensorService;
        }

        public final void zza(FitnessSensorServiceRequest fitnessSensorServiceRequest, zzbyf zzbyf) throws RemoteException {
            this.zzhir.zzaqz();
            if (this.zzhir.onRegister(fitnessSensorServiceRequest)) {
                zzbyf.zzn(Status.zzfni);
            } else {
                zzbyf.zzn(new Status(13));
            }
        }

        public final void zza(zzcak zzcak, zzbwz zzbwz) throws RemoteException {
            this.zzhir.zzaqz();
            zzbwz.zza(new DataSourcesResult(this.zzhir.onFindDataSources(zzcak.getDataTypes()), Status.zzfni));
        }

        public final void zza(zzcam zzcam, zzbyf zzbyf) throws RemoteException {
            this.zzhir.zzaqz();
            if (this.zzhir.onUnregister(zzcam.getDataSource())) {
                zzbyf.zzn(Status.zzfni);
            } else {
                zzbyf.zzn(new Status(13));
            }
        }
    }

    @CallSuper
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (Log.isLoggable("FitnessSensorService", 3)) {
            String valueOf = String.valueOf(intent);
            String name = getClass().getName();
            Log.d("FitnessSensorService", new StringBuilder(String.valueOf(valueOf).length() + 20 + String.valueOf(name).length()).append("Intent ").append(valueOf).append(" received by ").append(name).toString());
        }
        return this.zzhiq.asBinder();
    }

    @CallSuper
    public void onCreate() {
        super.onCreate();
        this.zzhiq = new zza();
    }

    public abstract List<DataSource> onFindDataSources(List<DataType> list);

    public abstract boolean onRegister(FitnessSensorServiceRequest fitnessSensorServiceRequest);

    public abstract boolean onUnregister(DataSource dataSource);

    /* access modifiers changed from: protected */
    @TargetApi(19)
    public final void zzaqz() throws SecurityException {
        int callingUid = Binder.getCallingUid();
        if (zzq.zzaml()) {
            ((AppOpsManager) getSystemService("appops")).checkPackage(callingUid, "com.google.android.gms");
            return;
        }
        String[] packagesForUid = getPackageManager().getPackagesForUid(callingUid);
        if (packagesForUid != null) {
            int length = packagesForUid.length;
            int i = 0;
            while (i < length) {
                if (!packagesForUid[i].equals("com.google.android.gms")) {
                    i++;
                } else {
                    return;
                }
            }
        }
        throw new SecurityException("Unauthorized caller");
    }
}
