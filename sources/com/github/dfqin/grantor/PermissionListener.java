package com.github.dfqin.grantor;

import android.support.annotation.NonNull;

public interface PermissionListener {
    void permissionDenied(@NonNull String[] strArr);

    void permissionGranted(@NonNull String[] strArr);
}
