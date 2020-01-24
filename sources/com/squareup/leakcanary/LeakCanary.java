package com.squareup.leakcanary;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

public final class LeakCanary {
    @NonNull
    public static RefWatcher install(@NonNull Application application) {
        return RefWatcher.DISABLED;
    }

    @NonNull
    public static RefWatcher installedRefWatcher() {
        return RefWatcher.DISABLED;
    }

    public static boolean isInAnalyzerProcess(@NonNull Context context) {
        return false;
    }

    private LeakCanary() {
        throw new AssertionError();
    }
}
