package com.tbruyelle.rxpermissions2;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import io.reactivex.subjects.PublishSubject;
import java.util.HashMap;
import java.util.Map;

public class RxPermissionsFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_CODE = 42;
    private boolean mLogging;
    private Map<String, PublishSubject<Permission>> mSubjects = new HashMap();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(23)
    public void requestPermissions(@NonNull String[] permissions) {
        requestPermissions(permissions, 42);
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 42) {
            boolean[] shouldShowRequestPermissionRationale = new boolean[permissions.length];
            for (int i = 0; i < permissions.length; i++) {
                shouldShowRequestPermissionRationale[i] = shouldShowRequestPermissionRationale(permissions[i]);
            }
            onRequestPermissionsResult(permissions, grantResults, shouldShowRequestPermissionRationale);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onRequestPermissionsResult(String[] permissions, int[] grantResults, boolean[] shouldShowRequestPermissionRationale) {
        int size = permissions.length;
        for (int i = 0; i < size; i++) {
            log("onRequestPermissionsResult  " + permissions[i]);
            PublishSubject<Permission> subject = (PublishSubject) this.mSubjects.get(permissions[i]);
            if (subject == null) {
                Log.e(RxPermissions.TAG, "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
                return;
            }
            this.mSubjects.remove(permissions[i]);
            subject.onNext(new Permission(permissions[i], grantResults[i] == 0, shouldShowRequestPermissionRationale[i]));
            subject.onComplete();
        }
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(23)
    public boolean isGranted(String permission) {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            return fragmentActivity.checkSelfPermission(permission) == 0;
        }
        throw new IllegalStateException("This fragment must be attached to an activity.");
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(23)
    public boolean isRevoked(String permission) {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity != null) {
            return fragmentActivity.getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
        }
        throw new IllegalStateException("This fragment must be attached to an activity.");
    }

    public void setLogging(boolean logging) {
        this.mLogging = logging;
    }

    public PublishSubject<Permission> getSubjectByPermission(@NonNull String permission) {
        return (PublishSubject) this.mSubjects.get(permission);
    }

    public boolean containsByPermission(@NonNull String permission) {
        return this.mSubjects.containsKey(permission);
    }

    public void setSubjectForPermission(@NonNull String permission, @NonNull PublishSubject<Permission> subject) {
        this.mSubjects.put(permission, subject);
    }

    /* access modifiers changed from: 0000 */
    public void log(String message) {
        if (this.mLogging) {
            Log.d(RxPermissions.TAG, message);
        }
    }
}
