package com.squareup.leakcanary;

import android.support.annotation.NonNull;

public final class RefWatcher {
    @NonNull
    public static final RefWatcher DISABLED = new RefWatcher();

    private RefWatcher() {
    }

    public void watch(@NonNull Object watchedReference) {
    }

    public void watch(@NonNull Object watchedReference, @NonNull String referenceName) {
    }
}
