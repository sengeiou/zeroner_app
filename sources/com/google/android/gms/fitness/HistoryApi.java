package com.google.android.gms.fitness;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataDeleteRequest;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;
import com.google.android.gms.fitness.request.DataUpdateRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataReadResult;
import com.google.android.gms.internal.zzbfr;
import java.util.concurrent.TimeUnit;

public interface HistoryApi {

    public static class ViewIntentBuilder {
        private final Context zzair;
        private final DataType zzgzg;
        private DataSource zzgzh;
        private long zzgzi;
        private long zzgzj;
        private String zzgzk;

        public ViewIntentBuilder(Context context, DataType dataType) {
            this.zzair = context;
            this.zzgzg = dataType;
        }

        public Intent build() {
            boolean z = true;
            zzbq.zza(this.zzgzi > 0, (Object) "Start time must be set");
            if (this.zzgzj <= this.zzgzi) {
                z = false;
            }
            zzbq.zza(z, (Object) "End time must be set and after start time");
            Intent intent = new Intent(Fitness.ACTION_VIEW);
            intent.setType(DataType.getMimeType(this.zzgzh.getDataType()));
            intent.putExtra(Fitness.EXTRA_START_TIME, this.zzgzi);
            intent.putExtra(Fitness.EXTRA_END_TIME, this.zzgzj);
            zzbfr.zza(this.zzgzh, intent, DataSource.EXTRA_DATA_SOURCE);
            if (this.zzgzk != null) {
                Intent intent2 = new Intent(intent).setPackage(this.zzgzk);
                ResolveInfo resolveActivity = this.zzair.getPackageManager().resolveActivity(intent2, 0);
                if (resolveActivity != null) {
                    intent2.setComponent(new ComponentName(this.zzgzk, resolveActivity.activityInfo.name));
                    return intent2;
                }
            }
            return intent;
        }

        public ViewIntentBuilder setDataSource(DataSource dataSource) {
            zzbq.zzb(dataSource.getDataType().equals(this.zzgzg), "Data source %s is not for the data type %s", dataSource, this.zzgzg);
            this.zzgzh = dataSource;
            return this;
        }

        public ViewIntentBuilder setPreferredApplication(String str) {
            this.zzgzk = str;
            return this;
        }

        public ViewIntentBuilder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            this.zzgzi = timeUnit.toMillis(j);
            this.zzgzj = timeUnit.toMillis(j2);
            return this;
        }
    }

    PendingResult<Status> deleteData(GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest);

    PendingResult<Status> insertData(GoogleApiClient googleApiClient, DataSet dataSet);

    PendingResult<DailyTotalResult> readDailyTotal(GoogleApiClient googleApiClient, DataType dataType);

    PendingResult<DailyTotalResult> readDailyTotalFromLocalDevice(GoogleApiClient googleApiClient, DataType dataType);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    PendingResult<DataReadResult> readData(GoogleApiClient googleApiClient, DataReadRequest dataReadRequest);

    @RequiresPermission(anyOf = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.BODY_SENSORS"}, conditional = true)
    PendingResult<Status> registerDataUpdateListener(GoogleApiClient googleApiClient, DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest);

    PendingResult<Status> unregisterDataUpdateListener(GoogleApiClient googleApiClient, PendingIntent pendingIntent);

    PendingResult<Status> updateData(GoogleApiClient googleApiClient, DataUpdateRequest dataUpdateRequest);
}
