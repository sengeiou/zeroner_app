package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public final class zzdb extends Fragment implements zzcf {
    private static WeakHashMap<FragmentActivity, WeakReference<zzdb>> zzfue = new WeakHashMap<>();
    /* access modifiers changed from: private */
    public int zzcbc = 0;
    private Map<String, LifecycleCallback> zzfuf = new ArrayMap();
    /* access modifiers changed from: private */
    public Bundle zzfug;

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0010, code lost:
        if (r0 != null) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.common.api.internal.zzdb zza(android.support.v4.app.FragmentActivity r3) {
        /*
            java.util.WeakHashMap<android.support.v4.app.FragmentActivity, java.lang.ref.WeakReference<com.google.android.gms.common.api.internal.zzdb>> r0 = zzfue
            java.lang.Object r0 = r0.get(r3)
            java.lang.ref.WeakReference r0 = (java.lang.ref.WeakReference) r0
            if (r0 == 0) goto L_0x0013
            java.lang.Object r0 = r0.get()
            com.google.android.gms.common.api.internal.zzdb r0 = (com.google.android.gms.common.api.internal.zzdb) r0
            if (r0 == 0) goto L_0x0013
        L_0x0012:
            return r0
        L_0x0013:
            android.support.v4.app.FragmentManager r0 = r3.getSupportFragmentManager()     // Catch:{ ClassCastException -> 0x004a }
            java.lang.String r1 = "SupportLifecycleFragmentImpl"
            android.support.v4.app.Fragment r0 = r0.findFragmentByTag(r1)     // Catch:{ ClassCastException -> 0x004a }
            com.google.android.gms.common.api.internal.zzdb r0 = (com.google.android.gms.common.api.internal.zzdb) r0     // Catch:{ ClassCastException -> 0x004a }
            if (r0 == 0) goto L_0x0028
            boolean r1 = r0.isRemoving()
            if (r1 == 0) goto L_0x003f
        L_0x0028:
            com.google.android.gms.common.api.internal.zzdb r0 = new com.google.android.gms.common.api.internal.zzdb
            r0.<init>()
            android.support.v4.app.FragmentManager r1 = r3.getSupportFragmentManager()
            android.support.v4.app.FragmentTransaction r1 = r1.beginTransaction()
            java.lang.String r2 = "SupportLifecycleFragmentImpl"
            android.support.v4.app.FragmentTransaction r1 = r1.add(r0, r2)
            r1.commitAllowingStateLoss()
        L_0x003f:
            java.util.WeakHashMap<android.support.v4.app.FragmentActivity, java.lang.ref.WeakReference<com.google.android.gms.common.api.internal.zzdb>> r1 = zzfue
            java.lang.ref.WeakReference r2 = new java.lang.ref.WeakReference
            r2.<init>(r0)
            r1.put(r3, r2)
            goto L_0x0012
        L_0x004a:
            r0 = move-exception
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzdb.zza(android.support.v4.app.FragmentActivity):com.google.android.gms.common.api.internal.zzdb");
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (LifecycleCallback dump : this.zzfuf.values()) {
            dump.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (LifecycleCallback onActivityResult : this.zzfuf.values()) {
            onActivityResult.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzcbc = 1;
        this.zzfug = bundle;
        for (Entry entry : this.zzfuf.entrySet()) {
            ((LifecycleCallback) entry.getValue()).onCreate(bundle != null ? bundle.getBundle((String) entry.getKey()) : null);
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzcbc = 5;
        for (LifecycleCallback onDestroy : this.zzfuf.values()) {
            onDestroy.onDestroy();
        }
    }

    public final void onResume() {
        super.onResume();
        this.zzcbc = 3;
        for (LifecycleCallback onResume : this.zzfuf.values()) {
            onResume.onResume();
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Entry entry : this.zzfuf.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((LifecycleCallback) entry.getValue()).onSaveInstanceState(bundle2);
                bundle.putBundle((String) entry.getKey(), bundle2);
            }
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzcbc = 2;
        for (LifecycleCallback onStart : this.zzfuf.values()) {
            onStart.onStart();
        }
    }

    public final void onStop() {
        super.onStop();
        this.zzcbc = 4;
        for (LifecycleCallback onStop : this.zzfuf.values()) {
            onStop.onStop();
        }
    }

    public final <T extends LifecycleCallback> T zza(String str, Class<T> cls) {
        return (LifecycleCallback) cls.cast(this.zzfuf.get(str));
    }

    public final void zza(String str, @NonNull LifecycleCallback lifecycleCallback) {
        if (!this.zzfuf.containsKey(str)) {
            this.zzfuf.put(str, lifecycleCallback);
            if (this.zzcbc > 0) {
                new Handler(Looper.getMainLooper()).post(new zzdc(this, lifecycleCallback, str));
                return;
            }
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 59).append("LifecycleCallback with tag ").append(str).append(" already added to this fragment.").toString());
    }

    public final /* synthetic */ Activity zzajn() {
        return getActivity();
    }
}
