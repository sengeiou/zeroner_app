package com.tbruyelle.rxpermissions2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

public class RxPermissions {
    static final String TAG = RxPermissions.class.getSimpleName();
    static final Object TRIGGER = new Object();
    @VisibleForTesting
    Lazy<RxPermissionsFragment> mRxPermissionsFragment;

    @FunctionalInterface
    public interface Lazy<V> {
        V get();
    }

    public RxPermissions(@NonNull FragmentActivity activity) {
        this.mRxPermissionsFragment = getLazySingleton(activity.getSupportFragmentManager());
    }

    public RxPermissions(@NonNull Fragment fragment) {
        this.mRxPermissionsFragment = getLazySingleton(fragment.getChildFragmentManager());
    }

    @NonNull
    private Lazy<RxPermissionsFragment> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new Lazy<RxPermissionsFragment>() {
            private RxPermissionsFragment rxPermissionsFragment;

            public synchronized RxPermissionsFragment get() {
                if (this.rxPermissionsFragment == null) {
                    this.rxPermissionsFragment = RxPermissions.this.getRxPermissionsFragment(fragmentManager);
                }
                return this.rxPermissionsFragment;
            }
        };
    }

    /* access modifiers changed from: private */
    public RxPermissionsFragment getRxPermissionsFragment(@NonNull FragmentManager fragmentManager) {
        RxPermissionsFragment rxPermissionsFragment = findRxPermissionsFragment(fragmentManager);
        if (!(rxPermissionsFragment == null)) {
            return rxPermissionsFragment;
        }
        RxPermissionsFragment rxPermissionsFragment2 = new RxPermissionsFragment();
        fragmentManager.beginTransaction().add((Fragment) rxPermissionsFragment2, TAG).commitNow();
        return rxPermissionsFragment2;
    }

    private RxPermissionsFragment findRxPermissionsFragment(@NonNull FragmentManager fragmentManager) {
        return (RxPermissionsFragment) fragmentManager.findFragmentByTag(TAG);
    }

    public void setLogging(boolean logging) {
        ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).setLogging(logging);
    }

    public <T> ObservableTransformer<T, Boolean> ensure(final String... permissions) {
        return new ObservableTransformer<T, Boolean>() {
            public ObservableSource<Boolean> apply(Observable<T> o) {
                return RxPermissions.this.request(o, permissions).buffer(permissions.length).flatMap(new Function<List<Permission>, ObservableSource<Boolean>>() {
                    public ObservableSource<Boolean> apply(List<Permission> permissions) {
                        if (permissions.isEmpty()) {
                            return Observable.empty();
                        }
                        for (Permission p : permissions) {
                            if (!p.granted) {
                                return Observable.just(Boolean.valueOf(false));
                            }
                        }
                        return Observable.just(Boolean.valueOf(true));
                    }
                });
            }
        };
    }

    public <T> ObservableTransformer<T, Permission> ensureEach(final String... permissions) {
        return new ObservableTransformer<T, Permission>() {
            public ObservableSource<Permission> apply(Observable<T> o) {
                return RxPermissions.this.request(o, permissions);
            }
        };
    }

    public <T> ObservableTransformer<T, Permission> ensureEachCombined(final String... permissions) {
        return new ObservableTransformer<T, Permission>() {
            public ObservableSource<Permission> apply(Observable<T> o) {
                return RxPermissions.this.request(o, permissions).buffer(permissions.length).flatMap(new Function<List<Permission>, ObservableSource<Permission>>() {
                    public ObservableSource<Permission> apply(List<Permission> permissions) {
                        if (permissions.isEmpty()) {
                            return Observable.empty();
                        }
                        return Observable.just(new Permission(permissions));
                    }
                });
            }
        };
    }

    public Observable<Boolean> request(String... permissions) {
        return Observable.just(TRIGGER).compose(ensure(permissions));
    }

    public Observable<Permission> requestEach(String... permissions) {
        return Observable.just(TRIGGER).compose(ensureEach(permissions));
    }

    public Observable<Permission> requestEachCombined(String... permissions) {
        return Observable.just(TRIGGER).compose(ensureEachCombined(permissions));
    }

    /* access modifiers changed from: private */
    public Observable<Permission> request(Observable<?> trigger, final String... permissions) {
        if (permissions != null && permissions.length != 0) {
            return oneOf(trigger, pending(permissions)).flatMap(new Function<Object, Observable<Permission>>() {
                public Observable<Permission> apply(Object o) {
                    return RxPermissions.this.requestImplementation(permissions);
                }
            });
        }
        throw new IllegalArgumentException("RxPermissions.request/requestEach requires at least one input permission");
    }

    private Observable<?> pending(String... permissions) {
        for (String p : permissions) {
            if (!((RxPermissionsFragment) this.mRxPermissionsFragment.get()).containsByPermission(p)) {
                return Observable.empty();
            }
        }
        return Observable.just(TRIGGER);
    }

    private Observable<?> oneOf(Observable<?> trigger, Observable<?> pending) {
        if (trigger == null) {
            return Observable.just(TRIGGER);
        }
        return Observable.merge((ObservableSource<? extends T>) trigger, (ObservableSource<? extends T>) pending);
    }

    /* access modifiers changed from: private */
    @TargetApi(23)
    public Observable<Permission> requestImplementation(String... permissions) {
        List<Observable<Permission>> list = new ArrayList<>(permissions.length);
        List<String> unrequestedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).log("Requesting permission " + permission);
            if (isGranted(permission)) {
                list.add(Observable.just(new Permission(permission, true, false)));
            } else if (isRevoked(permission)) {
                list.add(Observable.just(new Permission(permission, false, false)));
            } else {
                PublishSubject<Permission> subject = ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).getSubjectByPermission(permission);
                if (subject == null) {
                    unrequestedPermissions.add(permission);
                    subject = PublishSubject.create();
                    ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).setSubjectForPermission(permission, subject);
                }
                list.add(subject);
            }
        }
        if (!unrequestedPermissions.isEmpty()) {
            requestPermissionsFromFragment((String[]) unrequestedPermissions.toArray(new String[unrequestedPermissions.size()]));
        }
        return Observable.concat((ObservableSource<? extends ObservableSource<? extends T>>) Observable.fromIterable(list));
    }

    public Observable<Boolean> shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        if (!isMarshmallow()) {
            return Observable.just(Boolean.valueOf(false));
        }
        return Observable.just(Boolean.valueOf(shouldShowRequestPermissionRationaleImplementation(activity, permissions)));
    }

    @TargetApi(23)
    private boolean shouldShowRequestPermissionRationaleImplementation(Activity activity, String... permissions) {
        for (String p : permissions) {
            if (!isGranted(p) && !activity.shouldShowRequestPermissionRationale(p)) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(23)
    public void requestPermissionsFromFragment(String[] permissions) {
        ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).log("requestPermissionsFromFragment " + TextUtils.join(", ", permissions));
        ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).requestPermissions(permissions);
    }

    public boolean isGranted(String permission) {
        return !isMarshmallow() || ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).isGranted(permission);
    }

    public boolean isRevoked(String permission) {
        return isMarshmallow() && ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).isRevoked(permission);
    }

    /* access modifiers changed from: 0000 */
    public boolean isMarshmallow() {
        return VERSION.SDK_INT >= 23;
    }

    /* access modifiers changed from: 0000 */
    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        ((RxPermissionsFragment) this.mRxPermissionsFragment.get()).onRequestPermissionsResult(permissions, grantResults, new boolean[permissions.length]);
    }
}
