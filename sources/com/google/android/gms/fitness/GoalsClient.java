package com.google.android.gms.fitness;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.fitness.request.GoalsReadRequest;
import com.google.android.gms.internal.zzbvq;
import com.google.android.gms.internal.zzbyv;
import com.google.android.gms.tasks.Task;
import java.util.List;

public class GoalsClient extends GoogleApi<FitnessOptions> {
    private static final GoalsApi zzgzf = new zzbyv();

    GoalsClient(@NonNull Activity activity, @NonNull FitnessOptions fitnessOptions) {
        super(activity, zzbvq.zzhen, fitnessOptions, zza.zzfmj);
    }

    GoalsClient(@NonNull Context context, @NonNull FitnessOptions fitnessOptions) {
        super(context, zzbvq.zzhen, fitnessOptions, zza.zzfmj);
    }

    public Task<List<Goal>> readCurrentGoals(GoalsReadRequest goalsReadRequest) {
        return zzbj.zza(zzgzf.readCurrentGoals(zzago(), goalsReadRequest), zzi.zzgnw);
    }
}
