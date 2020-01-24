package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

class AppCompatActivityPermissionHelper extends BaseSupportPermissionsHelper<AppCompatActivity> {
    public AppCompatActivityPermissionHelper(AppCompatActivity host) {
        super(host);
    }

    public FragmentManager getSupportFragmentManager() {
        return ((AppCompatActivity) getHost()).getSupportFragmentManager();
    }

    public void directRequestPermissions(int requestCode, @NonNull String... perms) {
        ActivityCompat.requestPermissions((Activity) getHost(), perms, requestCode);
    }

    public boolean shouldShowRequestPermissionRationale(@NonNull String perm) {
        return ActivityCompat.shouldShowRequestPermissionRationale((Activity) getHost(), perm);
    }

    public Context getContext() {
        return (Context) getHost();
    }
}
